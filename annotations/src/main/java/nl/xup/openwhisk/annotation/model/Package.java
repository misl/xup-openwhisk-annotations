package nl.xup.openwhisk.annotation.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Model object to contain Openwhisk Package attributes needed for annotation processing.
 * 
 * @author misl
 */
public class Package {

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  private String name;
  private Map<String, Action> actions = new HashMap<>();
  private Map<String, Annotation> annotations = new HashMap<>();
  private Map<String, Parameter> parameters = new HashMap<>();

  // --------------------------------------------------------------------------
  // Getters / Setters
  // --------------------------------------------------------------------------

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public Map<String, Action> getActions() {
    return actions;
  }

  public Map<String, Annotation> getAnnotations() {
    return annotations;
  }

  public Map<String, Parameter> getParameters() {
    return parameters;
  }
}
