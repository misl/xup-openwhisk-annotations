package nl.xup.openwhisk.annotation;

import java.lang.annotation.*;

/**
 * Annotation to specify an Openwhisk annotationw. 
 * This annotation will ease registering various
 * Openwhisk components like action, packages, ...
 * 
 * $> wsk ... --annotation KEY VALUE
 * 
 * @author misl
 */
@Retention(RetentionPolicy.SOURCE)
public @interface OpenwhiskAnnotation {

  /**
   * The key for the Openwhisk annotation.
   * 
   * @return String with the Openwhisk annotation key.
   */
  String key();

  /**
   * The value for the Openwhisk annotation.
   * 
   * @return String with the Openwhisk annotation value.
   */
  String value();
}
