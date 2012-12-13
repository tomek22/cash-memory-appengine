package hr.punintended.cashmemory.service;

import static com.googlecode.objectify.ObjectifyService.ofy;
import hr.punintended.cashmemory.conf.Errors;
import hr.punintended.cashmemory.domain.AppUser;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

public class AuthorizationService {

  // TODO: String -> User
  public void checkUser(String user) throws OAuthRequestException {
    if (user == null)
      throw new OAuthRequestException(Errors.OAUTH_ERROR);
  }

  // TODO: String -> User
  private Ref<AppUser> safeGetAppUserRef(String user)
      throws OAuthRequestException {
    checkUser(user);
    Ref<AppUser> appUserRef = ofy().load().type(AppUser.class).id(user);
    if (appUserRef.get() == null) {
      appUserRef = Ref.create(ofy().save().entity(new AppUser(user)).now());
    }
    return appUserRef;
  }

  // TODO: String -> User
  public Key<AppUser> getAppUserKey(String user) throws OAuthRequestException {
    return safeGetAppUserRef(user).key();
  }

  // TODO: String -> User
  public AppUser getAppUser(String user) throws OAuthRequestException {
    return safeGetAppUserRef(user).get();
  }
}
