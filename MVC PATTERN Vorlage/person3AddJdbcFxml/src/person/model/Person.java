package person.model;


import java.sql.*;
import java.time.LocalDate;
import javafx.beans.property.*;


public final class Person {
  private final StringProperty svnr = new SimpleStringProperty();
  private final StringProperty nname = new SimpleStringProperty();
  private final StringProperty vname = new SimpleStringProperty();
  private final ObjectProperty<LocalDate> gebDat = new SimpleObjectProperty<>();
  private final ObjectProperty<Number> groesse = new SimpleObjectProperty<>();
  //private final StringProperty geschlecht = new SimpleStringProperty();
  private final ObjectProperty<Geschlecht> geschlecht = new SimpleObjectProperty<>();


  public Person() {
    clear();
  }


  public Person(Person p) {
    this.setSvnr(p.getSvnr());
    this.setNname(p.getNname());
    this.setVname(p.getVname());
    this.setGebDat(p.getGebDat());
    this.setGroesse(p.getGroesse());
    this.setGeschlecht(p.getGeschlecht());
  }


  public final void clear() {
    this.setSvnr(null);
    this.setNname(null);
    this.setVname(null);
    this.setGebDat(null);
    this.setGroesse(null);
    this.setGeschlecht(null);
  }


  private void killAndFill() {
    // Svnr
    if (svnr.get() == null) {
      throw new IllegalArgumentException("Sozialversicherungsnummer muss angegeben werden!");
    }

    if (!svnr.get().matches("[0-9]{4}[0-3][0-9][0-1][0-9][0-9]{2}")) {
      throw new IllegalArgumentException("Sozialversicherungsnummer muss im Format nnnnttmmjj sein!");
    }

    // NName
    if (nname.get() == null) {
      throw new IllegalArgumentException("Nachname muss angegeben werden!");
    }

    if (nname.get().length() < 2) {
      throw new IllegalArgumentException("Nachname muss länger als 2 Zeichen sein!");
    }

    // VName
    if (vname.get() != null
            && vname.get().length() < 2) {
      throw new IllegalArgumentException("Wenn der Vorname angegeben wird, dann muss er länger als 2 Zeichen sein!");
    }

    // Geburtsdatum
    if (gebDat.get() == null) {
      throw new IllegalArgumentException("Geburtsdatum muss angegeben werden!");
    }

    if (gebDat.get().isBefore(LocalDate.now().minusYears(120)) || gebDat.get().isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Das Geburtsdatum darf nicht mehr als 120 Jahre in der Vergangenheit oder in der Zukunft liegen!");
    }

    // Größe
    if (groesse.get() != null
            && (groesse.get().doubleValue() < 0.5 || groesse.get().doubleValue() > 2.5)) {
      throw new IllegalArgumentException("Wenn die Größe angegeben wird, muss sie zwischen 0,5 m und 2,5 m sein ("
              + groesse.get().doubleValue() + ")!");
    }

    // Geschlecht
    if (geschlecht.get() == null) {
      throw new IllegalArgumentException("Geschlecht muss angegeben werden!");
    }
    if (geschlecht.get() != Geschlecht.W && geschlecht.get() != Geschlecht.M) {
      throw new IllegalArgumentException("Geschlecht muss W oder M sein!");
    }
  }


  public void save(Statement statement) throws SQLException {
    killAndFill();

    String sql
           = "  insert "
            + " into Person (svnr "
            + "             ,nname "
            + "             ,vname "
            + "             ,gebdat "
            + "             ,groesse "
            + "             ,geschlecht "
            + "             ) "
            + " values ( '" + svnr.get() + "' "
            + "        , '" + nname.get() + "' "
            + (vname.get() == null 
            ? "        , null "
            : "        , '" + vname.get() + "' ")
            + "        , '" + gebDat.get() + "' "
            + "        ,  " + groesse.get() + "  "
            + "        , '" + geschlecht.get() + "' "
            + "        )";

    statement.executeUpdate(sql);
  }


  @Override
  public String toString() {
    return getSvnr() + "/"
            + getNname() + "/"
            + getVname() + "/"
            + getGebDat() + "/"
            + getGroesse() + "/"
            + getGeschlecht();
  }


  public String getSvnr() {
    return svnr.get();
  }


  public final void setSvnr(String value) {
    svnr.set(value);
  }


  public StringProperty svnrProperty() {
    return svnr;
  }


  public String getNname() {
    return nname.get();
  }


  public final void setNname(String value) {
    nname.set(value);
  }


  public StringProperty nnameProperty() {
    return nname;
  }


  public String getVname() {
    return vname.get();
  }


  public final void setVname(String value) {
    vname.set(value);
  }


  public StringProperty vnameProperty() {
    return vname;
  }


  public LocalDate getGebDat() {
    return gebDat.get();
  }


  public void setGebDat(LocalDate value) {
    gebDat.set(value);
  }


  public ObjectProperty gebDatProperty() {
    return gebDat;
  }


  public Number getGroesse() {
    return groesse.get();
  }


  public final void setGroesse(Number value) {
    groesse.set(value);
  }


  public ObjectProperty<Number> groesseProperty() {
    return groesse;
  }


  public Geschlecht getGeschlecht() {
    return geschlecht.get();
  }


  public final void setGeschlecht(Geschlecht value) {
    geschlecht.set(value);
  }


  public ObjectProperty<Geschlecht> geschlechtProperty() {
    return geschlecht;
  }

}
