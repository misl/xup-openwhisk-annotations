package nl.xup.openwhisk.test.actions;

import com.google.gson.JsonObject;

import nl.xup.openwhisk.annotation.OpenwhiskAction;

public class InvalidArgumentCountSignatureAction {

  // --------------------------------------------------------------------------
  // Entrypoint
  // --------------------------------------------------------------------------

  @OpenwhiskAction
  public static JsonObject main( JsonObject args, JsonObject args2 ) {
    JsonObject response = new JsonObject();
    return response;
  }
}
