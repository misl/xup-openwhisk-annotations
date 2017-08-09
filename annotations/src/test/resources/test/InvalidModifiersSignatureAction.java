package nl.xup.openwhisk.test.actions;

import com.google.gson.JsonObject;

import nl.xup.openwhisk.annotation.OpenwhiskAction;

public class InvalidModifiersSignatureAction {

  // --------------------------------------------------------------------------
  // Entrypoint
  // --------------------------------------------------------------------------

  @OpenwhiskAction
  public JsonObject main( JsonObject args ) {
    JsonObject response = new JsonObject();
    return response;
  }
}
