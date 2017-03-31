import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM Stylists *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Stylist firstStylist = new Stylist("Todd", "Balding");
    Stylist secondStylist = new Stylist("Todd", "Balding");
    assertTrue(firstStylist.equals(secondStylist));
  }

}
