package nl.xup.openwhisk.annotation.model;

/**
 * Pair interface used for both Parameter and Annotation.
 * 
 * @author misl
 */
interface Pair {

  // --------------------------------------------------------------------------
  // Getters
  // --------------------------------------------------------------------------

  /**
   * The pair key element.
   * 
   * @return String with the pair key.
   */
  public String getKey();

  /**
   * The pair value element.
   * 
   * @return String with the pair value.
   */
  public String getValue();
}

