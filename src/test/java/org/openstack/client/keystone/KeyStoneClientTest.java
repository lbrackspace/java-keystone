package org.openstack.client.keystone;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openstack.client.keystone.auth.AuthData;
import org.openstack.client.keystone.user.User;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.Assert.assertNotNull;

public class KeyStoneClientTest {
    private String auth_url;
    private static String auth_stag_url = KeyStoneUtil.getProperty("auth_stag_url");
    private static String bUser = KeyStoneUtil.getProperty("basic_auth_user");
    private static String bPass = KeyStoneUtil.getProperty("basic_auth_key");
    private AuthData authData;
    private static Integer accountId = 654321;
    private static String nastId;

    private final String type = "mosso";
    private KeyStoneClient client;
    private static final String username = "bobTester";
    private static final String key = "1234567891011121313";
    private static KeyStoneAdminClient keyStoneAdminClient;


    @BeforeClass
    public static void SetupTestUser() throws IOException, URISyntaxException, KeyStoneException {
        try {
            keyStoneAdminClient = new KeyStoneAdminClient(auth_stag_url, bPass, bUser);
            User user = keyStoneAdminClient.createUser(username, key, accountId, "14bb72c1-237c-42aa-9307-893045b596e0", true);
            nastId = user.getNastId();

        } catch (Exception e) {
            if (e instanceof KeyStoneException) {
                keyStoneAdminClient.deleteUser(username);
            }
            e.printStackTrace();
        }
    }

    @Test
    public void validateCloudUser() throws Exception {
        client = new KeyStoneClient(auth_stag_url);
        try {
            authData = client.authenticateUser(username, key);
            assertNotNull(authData);
        } catch (KeyStoneException ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex);
        }
    }

    @After
    public void tearDown() {
        try {
            keyStoneAdminClient.deleteUser(username);
        } catch (KeyStoneException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

