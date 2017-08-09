package nl.xup.openwhisk.test.actions;

import com.google.gson.JsonObject;

import nl.xup.openwhisk.annotation.OpenwhiskAction;

public class ValidSignatureAction {

  // --------------------------------------------------------------------------
  // Entrypoint
  // --------------------------------------------------------------------------

  @OpenwhiskAction
  public static JsonObject main( JsonObject args ) {
    JsonObject response = new JsonObject();
    return response;
  }
}
