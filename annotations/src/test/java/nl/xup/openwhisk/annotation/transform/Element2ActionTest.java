package nl.xup.openwhisk.annotation.transform;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

/**
 * Test cases for Element2Action 
 * 
 * @author misl
 */
public class Element2ActionTest {

  @Test
  public void testConstructor() {
    // Prepare
    
    // Execute
    final Element2Action mapper = new Element2Action();
    
    // Verify
    assertThat( mapper, is( notNullValue() ) );
  }
  
  @Test
  public void testTransformation() {
    // Prepare
    
    // Execute
    final Element2Action mapper = new Element2Action();
    
    // Verify
    assertThat( mapper, is( notNullValue() ) );
  }
}
