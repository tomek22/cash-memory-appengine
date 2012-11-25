package hr.punintended.cashmemory.dao;

import hr.punintended.cashmemory.domain.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.api.server.spi.ServiceException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public abstract class AbstractDAO<T extends AbstractEntity> {

  protected Class<T> klass;
  private String entityName;

  private EntityManager em;
  private int dummyTransaction = 0;

  protected AbstractDAO(Class<T> klass) {
    this.klass = klass;
    this.entityName = klass.getSimpleName();
  }

  @SuppressWarnings({ "cast", "unchecked" })
  public List<T> list() throws ServiceException {
    startTransaction();
    EntityManager mgr = getEntityManager();
    List<T> result = new ArrayList<T>();
    try {
      Query query = mgr.createQuery("select from " + entityName + " as e");
      for (Object obj : (List<Object>) query.getResultList()) {
        result.add(((T) obj));
      }
      commitTransaction();
    } finally {
      cleanTransaction();
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  protected List<T> findBy(String key, Object value) throws ServiceException {
    startTransaction();
    EntityManager mgr = getEntityManager();
    List<T> result = null;
    try {
      Query query = mgr.createQuery(
          "select from " + klass.getSimpleName() + " as e where e." + key
              + " = :value").setParameter("value", value);
      result = (List<T>) query.getResultList();
      commitTransaction();
    } finally {
      cleanTransaction();
    }
    return result;
  }

  public T get(Key key) throws ServiceException {
    validateGet(key);
    startTransaction();
    EntityManager mgr = getEntityManager();
    T entity = null;
    try {
      entity = mgr.find(klass, key);
      validateGet(entity);
      commitTransaction();
    } finally {
      cleanTransaction();
    }
    return entity;
  }

  public T insert(T entity) throws ServiceException {
    startTransaction();
    EntityManager mgr = getEntityManager();
    try {
      validateInsert(entity);
      mgr.persist(entity);
      commitTransaction();
    } finally {
      cleanTransaction();
    }
    return entity;
  }

  public T update(T newEntity) throws ServiceException {
    T managedEntity = null;
    startTransaction();
    try {
      Key key = newEntity.getKey();
      managedEntity = get(KeyFactory.createKey(key.getKind(), key.getId()));
      update(managedEntity, newEntity);
      commitTransaction();
    } finally {
      cleanTransaction();
    }
    return managedEntity;
  }

  public void delete(Key key) throws ServiceException {
    T entity = null;
    validateDelete(key);
    startTransaction();
    try {
      entity = get(key);
      validateDelete(entity);
      getEntityManager().remove(entity);
      commitTransaction();
    } finally {
      cleanTransaction();
    }
  }

  protected void validateGet(Key key) throws ServiceException {}

  protected void validateGet(T entity) throws ServiceException {
    if (entity == null) {
      throw new ServiceException(404, "Entity not found");
    }
  }

  protected void validateInsert(T entity) throws ServiceException {
    if (entity == null) {
      throw new ServiceException(400, "Bad Request");
    }
  }

  // TODO: make this more generic
  protected void update(T managedEntity, T newEntity) throws ServiceException {
    throw new ServiceException(501, "Update not implemented");
  }

  protected void validateDelete(Key key) throws ServiceException {}

  protected void validateDelete(T entity) throws ServiceException {
    if (entity == null) {
      throw new ServiceException(404, "Entity not found");
    }
  }

  public void startTransaction() {
    if (em == null) {
      em = getEntityManager();
    }
    if (!em.getTransaction().isActive()) {
      em.getTransaction().begin();
    } else {
      dummyTransaction++;
    }
  }

  public void commitTransaction() {
    if (dummyTransaction == 0 && em != null) {
      em.getTransaction().commit();
    }
  }

  public void cleanTransaction() {
    if (dummyTransaction == 0 && em != null && em.getTransaction().isActive()) {
      em.getTransaction().rollback();
    }
    if (dummyTransaction > 0) {
      dummyTransaction--;
    }
  }

  protected final EntityManager getEntityManager() {
    if (em != null)
      return em;
    return EMF.get().createEntityManager();
  }

  private static final class EMF {
    private static final EntityManagerFactory emfInstance = Persistence
        .createEntityManagerFactory("transactions-optional");

    private EMF() {}

    public static EntityManagerFactory get() {
      return emfInstance;
    }
  }
}
