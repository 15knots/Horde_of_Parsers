// $Id: CToken.java,v 1.2 2002/10/19 20:09:03 weber Exp $
package parser.grammar;
import antlr.CommonToken;


/**
 * Dieses Token hält den Namen der Quelldatei und eine von Lexer fortlaufend
 * zugewiesene Nummer.
 */
public class CToken extends CommonToken {
  private String source = null;
  private int tokenNumber;

  public String getSource()
  {
    return source== null? "": source;
  }

  void setSource(String src)
  {
    source = src;
  }

  public int getTokenNumber()
  {
    return tokenNumber;
  }

  void setTokenNumber(int i)
  {
    tokenNumber = i;
  }

  public String toString() {
     return super.toString()+ ", source=" + source;
  }
}
