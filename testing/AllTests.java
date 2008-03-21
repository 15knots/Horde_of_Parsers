import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 * @author weber
 */
public class AllTests {

  public static void main(String[] args) {
    junit.swingui.TestRunner.run(AllTests.class);
  }

  public static Test suite() {
    TestSuite suite= new TestSuite("Test for default package");
    //$JUnit-BEGIN$

    //$JUnit-END$
    return suite;
  }
}
