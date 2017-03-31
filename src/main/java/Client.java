import org.sql2o.*;
import java.util.List;

public class Client {
  private String name;
  private String details;
  private int stylist;
  private int id;

  public Client(String name, String details) {
    this.name = name;
    this.details = details;
  }

  @Override
  public boolean equals(Object otherClient) {
    if(!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getName().equals(newClient.getName()) &&
             this.getId() == newClient.getId();
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
      String sql = "INSERT INTO clients (name, details) VALUES (:name, :details)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("details", this.details)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Client> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients";
      return con.createQuery(sql)
      .executeAndFetch(Client.class);
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where id=:id";
      Client stylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
      return stylist;
    }
  }

  public void updateDetails(String details) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET details = :details WHERE id = :id";
      con.createQuery(sql)
        .addParameter("details", details)
        .addParameter("id", id)
        .executeUpdate();

    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }



}
