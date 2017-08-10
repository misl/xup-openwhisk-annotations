package nl.xup.openwhisk.annotation.transform;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import nl.xup.openwhisk.annotation.OpenwhiskAction;
import nl.xup.openwhisk.annotation.OpenwhiskAnnotation;
import nl.xup.openwhisk.annotation.OpenwhiskParameter;
import nl.xup.openwhisk.annotation.model.Action;
import nl.xup.openwhisk.annotation.model.Annotation;
import nl.xup.openwhisk.annotation.model.Parameter;

/**
 * Transforms annotation processor elements in Action objects.
 * 
 * @author misl
 */
public class Element2Action implements Function<Element, Action> {

  // --------------------------------------------------------------------------
  // Implementing Function
  // --------------------------------------------------------------------------

  /**
   * Performs the actual transformation.
   */
  public Action apply( Element element ) {
    Action action = new Action();
    action.setName( constructActionName( element ) );
    action.setPackageName( constructPackageName( element ) );
    action.setEntrypoint( constructEntrypoint( element ) );
    action.getAnnotations().putAll( constructAnnotations( element ) );
    action.getParameters().putAll( constructParameters( element ) );

    return action;
  }

  // --------------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------------

  private String constructActionName( final Element element ) {
    final String actionName = element.getAnnotation( OpenwhiskAction.class ).name();

    if (actionName == null || actionName.isEmpty()) {
      // No action name specified use method name.
      return element.getSimpleName().toString();
    }

    return actionName;
  }

  private String constructPackageName( final Element element ) {
    final String packageName = element.getAnnotation( OpenwhiskAction.class ).packageName();

    if (packageName == null || packageName.isEmpty()) {
      // No package name specified use the name of the enclosing class.
      // TODO: look for OpenwhiskPackage annotation in class or package. Using classname
      //       is only the last resort.
      return ((TypeElement) element.getEnclosingElement()).getSimpleName().toString();
    }

    return packageName;
  }

  private String constructEntrypoint( final Element element ) {
    // Entrypoint is fully qualified method name (without arguments), but
    // when method name is 'main' only the fully qualified classname suffices.
    if ("main".equals( element.getSimpleName().toString() )) {
      return ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString();
    }
    return String.format( "%s#%s",
        ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString(),
        element.getSimpleName().toString() );
  }

  private Map<String, Annotation> constructAnnotations( final Element element ) {
    final Map<String, Annotation> modelAnnotations = new HashMap<>();
    for (OpenwhiskAnnotation annotation : element.getAnnotation( OpenwhiskAction.class )
        .annotations()) {
      modelAnnotations.put( annotation.key(),
          new Annotation( annotation.key(), annotation.value() ) );
    }

    return modelAnnotations;
  }

  private Map<String, Parameter> constructParameters( final Element element ) {
    final Map<String, Parameter> modelParameters = new HashMap<>();
    for (OpenwhiskParameter parameter : element.getAnnotation( OpenwhiskAction.class )
        .parameters()) {
      modelParameters.put( parameter.key(), new Parameter( parameter.key(), parameter.value() ) );
    }
    return modelParameters;
  }
}
