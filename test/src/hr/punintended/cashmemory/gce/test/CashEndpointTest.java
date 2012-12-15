package hr.punintended.cashmemory.gce.test;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static hr.punintended.cashmemory.test.util.TestUtils.assertCount;
import static hr.punintended.cashmemory.test.util.TestUtils.assertEmpty;
import static hr.punintended.cashmemory.test.util.TestUtils.getFirstEntity;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import hr.punintended.cashmemory.domain.AppUser;
import hr.punintended.cashmemory.domain.DefaultAbstractEntity;
import hr.punintended.cashmemory.domain.ExpenseGroup;
import hr.punintended.cashmemory.domain.GroupMembership;
import hr.punintended.cashmemory.gce.CashEndpoint;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class CashEndpointTest {

  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
      new LocalDatastoreServiceTestConfig());

  // private final LocalServiceTestHelper helper =
  // new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
  // .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

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
    ofy().clear();
  }

  @Test
  public void insertGroupTest() throws Exception {
    assertEmpty(AppUser.class);
    assertEmpty(ExpenseGroup.class);
    ExpenseGroup expenseGroup = cashEndpoint.insertGroup(new ExpenseGroup(
        "Grupa"), user);
    assertCount(ExpenseGroup.class, 1);
    assertCount(AppUser.class, 1);
    assertCount(GroupMembership.class, expenseGroup, 1);
    assertNotNull(expenseGroup.getId());
    assertNotNull(expenseGroup.getDateCreated());
    assertEquals(expenseGroup.getLastUpdated(), expenseGroup.getDateCreated());
    assertEquals("Grupa", expenseGroup.getName());
    assertEquals(user, expenseGroup.getCreator().getName());
    assertFalse(expenseGroup.isAdHoc());

    Entity expenseGroupLoaded = getFirstEntity(ExpenseGroup.class);
    assertEquals(expenseGroup.getId().longValue(), expenseGroupLoaded.getKey()
        .getId());
    assertEquals(expenseGroup.getCreator().getName(),
        ((Key) expenseGroupLoaded.getProperty("creator")).getName());
    assertEquals(expenseGroup.getDateCreated(),
        expenseGroupLoaded.getProperty("dateCreated"));
    assertEquals(expenseGroup.getLastUpdated(),
        expenseGroupLoaded.getProperty("lastUpdated"));
    assertEquals(expenseGroup.getName(), expenseGroupLoaded.getProperty("name"));

    Entity appUserLoaded = getFirstEntity(AppUser.class);
    assertEquals(expenseGroup.getCreator().getName(), appUserLoaded.getKey()
        .getName());

    Entity groupMembershipLoaded = getFirstEntity(GroupMembership.class,
        expenseGroup);
    assertEquals(appUserLoaded.getKey().getName(), groupMembershipLoaded
        .getKey().getName());
    assertEquals(groupMembershipLoaded.getProperty("balance"), 0l);
  }

  @Test
  public void insertGroupTest_protectId() throws Exception {
    ExpenseGroup expenseGroup = new ExpenseGroup("Grupa");
    Field idField = DefaultAbstractEntity.class.getDeclaredField("id");
    idField.setAccessible(true);
    idField.set(expenseGroup, -321l);
    assertEquals(expenseGroup.getId(), Long.valueOf(-321l));
    expenseGroup = cashEndpoint.insertGroup(expenseGroup, user);
    assertNotNull(expenseGroup.getId());
    assertThat(expenseGroup.getId(), not(-321l));
  }

  @Test
  public void joinGroupTest() throws Exception {
    assertEmpty(AppUser.class);
    assertEmpty(ExpenseGroup.class);
    ExpenseGroup expenseGroup = cashEndpoint.insertGroup(new ExpenseGroup(
        "Grupa"), user);
    assertCount(ExpenseGroup.class, 1);
    assertCount(AppUser.class, 1);
    assertCount(GroupMembership.class, expenseGroup, 1);
    cashEndpoint.joinGroup(expenseGroup.getId(), "otherUser");
    assertCount(ExpenseGroup.class, 1);
    assertCount(AppUser.class, 2);
    assertCount(GroupMembership.class, expenseGroup, 2);
  }
}
