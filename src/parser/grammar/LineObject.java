// $Id: LineObject.java,v 1.2 2002/10/19 20:09:03 weber Exp $
package parser.grammar;

/**
 * Representiert eine '#line' Direktive für den C-Compiler.<br>
 * Deren Format ist anders als die #line-Preprocessor-direktive. Eine zusätzliche
 * Ziffer gibt den Zustand an.
 *
 */
class LineObject {
  private LineObject parent = null;
  private String source = null;
  int line = 1;

  /** MW: nur eines der flags kann gesetzt sein!*/
  private boolean enteringFile = false;
  private boolean returningToFile = false;
  private boolean systemHeader = false;
  private boolean treatAsC = false;

  /**
   * Default constructor.
   */
  public LineObject()
  {
    super();
  }

  /**
   * Copy constructor.
   */
  public LineObject( LineObject lobj )
  {
    parent = lobj.getParent();
    source = lobj.getSource();
    line = lobj.getLine();
    enteringFile = lobj.isEnteringFile();
    returningToFile = lobj.isReturningToFile();
    systemHeader = lobj.isSystemHeader();
    treatAsC = lobj.isTreatAsC();
  }

  public LineObject( String src)
  {
    source = src;
  }

  public void setSource(String src)
  {
    source = src;
  }

  public String getSource()
  {
    return source;
  }

  public void setParent(LineObject par)
  {
    parent = par;
  }

  public LineObject getParent()
  {
    return parent;
  }

  public void setLine(int l)
  {
    line = l;
  }

  public int getLine()
  {
    return line;
  }

  public void newline()
  {
    line++;
  }

  public void setEnteringFile(boolean v)
  {
    enteringFile = v;
  }

  public boolean isEnteringFile()
  {
    return enteringFile;
  }

  public void setReturningToFile(boolean v)
  {
    returningToFile = v;
  }

  public boolean isReturningToFile()
  {
    return returningToFile;
  }

  public void setSystemHeader(boolean v)
  {
    systemHeader = v;
  }

  public boolean isSystemHeader()
  {
    return systemHeader;
  }

  public void setTreatAsC(boolean v)
  {
    treatAsC = v;
  }

  public boolean isTreatAsC()
  {
    return treatAsC;
  }

  public String toString() {
    StringBuffer ret;
    ret = new StringBuffer("# " + line + " \"" + source + "\"");
    if (enteringFile) {
	ret.append(" 1");
    }
    if (returningToFile) {
	ret.append(" 2");
    }
    if (systemHeader) {
	ret.append(" 3");
    }
    if (treatAsC) {
	ret.append(" 4");
    }
    return ret.toString();
  }
}

