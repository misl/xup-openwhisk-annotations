package nl.xup.openwhisk.annotation.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Model object to as an entrypoint to the full Openwhisk model resulting from annotation
 * processing.
 * 
 * @author misl
 */
public class Model {

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  private Map<String, Package> packages = new HashMap<>();

  // --------------------------------------------------------------------------
  // Getters / Setters
  // --------------------------------------------------------------------------

  public Map<String, Package> getPackages() {
    return packages;
  }
}
