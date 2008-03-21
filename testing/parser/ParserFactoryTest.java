// $Id: ParserFactoryTest.java,v 1.1 2002/11/10 13:49:17 weber Exp $
/**
 * Project:	Horde of Parsers
 * 
 * Copyright 2002 by Martin Weber
 */

package parser;

import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * 
 * @author Martin Weber
 */
public class ParserFactoryTest extends TestCase {

  /**
   * Constructor for ParserFactoryTest.
   * @param arg0
   */
  public ParserFactoryTest(String arg0) {
    super(arg0);
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(ParserFactoryTest.class);
  }

  /** Prüft, ob für jeden Dialect ein Parser erzeugt werden kann.
   */
  public void testCreateParser() throws FileNotFoundException, IOException {
    Dialect[] dialects = ParserFactory.getDialects();
    for (int i = 0; i < dialects.length; i++) {
      Dialect dialect = dialects[i];
      assertNotNull(
        "null parser for Dialect",
        ParserFactory.createParser(dialect, null));
    }
  }

  /** @todo
   */
  public void testCreatePreparer() {
    Dialect[] dialects = ParserFactory.getDialects();
    for (int i = 0; i < dialects.length; i++) {
      Dialect dialect = dialects[i];
      assertNotNull(
        "null parser for Dialect",
        ParserFactory.createPreparer(dialect));
    }
  }

  /**
   * Prüft, ob alle installierten Dialekte vorhanden sind.
   **/
  public void testGetDialects() {
    Dialect[] dialects = ParserFactory.getDialects();
    for (int i = 0; i < dialects.length; i++) {
      Dialect dialect = dialects[i];
      assertNotNull("null Dialect", dialect);
    }
  }

  /**
   * Prüft, ob alle installierten Dialekte einen Namen haben. 
   **/
  public void testDialectNames() {
    Dialect[] dialects = ParserFactory.getDialects();
    for (int i = 0; i < dialects.length; i++) {
      Dialect dialect = dialects[i];
      assertNotNull("dialect name[" + i + "]", dialect.name);
    }
  }

  /**
   * Prüft, ob alle installierten Dialekte eine gültige Beschreibung haben. 
   **/
  public void testDialectDescriptions() {
    Dialect[] dialects = ParserFactory.getDialects();
    for (int i = 0; i < dialects.length; i++) {
      Dialect dialect = dialects[i];
      assertNotNull("dialect description[" + i + "]", dialect.description);
      assertTrue("empty dialect description[" + i + "]", !dialect.equals(""));
    }
  }

}
