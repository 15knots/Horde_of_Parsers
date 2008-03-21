// $Id: AllTestsStdC.java,v 1.1 2002/11/10 13:49:17 weber Exp $
/**
 * Project:	Horde of Parsers
 * 
 * Copyright 2002 by Martin Weber
 */
package parser.grammar;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Testsuite für die des der Standard-C-Grammatik.
 * 
 * @author Martin Weber
 */
public class AllTestsStdC {

  public static void main(String[] args) {
    junit.swingui.TestRunner.run(AllTestsStdC.class);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for parser.grammar (Standard C)");
    //$JUnit-BEGIN$
    suite.addTest(new TestSuite(StdCGrammarTest.class));
    //$JUnit-END$
    return suite;
  }
}
