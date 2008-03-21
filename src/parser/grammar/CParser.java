// $Id: CParser.java,v 1.2 2002/10/19 20:09:03 weber Exp $
package parser.grammar;

import antlr.RecognitionException;
import antlr.TokenStreamException;

/**
 * Soll weg!
 * Definiert die Anforderungen für ein C-Parser, wie er vom
 * Schnittstellenextrahierer verwendet werden kann.
 */
public interface CParser
{
  /**
   * Wrapper Method:
   * Ruft die allererste Produktion in generierten Parser auf.
   */
  public void parse() throws RecognitionException, TokenStreamException;
}

