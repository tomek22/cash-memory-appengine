package hr.punintended.cashmemory.gce.test;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;
import hr.punintended.cashmemory.domain.ExpenseGroup;
import hr.punintended.cashmemory.gce.CashEndpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;

public class CashEndpointTest {

  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
      new LocalUserServiceTestConfig());
  private DatastoreService ds;
  private String user;
  // private User user;

  private CashEndpoint cashEndpoint;

  @Before
  public void setUp() {
    helper.setUp();
    ds = DatastoreServiceFactory.getDatastoreService();
    user = "user@cashmemory.hr";
    // user = new User("user@cashmemory.hr", "cashmemory.hr");
    cashEndpoint = new CashEndpoint();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void insertGroupTest() throws OAuthRequestException {
    assertEquals(0,
        ds.prepare(new Query("ExpenseGroup")).countEntities(withLimit(1)));
    cashEndpoint.insertGroup(new ExpenseGroup("Grupa", "KN"), user);
    assertEquals(1,
        ds.prepare(new Query("AppUser")).countEntities(withLimit(1)));
    assertEquals(1,
        ds.prepare(new Query("ExpenseGroup")).countEntities(withLimit(1)));

  }
}
