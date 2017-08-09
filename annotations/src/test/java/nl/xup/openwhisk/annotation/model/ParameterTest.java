package nl.xup.openwhisk.annotation.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

/**
 * Test cases for Parameter
 * 
 * @author misl
 */
public class ParameterTest {

  @Test
  public void testConstructor() {
    // Prepare

    // Execute
    final Parameter parameter = new Parameter( null, null );

    // Verify
    assertThat( parameter, is( notNullValue() ) );
    assertThat( parameter.getKey(), is( nullValue() ) );
    assertThat( parameter.getValue(), is( nullValue() ) );
  }

  @Test
  public void testConstructorWothValues() {
    // Prepare

    // Execute
    final Parameter parameter = new Parameter( "key", "value" );

    // Verify
    assertThat( parameter, is( notNullValue() ) );
    assertThat( parameter.getKey(), is( equalTo( "key" ) ) );
    assertThat( parameter.getValue(), is( equalTo( "value" ) ) );
  }
}
