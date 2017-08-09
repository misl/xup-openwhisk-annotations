package nl.xup.openwhisk.test.actions;

import com.google.gson.JsonObject;

import nl.xup.openwhisk.annotation.OpenwhiskAction;

public class InvalidReturnTypeSignatureAction {

  // --------------------------------------------------------------------------
  // Entrypoint
  // --------------------------------------------------------------------------

  @OpenwhiskAction
  public static String actionName( JsonObject args ) {
    return "";
  }
}
