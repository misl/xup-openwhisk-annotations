package nl.xup.openwhisk.annotation.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

/**
 * Test cases for Action
 * 
 * @author misl
 */
public class ActionTest {

  @Test
  public void testConstructor() {
    // Prepare

    // Execute
    final Action action = new Action();

    // Verify
    assertThat( action, is( notNullValue() ) );
    assertThat( action.getName(), is( nullValue() ) );
    assertThat( action.getPackageName(), is( nullValue() ) );
    assertThat( action.getEntrypoint(), is( nullValue() ) );
    assertThat( action.getAnnotations(), is( notNullValue() ) );
    assertThat( action.getAnnotations().size(), is( 0 ) );
    assertThat( action.getParameters(), is( notNullValue() ) );
    assertThat( action.getParameters().size(), is( 0 ) );
  }

  @Test
  public void testSetters() {
    // Prepare
    final Action action = new Action();

    // Execute
    action.setName( "a" );
    action.setPackageName( "b" );
    action.setEntrypoint( "c" );
    action.getAnnotations().put( "d", new Annotation( "d", "dv" ) );
    action.getParameters().put( "e", new Parameter( "e", "ev" ) );

    // Verify
    assertThat( action, is( notNullValue() ) );
    assertThat( action.getName(), is( equalTo( "a" ) ) );
    assertThat( action.getPackageName(), is( equalTo( "b" ) ) );
    assertThat( action.getEntrypoint(), is( equalTo( "c" ) ) );
    assertThat( action.getAnnotations().size(), is( 1 ) );
    assertThat( action.getParameters().size(), is( 1 ) );
  }
}
