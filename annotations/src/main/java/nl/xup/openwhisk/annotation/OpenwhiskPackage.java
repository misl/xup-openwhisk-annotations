package nl.xup.openwhisk.annotation;

import java.lang.annotation.*;

/**
 * Annotation to identify Openwhisk packages.
 * This annotation will ease registering packages with Openwhisk.
 * 
 * $> wsk package create <packageName>
 *            (--annotation key value)* 
 *            (--param key value)*
 * 
 * @author misl
 */
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.SOURCE)
public @interface OpenwhiskPackage {

  /**
   * The name of the Openwhisk package. 
   * 
   * @return String with the package name
   */
  String name() default "";
  
  /**
   * The Openwhisk annotations applicable for the package.
   * 
   * @return Array of OpenwhiskAnnotation 
   */
  OpenwhiskAnnotation[] annotations() default {};
  
  /**
   * The Openwhisk parameters applicable for the package.
   * 
   * @return Array of OpenwhiskParameter
   */
  OpenwhiskParameter[] parameters() default {};
}
