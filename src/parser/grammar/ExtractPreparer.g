// $Header: /home/weber/cvsRepos/tessyparser/src/parser/grammar/ExtractPreparer.g,v 1.2 2002/10/19 19:43:05 weber Exp $
/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

urspruenglich GNUCTreeParser
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
header {
package parser.grammar;
}



/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		TreeParser
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

{
//++++++++++++++++++++++++++++ Class preamble

import parser.MessageReporter;
//++++++++++++++++++++++++++++ end Class preamble
}


/**
 * Bereitet den AST auf das Extrahieren der Schnittstellen vor.
 * ExpressionTree normalisieren/optimieren:
 *   'a->b' => '(*a).b '
 *   'a[b]' => '*(a+b)'
 */
class ExtractPreparer extends TreeParser;

options
{
	// wir gehen davon aus, das alle fuer die Extraktion relevanten Token
	// schon in der Basisgrammatik enthalten sind.
	importVocab = StdC;
	buildAST = true;
	ASTLabelType = "TNode";

	// Copied following options from java grammar.
	codeGenMakeSwitchThreshold = 2;
	codeGenBitsetTestThreshold = 999;//debugging 3;
}


{
//++++++++++++++++++++++++++++ user defined code (fields and methods)

	/** Gibt die Fehlermeldungen einheitlich aus. */
	private MessageReporter msgReporter= new parser.MessageReporter();

	/** Ueberschrieben, um nach System.out auszugeben, wie sich das
	* fuer einen Compiler gehoert.
	*/
	public void reportError(RecognitionException ex) {

		msgReporter.println( MessageReporter.ERROR, ex, null);
	}

	/** Ueberschrieben, um nach System.out auszugeben, wie sich das
	* fuer einen Compiler gehoert.
	*/
	public void reportError(String s) {
		msgReporter.println( MessageReporter.ERROR, s, null);
	}

	/** Ueberschrieben, um nach System.out auszugeben, wie sich das
	* fuer einen Compiler gehoert.
	*/
	public void reportWarning(String s) {
		msgReporter.println( MessageReporter.WARN, s, null);
	}


	/** Ueberschrieben zwecks mehr Info (als TNode ausgeben)*/
	public void traceIn(String rname, AST t) {
		super.traceIn(rname, (t instanceof TNode? (TNode)t: t));
	}

	/** Ueberschrieben zwecks mehr Info (als TNode ausgeben) */
	public void traceOut(String rname, AST t) {
		super.traceOut(rname, (t instanceof TNode? (TNode)t: t));
	}

//++++++++++++++++++++++++++++ end user defined code
}

//++++++++++++++++++++++++++++ rules


translationUnit  options {
	defaultErrorHandler=false;
}
	:  ( externalList )?
	;
/*      exception
	catch [RecognitionException ex]
	{
		reportError(ex);
		System.out.println("PROBLEM TREE:\n" + _t.toStringList());
		if (_t!=null) {_t = _t.getNextSibling();}
	}
	*/


protected
externalList
	:  ( functions )+
	;


// nur funktionsdefinitionen behalten..
protected
functions1
	:  #( NFunctionDef (.)*)
	|! #( NDeclaration (.)*)
	;

// nur funktionen und ihren Inhalt behalten..
protected
functions
	:  #( NFunctionDef NDeclarator compoundStatement)
	|! #( NDeclaration (.)*)
	;


protected
externalDef
	:  declaration
	|  functionDef
	|  typelessDeclaration
	;

protected
typelessDeclaration
	:  #(NTypeMissing initDeclList)
	;



protected
declaration
	:  #( NDeclaration declSpecifiers ( initDeclList )?)
//	:  #( NDeclaration declSpecifiers ( initDeclList )? ( SEMI )+)
	;


protected
declSpecifiers
	:  ( storageClassSpecifier
		| typeQualifier
		| typeSpecifier
		)+
	;

protected
storageClassSpecifier
	:  "auto"
	|  "register"
	|  "typedef"
	|  functionStorageClassSpecifier
	;


protected
functionStorageClassSpecifier
	:  "extern"
	|  "static"
	|  "inline"
	;

protected
typeQualifier
	:  "const"
	|  "volatile"
	;

