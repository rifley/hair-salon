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

  public String getDetails() {
    return details;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (name, details) VALUES (:name, :details)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("details", this.details)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Stylist> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists";
      return con.createQuery(sql)
      .executeAndFetch(Stylist.class);
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists where id=:id";
      Stylist stylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }

  public void updateDetails(String details) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET details = :details WHERE id = :id";
      con.createQuery(sql)
        .addParameter("details", details)
        .addParameter("id", id)
        .executeUpdate();

    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }


}
