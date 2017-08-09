package nl.xup.openwhisk.annotation.processor;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

import java.util.Arrays;

import javax.tools.JavaFileObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.testing.compile.JavaFileObjects;

/**
 * Test casess for {@link OpenwhiskProcessor}.
 */
@RunWith(JUnit4.class)
public class OpenwhiskProcessorTest {

  // --------------------------------------------------------------------------
  // Test cases
  // --------------------------------------------------------------------------

  @Test
  public void testActionDefValidSignature() {
    assert_().about( javaSources() )
        .that( Arrays.asList( JavaFileObjects.forResource( "test/ValidSignatureAction.java" ) ) )
        .processedWith( new OpenwhiskProcessor() ).compilesWithoutError();
    // .and().generatesFiles(
    // JavaFileObjects.forResource("META-INF/services/test.SomeService"),
    // JavaFileObjects.forResource("META-INF/services/test.AnotherService"));
  }

  @Test
  public void testActionDefInvalidModifiers() {
    final JavaFileObject fileObject =
        JavaFileObjects.forResource( "test/InvalidModifiersSignatureAction.java" );
    assert_().about( javaSources() ).that( Arrays.asList( fileObject ) )
        .processedWith( new OpenwhiskProcessor() ).failsToCompile()
        .withErrorContaining( "methods with modifiers: public static" ).in( fileObject )
        .onLine( 14 ).atColumn( 21 );
  }

  @Test
  public void testActionDefInvalidReturnType() {
    final JavaFileObject fileObject =
        JavaFileObjects.forResource( "test/InvalidReturnTypeSignatureAction.java" );
    assert_().about( javaSources() ).that( Arrays.asList( fileObject ) )
        .processedWith( new OpenwhiskProcessor() ).failsToCompile()
        .withErrorContaining( "methods returning a JsonObject" ).in( fileObject )
        .onLine( 14 ).atColumn( 24 );
  }

  @Test
  public void testActionDefInvalidArgumentCount() {
    final JavaFileObject fileObject =
        JavaFileObjects.forResource( "test/InvalidArgumentCountSignatureAction.java" );
    assert_().about( javaSources() ).that( Arrays.asList( fileObject ) )
        .processedWith( new OpenwhiskProcessor() ).failsToCompile()
        .withErrorContaining( "methods with a single argument" ).in( fileObject )
        .onLine( 14 ).atColumn( 28 );
  }

  @Test
  public void testActionDefInvalidArgumentType() {
    final JavaFileObject fileObject =
        JavaFileObjects.forResource( "test/InvalidArgumentTypeSignatureAction.java" );
    assert_().about( javaSources() ).that( Arrays.asList( fileObject ) )
        .processedWith( new OpenwhiskProcessor() ).failsToCompile()
        .withErrorContaining( "methods with a single JsonObject argument" ).in( fileObject )
        .onLine( 14 ).atColumn( 28 );
  }
}
