package nl.xup.openwhisk.examples.actions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import nl.xup.openwhisk.annotation.OpenwhiskAction;

public class EnvironmentAction {

  // --------------------------------------------------------------------------
  // Entrypoint
  // --------------------------------------------------------------------------

  @OpenwhiskAction(packageName="xup-openwhisk-examples")
  public static JsonObject getEnv(JsonObject args) {
    final Gson gson = new GsonBuilder().create();
    final JsonObject response = new JsonObject();
    response.add("args", args);
    response.add("env", gson.toJsonTree( System.getenv() ));
    return response;
  }
}