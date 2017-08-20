package nl.xup.openwhisk.examples.actions;

import com.google.gson.JsonObject;

import nl.xup.openwhisk.annotation.OpenwhiskAction;
import nl.xup.openwhisk.annotation.OpenwhiskAnnotation;
import nl.xup.openwhisk.annotation.OpenwhiskPackage;
import nl.xup.openwhisk.annotation.OpenwhiskParameter;

@OpenwhiskPackage(name = "xup-openwhisk-examples",
    annotations = {
        @OpenwhiskAnnotation(key = "doc", value = "Example of a package level annotation tag."),
        @OpenwhiskAnnotation(key = "tag", value = "Another tag")})
public class HelloWorldActions {

  // --------------------------------------------------------------------------
  // Entrypoint
  // --------------------------------------------------------------------------

  @OpenwhiskAction(name="hello", packageName="xup-openwhisk-examples", annotations=@OpenwhiskAnnotation( key="tag", value="Action tag."))
  public static JsonObject main(JsonObject args) {
    final JsonObject response = new JsonObject();
    response.addProperty("payload", "Hello world!");
    return response;
  }

  @OpenwhiskAction(name = "helloName", packageName = "xup-openwhisk-examples")
  public static JsonObject myfunc( JsonObject args ) {
    final String name = args.getAsJsonPrimitive( "name" ).getAsString();
    final JsonObject response = new JsonObject();
    response.addProperty( "payload", "Hello " + name + "!" );
    return response;
  }
}
