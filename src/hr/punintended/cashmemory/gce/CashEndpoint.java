package hr.punintended.cashmemory.gce;

import static com.googlecode.objectify.ObjectifyService.ofy;
import hr.punintended.cashmemory.domain.AppUser;
import hr.punintended.cashmemory.domain.ExpenseGroup;
import hr.punintended.cashmemory.domain.GroupMembership;
import hr.punintended.cashmemory.service.AuthorizationService;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.googlecode.objectify.Key;

@Api(name = "cash"
// , clientIds = { ClientIds.ANDROID, ClientIds.IOS, ClientIds.WEB }, audiences
// = { Audiences.APPSPOT }, scopes = { Scopes.GOOGLE_MAIL }
)
public class CashEndpoint {

  private AuthorizationService authorizationService = new AuthorizationService();

  // TODO: @Named("email") String -> User
  @ApiMethod(name = "group.insert", path = "group", httpMethod = "POST")
  public ExpenseGroup insertGroup(ExpenseGroup group,
      @Named("email") String user) throws OAuthRequestException {
    Key<AppUser> appUserKey = authorizationService.getAppUserKey(user);

    Key<ExpenseGroup> expenseGroupKey = ofy().save().entity(group).now();
    ofy().save()
        .entity(new GroupMembership(expenseGroupKey, appUserKey.getName()))
        .now();

    return ofy().load().key(expenseGroupKey).safeGet();
  }

  // TODO: implement invite only groups
  // TODO: @Named("email") String -> User
  @ApiMethod(name = "group.join", path = "group/{groupId}/membership", httpMethod = "POST")
  public GroupMembership joinGroup(@Named("groupId") Long groupId,
      @Named("email") String user) throws OAuthRequestException {
    Key<AppUser> appUserKey = authorizationService.getAppUserKey(user);
    Key<ExpenseGroup> groupKey = ofy().load().type(ExpenseGroup.class)
        .id(groupId).safeKey();

    Key<GroupMembership> membershipKey = ofy().save()
        .entity(new GroupMembership(groupKey, appUserKey.getName())).now();

    return ofy().load().key(membershipKey).safeGet();
  }

  // TODO: @Named("email") String -> User
  @ApiMethod(name = "user.get", path = "user/me", httpMethod = "GET")
  public AppUser getMe(@Named("email") String user)
      throws OAuthRequestException {
    return authorizationService.getAppUser(user);
  }

  // TODO: @Named("email") String -> User
  @ApiMethod(name = "user.insert", path = "user", httpMethod = "POST")
  public AppUser insertUser(@Named("currency") String currency,
      @Named("email") String user) throws OAuthRequestException {
    return authorizationService.registerAppUser(user);
  }

  // TODO: @Named("email") String -> User
  @ApiMethod(name = "user.groups", path = "user/me/group", httpMethod = "GET")
  public List<GroupMembership> listMembership(@Named("email") String user)
      throws OAuthRequestException {
    Key<AppUser> appUserKey = authorizationService.getAppUserKey(user);

    return ofy().load().type(GroupMembership.class).ancestor(appUserKey).list();
  }

  // TODO: @Named("email") String -> User
  @ApiMethod(name = "gcm.register", path = "user/me/gcm", httpMethod = "PUT")
  public void registerGCM(@Named("devReg") String deviceRegistrationId,
      @Named("email") String user) throws OAuthRequestException {
    AppUser appUser = authorizationService.getAppUser(user);
    appUser.setDeviceRegistrationId(deviceRegistrationId);
    ofy().save().entity(appUser).now();
  }

  // TODO: @Named("email") String -> User
  @ApiMethod(name = "gcm.unregister", path = "user/me/gcm", httpMethod = "DELETE")
  public void unregisterGCM(@Named("email") String user)
      throws OAuthRequestException {
    registerGCM(null, user);
  }

}
