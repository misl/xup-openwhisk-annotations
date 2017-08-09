package nl.xup.openwhisk.test.actions;

import com.google.gson.JsonObject;

import nl.xup.openwhisk.annotation.OpenwhiskAction;

public class InvalidArgumentTypeSignatureAction {

  // --------------------------------------------------------------------------
  // Entrypoint
  // --------------------------------------------------------------------------

  @OpenwhiskAction
  public static JsonObject main( String arg ) {
    JsonObject response = new JsonObject();
    return response;
  }
}
