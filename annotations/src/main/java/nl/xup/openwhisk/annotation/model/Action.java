package nl.xup.openwhisk.annotation.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Model object to contain all Action attributes needed for annotation 
 * processing.
 * 
 * @author misl
 */
public class Action {
  
  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  private String actionName;
  private String packageName;
  private String entrypoint;
  private Map<String, Annotation> annotations = new HashMap<>();
  private Map<String, Parameter> parameters = new HashMap<>();

  // --------------------------------------------------------------------------
  // Getters / Setters
  // --------------------------------------------------------------------------

  public String getActionName() {
    return actionName;
  }

  public void setActionName( String actionName ) {
    this.actionName = actionName;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName( String packageName ) {
    this.packageName = packageName;
  }

  public String getEntrypoint() {
    return entrypoint;
  }
  
  public void setEntrypoint( String entrypoint ) {
    this.entrypoint = entrypoint;
  } 
  
  public Map<String, Annotation> getAnnotations() {
    return annotations;
  }

  public Map<String, Parameter> getParameters() {
    return parameters;
  }
}
