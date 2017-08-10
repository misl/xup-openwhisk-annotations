package nl.xup.openwhisk.annotation.processor;

import java.io.Writer;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
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
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.google.auto.service.AutoService;

import nl.xup.openwhisk.annotation.OpenwhiskPackage;
import nl.xup.openwhisk.annotation.model.Action;
import nl.xup.openwhisk.annotation.model.Model;
import nl.xup.openwhisk.annotation.model.Package;
import nl.xup.openwhisk.annotation.transform.Element2Action;
import nl.xup.openwhisk.annotation.transform.Element2Package;

@SupportedAnnotationTypes({"nl.xup.openwhisk.annotation.OpenwhiskPackage",
    "nl.xup.openwhisk.annotation.OpenwhiskAction"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class OpenwhiskProcessor extends AbstractProcessor {

  // --------------------------------------------------------------------------
  // Constants
  // --------------------------------------------------------------------------

  private static final String JSON_OBJECT = "com.google.gson.JsonObject";
  private static final Set<Modifier> EXPECTED_METHOD_MODIFIERS =
      new HashSet<>( Arrays.asList( Modifier.PUBLIC, Modifier.STATIC ) );

  // --------------------------------------------------------------------------
  // Override AbstractProcessor
  // --------------------------------------------------------------------------

  @Override
  public boolean process( Set<? extends TypeElement> annotations, RoundEnvironment roundEnv ) {
    final Model model = new Model();

    // Loop over all supported annotations
    for (TypeElement annotation : annotations) {
      switch (annotation.getSimpleName().toString()) {
        case "OpenwhiskAction":
          processActionAnnotation( model, annotation, roundEnv );
          break;
        case "OpenwhiskPackage":
          processPackageAnnotation( model, annotation, roundEnv );
          break;

        default:
          break;
      }
    }

    if (!model.getPackages().isEmpty()) {
      try {
        final VelocityEngine engine = initTemplateEngine();
  
        generateFile( engine, "openwhisk.model.vm", model );
        generateFile( engine, "rewhisk.sh.vm", model );
      } catch ( final Exception ex ) {
        processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR,
            "Generating resouces failed!" );
      }
    }
       
    return true;
  }

  // --------------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------------

  private void processActionAnnotation( final Model model, final TypeElement annotation,
      final RoundEnvironment roundEnv ) {
    // Find all annotated methods
    Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith( annotation );

    // Process and check for duplicates.
    annotatedElements.stream()
        .map( e -> {
              // Only accepts verified elements
              if ( !verifyMethodSignature( e ) ) {
                return null;
              }

              final Action action = new Element2Action().apply( e );
              Package pkg = model.getPackages().get( action.getPackageName() );
              if ( pkg == null ) {
                // No package declared yet. Log it..
                processingEnv.getMessager().printMessage( Diagnostic.Kind.WARNING,
                    "Missing @OpenwhiskPackage declaration for package '" + action.getPackageName() + "'!",
                    e );
                // .. and create one.
                pkg = new Package();
                pkg.setName( action.getPackageName() );
                model.getPackages().put( pkg.getName(), pkg );
              }
              
              // Check for duplicates
              if (pkg.getActions().containsKey( action.getName() )) {
                processingEnv.getMessager().printMessage( Diagnostic.Kind.WARNING,
                    String.format( "Duplicate @OpenwhiskAction declaration for action '%s/%s'!", 
                    pkg.getName(), action.getName()), e );
                return null;
              }

              // Add action to package
              pkg.getActions().put( action.getName(), action );
              return action;
            } )
        .collect( Collectors.<Action>toList() );
  }

  private void processPackageAnnotation( final Model model, final TypeElement annotation,
      final RoundEnvironment roundEnv ) {
    // Find all annotated methods
    Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith( annotation );

    // Process and check for duplicates.
    annotatedElements.stream()
        .filter( e -> {
              // Predicate is abused to check for duplicates
              final String packageName = e.getAnnotation( OpenwhiskPackage.class ).name();
              if (model.getPackages().containsKey( packageName )) {
                processingEnv.getMessager().printMessage( Diagnostic.Kind.WARNING,
                    "Duplicate @OpenwhiskPackage declaration for package '" + packageName + "'!",
                    e );
                return false;
              }
              
              return true;
            } )
        .map( e -> { 
              final Package pkg = new Element2Package().apply( e );
              model.getPackages().put( pkg.getName(), pkg );
              return pkg;
            } )
        .collect( Collectors.<Package>toList() );
  }

  /**
   * Check whether the given element matches the Openwhisk Action method signature.
   * 
   * Only match on "public static JsonObject main(com.google.gson.JsonObject args)"
   * 
   * @param element the element to check
   * @return True is signature matches, false otherwise.
   */
  private boolean verifyMethodSignature( final Element element ) {
    // Assume the method is a proper action method until proven not.
    final ExecutableType type = (ExecutableType) element.asType();

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
  
  private VelocityEngine initTemplateEngine() throws Exception {
    final Properties props = new Properties();
    final URL url = this.getClass().getClassLoader().getResource("velocity.properties");
    props.load(url.openStream());

    final VelocityEngine ve = new VelocityEngine(props);
    ve.init();
    
    return ve;
  }
  
  private void generateFile( final VelocityEngine engine, final String templateFile, final Model model ) throws Exception {
    final VelocityContext vc = new VelocityContext();
    vc.put("packages", model.getPackages());

    Template vt = engine.getTemplate(templateFile);

    FileObject fileObject = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT,
        "", templateFile.replaceAll( "\\.vm", "" ));

    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "creating resource file: " + fileObject.toUri());

    Writer writer = fileObject.openWriter();

    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "applying velocity template: " + vt.getName());

    vt.merge(vc, writer);

    writer.close();
  }
}
