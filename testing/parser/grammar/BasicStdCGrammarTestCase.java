// $Id: BasicStdCGrammarTestCase.java,v 1.1 2002/11/10 13:49:17 weber Exp $
/**
 * Project:	Horde of Parsers
 * 
 * Copyright 2002 by Martin Weber
 */
package parser.grammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

import parser.Dialect;
import parser.ParserFactory;

import antlr.Parser;

/**
 * Testing Frame(work) f�r Tests der Grammatik f�r Standard C. 
 * Ben�tigt werden die Testdateien im 
 * Verzeichnis testing/files.
 * 
 * @author Martin Weber
 */
public class BasicStdCGrammarTestCase extends TestCase {

  /**
   * Constructor for StdCGrammarTest.
   * @param arg0
   */
  public BasicStdCGrammarTestCase(String arg0) {
    super(arg0);
  }

  /** 
   * Erzeugt einen Parser f�r Standard-C, der das angegebene File liest.
   * 
   * @param relFileName relativer Pfad zur C-Quellcodedatei f�r den Test.
   */
  protected StdCParser makeParser( String relFileName)
  throws FileNotFoundException, IOException
  {
    File inFile= new File( "testing/files", relFileName);
    dialect = ParserFactory.getDialect("standard");
    Parser parser = ParserFactory.createParser(dialect, inFile);
    return (StdCParser) parser;
  }
  
  /** Dialect f�r die ParserFactory. 
   */
  protected Dialect dialect;
  
}
