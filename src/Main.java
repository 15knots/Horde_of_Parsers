// $Id: Main.java,v 1.3 2008/03/15 18:21:36 weber Exp $

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import parser.Dialect;
import parser.ParserFactory;
import parser.grammar.ExtractPreparer;
import parser.grammar.StdCParser;
import parser.grammar.TNode;

import antlr.Parser;


/**
 * Ein Interface-Erkenner für C-Quellen in diversen Dialekten.
 *
 * @author Martin Weber
 */
public class Main
{
  //
  public static void main(String[] args)
  {
    long now;

    if(args.length < 1) {
      System.err.println("usage: "+Main.class.getName()
			 +"-dialect=      <dialect_name>"+" <filename>");
    } else {
        for (int i=0; i<args.length; i++)
        {
      try {
	File inFile;

	if(args[i].equals("-")) {
	  // read fron stdin
	  inFile = null;
	} else {
	  inFile = new File(args[i]);
	System.out.println("file:"+args[i]);
	}

	// vorläufig nur ISO C
	Dialect dialect = ParserFactory.getDialects()[0];
	// Datei parsieren
	TNode tree = new InterfaceExtractor().processFile(dialect, inFile);

	System.exit(0); // success

      } catch(FileNotFoundException e) {
	System.err.println("could not open: "+e.getMessage());
      } catch(Exception e) {
	System.out.println("Exception:");
	e.printStackTrace();
      }
      }
    }

    System.exit(1); // failure
  }

  /**
   * Wrapper wg ANTLR-Exceptions. Ermittelt die Schnittstellen für eine
   * einzige Datei.
   */
  public static class InterfaceExtractor
  {

    /*
     * @param dialect   der C-Dialekt, für den der Parser benötigt wird
     * @param inFile    Name der Quelldatei oder <code>null</code>, wenn
     *                  von standard input gelesen werden soll.
     */
    public TNode processFile(Dialect dialect, File inFile)
      throws FileNotFoundException, IOException
    {
      TNode tree = null;

      /**
       * 1: Datei mit dem zum Dialekt passenden Parser einlesen und den
       * Syntaxbaum erzeugen
       */
      Parser parser = ParserFactory.createParser(dialect, inFile);

      try {
	((StdCParser)parser).translationUnit();
	tree = (TNode)parser.getAST();
      } catch(antlr.RecognitionException e) {
	e.printStackTrace();
      } catch(antlr.TokenStreamException e) {
	e.printStackTrace();
      }

      parser = null; // wird nicht mehr gebraucht

      //+++ ab jetzt spielt der C-Dialekt keine Rolle mehr!
      if(false ){// tree != null) {

	/**
	 * @todo 2: den Syntaxbaum verschlanken: (Mit TreeParser).
	 */
	printTree(tree);
	ExtractPreparer preparer = ParserFactory.createPreparer(dialect);
	try {
	  preparer.translationUnit(tree);
	  tree = (TNode)preparer.getAST();
	} catch(antlr.RecognitionException e) {
	  e.printStackTrace();
	}

	System.out.println(
	"+++++++++ nach ExtractPreparer ++++++++++++++++++++++++++++++++++");
	printTree(tree);

	/**
	 * @todo 3: die Schnittstellen erkennen. geht nicht mit antlr.
	 */

	/**
	 * @todo 4: recursiv zu jeder Funktion die Schnittstellen sammeln.
	 *          (alias 'Linker')
	 */
      }

      return tree;
    }

    // print tree to System.out
    private void printTree(TNode tree)
    {

/*        if(false) {
   // print the tree as XML
   Writer out = new BufferedWriter(new OutputStreamWriter(System.out));
   tree.xmlSerialize(out);
   out.close();
   }
 */
      if(true) {
	// für den Instrumentierer ggf. eigenen Visitor schreiben...
	//antlr.ASTVisitor av = new antlr.DumpASTVisitor();
	//av.visit(tree);
	TNode.printTree(tree);
      }
    }
  }
}
