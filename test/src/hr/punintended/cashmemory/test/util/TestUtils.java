package hr.punintended.cashmemory.test.util;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;

public class TestUtils {

  public static final void assertCount(Class<?> entityClass, int size) {
    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    assertEquals(size, ds.prepare(new Query(entityClass.getSimpleName()))
        .countEntities(withLimit(size + 1)));
  }

  public static final void assertEmpty(Class<?> entityClass) {
    assertCount(entityClass, 0);
  }
}
