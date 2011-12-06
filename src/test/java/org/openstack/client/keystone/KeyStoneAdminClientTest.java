package org.openstack.client.keystone;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openstack.client.keystone.auth.AuthData;
import org.openstack.client.keystone.token.FullToken;
import org.openstack.client.keystone.user.User;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class KeyStoneAdminClientTest {
    private static String auth_url = KeyStoneUtil.getProperty("auth_management_uri");
    private static String auth_stag_url = KeyStoneUtil.getProperty("auth_stag_url");
    private static String bUser = KeyStoneUtil.getProperty("basic_auth_user");
    private static String bPass = KeyStoneUtil.getProperty("basic_auth_key");
    private AuthData authData;
    private static final String username = "bobTester";
    private static final String key = "1234567891011121313";
    private static String nastId;
    private static Integer accountId = 654321;
    private final String type = "mosso";

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

    @Before
    public void standUp() throws Exception {
        KeyStoneClient cclient = new KeyStoneClient();
        try {
            authData = cclient.authenticateUser(auth_stag_url, username, key);
        } catch (KeyStoneException ex) {
            System.out.println("FAILURE gathering authenticated user info.");
            System.out.print(ex);
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void shouldInstantiateClientWithBasicParams() throws KeyStoneException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient();
        assertNotNull(clientKeyStone);
    }

    @Test
    public void shouldInstantiateClientWithAdvancedParams() throws Exception, KeyStoneException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        assertNotNull(clientKeyStone);
    }

    @Test
    public void validateTokenUsingMossoShouldSucceed() throws Exception, KeyStoneException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.validateToken(String.valueOf(accountId), authData.getToken().getId(), "mosso");
        assertNotNull(returnToken);
        System.out.print("Token: " + returnToken.getId());
    }

    @Test
    public void validateTokenUsingCloudShouldSucceed() throws Exception, KeyStoneException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.validateToken(username, authData.getToken().getId(), "cloud");
        assertNotNull(returnToken);
        System.out.print("Token: " + returnToken.getId());
    }

    @Test
    public void validateTokenUsingNastShouldSucceed() throws URISyntaxException, KeyStoneException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.validateToken(nastId, authData.getToken().getId(), "nast");
        assertNotNull(returnToken);
        System.out.print("Token: " + returnToken.getId());
    }

    @Test
    public void validateTokenShouldSucceedReturnProperTokenForCloud() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.validateToken(username, authData.getToken().getId(), "cloud");
        assertNotNull(returnToken);
        assertEquals(authData.getToken().getId(), returnToken.getId());
        System.out.print("Token: " + returnToken.getId());
    }

    @Test
    public void validateTokenShouldSucceedReturnProperTokenForMosso() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.validateToken(String.valueOf(accountId), authData.getToken().getId(), "mosso");
        assertNotNull(returnToken);
        assertEquals(authData.getToken().getId(), returnToken.getId());
        System.out.print("Token: " + returnToken.getId());
    }

    @Test
    public void validateTokenShouldSucceedReturnProperTokenForNast() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.validateToken(nastId, authData.getToken().getId(), "nast");
        assertNotNull(returnToken);
        assertEquals(authData.getToken().getId(), returnToken.getId());
        System.out.print("Token: " + returnToken.getId());
    }

    @Test
    public void validateTokenShouldSucceedReturnProperTokenExpirationForCloud() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.validateToken(username, authData.getToken().getId(), "cloud");

        assertNotNull(returnToken);
        assertEquals(authData.getToken().getExpires(), returnToken.getExpires());
        System.out.print("Expires: " + returnToken.getExpires());
    }

    @Test
    public void validateTokenShouldSucceedReturnProperTokenExpirationForMosso() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.validateToken(String.valueOf(accountId), authData.getToken().getId(), "mosso");

        assertNotNull(returnToken);
        assertEquals(authData.getToken().getExpires(), returnToken.getExpires());
        System.out.print("Expires: " + returnToken.getExpires());
    }

    @Test
    public void validateTokenShouldSucceedReturnProperTokenExpirationForNast() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.validateToken(nastId, authData.getToken().getId(), "nast");
        assertNotNull(returnToken);
        assertEquals(authData.getToken().getExpires(), returnToken.getExpires());
        System.out.print("Expires: " + returnToken.getExpires());
    }

    @Test(expected = KeyStoneException.class)
    public void validateTokenShouldThrowExceptionWhenUnAuthorizedForCloud() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.validateToken("cloudBob", authData.getToken().getId(), "cloud");
    }

    @Test(expected = KeyStoneException.class)
    public void validateTokenShouldThrowExceptionWhenUnAuthorizedForMosso() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.validateToken("mossoBob", authData.getToken().getId(), "mosso");
    }

    @Test(expected = KeyStoneException.class)
    public void validateTokenShouldThrowExceptionWhenUnAuthorizedFornast() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.validateToken("nastBob", authData.getToken().getId(), "nast");
    }

    @Test(expected = KeyStoneException.class)
    public void validateTokenShouldThrowExceptionWhenTokenNullForCloud() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.validateToken(username, "fakeToken", "cloud");
    }

    @Test(expected = KeyStoneException.class)
    public void validateTokenShouldThrowExceptionWhenTokenNullForMosso() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.validateToken(String.valueOf(accountId), "fakeToken", "mosso");
    }

    @Test(expected = KeyStoneException.class)
    public void validateTokenShouldThrowExceptionWhenTokenNullForNast() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.validateToken(nastId, "fakeToken", "nast");
    }

    @Test
    public void getTokenShouldSucceedForCloud() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.getToken(authData.getToken().getId());
        assertNotNull(returnToken);
        assertEquals(authData.getToken().getId(), returnToken.getId());
        System.out.print("Token: " + returnToken.getId());
    }

    @Test
    public void getTokenShouldSucceedForMosso() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.getToken(authData.getToken().getId());
        assertNotNull(returnToken);
        assertEquals(authData.getToken().getId(), returnToken.getId());
        System.out.print("Token: " + returnToken.getId());
    }

    @Test
    public void getTokenShouldSucceedForNast() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.getToken(authData.getToken().getId());
        assertNotNull(returnToken);
        assertEquals(authData.getToken().getId(), returnToken.getId());
        System.out.print("Token: " + returnToken.getId());
    }

    @Test
    public void getTokenShouldSucceedCompareTokenForCloud() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.getToken(authData.getToken().getId());

        assertNotNull(returnToken);
        assertEquals(authData.getToken().getId(), returnToken.getId());
        assertEquals(authData.getToken().getExpires(), returnToken.getExpires());
        System.out.print("Token: " + returnToken.getId());
    }

    @Test
    public void getTokenShouldSucceedCompareTokenForMosso() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.getToken(authData.getToken().getId());

        assertNotNull(returnToken);
        assertEquals(authData.getToken().getId(), returnToken.getId());
        assertEquals(authData.getToken().getExpires(), returnToken.getExpires());
        System.out.print("Token: " + returnToken.getId());
    }

    @Test
    public void getTokenShouldSucceedCompareTokenForNast() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        FullToken returnToken = clientKeyStone.getToken(authData.getToken().getId());

        assertNotNull(returnToken);
        assertEquals(authData.getToken().getId(), returnToken.getId());
        System.out.print("Token: " + returnToken.getId());
    }

    @Test(expected = KeyStoneException.class)
    public void getTokenShouldThrowExceptionWhenUnAuthorized() throws Exception {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.getToken("fakeToken");
    }

    @Test(expected = KeyStoneException.class)
    public void getTokenShouldThrowExceptionWhenTokenNull() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.getToken(null);
    }

    @Test
    public void listUserShouldSucceedForCloud() throws Exception {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.listUser(username);
        assertNotNull(user);
    }

    @Test
    public void listUserShouldSucceedForMosso() throws Exception {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.listUser(String.valueOf(accountId), "mosso");
        assertNotNull(user);
    }

    @Test
    public void listUserShouldSucceedForNast() throws Exception {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.listUser(nastId, "nast");
        assertNotNull(user);
    }

    @Test
    public void listUserShouldSucceedCompareNameForCloud() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.listUser(username);
        assertNotNull(user);
        assertEquals(username, user.getId());
    }

    @Test
    public void listUserShouldReturnValidKeyForcloud() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.listUser(username);
        assertNotNull(user);
        assertEquals(key, user.getKey());
    }

    @Test(expected = KeyStoneException.class)
    public void listUserShouldThrowFaultIfInvalidUserForCloud() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.listUser("blah");
    }

    @Test
    public void listUserShouldSucceedCompareNameForMosso() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.listUser(String.valueOf(accountId), "mosso");
        assertNotNull(user);
        assertEquals(String.valueOf(accountId), String.valueOf(user.getMossoId()));
    }

    @Test
    public void listUserShouldReturnValidKeyForMosso() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.listUser(String.valueOf(accountId), "mosso");
        assertNotNull(user);
        assertEquals(key, user.getKey());
    }

    @Test(expected = KeyStoneException.class)
    public void listUserShouldThrowFaultIfInvalidUserForMosso() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.listUser("blah", "mosso");
    }

    @Test
    public void listUserShouldSucceedCompareNameForNast() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.listUser(nastId, "nast");
        assertNotNull(user);
        assertEquals(nastId, user.getNastId());
    }

    @Test
    public void listUserShouldReturnValidKeyForNast() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.listUser(nastId, "nast");
        assertNotNull(user);
        assertEquals(key, user.getKey());
    }

    @Test(expected = KeyStoneException.class)
    public void listUserShouldThrowFaultIfInvalidUserForNast() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.listUser("blah", "nast");
    }

    @Test
    public void getUserKeyShouldReturnValidKey() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        assertEquals(key, clientKeyStone.getUserKey(username).getKey());
    }

    @Test(expected = KeyStoneException.class)
    public void getUserKeyShouldFailForUnknownUser() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        clientKeyStone.getUserKey("blah").getKey();
    }

    @Test
    public void createUserShouldSucceed() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.createUser("bobTest3", "1234567891011121313", 123457, "longNastidNameShouldg0here1", true);
        assertNotNull(user);
        clientKeyStone.deleteUser("bobTest3");
    }

    @Test
    public void setUserKeyShouldSucceed() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.createUser("bobTest3", "1234567891011121313", 123457, "longNastidNameShouldg0here1", true);
        User returnUser = clientKeyStone.setUserKey("newUserKey123", "bobTest3");
        assertEquals("newUserKey123", returnUser.getKey());
        clientKeyStone.deleteUser("bobTest3");
    }

    @Test(expected = KeyStoneException.class)
    public void deleteUserShouldSucceed() throws KeyStoneException, URISyntaxException {
        KeyStoneAdminClient clientKeyStone = new KeyStoneAdminClient(auth_stag_url);
        User user = clientKeyStone.createUser("bobTest3", "1234567891011121313", 123457, "longNastidNameShouldg0here1", true);
        clientKeyStone.deleteUser(user.getId());
        clientKeyStone.listUser(user.getId());
    }

    private User listUserForTests(KeyStoneAdminClient keyStoneAdminClient) throws URISyntaxException, KeyStoneException {
        return keyStoneAdminClient.listUser(username);
    }

    private void deleteUserForTests(KeyStoneAdminClient keyStoneAdminClient) throws URISyntaxException, KeyStoneException {
        keyStoneAdminClient.deleteUser(username);
    }

    @AfterClass
    public static void Teardown() throws KeyStoneException, URISyntaxException {
        keyStoneAdminClient = new KeyStoneAdminClient(auth_stag_url, bPass, bUser);
        keyStoneAdminClient.deleteUser(username);
    }
}

