package org.springframework.social.dropbox.connect;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.AccountType;
import com.dropbox.core.v2.users.DbxUserUsersRequests;
import com.dropbox.core.v2.users.FullAccount;
import com.dropbox.core.v2.users.Name;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.social.connect.UserProfile;

import static junit.framework.Assert.assertEquals;

/**
 * @author Bryce Fischer
 * @author Robert Drysdale
 */
public class DropboxAdapterTest {
    private DropboxAdapter adapter = new DropboxAdapter();
    private DbxClientV2 dropboxApi = Mockito.mock(DbxClientV2.class);
	private DbxUserUsersRequests dpxUserUsersRequests = Mockito.mock(DbxUserUsersRequests.class);

    @Test
    public void fetchProfile() throws Exception {
		final String displayName = "DisplayName";
		
		Name name = new Name("giveName", 
				"sureName", 
				"familiarName", 
				displayName, 
				"abbreviatedName");
		
		FullAccount user = new FullAccount("thisIsAnAccountIdAndHasToBe40CharectersL", 
				name, 
				"emailaddress", 
				true, 
				false, 
				"en", 
				"referralLink", 
				true, 
				AccountType.BASIC);

		Mockito.when(dropboxApi.users())
				.thenReturn(dpxUserUsersRequests);
        Mockito.when(dpxUserUsersRequests.getCurrentAccount())
                .thenReturn(user);

        UserProfile profile = adapter.fetchUserProfile(dropboxApi);

        assertEquals(displayName, profile.getName());
    }
}
