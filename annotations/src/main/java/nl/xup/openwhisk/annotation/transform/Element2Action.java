package nl.xup.openwhisk.annotation.transform;

import java.util.function.Function;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import nl.xup.openwhisk.annotation.OpenwhiskAction;
import nl.xup.openwhisk.annotation.model.Action;

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
    action.setActionName( constructActionName( element ) );
    action.setPackageName( constructPackageName( element ) );
    action.setEntrypoint( constructEntrypoint( element ) );

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
      return ((TypeElement) element.getEnclosingElement()).getSimpleName().toString();
    }

    return packageName;
  }

  private String constructEntrypoint( final Element element ) {
    // Entrypoint is fully qualified method name (without arguments).
    return String.format( "%s#%s",
        ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString(),
        element.getSimpleName().toString() );
  }
}
