package org.openstack.client.keystone;

import org.junit.Before;
import org.junit.Test;
import org.openstack.client.keystone.auth.AuthData;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.net.URISyntaxException;

public class KeyStoneClientTest {
    private String username;
    private String auth_url;
    private String auth_stag_url;
    private String key;
    private AuthData authData;
    private final String type = "mosso";
    private KeyStoneClient client;


    @Before
    public void standUp() throws URISyntaxException {
        auth_url = KeyStoneUtil.getProperty("auth_management_uri");
        username = "bobTester";
        key = "1234567891011121313";
    }

    @Test
    public void validateCloudUser() throws Exception {
        client = new KeyStoneClient(auth_url);
        try {
            authData = client.authenticateUser(username, key);
            assertNotNull(authData);
        } catch (KeyStoneException ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex);
        }
    }
}

