// $Id: TNodeFactory.java,v 1.2 2002/10/19 20:09:03 weber Exp $
package parser.grammar;

import antlr.ASTFactory;

import antlr.collections.AST;


/**
 * This class extends ASTFactory to build instances of class TNode.
 * @deprecated Dies beruht auf einer älteren Implementation der ASTFactory.
 *             Mittlerweile hat eine ASTFActory weitere Methoden.
 */
public class TNodeFactory
  extends ASTFactory
{

  /**
   * Create a new ampty AST node
   */
  public AST create()
  {
    return new TNode();
  }

  /**
   * Create a new AST node from type and text
   */
  public AST create(int ttype, String text)
  {
    AST ast = new TNode();
    ast.setType(ttype);
    ast.setText(text);
    return ast;
  }

  /**
   * Create a new AST node from an existing AST node
   *
   * @return null if ast is null.
   */
  public AST create(AST ast)
  {

    if(ast == null) {
      return null;
    }

    AST newast = new TNode();
    newast.setType(ast.getType());
    newast.setText(ast.getText());
    return newast;
  }
}