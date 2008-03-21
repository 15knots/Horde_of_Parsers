// $Id: ParserFactory.java,v 1.3 2002/10/19 20:16:03 weber Exp $
package parser;

import antlr.BaseAST;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import parser.grammar.CToken;
import parser.grammar.StdCTokenTypes;
import parser.grammar.StdCLexer;
import parser.grammar.StdCParser;
import parser.grammar.TNode;
import parser.grammar.ExtractPreparer;

import antlr.CharScanner;
import antlr.Parser;


/**
 * Kennt alle parsierbaren C-Dialekte und erzeugt die f�r einen bestimmten
 * Dialekt notwendige Lexer-Parser-Kombination.
 *
 * @author Martin Weber
 */
public class ParserFactory
{

  /** alle implementierten Dialekte. */
  private static Dialect[] dialects = {
    new Dialect("standard", "ISO Standard C (ANSI C)"),
    new Dialect("keil_C51", "Keil C51 (nur zum Test!)"),
  };

  /**
   * nothing to intantiate here.
   */
  private ParserFactory() {}

  /**
   * Erzeugt die f�r einen bestimmten Dialekt notwendige
   * Lexer-Parser-Kombination.
   *
   * @param dialect   der C-Dialekt, f�r den der Parser ben�tigt wird
   * @param inFile    Name der Quelldatei oder <code>null</code>, wenn von
   *        standard input gelesen werden soll.
   *
   * @return der Parser f�r den angegeben Dialekt oder null, wenn ein
   *         ung�ltiger Dialekt angegeben wurde.
   */
  public static Parser createParser(Dialect dialect, File inFile)
    throws FileNotFoundException, IOException
  {
    CharScanner lexer;
    Parser parser = null;

    //NOTE: laut FAQ will ANTLR eine DataInputStream, um ��� usw. zu verdauen. Pr�fen!
    Reader reader = null;

    if(inFile == null) {
      // read from stdin
      reader = new InputStreamReader(System.in);
    } else {
      // read from file
      reader = new FileReader(inFile);
    }

    if(dialect.name.equals("standard")) {
      // dialektspezifisches...
      lexer = new StdCLexer(reader);
      parser = new StdCParser(lexer);
    } else if(dialect.name.equals("keil_C51")) {

      /**
       * @todo Wird noch: nur zum Test
       */

      // dialektspezifisches...
      lexer = new StdCLexer(reader);
      parser = new StdCParser(lexer);
    } else {
      return null; // kein g�ltiger Dialekt
    }

    // alle Lexer liefern Token vom Typ CToken
    lexer.setTokenObjectClass(CToken.class.getName());

//da alle Knoten vom Typ TNode sind, brauchen wir hier keine Factory, die je nach
// Token einen Knoten verschiedenen Typs erstellt...
//      ASTFactory astFactory= new TNodeFactory();
//      parser.setASTFactory( astFactory);
    // Parser-Knotentyp setzen
    parser.setASTNodeClass(TNode.class.getName());
    // tokenType-zu-tokenName-Mapping setzen..
    BaseAST.setVerboseStringConversion(true, parser.getTokenNames());

    // Dateinamen f�r Fehlermeldungen setzen...
    String filename;
    if(inFile == null) {
      // wir gehen davon aus, das der richtige Dateiname mit einer
      // #line direktive in der ersten Zeile gesetzt wurde
      filename = null;//"<stdin>";
    } else {
      filename = inFile.getCanonicalPath();
    }
    lexer.setFilename(filename);
    parser.setFilename(filename);

    return parser;
  }

  /**
   * Erzeugt die f�r einen bestimmten Dialekt notwendigen Treeparser um die
   * Extaction der Schnittstellen vorzubereiten.
   *
   * @param dialect   der C-Dialekt, f�r den der Parser ben�tigt wird
   *
   * @return der Treeparser f�r den angegeben Dialekt oder null, wenn ein
   *         ung�ltiger Dialekt angegeben wurde.
   */
  public static ExtractPreparer createPreparer(Dialect dialect)
  {
    ExtractPreparer preparer = new ExtractPreparer();
    // Parser-Knotentyp setzen
    preparer.setASTNodeClass(TNode.class.getName());
    return preparer;
  }

  /**
   * Liefert alle parsierbaren Sprachdialekte.
   */
  public static Dialect[] getDialects()
  {
    return dialects;
  }
}