protected
typeSpecifier
	:  "void"
	|  "char"
	|  "short"
	|  "int"
	|  "long"
	|  "float"
	|  "double"
	|  "signed"
	|  "unsigned"
	|  structSpecifier ( attributeDecl )*
	|  unionSpecifier  ( attributeDecl )*
	|  enumSpecifier
	|  typedefName
	|  #("typeof" LPAREN
				( (typeName )=> typeName
				| expr
				)
				RPAREN
		)
	|  "__complex"
	;

protected
typedefName
	:  #(NTypedefName ID)
	;

protected
structSpecifier
	:   #( "struct" structOrUnionBody )
	;

protected
unionSpecifier
	:   #( "union" structOrUnionBody )
	;

protected
structOrUnionBody
	:  ( (ID LCURLY) => ID LCURLY
			( structDeclarationList )?
			RCURLY
		|   LCURLY
				( structDeclarationList )?
				RCURLY
		| ID
		)
	;
/*
exception
catch [RecognitionException ex]
{
	reportError(ex);
	System.out.println("PROBLEM TREE:\n"+ _t.toStringList());
	if (_t!=null) {_t = _t.getNextSibling();}
}
*/


protected
structDeclarationList
	:  ( structDeclaration )+
	;
/*
exception
catch [RecognitionException ex]
{
	reportError(ex);
	System.out.println("PROBLEM TREE:\n"+ _t.toStringList());
	if (_t!=null) {_t = _t.getNextSibling();}
}
*/



protected
structDeclaration
	:  specifierQualifierList structDeclaratorList
	;
/*
exception
catch [RecognitionException ex]
{
	reportError(ex);
	System.out.println("PROBLEM TREE:\n"+ _t.toStringList());
	if (_t!=null) {_t = _t.getNextSibling();}
}
*/



protected
specifierQualifierList
	:  ( typeSpecifier| typeQualifier )+
	;
/*
exception
catch [RecognitionException ex]
{
	reportError(ex);
	System.out.println("PROBLEM TREE:\n"+ _t.toStringList());
	if (_t!=null) {_t = _t.getNextSibling();}
}
*/



protected
structDeclaratorList
	:  ( structDeclarator )+
	;
/*
exception
catch [RecognitionException ex]
{
	reportError(ex);
	System.out.println("PROBLEM TREE:\n"+ _t.toStringList());
	if (_t!=null) {_t = _t.getNextSibling();}
}
*/



protected
structDeclarator
	:
	#( NStructDeclarator ( declarator )? ( COLON expr )? ( attributeDecl )*
	)
	;
/*
exception
catch [RecognitionException ex]
{
	reportError(ex);
	System.out.println("PROBLEM TREE:\n"+ _t.toStringList());
	if (_t!=null) {_t = _t.getNextSibling();}
}
*/




protected
enumSpecifier
	:   #(  "enum"
		( ID )?
		( LCURLY enumList RCURLY )?
			)
	;


protected
enumList
	:  ( enumerator )+
	;


protected
enumerator
	:  ID ( ASSIGN expr )?
	;



protected
attributeDecl:
	#( "__attribute" (.)* )
	| #( NAsmAttribute LPAREN expr RPAREN )
	;

protected
initDeclList
	:  ( initDecl )+
	;


protected
initDecl
	{ String declName = ""; }
	:  #( NInitDecl	declarator ( attributeDecl )*
				( ASSIGN initializer
		| COLON expr
				)?
			)
	;


protected
pointerGroup
	:  #( NPointerGroup ( STAR ( typeQualifier )* )+ )
	;



protected
idList
	:  ID ( COMMA ID )*
	;



protected
initializer
	:  #( NInitializer (initializerElementLabel)? expr )
		|   lcurlyInitializer
	;

protected
initializerElementLabel
	:   #( NInitializerElementLabel
		(
				( LBRACKET expr RBRACKET (ASSIGN)? )
				| ID COLON
				| DOT ID ASSIGN
		)
			)
	;

protected
lcurlyInitializer
	:  #( NLcurlyInitializer
		initializerList
		RCURLY
			)
	;

protected
initializerList
	:  ( initializer )*
	;


protected
declarator
	:   #( NDeclarator ( pointerGroup )? ( ID | LPAREN declarator RPAREN )
		( #( NParameterTypeList ( parameterTypeList | (idList)? )
					RPAREN )
			| LBRACKET ( expr )? RBRACKET
		)*
			 )
	;



