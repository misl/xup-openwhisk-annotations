package nl.xup.openwhisk.annotation;

import java.lang.annotation.*;

/**
 * Annotation to identify Openwhisk actions.
 * This annotation will ease registering actions with Openwhisk.
 * 
 * $> wsk action create <packageName>/<name> action.jar --main <annotatedMethod>
 *            (--annotation key value)* 
 *            (--param key value)*
 * 
 * @author misl
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface OpenwhiskAction {

  /**
   * The name to be used for the Openwhisk action. Defaults to the method name.
   * @return String with the action name.
   */
  String name() default "";
  
  /**
   * The name of the Openwhisk package the action is part of. Defaults to 
   * the package name of the annotated action.
   * @return String with the package name
   */
  String packageName() default "";
  
  /**
   * The Openwhisk annotations applicable for the action.
   * 
   * @return Array of OpenwhiskAnnotation 
   */
  OpenwhiskAnnotation[] annotations() default {};
  
  /**
   * The Openwhisk parameters applicable for the action.
   * 
   * @return Array of OpenwhiskParameter
   */
  OpenwhiskParameter[] parameters() default {};
}
