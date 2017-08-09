package nl.xup.openwhisk.annotation.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
    assertThat( action.getActionName(), is( nullValue() ) );
    assertThat( action.getPackageName(), is( nullValue() ) );
    assertThat( action.getEntrypoint(), is( nullValue() ) );
  }

  @Test
  public void testSetters() {
    // Prepare
    final Action action = new Action();
    
    // Execute
    action.setActionName( "a" );
    action.setPackageName( "b" );
    action.setEntrypoint( "c" );
    
    // Verify
    assertThat( action, is( notNullValue() ) );
    assertThat( action.getActionName(), is( equalTo( "a" ) ) );
    assertThat( action.getPackageName(), is( equalTo( "b" ) ) );
    assertThat( action.getEntrypoint(), is( equalTo( "c" ) ) );
  }
}
