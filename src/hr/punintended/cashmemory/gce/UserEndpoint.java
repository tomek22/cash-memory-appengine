package hr.punintended.cashmemory.gce;

import hr.punintended.cashmemory.dao.AppUserDAO;
import hr.punintended.cashmemory.dao.GroupDAO;
import hr.punintended.cashmemory.domain.AppUser;
import hr.punintended.cashmemory.domain.GroupMembership;
import hr.punintended.cashmemory.service.AuthorizationService;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

@Api(name = "userendpoint"
// , clientIds = { ClientIds.ANDROID, ClientIds.IOS, ClientIds.WEB }, audiences
// = { Audiences.APPSPOT }, scopes = { Scopes.GOOGLE_MAIL }
)
public class UserEndpoint {

  private AuthorizationService authorizationService = new AuthorizationService();

  private AppUserDAO appUserDAO = new AppUserDAO();
  private GroupDAO groupDAO = new GroupDAO();

  @ApiMethod(name = "user.get", path = "user/me", httpMethod = "GET")
  public AppUser getMe(@Named("email") String email, User user)
      throws ServiceException, OAuthRequestException {
    // authorizationService.checkUser(user);
    // AppUser appUser = appUserDAO.find(user);
    AppUser appUser = appUserDAO.findByEmail(email);
    if (appUser == null)
      throw new ServiceException(404, "User not found");
    return appUser;
  }

  @ApiMethod(name = "user.insert", path = "user", httpMethod = "POST")
  public AppUser insertUser(@Named("email") String email,
      @Named("currency") String currency, User user) throws ServiceException,
      OAuthRequestException {
    // authorizationService.checkUser(user);
    // return appUserDAO.insert(new AppUser(user.getEmail()));
    return appUserDAO.insert(new AppUser(email, currency));
  }

  @ApiMethod(name = "user.memberships", path = "user/me/group", httpMethod = "GET")
  public List<GroupMembership> listMemberships(@Named("email") String email,
      User user) throws ServiceException, OAuthRequestException {
    // authorizationService.checkUser(user);
    // AppUser appUser = appUserDAO.find(user);
    AppUser appUser = appUserDAO.findByEmail(email);
    return groupDAO.findUserGroupMemberships(appUser.getKey());
  }

  @ApiMethod(name = "gcm.register", path = "user/me/gcm", httpMethod = "PUT")
  public void registerGCM(@Named("devReg") String deviceRegistrationId,
      @Named("email") String email, User user) throws OAuthRequestException,
      ServiceException {
    // authorizationService.checkUser(user);
    appUserDAO.startTransaction();
    try {
      // AppUser appUser = appUserDAO.find(user);
      AppUser appUser = appUserDAO.findByEmail(email);
      if (appUser == null)
        throw new ServiceException(404, "User not found");
      appUser.setDeviceRegistrationID(deviceRegistrationId);
      appUserDAO.update(appUser);
      appUserDAO.commitTransaction();
    } finally {
      appUserDAO.cleanTransaction();
    }
  }

  @ApiMethod(name = "gcm.unregister", path = "user/me/gcm", httpMethod = "DELETE")
  public void unregisterGCM(@Named("email") String email, User user)
      throws OAuthRequestException, ServiceException {
    registerGCM(null, email, user);
  }

}
