package util;


import java.text.NumberFormat;
import javafx.util.StringConverter;


public class NullableNumberStringConverter extends StringConverter<Number> {
  private NumberFormat df;


  public NullableNumberStringConverter(NumberFormat df) {
    this.df = df;
  }


  @Override
  public String toString(Number object) {
    if (object == null) {
      return "";
    }
    else {
      return df.format(object.doubleValue());
    }
  }


  @Override
  public Number fromString(String string) {
    Number number;
    try {
      if (string != null && string.length() == 0) {
        number = null;
      }
      else {
        number = df.parse(string);
      }
    }
    catch (Exception ex) {
      number = null;
    }
    return number;
  }
}