protected
parameterTypeList
//	:  ( parameterDeclaration ( COMMA | SEMI )? )+ ( VARARGS )?
	:  ( parameterDeclaration )* ( VARARGS )?
	;



protected
parameterDeclaration
	:  #( NParameterDeclaration declSpecifiers
		(declarator | nonemptyAbstractDeclarator)?
			)
	;


protected
functionDef
	:   #( NFunctionDef
		( functionDeclSpecifiers)?
		declarator
		(declaration | VARARGS)*
		compoundStatement
			)
	;
/*
exception
catch [RecognitionException ex]
{
	reportError(ex);
	System.out.println("PROBLEM TREE:\n"+ _t.toStringList());
	if (_t!=null) {_t = _t.getNextSibling();}
}
*/

protected
functionDeclSpecifiers
	:
		( functionStorageClassSpecifier
		| typeQualifier
		| typeSpecifier
		)+
	;

protected
declarationList
	:   (   //ANTLR doesn't know that declarationList properly eats all the declarations
				//so it warns about the ambiguity
				options { warnWhenFollowAmbig = false; }
				:  localLabelDecl
				|  declaration
			)+
	;

protected
localLabelDecl
	:   #("__label__" (ID)+ )
	;



protected
compoundStatement
	:  #( NCompoundStatement
		( declarationList
		| functionDef
		)*
		( statementList )?
		RCURLY
		)
	;

protected
statementList
	:  ( statement )+
	;

protected
statement
	:  statementBody
	;

protected
statementBody
	:! SEMI                    // Empty statements

	|  compoundStatement       // Group of statements

	|  #(NStatementExpr expr)                    // Expressions
	|		stat
	;

// other statements
protected
stat
// Iteration statements:
	:      #( "while" e:expr s:statement )
	|      #( "do" s2:statement e2:expr )
	|      #( "for" init:expr cond:expr iter:expr s3:statement)
// Jump statements:

	|  #("goto" expr )
	|  "continue"
	|  "break"
	|  #( "return" ( expr )? )
// Labeled statements:
	|  #( NLabel ID (statement)? )
	|  #( "case" expr (statement)? )
	|  #( "default" (statement)? )
// Selection statements:
	|  #( "if"   expr statement
				( "else" statement )?
		 )
	|  #( "switch" expr statement )
	;

exception
catch [RecognitionException ex]
{
	reportError(ex);
	System.out.println("PROBLEM TREE:\n"+ _t.toStringList());
	if (_t!=null) {_t = _t.getNextSibling();}
}

// aus den Statements extrahierte Sequenz von expressions
protected
exprSeq
	:  ( expr)*
	;



protected
expr
	:  assignExpr
	|  conditionalExpr
	|  logicalOrExpr
	|  logicalAndExpr
	|  inclusiveOrExpr
	|  exclusiveOrExpr
	|  bitAndExpr
	|  equalityExpr
	|  relationalExpr
	|  shiftExpr
	|  additiveExpr
	|  multExpr
	|  castExpr
	|  unaryExpr
	|  postfixExpr
	|  primaryExpr
	|  commaExpr
	|  emptyExpr
	|  compoundStatementExpr
	|  initializer
	|  rangeExpr
	|  gnuAsmExpr
	;

protected
commaExpr
	:   #(NCommaExpr expr expr)
	;

protected
emptyExpr
	:   NEmptyExpression
	;

protected
compoundStatementExpr
	:   #(LPAREN compoundStatement RPAREN)
	;

protected
rangeExpr
	:   #(NRangeExpr expr VARARGS expr)
	;

protected
gnuAsmExpr
	:   #(NGnuAsmExpr
		("volatile")?
		LPAREN stringConst
		( options { warnWhenFollowAmbig = false; }:
			COLON (strOptExprPair ( COMMA strOptExprPair)* )?
			( options { warnWhenFollowAmbig = false; }:
				COLON (strOptExprPair ( COMMA strOptExprPair)* )?
			)?
		)?
		( COLON stringConst ( COMMA stringConst)* )?
		RPAREN
			)
	;

protected
strOptExprPair
	:  stringConst ( LPAREN expr RPAREN )?
	;

