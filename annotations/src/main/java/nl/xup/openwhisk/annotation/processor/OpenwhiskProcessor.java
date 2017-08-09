package nl.xup.openwhisk.annotation.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.google.auto.service.AutoService;
import com.google.common.base.Joiner;

import nl.xup.openwhisk.annotation.model.Action;
import nl.xup.openwhisk.annotation.transform.Element2Action;

@SupportedAnnotationTypes("nl.xup.openwhisk.annotation.OpenwhiskAction")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class OpenwhiskProcessor extends AbstractProcessor {

  // --------------------------------------------------------------------------
  // Constants
  // --------------------------------------------------------------------------

  private static final String SPACE = " ";
  private static final String JSON_OBJECT = "com.google.gson.JsonObject";
  private static final Set<Modifier> EXPECTED_METHOD_MODIFIERS =
      new HashSet<>( Arrays.asList( Modifier.PUBLIC, Modifier.STATIC ) );

  // --------------------------------------------------------------------------
  // Override AbstractProcessor
  // --------------------------------------------------------------------------

  @Override
  public boolean process( Set<? extends TypeElement> annotations, RoundEnvironment roundEnv ) {
    final Collection<Action> actions = new ArrayList<>();

    // Loop over all supported annotations
    for (TypeElement annotation : annotations) {
      // Find all annotated methods
      Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith( annotation );

      // Check for incorrect method signatures
      Map<Boolean, List<Element>> annotatedMethods = annotatedElements.stream()
          .collect( Collectors.partitioningBy( element -> this.checkMethodSignature( element ) ) );

      List<Element> actionMethods = annotatedMethods.get( true );

      if (actionMethods.isEmpty()) {
        continue;
      }

      actions.addAll( actionMethods.stream().map( new Element2Action() )
          .collect( Collectors.<Action>toList() ) );

      // String className = ((TypeElement) actionMethods.get( 0 ).getEnclosingElement())
      // .getQualifiedName().toString();
      //
      // Map<String, String> setterMap = actionMethods.stream().collect( Collectors.toMap(
      // setter -> setter.getSimpleName().toString(),
      // setter -> ((ExecutableType) setter.asType()).getParameterTypes().get( 0 ).toString() ) );
      //
      // try {
      // writeBuilderFile( className, setterMap );
      // } catch (IOException e) {
      // e.printStackTrace();
      // }

    }

    return true;
  }

  // --------------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------------

  /**
   * Check whether the given element matches the Openwhisk Action method signature.
   * 
   * Only match on "public static JsonObject main(com.google.gson.JsonObject args)"
   * 
   * @param element the element to check
   * @return True is signature matches, false otherwise.
   */
  private boolean checkMethodSignature( final Element element ) {
    // Assume the method is a proper action method until proven not.
    final ExecutableType type = (ExecutableType) element.asType();
    final String methodSignature = getMethodSignature( element );
    processingEnv.getMessager().printMessage( Diagnostic.Kind.NOTE, methodSignature );

    // Check modifiers
    if (!element.getModifiers().containsAll( EXPECTED_METHOD_MODIFIERS )) {
      processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR,
          "Signature error: @OpenwhiskAction annotation only applies to methods with modifiers: public static",
          element );
      return false;
    }


    // Check return type
    if (!JSON_OBJECT.equals( type.getReturnType().toString() )) {
      processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR,
          "Signature error: @OpenwhiskAction annotation only applies to methods returning a JsonObject",
          element );
      return false;
    }

    // Check signature argument
    if (type.getParameterTypes().size() != 1) {
      processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR,
          "Signature error: @OpenwhiskAction annotation only applies to methods with a single argument",
          element );
      return false;
    }
    if (!JSON_OBJECT.equals( type.getParameterTypes().get( 0 ).toString() )) {
      processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR,
          "Signature error: @OpenwhiskAction annotation only applies to methods with a single JsonObject argument",
          element );
      return false;
    }

    return true;
  }

  private String getMethodSignature( final Element element ) {
    final ExecutableType type = (ExecutableType) element.asType();

    final StringBuilder signatureBuilder = new StringBuilder();
    signatureBuilder.append( Joiner.on( SPACE ).join( element.getModifiers() ) ).append( SPACE );
    signatureBuilder.append( type.getReturnType() ).append( SPACE );
    signatureBuilder.append( element.getSimpleName() ).append( "(" );
    // TODO: args
    signatureBuilder.append( ")" );
    return signatureBuilder.toString();
  }

  private void writeBuilderFile( String className, Map<String, String> setterMap )
      throws IOException {

    String packageName = null;
    int lastDot = className.lastIndexOf( '.' );
    if (lastDot > 0) {
      packageName = className.substring( 0, lastDot );
    }

    String simpleClassName = className.substring( lastDot + 1 );
    String builderClassName = className + "Builder";
    String builderSimpleClassName = builderClassName.substring( lastDot + 1 );

    JavaFileObject builderFile = processingEnv.getFiler().createSourceFile( builderClassName );
    try (PrintWriter out = new PrintWriter( builderFile.openWriter() )) {

      if (packageName != null) {
        out.print( "package " );
        out.print( packageName );
        out.println( ";" );
        out.println();
      }

      out.print( "public class " );
      out.print( builderSimpleClassName );
      out.println( " {" );
      out.println();

      out.print( "    private " );
      out.print( simpleClassName );
      out.print( " object = new " );
      out.print( simpleClassName );
      out.println( "();" );
      out.println();

      out.print( "    public " );
      out.print( simpleClassName );
      out.println( " build() {" );
      out.println( "        return object;" );
      out.println( "    }" );
      out.println();

      setterMap.entrySet().forEach( setter -> {
        String methodName = setter.getKey();
        String argumentType = setter.getValue();

        out.print( "    public " );
        out.print( builderSimpleClassName );
        out.print( " " );
        out.print( methodName );

        out.print( "(" );

        out.print( argumentType );
        out.println( " value) {" );
        out.print( "        object." );
        out.print( methodName );
        out.println( "(value);" );
        out.println( "        return this;" );
        out.println( "    }" );
        out.println();
      } );

      out.println( "}" );

    }
  }

}
