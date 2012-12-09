package hr.punintended.cashmemory.service;

import static com.googlecode.objectify.ObjectifyService.ofy;
import hr.punintended.cashmemory.conf.Errors;
import hr.punintended.cashmemory.domain.AppUser;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.googlecode.objectify.Key;

public class AuthorizationService {

  // TODO: String -> User
  public void checkUser(String user) throws OAuthRequestException {
    if (user == null)
      throw new OAuthRequestException(Errors.OAUTH_ERROR);
  }

  // TODO: String -> User
  public Key<AppUser> getAppUserKey(String user) throws OAuthRequestException {
    checkUser(user);
    return ofy().load().type(AppUser.class).id(user).safeKey();
  }

  // TODO: String -> User
  public AppUser getAppUser(String user) throws OAuthRequestException {
    checkUser(user);
    return ofy().load().type(AppUser.class).id(user).safeGet();
  }

  // TODO: String -> User
  public AppUser registerAppUser(String user) throws OAuthRequestException {
    checkUser(user);
    Key<AppUser> appUserKey = ofy().save().entity(new AppUser(user)).now();
    return ofy().load().key(appUserKey).safeGet();
  }
}
