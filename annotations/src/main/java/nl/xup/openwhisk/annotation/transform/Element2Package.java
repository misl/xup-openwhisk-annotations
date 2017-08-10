package nl.xup.openwhisk.annotation.transform;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import nl.xup.openwhisk.annotation.OpenwhiskAnnotation;
import nl.xup.openwhisk.annotation.OpenwhiskPackage;
import nl.xup.openwhisk.annotation.OpenwhiskParameter;
import nl.xup.openwhisk.annotation.model.Annotation;
import nl.xup.openwhisk.annotation.model.Package;
import nl.xup.openwhisk.annotation.model.Parameter;

/**
 * Transforms annotation processor elements into Package objects.
 * 
 * @author misl
 */
public class Element2Package implements Function<Element, Package> {

  // --------------------------------------------------------------------------
  // Implementing Function
  // --------------------------------------------------------------------------

  /**
   * Performs the actual transformation.
   */
  public Package apply( Element element ) {
    Package pkg = new Package();
    pkg.setName( constructPackageName( element ) );
    pkg.getAnnotations().putAll( constructAnnotations( element ) );
    pkg.getParameters().putAll( constructParameters( element ) );

    return pkg;
  }

  // --------------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------------

  private String constructPackageName( final Element element ) {
    final String packageName = element.getAnnotation( OpenwhiskPackage.class ).name();
    return packageName;
  }

  private Map<String, Annotation> constructAnnotations( final Element element ) {
    final Map<String, Annotation> modelAnnotations = new HashMap<>();
    for (OpenwhiskAnnotation annotation : element.getAnnotation( OpenwhiskPackage.class ).annotations()) {
      modelAnnotations.put( annotation.key(), new Annotation( annotation.key(), annotation.value() ) );
    }

    return modelAnnotations;
  }

  private Map<String, Parameter> constructParameters( final Element element ) {
    final Map<String, Parameter> modelParameters = new HashMap<>();
    for (OpenwhiskParameter parameter : element.getAnnotation( OpenwhiskPackage.class ).parameters()) {
      modelParameters.put( parameter.key(), new Parameter( parameter.key(), parameter.value() ) );
    }
    return modelParameters;
  }
}
