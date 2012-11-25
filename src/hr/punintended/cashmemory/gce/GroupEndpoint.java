package hr.punintended.cashmemory.gce;

import hr.punintended.cashmemory.dao.AppUserDAO;
import hr.punintended.cashmemory.dao.GroupDAO;
import hr.punintended.cashmemory.domain.AppUser;
import hr.punintended.cashmemory.domain.Group;
import hr.punintended.cashmemory.domain.GroupMembership;
import hr.punintended.cashmemory.service.AuthorizationService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

@Api(name = "groupendpoint"
// , clientIds = { ClientIds.ANDROID, ClientIds.IOS, ClientIds.WEB }, audiences
// = { Audiences.APPSPOT }, scopes = { Scopes.GOOGLE_MAIL }
)
public class GroupEndpoint {

  private AuthorizationService authorizationService = new AuthorizationService();

  private AppUserDAO appUserDAO = new AppUserDAO();
  private GroupDAO groupDAO = new GroupDAO();

  @ApiMethod(name = "group.insert", path = "group", httpMethod = "POST")
  public Group insertGroup(Group group, @Named("email") String email, User user)
      throws ServiceException, OAuthRequestException {
    // authorizationService.checkUser(user);
    // AppUser appUser = appUserDAO.find(user);
    AppUser appUser = appUserDAO.findByEmail(email);
    if (appUser == null)
      throw new ServiceException(404, "User not found");
    List<GroupMembership> memberships = new ArrayList<GroupMembership>();
    memberships.add(new GroupMembership(appUser.getKey()));
    group.setMemberships(memberships);
    group = groupDAO.insert(group);
    return group;
  }

  @ApiMethod(name = "group.join", path = "group/{groupId}/membership", httpMethod = "POST")
  public GroupMembership joinGroup(@Named("groupId") Long groupId,
      @Named("email") String email, User user) throws ServiceException {
    // authorizationService.checkUser(user);
    // AppUser appUser = appUserDAO.find(user);
    AppUser appUser = appUserDAO.findByEmail(email);
    if (appUser == null)
      throw new ServiceException(404, "User not found");
    GroupMembership groupMembership = new GroupMembership(appUser.getKey());
    groupDAO.startTransaction();
    try {
      Group group = groupDAO.get(KeyFactory.createKey(
          Group.class.getSimpleName(), groupId));
      // TODO: Optimize this with a single query in objectify
      for (GroupMembership membership : group.getMemberships())
        if (membership.getAppUser().equals(appUser.getKey()))
          throw new ServiceException(409, "User already member of this group");
      group.getMemberships().add(groupMembership);
      groupDAO.commitTransaction();
    } finally {
      groupDAO.cleanTransaction();
    }
    return groupMembership;
  }

}
