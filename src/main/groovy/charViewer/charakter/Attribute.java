package charViewer.charakter;

public enum Attribute {

  STRENGTH("STR"),

  DEXTERITY("DEX"),

  CONSTITUTION("CON"),

  INTELLIGENCE("INT"),

  WISDOM("WIS"),

  CHARISMA("CHA");

  private final String abbreviation;

  Attribute(String abbr) {
    this.abbreviation = abbr;
  }

  public String getAbbreviation() {
    return abbreviation;
  }
}
