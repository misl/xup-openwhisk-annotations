package nl.xup.openwhisk.annotation.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

/**
 * Test cases for Package
 * 
 * @author misl
 */
public class PackageTest {

  @Test
  public void testConstructor() {
    // Prepare

    // Execute
    final Package pkg = new Package();

    // Verify
    assertThat( pkg, is( notNullValue() ) );
    assertThat( pkg.getName(), is( nullValue() ) );
    assertThat( pkg.getActions(), is( notNullValue() ) );
    assertThat( pkg.getActions().size(), is( 0 ) );
    assertThat( pkg.getAnnotations(), is( notNullValue() ) );
    assertThat( pkg.getAnnotations().size(), is( 0 ) );
    assertThat( pkg.getParameters(), is( notNullValue() ) );
    assertThat( pkg.getParameters().size(), is( 0 ) );
  }

  @Test
  public void testSetters() {
    // Prepare
    final Package pkg = new Package();

    // Execute
    pkg.setName( "a" );
    pkg.getActions().put( "b", new Action() );
    pkg.getAnnotations().put( "c", new Annotation( "c", "cv" ) );
    pkg.getParameters().put( "d", new Parameter( "d", "dv" ) );

    // Verify
    assertThat( pkg, is( notNullValue() ) );
    assertThat( pkg.getName(), is( equalTo( "a" ) ) );
    assertThat( pkg.getActions().size(), is( 1 ) );
    assertThat( pkg.getAnnotations().size(), is( 1 ) );
    assertThat( pkg.getParameters().size(), is( 1 ) );
  }
}
