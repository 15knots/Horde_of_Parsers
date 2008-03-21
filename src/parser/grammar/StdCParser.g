// $Header: /home/weber/cvsRepos/tessyparser/src/parser/grammar/StdCParser.g,v 1.4 2002/10/25 13:39:05 weber Exp $
/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	Copyright (c) Non, Inc. 1997 -- All Rights Reserved

PROJECT:        C Compiler
MODULE:         Parser
FILE:           stdc.g

AUTHOR:         John D. Mitchell (john@non.net), Jul 12, 1997

REVISION HISTORY:

	Name    Date            Description
	----    ----            -----------
	JDM     97.07.12        Initial version.
	JTC     97.11.18        Declaration vs declarator & misc. hacking.
	JDM     97.11.20        Fixed:  declaration vs funcDef,
					parenthesized expressions,
					declarator iteration,
					varargs recognition,
					empty source file recognition,
					and some typos.


DESCRIPTION:

	This grammar supports the Standard C language.

	Note clearly that this grammar does *NOT* deal with
	preprocessor functionality (including things like trigraphs)
	Nor does this grammar deal with multi-byte characters nor strings
	containing multi-byte characters [these constructs are "exercises
	for the reader" as it were :-)].

	Please refer to the ISO/ANSI C Language Standard if you believe
	this grammar to be in error.  Please cite chapter and verse in any
	correspondence to the author to back up your claim.

TODO:

	- typedefName is commented out, needs a symbol table to resolve
	ambiguity. MW: echt?

	- trees

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/

// NOTE: Uebersetzen mit
//			$(ANTLR) StdCParser.g



header {
package parser.grammar;
}

/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		Parser
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

{
//++++++++++++++++++++++++++++ Class preamble
import parser.MessageReporter;

//++++++++++++++++++++++++++++ end Class preamble
}

/**
 * Der Parser fuer Standard/ISO C. Alle anderen Parser fuer die
 * Tessy-Crosscompiler sind von dieser Basisgrammatik abgeleitet.
 *
 *  @see #translationUnit
 */
class StdCParser extends Parser;

options
{
	k = 2;
	exportVocab = StdC;
	buildAST = true;
	ASTLabelType = "TNode";

	// code generation options...
	codeGenMakeSwitchThreshold = 2;
	codeGenBitsetTestThreshold = 3;
}


{
//++++++++++++++++++++++++++++ user defined code (fields and methods)

	/** Gibt die Fehlermeldungen einheitlich aus. */
	private MessageReporter msgReporter= new parser.MessageReporter();

	// Suppport C++-style single-line comments?
	public static boolean CPPComments = true;

	// access to symbol table
	public CSymbolTable symbolTable = new CSymbolTable();

	// source for names to unnamed scopes
	protected int unnamedScopeCounter = 0;

	public boolean isTypedefName(String name) {
		boolean returnValue = false;
		TNode node = symbolTable.lookupNameInCurrentScope(name);
		for (; node != null; node = (TNode) node.getNextSibling() ) {
			if(node.getType() == LITERAL_typedef) {
		returnValue = true;
		break;
			}
		}
		return returnValue;
	}


	public String getAScopeName() {
		return "" + (unnamedScopeCounter++);
	}

	public void pushScope(String scopeName) {
		symbolTable.pushScope(scopeName);
	}

	public void popScope() {
		symbolTable.popScope();
	}

	/** Ueberschrieben, um nach System.out auszugeben, wie sich das
	* fuer einen Compiler gehoert.
	*/
	public void reportError(RecognitionException ex) {

		msgReporter.print( MessageReporter.ERROR, ex, getFilename());
		try {
			System.out.println( ex + " token name:" + tokenNames[LA(1)]);
//      ex.printStackTrace(System.err);
		}
		catch (TokenStreamException e) {
		  System.out.println();
			System.out.println( ex);
//      ex.printStackTrace(System.err);
		}
	}

	/** Ueberschrieben, um nach System.out auszugeben, wie sich das
	* fuer einen Compiler gehoert.
	*/
	public void reportError(String s) {
		msgReporter.println( MessageReporter.ERROR, s, getFilename());
	}

	/** Ueberschrieben, um nach System.out auszugeben, wie sich das
	* fuer einen Compiler gehoert.
	*/
	public void reportWarning(String s) {
		msgReporter.println( MessageReporter.WARN, s, getFilename());
	}


	/** Ueberschrieben zwecks mehr Info */
	public void match(int t) throws MismatchedTokenException {
		boolean debugging = false;

		if ( debugging ) {
		 for (int x=0; x<traceDepth; x++) System.out.print(" ");
		 try {
			System.out.println("Match("+tokenNames[t]+") with LA(1)="+
		tokenNames[LA(1)] + ((inputState.guessing>0)?" [inputState.guessing "+ inputState.guessing + "]":""));
		 }
		 catch (TokenStreamException e) {
			System.out.println("Match("+tokenNames[t]+") " + ((inputState.guessing>0)?" [inputState.guessing "+ inputState.guessing + "]":""));

		 }

		}
		try {
			if ( LA(1)!=t ) {
		if ( debugging ){
				for (int x=0; x<traceDepth; x++) System.out.print(" ");
				System.out.println("token mismatch: "+tokenNames[LA(1)]
				+ "!="+tokenNames[t]);
		}
		throw new MismatchedTokenException(tokenNames, LT(1), t, false, getFilename());

			} else {
		// mark token as consumed -- fetch next token deferred until LA/LT
		consume();
			}
		}
		catch (TokenStreamException e) {
		}

	}

	/** Ueberschrieben zwecks mehr Info (Tokenname ausgeben)*/
	public void traceIn(String rname) {
		++traceDepth;
		traceIndent();
		try {
		 System.out.println("> "+rname+"; LA(1)==("+ getTokenName( LT(1).getType())
		+ ") " + LT(1).getText() + ((inputState.guessing > 0)?" [guessing]":""));
		}
		catch (TokenStreamException e) {
		 System.out.println("> "+rname+ ((inputState.guessing > 0)?" [guessing]":""));
		}
	}

	/** Ueberschrieben zwecks mehr Info (Tokenname ausgeben) */
	public void traceOut(String rname) {
		traceIndent();
		try {
			System.out.println("< "+rname+"; LA(1)==("+ getTokenName(LT(1).getType())
		+ ") "+ LT(1).getText() + ((inputState.guessing > 0)?" [guessing]":""));
		}
		catch (TokenStreamException e) {
			System.out.println("> "+rname+ ((inputState.guessing > 0)?" [guessing]":""));
		}
		traceDepth--;
	}

//++++++++++++++++++++++++++++ end user defined code
}


//++++++++++++++++++++++++++++ rules

translationUnit
	:       externalList

	|       /* Empty source files are *not* allowed.  */
		{ reportError( "empty source file");	}
	;


protected
externalList
	:       ( externalDef )+
	;


protected
externalDef
	:       ( "typedef" | declaration )=> declaration
	|       functionDef
	|       asm_expr
	;


protected
asm_expr
	:       "asm"^
		("volatile")? LCURLY! expr RCURLY! SEMI!
	;


protected
declaration
	{ AST ds1 = null; }
	:       ds:declSpecifiers       { ds1 = astFactory.dupList(#ds); }
		(
				initDeclList[ds1]
		)?
		SEMI!
					{ ## = #( #[NDeclaration], ##); }

	;


protected
declSpecifiers
	{ int specCount=0; }
	:       ( options { // this loop properly aborts when
				 //  it finds a non-typedefName ID MBZ
		warnWhenFollowAmbig = false;
		} :
		  s:storageClassSpecifier
		| typeQualifier
//		| ( "struct" | "union" | "enum" | typeSpecifier[specCount] )=>
//			specCount = typeSpecifier[specCount]
		| (  typeSpecifier[specCount] )=>
			specCount = typeSpecifier[specCount]
		)+
	;

protected
storageClassSpecifier
	:       "auto"
	|       "register"
	|       "typedef"
	|       functionStorageClassSpecifier
	;


protected
functionStorageClassSpecifier
	:       "extern"
	|       "static"
	;


protected
typeQualifier
	:       "const"
	|       "volatile"
	;

protected
typeSpecifier [int specCount] returns [int retSpecCount]
	{ retSpecCount = specCount + 1; }
	:
	(       "void"
	|       "char"
	|       "short"
	|       "int"
	|       "long"
	|       "float"
	|       "double"
	|       "signed"
	|       "unsigned"
	|       structOrUnionSpecifier
	|       enumSpecifier
	|       { specCount == 0 }? typedefName
	)
	;


protected
typedefName
	:       { isTypedefName ( LT(1).getText() ) }?
		i:ID                    { ## = #(#[NTypedefName], #i); }
	;

protected
structOrUnionSpecifier
	{ String scopeName; }
	:       sou:structOrUnion!
		( ( ID LCURLY )=> i:ID l:LCURLY
							{
							scopeName = #sou.getText() + " " + #i.getText();
							#l.setText(scopeName);
							pushScope(scopeName);
							}
			structDeclarationList
							{ popScope();}
			RCURLY!
		|   l1:LCURLY
							{
							scopeName = getAScopeName();
							#l1.setText(scopeName);
							pushScope(scopeName);
							}
				structDeclarationList
							{ popScope(); }
				RCURLY!
		| ID
		)
							{
							## = #( #sou, ## );
							}
	;


protected
structOrUnion
	:       "struct"
	|       "union"
	;


protected
structDeclarationList
	:       ( structDeclaration )+
	;


protected
structDeclaration
	:       specifierQualifierList structDeclaratorList ( SEMI! )+
	;


protected
specifierQualifierList
	{ int specCount = 0; }
	:       (               options {   // this loop properly aborts when
		// it finds a non-typedefName ID MBZ
		warnWhenFollowAmbig = false;
		} :
		( typeSpecifier[specCount] )=>
			specCount = typeSpecifier[specCount]
		| typeQualifier
		)+
	;


protected
structDeclaratorList
	:       structDeclarator ( COMMA! structDeclarator )*
	;


protected
structDeclarator
	:
	(       COLON constExpr
	|       declarator[false] ( COLON constExpr )?
	)
						{ ## = #( #[NStructDeclarator], ##); }
	;


protected
enumSpecifier
	:       "enum"^
		( ( ID LCURLY )=> i:ID LCURLY enumList[i.getText()] RCURLY!
		| LCURLY enumList["anonymous"] RCURLY!
		| ID
		)
	;


protected
enumList[String enumName]
	:       enumerator[enumName] ( COMMA! enumerator[enumName] )*
	;

protected
enumerator[String enumName]
	:       i:ID                { symbolTable.add(  i.getText(),
							#(   null,
									#[LITERAL_enum, "enum"],
									#[ ID, enumName]
							 )
								 );
						}
		(ASSIGN constExpr)?
	;


protected
initDeclList[AST declarationSpecifiers]
	:       initDecl[declarationSpecifiers]
		( COMMA! initDecl[declarationSpecifiers] )*
	;


protected
initDecl[AST declarationSpecifiers]
	{ String declName = ""; }
	:       declName = d:declarator[false]
					{   AST ds1, d1;
							ds1 = astFactory.dupList(declarationSpecifiers);
							d1 = astFactory.dupList(#d);
							symbolTable.add(declName, #(null, ds1, d1) );
					}
		( ASSIGN initializer
		| COLON expr
		)?
					{ ## = #( #[NInitDecl], ## ); }

	;

protected
pointerGroup
	:       ( STAR ( typeQualifier )* )+    { ## = #( #[NPointerGroup], ##); }
	;



protected
idList
	:       ID ( COMMA! ID )*
	;


protected
initializer
	:       ( assignExpr
		|       LCURLY initializerList ( COMMA! )? RCURLY!
		)
			{ ## = #( #[NInitializer], ## ); }
	;


protected
initializerList
	:       initializer ( COMMA! initializer )*
	;


protected
declarator[boolean isFunctionDefinition] returns [String declName]
	{ declName = ""; }
	: ( pointerGroup )?
		( id:ID                         { declName = id.getText(); }
			| LPAREN declName = declarator[false] RPAREN
		)

		( ! LPAREN
				{
			if (isFunctionDefinition) {
					pushScope(declName);
			}
			else {
					pushScope("!"+declName);
			}
				}
				(
			(declSpecifiers)=> p:parameterTypeList
			{
				## = #( null, ##, #( #[NParameterTypeList], #p ) );
			}

			| (i:idList)?
			{
				## = #( null, ##, #( #[NParameterTypeList], #i ) );
			}
				)
				{
		popScope();
				}
			RPAREN
		| LBRACKET ( constExpr )? RBRACKET
		)*
		{ ## = #( #[NDeclarator], ## ); }
	;

protected
parameterTypeList
	:  parameterDeclaration
			(
				options { warnWhenFollowAmbig = false;  }
				:  COMMA! parameterDeclaration
			)*
		( COMMA! VARARGS )?
	;


protected
parameterDeclaration
	{ String declName; }
	:  ds:declSpecifiers
			 ( ( declarator[false] )=> declName = d:declarator[false]
			{
				AST d2, ds2;
				d2 = astFactory.dupList(#d);
				ds2 = astFactory.dupList(#ds);
				symbolTable.add(declName, #(null, ds2, d2));
			}
		| nonemptyAbstractDeclarator
				)?
			{
				## = #( #[NParameterDeclaration], ## );
			}
	;

/* JTC:
 * This handles both new and old style functions.
 * see declarator rule to see differences in parameters
 * and here (declaration SEMI)* is the param type decls for the
 * old style.  may want to do some checking to check for illegal
 * combinations (but I assume all parsed code will be legal?)
 */

protected
functionDef
	{ String declName; }
	:       ( (functionDeclSpecifiers)=> ds:functionDeclSpecifiers
		|  //epsilon
		)
		declName = d:declarator[true]
					{
					AST d2, ds2;
					d2 = astFactory.dupList(#d);
					ds2 = astFactory.dupList(#ds);
					symbolTable.add(declName, #(null, ds2, d2));
					pushScope(declName);
					}
		( declaration )* (VARARGS)? ( SEMI! )*
					{ popScope(); }
		compoundStatement[declName]
		{ ## = #( #[NFunctionDef], ## );}
	;

protected
functionDeclSpecifiers
	{ int specCount = 0; }
	:       ( options {   // this loop properly aborts when
						 // it finds a non-typedefName ID MBZ
			warnWhenFollowAmbig = false;
			}
			: functionStorageClassSpecifier
			| typeQualifier
			| ( typeSpecifier[specCount] )
			  =>specCount = typeSpecifier[specCount]
		)+
	;

protected
declarationList
	:       ( options {   // this loop properly aborts when
						 // it finds a non-typedefName ID MBZ
			warnWhenFollowAmbig = false;
			}
			: ( declarationPredictor )=> declaration
		)+
	;

protected
declarationPredictor
	:       (options {      //only want to look at declaration if I don't see typedef
				warnWhenFollowAmbig = false;
		}:
		"typedef"
		| declaration
		)
	;


protected
compoundStatement[String scopeName]
	:       LCURLY!
					{
				pushScope(scopeName);
					}
		( ( declarationPredictor)=> declarationList )?
		( statementList )?
					{ popScope(); }
		RCURLY!
					{ ## = #( #[NCompoundStatement, scopeName], ##); }
	;


protected
statementList
	:       ( statement )+
	;
protected
statement
	:       SEMI                    // Empty statements

	|       compoundStatement[getAScopeName()]       // Group of statements

	|       expr SEMI!               { ## = #( #[NStatementExpr], ## ); } // Expressions

// Iteration statements:

	|       "while"^ LPAREN! expr RPAREN! statement
	|       "do"^ statement "while"! LPAREN! expr RPAREN! SEMI!
	|!       "for"
		LPAREN ( e1:expr )? SEMI ( e2:expr )? SEMI ( e3:expr )? RPAREN
		s:statement
						{
					if ( #e1 == null) { #e1 = #[ NEmptyExpression ]; }
					if ( #e2 == null) { #e2 = #[ NEmptyExpression ]; }
					if ( #e3 == null) { #e3 = #[ NEmptyExpression ]; }
					## = #( #[LITERAL_for, "for"], #e1, #e2, #e3, #s );
						}


// Jump statements:

	|       "goto"^ ID SEMI!
	|       "continue" SEMI!
	|       "break" SEMI!
	|       "return"^ ( expr )? SEMI!


// Labeled statements:
	|       ID COLON! (options {warnWhenFollowAmbig=false;}:statement)? { ## = #( #[NLabel], ## ); }
	|       "case"^ constExpr COLON! statement
	|       "default"^ COLON! statement



// Selection statements:

	|       "if"^
		 LPAREN! expr RPAREN! statement
		( //standard if-else ambiguity
			options {
					warnWhenFollowAmbig = false;
			} :
		"else" statement )?
	|       "switch"^ LPAREN! expr RPAREN! statement
	;






protected
expr
	:       assignExpr (options {
				/* MBZ:
						COMMA is ambiguous between comma expressions and
						argument lists.  argExprList should get priority,
						and it does by being deeper in the expr rule tree
						and using (COMMA assignExpr)*
				*/
				warnWhenFollowAmbig = false;
					} :
					c:COMMA^ { #c.setType(NCommaExpr); } assignExpr
					)*
	;


protected
assignExpr
	:       conditionalExpr ( a:assignOperator! assignExpr { ## = #( #a, ## );} )?
	;

protected
assignOperator
	:       ASSIGN
	|       DIV_ASSIGN
	|       PLUS_ASSIGN
	|       MINUS_ASSIGN
	|       STAR_ASSIGN
	|       MOD_ASSIGN
	|       RSHIFT_ASSIGN
	|       LSHIFT_ASSIGN
	|       BAND_ASSIGN
	|       BOR_ASSIGN
	|       BXOR_ASSIGN
	;


protected
conditionalExpr
	:       logicalOrExpr
		( QUESTION^ expr COLON! conditionalExpr )?
	;


protected
constExpr
	:       conditionalExpr
	;

protected
logicalOrExpr
	:       logicalAndExpr ( LOR^ logicalAndExpr )*
	;


protected
logicalAndExpr
	:       inclusiveOrExpr ( LAND^ inclusiveOrExpr )*
	;

protected
inclusiveOrExpr
	:       exclusiveOrExpr ( BOR^ exclusiveOrExpr )*
	;


protected
exclusiveOrExpr
	:       bitAndExpr ( BXOR^ bitAndExpr )*
	;


protected
bitAndExpr
	:       equalityExpr ( BAND^ equalityExpr )*
	;



protected
equalityExpr
	:       relationalExpr
		( ( EQUAL^ | NOT_EQUAL^ ) relationalExpr )*
	;


protected
relationalExpr
	:       shiftExpr
		( ( LT^ | LTE^ | GT^ | GTE^ ) shiftExpr )*
	;



protected
shiftExpr
	:       additiveExpr
		( ( LSHIFT^ | RSHIFT^ ) additiveExpr )*
	;


protected
additiveExpr
	:       multExpr
		( ( PLUS^ | MINUS^ ) multExpr )*
	;


protected
multExpr
	:       castExpr
		( ( STAR^ | DIV^ | MOD^ ) castExpr )*
	;


protected
castExpr
	:       ( LPAREN typeName RPAREN )=>
		LPAREN! typeName RPAREN! ( castExpr )
					{ ## = #( #[NCast, "("], ## ); }

	|       unaryExpr
	;


protected
typeName
	:       specifierQualifierList (nonemptyAbstractDeclarator)?
	;

protected
nonemptyAbstractDeclarator
	:   (
		pointerGroup
		(   (LPAREN
				(   nonemptyAbstractDeclarator
			| parameterTypeList
				)?
				RPAREN)
		| (LBRACKET (expr)? RBRACKET)
		)*

			|   (   (LPAREN
				(   nonemptyAbstractDeclarator
			| parameterTypeList
				)?
				RPAREN)
		| (LBRACKET (expr)? RBRACKET)
		)+
			)
					{   ## = #( #[NNonemptyAbstractDeclarator], ## ); }

	;

/* JTC:

LR rules:

protected
abstractDeclarator
	:       nonemptyAbstractDeclarator
	|       // null
	;

protected
nonemptyAbstractDeclarator
	:       LPAREN  nonemptyAbstractDeclarator RPAREN
	|       abstractDeclarator LPAREN RPAREN
	|       abstractDeclarator (LBRACKET (expr)? RBRACKET)
	|       STAR abstractDeclarator
	;
*/

protected
unaryExpr
	:       postfixExpr
	|       INC^ unaryExpr
	|       DEC^ unaryExpr
	|       u:unaryOperator castExpr { ## = #( #[NUnaryExpr], ## ); }

	|       "sizeof"^
		( ( LPAREN typeName )=> LPAREN typeName RPAREN
		| unaryExpr
		)
	;


protected
unaryOperator
	:       BAND
	|       STAR
	|       PLUS
	|       MINUS
	|       BNOT
	|       LNOT
	;

protected
postfixExpr
	:       primaryExpr
		(
		postfixSuffix                   {## = #( #[NPostfixExpr], ## );}
		)?
	;
protected
postfixSuffix
	:
		( PTR ID
		| DOT ID
		| functionCall
		| LBRACKET expr RBRACKET
		| INC
		| DEC
		)+
	;

protected
functionCall
	:
		LPAREN^ (a:argExprList)? RPAREN
			{
			##.setType( NFunctionCallArgs );
			}
	;


protected
primaryExpr
	:       ID
	|       charConst
	|       intConst
	|       floatConst
	|       stringConst

// JTC:
// ID should catch the enumerator
// leaving it in gives ambiguous err
//      | enumerator
	|       LPAREN! expr RPAREN!        { ## = #( #[NExpressionGroup, "("], ## ); }
	;

protected
argExprList
	:       assignExpr ( COMMA! assignExpr )*
	;



protected
charConst
	:       CharLiteral
	;


protected
stringConst
	:       (StringLiteral)+                { ## = #(#[NStringSeq], ##); }
	;


protected
intConst
	:       IntOctalConst
	|       LongOctalConst
	|       UnsignedOctalConst
	|       IntIntConst
	|       LongIntConst
	|       UnsignedIntConst
	|       IntHexConst
	|       LongHexConst
	|       UnsignedHexConst
	;


protected
floatConst
	:       FloatDoubleConst
	|       DoubleDoubleConst
	|       LongDoubleConst
	;






protected
dummy
	:       NTypedefName
	|       NInitDecl
	|       NDeclarator
	|       NStructDeclarator
	|       NDeclaration
	|       NCast
	|       NPointerGroup
	|       NExpressionGroup
	|       NFunctionCallArgs
	|       NNonemptyAbstractDeclarator
	|       NInitializer
	|       NStatementExpr
	|       NEmptyExpression
	|       NParameterTypeList
	|       NFunctionDef
	|       NCompoundStatement
	|       NParameterDeclaration
	|       NCommaExpr
	|       NUnaryExpr
	|       NLabel
	|       NPostfixExpr
	|       NRangeExpr
	|       NStringSeq
	|       NInitializerElementLabel
	|       NLcurlyInitializer
	|       NAsmAttribute
	|       NGnuAsmExpr
	|       NTypeMissing
	;






/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		Lexer
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
{
//++++++++++++++++++++++++++++ Class preamble
	import java.io.*;
	import antlr.*;
//++++++++++++++++++++++++++++ end Class preamble
}

/**
 * Der Lexer fuer Standard/ISO C. Alle anderen Lexer fuer die
 * Tessy-Crosscompiler sind von dieser Basisgrammatik abgeleitet.
 */
class StdCLexer extends Lexer;

options
{
	k = 3;
	exportVocab = StdC;
	testLiterals = false;
}

{
//++++++++++++++++++++++++++++ user defined code (fields and methods)

 /**
	* MW: Das LineObject ist mit ANTLR 2.7.0 wohl ueberholt?
	*/
	private LineObject lineObject = new LineObject();
	private PreprocessorInfoChannel preprocessorInfoChannel = new PreprocessorInfoChannel();

	/** true: alle ausgelieferten Token zaehlen und durchnummerieren */
	private boolean countingTokens = true;
	private int tokenNumber = 0;
	private int deferredLineCount = 0;

	/** Legt Nummerierung der ausgelieferten Token fest. Wird fuer Preprocessing-
	 *  Direktiven gebraucht.
	 * true: alle ausgelieferten Token zaehlen und durchnummerieren
	 */
	public void setCountingTokens(boolean ct)
	{
		countingTokens = ct;
		if ( countingTokens ) {
			tokenNumber = 0;
		}
		else {
			tokenNumber = 1;
		}
	}

	/** Ueberschrieben, damit das LineObject das mitbekommt.
	 */
	public void setFilename(String f) {
		super.setFilename( f);
		lineObject.setSource(f);
	}

	public PreprocessorInfoChannel getPreprocessorInfoChannel()
	{
		return preprocessorInfoChannel;
	}

	private void setPreprocessingDirective(String pre)
	{
		preprocessorInfoChannel.addLineForTokenNumber( pre, new Integer(tokenNumber) );
	}

	/**
	 * Ueberschrieben, um die Token mit dem Name der Quelldatei zu initalisieren
	 * und um die Token fortlaufend zu nummerieren.
	 */
	protected Token makeToken(int t)
	{
		if ( t != Token.SKIP && countingTokens) {
	tokenNumber++;
		}
		CToken tok = (CToken) super.makeToken(t);
		tok.setLine(lineObject.line); // MW: wird auch von super.makeToken() erledigt?
		tok.setSource( getFilename());
		tok.setTokenNumber(tokenNumber);

		lineObject.line += deferredLineCount;
		deferredLineCount = 0;
		return tok;
	}

	private void deferredNewline() {
			deferredLineCount++;
	}

	public void newline() {
			lineObject.newline();
	}

//++++++++++++++++++++++++++++ end user defined code
}

//++++++++++++++++++++++++++++ rules

// a dummy rule to force vocabulary to be all characters (except special
//   ones that ANTLR uses internally (0 to 2)
protected
Vocabulary
	:       '\3'..'\377'
	;


/* Operators: */

ASSIGN          : '=' ;
COLON           : ':' ;
COMMA           : ',' ;
QUESTION        : '?' ;
SEMI            : ';' ;
PTR             : "->" ;


// DOT & VARARGS are commented out since they are generated as part of
// the Number rule below due to some bizarre lexical ambiguity shme.

// DOT  :       '.' ;
protected
DOT:;

// VARARGS      : "..." ;
protected
VARARGS:;


LPAREN          : '(' ;
RPAREN          : ')' ;
LBRACKET        : '[' ;
RBRACKET        : ']' ;
LCURLY          : '{' ;
RCURLY          : '}' ;

EQUAL           : "==" ;
NOT_EQUAL       : "!=" ;
LTE             : "<=" ;
LT              : "<" ;
GTE             : ">=" ;
GT              : ">" ;

DIV             : '/' ;
DIV_ASSIGN      : "/=" ;
PLUS            : '+' ;
PLUS_ASSIGN     : "+=" ;
INC             : "++" ;
MINUS           : '-' ;
MINUS_ASSIGN    : "-=" ;
DEC             : "--" ;
STAR            : '*' ;
STAR_ASSIGN     : "*=" ;
MOD             : '%' ;
MOD_ASSIGN      : "%=" ;
RSHIFT          : ">>" ;
RSHIFT_ASSIGN   : ">>=" ;
LSHIFT          : "<<" ;
LSHIFT_ASSIGN   : "<<=" ;

LAND            : "&&" ;
LNOT            : '!' ;
LOR             : "||" ;

BAND            : '&' ;
BAND_ASSIGN     : "&=" ;
BNOT            : '~' ;
BOR             : '|' ;
BOR_ASSIGN      : "|=" ;
BXOR            : '^' ;
BXOR_ASSIGN     : "^=" ;


protected Space:
	( ' ' | '\t' | '\f')
	;

//Whitespace-- ignored
Whitespace
	:       ( ( '\003'..'\010' | Space  | '\016'.. '\037' | '\177'..'\377' )
		// handle newlines
		|	(	"\r\n"  // Evil DOS
			|	'\r'    // Macintosh
			|	'\n'    // Unix (the right way)
			)
			{ newline(); }
		)                       { _ttype = Token.SKIP;  }
	;

protected
Newline
	: (	/*	'\r' '\n' can be matched in one alternative or by matching
			'\r' in one iteration and '\n' in another.  I am trying to
			handle any flavor of newline that comes in, but the language
			that allows both "\r\n" and "\r" and "\n" to all be valid
			newline is ambiguous.  Consequently, the resulting grammar
			must be ambiguous.  I'm shutting this warning off.
		 */
		options { generateAmbigWarnings=false; }
		:	"\r\n"
		|	'\r'
		|	'\n'
	  ) {deferredNewline();}
	;


// multiple-line comments
Comment
	:	"/*"
		(	{ LA(2)!='/' }? '*'
		|	Newline
		|	~('*'|'\n'|'\r')
		)*
		"*/"
		{	$setType(Token.SKIP);}
	;

CPPComment
	:
		"//" ( ~('\n') )*
		{	$setType(Token.SKIP);}
	;

// testen mit: gcc -E -ansi -pedantic -o - proper-split.C |grep "^#"
PREPROC_DIRECTIVE
options {
	paraphrase = "a preprocessor directive";
}
	:
	'#' (
	  ( "line" | (( Space)+ '0'..'9')) => LineDirective
	  // alle anderen driektiven..
	  | (~'\n')*  { setPreprocessingDirective(getText()); }
	  )
	    {_ttype = Token.SKIP;	}
	;

protected LineDirective
options {
	paraphrase = "a line directive";
}
	{
		boolean oldCountingTokens = countingTokens;
		countingTokens = false;
	}
	: {
		lineObject = new LineObject();
		deferredLineCount = 0;
	  }
		("line")?  //this would be for if the directive started "#line", but not there for GNU directives
		(Space)+
		n:Number { lineObject.setLine(Integer.parseInt(n.getText())); }
		(Space)+
		(  // "filename" or FILENAME
		  fn:StringLiteralSingleLine {  try {
				lineObject.setSource(
					fn.getText().substring(1,fn.getText().length()-1));
				}
				catch (StringIndexOutOfBoundsException e) { /*not possible*/ }
			}
		  | fi:ID { lineObject.setSource(fi.getText()); }
		)?

		(Space)* ( "1"		{ lineObject.setEnteringFile(true); } )?
		(Space)* ( "2"		{ lineObject.setReturningToFile(true); } )?
		(Space)* ( "3"		{ lineObject.setSystemHeader(true); } )?
		(Space)* ( "4"		{ lineObject.setTreatAsC(true); } )?
		(~('\r' | '\n'))*
		("\r\n" | "\r" | "\n")
	  {
		preprocessorInfoChannel.addLineForTokenNumber(
			new LineObject(lineObject), new Integer(tokenNumber));
		countingTokens = oldCountingTokens;
	  }
	;



/* Literals: */

/*
 * Note that we do NOT handle tri-graphs nor multi-byte sequences.
 */


/*
 * Note that we can't have empty character constants (even though we
 * can have empty strings :-).
 */
CharLiteral
	:       '\'' ( Escape | ~( '\'' ) ) '\''
	;


/*
 * Can't have raw imbedded newlines in string constants.  Strict reading of
 * the standard gives odd dichotomy between newlines & carriage returns.
 * Go figure.
 */
StringLiteral
	:       '"'
		( Escape
		  | Newline 	{ _ttype = BadStringLiteral; }
		  // String mit Zeilenumbruch
		  | '\\' Newline
		  | ~( '"' | '\r' | '\n' | '\\' )
		)*
		'"'
	;

// StringLinteral in einer Zeile
protected
StringLiteralSingleLine
	:       '"' ( Escape | ~( '"' | '\r' | '\n' | '\\' ))* '"'
	;


protected BadStringLiteral
	:       // Imaginary token.
	;


/*
 * Handle the various escape sequences.
 *
 * Note carefully that these numeric escape *sequences* are *not* of the
 * same form as the C language numeric *constants*.
 *
 * There is no such thing as a binary numeric escape sequence.
 *
 * Octal escape sequences are either 1, 2, or 3 octal digits exactly.
 *
 * There is no such thing as a decimal escape sequence.
 *
 * Hexadecimal escape sequences are begun with a leading \x and continue
 * until a non-hexadecimal character is found.
 *
 * No real handling of tri-graph sequences, yet.
 */

protected
Escape
	:       '\\'
		( options{warnWhenFollowAmbig=false;}:
		'a'
		| 'b'
		| 'f'
		| 'n'
		| 'r'
		| 't'
		| 'v'
		| '"'
		| '\''
		| '\\'
		| '?'
		| ('0'..'3') ( options{warnWhenFollowAmbig=false;}
			: Digit ( options{warnWhenFollowAmbig=false;}: Digit )? )?
		| ('4'..'7') ( options{warnWhenFollowAmbig=false;}
			: Digit )?
		| 'x' ( options{warnWhenFollowAmbig=false;}: Digit | 'a'..'f' | 'A'..'F' )+
		)
	;


/* Numeric Constants: */

protected
Digit
	:       '0'..'9'
	;

protected
LongSuffix
	:       'l'
	|       'L'
	;

protected
UnsignedSuffix
	:       'u'
	|       'U'
	;

protected
FloatSuffix
	:       'f'
	|       'F'
	;

protected
Exponent
	:       ( 'e' | 'E' ) ( '+' | '-' )? ( Digit )+
	;


protected
DoubleDoubleConst:;

protected
FloatDoubleConst:;

protected
LongDoubleConst:;

protected
IntOctalConst:;

protected
LongOctalConst:;

protected
UnsignedOctalConst:;

protected
IntIntConst:;

protected
LongIntConst:;

protected
UnsignedIntConst:;

protected
IntHexConst:;

protected
LongHexConst:;

protected
UnsignedHexConst:;




Number
	:       ( ( Digit )+ ( '.' | 'e' | 'E' ) )=> ( Digit )+
		( '.' ( Digit )* ( Exponent )?
		| Exponent
		)                       { _ttype = DoubleDoubleConst;   }
		( FloatSuffix           { _ttype = FloatDoubleConst;    }
		| LongSuffix            { _ttype = LongDoubleConst;     }
		)?

	|       ( "..." )=> "..."       { _ttype = VARARGS;     }

	|       '.'                     { _ttype = DOT; }
		( ( Digit )+ ( Exponent )?
					{ _ttype = DoubleDoubleConst;   }
			( FloatSuffix         { _ttype = FloatDoubleConst;    }
			| LongSuffix          { _ttype = LongDoubleConst;     }
			)?
		)?

	|       '0' ( '0'..'7' )*       { _ttype = IntOctalConst;       }
		( LongSuffix            { _ttype = LongOctalConst;      }
		| UnsignedSuffix        { _ttype = UnsignedOctalConst;  }
		)?

	|       '1'..'9' ( Digit )*     { _ttype = IntIntConst;         }
		( LongSuffix            { _ttype = LongIntConst;        }
		| UnsignedSuffix        { _ttype = UnsignedIntConst;    }
		)?

	|       '0' ( 'x' | 'X' ) ( 'a'..'f' | 'A'..'F' | Digit )+
					{ _ttype = IntHexConst;         }
		( LongSuffix            { _ttype = LongHexConst;        }
		| UnsignedSuffix        { _ttype = UnsignedHexConst;    }
		)?
	;



/** an identifier.  Note that testLiterals is set to true!  This means
 * that after we match the rule, we look in the literals table to see
 * if it's a literal or really an identifer
 */
ID
	options {
		testLiterals=true;
		paraphrase = "an identifier";
	}
	: IDletter
		(IDletter | Digit)*
	;

/** Liefert erstes Zeichen eines Identifiers, zum Ueberschreiben
*/
protected
IDletter
	: ('a'..'z'|'A'..'Z'|'_')
	;