package hr.punintended.cashmemory.dao;

import hr.punintended.cashmemory.domain.Group;
import hr.punintended.cashmemory.domain.GroupMembership;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;

public class GroupDAO extends AbstractDAO<Group> {

  public GroupDAO() {
    super(Group.class);
  }

  @SuppressWarnings("unchecked")
  public List<GroupMembership> findUserGroupMemberships(Key appUser) {
    startTransaction();
    EntityManager mgr = getEntityManager();
    List<GroupMembership> result = new ArrayList<GroupMembership>();
    try {
      Query query = mgr.createQuery(
          "select from " + GroupMembership.class.getSimpleName()
              + " as e where e.appUser = :appUser").setParameter("appUser",
          appUser);
      for (Object obj : (List<Object>) query.getResultList()) {
        result.add(((GroupMembership) obj));
      }
      commitTransaction();
    } finally {
      cleanTransaction();
    }
    return result;
  }
}
