package parser;

/**
 * Copyright:     Copyright (c) 2002
 * Organisation:  Razorcat Development
 * @author Martin Weber
 * @version 1.0
 */

 /**
  * Enthält den Namen und die Beschreibung eines Sprachdialektes.
  */
public class Dialect {

  /** der Name*/
  public final String name;
  /** die Beschreibung des Dialektes für Benutzer */
  public final String description;

  public Dialect( String name, String description)
  {
    this.name= name;
    this.description = description;
  }
}