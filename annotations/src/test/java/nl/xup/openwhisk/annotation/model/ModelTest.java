package nl.xup.openwhisk.annotation.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

/**
 * Test cases for Model
 * 
 * @author misl
 */
public class ModelTest {

  @Test
  public void testConstructor() {
    // Prepare

    // Execute
    final Model model = new Model();

    // Verify
    assertThat( model, is( notNullValue() ) );
    assertThat( model.getPackages(), is( notNullValue() ) );
    assertThat( model.getPackages().size(), is( 0 ) );
  }
}
