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
  public void getName_returnsNameOfStylist_string() {
    Stylist newStylist = new Stylist("Janet", "Shes so hot right now");
    assertEquals(newStylist.getName(), "Janet");
  }


  @Test
  public void equals_returnsTrueIfStringContentsAreTheSame() {
    Stylist firstStylist = new Stylist("Todd", "Balding");
    Stylist secondStylist = new Stylist("Todd", "Balding");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Stylist myStylist = new Stylist("Todd", "Balding");
    myStylist.save();
    assertTrue(Stylist.all().get(0).equals(myStylist));
  }

  @Test
  public void find_findsStylistWithMatchingId_secondStylist() {
    Stylist firstStylist = new Stylist("Janet", "Shes so hot right now");
    Stylist secondStylist = new Stylist("Todd", "Classic todd");
    firstStylist.save();
    secondStylist.save();
    assertEquals(Stylist.find(secondStylist.getId()),secondStylist);
  }

  @Test
  public void update_updatesDetailsForStylists_true() {
    Stylist newStylist = new Stylist("Lola", "la la la");
    Stylist newerStylist = new Stylist("Lela", "lololol");
    newStylist.save();
    newerStylist.save();
    newerStylist.updateDetails("fa fa fa");
    assertEquals("fa fa fa", Stylist.find(newerStylist.getId()).getDetails());
  }

  @Test
  public void delete_deletesStylist_true() {
    Stylist newStylist = new Stylist("Ryan", "Works for free most days");
    newStylist.save();
    int oldStylistId = newStylist.getId();
    newStylist.delete();
    assertEquals(null, Stylist.find(oldStylistId));
  }



}
