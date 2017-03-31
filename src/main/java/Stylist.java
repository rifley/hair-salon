import org.sql2o.*;
import java.util.List;

public class Stylist {
  private String name;
  private String details;
  private int id;

  public Stylist(String name, String details) {
    this.name = name;
    this.details = details;
  }

  @Override
  public boolean equals(Object otherStylist) {
    if(!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getName().equals(newStylist.getName()) &&
             this.getId() == newStylist.getId();
    }
  }

  public String getName() {
    return name;
  }
  public int getId() {
    return id;
  }

}