protected
assignExpr
	:  #( ASSIGN expr expr)
	|  #( DIV_ASSIGN expr expr)
	|  #( PLUS_ASSIGN expr expr)
	|  #( MINUS_ASSIGN expr expr)
	|  #( STAR_ASSIGN expr expr)
	|  #( MOD_ASSIGN expr expr)
	|  #( RSHIFT_ASSIGN expr expr)
	|  #( LSHIFT_ASSIGN expr expr)
	|  #( BAND_ASSIGN expr expr)
	|  #( BOR_ASSIGN expr expr)
	|  #( BXOR_ASSIGN expr expr)
	;


protected
conditionalExpr
	:  #( QUESTION expr (expr)? COLON! expr )
	;


protected
logicalOrExpr
	:  #( LOR expr expr)
	;


protected
logicalAndExpr
	:  #( LAND expr expr )
	;


protected
inclusiveOrExpr
	:  #( BOR expr expr )
	;


protected
exclusiveOrExpr
	:  #( BXOR expr expr )
	;


protected
bitAndExpr
	:  #( BAND expr expr )
	;



protected
equalityExpr
	:  #( EQUAL expr expr)
	|  #( NOT_EQUAL expr expr)
	;


protected
relationalExpr
	:  #( LT expr expr)
	|  #( LTE expr expr)
	|  #( GT expr expr)
	|  #( GTE expr expr)
	;



protected
shiftExpr
	:  #( LSHIFT expr expr)
		| #( RSHIFT expr expr)
	;


protected
additiveExpr
	:  #( PLUS expr expr)
	|  #( MINUS expr expr)
	;


protected
multExpr
	:  #( STAR expr expr)
	|  #( DIV expr expr)
	|  #( MOD expr expr)
	;



protected
castExpr
	:  #( NCast typeName RPAREN! expr)
	;


protected
typeName
	:  specifierQualifierList (nonemptyAbstractDeclarator)?
	;

protected
nonemptyAbstractDeclarator
	:   #( NNonemptyAbstractDeclarator
			(   pointerGroup
		(   (LPAREN
				(   nonemptyAbstractDeclarator
			| parameterTypeList
				)?
				RPAREN)
		| (LBRACKET (expr)? RBRACKET)
		)*

			|  (   (LPAREN
				(   nonemptyAbstractDeclarator
			| parameterTypeList
				)?
				RPAREN)
		| (LBRACKET (expr)? RBRACKET)
		)+
			)
			)
	;



protected
unaryExpr
	:  #( INC expr )
	|  #( DEC expr )
	|  #( NUnaryExpr unaryOperator expr)
	|  #( "sizeof"
				( ( LPAREN typeName )=> LPAREN typeName RPAREN
				| expr
				)
		)
	|  #( "__alignof"
				( ( LPAREN typeName )=> LPAREN typeName RPAREN
				| expr
				)
		)
	;
/*
exception
catch [RecognitionException ex]
{
	reportError(ex);
	System.out.println("PROBLEM TREE:\n"+ _t.toStringList());
	if (_t!=null) {_t = _t.getNextSibling();}
}
*/

protected
		unaryOperator
	:  BAND
	|  STAR
	|  PLUS
	|  MINUS
	|  BNOT
	|  LNOT
	|  LAND
	|  "__real"
	|  "__imag"
	;


protected
postfixExpr
	:  #( NPostfixExpr
				primaryExpr
				( PTR ID
				| DOT ID
				| #( NFunctionCallArgs (argExprList)? RPAREN )
				| LBRACKET expr RBRACKET
				| INC
				| DEC
				)+
		)
	;



protected
primaryExpr
	:  ID
	|  Number
	|  charConst
	|  stringConst

// JTC:
// ID should catch the enumerator
// leaving it in gives ambiguous err
//      | enumerator

	|  #( NExpressionGroup expr )
	;



protected
argExprList
	:  ( expr )+
	;



protected
charConst
	:  CharLiteral
	;


protected
stringConst
	:  #(NStringSeq (StringLiteral)+)
	;


protected
intConst
	:  IntOctalConst
	|  LongOctalConst
	|  UnsignedOctalConst
	|  IntIntConst
	|  LongIntConst
	|  UnsignedIntConst
	|  IntHexConst
	|  LongHexConst
	|  UnsignedHexConst
	;


protected
floatConst
	:  FloatDoubleConst
	|  DoubleDoubleConst
	|  LongDoubleConst
	;
