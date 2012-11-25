package hr.punintended.cashmemory.dao;

import hr.punintended.cashmemory.conf.Errors;
import hr.punintended.cashmemory.domain.AppUser;

import java.util.List;

import com.google.api.server.spi.ServiceException;
import com.google.appengine.api.users.User;

public class AppUserDAO extends AbstractDAO<AppUser> {

  public AppUserDAO() {
    super(AppUser.class);
  }

  @Override
  protected void validateInsert(AppUser appUser) throws ServiceException {
    super.validateInsert(appUser);
    if (findByEmail(appUser.getEmail()) != null) {
      throw new ServiceException(409, Errors.USER_EXISTS);
    }
  }

  public AppUser findByEmail(String email) throws ServiceException {
    List<AppUser> users = findBy("email", email);
    return (users != null && !users.isEmpty()) ? users.get(0) : null;
  }

  public AppUser find(User user) throws ServiceException {
    return findByEmail(user.getEmail());
  }

}
