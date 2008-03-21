package parser;
/**
 * Title:        Tessy C Parser
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Razorcat Development GmbH
 * @author
 * @version 1.0
 */

// from java
import java.io.*;
import java.util.*;

// from antlr
import antlr.*;

// from parser
import ast.*;
import idb.*;

public class InterfaceDB {

  //
  public static void main (String   args[]) {
    ArrayList   sources   = new ArrayList();
    File        dbfile    = null;
    String      directory = null,
                module    = null;
    boolean     newdb     = false;
    int         needed    = 3,
                arg       = 0;

    // check arguments
    if (args.length == 0) {
      // show usage
      System.out.println("usage: idb32 [-new] <directory> <module> <filename ...>");
      // and exit
      System.exit(-1);
    }

    // check if new database should be created.
    if      (args[arg].equals("-new")) {
      // create a new database
      newdb = true;
      // skip arg
      arg++;
      // need one more for parameters.
      needed++;
    }
    else if (args[arg].equals("-dump")) {
      // skip arg
      arg++;
      // need only filename.
      needed = 2;
    }

    // check arguments
    if (args.length < needed) {
      // show usage
      System.out.println("usage: idb32 [-new] <directory> <module> <filename ...>");
      System.out.println("       idb32 -dump <filename ...>");
      // and exit
      System.exit(-1);
    }

    // check if dump was requested.
    if (needed > 2) {
      // first parameter after options is the module directory
      directory = args[arg++];

      // next parameter is the module name.
      module = args[arg++];

      // create database file.
      dbfile = new File(directory + File.separator + module + File.separator + "ts_" + module + (newdb ? "_new" : "") + ".idb");
    }

    // next parameters are the files to parse
    for (int i = arg, length = args.length; i < length; i++) {
      sources.add(new File(args[i]));
    }

    try {
      GnuCLexer     lexer  = new GnuCLexer(new FileInputStream((File) sources.get(0)));
      GnuCParser    parser = new GnuCParser(lexer);
      IDBTypeMap    tmap   = new IDBTypeMap();

      // setup lexer
      lexer.setTokenObjectClass("parser.CToken");
      lexer.initialize();

      // setup parser.
      parser.setASTNodeType(ParserAST.class.getName());
      parser.setASTFactory(new ParserASTFactory());
      if (needed > 2) {
        parser.setIDB(new IDB(module, dbfile));
      }

      // setup AST node.
      ParserAST.setTokenVocabulary("parser.GNUCTokenTypes");

      // invoke parser
      parser.translationUnit();
      // create database
      parser.getIDB().write();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}