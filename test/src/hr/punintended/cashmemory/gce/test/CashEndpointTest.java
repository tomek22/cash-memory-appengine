package hr.punintended.cashmemory.gce.test;

import static hr.punintended.cashmemory.test.util.TestUtils.assertCount;
import static hr.punintended.cashmemory.test.util.TestUtils.assertEmpty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import hr.punintended.cashmemory.domain.AppUser;
import hr.punintended.cashmemory.domain.ExpenseGroup;
import hr.punintended.cashmemory.gce.CashEndpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class CashEndpointTest {

  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
      new LocalDatastoreServiceTestConfig());

//  private final LocalServiceTestHelper helper =
//      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
//          .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

  private String user;
  // private User user;

  private CashEndpoint cashEndpoint;

  @Before
  public void setUp() {
    helper.setUp();
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
    assertEmpty(AppUser.class);
    assertEmpty(ExpenseGroup.class);
    ExpenseGroup expenseGroup = cashEndpoint.insertGroup(new ExpenseGroup(
        "Grupa"), user);
    assertNotNull(expenseGroup.getId());
    assertNotNull(expenseGroup.getDateCreated());
    assertEquals(expenseGroup.getLastUpdated(), expenseGroup.getDateCreated());
    assertEquals("Grupa", expenseGroup.getName());
    assertEquals(user, expenseGroup.getCreator().getName());
    assertFalse(expenseGroup.isAdHoc());
    assertCount(AppUser.class, 1);
    assertCount(AppUser.class, 1);
  }
}
