package parser;

import antlr.RecognitionException;


/**
 * Gibt die Fehlermeldungen des Scanners und Parsers aus.
 * 
 * @author weber
 */
public class MessageReporter
{
  public static final int INFO = 0;
  public static final int WARN = 1;
  public static final int ERROR = 2;

  /** 
   */
  public void print( int messageType, RecognitionException ex, 
                      String filename )
  {
    print( messageType, ex.getMessage(), filename );
  }

  /** 
   */
  public void println( int messageType, RecognitionException ex, 
                        String filename )
  {
    print( messageType, ex.getMessage(), filename );
    System.out.println();
  }

  /** 
   */
  public void print( int messageType, String s, String filename )
  {
    String typeTxt = "";

    switch( messageType ) {

      case INFO:
        typeTxt = "info: ";
        break;

      case WARN:
        typeTxt = "warning: ";
        break;

      case ERROR:
        typeTxt = "error: ";
        break;
    }

    if( filename != null ) {
      System.out.print( filename+": " );
    }

    System.out.print( typeTxt+s );
  }

  /** 
   */
  public void println( int messageType, String s, String filename )
  {
    print( messageType, s, filename );
    System.out.println();
  }
}