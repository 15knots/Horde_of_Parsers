package parser;

import java.io.FileInputStream;


/** 
 * Ein Interface-Erkenner für C-Quellen im GNU-C Dialekt.
 */
public class GCCParser {

  //
  public static void main(String args[]) {

    long now;

    if (args.length < 1) {
      System.out.println("usage: gp <filename>");
    }
    else {
      try {
        GnuCLexer lexer= new GnuCLexer(new FileInputStream(args[0]));
        GnuCParser parser= new GnuCParser(lexer);

        //
        lexer.setTokenObjectClass("parser.grammar.CToken");
        lexer.initialize();

        //
        parser.setASTNodeType(TNode.class.getName());
        parser.setASTFactory(new ParserASTFactory());
        ParserAST.setTokenVocabulary("parser.GNUCTokenTypes");

        // invoke parser
        parser.translationUnit();
 //       TNode.printTree(parser.getAST());
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
