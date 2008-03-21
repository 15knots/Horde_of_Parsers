// $Id: StdCGrammarTest.java,v 1.1 2002/11/10 13:49:17 weber Exp $
/**
 * Project:	Horde of Parsers
 * 
 * Copyright 2002 by Martin Weber
 */
package parser.grammar;

import java.io.FileNotFoundException;
import java.io.IOException;

import antlr.collections.AST;

/**
 * Testet die Grammtik für Standard C. Benötigt werden die Testdateien im 
 * Verzeichnis testing/files.
 * 
 * @author Martin Weber
 */
public class StdCGrammarTest extends BasicStdCGrammarTestCase {

  /**
   * Constructor for StdCGrammarTest.
   * @param arg0
   */
  public StdCGrammarTest(String arg0) {
    super(arg0);
  }

  public static void main(String[] args) {
    junit.swingui.TestRunner.run(StdCGrammarTest.class);
  }

  public void testSimpleDeclarations()
    throws
      FileNotFoundException,
      IOException,
      antlr.RecognitionException,
      antlr.TokenStreamException 
  {
    StdCParser parser = makeParser("simpleDeclarations.c");

    ((StdCParser) parser).translationUnit();
    assertEquals("compilation error count:", 0, parser.getErrorCount());
    AST tree = parser.getAST();
    assertNotNull("AST", tree);
  }

}
