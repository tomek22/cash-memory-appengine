package hr.punintended.cashmemory.test.util;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

public class TestUtils {

  public static final void assertCount(Class<?> entityClass, Key parent,
      int size) {
    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    Query query = parent == null ? new Query(entityClass.getSimpleName())
        : new Query(entityClass.getSimpleName(), parent);
    assertEquals(size, ds.prepare(query).countEntities(withLimit(size + 1)));
  }

  public static final void assertCount(Class<?> entityClass,
      Object objectifyEntity, int size) {
    assertCount(entityClass, getRawKey(objectifyEntity), size);
  }

  public static final void assertCount(Class<?> entityClass, int size) {
    assertCount(entityClass, null, size);
  }

  public static final void assertEmpty(Class<?> entityClass) {
    assertCount(entityClass, 0);
  }

  public static final List<Entity> getEntities(Class<?> entityClass,
      Key parent, int size) {
    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    Query query = parent == null ? new Query(entityClass.getSimpleName())
        : new Query(entityClass.getSimpleName(), parent);
    return (List<Entity>) ds.prepare(query).asList(withLimit(size));
  }

  public static final List<Entity> getEntities(Class<Entity> entityClass,
      int size) {
    return getEntities(entityClass, null, size);
  }

  public static final Entity getFirstEntity(Class<?> entityClass, Key parent) {
    return getEntities(entityClass, parent, 1).get(0);
  }

  public static final Entity getFirstEntity(Class<?> entityClass,
      Object objectifyEntity) {
    return getFirstEntity(entityClass, getRawKey(objectifyEntity));
  }

  public static final Entity getFirstEntity(Class<?> entityClass) {
    return getFirstEntity(entityClass, null);
  }

  private static Key getRawKey(Object objectifyEntity) {
    return com.googlecode.objectify.Key.create(objectifyEntity).getRaw();
  }
}
