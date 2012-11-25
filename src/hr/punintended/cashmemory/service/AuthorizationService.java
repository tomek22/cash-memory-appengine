package hr.punintended.cashmemory.service;

import hr.punintended.cashmemory.conf.Errors;
import hr.punintended.cashmemory.dao.AppUserDAO;
import hr.punintended.cashmemory.domain.AppUser;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

public class AuthorizationService {

  private AppUserDAO appUserDAO = new AppUserDAO();

  public void checkUser(User user) throws OAuthRequestException {
    if (user == null)
      throw new OAuthRequestException(Errors.OAUTH_ERROR);
  }

  public void checkAppUser(User user, AppUser appUser)
      throws OAuthRequestException, ServiceException {
    checkUser(user);
    AppUser other = appUserDAO.find(user);
    if (other == null || other.getKey().getId() != appUser.getKey().getId()) {
      throw new UnauthorizedException(Errors.UNAUTH_ERROR);
    }
  }
}
