package nl.xup.openwhisk.examples.actions;

import com.google.gson.JsonObject;

import nl.xup.openwhisk.annotation.OpenwhiskAction;

public class HelloWorldActions {

  // --------------------------------------------------------------------------
  // Entrypoint
  // --------------------------------------------------------------------------

  @OpenwhiskAction(name="hello", packageName="xup-openwhisk-examples")
  public static JsonObject main(JsonObject args) {
    final JsonObject response = new JsonObject();
    response.addProperty("payload", "Hello world!");
    return response;
  }

  @OpenwhiskAction(name="helloName", packageName="xup-openwhisk-examples")
  public static JsonObject myfunc(JsonObject args) {
    final String name = args.getAsJsonPrimitive("name").getAsString();
    final JsonObject response = new JsonObject();
    response.addProperty("payload", "Hello " + name + "!");
    return response;
  }
}