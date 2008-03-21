// $ANTLR : "ExtractPreparer.g" -> "ExtractPreparer.java"$

package parser.grammar;

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

//++++++++++++++++++++++++++++ Class preamble

import parser.MessageReporter;
//++++++++++++++++++++++++++++ end Class preamble


/**
 * Bereitet den AST auf das Extrahieren der Schnittstellen vor.
 * ExpressionTree normalisieren/optimieren:
 *   'a->b' => '(*a).b '
 *   'a[b]' => '*(a+b)'
 */
public class ExtractPreparer extends antlr.TreeParser       implements ExtractPreparerTokenTypes
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
public ExtractPreparer() {
	tokenNames = _tokenNames;
}

	public final void translationUnit(AST _t) throws RecognitionException {
		
		TNode translationUnit_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode translationUnit_AST = null;
		
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case NDeclaration:
		case NFunctionDef:
		{
			externalList(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case 3:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		translationUnit_AST = (TNode)currentAST.root;
		returnAST = translationUnit_AST;
		_retTree = _t;
	}
	
	protected final void externalList(AST _t) throws RecognitionException {
		
		TNode externalList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode externalList_AST = null;
		
		try {      // for error handling
			{
			int _cnt1066=0;
			_loop1066:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==NDeclaration||_t.getType()==NFunctionDef)) {
					functions(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt1066>=1 ) { break _loop1066; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt1066++;
			} while (true);
			}
			externalList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = externalList_AST;
		_retTree = _t;
	}
	
	protected final void functions(AST _t) throws RecognitionException {
		
		TNode functions_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode functions_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NFunctionDef:
			{
				AST __t1075 = _t;
				TNode tmp1_AST = null;
				TNode tmp1_AST_in = null;
				tmp1_AST = (TNode)astFactory.create((TNode)_t);
				tmp1_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp1_AST);
				ASTPair __currentAST1075 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NFunctionDef);
				_t = _t.getFirstChild();
				TNode tmp2_AST = null;
				TNode tmp2_AST_in = null;
				tmp2_AST = (TNode)astFactory.create((TNode)_t);
				tmp2_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp2_AST);
				match(_t,NDeclarator);
				_t = _t.getNextSibling();
				compoundStatement(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1075;
				_t = __t1075;
				_t = _t.getNextSibling();
				functions_AST = (TNode)currentAST.root;
				break;
			}
			case NDeclaration:
			{
				AST __t1076 = _t;
				TNode tmp3_AST = null;
				TNode tmp3_AST_in = null;
				tmp3_AST = (TNode)astFactory.create((TNode)_t);
				tmp3_AST_in = (TNode)_t;
				ASTPair __currentAST1076 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NDeclaration);
				_t = _t.getFirstChild();
				{
				_loop1078:
				do {
					if (_t==null) _t=ASTNULL;
					if (((_t.getType() >= LITERAL_typedef && _t.getType() <= LITERAL___imag))) {
						TNode tmp4_AST_in = null;
						if ( _t==null ) throw new MismatchedTokenException();
						_t = _t.getNextSibling();
					}
					else {
						break _loop1078;
					}
					
				} while (true);
				}
				currentAST = __currentAST1076;
				_t = __t1076;
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = functions_AST;
		_retTree = _t;
	}
	
	protected final void functions1(AST _t) throws RecognitionException {
		
		TNode functions1_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode functions1_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NFunctionDef:
			{
				AST __t1068 = _t;
				TNode tmp5_AST = null;
				TNode tmp5_AST_in = null;
				tmp5_AST = (TNode)astFactory.create((TNode)_t);
				tmp5_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp5_AST);
				ASTPair __currentAST1068 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NFunctionDef);
				_t = _t.getFirstChild();
				{
				_loop1070:
				do {
					if (_t==null) _t=ASTNULL;
					if (((_t.getType() >= LITERAL_typedef && _t.getType() <= LITERAL___imag))) {
						TNode tmp6_AST = null;
						TNode tmp6_AST_in = null;
						tmp6_AST = (TNode)astFactory.create((TNode)_t);
						tmp6_AST_in = (TNode)_t;
						astFactory.addASTChild(currentAST, tmp6_AST);
						if ( _t==null ) throw new MismatchedTokenException();
						_t = _t.getNextSibling();
					}
					else {
						break _loop1070;
					}
					
				} while (true);
				}
				currentAST = __currentAST1068;
				_t = __t1068;
				_t = _t.getNextSibling();
				functions1_AST = (TNode)currentAST.root;
				break;
			}
			case NDeclaration:
			{
				AST __t1071 = _t;
				TNode tmp7_AST = null;
				TNode tmp7_AST_in = null;
				tmp7_AST = (TNode)astFactory.create((TNode)_t);
				tmp7_AST_in = (TNode)_t;
				ASTPair __currentAST1071 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NDeclaration);
				_t = _t.getFirstChild();
				{
				_loop1073:
				do {
					if (_t==null) _t=ASTNULL;
					if (((_t.getType() >= LITERAL_typedef && _t.getType() <= LITERAL___imag))) {
						TNode tmp8_AST_in = null;
						if ( _t==null ) throw new MismatchedTokenException();
						_t = _t.getNextSibling();
					}
					else {
						break _loop1073;
					}
					
				} while (true);
				}
				currentAST = __currentAST1071;
				_t = __t1071;
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = functions1_AST;
		_retTree = _t;
	}
	
	protected final void compoundStatement(AST _t) throws RecognitionException {
		
		TNode compoundStatement_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode compoundStatement_AST = null;
		
		try {      // for error handling
			AST __t1205 = _t;
			TNode tmp9_AST = null;
			TNode tmp9_AST_in = null;
			tmp9_AST = (TNode)astFactory.create((TNode)_t);
			tmp9_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp9_AST);
			ASTPair __currentAST1205 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NCompoundStatement);
			_t = _t.getFirstChild();
			{
			_loop1207:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case NDeclaration:
				case LITERAL___label__:
				{
					declarationList(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case NFunctionDef:
				{
					functionDef(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				default:
				{
					break _loop1207;
				}
				}
			} while (true);
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case SEMI:
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
			case NStatementExpr:
			case NCompoundStatement:
			case NLabel:
			{
				statementList(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			TNode tmp10_AST = null;
			TNode tmp10_AST_in = null;
			tmp10_AST = (TNode)astFactory.create((TNode)_t);
			tmp10_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp10_AST);
			match(_t,RCURLY);
			_t = _t.getNextSibling();
			currentAST = __currentAST1205;
			_t = __t1205;
			_t = _t.getNextSibling();
			compoundStatement_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = compoundStatement_AST;
		_retTree = _t;
	}
	
	protected final void externalDef(AST _t) throws RecognitionException {
		
		TNode externalDef_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode externalDef_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NDeclaration:
			{
				declaration(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				externalDef_AST = (TNode)currentAST.root;
				break;
			}
			case NFunctionDef:
			{
				functionDef(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				externalDef_AST = (TNode)currentAST.root;
				break;
			}
			case NTypeMissing:
			{
				typelessDeclaration(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				externalDef_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = externalDef_AST;
		_retTree = _t;
	}
	
	protected final void declaration(AST _t) throws RecognitionException {
		
		TNode declaration_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode declaration_AST = null;
		
		try {      // for error handling
			AST __t1083 = _t;
			TNode tmp11_AST = null;
			TNode tmp11_AST_in = null;
			tmp11_AST = (TNode)astFactory.create((TNode)_t);
			tmp11_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp11_AST);
			ASTPair __currentAST1083 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NDeclaration);
			_t = _t.getFirstChild();
			declSpecifiers(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NInitDecl:
			{
				initDeclList(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			currentAST = __currentAST1083;
			_t = __t1083;
			_t = _t.getNextSibling();
			declaration_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = declaration_AST;
		_retTree = _t;
	}
	
	protected final void functionDef(AST _t) throws RecognitionException {
		
		TNode functionDef_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode functionDef_AST = null;
		
		try {      // for error handling
			AST __t1190 = _t;
			TNode tmp12_AST = null;
			TNode tmp12_AST_in = null;
			tmp12_AST = (TNode)astFactory.create((TNode)_t);
			tmp12_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp12_AST);
			ASTPair __currentAST1190 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NFunctionDef);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LITERAL_volatile:
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
			case LITERAL_struct:
			case LITERAL_union:
			case LITERAL_enum:
			case NTypedefName:
			case LITERAL_inline:
			case LITERAL_typeof:
			case LITERAL___complex:
			{
				functionDeclSpecifiers(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case NDeclarator:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			declarator(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop1193:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case NDeclaration:
				{
					declaration(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case VARARGS:
				{
					TNode tmp13_AST = null;
					TNode tmp13_AST_in = null;
					tmp13_AST = (TNode)astFactory.create((TNode)_t);
					tmp13_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp13_AST);
					match(_t,VARARGS);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					break _loop1193;
				}
				}
			} while (true);
			}
			compoundStatement(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1190;
			_t = __t1190;
			_t = _t.getNextSibling();
			functionDef_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = functionDef_AST;
		_retTree = _t;
	}
	
	protected final void typelessDeclaration(AST _t) throws RecognitionException {
		
		TNode typelessDeclaration_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode typelessDeclaration_AST = null;
		
		try {      // for error handling
			AST __t1081 = _t;
			TNode tmp14_AST = null;
			TNode tmp14_AST_in = null;
			tmp14_AST = (TNode)astFactory.create((TNode)_t);
			tmp14_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp14_AST);
			ASTPair __currentAST1081 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NTypeMissing);
			_t = _t.getFirstChild();
			initDeclList(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1081;
			_t = __t1081;
			_t = _t.getNextSibling();
			typelessDeclaration_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = typelessDeclaration_AST;
		_retTree = _t;
	}
	
	protected final void initDeclList(AST _t) throws RecognitionException {
		
		TNode initDeclList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode initDeclList_AST = null;
		
		try {      // for error handling
			{
			int _cnt1144=0;
			_loop1144:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==NInitDecl)) {
					initDecl(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt1144>=1 ) { break _loop1144; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt1144++;
			} while (true);
			}
			initDeclList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = initDeclList_AST;
		_retTree = _t;
	}
	
	protected final void declSpecifiers(AST _t) throws RecognitionException {
		
		TNode declSpecifiers_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode declSpecifiers_AST = null;
		
		try {      // for error handling
			{
			int _cnt1087=0;
			_loop1087:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_typedef:
				case LITERAL_auto:
				case LITERAL_register:
				case LITERAL_extern:
				case LITERAL_static:
				case LITERAL_inline:
				{
					storageClassSpecifier(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case LITERAL_volatile:
				case LITERAL_const:
				{
					typeQualifier(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case LITERAL_void:
				case LITERAL_char:
				case LITERAL_short:
				case LITERAL_int:
				case LITERAL_long:
				case LITERAL_float:
				case LITERAL_double:
				case LITERAL_signed:
				case LITERAL_unsigned:
				case LITERAL_struct:
				case LITERAL_union:
				case LITERAL_enum:
				case NTypedefName:
				case LITERAL_typeof:
				case LITERAL___complex:
				{
					typeSpecifier(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				default:
				{
					if ( _cnt1087>=1 ) { break _loop1087; } else {throw new NoViableAltException(_t);}
				}
				}
				_cnt1087++;
			} while (true);
			}
			declSpecifiers_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = declSpecifiers_AST;
		_retTree = _t;
	}
	
	protected final void storageClassSpecifier(AST _t) throws RecognitionException {
		
		TNode storageClassSpecifier_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode storageClassSpecifier_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LITERAL_auto:
			{
				TNode tmp15_AST = null;
				TNode tmp15_AST_in = null;
				tmp15_AST = (TNode)astFactory.create((TNode)_t);
				tmp15_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp15_AST);
				match(_t,LITERAL_auto);
				_t = _t.getNextSibling();
				storageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_register:
			{
				TNode tmp16_AST = null;
				TNode tmp16_AST_in = null;
				tmp16_AST = (TNode)astFactory.create((TNode)_t);
				tmp16_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp16_AST);
				match(_t,LITERAL_register);
				_t = _t.getNextSibling();
				storageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_typedef:
			{
				TNode tmp17_AST = null;
				TNode tmp17_AST_in = null;
				tmp17_AST = (TNode)astFactory.create((TNode)_t);
				tmp17_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp17_AST);
				match(_t,LITERAL_typedef);
				_t = _t.getNextSibling();
				storageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_extern:
			case LITERAL_static:
			case LITERAL_inline:
			{
				functionStorageClassSpecifier(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				storageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = storageClassSpecifier_AST;
		_retTree = _t;
	}
	
	protected final void typeQualifier(AST _t) throws RecognitionException {
		
		TNode typeQualifier_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode typeQualifier_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LITERAL_const:
			{
				TNode tmp18_AST = null;
				TNode tmp18_AST_in = null;
				tmp18_AST = (TNode)astFactory.create((TNode)_t);
				tmp18_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp18_AST);
				match(_t,LITERAL_const);
				_t = _t.getNextSibling();
				typeQualifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_volatile:
			{
				TNode tmp19_AST = null;
				TNode tmp19_AST_in = null;
				tmp19_AST = (TNode)astFactory.create((TNode)_t);
				tmp19_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp19_AST);
				match(_t,LITERAL_volatile);
				_t = _t.getNextSibling();
				typeQualifier_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = typeQualifier_AST;
		_retTree = _t;
	}
	
	protected final void typeSpecifier(AST _t) throws RecognitionException {
		
		TNode typeSpecifier_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode typeSpecifier_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LITERAL_void:
			{
				TNode tmp20_AST = null;
				TNode tmp20_AST_in = null;
				tmp20_AST = (TNode)astFactory.create((TNode)_t);
				tmp20_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp20_AST);
				match(_t,LITERAL_void);
				_t = _t.getNextSibling();
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_char:
			{
				TNode tmp21_AST = null;
				TNode tmp21_AST_in = null;
				tmp21_AST = (TNode)astFactory.create((TNode)_t);
				tmp21_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp21_AST);
				match(_t,LITERAL_char);
				_t = _t.getNextSibling();
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_short:
			{
				TNode tmp22_AST = null;
				TNode tmp22_AST_in = null;
				tmp22_AST = (TNode)astFactory.create((TNode)_t);
				tmp22_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp22_AST);
				match(_t,LITERAL_short);
				_t = _t.getNextSibling();
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_int:
			{
				TNode tmp23_AST = null;
				TNode tmp23_AST_in = null;
				tmp23_AST = (TNode)astFactory.create((TNode)_t);
				tmp23_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp23_AST);
				match(_t,LITERAL_int);
				_t = _t.getNextSibling();
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_long:
			{
				TNode tmp24_AST = null;
				TNode tmp24_AST_in = null;
				tmp24_AST = (TNode)astFactory.create((TNode)_t);
				tmp24_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp24_AST);
				match(_t,LITERAL_long);
				_t = _t.getNextSibling();
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_float:
			{
				TNode tmp25_AST = null;
				TNode tmp25_AST_in = null;
				tmp25_AST = (TNode)astFactory.create((TNode)_t);
				tmp25_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp25_AST);
				match(_t,LITERAL_float);
				_t = _t.getNextSibling();
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_double:
			{
				TNode tmp26_AST = null;
				TNode tmp26_AST_in = null;
				tmp26_AST = (TNode)astFactory.create((TNode)_t);
				tmp26_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp26_AST);
				match(_t,LITERAL_double);
				_t = _t.getNextSibling();
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_signed:
			{
				TNode tmp27_AST = null;
				TNode tmp27_AST_in = null;
				tmp27_AST = (TNode)astFactory.create((TNode)_t);
				tmp27_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp27_AST);
				match(_t,LITERAL_signed);
				_t = _t.getNextSibling();
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_unsigned:
			{
				TNode tmp28_AST = null;
				TNode tmp28_AST_in = null;
				tmp28_AST = (TNode)astFactory.create((TNode)_t);
				tmp28_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp28_AST);
				match(_t,LITERAL_unsigned);
				_t = _t.getNextSibling();
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_struct:
			{
				structSpecifier(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop1093:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==NAsmAttribute||_t.getType()==LITERAL___attribute)) {
						attributeDecl(_t);
						_t = _retTree;
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop1093;
					}
					
				} while (true);
				}
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_union:
			{
				unionSpecifier(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop1095:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==NAsmAttribute||_t.getType()==LITERAL___attribute)) {
						attributeDecl(_t);
						_t = _retTree;
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop1095;
					}
					
				} while (true);
				}
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_enum:
			{
				enumSpecifier(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case NTypedefName:
			{
				typedefName(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_typeof:
			{
				AST __t1096 = _t;
				TNode tmp29_AST = null;
				TNode tmp29_AST_in = null;
				tmp29_AST = (TNode)astFactory.create((TNode)_t);
				tmp29_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp29_AST);
				ASTPair __currentAST1096 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_typeof);
				_t = _t.getFirstChild();
				TNode tmp30_AST = null;
				TNode tmp30_AST_in = null;
				tmp30_AST = (TNode)astFactory.create((TNode)_t);
				tmp30_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp30_AST);
				match(_t,LPAREN);
				_t = _t.getNextSibling();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_volatile:
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
				case LITERAL_struct:
				case LITERAL_union:
				case LITERAL_enum:
				case NTypedefName:
				case LITERAL_typeof:
				case LITERAL___complex:
				{
					typeName(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case ID:
				case ASSIGN:
				case STAR:
				case LPAREN:
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
				case INC:
				case DEC:
				case LITERAL_sizeof:
				case CharLiteral:
				case NCast:
				case NExpressionGroup:
				case NInitializer:
				case NEmptyExpression:
				case NCommaExpr:
				case NUnaryExpr:
				case NPostfixExpr:
				case NRangeExpr:
				case NStringSeq:
				case NLcurlyInitializer:
				case NGnuAsmExpr:
				case Number:
				case LITERAL___alignof:
				{
					expr(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				TNode tmp31_AST = null;
				TNode tmp31_AST_in = null;
				tmp31_AST = (TNode)astFactory.create((TNode)_t);
				tmp31_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp31_AST);
				match(_t,RPAREN);
				_t = _t.getNextSibling();
				currentAST = __currentAST1096;
				_t = __t1096;
				_t = _t.getNextSibling();
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL___complex:
			{
				TNode tmp32_AST = null;
				TNode tmp32_AST_in = null;
				tmp32_AST = (TNode)astFactory.create((TNode)_t);
				tmp32_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp32_AST);
				match(_t,LITERAL___complex);
				_t = _t.getNextSibling();
				typeSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = typeSpecifier_AST;
		_retTree = _t;
	}
	
	protected final void functionStorageClassSpecifier(AST _t) throws RecognitionException {
		
		TNode functionStorageClassSpecifier_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode functionStorageClassSpecifier_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LITERAL_extern:
			{
				TNode tmp33_AST = null;
				TNode tmp33_AST_in = null;
				tmp33_AST = (TNode)astFactory.create((TNode)_t);
				tmp33_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp33_AST);
				match(_t,LITERAL_extern);
				_t = _t.getNextSibling();
				functionStorageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_static:
			{
				TNode tmp34_AST = null;
				TNode tmp34_AST_in = null;
				tmp34_AST = (TNode)astFactory.create((TNode)_t);
				tmp34_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp34_AST);
				match(_t,LITERAL_static);
				_t = _t.getNextSibling();
				functionStorageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_inline:
			{
				TNode tmp35_AST = null;
				TNode tmp35_AST_in = null;
				tmp35_AST = (TNode)astFactory.create((TNode)_t);
				tmp35_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp35_AST);
				match(_t,LITERAL_inline);
				_t = _t.getNextSibling();
				functionStorageClassSpecifier_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = functionStorageClassSpecifier_AST;
		_retTree = _t;
	}
	
	protected final void structSpecifier(AST _t) throws RecognitionException {
		
		TNode structSpecifier_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structSpecifier_AST = null;
		
		try {      // for error handling
			AST __t1103 = _t;
			TNode tmp36_AST = null;
			TNode tmp36_AST_in = null;
			tmp36_AST = (TNode)astFactory.create((TNode)_t);
			tmp36_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp36_AST);
			ASTPair __currentAST1103 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LITERAL_struct);
			_t = _t.getFirstChild();
			structOrUnionBody(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1103;
			_t = __t1103;
			_t = _t.getNextSibling();
			structSpecifier_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = structSpecifier_AST;
		_retTree = _t;
	}
	
	protected final void attributeDecl(AST _t) throws RecognitionException {
		
		TNode attributeDecl_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode attributeDecl_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LITERAL___attribute:
			{
				AST __t1138 = _t;
				TNode tmp37_AST = null;
				TNode tmp37_AST_in = null;
				tmp37_AST = (TNode)astFactory.create((TNode)_t);
				tmp37_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp37_AST);
				ASTPair __currentAST1138 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL___attribute);
				_t = _t.getFirstChild();
				{
				_loop1140:
				do {
					if (_t==null) _t=ASTNULL;
					if (((_t.getType() >= LITERAL_typedef && _t.getType() <= LITERAL___imag))) {
						TNode tmp38_AST = null;
						TNode tmp38_AST_in = null;
						tmp38_AST = (TNode)astFactory.create((TNode)_t);
						tmp38_AST_in = (TNode)_t;
						astFactory.addASTChild(currentAST, tmp38_AST);
						if ( _t==null ) throw new MismatchedTokenException();
						_t = _t.getNextSibling();
					}
					else {
						break _loop1140;
					}
					
				} while (true);
				}
				currentAST = __currentAST1138;
				_t = __t1138;
				_t = _t.getNextSibling();
				attributeDecl_AST = (TNode)currentAST.root;
				break;
			}
			case NAsmAttribute:
			{
				AST __t1141 = _t;
				TNode tmp39_AST = null;
				TNode tmp39_AST_in = null;
				tmp39_AST = (TNode)astFactory.create((TNode)_t);
				tmp39_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp39_AST);
				ASTPair __currentAST1141 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NAsmAttribute);
				_t = _t.getFirstChild();
				TNode tmp40_AST = null;
				TNode tmp40_AST_in = null;
				tmp40_AST = (TNode)astFactory.create((TNode)_t);
				tmp40_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp40_AST);
				match(_t,LPAREN);
				_t = _t.getNextSibling();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				TNode tmp41_AST = null;
				TNode tmp41_AST_in = null;
				tmp41_AST = (TNode)astFactory.create((TNode)_t);
				tmp41_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp41_AST);
				match(_t,RPAREN);
				_t = _t.getNextSibling();
				currentAST = __currentAST1141;
				_t = __t1141;
				_t = _t.getNextSibling();
				attributeDecl_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = attributeDecl_AST;
		_retTree = _t;
	}
	
	protected final void unionSpecifier(AST _t) throws RecognitionException {
		
		TNode unionSpecifier_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode unionSpecifier_AST = null;
		
		try {      // for error handling
			AST __t1105 = _t;
			TNode tmp42_AST = null;
			TNode tmp42_AST_in = null;
			tmp42_AST = (TNode)astFactory.create((TNode)_t);
			tmp42_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp42_AST);
			ASTPair __currentAST1105 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LITERAL_union);
			_t = _t.getFirstChild();
			structOrUnionBody(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1105;
			_t = __t1105;
			_t = _t.getNextSibling();
			unionSpecifier_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = unionSpecifier_AST;
		_retTree = _t;
	}
	
	protected final void enumSpecifier(AST _t) throws RecognitionException {
		
		TNode enumSpecifier_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode enumSpecifier_AST = null;
		
		try {      // for error handling
			AST __t1129 = _t;
			TNode tmp43_AST = null;
			TNode tmp43_AST_in = null;
			tmp43_AST = (TNode)astFactory.create((TNode)_t);
			tmp43_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp43_AST);
			ASTPair __currentAST1129 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LITERAL_enum);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ID:
			{
				TNode tmp44_AST = null;
				TNode tmp44_AST_in = null;
				tmp44_AST = (TNode)astFactory.create((TNode)_t);
				tmp44_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp44_AST);
				match(_t,ID);
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			case LCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LCURLY:
			{
				TNode tmp45_AST = null;
				TNode tmp45_AST_in = null;
				tmp45_AST = (TNode)astFactory.create((TNode)_t);
				tmp45_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp45_AST);
				match(_t,LCURLY);
				_t = _t.getNextSibling();
				enumList(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				TNode tmp46_AST = null;
				TNode tmp46_AST_in = null;
				tmp46_AST = (TNode)astFactory.create((TNode)_t);
				tmp46_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp46_AST);
				match(_t,RCURLY);
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			currentAST = __currentAST1129;
			_t = __t1129;
			_t = _t.getNextSibling();
			enumSpecifier_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = enumSpecifier_AST;
		_retTree = _t;
	}
	
	protected final void typedefName(AST _t) throws RecognitionException {
		
		TNode typedefName_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode typedefName_AST = null;
		
		try {      // for error handling
			AST __t1101 = _t;
			TNode tmp47_AST = null;
			TNode tmp47_AST_in = null;
			tmp47_AST = (TNode)astFactory.create((TNode)_t);
			tmp47_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp47_AST);
			ASTPair __currentAST1101 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NTypedefName);
			_t = _t.getFirstChild();
			TNode tmp48_AST = null;
			TNode tmp48_AST_in = null;
			tmp48_AST = (TNode)astFactory.create((TNode)_t);
			tmp48_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp48_AST);
			match(_t,ID);
			_t = _t.getNextSibling();
			currentAST = __currentAST1101;
			_t = __t1101;
			_t = _t.getNextSibling();
			typedefName_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = typedefName_AST;
		_retTree = _t;
	}
	
	protected final void typeName(AST _t) throws RecognitionException {
		
		TNode typeName_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode typeName_AST = null;
		
		try {      // for error handling
			specifierQualifierList(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NNonemptyAbstractDeclarator:
			{
				nonemptyAbstractDeclarator(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			typeName_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = typeName_AST;
		_retTree = _t;
	}
	
	protected final void expr(AST _t) throws RecognitionException {
		
		TNode expr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode expr_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
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
				assignExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case QUESTION:
			{
				conditionalExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case LOR:
			{
				logicalOrExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case LAND:
			{
				logicalAndExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case BOR:
			{
				inclusiveOrExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case BXOR:
			{
				exclusiveOrExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case BAND:
			{
				bitAndExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case EQUAL:
			case NOT_EQUAL:
			{
				equalityExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case LT:
			case LTE:
			case GT:
			case GTE:
			{
				relationalExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case LSHIFT:
			case RSHIFT:
			{
				shiftExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case PLUS:
			case MINUS:
			{
				additiveExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case STAR:
			case DIV:
			case MOD:
			{
				multExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case NCast:
			{
				castExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case INC:
			case DEC:
			case LITERAL_sizeof:
			case NUnaryExpr:
			case LITERAL___alignof:
			{
				unaryExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case NPostfixExpr:
			{
				postfixExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case ID:
			case CharLiteral:
			case NExpressionGroup:
			case NStringSeq:
			case Number:
			{
				primaryExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case NCommaExpr:
			{
				commaExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case NEmptyExpression:
			{
				emptyExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case LPAREN:
			{
				compoundStatementExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case NInitializer:
			case NLcurlyInitializer:
			{
				initializer(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case NRangeExpr:
			{
				rangeExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			case NGnuAsmExpr:
			{
				gnuAsmExpr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = expr_AST;
		_retTree = _t;
	}
	
	protected final void structOrUnionBody(AST _t) throws RecognitionException {
		
		TNode structOrUnionBody_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structOrUnionBody_AST = null;
		
		try {      // for error handling
			{
			boolean synPredMatched1109 = false;
			if (((_t.getType()==ID))) {
				AST __t1109 = _t;
				synPredMatched1109 = true;
				inputState.guessing++;
				try {
					{
					match(_t,ID);
					_t = _t.getNextSibling();
					match(_t,LCURLY);
					_t = _t.getNextSibling();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched1109 = false;
				}
				_t = __t1109;
				inputState.guessing--;
			}
			if ( synPredMatched1109 ) {
				TNode tmp49_AST = null;
				TNode tmp49_AST_in = null;
				tmp49_AST = (TNode)astFactory.create((TNode)_t);
				tmp49_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp49_AST);
				match(_t,ID);
				_t = _t.getNextSibling();
				TNode tmp50_AST = null;
				TNode tmp50_AST_in = null;
				tmp50_AST = (TNode)astFactory.create((TNode)_t);
				tmp50_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp50_AST);
				match(_t,LCURLY);
				_t = _t.getNextSibling();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_volatile:
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
				case LITERAL_struct:
				case LITERAL_union:
				case LITERAL_enum:
				case NTypedefName:
				case LITERAL_typeof:
				case LITERAL___complex:
				{
					structDeclarationList(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case RCURLY:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				TNode tmp51_AST = null;
				TNode tmp51_AST_in = null;
				tmp51_AST = (TNode)astFactory.create((TNode)_t);
				tmp51_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp51_AST);
				match(_t,RCURLY);
				_t = _t.getNextSibling();
			}
			else if ((_t.getType()==LCURLY)) {
				TNode tmp52_AST = null;
				TNode tmp52_AST_in = null;
				tmp52_AST = (TNode)astFactory.create((TNode)_t);
				tmp52_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp52_AST);
				match(_t,LCURLY);
				_t = _t.getNextSibling();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_volatile:
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
				case LITERAL_struct:
				case LITERAL_union:
				case LITERAL_enum:
				case NTypedefName:
				case LITERAL_typeof:
				case LITERAL___complex:
				{
					structDeclarationList(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case RCURLY:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				TNode tmp53_AST = null;
				TNode tmp53_AST_in = null;
				tmp53_AST = (TNode)astFactory.create((TNode)_t);
				tmp53_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp53_AST);
				match(_t,RCURLY);
				_t = _t.getNextSibling();
			}
			else if ((_t.getType()==ID)) {
				TNode tmp54_AST = null;
				TNode tmp54_AST_in = null;
				tmp54_AST = (TNode)astFactory.create((TNode)_t);
				tmp54_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp54_AST);
				match(_t,ID);
				_t = _t.getNextSibling();
			}
			else {
				throw new NoViableAltException(_t);
			}
			
			}
			structOrUnionBody_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = structOrUnionBody_AST;
		_retTree = _t;
	}
	
	protected final void structDeclarationList(AST _t) throws RecognitionException {
		
		TNode structDeclarationList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structDeclarationList_AST = null;
		
		try {      // for error handling
			{
			int _cnt1114=0;
			_loop1114:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==LITERAL_volatile||_t.getType()==LITERAL_const||_t.getType()==LITERAL_void||_t.getType()==LITERAL_char||_t.getType()==LITERAL_short||_t.getType()==LITERAL_int||_t.getType()==LITERAL_long||_t.getType()==LITERAL_float||_t.getType()==LITERAL_double||_t.getType()==LITERAL_signed||_t.getType()==LITERAL_unsigned||_t.getType()==LITERAL_struct||_t.getType()==LITERAL_union||_t.getType()==LITERAL_enum||_t.getType()==NTypedefName||_t.getType()==LITERAL_typeof||_t.getType()==LITERAL___complex)) {
					structDeclaration(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt1114>=1 ) { break _loop1114; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt1114++;
			} while (true);
			}
			structDeclarationList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = structDeclarationList_AST;
		_retTree = _t;
	}
	
	protected final void structDeclaration(AST _t) throws RecognitionException {
		
		TNode structDeclaration_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structDeclaration_AST = null;
		
		try {      // for error handling
			specifierQualifierList(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			structDeclaratorList(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			structDeclaration_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = structDeclaration_AST;
		_retTree = _t;
	}
	
	protected final void specifierQualifierList(AST _t) throws RecognitionException {
		
		TNode specifierQualifierList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode specifierQualifierList_AST = null;
		
		try {      // for error handling
			{
			int _cnt1118=0;
			_loop1118:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_void:
				case LITERAL_char:
				case LITERAL_short:
				case LITERAL_int:
				case LITERAL_long:
				case LITERAL_float:
				case LITERAL_double:
				case LITERAL_signed:
				case LITERAL_unsigned:
				case LITERAL_struct:
				case LITERAL_union:
				case LITERAL_enum:
				case NTypedefName:
				case LITERAL_typeof:
				case LITERAL___complex:
				{
					typeSpecifier(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case LITERAL_volatile:
				case LITERAL_const:
				{
					typeQualifier(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				default:
				{
					if ( _cnt1118>=1 ) { break _loop1118; } else {throw new NoViableAltException(_t);}
				}
				}
				_cnt1118++;
			} while (true);
			}
			specifierQualifierList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = specifierQualifierList_AST;
		_retTree = _t;
	}
	
	protected final void structDeclaratorList(AST _t) throws RecognitionException {
		
		TNode structDeclaratorList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structDeclaratorList_AST = null;
		
		try {      // for error handling
			{
			int _cnt1121=0;
			_loop1121:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==NStructDeclarator)) {
					structDeclarator(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt1121>=1 ) { break _loop1121; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt1121++;
			} while (true);
			}
			structDeclaratorList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = structDeclaratorList_AST;
		_retTree = _t;
	}
	
	protected final void structDeclarator(AST _t) throws RecognitionException {
		
		TNode structDeclarator_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode structDeclarator_AST = null;
		
		try {      // for error handling
			AST __t1123 = _t;
			TNode tmp55_AST = null;
			TNode tmp55_AST_in = null;
			tmp55_AST = (TNode)astFactory.create((TNode)_t);
			tmp55_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp55_AST);
			ASTPair __currentAST1123 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NStructDeclarator);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NDeclarator:
			{
				declarator(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case 3:
			case COLON:
			case NAsmAttribute:
			case LITERAL___attribute:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case COLON:
			{
				TNode tmp56_AST = null;
				TNode tmp56_AST_in = null;
				tmp56_AST = (TNode)astFactory.create((TNode)_t);
				tmp56_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp56_AST);
				match(_t,COLON);
				_t = _t.getNextSibling();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case 3:
			case NAsmAttribute:
			case LITERAL___attribute:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			{
			_loop1127:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==NAsmAttribute||_t.getType()==LITERAL___attribute)) {
					attributeDecl(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop1127;
				}
				
			} while (true);
			}
			currentAST = __currentAST1123;
			_t = __t1123;
			_t = _t.getNextSibling();
			structDeclarator_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = structDeclarator_AST;
		_retTree = _t;
	}
	
	protected final void declarator(AST _t) throws RecognitionException {
		
		TNode declarator_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode declarator_AST = null;
		
		try {      // for error handling
			AST __t1173 = _t;
			TNode tmp57_AST = null;
			TNode tmp57_AST_in = null;
			tmp57_AST = (TNode)astFactory.create((TNode)_t);
			tmp57_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp57_AST);
			ASTPair __currentAST1173 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NDeclarator);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NPointerGroup:
			{
				pointerGroup(_t);
				_t = _retTree;
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
				throw new NoViableAltException(_t);
			}
			}
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ID:
			{
				TNode tmp58_AST = null;
				TNode tmp58_AST_in = null;
				tmp58_AST = (TNode)astFactory.create((TNode)_t);
				tmp58_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp58_AST);
				match(_t,ID);
				_t = _t.getNextSibling();
				break;
			}
			case LPAREN:
			{
				TNode tmp59_AST = null;
				TNode tmp59_AST_in = null;
				tmp59_AST = (TNode)astFactory.create((TNode)_t);
				tmp59_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp59_AST);
				match(_t,LPAREN);
				_t = _t.getNextSibling();
				declarator(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				TNode tmp60_AST = null;
				TNode tmp60_AST_in = null;
				tmp60_AST = (TNode)astFactory.create((TNode)_t);
				tmp60_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp60_AST);
				match(_t,RPAREN);
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			{
			_loop1181:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case NParameterTypeList:
				{
					AST __t1177 = _t;
					TNode tmp61_AST = null;
					TNode tmp61_AST_in = null;
					tmp61_AST = (TNode)astFactory.create((TNode)_t);
					tmp61_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp61_AST);
					ASTPair __currentAST1177 = currentAST.copy();
					currentAST.root = currentAST.child;
					currentAST.child = null;
					match(_t,NParameterTypeList);
					_t = _t.getFirstChild();
					{
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==RPAREN||_t.getType()==VARARGS||_t.getType()==NParameterDeclaration)) {
						parameterTypeList(_t);
						_t = _retTree;
						astFactory.addASTChild(currentAST, returnAST);
					}
					else if ((_t.getType()==ID||_t.getType()==RPAREN)) {
						{
						if (_t==null) _t=ASTNULL;
						switch ( _t.getType()) {
						case ID:
						{
							idList(_t);
							_t = _retTree;
							astFactory.addASTChild(currentAST, returnAST);
							break;
						}
						case RPAREN:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(_t);
						}
						}
						}
					}
					else {
						throw new NoViableAltException(_t);
					}
					
					}
					TNode tmp62_AST = null;
					TNode tmp62_AST_in = null;
					tmp62_AST = (TNode)astFactory.create((TNode)_t);
					tmp62_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp62_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
					currentAST = __currentAST1177;
					_t = __t1177;
					_t = _t.getNextSibling();
					break;
				}
				case LBRACKET:
				{
					TNode tmp63_AST = null;
					TNode tmp63_AST_in = null;
					tmp63_AST = (TNode)astFactory.create((TNode)_t);
					tmp63_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp63_AST);
					match(_t,LBRACKET);
					_t = _t.getNextSibling();
					{
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case ID:
					case ASSIGN:
					case STAR:
					case LPAREN:
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
					case INC:
					case DEC:
					case LITERAL_sizeof:
					case CharLiteral:
					case NCast:
					case NExpressionGroup:
					case NInitializer:
					case NEmptyExpression:
					case NCommaExpr:
					case NUnaryExpr:
					case NPostfixExpr:
					case NRangeExpr:
					case NStringSeq:
					case NLcurlyInitializer:
					case NGnuAsmExpr:
					case Number:
					case LITERAL___alignof:
					{
						expr(_t);
						_t = _retTree;
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case RBRACKET:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(_t);
					}
					}
					}
					TNode tmp64_AST = null;
					TNode tmp64_AST_in = null;
					tmp64_AST = (TNode)astFactory.create((TNode)_t);
					tmp64_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp64_AST);
					match(_t,RBRACKET);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					break _loop1181;
				}
				}
			} while (true);
			}
			currentAST = __currentAST1173;
			_t = __t1173;
			_t = _t.getNextSibling();
			declarator_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = declarator_AST;
		_retTree = _t;
	}
	
	protected final void enumList(AST _t) throws RecognitionException {
		
		TNode enumList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode enumList_AST = null;
		
		try {      // for error handling
			{
			int _cnt1134=0;
			_loop1134:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ID)) {
					enumerator(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt1134>=1 ) { break _loop1134; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt1134++;
			} while (true);
			}
			enumList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = enumList_AST;
		_retTree = _t;
	}
	
	protected final void enumerator(AST _t) throws RecognitionException {
		
		TNode enumerator_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode enumerator_AST = null;
		
		try {      // for error handling
			TNode tmp65_AST = null;
			TNode tmp65_AST_in = null;
			tmp65_AST = (TNode)astFactory.create((TNode)_t);
			tmp65_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp65_AST);
			match(_t,ID);
			_t = _t.getNextSibling();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ASSIGN:
			{
				TNode tmp66_AST = null;
				TNode tmp66_AST_in = null;
				tmp66_AST = (TNode)astFactory.create((TNode)_t);
				tmp66_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp66_AST);
				match(_t,ASSIGN);
				_t = _t.getNextSibling();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RCURLY:
			case ID:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			enumerator_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = enumerator_AST;
		_retTree = _t;
	}
	
	protected final void initDecl(AST _t) throws RecognitionException {
		
		TNode initDecl_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode initDecl_AST = null;
		String declName = "";
		
		try {      // for error handling
			AST __t1146 = _t;
			TNode tmp67_AST = null;
			TNode tmp67_AST_in = null;
			tmp67_AST = (TNode)astFactory.create((TNode)_t);
			tmp67_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp67_AST);
			ASTPair __currentAST1146 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NInitDecl);
			_t = _t.getFirstChild();
			declarator(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop1148:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==NAsmAttribute||_t.getType()==LITERAL___attribute)) {
					attributeDecl(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop1148;
				}
				
			} while (true);
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ASSIGN:
			{
				TNode tmp68_AST = null;
				TNode tmp68_AST_in = null;
				tmp68_AST = (TNode)astFactory.create((TNode)_t);
				tmp68_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp68_AST);
				match(_t,ASSIGN);
				_t = _t.getNextSibling();
				initializer(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case COLON:
			{
				TNode tmp69_AST = null;
				TNode tmp69_AST_in = null;
				tmp69_AST = (TNode)astFactory.create((TNode)_t);
				tmp69_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp69_AST);
				match(_t,COLON);
				_t = _t.getNextSibling();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			currentAST = __currentAST1146;
			_t = __t1146;
			_t = _t.getNextSibling();
			initDecl_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = initDecl_AST;
		_retTree = _t;
	}
	
	protected final void initializer(AST _t) throws RecognitionException {
		
		TNode initializer_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode initializer_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NInitializer:
			{
				AST __t1160 = _t;
				TNode tmp70_AST = null;
				TNode tmp70_AST_in = null;
				tmp70_AST = (TNode)astFactory.create((TNode)_t);
				tmp70_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp70_AST);
				ASTPair __currentAST1160 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NInitializer);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case NInitializerElementLabel:
				{
					initializerElementLabel(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case ID:
				case ASSIGN:
				case STAR:
				case LPAREN:
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
				case INC:
				case DEC:
				case LITERAL_sizeof:
				case CharLiteral:
				case NCast:
				case NExpressionGroup:
				case NInitializer:
				case NEmptyExpression:
				case NCommaExpr:
				case NUnaryExpr:
				case NPostfixExpr:
				case NRangeExpr:
				case NStringSeq:
				case NLcurlyInitializer:
				case NGnuAsmExpr:
				case Number:
				case LITERAL___alignof:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1160;
				_t = __t1160;
				_t = _t.getNextSibling();
				initializer_AST = (TNode)currentAST.root;
				break;
			}
			case NLcurlyInitializer:
			{
				lcurlyInitializer(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				initializer_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = initializer_AST;
		_retTree = _t;
	}
	
	protected final void pointerGroup(AST _t) throws RecognitionException {
		
		TNode pointerGroup_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode pointerGroup_AST = null;
		
		try {      // for error handling
			AST __t1151 = _t;
			TNode tmp71_AST = null;
			TNode tmp71_AST_in = null;
			tmp71_AST = (TNode)astFactory.create((TNode)_t);
			tmp71_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp71_AST);
			ASTPair __currentAST1151 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NPointerGroup);
			_t = _t.getFirstChild();
			{
			int _cnt1155=0;
			_loop1155:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==STAR)) {
					TNode tmp72_AST = null;
					TNode tmp72_AST_in = null;
					tmp72_AST = (TNode)astFactory.create((TNode)_t);
					tmp72_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp72_AST);
					match(_t,STAR);
					_t = _t.getNextSibling();
					{
					_loop1154:
					do {
						if (_t==null) _t=ASTNULL;
						if ((_t.getType()==LITERAL_volatile||_t.getType()==LITERAL_const)) {
							typeQualifier(_t);
							_t = _retTree;
							astFactory.addASTChild(currentAST, returnAST);
						}
						else {
							break _loop1154;
						}
						
					} while (true);
					}
				}
				else {
					if ( _cnt1155>=1 ) { break _loop1155; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt1155++;
			} while (true);
			}
			currentAST = __currentAST1151;
			_t = __t1151;
			_t = _t.getNextSibling();
			pointerGroup_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = pointerGroup_AST;
		_retTree = _t;
	}
	
	protected final void idList(AST _t) throws RecognitionException {
		
		TNode idList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode idList_AST = null;
		
		try {      // for error handling
			TNode tmp73_AST = null;
			TNode tmp73_AST_in = null;
			tmp73_AST = (TNode)astFactory.create((TNode)_t);
			tmp73_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp73_AST);
			match(_t,ID);
			_t = _t.getNextSibling();
			{
			_loop1158:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==COMMA)) {
					TNode tmp74_AST = null;
					TNode tmp74_AST_in = null;
					tmp74_AST = (TNode)astFactory.create((TNode)_t);
					tmp74_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp74_AST);
					match(_t,COMMA);
					_t = _t.getNextSibling();
					TNode tmp75_AST = null;
					TNode tmp75_AST_in = null;
					tmp75_AST = (TNode)astFactory.create((TNode)_t);
					tmp75_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp75_AST);
					match(_t,ID);
					_t = _t.getNextSibling();
				}
				else {
					break _loop1158;
				}
				
			} while (true);
			}
			idList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = idList_AST;
		_retTree = _t;
	}
	
	protected final void initializerElementLabel(AST _t) throws RecognitionException {
		
		TNode initializerElementLabel_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode initializerElementLabel_AST = null;
		
		try {      // for error handling
			AST __t1163 = _t;
			TNode tmp76_AST = null;
			TNode tmp76_AST_in = null;
			tmp76_AST = (TNode)astFactory.create((TNode)_t);
			tmp76_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp76_AST);
			ASTPair __currentAST1163 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NInitializerElementLabel);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LBRACKET:
			{
				{
				TNode tmp77_AST = null;
				TNode tmp77_AST_in = null;
				tmp77_AST = (TNode)astFactory.create((TNode)_t);
				tmp77_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp77_AST);
				match(_t,LBRACKET);
				_t = _t.getNextSibling();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				TNode tmp78_AST = null;
				TNode tmp78_AST_in = null;
				tmp78_AST = (TNode)astFactory.create((TNode)_t);
				tmp78_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp78_AST);
				match(_t,RBRACKET);
				_t = _t.getNextSibling();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ASSIGN:
				{
					TNode tmp79_AST = null;
					TNode tmp79_AST_in = null;
					tmp79_AST = (TNode)astFactory.create((TNode)_t);
					tmp79_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp79_AST);
					match(_t,ASSIGN);
					_t = _t.getNextSibling();
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				}
				break;
			}
			case ID:
			{
				TNode tmp80_AST = null;
				TNode tmp80_AST_in = null;
				tmp80_AST = (TNode)astFactory.create((TNode)_t);
				tmp80_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp80_AST);
				match(_t,ID);
				_t = _t.getNextSibling();
				TNode tmp81_AST = null;
				TNode tmp81_AST_in = null;
				tmp81_AST = (TNode)astFactory.create((TNode)_t);
				tmp81_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp81_AST);
				match(_t,COLON);
				_t = _t.getNextSibling();
				break;
			}
			case DOT:
			{
				TNode tmp82_AST = null;
				TNode tmp82_AST_in = null;
				tmp82_AST = (TNode)astFactory.create((TNode)_t);
				tmp82_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp82_AST);
				match(_t,DOT);
				_t = _t.getNextSibling();
				TNode tmp83_AST = null;
				TNode tmp83_AST_in = null;
				tmp83_AST = (TNode)astFactory.create((TNode)_t);
				tmp83_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp83_AST);
				match(_t,ID);
				_t = _t.getNextSibling();
				TNode tmp84_AST = null;
				TNode tmp84_AST_in = null;
				tmp84_AST = (TNode)astFactory.create((TNode)_t);
				tmp84_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp84_AST);
				match(_t,ASSIGN);
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			currentAST = __currentAST1163;
			_t = __t1163;
			_t = _t.getNextSibling();
			initializerElementLabel_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = initializerElementLabel_AST;
		_retTree = _t;
	}
	
	protected final void lcurlyInitializer(AST _t) throws RecognitionException {
		
		TNode lcurlyInitializer_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode lcurlyInitializer_AST = null;
		
		try {      // for error handling
			AST __t1168 = _t;
			TNode tmp85_AST = null;
			TNode tmp85_AST_in = null;
			tmp85_AST = (TNode)astFactory.create((TNode)_t);
			tmp85_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp85_AST);
			ASTPair __currentAST1168 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NLcurlyInitializer);
			_t = _t.getFirstChild();
			initializerList(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			TNode tmp86_AST = null;
			TNode tmp86_AST_in = null;
			tmp86_AST = (TNode)astFactory.create((TNode)_t);
			tmp86_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp86_AST);
			match(_t,RCURLY);
			_t = _t.getNextSibling();
			currentAST = __currentAST1168;
			_t = __t1168;
			_t = _t.getNextSibling();
			lcurlyInitializer_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = lcurlyInitializer_AST;
		_retTree = _t;
	}
	
	protected final void initializerList(AST _t) throws RecognitionException {
		
		TNode initializerList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode initializerList_AST = null;
		
		try {      // for error handling
			{
			_loop1171:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==NInitializer||_t.getType()==NLcurlyInitializer)) {
					initializer(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop1171;
				}
				
			} while (true);
			}
			initializerList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = initializerList_AST;
		_retTree = _t;
	}
	
	protected final void parameterTypeList(AST _t) throws RecognitionException {
		
		TNode parameterTypeList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode parameterTypeList_AST = null;
		
		try {      // for error handling
			{
			_loop1184:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==NParameterDeclaration)) {
					parameterDeclaration(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop1184;
				}
				
			} while (true);
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case VARARGS:
			{
				TNode tmp87_AST = null;
				TNode tmp87_AST_in = null;
				tmp87_AST = (TNode)astFactory.create((TNode)_t);
				tmp87_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp87_AST);
				match(_t,VARARGS);
				_t = _t.getNextSibling();
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			parameterTypeList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = parameterTypeList_AST;
		_retTree = _t;
	}
	
	protected final void parameterDeclaration(AST _t) throws RecognitionException {
		
		TNode parameterDeclaration_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode parameterDeclaration_AST = null;
		
		try {      // for error handling
			AST __t1187 = _t;
			TNode tmp88_AST = null;
			TNode tmp88_AST_in = null;
			tmp88_AST = (TNode)astFactory.create((TNode)_t);
			tmp88_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp88_AST);
			ASTPair __currentAST1187 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NParameterDeclaration);
			_t = _t.getFirstChild();
			declSpecifiers(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NDeclarator:
			{
				declarator(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case NNonemptyAbstractDeclarator:
			{
				nonemptyAbstractDeclarator(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			currentAST = __currentAST1187;
			_t = __t1187;
			_t = _t.getNextSibling();
			parameterDeclaration_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = parameterDeclaration_AST;
		_retTree = _t;
	}
	
	protected final void nonemptyAbstractDeclarator(AST _t) throws RecognitionException {
		
		TNode nonemptyAbstractDeclarator_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode nonemptyAbstractDeclarator_AST = null;
		
		try {      // for error handling
			AST __t1306 = _t;
			TNode tmp89_AST = null;
			TNode tmp89_AST_in = null;
			tmp89_AST = (TNode)astFactory.create((TNode)_t);
			tmp89_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp89_AST);
			ASTPair __currentAST1306 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NNonemptyAbstractDeclarator);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NPointerGroup:
			{
				pointerGroup(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop1313:
				do {
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case LPAREN:
					{
						{
						TNode tmp90_AST = null;
						TNode tmp90_AST_in = null;
						tmp90_AST = (TNode)astFactory.create((TNode)_t);
						tmp90_AST_in = (TNode)_t;
						astFactory.addASTChild(currentAST, tmp90_AST);
						match(_t,LPAREN);
						_t = _t.getNextSibling();
						{
						if (_t==null) _t=ASTNULL;
						if ((_t.getType()==NNonemptyAbstractDeclarator)) {
							nonemptyAbstractDeclarator(_t);
							_t = _retTree;
							astFactory.addASTChild(currentAST, returnAST);
						}
						else if ((_t.getType()==RPAREN||_t.getType()==VARARGS||_t.getType()==NParameterDeclaration)) {
							parameterTypeList(_t);
							_t = _retTree;
							astFactory.addASTChild(currentAST, returnAST);
						}
						else if ((_t.getType()==RPAREN)) {
						}
						else {
							throw new NoViableAltException(_t);
						}
						
						}
						TNode tmp91_AST = null;
						TNode tmp91_AST_in = null;
						tmp91_AST = (TNode)astFactory.create((TNode)_t);
						tmp91_AST_in = (TNode)_t;
						astFactory.addASTChild(currentAST, tmp91_AST);
						match(_t,RPAREN);
						_t = _t.getNextSibling();
						}
						break;
					}
					case LBRACKET:
					{
						{
						TNode tmp92_AST = null;
						TNode tmp92_AST_in = null;
						tmp92_AST = (TNode)astFactory.create((TNode)_t);
						tmp92_AST_in = (TNode)_t;
						astFactory.addASTChild(currentAST, tmp92_AST);
						match(_t,LBRACKET);
						_t = _t.getNextSibling();
						{
						if (_t==null) _t=ASTNULL;
						switch ( _t.getType()) {
						case ID:
						case ASSIGN:
						case STAR:
						case LPAREN:
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
						case INC:
						case DEC:
						case LITERAL_sizeof:
						case CharLiteral:
						case NCast:
						case NExpressionGroup:
						case NInitializer:
						case NEmptyExpression:
						case NCommaExpr:
						case NUnaryExpr:
						case NPostfixExpr:
						case NRangeExpr:
						case NStringSeq:
						case NLcurlyInitializer:
						case NGnuAsmExpr:
						case Number:
						case LITERAL___alignof:
						{
							expr(_t);
							_t = _retTree;
							astFactory.addASTChild(currentAST, returnAST);
							break;
						}
						case RBRACKET:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(_t);
						}
						}
						}
						TNode tmp93_AST = null;
						TNode tmp93_AST_in = null;
						tmp93_AST = (TNode)astFactory.create((TNode)_t);
						tmp93_AST_in = (TNode)_t;
						astFactory.addASTChild(currentAST, tmp93_AST);
						match(_t,RBRACKET);
						_t = _t.getNextSibling();
						}
						break;
					}
					default:
					{
						break _loop1313;
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
				int _cnt1319=0;
				_loop1319:
				do {
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case LPAREN:
					{
						{
						TNode tmp94_AST = null;
						TNode tmp94_AST_in = null;
						tmp94_AST = (TNode)astFactory.create((TNode)_t);
						tmp94_AST_in = (TNode)_t;
						astFactory.addASTChild(currentAST, tmp94_AST);
						match(_t,LPAREN);
						_t = _t.getNextSibling();
						{
						if (_t==null) _t=ASTNULL;
						if ((_t.getType()==NNonemptyAbstractDeclarator)) {
							nonemptyAbstractDeclarator(_t);
							_t = _retTree;
							astFactory.addASTChild(currentAST, returnAST);
						}
						else if ((_t.getType()==RPAREN||_t.getType()==VARARGS||_t.getType()==NParameterDeclaration)) {
							parameterTypeList(_t);
							_t = _retTree;
							astFactory.addASTChild(currentAST, returnAST);
						}
						else if ((_t.getType()==RPAREN)) {
						}
						else {
							throw new NoViableAltException(_t);
						}
						
						}
						TNode tmp95_AST = null;
						TNode tmp95_AST_in = null;
						tmp95_AST = (TNode)astFactory.create((TNode)_t);
						tmp95_AST_in = (TNode)_t;
						astFactory.addASTChild(currentAST, tmp95_AST);
						match(_t,RPAREN);
						_t = _t.getNextSibling();
						}
						break;
					}
					case LBRACKET:
					{
						{
						TNode tmp96_AST = null;
						TNode tmp96_AST_in = null;
						tmp96_AST = (TNode)astFactory.create((TNode)_t);
						tmp96_AST_in = (TNode)_t;
						astFactory.addASTChild(currentAST, tmp96_AST);
						match(_t,LBRACKET);
						_t = _t.getNextSibling();
						{
						if (_t==null) _t=ASTNULL;
						switch ( _t.getType()) {
						case ID:
						case ASSIGN:
						case STAR:
						case LPAREN:
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
						case INC:
						case DEC:
						case LITERAL_sizeof:
						case CharLiteral:
						case NCast:
						case NExpressionGroup:
						case NInitializer:
						case NEmptyExpression:
						case NCommaExpr:
						case NUnaryExpr:
						case NPostfixExpr:
						case NRangeExpr:
						case NStringSeq:
						case NLcurlyInitializer:
						case NGnuAsmExpr:
						case Number:
						case LITERAL___alignof:
						{
							expr(_t);
							_t = _retTree;
							astFactory.addASTChild(currentAST, returnAST);
							break;
						}
						case RBRACKET:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(_t);
						}
						}
						}
						TNode tmp97_AST = null;
						TNode tmp97_AST_in = null;
						tmp97_AST = (TNode)astFactory.create((TNode)_t);
						tmp97_AST_in = (TNode)_t;
						astFactory.addASTChild(currentAST, tmp97_AST);
						match(_t,RBRACKET);
						_t = _t.getNextSibling();
						}
						break;
					}
					default:
					{
						if ( _cnt1319>=1 ) { break _loop1319; } else {throw new NoViableAltException(_t);}
					}
					}
					_cnt1319++;
				} while (true);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			currentAST = __currentAST1306;
			_t = __t1306;
			_t = _t.getNextSibling();
			nonemptyAbstractDeclarator_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = nonemptyAbstractDeclarator_AST;
		_retTree = _t;
	}
	
	protected final void functionDeclSpecifiers(AST _t) throws RecognitionException {
		
		TNode functionDeclSpecifiers_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode functionDeclSpecifiers_AST = null;
		
		try {      // for error handling
			{
			int _cnt1196=0;
			_loop1196:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_extern:
				case LITERAL_static:
				case LITERAL_inline:
				{
					functionStorageClassSpecifier(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case LITERAL_volatile:
				case LITERAL_const:
				{
					typeQualifier(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case LITERAL_void:
				case LITERAL_char:
				case LITERAL_short:
				case LITERAL_int:
				case LITERAL_long:
				case LITERAL_float:
				case LITERAL_double:
				case LITERAL_signed:
				case LITERAL_unsigned:
				case LITERAL_struct:
				case LITERAL_union:
				case LITERAL_enum:
				case NTypedefName:
				case LITERAL_typeof:
				case LITERAL___complex:
				{
					typeSpecifier(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				default:
				{
					if ( _cnt1196>=1 ) { break _loop1196; } else {throw new NoViableAltException(_t);}
				}
				}
				_cnt1196++;
			} while (true);
			}
			functionDeclSpecifiers_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = functionDeclSpecifiers_AST;
		_retTree = _t;
	}
	
	protected final void declarationList(AST _t) throws RecognitionException {
		
		TNode declarationList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode declarationList_AST = null;
		
		try {      // for error handling
			{
			int _cnt1199=0;
			_loop1199:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==LITERAL___label__)) {
					localLabelDecl(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((_t.getType()==NDeclaration)) {
					declaration(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt1199>=1 ) { break _loop1199; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt1199++;
			} while (true);
			}
			declarationList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = declarationList_AST;
		_retTree = _t;
	}
	
	protected final void localLabelDecl(AST _t) throws RecognitionException {
		
		TNode localLabelDecl_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode localLabelDecl_AST = null;
		
		try {      // for error handling
			AST __t1201 = _t;
			TNode tmp98_AST = null;
			TNode tmp98_AST_in = null;
			tmp98_AST = (TNode)astFactory.create((TNode)_t);
			tmp98_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp98_AST);
			ASTPair __currentAST1201 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LITERAL___label__);
			_t = _t.getFirstChild();
			{
			int _cnt1203=0;
			_loop1203:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ID)) {
					TNode tmp99_AST = null;
					TNode tmp99_AST_in = null;
					tmp99_AST = (TNode)astFactory.create((TNode)_t);
					tmp99_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp99_AST);
					match(_t,ID);
					_t = _t.getNextSibling();
				}
				else {
					if ( _cnt1203>=1 ) { break _loop1203; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt1203++;
			} while (true);
			}
			currentAST = __currentAST1201;
			_t = __t1201;
			_t = _t.getNextSibling();
			localLabelDecl_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = localLabelDecl_AST;
		_retTree = _t;
	}
	
	protected final void statementList(AST _t) throws RecognitionException {
		
		TNode statementList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode statementList_AST = null;
		
		try {      // for error handling
			{
			int _cnt1211=0;
			_loop1211:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==SEMI||_t.getType()==LITERAL_while||_t.getType()==LITERAL_do||_t.getType()==LITERAL_for||_t.getType()==LITERAL_goto||_t.getType()==LITERAL_continue||_t.getType()==LITERAL_break||_t.getType()==LITERAL_return||_t.getType()==LITERAL_case||_t.getType()==LITERAL_default||_t.getType()==LITERAL_if||_t.getType()==LITERAL_switch||_t.getType()==NStatementExpr||_t.getType()==NCompoundStatement||_t.getType()==NLabel)) {
					statement(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt1211>=1 ) { break _loop1211; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt1211++;
			} while (true);
			}
			statementList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = statementList_AST;
		_retTree = _t;
	}
	
	protected final void statement(AST _t) throws RecognitionException {
		
		TNode statement_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode statement_AST = null;
		
		try {      // for error handling
			statementBody(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = statement_AST;
		_retTree = _t;
	}
	
	protected final void statementBody(AST _t) throws RecognitionException {
		
		TNode statementBody_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode statementBody_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case SEMI:
			{
				TNode tmp100_AST = null;
				TNode tmp100_AST_in = null;
				tmp100_AST = (TNode)astFactory.create((TNode)_t);
				tmp100_AST_in = (TNode)_t;
				match(_t,SEMI);
				_t = _t.getNextSibling();
				break;
			}
			case NCompoundStatement:
			{
				compoundStatement(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				statementBody_AST = (TNode)currentAST.root;
				break;
			}
			case NStatementExpr:
			{
				AST __t1214 = _t;
				TNode tmp101_AST = null;
				TNode tmp101_AST_in = null;
				tmp101_AST = (TNode)astFactory.create((TNode)_t);
				tmp101_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp101_AST);
				ASTPair __currentAST1214 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NStatementExpr);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1214;
				_t = __t1214;
				_t = _t.getNextSibling();
				statementBody_AST = (TNode)currentAST.root;
				break;
			}
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
			case NLabel:
			{
				stat(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				statementBody_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = statementBody_AST;
		_retTree = _t;
	}
	
	protected final void stat(AST _t) throws RecognitionException {
		
		TNode stat_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode stat_AST = null;
		TNode e_AST = null;
		TNode e = null;
		TNode s_AST = null;
		TNode s = null;
		TNode s2_AST = null;
		TNode s2 = null;
		TNode e2_AST = null;
		TNode e2 = null;
		TNode init_AST = null;
		TNode init = null;
		TNode cond_AST = null;
		TNode cond = null;
		TNode iter_AST = null;
		TNode iter = null;
		TNode s3_AST = null;
		TNode s3 = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LITERAL_while:
			{
				AST __t1216 = _t;
				TNode tmp102_AST = null;
				TNode tmp102_AST_in = null;
				tmp102_AST = (TNode)astFactory.create((TNode)_t);
				tmp102_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp102_AST);
				ASTPair __currentAST1216 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_while);
				_t = _t.getFirstChild();
				e = _t==ASTNULL ? null : (TNode)_t;
				expr(_t);
				_t = _retTree;
				e_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				s = _t==ASTNULL ? null : (TNode)_t;
				statement(_t);
				_t = _retTree;
				s_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1216;
				_t = __t1216;
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_do:
			{
				AST __t1217 = _t;
				TNode tmp103_AST = null;
				TNode tmp103_AST_in = null;
				tmp103_AST = (TNode)astFactory.create((TNode)_t);
				tmp103_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp103_AST);
				ASTPair __currentAST1217 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_do);
				_t = _t.getFirstChild();
				s2 = _t==ASTNULL ? null : (TNode)_t;
				statement(_t);
				_t = _retTree;
				s2_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				e2 = _t==ASTNULL ? null : (TNode)_t;
				expr(_t);
				_t = _retTree;
				e2_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1217;
				_t = __t1217;
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_for:
			{
				AST __t1218 = _t;
				TNode tmp104_AST = null;
				TNode tmp104_AST_in = null;
				tmp104_AST = (TNode)astFactory.create((TNode)_t);
				tmp104_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp104_AST);
				ASTPair __currentAST1218 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_for);
				_t = _t.getFirstChild();
				init = _t==ASTNULL ? null : (TNode)_t;
				expr(_t);
				_t = _retTree;
				init_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				cond = _t==ASTNULL ? null : (TNode)_t;
				expr(_t);
				_t = _retTree;
				cond_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				iter = _t==ASTNULL ? null : (TNode)_t;
				expr(_t);
				_t = _retTree;
				iter_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				s3 = _t==ASTNULL ? null : (TNode)_t;
				statement(_t);
				_t = _retTree;
				s3_AST = (TNode)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1218;
				_t = __t1218;
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_goto:
			{
				AST __t1219 = _t;
				TNode tmp105_AST = null;
				TNode tmp105_AST_in = null;
				tmp105_AST = (TNode)astFactory.create((TNode)_t);
				tmp105_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp105_AST);
				ASTPair __currentAST1219 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_goto);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1219;
				_t = __t1219;
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_continue:
			{
				TNode tmp106_AST = null;
				TNode tmp106_AST_in = null;
				tmp106_AST = (TNode)astFactory.create((TNode)_t);
				tmp106_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp106_AST);
				match(_t,LITERAL_continue);
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_break:
			{
				TNode tmp107_AST = null;
				TNode tmp107_AST_in = null;
				tmp107_AST = (TNode)astFactory.create((TNode)_t);
				tmp107_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp107_AST);
				match(_t,LITERAL_break);
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_return:
			{
				AST __t1220 = _t;
				TNode tmp108_AST = null;
				TNode tmp108_AST_in = null;
				tmp108_AST = (TNode)astFactory.create((TNode)_t);
				tmp108_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp108_AST);
				ASTPair __currentAST1220 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_return);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ID:
				case ASSIGN:
				case STAR:
				case LPAREN:
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
				case INC:
				case DEC:
				case LITERAL_sizeof:
				case CharLiteral:
				case NCast:
				case NExpressionGroup:
				case NInitializer:
				case NEmptyExpression:
				case NCommaExpr:
				case NUnaryExpr:
				case NPostfixExpr:
				case NRangeExpr:
				case NStringSeq:
				case NLcurlyInitializer:
				case NGnuAsmExpr:
				case Number:
				case LITERAL___alignof:
				{
					expr(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST1220;
				_t = __t1220;
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			case NLabel:
			{
				AST __t1222 = _t;
				TNode tmp109_AST = null;
				TNode tmp109_AST_in = null;
				tmp109_AST = (TNode)astFactory.create((TNode)_t);
				tmp109_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp109_AST);
				ASTPair __currentAST1222 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NLabel);
				_t = _t.getFirstChild();
				TNode tmp110_AST = null;
				TNode tmp110_AST_in = null;
				tmp110_AST = (TNode)astFactory.create((TNode)_t);
				tmp110_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp110_AST);
				match(_t,ID);
				_t = _t.getNextSibling();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case SEMI:
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
				case NStatementExpr:
				case NCompoundStatement:
				case NLabel:
				{
					statement(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST1222;
				_t = __t1222;
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_case:
			{
				AST __t1224 = _t;
				TNode tmp111_AST = null;
				TNode tmp111_AST_in = null;
				tmp111_AST = (TNode)astFactory.create((TNode)_t);
				tmp111_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp111_AST);
				ASTPair __currentAST1224 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_case);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case SEMI:
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
				case NStatementExpr:
				case NCompoundStatement:
				case NLabel:
				{
					statement(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST1224;
				_t = __t1224;
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_default:
			{
				AST __t1226 = _t;
				TNode tmp112_AST = null;
				TNode tmp112_AST_in = null;
				tmp112_AST = (TNode)astFactory.create((TNode)_t);
				tmp112_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp112_AST);
				ASTPair __currentAST1226 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_default);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case SEMI:
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
				case NStatementExpr:
				case NCompoundStatement:
				case NLabel:
				{
					statement(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST1226;
				_t = __t1226;
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_if:
			{
				AST __t1228 = _t;
				TNode tmp113_AST = null;
				TNode tmp113_AST_in = null;
				tmp113_AST = (TNode)astFactory.create((TNode)_t);
				tmp113_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp113_AST);
				ASTPair __currentAST1228 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_if);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				statement(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_else:
				{
					TNode tmp114_AST = null;
					TNode tmp114_AST_in = null;
					tmp114_AST = (TNode)astFactory.create((TNode)_t);
					tmp114_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp114_AST);
					match(_t,LITERAL_else);
					_t = _t.getNextSibling();
					statement(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				currentAST = __currentAST1228;
				_t = __t1228;
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_switch:
			{
				AST __t1230 = _t;
				TNode tmp115_AST = null;
				TNode tmp115_AST_in = null;
				tmp115_AST = (TNode)astFactory.create((TNode)_t);
				tmp115_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp115_AST);
				ASTPair __currentAST1230 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_switch);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				statement(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1230;
				_t = __t1230;
				_t = _t.getNextSibling();
				stat_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				
					reportError(ex);
					System.out.println("PROBLEM TREE:\n"+ _t.toStringList());
					if (_t!=null) {_t = _t.getNextSibling();}
				
			} else {
				throw ex;
			}
		}
		returnAST = stat_AST;
		_retTree = _t;
	}
	
	protected final void exprSeq(AST _t) throws RecognitionException {
		
		TNode exprSeq_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode exprSeq_AST = null;
		
		try {      // for error handling
			{
			_loop1233:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ID||_t.getType()==ASSIGN||_t.getType()==STAR||_t.getType()==LPAREN||_t.getType()==DIV_ASSIGN||_t.getType()==PLUS_ASSIGN||_t.getType()==MINUS_ASSIGN||_t.getType()==STAR_ASSIGN||_t.getType()==MOD_ASSIGN||_t.getType()==RSHIFT_ASSIGN||_t.getType()==LSHIFT_ASSIGN||_t.getType()==BAND_ASSIGN||_t.getType()==BOR_ASSIGN||_t.getType()==BXOR_ASSIGN||_t.getType()==QUESTION||_t.getType()==LOR||_t.getType()==LAND||_t.getType()==BOR||_t.getType()==BXOR||_t.getType()==BAND||_t.getType()==EQUAL||_t.getType()==NOT_EQUAL||_t.getType()==LT||_t.getType()==LTE||_t.getType()==GT||_t.getType()==GTE||_t.getType()==LSHIFT||_t.getType()==RSHIFT||_t.getType()==PLUS||_t.getType()==MINUS||_t.getType()==DIV||_t.getType()==MOD||_t.getType()==INC||_t.getType()==DEC||_t.getType()==LITERAL_sizeof||_t.getType()==CharLiteral||_t.getType()==NCast||_t.getType()==NExpressionGroup||_t.getType()==NInitializer||_t.getType()==NEmptyExpression||_t.getType()==NCommaExpr||_t.getType()==NUnaryExpr||_t.getType()==NPostfixExpr||_t.getType()==NRangeExpr||_t.getType()==NStringSeq||_t.getType()==NLcurlyInitializer||_t.getType()==NGnuAsmExpr||_t.getType()==Number||_t.getType()==LITERAL___alignof)) {
					expr(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop1233;
				}
				
			} while (true);
			}
			exprSeq_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = exprSeq_AST;
		_retTree = _t;
	}
	
	protected final void assignExpr(AST _t) throws RecognitionException {
		
		TNode assignExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode assignExpr_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ASSIGN:
			{
				AST __t1259 = _t;
				TNode tmp116_AST = null;
				TNode tmp116_AST_in = null;
				tmp116_AST = (TNode)astFactory.create((TNode)_t);
				tmp116_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp116_AST);
				ASTPair __currentAST1259 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1259;
				_t = __t1259;
				_t = _t.getNextSibling();
				assignExpr_AST = (TNode)currentAST.root;
				break;
			}
			case DIV_ASSIGN:
			{
				AST __t1260 = _t;
				TNode tmp117_AST = null;
				TNode tmp117_AST_in = null;
				tmp117_AST = (TNode)astFactory.create((TNode)_t);
				tmp117_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp117_AST);
				ASTPair __currentAST1260 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,DIV_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1260;
				_t = __t1260;
				_t = _t.getNextSibling();
				assignExpr_AST = (TNode)currentAST.root;
				break;
			}
			case PLUS_ASSIGN:
			{
				AST __t1261 = _t;
				TNode tmp118_AST = null;
				TNode tmp118_AST_in = null;
				tmp118_AST = (TNode)astFactory.create((TNode)_t);
				tmp118_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp118_AST);
				ASTPair __currentAST1261 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,PLUS_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1261;
				_t = __t1261;
				_t = _t.getNextSibling();
				assignExpr_AST = (TNode)currentAST.root;
				break;
			}
			case MINUS_ASSIGN:
			{
				AST __t1262 = _t;
				TNode tmp119_AST = null;
				TNode tmp119_AST_in = null;
				tmp119_AST = (TNode)astFactory.create((TNode)_t);
				tmp119_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp119_AST);
				ASTPair __currentAST1262 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,MINUS_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1262;
				_t = __t1262;
				_t = _t.getNextSibling();
				assignExpr_AST = (TNode)currentAST.root;
				break;
			}
			case STAR_ASSIGN:
			{
				AST __t1263 = _t;
				TNode tmp120_AST = null;
				TNode tmp120_AST_in = null;
				tmp120_AST = (TNode)astFactory.create((TNode)_t);
				tmp120_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp120_AST);
				ASTPair __currentAST1263 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,STAR_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1263;
				_t = __t1263;
				_t = _t.getNextSibling();
				assignExpr_AST = (TNode)currentAST.root;
				break;
			}
			case MOD_ASSIGN:
			{
				AST __t1264 = _t;
				TNode tmp121_AST = null;
				TNode tmp121_AST_in = null;
				tmp121_AST = (TNode)astFactory.create((TNode)_t);
				tmp121_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp121_AST);
				ASTPair __currentAST1264 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,MOD_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1264;
				_t = __t1264;
				_t = _t.getNextSibling();
				assignExpr_AST = (TNode)currentAST.root;
				break;
			}
			case RSHIFT_ASSIGN:
			{
				AST __t1265 = _t;
				TNode tmp122_AST = null;
				TNode tmp122_AST_in = null;
				tmp122_AST = (TNode)astFactory.create((TNode)_t);
				tmp122_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp122_AST);
				ASTPair __currentAST1265 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,RSHIFT_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1265;
				_t = __t1265;
				_t = _t.getNextSibling();
				assignExpr_AST = (TNode)currentAST.root;
				break;
			}
			case LSHIFT_ASSIGN:
			{
				AST __t1266 = _t;
				TNode tmp123_AST = null;
				TNode tmp123_AST_in = null;
				tmp123_AST = (TNode)astFactory.create((TNode)_t);
				tmp123_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp123_AST);
				ASTPair __currentAST1266 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LSHIFT_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1266;
				_t = __t1266;
				_t = _t.getNextSibling();
				assignExpr_AST = (TNode)currentAST.root;
				break;
			}
			case BAND_ASSIGN:
			{
				AST __t1267 = _t;
				TNode tmp124_AST = null;
				TNode tmp124_AST_in = null;
				tmp124_AST = (TNode)astFactory.create((TNode)_t);
				tmp124_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp124_AST);
				ASTPair __currentAST1267 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BAND_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1267;
				_t = __t1267;
				_t = _t.getNextSibling();
				assignExpr_AST = (TNode)currentAST.root;
				break;
			}
			case BOR_ASSIGN:
			{
				AST __t1268 = _t;
				TNode tmp125_AST = null;
				TNode tmp125_AST_in = null;
				tmp125_AST = (TNode)astFactory.create((TNode)_t);
				tmp125_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp125_AST);
				ASTPair __currentAST1268 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BOR_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1268;
				_t = __t1268;
				_t = _t.getNextSibling();
				assignExpr_AST = (TNode)currentAST.root;
				break;
			}
			case BXOR_ASSIGN:
			{
				AST __t1269 = _t;
				TNode tmp126_AST = null;
				TNode tmp126_AST_in = null;
				tmp126_AST = (TNode)astFactory.create((TNode)_t);
				tmp126_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp126_AST);
				ASTPair __currentAST1269 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,BXOR_ASSIGN);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1269;
				_t = __t1269;
				_t = _t.getNextSibling();
				assignExpr_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = assignExpr_AST;
		_retTree = _t;
	}
	
	protected final void conditionalExpr(AST _t) throws RecognitionException {
		
		TNode conditionalExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode conditionalExpr_AST = null;
		
		try {      // for error handling
			AST __t1271 = _t;
			TNode tmp127_AST = null;
			TNode tmp127_AST_in = null;
			tmp127_AST = (TNode)astFactory.create((TNode)_t);
			tmp127_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp127_AST);
			ASTPair __currentAST1271 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,QUESTION);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ID:
			case ASSIGN:
			case STAR:
			case LPAREN:
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
			case INC:
			case DEC:
			case LITERAL_sizeof:
			case CharLiteral:
			case NCast:
			case NExpressionGroup:
			case NInitializer:
			case NEmptyExpression:
			case NCommaExpr:
			case NUnaryExpr:
			case NPostfixExpr:
			case NRangeExpr:
			case NStringSeq:
			case NLcurlyInitializer:
			case NGnuAsmExpr:
			case Number:
			case LITERAL___alignof:
			{
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case COLON:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			TNode tmp128_AST_in = null;
			match(_t,COLON);
			_t = _t.getNextSibling();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1271;
			_t = __t1271;
			_t = _t.getNextSibling();
			conditionalExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = conditionalExpr_AST;
		_retTree = _t;
	}
	
	protected final void logicalOrExpr(AST _t) throws RecognitionException {
		
		TNode logicalOrExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode logicalOrExpr_AST = null;
		
		try {      // for error handling
			AST __t1274 = _t;
			TNode tmp129_AST = null;
			TNode tmp129_AST_in = null;
			tmp129_AST = (TNode)astFactory.create((TNode)_t);
			tmp129_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp129_AST);
			ASTPair __currentAST1274 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LOR);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1274;
			_t = __t1274;
			_t = _t.getNextSibling();
			logicalOrExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = logicalOrExpr_AST;
		_retTree = _t;
	}
	
	protected final void logicalAndExpr(AST _t) throws RecognitionException {
		
		TNode logicalAndExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode logicalAndExpr_AST = null;
		
		try {      // for error handling
			AST __t1276 = _t;
			TNode tmp130_AST = null;
			TNode tmp130_AST_in = null;
			tmp130_AST = (TNode)astFactory.create((TNode)_t);
			tmp130_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp130_AST);
			ASTPair __currentAST1276 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LAND);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1276;
			_t = __t1276;
			_t = _t.getNextSibling();
			logicalAndExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = logicalAndExpr_AST;
		_retTree = _t;
	}
	
	protected final void inclusiveOrExpr(AST _t) throws RecognitionException {
		
		TNode inclusiveOrExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode inclusiveOrExpr_AST = null;
		
		try {      // for error handling
			AST __t1278 = _t;
			TNode tmp131_AST = null;
			TNode tmp131_AST_in = null;
			tmp131_AST = (TNode)astFactory.create((TNode)_t);
			tmp131_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp131_AST);
			ASTPair __currentAST1278 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,BOR);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1278;
			_t = __t1278;
			_t = _t.getNextSibling();
			inclusiveOrExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = inclusiveOrExpr_AST;
		_retTree = _t;
	}
	
	protected final void exclusiveOrExpr(AST _t) throws RecognitionException {
		
		TNode exclusiveOrExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode exclusiveOrExpr_AST = null;
		
		try {      // for error handling
			AST __t1280 = _t;
			TNode tmp132_AST = null;
			TNode tmp132_AST_in = null;
			tmp132_AST = (TNode)astFactory.create((TNode)_t);
			tmp132_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp132_AST);
			ASTPair __currentAST1280 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,BXOR);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1280;
			_t = __t1280;
			_t = _t.getNextSibling();
			exclusiveOrExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = exclusiveOrExpr_AST;
		_retTree = _t;
	}
	
	protected final void bitAndExpr(AST _t) throws RecognitionException {
		
		TNode bitAndExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode bitAndExpr_AST = null;
		
		try {      // for error handling
			AST __t1282 = _t;
			TNode tmp133_AST = null;
			TNode tmp133_AST_in = null;
			tmp133_AST = (TNode)astFactory.create((TNode)_t);
			tmp133_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp133_AST);
			ASTPair __currentAST1282 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,BAND);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1282;
			_t = __t1282;
			_t = _t.getNextSibling();
			bitAndExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = bitAndExpr_AST;
		_retTree = _t;
	}
	
	protected final void equalityExpr(AST _t) throws RecognitionException {
		
		TNode equalityExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode equalityExpr_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case EQUAL:
			{
				AST __t1284 = _t;
				TNode tmp134_AST = null;
				TNode tmp134_AST_in = null;
				tmp134_AST = (TNode)astFactory.create((TNode)_t);
				tmp134_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp134_AST);
				ASTPair __currentAST1284 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,EQUAL);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1284;
				_t = __t1284;
				_t = _t.getNextSibling();
				equalityExpr_AST = (TNode)currentAST.root;
				break;
			}
			case NOT_EQUAL:
			{
				AST __t1285 = _t;
				TNode tmp135_AST = null;
				TNode tmp135_AST_in = null;
				tmp135_AST = (TNode)astFactory.create((TNode)_t);
				tmp135_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp135_AST);
				ASTPair __currentAST1285 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NOT_EQUAL);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1285;
				_t = __t1285;
				_t = _t.getNextSibling();
				equalityExpr_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = equalityExpr_AST;
		_retTree = _t;
	}
	
	protected final void relationalExpr(AST _t) throws RecognitionException {
		
		TNode relationalExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode relationalExpr_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LT:
			{
				AST __t1287 = _t;
				TNode tmp136_AST = null;
				TNode tmp136_AST_in = null;
				tmp136_AST = (TNode)astFactory.create((TNode)_t);
				tmp136_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp136_AST);
				ASTPair __currentAST1287 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LT);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1287;
				_t = __t1287;
				_t = _t.getNextSibling();
				relationalExpr_AST = (TNode)currentAST.root;
				break;
			}
			case LTE:
			{
				AST __t1288 = _t;
				TNode tmp137_AST = null;
				TNode tmp137_AST_in = null;
				tmp137_AST = (TNode)astFactory.create((TNode)_t);
				tmp137_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp137_AST);
				ASTPair __currentAST1288 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LTE);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1288;
				_t = __t1288;
				_t = _t.getNextSibling();
				relationalExpr_AST = (TNode)currentAST.root;
				break;
			}
			case GT:
			{
				AST __t1289 = _t;
				TNode tmp138_AST = null;
				TNode tmp138_AST_in = null;
				tmp138_AST = (TNode)astFactory.create((TNode)_t);
				tmp138_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp138_AST);
				ASTPair __currentAST1289 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,GT);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1289;
				_t = __t1289;
				_t = _t.getNextSibling();
				relationalExpr_AST = (TNode)currentAST.root;
				break;
			}
			case GTE:
			{
				AST __t1290 = _t;
				TNode tmp139_AST = null;
				TNode tmp139_AST_in = null;
				tmp139_AST = (TNode)astFactory.create((TNode)_t);
				tmp139_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp139_AST);
				ASTPair __currentAST1290 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,GTE);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1290;
				_t = __t1290;
				_t = _t.getNextSibling();
				relationalExpr_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = relationalExpr_AST;
		_retTree = _t;
	}
	
	protected final void shiftExpr(AST _t) throws RecognitionException {
		
		TNode shiftExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode shiftExpr_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LSHIFT:
			{
				AST __t1292 = _t;
				TNode tmp140_AST = null;
				TNode tmp140_AST_in = null;
				tmp140_AST = (TNode)astFactory.create((TNode)_t);
				tmp140_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp140_AST);
				ASTPair __currentAST1292 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LSHIFT);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1292;
				_t = __t1292;
				_t = _t.getNextSibling();
				shiftExpr_AST = (TNode)currentAST.root;
				break;
			}
			case RSHIFT:
			{
				AST __t1293 = _t;
				TNode tmp141_AST = null;
				TNode tmp141_AST_in = null;
				tmp141_AST = (TNode)astFactory.create((TNode)_t);
				tmp141_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp141_AST);
				ASTPair __currentAST1293 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,RSHIFT);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1293;
				_t = __t1293;
				_t = _t.getNextSibling();
				shiftExpr_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = shiftExpr_AST;
		_retTree = _t;
	}
	
	protected final void additiveExpr(AST _t) throws RecognitionException {
		
		TNode additiveExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode additiveExpr_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case PLUS:
			{
				AST __t1295 = _t;
				TNode tmp142_AST = null;
				TNode tmp142_AST_in = null;
				tmp142_AST = (TNode)astFactory.create((TNode)_t);
				tmp142_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp142_AST);
				ASTPair __currentAST1295 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,PLUS);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1295;
				_t = __t1295;
				_t = _t.getNextSibling();
				additiveExpr_AST = (TNode)currentAST.root;
				break;
			}
			case MINUS:
			{
				AST __t1296 = _t;
				TNode tmp143_AST = null;
				TNode tmp143_AST_in = null;
				tmp143_AST = (TNode)astFactory.create((TNode)_t);
				tmp143_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp143_AST);
				ASTPair __currentAST1296 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,MINUS);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1296;
				_t = __t1296;
				_t = _t.getNextSibling();
				additiveExpr_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = additiveExpr_AST;
		_retTree = _t;
	}
	
	protected final void multExpr(AST _t) throws RecognitionException {
		
		TNode multExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode multExpr_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case STAR:
			{
				AST __t1298 = _t;
				TNode tmp144_AST = null;
				TNode tmp144_AST_in = null;
				tmp144_AST = (TNode)astFactory.create((TNode)_t);
				tmp144_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp144_AST);
				ASTPair __currentAST1298 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,STAR);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1298;
				_t = __t1298;
				_t = _t.getNextSibling();
				multExpr_AST = (TNode)currentAST.root;
				break;
			}
			case DIV:
			{
				AST __t1299 = _t;
				TNode tmp145_AST = null;
				TNode tmp145_AST_in = null;
				tmp145_AST = (TNode)astFactory.create((TNode)_t);
				tmp145_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp145_AST);
				ASTPair __currentAST1299 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,DIV);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1299;
				_t = __t1299;
				_t = _t.getNextSibling();
				multExpr_AST = (TNode)currentAST.root;
				break;
			}
			case MOD:
			{
				AST __t1300 = _t;
				TNode tmp146_AST = null;
				TNode tmp146_AST_in = null;
				tmp146_AST = (TNode)astFactory.create((TNode)_t);
				tmp146_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp146_AST);
				ASTPair __currentAST1300 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,MOD);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1300;
				_t = __t1300;
				_t = _t.getNextSibling();
				multExpr_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = multExpr_AST;
		_retTree = _t;
	}
	
	protected final void castExpr(AST _t) throws RecognitionException {
		
		TNode castExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode castExpr_AST = null;
		
		try {      // for error handling
			AST __t1302 = _t;
			TNode tmp147_AST = null;
			TNode tmp147_AST_in = null;
			tmp147_AST = (TNode)astFactory.create((TNode)_t);
			tmp147_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp147_AST);
			ASTPair __currentAST1302 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NCast);
			_t = _t.getFirstChild();
			typeName(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			TNode tmp148_AST_in = null;
			match(_t,RPAREN);
			_t = _t.getNextSibling();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1302;
			_t = __t1302;
			_t = _t.getNextSibling();
			castExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = castExpr_AST;
		_retTree = _t;
	}
	
	protected final void unaryExpr(AST _t) throws RecognitionException {
		
		TNode unaryExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode unaryExpr_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case INC:
			{
				AST __t1321 = _t;
				TNode tmp149_AST = null;
				TNode tmp149_AST_in = null;
				tmp149_AST = (TNode)astFactory.create((TNode)_t);
				tmp149_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp149_AST);
				ASTPair __currentAST1321 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,INC);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1321;
				_t = __t1321;
				_t = _t.getNextSibling();
				unaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case DEC:
			{
				AST __t1322 = _t;
				TNode tmp150_AST = null;
				TNode tmp150_AST_in = null;
				tmp150_AST = (TNode)astFactory.create((TNode)_t);
				tmp150_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp150_AST);
				ASTPair __currentAST1322 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,DEC);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1322;
				_t = __t1322;
				_t = _t.getNextSibling();
				unaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case NUnaryExpr:
			{
				AST __t1323 = _t;
				TNode tmp151_AST = null;
				TNode tmp151_AST_in = null;
				tmp151_AST = (TNode)astFactory.create((TNode)_t);
				tmp151_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp151_AST);
				ASTPair __currentAST1323 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NUnaryExpr);
				_t = _t.getFirstChild();
				unaryOperator(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1323;
				_t = __t1323;
				_t = _t.getNextSibling();
				unaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL_sizeof:
			{
				AST __t1324 = _t;
				TNode tmp152_AST = null;
				TNode tmp152_AST_in = null;
				tmp152_AST = (TNode)astFactory.create((TNode)_t);
				tmp152_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp152_AST);
				ASTPair __currentAST1324 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL_sizeof);
				_t = _t.getFirstChild();
				{
				boolean synPredMatched1327 = false;
				if (((_t.getType()==LPAREN))) {
					AST __t1327 = _t;
					synPredMatched1327 = true;
					inputState.guessing++;
					try {
						{
						match(_t,LPAREN);
						_t = _t.getNextSibling();
						typeName(_t);
						_t = _retTree;
						}
					}
					catch (RecognitionException pe) {
						synPredMatched1327 = false;
					}
					_t = __t1327;
					inputState.guessing--;
				}
				if ( synPredMatched1327 ) {
					TNode tmp153_AST = null;
					TNode tmp153_AST_in = null;
					tmp153_AST = (TNode)astFactory.create((TNode)_t);
					tmp153_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp153_AST);
					match(_t,LPAREN);
					_t = _t.getNextSibling();
					typeName(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					TNode tmp154_AST = null;
					TNode tmp154_AST_in = null;
					tmp154_AST = (TNode)astFactory.create((TNode)_t);
					tmp154_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp154_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
				}
				else if ((_t.getType()==ID||_t.getType()==ASSIGN||_t.getType()==STAR||_t.getType()==LPAREN||_t.getType()==DIV_ASSIGN||_t.getType()==PLUS_ASSIGN||_t.getType()==MINUS_ASSIGN||_t.getType()==STAR_ASSIGN||_t.getType()==MOD_ASSIGN||_t.getType()==RSHIFT_ASSIGN||_t.getType()==LSHIFT_ASSIGN||_t.getType()==BAND_ASSIGN||_t.getType()==BOR_ASSIGN||_t.getType()==BXOR_ASSIGN||_t.getType()==QUESTION||_t.getType()==LOR||_t.getType()==LAND||_t.getType()==BOR||_t.getType()==BXOR||_t.getType()==BAND||_t.getType()==EQUAL||_t.getType()==NOT_EQUAL||_t.getType()==LT||_t.getType()==LTE||_t.getType()==GT||_t.getType()==GTE||_t.getType()==LSHIFT||_t.getType()==RSHIFT||_t.getType()==PLUS||_t.getType()==MINUS||_t.getType()==DIV||_t.getType()==MOD||_t.getType()==INC||_t.getType()==DEC||_t.getType()==LITERAL_sizeof||_t.getType()==CharLiteral||_t.getType()==NCast||_t.getType()==NExpressionGroup||_t.getType()==NInitializer||_t.getType()==NEmptyExpression||_t.getType()==NCommaExpr||_t.getType()==NUnaryExpr||_t.getType()==NPostfixExpr||_t.getType()==NRangeExpr||_t.getType()==NStringSeq||_t.getType()==NLcurlyInitializer||_t.getType()==NGnuAsmExpr||_t.getType()==Number||_t.getType()==LITERAL___alignof)) {
					expr(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					throw new NoViableAltException(_t);
				}
				
				}
				currentAST = __currentAST1324;
				_t = __t1324;
				_t = _t.getNextSibling();
				unaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL___alignof:
			{
				AST __t1328 = _t;
				TNode tmp155_AST = null;
				TNode tmp155_AST_in = null;
				tmp155_AST = (TNode)astFactory.create((TNode)_t);
				tmp155_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp155_AST);
				ASTPair __currentAST1328 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,LITERAL___alignof);
				_t = _t.getFirstChild();
				{
				boolean synPredMatched1331 = false;
				if (((_t.getType()==LPAREN))) {
					AST __t1331 = _t;
					synPredMatched1331 = true;
					inputState.guessing++;
					try {
						{
						match(_t,LPAREN);
						_t = _t.getNextSibling();
						typeName(_t);
						_t = _retTree;
						}
					}
					catch (RecognitionException pe) {
						synPredMatched1331 = false;
					}
					_t = __t1331;
					inputState.guessing--;
				}
				if ( synPredMatched1331 ) {
					TNode tmp156_AST = null;
					TNode tmp156_AST_in = null;
					tmp156_AST = (TNode)astFactory.create((TNode)_t);
					tmp156_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp156_AST);
					match(_t,LPAREN);
					_t = _t.getNextSibling();
					typeName(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					TNode tmp157_AST = null;
					TNode tmp157_AST_in = null;
					tmp157_AST = (TNode)astFactory.create((TNode)_t);
					tmp157_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp157_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
				}
				else if ((_t.getType()==ID||_t.getType()==ASSIGN||_t.getType()==STAR||_t.getType()==LPAREN||_t.getType()==DIV_ASSIGN||_t.getType()==PLUS_ASSIGN||_t.getType()==MINUS_ASSIGN||_t.getType()==STAR_ASSIGN||_t.getType()==MOD_ASSIGN||_t.getType()==RSHIFT_ASSIGN||_t.getType()==LSHIFT_ASSIGN||_t.getType()==BAND_ASSIGN||_t.getType()==BOR_ASSIGN||_t.getType()==BXOR_ASSIGN||_t.getType()==QUESTION||_t.getType()==LOR||_t.getType()==LAND||_t.getType()==BOR||_t.getType()==BXOR||_t.getType()==BAND||_t.getType()==EQUAL||_t.getType()==NOT_EQUAL||_t.getType()==LT||_t.getType()==LTE||_t.getType()==GT||_t.getType()==GTE||_t.getType()==LSHIFT||_t.getType()==RSHIFT||_t.getType()==PLUS||_t.getType()==MINUS||_t.getType()==DIV||_t.getType()==MOD||_t.getType()==INC||_t.getType()==DEC||_t.getType()==LITERAL_sizeof||_t.getType()==CharLiteral||_t.getType()==NCast||_t.getType()==NExpressionGroup||_t.getType()==NInitializer||_t.getType()==NEmptyExpression||_t.getType()==NCommaExpr||_t.getType()==NUnaryExpr||_t.getType()==NPostfixExpr||_t.getType()==NRangeExpr||_t.getType()==NStringSeq||_t.getType()==NLcurlyInitializer||_t.getType()==NGnuAsmExpr||_t.getType()==Number||_t.getType()==LITERAL___alignof)) {
					expr(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					throw new NoViableAltException(_t);
				}
				
				}
				currentAST = __currentAST1328;
				_t = __t1328;
				_t = _t.getNextSibling();
				unaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = unaryExpr_AST;
		_retTree = _t;
	}
	
	protected final void postfixExpr(AST _t) throws RecognitionException {
		
		TNode postfixExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode postfixExpr_AST = null;
		
		try {      // for error handling
			AST __t1334 = _t;
			TNode tmp158_AST = null;
			TNode tmp158_AST_in = null;
			tmp158_AST = (TNode)astFactory.create((TNode)_t);
			tmp158_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp158_AST);
			ASTPair __currentAST1334 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NPostfixExpr);
			_t = _t.getFirstChild();
			primaryExpr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			int _cnt1338=0;
			_loop1338:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case PTR:
				{
					TNode tmp159_AST = null;
					TNode tmp159_AST_in = null;
					tmp159_AST = (TNode)astFactory.create((TNode)_t);
					tmp159_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp159_AST);
					match(_t,PTR);
					_t = _t.getNextSibling();
					TNode tmp160_AST = null;
					TNode tmp160_AST_in = null;
					tmp160_AST = (TNode)astFactory.create((TNode)_t);
					tmp160_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp160_AST);
					match(_t,ID);
					_t = _t.getNextSibling();
					break;
				}
				case DOT:
				{
					TNode tmp161_AST = null;
					TNode tmp161_AST_in = null;
					tmp161_AST = (TNode)astFactory.create((TNode)_t);
					tmp161_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp161_AST);
					match(_t,DOT);
					_t = _t.getNextSibling();
					TNode tmp162_AST = null;
					TNode tmp162_AST_in = null;
					tmp162_AST = (TNode)astFactory.create((TNode)_t);
					tmp162_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp162_AST);
					match(_t,ID);
					_t = _t.getNextSibling();
					break;
				}
				case NFunctionCallArgs:
				{
					AST __t1336 = _t;
					TNode tmp163_AST = null;
					TNode tmp163_AST_in = null;
					tmp163_AST = (TNode)astFactory.create((TNode)_t);
					tmp163_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp163_AST);
					ASTPair __currentAST1336 = currentAST.copy();
					currentAST.root = currentAST.child;
					currentAST.child = null;
					match(_t,NFunctionCallArgs);
					_t = _t.getFirstChild();
					{
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case ID:
					case ASSIGN:
					case STAR:
					case LPAREN:
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
					case INC:
					case DEC:
					case LITERAL_sizeof:
					case CharLiteral:
					case NCast:
					case NExpressionGroup:
					case NInitializer:
					case NEmptyExpression:
					case NCommaExpr:
					case NUnaryExpr:
					case NPostfixExpr:
					case NRangeExpr:
					case NStringSeq:
					case NLcurlyInitializer:
					case NGnuAsmExpr:
					case Number:
					case LITERAL___alignof:
					{
						argExprList(_t);
						_t = _retTree;
						astFactory.addASTChild(currentAST, returnAST);
						break;
					}
					case RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(_t);
					}
					}
					}
					TNode tmp164_AST = null;
					TNode tmp164_AST_in = null;
					tmp164_AST = (TNode)astFactory.create((TNode)_t);
					tmp164_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp164_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
					currentAST = __currentAST1336;
					_t = __t1336;
					_t = _t.getNextSibling();
					break;
				}
				case LBRACKET:
				{
					TNode tmp165_AST = null;
					TNode tmp165_AST_in = null;
					tmp165_AST = (TNode)astFactory.create((TNode)_t);
					tmp165_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp165_AST);
					match(_t,LBRACKET);
					_t = _t.getNextSibling();
					expr(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					TNode tmp166_AST = null;
					TNode tmp166_AST_in = null;
					tmp166_AST = (TNode)astFactory.create((TNode)_t);
					tmp166_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp166_AST);
					match(_t,RBRACKET);
					_t = _t.getNextSibling();
					break;
				}
				case INC:
				{
					TNode tmp167_AST = null;
					TNode tmp167_AST_in = null;
					tmp167_AST = (TNode)astFactory.create((TNode)_t);
					tmp167_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp167_AST);
					match(_t,INC);
					_t = _t.getNextSibling();
					break;
				}
				case DEC:
				{
					TNode tmp168_AST = null;
					TNode tmp168_AST_in = null;
					tmp168_AST = (TNode)astFactory.create((TNode)_t);
					tmp168_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp168_AST);
					match(_t,DEC);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					if ( _cnt1338>=1 ) { break _loop1338; } else {throw new NoViableAltException(_t);}
				}
				}
				_cnt1338++;
			} while (true);
			}
			currentAST = __currentAST1334;
			_t = __t1334;
			_t = _t.getNextSibling();
			postfixExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = postfixExpr_AST;
		_retTree = _t;
	}
	
	protected final void primaryExpr(AST _t) throws RecognitionException {
		
		TNode primaryExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode primaryExpr_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ID:
			{
				TNode tmp169_AST = null;
				TNode tmp169_AST_in = null;
				tmp169_AST = (TNode)astFactory.create((TNode)_t);
				tmp169_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp169_AST);
				match(_t,ID);
				_t = _t.getNextSibling();
				primaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case Number:
			{
				TNode tmp170_AST = null;
				TNode tmp170_AST_in = null;
				tmp170_AST = (TNode)astFactory.create((TNode)_t);
				tmp170_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp170_AST);
				match(_t,Number);
				_t = _t.getNextSibling();
				primaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case CharLiteral:
			{
				charConst(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case NStringSeq:
			{
				stringConst(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			case NExpressionGroup:
			{
				AST __t1340 = _t;
				TNode tmp171_AST = null;
				TNode tmp171_AST_in = null;
				tmp171_AST = (TNode)astFactory.create((TNode)_t);
				tmp171_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp171_AST);
				ASTPair __currentAST1340 = currentAST.copy();
				currentAST.root = currentAST.child;
				currentAST.child = null;
				match(_t,NExpressionGroup);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				currentAST = __currentAST1340;
				_t = __t1340;
				_t = _t.getNextSibling();
				primaryExpr_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = primaryExpr_AST;
		_retTree = _t;
	}
	
	protected final void commaExpr(AST _t) throws RecognitionException {
		
		TNode commaExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode commaExpr_AST = null;
		
		try {      // for error handling
			AST __t1236 = _t;
			TNode tmp172_AST = null;
			TNode tmp172_AST_in = null;
			tmp172_AST = (TNode)astFactory.create((TNode)_t);
			tmp172_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp172_AST);
			ASTPair __currentAST1236 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NCommaExpr);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1236;
			_t = __t1236;
			_t = _t.getNextSibling();
			commaExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = commaExpr_AST;
		_retTree = _t;
	}
	
	protected final void emptyExpr(AST _t) throws RecognitionException {
		
		TNode emptyExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode emptyExpr_AST = null;
		
		try {      // for error handling
			TNode tmp173_AST = null;
			TNode tmp173_AST_in = null;
			tmp173_AST = (TNode)astFactory.create((TNode)_t);
			tmp173_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp173_AST);
			match(_t,NEmptyExpression);
			_t = _t.getNextSibling();
			emptyExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = emptyExpr_AST;
		_retTree = _t;
	}
	
	protected final void compoundStatementExpr(AST _t) throws RecognitionException {
		
		TNode compoundStatementExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode compoundStatementExpr_AST = null;
		
		try {      // for error handling
			AST __t1239 = _t;
			TNode tmp174_AST = null;
			TNode tmp174_AST_in = null;
			tmp174_AST = (TNode)astFactory.create((TNode)_t);
			tmp174_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp174_AST);
			ASTPair __currentAST1239 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LPAREN);
			_t = _t.getFirstChild();
			compoundStatement(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			TNode tmp175_AST = null;
			TNode tmp175_AST_in = null;
			tmp175_AST = (TNode)astFactory.create((TNode)_t);
			tmp175_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp175_AST);
			match(_t,RPAREN);
			_t = _t.getNextSibling();
			currentAST = __currentAST1239;
			_t = __t1239;
			_t = _t.getNextSibling();
			compoundStatementExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = compoundStatementExpr_AST;
		_retTree = _t;
	}
	
	protected final void rangeExpr(AST _t) throws RecognitionException {
		
		TNode rangeExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode rangeExpr_AST = null;
		
		try {      // for error handling
			AST __t1241 = _t;
			TNode tmp176_AST = null;
			TNode tmp176_AST_in = null;
			tmp176_AST = (TNode)astFactory.create((TNode)_t);
			tmp176_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp176_AST);
			ASTPair __currentAST1241 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NRangeExpr);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			TNode tmp177_AST = null;
			TNode tmp177_AST_in = null;
			tmp177_AST = (TNode)astFactory.create((TNode)_t);
			tmp177_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp177_AST);
			match(_t,VARARGS);
			_t = _t.getNextSibling();
			expr(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST1241;
			_t = __t1241;
			_t = _t.getNextSibling();
			rangeExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = rangeExpr_AST;
		_retTree = _t;
	}
	
	protected final void gnuAsmExpr(AST _t) throws RecognitionException {
		
		TNode gnuAsmExpr_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode gnuAsmExpr_AST = null;
		
		try {      // for error handling
			AST __t1243 = _t;
			TNode tmp178_AST = null;
			TNode tmp178_AST_in = null;
			tmp178_AST = (TNode)astFactory.create((TNode)_t);
			tmp178_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp178_AST);
			ASTPair __currentAST1243 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NGnuAsmExpr);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LITERAL_volatile:
			{
				TNode tmp179_AST = null;
				TNode tmp179_AST_in = null;
				tmp179_AST = (TNode)astFactory.create((TNode)_t);
				tmp179_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp179_AST);
				match(_t,LITERAL_volatile);
				_t = _t.getNextSibling();
				break;
			}
			case LPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			TNode tmp180_AST = null;
			TNode tmp180_AST_in = null;
			tmp180_AST = (TNode)astFactory.create((TNode)_t);
			tmp180_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp180_AST);
			match(_t,LPAREN);
			_t = _t.getNextSibling();
			stringConst(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==COLON)) {
				TNode tmp181_AST = null;
				TNode tmp181_AST_in = null;
				tmp181_AST = (TNode)astFactory.create((TNode)_t);
				tmp181_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp181_AST);
				match(_t,COLON);
				_t = _t.getNextSibling();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case NStringSeq:
				{
					strOptExprPair(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
					{
					_loop1248:
					do {
						if (_t==null) _t=ASTNULL;
						if ((_t.getType()==COMMA)) {
							TNode tmp182_AST = null;
							TNode tmp182_AST_in = null;
							tmp182_AST = (TNode)astFactory.create((TNode)_t);
							tmp182_AST_in = (TNode)_t;
							astFactory.addASTChild(currentAST, tmp182_AST);
							match(_t,COMMA);
							_t = _t.getNextSibling();
							strOptExprPair(_t);
							_t = _retTree;
							astFactory.addASTChild(currentAST, returnAST);
						}
						else {
							break _loop1248;
						}
						
					} while (true);
					}
					break;
				}
				case COLON:
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				{
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==COLON)) {
					TNode tmp183_AST = null;
					TNode tmp183_AST_in = null;
					tmp183_AST = (TNode)astFactory.create((TNode)_t);
					tmp183_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp183_AST);
					match(_t,COLON);
					_t = _t.getNextSibling();
					{
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case NStringSeq:
					{
						strOptExprPair(_t);
						_t = _retTree;
						astFactory.addASTChild(currentAST, returnAST);
						{
						_loop1252:
						do {
							if (_t==null) _t=ASTNULL;
							if ((_t.getType()==COMMA)) {
								TNode tmp184_AST = null;
								TNode tmp184_AST_in = null;
								tmp184_AST = (TNode)astFactory.create((TNode)_t);
								tmp184_AST_in = (TNode)_t;
								astFactory.addASTChild(currentAST, tmp184_AST);
								match(_t,COMMA);
								_t = _t.getNextSibling();
								strOptExprPair(_t);
								_t = _retTree;
								astFactory.addASTChild(currentAST, returnAST);
							}
							else {
								break _loop1252;
							}
							
						} while (true);
						}
						break;
					}
					case COLON:
					case RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(_t);
					}
					}
					}
				}
				else if ((_t.getType()==COLON||_t.getType()==RPAREN)) {
				}
				else {
					throw new NoViableAltException(_t);
				}
				
				}
			}
			else if ((_t.getType()==COLON||_t.getType()==RPAREN)) {
			}
			else {
				throw new NoViableAltException(_t);
			}
			
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case COLON:
			{
				TNode tmp185_AST = null;
				TNode tmp185_AST_in = null;
				tmp185_AST = (TNode)astFactory.create((TNode)_t);
				tmp185_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp185_AST);
				match(_t,COLON);
				_t = _t.getNextSibling();
				stringConst(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop1255:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==COMMA)) {
						TNode tmp186_AST = null;
						TNode tmp186_AST_in = null;
						tmp186_AST = (TNode)astFactory.create((TNode)_t);
						tmp186_AST_in = (TNode)_t;
						astFactory.addASTChild(currentAST, tmp186_AST);
						match(_t,COMMA);
						_t = _t.getNextSibling();
						stringConst(_t);
						_t = _retTree;
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop1255;
					}
					
				} while (true);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			TNode tmp187_AST = null;
			TNode tmp187_AST_in = null;
			tmp187_AST = (TNode)astFactory.create((TNode)_t);
			tmp187_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp187_AST);
			match(_t,RPAREN);
			_t = _t.getNextSibling();
			currentAST = __currentAST1243;
			_t = __t1243;
			_t = _t.getNextSibling();
			gnuAsmExpr_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = gnuAsmExpr_AST;
		_retTree = _t;
	}
	
	protected final void stringConst(AST _t) throws RecognitionException {
		
		TNode stringConst_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode stringConst_AST = null;
		
		try {      // for error handling
			AST __t1346 = _t;
			TNode tmp188_AST = null;
			TNode tmp188_AST_in = null;
			tmp188_AST = (TNode)astFactory.create((TNode)_t);
			tmp188_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp188_AST);
			ASTPair __currentAST1346 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NStringSeq);
			_t = _t.getFirstChild();
			{
			int _cnt1348=0;
			_loop1348:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==StringLiteral)) {
					TNode tmp189_AST = null;
					TNode tmp189_AST_in = null;
					tmp189_AST = (TNode)astFactory.create((TNode)_t);
					tmp189_AST_in = (TNode)_t;
					astFactory.addASTChild(currentAST, tmp189_AST);
					match(_t,StringLiteral);
					_t = _t.getNextSibling();
				}
				else {
					if ( _cnt1348>=1 ) { break _loop1348; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt1348++;
			} while (true);
			}
			currentAST = __currentAST1346;
			_t = __t1346;
			_t = _t.getNextSibling();
			stringConst_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = stringConst_AST;
		_retTree = _t;
	}
	
	protected final void strOptExprPair(AST _t) throws RecognitionException {
		
		TNode strOptExprPair_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode strOptExprPair_AST = null;
		
		try {      // for error handling
			stringConst(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LPAREN:
			{
				TNode tmp190_AST = null;
				TNode tmp190_AST_in = null;
				tmp190_AST = (TNode)astFactory.create((TNode)_t);
				tmp190_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp190_AST);
				match(_t,LPAREN);
				_t = _t.getNextSibling();
				expr(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				TNode tmp191_AST = null;
				TNode tmp191_AST_in = null;
				tmp191_AST = (TNode)astFactory.create((TNode)_t);
				tmp191_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp191_AST);
				match(_t,RPAREN);
				_t = _t.getNextSibling();
				break;
			}
			case COMMA:
			case COLON:
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			strOptExprPair_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = strOptExprPair_AST;
		_retTree = _t;
	}
	
	protected final void unaryOperator(AST _t) throws RecognitionException {
		
		TNode unaryOperator_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode unaryOperator_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case BAND:
			{
				TNode tmp192_AST = null;
				TNode tmp192_AST_in = null;
				tmp192_AST = (TNode)astFactory.create((TNode)_t);
				tmp192_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp192_AST);
				match(_t,BAND);
				_t = _t.getNextSibling();
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case STAR:
			{
				TNode tmp193_AST = null;
				TNode tmp193_AST_in = null;
				tmp193_AST = (TNode)astFactory.create((TNode)_t);
				tmp193_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp193_AST);
				match(_t,STAR);
				_t = _t.getNextSibling();
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case PLUS:
			{
				TNode tmp194_AST = null;
				TNode tmp194_AST_in = null;
				tmp194_AST = (TNode)astFactory.create((TNode)_t);
				tmp194_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp194_AST);
				match(_t,PLUS);
				_t = _t.getNextSibling();
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case MINUS:
			{
				TNode tmp195_AST = null;
				TNode tmp195_AST_in = null;
				tmp195_AST = (TNode)astFactory.create((TNode)_t);
				tmp195_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp195_AST);
				match(_t,MINUS);
				_t = _t.getNextSibling();
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case BNOT:
			{
				TNode tmp196_AST = null;
				TNode tmp196_AST_in = null;
				tmp196_AST = (TNode)astFactory.create((TNode)_t);
				tmp196_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp196_AST);
				match(_t,BNOT);
				_t = _t.getNextSibling();
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case LNOT:
			{
				TNode tmp197_AST = null;
				TNode tmp197_AST_in = null;
				tmp197_AST = (TNode)astFactory.create((TNode)_t);
				tmp197_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp197_AST);
				match(_t,LNOT);
				_t = _t.getNextSibling();
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case LAND:
			{
				TNode tmp198_AST = null;
				TNode tmp198_AST_in = null;
				tmp198_AST = (TNode)astFactory.create((TNode)_t);
				tmp198_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp198_AST);
				match(_t,LAND);
				_t = _t.getNextSibling();
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL___real:
			{
				TNode tmp199_AST = null;
				TNode tmp199_AST_in = null;
				tmp199_AST = (TNode)astFactory.create((TNode)_t);
				tmp199_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp199_AST);
				match(_t,LITERAL___real);
				_t = _t.getNextSibling();
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			case LITERAL___imag:
			{
				TNode tmp200_AST = null;
				TNode tmp200_AST_in = null;
				tmp200_AST = (TNode)astFactory.create((TNode)_t);
				tmp200_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp200_AST);
				match(_t,LITERAL___imag);
				_t = _t.getNextSibling();
				unaryOperator_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = unaryOperator_AST;
		_retTree = _t;
	}
	
	protected final void argExprList(AST _t) throws RecognitionException {
		
		TNode argExprList_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode argExprList_AST = null;
		
		try {      // for error handling
			{
			int _cnt1343=0;
			_loop1343:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ID||_t.getType()==ASSIGN||_t.getType()==STAR||_t.getType()==LPAREN||_t.getType()==DIV_ASSIGN||_t.getType()==PLUS_ASSIGN||_t.getType()==MINUS_ASSIGN||_t.getType()==STAR_ASSIGN||_t.getType()==MOD_ASSIGN||_t.getType()==RSHIFT_ASSIGN||_t.getType()==LSHIFT_ASSIGN||_t.getType()==BAND_ASSIGN||_t.getType()==BOR_ASSIGN||_t.getType()==BXOR_ASSIGN||_t.getType()==QUESTION||_t.getType()==LOR||_t.getType()==LAND||_t.getType()==BOR||_t.getType()==BXOR||_t.getType()==BAND||_t.getType()==EQUAL||_t.getType()==NOT_EQUAL||_t.getType()==LT||_t.getType()==LTE||_t.getType()==GT||_t.getType()==GTE||_t.getType()==LSHIFT||_t.getType()==RSHIFT||_t.getType()==PLUS||_t.getType()==MINUS||_t.getType()==DIV||_t.getType()==MOD||_t.getType()==INC||_t.getType()==DEC||_t.getType()==LITERAL_sizeof||_t.getType()==CharLiteral||_t.getType()==NCast||_t.getType()==NExpressionGroup||_t.getType()==NInitializer||_t.getType()==NEmptyExpression||_t.getType()==NCommaExpr||_t.getType()==NUnaryExpr||_t.getType()==NPostfixExpr||_t.getType()==NRangeExpr||_t.getType()==NStringSeq||_t.getType()==NLcurlyInitializer||_t.getType()==NGnuAsmExpr||_t.getType()==Number||_t.getType()==LITERAL___alignof)) {
					expr(_t);
					_t = _retTree;
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt1343>=1 ) { break _loop1343; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt1343++;
			} while (true);
			}
			argExprList_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = argExprList_AST;
		_retTree = _t;
	}
	
	protected final void charConst(AST _t) throws RecognitionException {
		
		TNode charConst_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode charConst_AST = null;
		
		try {      // for error handling
			TNode tmp201_AST = null;
			TNode tmp201_AST_in = null;
			tmp201_AST = (TNode)astFactory.create((TNode)_t);
			tmp201_AST_in = (TNode)_t;
			astFactory.addASTChild(currentAST, tmp201_AST);
			match(_t,CharLiteral);
			_t = _t.getNextSibling();
			charConst_AST = (TNode)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = charConst_AST;
		_retTree = _t;
	}
	
	protected final void intConst(AST _t) throws RecognitionException {
		
		TNode intConst_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode intConst_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IntOctalConst:
			{
				TNode tmp202_AST = null;
				TNode tmp202_AST_in = null;
				tmp202_AST = (TNode)astFactory.create((TNode)_t);
				tmp202_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp202_AST);
				match(_t,IntOctalConst);
				_t = _t.getNextSibling();
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case LongOctalConst:
			{
				TNode tmp203_AST = null;
				TNode tmp203_AST_in = null;
				tmp203_AST = (TNode)astFactory.create((TNode)_t);
				tmp203_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp203_AST);
				match(_t,LongOctalConst);
				_t = _t.getNextSibling();
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case UnsignedOctalConst:
			{
				TNode tmp204_AST = null;
				TNode tmp204_AST_in = null;
				tmp204_AST = (TNode)astFactory.create((TNode)_t);
				tmp204_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp204_AST);
				match(_t,UnsignedOctalConst);
				_t = _t.getNextSibling();
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case IntIntConst:
			{
				TNode tmp205_AST = null;
				TNode tmp205_AST_in = null;
				tmp205_AST = (TNode)astFactory.create((TNode)_t);
				tmp205_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp205_AST);
				match(_t,IntIntConst);
				_t = _t.getNextSibling();
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case LongIntConst:
			{
				TNode tmp206_AST = null;
				TNode tmp206_AST_in = null;
				tmp206_AST = (TNode)astFactory.create((TNode)_t);
				tmp206_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp206_AST);
				match(_t,LongIntConst);
				_t = _t.getNextSibling();
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case UnsignedIntConst:
			{
				TNode tmp207_AST = null;
				TNode tmp207_AST_in = null;
				tmp207_AST = (TNode)astFactory.create((TNode)_t);
				tmp207_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp207_AST);
				match(_t,UnsignedIntConst);
				_t = _t.getNextSibling();
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case IntHexConst:
			{
				TNode tmp208_AST = null;
				TNode tmp208_AST_in = null;
				tmp208_AST = (TNode)astFactory.create((TNode)_t);
				tmp208_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp208_AST);
				match(_t,IntHexConst);
				_t = _t.getNextSibling();
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case LongHexConst:
			{
				TNode tmp209_AST = null;
				TNode tmp209_AST_in = null;
				tmp209_AST = (TNode)astFactory.create((TNode)_t);
				tmp209_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp209_AST);
				match(_t,LongHexConst);
				_t = _t.getNextSibling();
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			case UnsignedHexConst:
			{
				TNode tmp210_AST = null;
				TNode tmp210_AST_in = null;
				tmp210_AST = (TNode)astFactory.create((TNode)_t);
				tmp210_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp210_AST);
				match(_t,UnsignedHexConst);
				_t = _t.getNextSibling();
				intConst_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = intConst_AST;
		_retTree = _t;
	}
	
	protected final void floatConst(AST _t) throws RecognitionException {
		
		TNode floatConst_AST_in = (TNode)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		TNode floatConst_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case FloatDoubleConst:
			{
				TNode tmp211_AST = null;
				TNode tmp211_AST_in = null;
				tmp211_AST = (TNode)astFactory.create((TNode)_t);
				tmp211_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp211_AST);
				match(_t,FloatDoubleConst);
				_t = _t.getNextSibling();
				floatConst_AST = (TNode)currentAST.root;
				break;
			}
			case DoubleDoubleConst:
			{
				TNode tmp212_AST = null;
				TNode tmp212_AST_in = null;
				tmp212_AST = (TNode)astFactory.create((TNode)_t);
				tmp212_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp212_AST);
				match(_t,DoubleDoubleConst);
				_t = _t.getNextSibling();
				floatConst_AST = (TNode)currentAST.root;
				break;
			}
			case LongDoubleConst:
			{
				TNode tmp213_AST = null;
				TNode tmp213_AST_in = null;
				tmp213_AST = (TNode)astFactory.create((TNode)_t);
				tmp213_AST_in = (TNode)_t;
				astFactory.addASTChild(currentAST, tmp213_AST);
				match(_t,LongDoubleConst);
				_t = _t.getNextSibling();
				floatConst_AST = (TNode)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		returnAST = floatConst_AST;
		_retTree = _t;
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
		"IDletter",
		"\"inline\"",
		"\"typeof\"",
		"\"__complex\"",
		"\"__attribute\"",
		"\"__label__\"",
		"\"__alignof\"",
		"\"__real\"",
		"\"__imag\""
	};
	
	}
	
