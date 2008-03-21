// $Id: AllTests.java,v 1.1 2002/11/10 13:49:17 weber Exp $
/**
 * Project:	Horde of Parsers
 * 
 * Copyright 2002 by Martin Weber
 */

package parser;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Parser tests.
 * @author Martin Weber
 */
public class AllTests {

  public static void main(String[] args) {}

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for package parser");
    //$JUnit-BEGIN$
    suite.addTest(new TestSuite(ParserFactoryTest.class));
    // Standard C grammatik Testes...
    suite.addTest( parser.grammar.AllTestsStdC.suite());
    
    //$JUnit-END$
    return suite;
  }
}
