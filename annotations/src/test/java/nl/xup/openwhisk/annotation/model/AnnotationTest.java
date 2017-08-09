package nl.xup.openwhisk.annotation.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

/**
 * Test cases for Annotation
 * 
 * @author misl
 */
public class AnnotationTest {

  @Test
  public void testConstructor() {
    // Prepare

    // Execute
    final Annotation annotation = new Annotation( null, null );

    // Verify
    assertThat( annotation, is( notNullValue() ) );
    assertThat( annotation.getKey(), is( nullValue() ) );
    assertThat( annotation.getValue(), is( nullValue() ) );
  }

  @Test
  public void testConstructorWothValues() {
    // Prepare

    // Execute
    final Annotation annotation = new Annotation( "key", "value" );

    // Verify
    assertThat( annotation, is( notNullValue() ) );
    assertThat( annotation.getKey(), is( equalTo( "key" ) ) );
    assertThat( annotation.getValue(), is( equalTo( "value" ) ) );
  }
}
