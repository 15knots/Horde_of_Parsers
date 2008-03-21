// $ANTLR : "StdCParser.g" -> "StdCParser.java"$

package parser.grammar;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

//++++++++++++++++++++++++++++ Class preamble
import parser.MessageReporter;

//++++++++++++++++++++++++++++ end Class preamble

/**
 * Der Parser fuer Standard/ISO C. Alle anderen Parser fuer die
 * Tessy-Crosscompiler sind von dieser Basisgrammatik abgeleitet.
 *
 *  @see #translationUnit
 */
public class StdCParser extends antlr.LLkParser       implements StdCTokenTypes
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

protected StdCParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public StdCParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected StdCParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public StdCParser(TokenStream lexer) {
  this(lexer,2);
}

public StdCParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void translationUnit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode translationUnit_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_typedef:
			case LITERAL_asm:
			case LITERAL_volatile:
			case LITERAL_auto:
			case LITERAL_register:
			case LITERAL_extern:
			case LITERAL_static:
			case LITERAL_const:
			case LITERAL_void:
			case LITERAL_char:
			case LITERAL_short:
			case LITERAL_int:
			case LITERAL_long:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_signed:
			case LITERAL_unsigned:
			case ID:
			case LITERAL_struct:
			case LITERAL_union:
			case LITERAL_enum:
			case STAR:
			case LPAREN:
			{
				externalList();
				astFactory.addASTChild(currentAST, returnAST);
				translationUnit_AST = (TNode)currentAST.root;
				break;
			}
			case EOF:
			{
				if ( inputState.guessing==0 ) {
					reportError( "empty source file");	
				}
				translationUnit_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = translationUnit_AST;
	}
	
	protected final void externalList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode externalList_AST = null;
		
		try {      // for error handling
			{
			int _cnt679=0;
			_loop679:
			do {
				if ((_tokenSet_1.member(LA(1)))) {
					externalDef();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt679>=1 ) { break _loop679; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt679++;
			} while (true);
			}
			externalList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = externalList_AST;
	}
	
	protected final void externalDef() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode externalDef_AST = null;
		
		try {      // for error handling
			boolean synPredMatched682 = false;
			if (((_tokenSet_2.member(LA(1))) && (_tokenSet_3.member(LA(2))))) {
				int _m682 = mark();
				synPredMatched682 = true;
				inputState.guessing++;
				try {
					{
					if ((LA(1)==LITERAL_typedef) && (true)) {
						match(LITERAL_typedef);
					}
					else if ((_tokenSet_2.member(LA(1))) && (_tokenSet_3.member(LA(2)))) {
						declaration();
					}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					
					}
				}
				catch (RecognitionException pe) {
					synPredMatched682 = false;
				}
				rewind(_m682);
				inputState.guessing--;
			}
			if ( synPredMatched682 ) {
				declaration();
				astFactory.addASTChild(currentAST, returnAST);
				externalDef_AST = (TNode)currentAST.root;
			}
			else if ((_tokenSet_4.member(LA(1))) && (_tokenSet_5.member(LA(2)))) {
				functionDef();
				astFactory.addASTChild(currentAST, returnAST);
				externalDef_AST = (TNode)currentAST.root;
			}
			else if ((LA(1)==LITERAL_asm)) {
				asm_expr();
				astFactory.addASTChild(currentAST, returnAST);
				externalDef_AST = (TNode)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_6);
			} else {
			  throw ex;
			}
		}
		returnAST = externalDef_AST;
	}
	
	protected final void declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode declaration_AST = null;
		TNode ds_AST = null;
		AST ds1 = null;
		
		try {      // for error handling
			declSpecifiers();
			ds_AST = (TNode)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				ds1 = astFactory.dupList(ds_AST);
			}
			{
			switch ( LA(1)) {
			case ID:
			case STAR:
			case LPAREN:
			{
				initDeclList(ds1);
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(SEMI);
			if ( inputState.guessing==0 ) {
				declaration_AST = (TNode)currentAST.root;
				declaration_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NDeclaration)).add(declaration_AST));
				currentAST.root = declaration_AST;
				currentAST.child = declaration_AST!=null &&declaration_AST.getFirstChild()!=null ?
					declaration_AST.getFirstChild() : declaration_AST;
				currentAST.advanceChildToEnd();
			}
			declaration_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_7);
			} else {
			  throw ex;
			}
		}
		returnAST = declaration_AST;
	}
	
	protected final void functionDef() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode functionDef_AST = null;
		TNode ds_AST = null;
		TNode d_AST = null;
		String declName;
		
		try {      // for error handling
			{
			boolean synPredMatched769 = false;
			if (((_tokenSet_8.member(LA(1))) && (_tokenSet_9.member(LA(2))))) {
				int _m769 = mark();
				synPredMatched769 = true;
				inputState.guessing++;
				try {
					{
					functionDeclSpecifiers();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched769 = false;
				}
				rewind(_m769);
				inputState.guessing--;
			}
			if ( synPredMatched769 ) {
				functionDeclSpecifiers();
				ds_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_10.member(LA(1))) && (_tokenSet_5.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			declName=declarator(true);
			d_AST = (TNode)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				
									AST d2, ds2;
									d2 = astFactory.dupList(d_AST);
									ds2 = astFactory.dupList(ds_AST);
									symbolTable.add(declName, (TNode)astFactory.make( (new ASTArray(3)).add(null).add(ds2).add(d2)));
									pushScope(declName);
									
			}
			{
			_loop771:
			do {
				if ((_tokenSet_2.member(LA(1)))) {
					declaration();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop771;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case VARARGS:
			{
				TNode tmp2_AST = null;
				tmp2_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp2_AST);
				match(VARARGS);
				break;
			}
			case LCURLY:
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			_loop774:
			do {
				if ((LA(1)==SEMI)) {
					match(SEMI);
				}
				else {
					break _loop774;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				popScope();
			}
			compoundStatement(declName);
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				functionDef_AST = (TNode)currentAST.root;
				functionDef_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NFunctionDef)).add(functionDef_AST));
				currentAST.root = functionDef_AST;
				currentAST.child = functionDef_AST!=null &&functionDef_AST.getFirstChild()!=null ?
					functionDef_AST.getFirstChild() : functionDef_AST;
				currentAST.advanceChildToEnd();
			}
			functionDef_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_6);
			} else {
			  throw ex;
			}
		}
		returnAST = functionDef_AST;
	}
	
	protected final void asm_expr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode asm_expr_AST = null;
		
		try {      // for error handling
			TNode tmp4_AST = null;
			tmp4_AST = (TNode)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp4_AST);
			match(LITERAL_asm);
			{
			switch ( LA(1)) {
			case LITERAL_volatile:
			{
				TNode tmp5_AST = null;
				tmp5_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp5_AST);
				match(LITERAL_volatile);
				break;
			}
			case LCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LCURLY);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(RCURLY);
			match(SEMI);
			asm_expr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_6);
			} else {
			  throw ex;
			}
		}
		returnAST = asm_expr_AST;
	}
	
	protected final void expr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode expr_AST = null;
		Token  c = null;
		TNode c_AST = null;
		
		try {      // for error handling
			assignExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop804:
			do {
				if ((LA(1)==COMMA) && (_tokenSet_11.member(LA(2)))) {
					c = LT(1);
					c_AST = (TNode)astFactory.create(c);
					astFactory.makeASTRoot(currentAST, c_AST);
					match(COMMA);
					if ( inputState.guessing==0 ) {
						c_AST.setType(NCommaExpr);
					}
					assignExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop804;
				}
				
			} while (true);
			}
			expr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = expr_AST;
	}
	
	protected final void declSpecifiers() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode declSpecifiers_AST = null;
		TNode s_AST = null;
		int specCount=0;
		
		try {      // for error handling
			{
			int _cnt691=0;
			_loop691:
			do {
				switch ( LA(1)) {
				case LITERAL_typedef:
				case LITERAL_auto:
				case LITERAL_register:
				case LITERAL_extern:
				case LITERAL_static:
				{
					storageClassSpecifier();
					s_AST = (TNode)returnAST;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case LITERAL_volatile:
				case LITERAL_const:
				{
					typeQualifier();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				default:
					boolean synPredMatched690 = false;
					if (((_tokenSet_13.member(LA(1))) && (_tokenSet_14.member(LA(2))))) {
						int _m690 = mark();
						synPredMatched690 = true;
						inputState.guessing++;
						try {
							{
							typeSpecifier(specCount);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched690 = false;
						}
						rewind(_m690);
						inputState.guessing--;
					}
					if ( synPredMatched690 ) {
						specCount=typeSpecifier(specCount);
						astFactory.addASTChild(currentAST, returnAST);
					}
				else {
					if ( _cnt691>=1 ) { break _loop691; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				}
				_cnt691++;
			} while (true);
			}
			declSpecifiers_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		returnAST = declSpecifiers_AST;
	}
	
	protected final void initDeclList(
		AST declarationSpecifiers
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode initDeclList_AST = null;
		
		try {      // for error handling
			initDecl(declarationSpecifiers);
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop731:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					initDecl(declarationSpecifiers);
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop731;
				}
				
			} while (true);
			}
			initDeclList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_16);
			} else {
			  throw ex;
			}
		}
		returnAST = initDeclList_AST;
	}
	
	protected final void storageClassSpecifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode storageClassSpecifier_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_auto:
			{
				TNode tmp10_AST = null;
				tmp10_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp10_AST);
				match(LITERAL_auto);
				storageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_register:
			{
				TNode tmp11_AST = null;
				tmp11_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp11_AST);
				match(LITERAL_register);
				storageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_typedef:
			{
				TNode tmp12_AST = null;
				tmp12_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp12_AST);
				match(LITERAL_typedef);
				storageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_extern:
			case LITERAL_static:
			{
				functionStorageClassSpecifier();
				astFactory.addASTChild(currentAST, returnAST);
				storageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_17);
			} else {
			  throw ex;
			}
		}
		returnAST = storageClassSpecifier_AST;
	}
	
	protected final void typeQualifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode typeQualifier_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_const:
			{
				TNode tmp13_AST = null;
				tmp13_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp13_AST);
				match(LITERAL_const);
				typeQualifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_volatile:
			{
				TNode tmp14_AST = null;
				tmp14_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp14_AST);
				match(LITERAL_volatile);
				typeQualifier_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = typeQualifier_AST;
	}
	
	protected final int  typeSpecifier(
		int specCount
	) throws RecognitionException, TokenStreamException {
		int retSpecCount;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode typeSpecifier_AST = null;
		retSpecCount = specCount + 1;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_void:
			{
				TNode tmp15_AST = null;
				tmp15_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp15_AST);
				match(LITERAL_void);
				break;
			}
			case LITERAL_char:
			{
				TNode tmp16_AST = null;
				tmp16_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp16_AST);
				match(LITERAL_char);
				break;
			}
			case LITERAL_short:
			{
				TNode tmp17_AST = null;
				tmp17_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp17_AST);
				match(LITERAL_short);
				break;
			}
			case LITERAL_int:
			{
				TNode tmp18_AST = null;
				tmp18_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp18_AST);
				match(LITERAL_int);
				break;
			}
			case LITERAL_long:
			{
				TNode tmp19_AST = null;
				tmp19_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp19_AST);
				match(LITERAL_long);
				break;
			}
			case LITERAL_float:
			{
				TNode tmp20_AST = null;
				tmp20_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp20_AST);
				match(LITERAL_float);
				break;
			}
			case LITERAL_double:
			{
				TNode tmp21_AST = null;
				tmp21_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp21_AST);
				match(LITERAL_double);
				break;
			}
			case LITERAL_signed:
			{
				TNode tmp22_AST = null;
				tmp22_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp22_AST);
				match(LITERAL_signed);
				break;
			}
			case LITERAL_unsigned:
			{
				TNode tmp23_AST = null;
				tmp23_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp23_AST);
				match(LITERAL_unsigned);
				break;
			}
			case LITERAL_struct:
			case LITERAL_union:
			{
				structOrUnionSpecifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_enum:
			{
				enumSpecifier();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
				if (((LA(1)==ID))&&( specCount == 0 )) {
					typedefName();
					astFactory.addASTChild(currentAST, returnAST);
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			typeSpecifier_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = typeSpecifier_AST;
		return retSpecCount;
	}
	
	protected final void functionStorageClassSpecifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode functionStorageClassSpecifier_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_extern:
			{
				TNode tmp24_AST = null;
				tmp24_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp24_AST);
				match(LITERAL_extern);
				functionStorageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_static:
			{
				TNode tmp25_AST = null;
				tmp25_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp25_AST);
				match(LITERAL_static);
				functionStorageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_17);
			} else {
			  throw ex;
			}
		}
		returnAST = functionStorageClassSpecifier_AST;
	}
	
	protected final void structOrUnionSpecifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structOrUnionSpecifier_AST = null;
		TNode sou_AST = null;
		Token  i = null;
		TNode i_AST = null;
		Token  l = null;
		TNode l_AST = null;
		Token  l1 = null;
		TNode l1_AST = null;
		String scopeName;
		
		try {      // for error handling
			structOrUnion();
			sou_AST = (TNode)returnAST;
			{
			boolean synPredMatched701 = false;
			if (((LA(1)==ID) && (LA(2)==LCURLY))) {
				int _m701 = mark();
				synPredMatched701 = true;
				inputState.guessing++;
				try {
					{
					match(ID);
					match(LCURLY);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched701 = false;
				}
				rewind(_m701);
				inputState.guessing--;
			}
			if ( synPredMatched701 ) {
				i = LT(1);
				i_AST = (TNode)astFactory.create(i);
				astFactory.addASTChild(currentAST, i_AST);
				match(ID);
				l = LT(1);
				l_AST = (TNode)astFactory.create(l);
				astFactory.addASTChild(currentAST, l_AST);
				match(LCURLY);
				if ( inputState.guessing==0 ) {
					
												scopeName = sou_AST.getText() + " " + i_AST.getText();
												l_AST.setText(scopeName);
												pushScope(scopeName);
												
				}
				structDeclarationList();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					popScope();
				}
				match(RCURLY);
			}
			else if ((LA(1)==LCURLY)) {
				l1 = LT(1);
				l1_AST = (TNode)astFactory.create(l1);
				astFactory.addASTChild(currentAST, l1_AST);
				match(LCURLY);
				if ( inputState.guessing==0 ) {
					
												scopeName = getAScopeName();
												l1_AST.setText(scopeName);
												pushScope(scopeName);
												
				}
				structDeclarationList();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					popScope();
				}
				match(RCURLY);
			}
			else if ((LA(1)==ID) && (_tokenSet_18.member(LA(2)))) {
				TNode tmp28_AST = null;
				tmp28_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp28_AST);
				match(ID);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				structOrUnionSpecifier_AST = (TNode)currentAST.root;
				
											structOrUnionSpecifier_AST = (TNode)astFactory.make( (new ASTArray(2)).add(sou_AST).add(structOrUnionSpecifier_AST));
											
				currentAST.root = structOrUnionSpecifier_AST;
				currentAST.child = structOrUnionSpecifier_AST!=null &&structOrUnionSpecifier_AST.getFirstChild()!=null ?
					structOrUnionSpecifier_AST.getFirstChild() : structOrUnionSpecifier_AST;
				currentAST.advanceChildToEnd();
			}
			structOrUnionSpecifier_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = structOrUnionSpecifier_AST;
	}
	
	protected final void enumSpecifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode enumSpecifier_AST = null;
		Token  i = null;
		TNode i_AST = null;
		
		try {      // for error handling
			TNode tmp29_AST = null;
			tmp29_AST = (TNode)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp29_AST);
			match(LITERAL_enum);
			{
			boolean synPredMatched723 = false;
			if (((LA(1)==ID) && (LA(2)==LCURLY))) {
				int _m723 = mark();
				synPredMatched723 = true;
				inputState.guessing++;
				try {
					{
					match(ID);
					match(LCURLY);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched723 = false;
				}
				rewind(_m723);
				inputState.guessing--;
			}
			if ( synPredMatched723 ) {
				i = LT(1);
				i_AST = (TNode)astFactory.create(i);
				astFactory.addASTChild(currentAST, i_AST);
				match(ID);
				TNode tmp30_AST = null;
				tmp30_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp30_AST);
				match(LCURLY);
				enumList(i.getText());
				astFactory.addASTChild(currentAST, returnAST);
				match(RCURLY);
			}
			else if ((LA(1)==LCURLY)) {
				TNode tmp32_AST = null;
				tmp32_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp32_AST);
				match(LCURLY);
				enumList("anonymous");
				astFactory.addASTChild(currentAST, returnAST);
				match(RCURLY);
			}
			else if ((LA(1)==ID) && (_tokenSet_18.member(LA(2)))) {
				TNode tmp34_AST = null;
				tmp34_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp34_AST);
				match(ID);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			enumSpecifier_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = enumSpecifier_AST;
	}
	
	protected final void typedefName() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode typedefName_AST = null;
		Token  i = null;
		TNode i_AST = null;
		
		try {      // for error handling
			if (!( isTypedefName ( LT(1).getText() ) ))
			  throw new SemanticException(" isTypedefName ( LT(1).getText() ) ");
			i = LT(1);
			i_AST = (TNode)astFactory.create(i);
			astFactory.addASTChild(currentAST, i_AST);
			match(ID);
			if ( inputState.guessing==0 ) {
				typedefName_AST = (TNode)currentAST.root;
				typedefName_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NTypedefName)).add(i_AST));
				currentAST.root = typedefName_AST;
				currentAST.child = typedefName_AST!=null &&typedefName_AST.getFirstChild()!=null ?
					typedefName_AST.getFirstChild() : typedefName_AST;
				currentAST.advanceChildToEnd();
			}
			typedefName_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = typedefName_AST;
	}
	
	protected final void structOrUnion() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structOrUnion_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_struct:
			{
				TNode tmp35_AST = null;
				tmp35_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp35_AST);
				match(LITERAL_struct);
				structOrUnion_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_union:
			{
				TNode tmp36_AST = null;
				tmp36_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp36_AST);
				match(LITERAL_union);
				structOrUnion_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		returnAST = structOrUnion_AST;
	}
	
	protected final void structDeclarationList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structDeclarationList_AST = null;
		
		try {      // for error handling
			{
			int _cnt705=0;
			_loop705:
			do {
				if ((_tokenSet_20.member(LA(1)))) {
					structDeclaration();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt705>=1 ) { break _loop705; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt705++;
			} while (true);
			}
			structDeclarationList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = structDeclarationList_AST;
	}
	
	protected final void structDeclaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structDeclaration_AST = null;
		
		try {      // for error handling
			specifierQualifierList();
			astFactory.addASTChild(currentAST, returnAST);
			structDeclaratorList();
			astFactory.addASTChild(currentAST, returnAST);
			{
			int _cnt708=0;
			_loop708:
			do {
				if ((LA(1)==SEMI)) {
					match(SEMI);
				}
				else {
					if ( _cnt708>=1 ) { break _loop708; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt708++;
			} while (true);
			}
			structDeclaration_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		returnAST = structDeclaration_AST;
	}
	
	protected final void specifierQualifierList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode specifierQualifierList_AST = null;
		int specCount = 0;
		
		try {      // for error handling
			{
			int _cnt713=0;
			_loop713:
			do {
				boolean synPredMatched712 = false;
				if (((_tokenSet_13.member(LA(1))) && (_tokenSet_23.member(LA(2))))) {
					int _m712 = mark();
					synPredMatched712 = true;
					inputState.guessing++;
					try {
						{
						typeSpecifier(specCount);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched712 = false;
					}
					rewind(_m712);
					inputState.guessing--;
				}
				if ( synPredMatched712 ) {
					specCount=typeSpecifier(specCount);
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==LITERAL_volatile||LA(1)==LITERAL_const)) {
					typeQualifier();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt713>=1 ) { break _loop713; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt713++;
			} while (true);
			}
			specifierQualifierList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		returnAST = specifierQualifierList_AST;
	}
	
	protected final void structDeclaratorList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structDeclaratorList_AST = null;
		
		try {      // for error handling
			structDeclarator();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop716:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					structDeclarator();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop716;
				}
				
			} while (true);
			}
			structDeclaratorList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_16);
			} else {
			  throw ex;
			}
		}
		returnAST = structDeclaratorList_AST;
	}
	
	protected final void structDeclarator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structDeclarator_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case COLON:
			{
				TNode tmp39_AST = null;
				tmp39_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp39_AST);
				match(COLON);
				constExpr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case ID:
			case STAR:
			case LPAREN:
			{
				declarator(false);
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case COLON:
				{
					TNode tmp40_AST = null;
					tmp40_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp40_AST);
					match(COLON);
					constExpr();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case SEMI:
				case COMMA:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				structDeclarator_AST = (TNode)currentAST.root;
				structDeclarator_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NStructDeclarator)).add(structDeclarator_AST));
				currentAST.root = structDeclarator_AST;
				currentAST.child = structDeclarator_AST!=null &&structDeclarator_AST.getFirstChild()!=null ?
					structDeclarator_AST.getFirstChild() : structDeclarator_AST;
				currentAST.advanceChildToEnd();
			}
			structDeclarator_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		returnAST = structDeclarator_AST;
	}
	
	protected final void constExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode constExpr_AST = null;
		
		try {      // for error handling
			conditionalExpr();
			astFactory.addASTChild(currentAST, returnAST);
			constExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		returnAST = constExpr_AST;
	}
	
	protected final String  declarator(
		boolean isFunctionDefinition
	) throws RecognitionException, TokenStreamException {
		String declName;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode declarator_AST = null;
		Token  id = null;
		TNode id_AST = null;
		TNode p_AST = null;
		TNode i_AST = null;
		declName = "";
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case STAR:
			{
				pointerGroup();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case ID:
			case LPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case ID:
			{
				id = LT(1);
				id_AST = (TNode)astFactory.create(id);
				astFactory.addASTChild(currentAST, id_AST);
				match(ID);
				if ( inputState.guessing==0 ) {
					declName = id.getText();
				}
				break;
			}
			case LPAREN:
			{
				TNode tmp41_AST = null;
				tmp41_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp41_AST);
				match(LPAREN);
				declName=declarator(false);
				astFactory.addASTChild(currentAST, returnAST);
				TNode tmp42_AST = null;
				tmp42_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp42_AST);
				match(RPAREN);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			_loop757:
			do {
				switch ( LA(1)) {
				case LPAREN:
				{
					TNode tmp43_AST = null;
					tmp43_AST = (TNode)astFactory.create(LT(1));
					match(LPAREN);
					if ( inputState.guessing==0 ) {
						
									if (isFunctionDefinition) {
											pushScope(declName);
									}
									else {
											pushScope("!"+declName);
									}
										
					}
					{
					boolean synPredMatched754 = false;
					if (((_tokenSet_2.member(LA(1))) && (_tokenSet_27.member(LA(2))))) {
						int _m754 = mark();
						synPredMatched754 = true;
						inputState.guessing++;
						try {
							{
							declSpecifiers();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched754 = false;
						}
						rewind(_m754);
						inputState.guessing--;
					}
					if ( synPredMatched754 ) {
						parameterTypeList();
						p_AST = (TNode)returnAST;
						if ( inputState.guessing==0 ) {
							declarator_AST = (TNode)currentAST.root;
							
											declarator_AST = (TNode)astFactory.make( (new ASTArray(3)).add(null).add(declarator_AST).add((TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NParameterTypeList)).add(p_AST))));
										
							currentAST.root = declarator_AST;
							currentAST.child = declarator_AST!=null &&declarator_AST.getFirstChild()!=null ?
								declarator_AST.getFirstChild() : declarator_AST;
							currentAST.advanceChildToEnd();
						}
					}
					else if ((LA(1)==ID||LA(1)==RPAREN) && (_tokenSet_28.member(LA(2)))) {
						{
						switch ( LA(1)) {
						case ID:
						{
							idList();
							i_AST = (TNode)returnAST;
							break;
						}
						case RPAREN:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						if ( inputState.guessing==0 ) {
							declarator_AST = (TNode)currentAST.root;
							
											declarator_AST = (TNode)astFactory.make( (new ASTArray(3)).add(null).add(declarator_AST).add((TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NParameterTypeList)).add(i_AST))));
										
							currentAST.root = declarator_AST;
							currentAST.child = declarator_AST!=null &&declarator_AST.getFirstChild()!=null ?
								declarator_AST.getFirstChild() : declarator_AST;
							currentAST.advanceChildToEnd();
						}
					}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					
					}
					if ( inputState.guessing==0 ) {
						
								popScope();
										
					}
					TNode tmp44_AST = null;
					tmp44_AST = (TNode)astFactory.create(LT(1));
					match(RPAREN);
					break;
				}
				case LBRACKET:
				{
					TNode tmp45_AST = null;
					tmp45_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp45_AST);
					match(LBRACKET);
					{
					switch ( LA(1)) {
					case ID:
					case STAR:
					case LPAREN:
					case BAND:
					case PLUS:
					case MINUS:
					case INC:
					case DEC:
					case LITERAL_sizeof:
					case BNOT:
					case LNOT:
					case CharLiteral:
					case StringLiteral:
					case IntOctalConst:
					case LongOctalConst:
					case UnsignedOctalConst:
					case IntIntConst:
					case LongIntConst:
					case UnsignedIntConst:
					case IntHexConst:
					case LongHexConst:
					case UnsignedHexConst:
					case FloatDoubleConst:
					case DoubleDoubleConst:
					case LongDoubleConst:
					{
						constExpr();
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case RBRACKET:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					TNode tmp46_AST = null;
					tmp46_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp46_AST);
					match(RBRACKET);
					break;
				}
				default:
				{
					break _loop757;
				}
				}
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				declarator_AST = (TNode)currentAST.root;
				declarator_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NDeclarator)).add(declarator_AST));
				currentAST.root = declarator_AST;
				currentAST.child = declarator_AST!=null &&declarator_AST.getFirstChild()!=null ?
					declarator_AST.getFirstChild() : declarator_AST;
				currentAST.advanceChildToEnd();
			}
			declarator_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_29);
			} else {
			  throw ex;
			}
		}
		returnAST = declarator_AST;
		return declName;
	}
	
	protected final void enumList(
		String enumName
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode enumList_AST = null;
		
		try {      // for error handling
			enumerator(enumName);
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop726:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					enumerator(enumName);
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop726;
				}
				
			} while (true);
			}
			enumList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = enumList_AST;
	}
	
	protected final void enumerator(
		String enumName
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode enumerator_AST = null;
		Token  i = null;
		TNode i_AST = null;
		
		try {      // for error handling
			i = LT(1);
			i_AST = (TNode)astFactory.create(i);
			astFactory.addASTChild(currentAST, i_AST);
			match(ID);
			if ( inputState.guessing==0 ) {
				symbolTable.add(  i.getText(),
											(TNode)astFactory.make( (new ASTArray(3)).add(null).add((TNode)astFactory.create(LITERAL_enum,"enum")).add((TNode)astFactory.create(ID,enumName)))
												 );
										
			}
			{
			switch ( LA(1)) {
			case ASSIGN:
			{
				TNode tmp48_AST = null;
				tmp48_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp48_AST);
				match(ASSIGN);
				constExpr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RCURLY:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			enumerator_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_30);
			} else {
			  throw ex;
			}
		}
		returnAST = enumerator_AST;
	}
	
	protected final void initDecl(
		AST declarationSpecifiers
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode initDecl_AST = null;
		TNode d_AST = null;
		String declName = "";
		
		try {      // for error handling
			declName=declarator(false);
			d_AST = (TNode)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				AST ds1, d1;
											ds1 = astFactory.dupList(declarationSpecifiers);
											d1 = astFactory.dupList(d_AST);
											symbolTable.add(declName, (TNode)astFactory.make( (new ASTArray(3)).add(null).add(ds1).add(d1)) );
									
			}
			{
			switch ( LA(1)) {
			case ASSIGN:
			{
				TNode tmp49_AST = null;
				tmp49_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp49_AST);
				match(ASSIGN);
				initializer();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case COLON:
			{
				TNode tmp50_AST = null;
				tmp50_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp50_AST);
				match(COLON);
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				initDecl_AST = (TNode)currentAST.root;
				initDecl_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NInitDecl)).add(initDecl_AST));
				currentAST.root = initDecl_AST;
				currentAST.child = initDecl_AST!=null &&initDecl_AST.getFirstChild()!=null ?
					initDecl_AST.getFirstChild() : initDecl_AST;
				currentAST.advanceChildToEnd();
			}
			initDecl_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		returnAST = initDecl_AST;
	}
	
	protected final void initializer() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode initializer_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ID:
			case STAR:
			case LPAREN:
			case BAND:
			case PLUS:
			case MINUS:
			case INC:
			case DEC:
			case LITERAL_sizeof:
			case BNOT:
			case LNOT:
			case CharLiteral:
			case StringLiteral:
			case IntOctalConst:
			case LongOctalConst:
			case UnsignedOctalConst:
			case IntIntConst:
			case LongIntConst:
			case UnsignedIntConst:
			case IntHexConst:
			case LongHexConst:
			case UnsignedHexConst:
			case FloatDoubleConst:
			case DoubleDoubleConst:
			case LongDoubleConst:
			{
				assignExpr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LCURLY:
			{
				TNode tmp51_AST = null;
				tmp51_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp51_AST);
				match(LCURLY);
				initializerList();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case COMMA:
				{
					match(COMMA);
					break;
				}
				case RCURLY:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(RCURLY);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				initializer_AST = (TNode)currentAST.root;
				initializer_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NInitializer)).add(initializer_AST));
				currentAST.root = initializer_AST;
				currentAST.child = initializer_AST!=null &&initializer_AST.getFirstChild()!=null ?
					initializer_AST.getFirstChild() : initializer_AST;
				currentAST.advanceChildToEnd();
			}
			initializer_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_31);
			} else {
			  throw ex;
			}
		}
		returnAST = initializer_AST;
	}
	
	protected final void pointerGroup() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode pointerGroup_AST = null;
		
		try {      // for error handling
			{
			int _cnt738=0;
			_loop738:
			do {
				if ((LA(1)==STAR)) {
					TNode tmp54_AST = null;
					tmp54_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp54_AST);
					match(STAR);
					{
					_loop737:
					do {
						if ((LA(1)==LITERAL_volatile||LA(1)==LITERAL_const)) {
							typeQualifier();
							astFactory.addASTChild(currentAST, returnAST);
						}
						else {
							break _loop737;
						}
						
					} while (true);
					}
				}
				else {
					if ( _cnt738>=1 ) { break _loop738; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt738++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				pointerGroup_AST = (TNode)currentAST.root;
				pointerGroup_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NPointerGroup)).add(pointerGroup_AST));
				currentAST.root = pointerGroup_AST;
				currentAST.child = pointerGroup_AST!=null &&pointerGroup_AST.getFirstChild()!=null ?
					pointerGroup_AST.getFirstChild() : pointerGroup_AST;
				currentAST.advanceChildToEnd();
			}
			pointerGroup_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		returnAST = pointerGroup_AST;
	}
	
	protected final void idList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode idList_AST = null;
		
		try {      // for error handling
			TNode tmp55_AST = null;
			tmp55_AST = (TNode)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp55_AST);
			match(ID);
			{
			_loop741:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					TNode tmp57_AST = null;
					tmp57_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp57_AST);
					match(ID);
				}
				else {
					break _loop741;
				}
				
			} while (true);
			}
			idList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		returnAST = idList_AST;
	}
	
	protected final void assignExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode assignExpr_AST = null;
		TNode a_AST = null;
		
		try {      // for error handling
			conditionalExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case ASSIGN:
			case DIV_ASSIGN:
			case PLUS_ASSIGN:
			case MINUS_ASSIGN:
			case STAR_ASSIGN:
			case MOD_ASSIGN:
			case RSHIFT_ASSIGN:
			case LSHIFT_ASSIGN:
			case BAND_ASSIGN:
			case BOR_ASSIGN:
			case BXOR_ASSIGN:
			{
				assignOperator();
				a_AST = (TNode)returnAST;
				assignExpr();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					assignExpr_AST = (TNode)currentAST.root;
					assignExpr_AST = (TNode)astFactory.make( (new ASTArray(2)).add(a_AST).add(assignExpr_AST));
					currentAST.root = assignExpr_AST;
					currentAST.child = assignExpr_AST!=null &&assignExpr_AST.getFirstChild()!=null ?
						assignExpr_AST.getFirstChild() : assignExpr_AST;
					currentAST.advanceChildToEnd();
				}
				break;
			}
			case RCURLY:
			case SEMI:
			case COMMA:
			case COLON:
			case RPAREN:
			case RBRACKET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			assignExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = assignExpr_AST;
	}
	
	protected final void initializerList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode initializerList_AST = null;
		
		try {      // for error handling
			initializer();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop747:
			do {
				if ((LA(1)==COMMA) && (_tokenSet_34.member(LA(2)))) {
					match(COMMA);
					initializer();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop747;
				}
				
			} while (true);
			}
			initializerList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_30);
			} else {
			  throw ex;
			}
		}
		returnAST = initializerList_AST;
	}
	
	protected final void parameterTypeList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode parameterTypeList_AST = null;
		
		try {      // for error handling
			parameterDeclaration();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop760:
			do {
				if ((LA(1)==COMMA) && (_tokenSet_2.member(LA(2)))) {
					match(COMMA);
					parameterDeclaration();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop760;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case COMMA:
			{
				match(COMMA);
				TNode tmp61_AST = null;
				tmp61_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp61_AST);
				match(VARARGS);
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			parameterTypeList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		returnAST = parameterTypeList_AST;
	}
	
	protected final void parameterDeclaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode parameterDeclaration_AST = null;
		TNode ds_AST = null;
		TNode d_AST = null;
		String declName;
		
		try {      // for error handling
			declSpecifiers();
			ds_AST = (TNode)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			{
			boolean synPredMatched765 = false;
			if (((_tokenSet_10.member(LA(1))) && (_tokenSet_35.member(LA(2))))) {
				int _m765 = mark();
				synPredMatched765 = true;
				inputState.guessing++;
				try {
					{
					declarator(false);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched765 = false;
				}
				rewind(_m765);
				inputState.guessing--;
			}
			if ( synPredMatched765 ) {
				declName=declarator(false);
				d_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					
									AST d2, ds2;
									d2 = astFactory.dupList(d_AST);
									ds2 = astFactory.dupList(ds_AST);
									symbolTable.add(declName, (TNode)astFactory.make( (new ASTArray(3)).add(null).add(ds2).add(d2)));
								
				}
			}
			else if ((_tokenSet_36.member(LA(1))) && (_tokenSet_37.member(LA(2)))) {
				nonemptyAbstractDeclarator();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((LA(1)==COMMA||LA(1)==RPAREN)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				parameterDeclaration_AST = (TNode)currentAST.root;
				
								parameterDeclaration_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NParameterDeclaration)).add(parameterDeclaration_AST));
							
				currentAST.root = parameterDeclaration_AST;
				currentAST.child = parameterDeclaration_AST!=null &&parameterDeclaration_AST.getFirstChild()!=null ?
					parameterDeclaration_AST.getFirstChild() : parameterDeclaration_AST;
				currentAST.advanceChildToEnd();
			}
			parameterDeclaration_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_38);
			} else {
			  throw ex;
			}
		}
		returnAST = parameterDeclaration_AST;
	}
	
	protected final void nonemptyAbstractDeclarator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode nonemptyAbstractDeclarator_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case STAR:
			{
				pointerGroup();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop859:
				do {
					switch ( LA(1)) {
					case LPAREN:
					{
						{
						TNode tmp62_AST = null;
						tmp62_AST = (TNode)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp62_AST);
						match(LPAREN);
						{
						switch ( LA(1)) {
						case STAR:
						case LPAREN:
						case LBRACKET:
						{
							nonemptyAbstractDeclarator();
							astFactory.addASTChild(currentAST, returnAST);
							break;
						}
						case LITERAL_typedef:
						case LITERAL_volatile:
						case LITERAL_auto:
						case LITERAL_register:
						case LITERAL_extern:
						case LITERAL_static:
						case LITERAL_const:
						case LITERAL_void:
						case LITERAL_char:
						case LITERAL_short:
						case LITERAL_int:
						case LITERAL_long:
						case LITERAL_float:
						case LITERAL_double:
						case LITERAL_signed:
						case LITERAL_unsigned:
						case ID:
						case LITERAL_struct:
						case LITERAL_union:
						case LITERAL_enum:
						{
							parameterTypeList();
							astFactory.addASTChild(currentAST, returnAST);
							break;
						}
						case RPAREN:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						TNode tmp63_AST = null;
						tmp63_AST = (TNode)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp63_AST);
						match(RPAREN);
						}
						break;
					}
					case LBRACKET:
					{
						{
						TNode tmp64_AST = null;
						tmp64_AST = (TNode)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp64_AST);
						match(LBRACKET);
						{
						switch ( LA(1)) {
						case ID:
						case STAR:
						case LPAREN:
						case BAND:
						case PLUS:
						case MINUS:
						case INC:
						case DEC:
						case LITERAL_sizeof:
						case BNOT:
						case LNOT:
						case CharLiteral:
						case StringLiteral:
						case IntOctalConst:
						case LongOctalConst:
						case UnsignedOctalConst:
						case IntIntConst:
						case LongIntConst:
						case UnsignedIntConst:
						case IntHexConst:
						case LongHexConst:
						case UnsignedHexConst:
						case FloatDoubleConst:
						case DoubleDoubleConst:
						case LongDoubleConst:
						{
							expr();
							astFactory.addASTChild(currentAST, returnAST);
							break;
						}
						case RBRACKET:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						TNode tmp65_AST = null;
						tmp65_AST = (TNode)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp65_AST);
						match(RBRACKET);
						}
						break;
					}
					default:
					{
						break _loop859;
					}
					}
				} while (true);
				}
				break;
			}
			case LPAREN:
			case LBRACKET:
			{
				{
				int _cnt865=0;
				_loop865:
				do {
					switch ( LA(1)) {
					case LPAREN:
					{
						{
						TNode tmp66_AST = null;
						tmp66_AST = (TNode)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp66_AST);
						match(LPAREN);
						{
						switch ( LA(1)) {
						case STAR:
						case LPAREN:
						case LBRACKET:
						{
							nonemptyAbstractDeclarator();
							astFactory.addASTChild(currentAST, returnAST);
							break;
						}
						case LITERAL_typedef:
						case LITERAL_volatile:
						case LITERAL_auto:
						case LITERAL_register:
						case LITERAL_extern:
						case LITERAL_static:
						case LITERAL_const:
						case LITERAL_void:
						case LITERAL_char:
						case LITERAL_short:
						case LITERAL_int:
						case LITERAL_long:
						case LITERAL_float:
						case LITERAL_double:
						case LITERAL_signed:
						case LITERAL_unsigned:
						case ID:
						case LITERAL_struct:
						case LITERAL_union:
						case LITERAL_enum:
						{
							parameterTypeList();
							astFactory.addASTChild(currentAST, returnAST);
							break;
						}
						case RPAREN:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						TNode tmp67_AST = null;
						tmp67_AST = (TNode)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp67_AST);
						match(RPAREN);
						}
						break;
					}
					case LBRACKET:
					{
						{
						TNode tmp68_AST = null;
						tmp68_AST = (TNode)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp68_AST);
						match(LBRACKET);
						{
						switch ( LA(1)) {
						case ID:
						case STAR:
						case LPAREN:
						case BAND:
						case PLUS:
						case MINUS:
						case INC:
						case DEC:
						case LITERAL_sizeof:
						case BNOT:
						case LNOT:
						case CharLiteral:
						case StringLiteral:
						case IntOctalConst:
						case LongOctalConst:
						case UnsignedOctalConst:
						case IntIntConst:
						case LongIntConst:
						case UnsignedIntConst:
						case IntHexConst:
						case LongHexConst:
						case UnsignedHexConst:
						case FloatDoubleConst:
						case DoubleDoubleConst:
						case LongDoubleConst:
						{
							expr();
							astFactory.addASTChild(currentAST, returnAST);
							break;
						}
						case RBRACKET:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						TNode tmp69_AST = null;
						tmp69_AST = (TNode)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp69_AST);
						match(RBRACKET);
						}
						break;
					}
					default:
					{
						if ( _cnt865>=1 ) { break _loop865; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					}
					_cnt865++;
				} while (true);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				nonemptyAbstractDeclarator_AST = (TNode)currentAST.root;
				nonemptyAbstractDeclarator_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NNonemptyAbstractDeclarator)).add(nonemptyAbstractDeclarator_AST));
				currentAST.root = nonemptyAbstractDeclarator_AST;
				currentAST.child = nonemptyAbstractDeclarator_AST!=null &&nonemptyAbstractDeclarator_AST.getFirstChild()!=null ?
					nonemptyAbstractDeclarator_AST.getFirstChild() : nonemptyAbstractDeclarator_AST;
				currentAST.advanceChildToEnd();
			}
			nonemptyAbstractDeclarator_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_38);
			} else {
			  throw ex;
			}
		}
		returnAST = nonemptyAbstractDeclarator_AST;
	}
	
	protected final void functionDeclSpecifiers() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode functionDeclSpecifiers_AST = null;
		int specCount = 0;
		
		try {      // for error handling
			{
			int _cnt779=0;
			_loop779:
			do {
				switch ( LA(1)) {
				case LITERAL_extern:
				case LITERAL_static:
				{
					functionStorageClassSpecifier();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case LITERAL_volatile:
				case LITERAL_const:
				{
					typeQualifier();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				default:
					boolean synPredMatched778 = false;
					if (((_tokenSet_13.member(LA(1))) && (_tokenSet_9.member(LA(2))))) {
						int _m778 = mark();
						synPredMatched778 = true;
						inputState.guessing++;
						try {
							{
							typeSpecifier(specCount);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched778 = false;
						}
						rewind(_m778);
						inputState.guessing--;
					}
					if ( synPredMatched778 ) {
						specCount=typeSpecifier(specCount);
						astFactory.addASTChild(currentAST, returnAST);
					}
				else {
					if ( _cnt779>=1 ) { break _loop779; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				}
				_cnt779++;
			} while (true);
			}
			functionDeclSpecifiers_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = functionDeclSpecifiers_AST;
	}
	
	protected final void compoundStatement(
		String scopeName
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode compoundStatement_AST = null;
		
		try {      // for error handling
			match(LCURLY);
			if ( inputState.guessing==0 ) {
				
								pushScope(scopeName);
									
			}
			{
			boolean synPredMatched790 = false;
			if (((_tokenSet_2.member(LA(1))) && (_tokenSet_3.member(LA(2))))) {
				int _m790 = mark();
				synPredMatched790 = true;
				inputState.guessing++;
				try {
					{
					declarationPredictor();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched790 = false;
				}
				rewind(_m790);
				inputState.guessing--;
			}
			if ( synPredMatched790 ) {
				declarationList();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_39.member(LA(1))) && (_tokenSet_40.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			{
			switch ( LA(1)) {
			case LCURLY:
			case SEMI:
			case ID:
			case STAR:
			case LPAREN:
			case LITERAL_while:
			case LITERAL_do:
			case LITERAL_for:
			case LITERAL_goto:
			case LITERAL_continue:
			case LITERAL_break:
			case LITERAL_return:
			case LITERAL_case:
			case LITERAL_default:
			case LITERAL_if:
			case LITERAL_switch:
			case BAND:
			case PLUS:
			case MINUS:
			case INC:
			case DEC:
			case LITERAL_sizeof:
			case BNOT:
			case LNOT:
			case CharLiteral:
			case StringLiteral:
			case IntOctalConst:
			case LongOctalConst:
			case UnsignedOctalConst:
			case IntIntConst:
			case LongIntConst:
			case UnsignedIntConst:
			case IntHexConst:
			case LongHexConst:
			case UnsignedHexConst:
			case FloatDoubleConst:
			case DoubleDoubleConst:
			case LongDoubleConst:
			{
				statementList();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				popScope();
			}
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				compoundStatement_AST = (TNode)currentAST.root;
				compoundStatement_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NCompoundStatement,scopeName)).add(compoundStatement_AST));
				currentAST.root = compoundStatement_AST;
				currentAST.child = compoundStatement_AST!=null &&compoundStatement_AST.getFirstChild()!=null ?
					compoundStatement_AST.getFirstChild() : compoundStatement_AST;
				currentAST.advanceChildToEnd();
			}
			compoundStatement_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_41);
			} else {
			  throw ex;
			}
		}
		returnAST = compoundStatement_AST;
	}
	
	protected final void declarationList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode declarationList_AST = null;
		
		try {      // for error handling
			{
			int _cnt784=0;
			_loop784:
			do {
				boolean synPredMatched783 = false;
				if (((_tokenSet_2.member(LA(1))) && (_tokenSet_3.member(LA(2))))) {
					int _m783 = mark();
					synPredMatched783 = true;
					inputState.guessing++;
					try {
						{
						declarationPredictor();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched783 = false;
					}
					rewind(_m783);
					inputState.guessing--;
				}
				if ( synPredMatched783 ) {
					declaration();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt784>=1 ) { break _loop784; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt784++;
			} while (true);
			}
			declarationList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_39);
			} else {
			  throw ex;
			}
		}
		returnAST = declarationList_AST;
	}
	
	protected final void declarationPredictor() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode declarationPredictor_AST = null;
		
		try {      // for error handling
			{
			if ((LA(1)==LITERAL_typedef) && (LA(2)==EOF)) {
				TNode tmp72_AST = null;
				tmp72_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp72_AST);
				match(LITERAL_typedef);
			}
			else if ((_tokenSet_2.member(LA(1))) && (_tokenSet_3.member(LA(2)))) {
				declaration();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			declarationPredictor_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = declarationPredictor_AST;
	}
	
	protected final void statementList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode statementList_AST = null;
		
		try {      // for error handling
			{
			int _cnt794=0;
			_loop794:
			do {
				if ((_tokenSet_42.member(LA(1)))) {
					statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt794>=1 ) { break _loop794; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt794++;
			} while (true);
			}
			statementList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = statementList_AST;
	}
	
	protected final void statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode statement_AST = null;
		TNode e1_AST = null;
		TNode e2_AST = null;
		TNode e3_AST = null;
		TNode s_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case SEMI:
			{
				TNode tmp73_AST = null;
				tmp73_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp73_AST);
				match(SEMI);
				statement_AST = (TNode)currentAST.root;
				break;
			}
			case LCURLY:
			{
				compoundStatement(getAScopeName());
				astFactory.addASTChild(currentAST, returnAST);
				statement_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_while:
			{
				TNode tmp74_AST = null;
				tmp74_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp74_AST);
				match(LITERAL_while);
				match(LPAREN);
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
				statement();
				astFactory.addASTChild(currentAST, returnAST);
				statement_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_do:
			{
				TNode tmp77_AST = null;
				tmp77_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp77_AST);
				match(LITERAL_do);
				statement();
				astFactory.addASTChild(currentAST, returnAST);
				match(LITERAL_while);
				match(LPAREN);
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
				match(SEMI);
				statement_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_for:
			{
				match(LITERAL_for);
				TNode tmp83_AST = null;
				tmp83_AST = (TNode)astFactory.create(LT(1));
				match(LPAREN);
				{
				switch ( LA(1)) {
				case ID:
				case STAR:
				case LPAREN:
				case BAND:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case LITERAL_sizeof:
				case BNOT:
				case LNOT:
				case CharLiteral:
				case StringLiteral:
				case IntOctalConst:
				case LongOctalConst:
				case UnsignedOctalConst:
				case IntIntConst:
				case LongIntConst:
				case UnsignedIntConst:
				case IntHexConst:
				case LongHexConst:
				case UnsignedHexConst:
				case FloatDoubleConst:
				case DoubleDoubleConst:
				case LongDoubleConst:
				{
					expr();
					e1_AST = (TNode)returnAST;
					break;
				}
				case SEMI:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				TNode tmp84_AST = null;
				tmp84_AST = (TNode)astFactory.create(LT(1));
				match(SEMI);
				{
				switch ( LA(1)) {
				case ID:
				case STAR:
				case LPAREN:
				case BAND:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case LITERAL_sizeof:
				case BNOT:
				case LNOT:
				case CharLiteral:
				case StringLiteral:
				case IntOctalConst:
				case LongOctalConst:
				case UnsignedOctalConst:
				case IntIntConst:
				case LongIntConst:
				case UnsignedIntConst:
				case IntHexConst:
				case LongHexConst:
				case UnsignedHexConst:
				case FloatDoubleConst:
				case DoubleDoubleConst:
				case LongDoubleConst:
				{
					expr();
					e2_AST = (TNode)returnAST;
					break;
				}
				case SEMI:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				TNode tmp85_AST = null;
				tmp85_AST = (TNode)astFactory.create(LT(1));
				match(SEMI);
				{
				switch ( LA(1)) {
				case ID:
				case STAR:
				case LPAREN:
				case BAND:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case LITERAL_sizeof:
				case BNOT:
				case LNOT:
				case CharLiteral:
				case StringLiteral:
				case IntOctalConst:
				case LongOctalConst:
				case UnsignedOctalConst:
				case IntIntConst:
				case LongIntConst:
				case UnsignedIntConst:
				case IntHexConst:
				case LongHexConst:
				case UnsignedHexConst:
				case FloatDoubleConst:
				case DoubleDoubleConst:
				case LongDoubleConst:
				{
					expr();
					e3_AST = (TNode)returnAST;
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				TNode tmp86_AST = null;
				tmp86_AST = (TNode)astFactory.create(LT(1));
				match(RPAREN);
				statement();
				s_AST = (TNode)returnAST;
				if ( inputState.guessing==0 ) {
					statement_AST = (TNode)currentAST.root;
					
										if ( e1_AST == null) { e1_AST = (TNode)astFactory.create(NEmptyExpression); }
										if ( e2_AST == null) { e2_AST = (TNode)astFactory.create(NEmptyExpression); }
										if ( e3_AST == null) { e3_AST = (TNode)astFactory.create(NEmptyExpression); }
										statement_AST = (TNode)astFactory.make( (new ASTArray(5)).add((TNode)astFactory.create(LITERAL_for,"for")).add(e1_AST).add(e2_AST).add(e3_AST).add(s_AST));
											
					currentAST.root = statement_AST;
					currentAST.child = statement_AST!=null &&statement_AST.getFirstChild()!=null ?
						statement_AST.getFirstChild() : statement_AST;
					currentAST.advanceChildToEnd();
				}
				break;
			}
			case LITERAL_goto:
			{
				TNode tmp87_AST = null;
				tmp87_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp87_AST);
				match(LITERAL_goto);
				TNode tmp88_AST = null;
				tmp88_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp88_AST);
				match(ID);
				match(SEMI);
				statement_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_continue:
			{
				TNode tmp90_AST = null;
				tmp90_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp90_AST);
				match(LITERAL_continue);
				match(SEMI);
				statement_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_break:
			{
				TNode tmp92_AST = null;
				tmp92_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp92_AST);
				match(LITERAL_break);
				match(SEMI);
				statement_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_return:
			{
				TNode tmp94_AST = null;
				tmp94_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp94_AST);
				match(LITERAL_return);
				{
				switch ( LA(1)) {
				case ID:
				case STAR:
				case LPAREN:
				case BAND:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case LITERAL_sizeof:
				case BNOT:
				case LNOT:
				case CharLiteral:
				case StringLiteral:
				case IntOctalConst:
				case LongOctalConst:
				case UnsignedOctalConst:
				case IntIntConst:
				case LongIntConst:
				case UnsignedIntConst:
				case IntHexConst:
				case LongHexConst:
				case UnsignedHexConst:
				case FloatDoubleConst:
				case DoubleDoubleConst:
				case LongDoubleConst:
				{
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case SEMI:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(SEMI);
				statement_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_case:
			{
				TNode tmp96_AST = null;
				tmp96_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp96_AST);
				match(LITERAL_case);
				constExpr();
				astFactory.addASTChild(currentAST, returnAST);
				match(COLON);
				statement();
				astFactory.addASTChild(currentAST, returnAST);
				statement_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_default:
			{
				TNode tmp98_AST = null;
				tmp98_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp98_AST);
				match(LITERAL_default);
				match(COLON);
				statement();
				astFactory.addASTChild(currentAST, returnAST);
				statement_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_if:
			{
				TNode tmp100_AST = null;
				tmp100_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp100_AST);
				match(LITERAL_if);
				match(LPAREN);
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
				statement();
				astFactory.addASTChild(currentAST, returnAST);
				{
				if ((LA(1)==LITERAL_else) && (_tokenSet_42.member(LA(2)))) {
					TNode tmp103_AST = null;
					tmp103_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp103_AST);
					match(LITERAL_else);
					statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((_tokenSet_43.member(LA(1))) && (_tokenSet_40.member(LA(2)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				statement_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_switch:
			{
				TNode tmp104_AST = null;
				tmp104_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp104_AST);
				match(LITERAL_switch);
				match(LPAREN);
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
				statement();
				astFactory.addASTChild(currentAST, returnAST);
				statement_AST = (TNode)currentAST.root;
				break;
			}
			default:
				if ((_tokenSet_11.member(LA(1))) && (_tokenSet_44.member(LA(2)))) {
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					match(SEMI);
					if ( inputState.guessing==0 ) {
						statement_AST = (TNode)currentAST.root;
						statement_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NStatementExpr)).add(statement_AST));
						currentAST.root = statement_AST;
						currentAST.child = statement_AST!=null &&statement_AST.getFirstChild()!=null ?
							statement_AST.getFirstChild() : statement_AST;
						currentAST.advanceChildToEnd();
					}
					statement_AST = (TNode)currentAST.root;
				}
				else if ((LA(1)==ID) && (LA(2)==COLON)) {
					TNode tmp108_AST = null;
					tmp108_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp108_AST);
					match(ID);
					match(COLON);
					{
					if ((_tokenSet_42.member(LA(1))) && (_tokenSet_45.member(LA(2)))) {
						statement();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else if ((_tokenSet_43.member(LA(1))) && (_tokenSet_40.member(LA(2)))) {
					}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					
					}
					if ( inputState.guessing==0 ) {
						statement_AST = (TNode)currentAST.root;
						statement_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NLabel)).add(statement_AST));
						currentAST.root = statement_AST;
						currentAST.child = statement_AST!=null &&statement_AST.getFirstChild()!=null ?
							statement_AST.getFirstChild() : statement_AST;
						currentAST.advanceChildToEnd();
					}
					statement_AST = (TNode)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_43);
			} else {
			  throw ex;
			}
		}
		returnAST = statement_AST;
	}
	
	protected final void conditionalExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode conditionalExpr_AST = null;
		
		try {      // for error handling
			logicalOrExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case QUESTION:
			{
				TNode tmp110_AST = null;
				tmp110_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp110_AST);
				match(QUESTION);
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				match(COLON);
				conditionalExpr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RCURLY:
			case SEMI:
			case COMMA:
			case COLON:
			case ASSIGN:
			case RPAREN:
			case RBRACKET:
			case DIV_ASSIGN:
			case PLUS_ASSIGN:
			case MINUS_ASSIGN:
			case STAR_ASSIGN:
			case MOD_ASSIGN:
			case RSHIFT_ASSIGN:
			case LSHIFT_ASSIGN:
			case BAND_ASSIGN:
			case BOR_ASSIGN:
			case BXOR_ASSIGN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			conditionalExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_46);
			} else {
			  throw ex;
			}
		}
		returnAST = conditionalExpr_AST;
	}
	
	protected final void assignOperator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode assignOperator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ASSIGN:
			{
				TNode tmp112_AST = null;
				tmp112_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp112_AST);
				match(ASSIGN);
				assignOperator_AST = (TNode)currentAST.root;
				break;
			}
			case DIV_ASSIGN:
			{
				TNode tmp113_AST = null;
				tmp113_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp113_AST);
				match(DIV_ASSIGN);
				assignOperator_AST = (TNode)currentAST.root;
				break;
			}
			case PLUS_ASSIGN:
			{
				TNode tmp114_AST = null;
				tmp114_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp114_AST);
				match(PLUS_ASSIGN);
				assignOperator_AST = (TNode)currentAST.root;
				break;
			}
			case MINUS_ASSIGN:
			{
				TNode tmp115_AST = null;
				tmp115_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp115_AST);
				match(MINUS_ASSIGN);
				assignOperator_AST = (TNode)currentAST.root;
				break;
			}
			case STAR_ASSIGN:
			{
				TNode tmp116_AST = null;
				tmp116_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp116_AST);
				match(STAR_ASSIGN);
				assignOperator_AST = (TNode)currentAST.root;
				break;
			}
			case MOD_ASSIGN:
			{
				TNode tmp117_AST = null;
				tmp117_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp117_AST);
				match(MOD_ASSIGN);
				assignOperator_AST = (TNode)currentAST.root;
				break;
			}
			case RSHIFT_ASSIGN:
			{
				TNode tmp118_AST = null;
				tmp118_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp118_AST);
				match(RSHIFT_ASSIGN);
				assignOperator_AST = (TNode)currentAST.root;
				break;
			}
			case LSHIFT_ASSIGN:
			{
				TNode tmp119_AST = null;
				tmp119_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp119_AST);
				match(LSHIFT_ASSIGN);
				assignOperator_AST = (TNode)currentAST.root;
				break;
			}
			case BAND_ASSIGN:
			{
				TNode tmp120_AST = null;
				tmp120_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp120_AST);
				match(BAND_ASSIGN);
				assignOperator_AST = (TNode)currentAST.root;
				break;
			}
			case BOR_ASSIGN:
			{
				TNode tmp121_AST = null;
				tmp121_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp121_AST);
				match(BOR_ASSIGN);
				assignOperator_AST = (TNode)currentAST.root;
				break;
			}
			case BXOR_ASSIGN:
			{
				TNode tmp122_AST = null;
				tmp122_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp122_AST);
				match(BXOR_ASSIGN);
				assignOperator_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		returnAST = assignOperator_AST;
	}
	
	protected final void logicalOrExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode logicalOrExpr_AST = null;
		
		try {      // for error handling
			logicalAndExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop813:
			do {
				if ((LA(1)==LOR)) {
					TNode tmp123_AST = null;
					tmp123_AST = (TNode)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp123_AST);
					match(LOR);
					logicalAndExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop813;
				}
				
			} while (true);
			}
			logicalOrExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_47);
			} else {
			  throw ex;
			}
		}
		returnAST = logicalOrExpr_AST;
	}
	
	protected final void logicalAndExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode logicalAndExpr_AST = null;
		
		try {      // for error handling
			inclusiveOrExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop816:
			do {
				if ((LA(1)==LAND)) {
					TNode tmp124_AST = null;
					tmp124_AST = (TNode)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp124_AST);
					match(LAND);
					inclusiveOrExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop816;
				}
				
			} while (true);
			}
			logicalAndExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		returnAST = logicalAndExpr_AST;
	}
	
	protected final void inclusiveOrExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode inclusiveOrExpr_AST = null;
		
		try {      // for error handling
			exclusiveOrExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop819:
			do {
				if ((LA(1)==BOR)) {
					TNode tmp125_AST = null;
					tmp125_AST = (TNode)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp125_AST);
					match(BOR);
					exclusiveOrExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop819;
				}
				
			} while (true);
			}
			inclusiveOrExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_49);
			} else {
			  throw ex;
			}
		}
		returnAST = inclusiveOrExpr_AST;
	}
	
	protected final void exclusiveOrExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode exclusiveOrExpr_AST = null;
		
		try {      // for error handling
			bitAndExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop822:
			do {
				if ((LA(1)==BXOR)) {
					TNode tmp126_AST = null;
					tmp126_AST = (TNode)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp126_AST);
					match(BXOR);
					bitAndExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop822;
				}
				
			} while (true);
			}
			exclusiveOrExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_50);
			} else {
			  throw ex;
			}
		}
		returnAST = exclusiveOrExpr_AST;
	}
	
	protected final void bitAndExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode bitAndExpr_AST = null;
		
		try {      // for error handling
			equalityExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop825:
			do {
				if ((LA(1)==BAND)) {
					TNode tmp127_AST = null;
					tmp127_AST = (TNode)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp127_AST);
					match(BAND);
					equalityExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop825;
				}
				
			} while (true);
			}
			bitAndExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_51);
			} else {
			  throw ex;
			}
		}
		returnAST = bitAndExpr_AST;
	}
	
	protected final void equalityExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode equalityExpr_AST = null;
		
		try {      // for error handling
			relationalExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop829:
			do {
				if ((LA(1)==EQUAL||LA(1)==NOT_EQUAL)) {
					{
					switch ( LA(1)) {
					case EQUAL:
					{
						TNode tmp128_AST = null;
						tmp128_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp128_AST);
						match(EQUAL);
						break;
					}
					case NOT_EQUAL:
					{
						TNode tmp129_AST = null;
						tmp129_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp129_AST);
						match(NOT_EQUAL);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					relationalExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop829;
				}
				
			} while (true);
			}
			equalityExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_52);
			} else {
			  throw ex;
			}
		}
		returnAST = equalityExpr_AST;
	}
	
	protected final void relationalExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode relationalExpr_AST = null;
		
		try {      // for error handling
			shiftExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop833:
			do {
				if (((LA(1) >= LT && LA(1) <= GTE))) {
					{
					switch ( LA(1)) {
					case LT:
					{
						TNode tmp130_AST = null;
						tmp130_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp130_AST);
						match(LT);
						break;
					}
					case LTE:
					{
						TNode tmp131_AST = null;
						tmp131_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp131_AST);
						match(LTE);
						break;
					}
					case GT:
					{
						TNode tmp132_AST = null;
						tmp132_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp132_AST);
						match(GT);
						break;
					}
					case GTE:
					{
						TNode tmp133_AST = null;
						tmp133_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp133_AST);
						match(GTE);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					shiftExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop833;
				}
				
			} while (true);
			}
			relationalExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_53);
			} else {
			  throw ex;
			}
		}
		returnAST = relationalExpr_AST;
	}
	
	protected final void shiftExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode shiftExpr_AST = null;
		
		try {      // for error handling
			additiveExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop837:
			do {
				if ((LA(1)==LSHIFT||LA(1)==RSHIFT)) {
					{
					switch ( LA(1)) {
					case LSHIFT:
					{
						TNode tmp134_AST = null;
						tmp134_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp134_AST);
						match(LSHIFT);
						break;
					}
					case RSHIFT:
					{
						TNode tmp135_AST = null;
						tmp135_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp135_AST);
						match(RSHIFT);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					additiveExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop837;
				}
				
			} while (true);
			}
			shiftExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_54);
			} else {
			  throw ex;
			}
		}
		returnAST = shiftExpr_AST;
	}
	
	protected final void additiveExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode additiveExpr_AST = null;
		
		try {      // for error handling
			multExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop841:
			do {
				if ((LA(1)==PLUS||LA(1)==MINUS)) {
					{
					switch ( LA(1)) {
					case PLUS:
					{
						TNode tmp136_AST = null;
						tmp136_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp136_AST);
						match(PLUS);
						break;
					}
					case MINUS:
					{
						TNode tmp137_AST = null;
						tmp137_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp137_AST);
						match(MINUS);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					multExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop841;
				}
				
			} while (true);
			}
			additiveExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		returnAST = additiveExpr_AST;
	}
	
	protected final void multExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode multExpr_AST = null;
		
		try {      // for error handling
			castExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop845:
			do {
				if ((_tokenSet_56.member(LA(1)))) {
					{
					switch ( LA(1)) {
					case STAR:
					{
						TNode tmp138_AST = null;
						tmp138_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp138_AST);
						match(STAR);
						break;
					}
					case DIV:
					{
						TNode tmp139_AST = null;
						tmp139_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp139_AST);
						match(DIV);
						break;
					}
					case MOD:
					{
						TNode tmp140_AST = null;
						tmp140_AST = (TNode)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp140_AST);
						match(MOD);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					castExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop845;
				}
				
			} while (true);
			}
			multExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_57);
			} else {
			  throw ex;
			}
		}
		returnAST = multExpr_AST;
	}
	
	protected final void castExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode castExpr_AST = null;
		
		try {      // for error handling
			boolean synPredMatched848 = false;
			if (((LA(1)==LPAREN) && (_tokenSet_20.member(LA(2))))) {
				int _m848 = mark();
				synPredMatched848 = true;
				inputState.guessing++;
				try {
					{
					match(LPAREN);
					typeName();
					match(RPAREN);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched848 = false;
				}
				rewind(_m848);
				inputState.guessing--;
			}
			if ( synPredMatched848 ) {
				match(LPAREN);
				typeName();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
				{
				castExpr();
				astFactory.addASTChild(currentAST, returnAST);
				}
				if ( inputState.guessing==0 ) {
					castExpr_AST = (TNode)currentAST.root;
					castExpr_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NCast,"(")).add(castExpr_AST));
					currentAST.root = castExpr_AST;
					currentAST.child = castExpr_AST!=null &&castExpr_AST.getFirstChild()!=null ?
						castExpr_AST.getFirstChild() : castExpr_AST;
					currentAST.advanceChildToEnd();
				}
				castExpr_AST = (TNode)currentAST.root;
			}
			else if ((_tokenSet_11.member(LA(1))) && (_tokenSet_58.member(LA(2)))) {
				unaryExpr();
				astFactory.addASTChild(currentAST, returnAST);
				castExpr_AST = (TNode)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		returnAST = castExpr_AST;
	}
	
	protected final void typeName() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode typeName_AST = null;
		
		try {      // for error handling
			specifierQualifierList();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case STAR:
			case LPAREN:
			case LBRACKET:
			{
				nonemptyAbstractDeclarator();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			typeName_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		returnAST = typeName_AST;
	}
	
	protected final void unaryExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode unaryExpr_AST = null;
		TNode u_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ID:
			case LPAREN:
			case CharLiteral:
			case StringLiteral:
			case IntOctalConst:
			case LongOctalConst:
			case UnsignedOctalConst:
			case IntIntConst:
			case LongIntConst:
			case UnsignedIntConst:
			case IntHexConst:
			case LongHexConst:
			case UnsignedHexConst:
			case FloatDoubleConst:
			case DoubleDoubleConst:
			case LongDoubleConst:
			{
				postfixExpr();
				astFactory.addASTChild(currentAST, returnAST);
				unaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case INC:
			{
				TNode tmp143_AST = null;
				tmp143_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp143_AST);
				match(INC);
				unaryExpr();
				astFactory.addASTChild(currentAST, returnAST);
				unaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case DEC:
			{
				TNode tmp144_AST = null;
				tmp144_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp144_AST);
				match(DEC);
				unaryExpr();
				astFactory.addASTChild(currentAST, returnAST);
				unaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case STAR:
			case BAND:
			case PLUS:
			case MINUS:
			case BNOT:
			case LNOT:
			{
				unaryOperator();
				u_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				castExpr();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					unaryExpr_AST = (TNode)currentAST.root;
					unaryExpr_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NUnaryExpr)).add(unaryExpr_AST));
					currentAST.root = unaryExpr_AST;
					currentAST.child = unaryExpr_AST!=null &&unaryExpr_AST.getFirstChild()!=null ?
						unaryExpr_AST.getFirstChild() : unaryExpr_AST;
					currentAST.advanceChildToEnd();
				}
				unaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_sizeof:
			{
				TNode tmp145_AST = null;
				tmp145_AST = (TNode)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp145_AST);
				match(LITERAL_sizeof);
				{
				boolean synPredMatched869 = false;
				if (((LA(1)==LPAREN) && (_tokenSet_20.member(LA(2))))) {
					int _m869 = mark();
					synPredMatched869 = true;
					inputState.guessing++;
					try {
						{
						match(LPAREN);
						typeName();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched869 = false;
					}
					rewind(_m869);
					inputState.guessing--;
				}
				if ( synPredMatched869 ) {
					TNode tmp146_AST = null;
					tmp146_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp146_AST);
					match(LPAREN);
					typeName();
					astFactory.addASTChild(currentAST, returnAST);
					TNode tmp147_AST = null;
					tmp147_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp147_AST);
					match(RPAREN);
				}
				else if ((_tokenSet_11.member(LA(1))) && (_tokenSet_58.member(LA(2)))) {
					unaryExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				unaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		returnAST = unaryExpr_AST;
	}
	
	protected final void postfixExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode postfixExpr_AST = null;
		
		try {      // for error handling
			primaryExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case LPAREN:
			case LBRACKET:
			case INC:
			case DEC:
			case PTR:
			case DOT:
			{
				postfixSuffix();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					postfixExpr_AST = (TNode)currentAST.root;
					postfixExpr_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NPostfixExpr)).add(postfixExpr_AST));
					currentAST.root = postfixExpr_AST;
					currentAST.child = postfixExpr_AST!=null &&postfixExpr_AST.getFirstChild()!=null ?
						postfixExpr_AST.getFirstChild() : postfixExpr_AST;
					currentAST.advanceChildToEnd();
				}
				break;
			}
			case RCURLY:
			case SEMI:
			case COMMA:
			case COLON:
			case ASSIGN:
			case STAR:
			case RPAREN:
			case RBRACKET:
			case DIV_ASSIGN:
			case PLUS_ASSIGN:
			case MINUS_ASSIGN:
			case STAR_ASSIGN:
			case MOD_ASSIGN:
			case RSHIFT_ASSIGN:
			case LSHIFT_ASSIGN:
			case BAND_ASSIGN:
			case BOR_ASSIGN:
			case BXOR_ASSIGN:
			case QUESTION:
			case LOR:
			case LAND:
			case BOR:
			case BXOR:
			case BAND:
			case EQUAL:
			case NOT_EQUAL:
			case LT:
			case LTE:
			case GT:
			case GTE:
			case LSHIFT:
			case RSHIFT:
			case PLUS:
			case MINUS:
			case DIV:
			case MOD:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			postfixExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		returnAST = postfixExpr_AST;
	}
	
	protected final void unaryOperator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode unaryOperator_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case BAND:
			{
				TNode tmp148_AST = null;
				tmp148_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp148_AST);
				match(BAND);
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case STAR:
			{
				TNode tmp149_AST = null;
				tmp149_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp149_AST);
				match(STAR);
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case PLUS:
			{
				TNode tmp150_AST = null;
				tmp150_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp150_AST);
				match(PLUS);
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case MINUS:
			{
				TNode tmp151_AST = null;
				tmp151_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp151_AST);
				match(MINUS);
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case BNOT:
			{
				TNode tmp152_AST = null;
				tmp152_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp152_AST);
				match(BNOT);
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case LNOT:
			{
				TNode tmp153_AST = null;
				tmp153_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp153_AST);
				match(LNOT);
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		returnAST = unaryOperator_AST;
	}
	
	protected final void primaryExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode primaryExpr_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ID:
			{
				TNode tmp154_AST = null;
				tmp154_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp154_AST);
				match(ID);
				primaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case CharLiteral:
			{
				charConst();
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case IntOctalConst:
			case LongOctalConst:
			case UnsignedOctalConst:
			case IntIntConst:
			case LongIntConst:
			case UnsignedIntConst:
			case IntHexConst:
			case LongHexConst:
			case UnsignedHexConst:
			{
				intConst();
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case FloatDoubleConst:
			case DoubleDoubleConst:
			case LongDoubleConst:
			{
				floatConst();
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case StringLiteral:
			{
				stringConst();
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case LPAREN:
			{
				match(LPAREN);
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
				if ( inputState.guessing==0 ) {
					primaryExpr_AST = (TNode)currentAST.root;
					primaryExpr_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NExpressionGroup,"(")).add(primaryExpr_AST));
					currentAST.root = primaryExpr_AST;
					currentAST.child = primaryExpr_AST!=null &&primaryExpr_AST.getFirstChild()!=null ?
						primaryExpr_AST.getFirstChild() : primaryExpr_AST;
					currentAST.advanceChildToEnd();
				}
				primaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_60);
			} else {
			  throw ex;
			}
		}
		returnAST = primaryExpr_AST;
	}
	
	protected final void postfixSuffix() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode postfixSuffix_AST = null;
		
		try {      // for error handling
			{
			int _cnt875=0;
			_loop875:
			do {
				switch ( LA(1)) {
				case PTR:
				{
					TNode tmp157_AST = null;
					tmp157_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp157_AST);
					match(PTR);
					TNode tmp158_AST = null;
					tmp158_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp158_AST);
					match(ID);
					break;
				}
				case DOT:
				{
					TNode tmp159_AST = null;
					tmp159_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp159_AST);
					match(DOT);
					TNode tmp160_AST = null;
					tmp160_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp160_AST);
					match(ID);
					break;
				}
				case LPAREN:
				{
					functionCall();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case LBRACKET:
				{
					TNode tmp161_AST = null;
					tmp161_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp161_AST);
					match(LBRACKET);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					TNode tmp162_AST = null;
					tmp162_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp162_AST);
					match(RBRACKET);
					break;
				}
				case INC:
				{
					TNode tmp163_AST = null;
					tmp163_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp163_AST);
					match(INC);
					break;
				}
				case DEC:
				{
					TNode tmp164_AST = null;
					tmp164_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp164_AST);
					match(DEC);
					break;
				}
				default:
				{
					if ( _cnt875>=1 ) { break _loop875; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				}
				_cnt875++;
			} while (true);
			}
			postfixSuffix_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		returnAST = postfixSuffix_AST;
	}
	
	protected final void functionCall() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode functionCall_AST = null;
		TNode a_AST = null;
		
		try {      // for error handling
			TNode tmp165_AST = null;
			tmp165_AST = (TNode)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp165_AST);
			match(LPAREN);
			{
			switch ( LA(1)) {
			case ID:
			case STAR:
			case LPAREN:
			case BAND:
			case PLUS:
			case MINUS:
			case INC:
			case DEC:
			case LITERAL_sizeof:
			case BNOT:
			case LNOT:
			case CharLiteral:
			case StringLiteral:
			case IntOctalConst:
			case LongOctalConst:
			case UnsignedOctalConst:
			case IntIntConst:
			case LongIntConst:
			case UnsignedIntConst:
			case IntHexConst:
			case LongHexConst:
			case UnsignedHexConst:
			case FloatDoubleConst:
			case DoubleDoubleConst:
			case LongDoubleConst:
			{
				argExprList();
				a_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			TNode tmp166_AST = null;
			tmp166_AST = (TNode)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp166_AST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				functionCall_AST = (TNode)currentAST.root;
				
							functionCall_AST.setType( NFunctionCallArgs );
							
			}
			functionCall_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_60);
			} else {
			  throw ex;
			}
		}
		returnAST = functionCall_AST;
	}
	
	protected final void argExprList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode argExprList_AST = null;
		
		try {      // for error handling
			assignExpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop881:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					assignExpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop881;
				}
				
			} while (true);
			}
			argExprList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		returnAST = argExprList_AST;
	}
	
	protected final void charConst() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode charConst_AST = null;
		
		try {      // for error handling
			TNode tmp168_AST = null;
			tmp168_AST = (TNode)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp168_AST);
			match(CharLiteral);
			charConst_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_60);
			} else {
			  throw ex;
			}
		}
		returnAST = charConst_AST;
	}
	
	protected final void intConst() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode intConst_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case IntOctalConst:
			{
				TNode tmp169_AST = null;
				tmp169_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp169_AST);
				match(IntOctalConst);
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case LongOctalConst:
			{
				TNode tmp170_AST = null;
				tmp170_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp170_AST);
				match(LongOctalConst);
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case UnsignedOctalConst:
			{
				TNode tmp171_AST = null;
				tmp171_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp171_AST);
				match(UnsignedOctalConst);
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case IntIntConst:
			{
				TNode tmp172_AST = null;
				tmp172_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp172_AST);
				match(IntIntConst);
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case LongIntConst:
			{
				TNode tmp173_AST = null;
				tmp173_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp173_AST);
				match(LongIntConst);
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case UnsignedIntConst:
			{
				TNode tmp174_AST = null;
				tmp174_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp174_AST);
				match(UnsignedIntConst);
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case IntHexConst:
			{
				TNode tmp175_AST = null;
				tmp175_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp175_AST);
				match(IntHexConst);
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case LongHexConst:
			{
				TNode tmp176_AST = null;
				tmp176_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp176_AST);
				match(LongHexConst);
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case UnsignedHexConst:
			{
				TNode tmp177_AST = null;
				tmp177_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp177_AST);
				match(UnsignedHexConst);
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_60);
			} else {
			  throw ex;
			}
		}
		returnAST = intConst_AST;
	}
	
	protected final void floatConst() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode floatConst_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case FloatDoubleConst:
			{
				TNode tmp178_AST = null;
				tmp178_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp178_AST);
				match(FloatDoubleConst);
				floatConst_AST = (TNode)currentAST.root;
				break;
			}
			case DoubleDoubleConst:
			{
				TNode tmp179_AST = null;
				tmp179_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp179_AST);
				match(DoubleDoubleConst);
				floatConst_AST = (TNode)currentAST.root;
				break;
			}
			case LongDoubleConst:
			{
				TNode tmp180_AST = null;
				tmp180_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp180_AST);
				match(LongDoubleConst);
				floatConst_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_60);
			} else {
			  throw ex;
			}
		}
		returnAST = floatConst_AST;
	}
	
	protected final void stringConst() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode stringConst_AST = null;
		
		try {      // for error handling
			{
			int _cnt885=0;
			_loop885:
			do {
				if ((LA(1)==StringLiteral)) {
					TNode tmp181_AST = null;
					tmp181_AST = (TNode)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp181_AST);
					match(StringLiteral);
				}
				else {
					if ( _cnt885>=1 ) { break _loop885; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt885++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				stringConst_AST = (TNode)currentAST.root;
				stringConst_AST = (TNode)astFactory.make( (new ASTArray(2)).add((TNode)astFactory.create(NStringSeq)).add(stringConst_AST));
				currentAST.root = stringConst_AST;
				currentAST.child = stringConst_AST!=null &&stringConst_AST.getFirstChild()!=null ?
					stringConst_AST.getFirstChild() : stringConst_AST;
				currentAST.advanceChildToEnd();
			}
			stringConst_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_60);
			} else {
			  throw ex;
			}
		}
		returnAST = stringConst_AST;
	}
	
	protected final void dummy() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode dummy_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NTypedefName:
			{
				TNode tmp182_AST = null;
				tmp182_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp182_AST);
				match(NTypedefName);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NInitDecl:
			{
				TNode tmp183_AST = null;
				tmp183_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp183_AST);
				match(NInitDecl);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NDeclarator:
			{
				TNode tmp184_AST = null;
				tmp184_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp184_AST);
				match(NDeclarator);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NStructDeclarator:
			{
				TNode tmp185_AST = null;
				tmp185_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp185_AST);
				match(NStructDeclarator);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NDeclaration:
			{
				TNode tmp186_AST = null;
				tmp186_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp186_AST);
				match(NDeclaration);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NCast:
			{
				TNode tmp187_AST = null;
				tmp187_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp187_AST);
				match(NCast);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NPointerGroup:
			{
				TNode tmp188_AST = null;
				tmp188_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp188_AST);
				match(NPointerGroup);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NExpressionGroup:
			{
				TNode tmp189_AST = null;
				tmp189_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp189_AST);
				match(NExpressionGroup);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NFunctionCallArgs:
			{
				TNode tmp190_AST = null;
				tmp190_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp190_AST);
				match(NFunctionCallArgs);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NNonemptyAbstractDeclarator:
			{
				TNode tmp191_AST = null;
				tmp191_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp191_AST);
				match(NNonemptyAbstractDeclarator);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NInitializer:
			{
				TNode tmp192_AST = null;
				tmp192_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp192_AST);
				match(NInitializer);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NStatementExpr:
			{
				TNode tmp193_AST = null;
				tmp193_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp193_AST);
				match(NStatementExpr);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NEmptyExpression:
			{
				TNode tmp194_AST = null;
				tmp194_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp194_AST);
				match(NEmptyExpression);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NParameterTypeList:
			{
				TNode tmp195_AST = null;
				tmp195_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp195_AST);
				match(NParameterTypeList);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NFunctionDef:
			{
				TNode tmp196_AST = null;
				tmp196_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp196_AST);
				match(NFunctionDef);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NCompoundStatement:
			{
				TNode tmp197_AST = null;
				tmp197_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp197_AST);
				match(NCompoundStatement);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NParameterDeclaration:
			{
				TNode tmp198_AST = null;
				tmp198_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp198_AST);
				match(NParameterDeclaration);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NCommaExpr:
			{
				TNode tmp199_AST = null;
				tmp199_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp199_AST);
				match(NCommaExpr);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NUnaryExpr:
			{
				TNode tmp200_AST = null;
				tmp200_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp200_AST);
				match(NUnaryExpr);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NLabel:
			{
				TNode tmp201_AST = null;
				tmp201_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp201_AST);
				match(NLabel);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NPostfixExpr:
			{
				TNode tmp202_AST = null;
				tmp202_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp202_AST);
				match(NPostfixExpr);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NRangeExpr:
			{
				TNode tmp203_AST = null;
				tmp203_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp203_AST);
				match(NRangeExpr);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NStringSeq:
			{
				TNode tmp204_AST = null;
				tmp204_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp204_AST);
				match(NStringSeq);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NInitializerElementLabel:
			{
				TNode tmp205_AST = null;
				tmp205_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp205_AST);
				match(NInitializerElementLabel);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NLcurlyInitializer:
			{
				TNode tmp206_AST = null;
				tmp206_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp206_AST);
				match(NLcurlyInitializer);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NAsmAttribute:
			{
				TNode tmp207_AST = null;
				tmp207_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp207_AST);
				match(NAsmAttribute);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NGnuAsmExpr:
			{
				TNode tmp208_AST = null;
				tmp208_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp208_AST);
				match(NGnuAsmExpr);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			case NTypeMissing:
			{
				TNode tmp209_AST = null;
				tmp209_AST = (TNode)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp209_AST);
				match(NTypeMissing);
				dummy_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = dummy_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"typedef\"",
		"\"asm\"",
		"\"volatile\"",
		"LCURLY",
		"RCURLY",
		"SEMI",
		"\"auto\"",
		"\"register\"",
		"\"extern\"",
		"\"static\"",
		"\"const\"",
		"\"void\"",
		"\"char\"",
		"\"short\"",
		"\"int\"",
		"\"long\"",
		"\"float\"",
		"\"double\"",
		"\"signed\"",
		"\"unsigned\"",
		"an identifier",
		"\"struct\"",
		"\"union\"",
		"COMMA",
		"COLON",
		"\"enum\"",
		"ASSIGN",
		"STAR",
		"LPAREN",
		"RPAREN",
		"LBRACKET",
		"RBRACKET",
		"VARARGS",
		"\"while\"",
		"\"do\"",
		"\"for\"",
		"\"goto\"",
		"\"continue\"",
		"\"break\"",
		"\"return\"",
		"\"case\"",
		"\"default\"",
		"\"if\"",
		"\"else\"",
		"\"switch\"",
		"DIV_ASSIGN",
		"PLUS_ASSIGN",
		"MINUS_ASSIGN",
		"STAR_ASSIGN",
		"MOD_ASSIGN",
		"RSHIFT_ASSIGN",
		"LSHIFT_ASSIGN",
		"BAND_ASSIGN",
		"BOR_ASSIGN",
		"BXOR_ASSIGN",
		"QUESTION",
		"LOR",
		"LAND",
		"BOR",
		"BXOR",
		"BAND",
		"EQUAL",
		"NOT_EQUAL",
		"LT",
		"LTE",
		"GT",
		"GTE",
		"LSHIFT",
		"RSHIFT",
		"PLUS",
		"MINUS",
		"DIV",
		"MOD",
		"INC",
		"DEC",
		"\"sizeof\"",
		"BNOT",
		"LNOT",
		"PTR",
		"DOT",
		"CharLiteral",
		"StringLiteral",
		"IntOctalConst",
		"LongOctalConst",
		"UnsignedOctalConst",
		"IntIntConst",
		"LongIntConst",
		"UnsignedIntConst",
		"IntHexConst",
		"LongHexConst",
		"UnsignedHexConst",
		"FloatDoubleConst",
		"DoubleDoubleConst",
		"LongDoubleConst",
		"NTypedefName",
		"NInitDecl",
		"NDeclarator",
		"NStructDeclarator",
		"NDeclaration",
		"NCast",
		"NPointerGroup",
		"NExpressionGroup",
		"NFunctionCallArgs",
		"NNonemptyAbstractDeclarator",
		"NInitializer",
		"NStatementExpr",
		"NEmptyExpression",
		"NParameterTypeList",
		"NFunctionDef",
		"NCompoundStatement",
		"NParameterDeclaration",
		"NCommaExpr",
		"NUnaryExpr",
		"NLabel",
		"NPostfixExpr",
		"NRangeExpr",
		"NStringSeq",
		"NInitializerElementLabel",
		"NLcurlyInitializer",
		"NAsmAttribute",
		"NGnuAsmExpr",
		"NTypeMissing",
		"Vocabulary",
		"Space",
		"Whitespace",
		"Newline",
		"Comment",
		"CPPComment",
		"a preprocessor directive",
		"a line directive",
		"StringLiteralSingleLine",
		"BadStringLiteral",
		"Escape",
		"Digit",
		"LongSuffix",
		"UnsignedSuffix",
		"FloatSuffix",
		"Exponent",
		"Number",
		"IDletter"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 7113538672L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 671087696L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 7113539280L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 7113535552L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 93012885200L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 7113538674L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 422150859128818L, 17179076097L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 671084608L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 7113535680L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 6459228160L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 6459228160L, 17179076097L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 43352326912L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { 671055872L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 33017560784L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 32363250176L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 512L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 33017560656L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 33285996112L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 16777344L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { 671072320L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = { 256L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = { 671072576L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { 33151762624L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = { 32497467392L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = { 134218240L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = { 34762392320L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = { 33017560272L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = { 100931731152L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = { 79456894672L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = { 134217984L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = { 134218496L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = { 30215766016L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = { 8589934592L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = { 6459228288L, 17179076097L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = { 32363266112L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = { 23622320128L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = { 67377298512L, 17179076097L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = { 8724152320L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = { 422081485341568L, 17179076097L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	private static final long[] mk_tokenSet_40() {
		long[] data = { -111669149710L, 17179869183L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
	private static final long[] mk_tokenSet_41() {
		long[] data = { 562819628007410L, 17179076097L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
	private static final long[] mk_tokenSet_42() {
		long[] data = { 422081485341312L, 17179076097L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_42 = new BitSet(mk_tokenSet_42());
	private static final long[] mk_tokenSet_43() {
		long[] data = { 562818973696896L, 17179076097L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_43 = new BitSet(mk_tokenSet_43());
	private static final long[] mk_tokenSet_44() {
		long[] data = { -562924452068800L, 17179869183L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_44 = new BitSet(mk_tokenSet_44());
	private static final long[] mk_tokenSet_45() {
		long[] data = { -111669149744L, 17179869183L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_45 = new BitSet(mk_tokenSet_45());
	private static final long[] mk_tokenSet_46() {
		long[] data = { 575897846776070912L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_46 = new BitSet(mk_tokenSet_46());
	private static final long[] mk_tokenSet_47() {
		long[] data = { 1152358599079494400L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_47 = new BitSet(mk_tokenSet_47());
	private static final long[] mk_tokenSet_48() {
		long[] data = { 2305280103686341376L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_48 = new BitSet(mk_tokenSet_48());
	private static final long[] mk_tokenSet_49() {
		long[] data = { 4611123112900035328L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_49 = new BitSet(mk_tokenSet_49());
	private static final long[] mk_tokenSet_50() {
		long[] data = { 9222809131327423232L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_50 = new BitSet(mk_tokenSet_50());
	private static final long[] mk_tokenSet_51() {
		long[] data = { -562905527352576L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_51 = new BitSet(mk_tokenSet_51());
	private static final long[] mk_tokenSet_52() {
		long[] data = { -562905527352576L, 1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_52 = new BitSet(mk_tokenSet_52());
	private static final long[] mk_tokenSet_53() {
		long[] data = { -562905527352576L, 7L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_53 = new BitSet(mk_tokenSet_53());
	private static final long[] mk_tokenSet_54() {
		long[] data = { -562905527352576L, 127L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_54 = new BitSet(mk_tokenSet_54());
	private static final long[] mk_tokenSet_55() {
		long[] data = { -562905527352576L, 511L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_55 = new BitSet(mk_tokenSet_55());
	private static final long[] mk_tokenSet_56() {
		long[] data = { 2147483648L, 6144L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_56 = new BitSet(mk_tokenSet_56());
	private static final long[] mk_tokenSet_57() {
		long[] data = { -562905527352576L, 2047L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_57 = new BitSet(mk_tokenSet_57());
	private static final long[] mk_tokenSet_58() {
		long[] data = { -562881888255232L, 17179869183L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_58 = new BitSet(mk_tokenSet_58());
	private static final long[] mk_tokenSet_59() {
		long[] data = { -562903379868928L, 8191L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_59 = new BitSet(mk_tokenSet_59());
	private static final long[] mk_tokenSet_60() {
		long[] data = { -562881905032448L, 819199L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_60 = new BitSet(mk_tokenSet_60());
	
	}
