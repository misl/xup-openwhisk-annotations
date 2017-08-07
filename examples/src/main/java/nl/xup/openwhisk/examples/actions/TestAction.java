package nl.xup.openwhisk.examples.actions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import nl.xup.openwhisk.annotation.OpenwhiskAction;

public class TestAction {

  // --------------------------------------------------------------------------
  // Entrypoint
  // --------------------------------------------------------------------------

  @OpenwhiskAction(name="testAction", packageName="test")
  public static JsonObject main(JsonObject args) {
    JsonObject response = new JsonObject();
    response.add("args", args);
    Gson gson = new GsonBuilder().create();
    response.add("env", gson.toJsonTree( System.getenv() ));
    return response;
  }

  @OpenwhiskAction(name="differenName", packageName="test")
  public static JsonObject myfunc(JsonObject args) {
    JsonObject response = new JsonObject();
    response.add("args", args);
    Gson gson = new GsonBuilder().create();
    response.add("env", gson.toJsonTree( System.getenv() ));
    return response;
  }

  @OpenwhiskAction(name="privateAction", packageName="test")
  private static JsonObject func3(JsonObject args) {
    JsonObject response = new JsonObject();
    response.add("args", args);
    Gson gson = new GsonBuilder().create();
    response.add("env", gson.toJsonTree( System.getenv() ));
    return response;
  }
}