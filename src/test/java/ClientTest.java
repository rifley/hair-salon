import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void getName_returnsNameOfStylist_string() {
    Client newClient = new Client("Frannie", "Always with the mints");
    assertEquals(newClient.getName(), "Frannie");
  }


  @Test
  public void equals_returnsTrueIfStringContentsAreTheSame() {
    Client firstClient = new Client("Mel", "Smells like mothballs");
    Client secondClient = new Client("Mel", "Smells like mothballs");
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Client myClient = new Client("Mel", "Smells like mothballs");
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }

  @Test
  public void find_findsClientWithMatchingId_secondClient() {
    Client firstClient = new Client("Frannie", "Always with the mints");
    Client secondClient = new Client("Mel", "Smells like mothballs");
    firstClient.save();
    secondClient.save();
    assertEquals(Client.find(secondClient.getId()),secondClient);
  }

  @Test
  public void updateDetails_updatesClientDetails_string() {
    Client newClient = new Client("June", "Loves the Summer");
    newClient.save();
    newClient.updateDetails("Summer smells, Spring sings");
    assertEquals("Summer smells, Spring sings", Client.find(newClient.getId()).getDetails());
  }

  @Test
  public void delete_deletesClient_true() {
    Client newClient = new Client("James", "Owns a rather large peach");
    newClient.save();
    int oldClientId = newClient.getId();
    newClient.delete();
    assertEquals(null, Client.find(oldClientId));
  }

}
