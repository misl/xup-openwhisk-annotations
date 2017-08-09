package nl.xup.openwhisk.annotation;

import java.lang.annotation.*;

/**
 * Annotation to specify an Openwhisk parameters. 
 * This annotation will ease registering various
 * Openwhisk components like action, packages, ...
 * 
 * $> wsk ... --param KEY VALUE
 * 
 * @author misl
 */
@Retention(RetentionPolicy.SOURCE)
public @interface OpenwhiskParameter {

  /**
   * The key for the Openwhisk parameter.
   * 
   * @return String with the Openwhisk parameter key.
   */
  String key();

  /**
   * The value for the Openwhisk parameter.
   * 
   * @return String with the Openwhisk parameter value.
   */
  String value();
}
