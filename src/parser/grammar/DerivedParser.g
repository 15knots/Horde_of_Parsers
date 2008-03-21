// $Header: /home/weber/cvsRepos/tessyparser/src/parser/grammar/DerivedParser.g,v 1.2 2008/03/15 18:21:36 weber Exp $
/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  Anleitung zur Grammar-Inheritance mit ANTLR.

  Diese Grammatik ist von StdCParser.g abgeleitet.
  
  NOTE: ANTLR kann zwar Grammatiken vererben, die generierten Klassen 
  		bilden aber keine Klassenhierarchie; das Erben von Basis-
  		Grammatiken realisiert ANTLR durch Kopieren des Quelltextes.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/

// NOTE: Uebersetzen mit
//			$(ANTLR) -glib StdCParser.g   DerivedParser.g


header {
package parser.grammar;
}


/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		Parser
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

{
//++++++++++++++++++++++++++++ Class preamble

import java.io.*;

import antlr.CommonAST;
//++++++++++++++++++++++++++++ end Class preamble
}

/** Beispiel fuer eine abgeleitete Parsergrammatik.
*/
class DerivedParser extends StdCParser;

options
{
exportVocab = Derived;
}

// NOTE: NUR wenn der folgende Block fehlt, wird der entsprechende Block von 
//		ANTLR aus der Basisgrammatik in die erzeugte java-Datei kopiert.
//		Also ggf. von Hand aus der Basisgrammatik kopieren!
{
//++++++++++++++++++++++++++++ user defined code (fields and methods)

	// DERIVED: Suppport C++-style single-line comments?
public static boolean derivedCPPComments = true;

  public void derivedtraceOut(String rname) {
    for (int x=0; x<traceDepth; x++) System.out.print(" ");
  }

//++++++++++++++++++++++++++++ end user defined code
}

//++++++++++++++++++++++++++++ rules

//
//translationUnit
//        :       ( externalList )?       /* Empty source files are allowed.  */
//        ;


// ueberschrieben in grammatik
idList
	:       ID ( options{warnWhenFollowAmbig=false;}: COMMA ID )*
	;


/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		Lexer
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
class DerivedLexer extends StdCLexer;
options
	{
	importVocab = STDC;
	testLiterals = false;
	}
tokens {
	LITERAL___extension__ = "__extension__";
}

// NOTE: NUR wenn der folgende Block fehlt, wird der entsprechende Block von ANTLR
// aus der Basisgrammatik in die erzeugte java-Datei kopiert.
//
{
//++++++++++++++++++++++++++++ user defined code (fields and methods)
	// in derived
  public void initialize(String src)
  {
    setOriginalSource(src);
    initialize();
  }

//++++++++++++++++++++++++++++ end user defined code
}

//++++++++++++++++++++++++++++ rules
// in derived
Whitespace
	:       ( ( ' ' | '\t' | '\014')
		| "\r\n"                { newline(); }
		| ( '\n' | '\r' )       { newline();    }
		)                       { _ttype = Token.SKIP;  }
	;


// in derived
protected IntSuffix
	:   'L'
	    | 'l'
	    | 'U'
	    | 'u'
	    | 'I'
	    | 'i'
	    | 'J'
	    | 'j'
	;




