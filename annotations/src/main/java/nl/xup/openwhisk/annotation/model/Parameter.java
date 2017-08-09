package nl.xup.openwhisk.annotation.model;

/**
 * Model object to contain parameter information used for annotation processing.
 * 
 * @author misl
 */
public class Parameter implements Pair {

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  private String key;
  private String value;

  // --------------------------------------------------------------------------
  // Constructor
  // --------------------------------------------------------------------------

  public Parameter(final String key, final String value) {
    this.key = key;
    this.value = value;
  }

  // --------------------------------------------------------------------------
  // Implements Pair
  // --------------------------------------------------------------------------

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getValue() {
    return value;
  }
}
