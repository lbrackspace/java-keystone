package org.openstack.client.keystone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack.client.keystone.Wrapper.KeyStoneResponseWrapper;
import org.openstack.client.keystone.auth.AuthData;
import org.openstack.client.keystone.pojo.CloudCredentials;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

public class KeyStoneClient {
    private final Log logger = LogFactory.getLog(KeyStoneClient.class);

    private String authUrl;
    private Client client;

    /**
     * @param authUrl the url to the KeyStone auth service
     * @param client  the client used to talk to KeyStone
     */
    public KeyStoneClient(String authUrl, Client client) throws KeyStoneException {
        this.authUrl = authUrl;
        if (authUrl == null) {
            this.authUrl = KeyStoneUtil.getProperty("auth_management_uri");
        }
        this.client = client;
    }

    /**
     * This method will create the clients web resource based on the auth_public_uri
     * found in the properties file
     *
     * @param authUrl the url to the KeyStone auth service
     * @throws URISyntaxException
     */
    public KeyStoneClient(String authUrl) throws KeyStoneException {
        this(authUrl, ClientBuilder.newBuilder().build());
    }

    /**
     * This method uses the default authUrl found in
     * the properties file
     */
    public KeyStoneClient() throws KeyStoneException {
        this(KeyStoneUtil.getProperty("auth_management_uri"));
    }


    /**
     * This method authenticates the user based on the authUrl, username, key and authType, where
     * authType is the service to authenticate against i.e(CLOUD|MOSSO|NAST). authType defaults to
     * Password type based authentication when neither CLOUD, MOSSO, or NAST is specified.
     *
     * @param url      the url to authenticate against
     * @param username the username used for authentication
     * @param key      the key used for authentication
     * @return AuthData
     * @throws KeyStoneException
     * @throws URISyntaxException
     */
    public AuthData authenticateUser(String url, String username, String key) throws KeyStoneException, URISyntaxException {
        URI uri = new URI(url + KeyStoneConstants.AUTH_PATH);
        Response response;
        try {
            CloudCredentials cloudCredentials = new CloudCredentials();
            cloudCredentials.setUsername(username);
            cloudCredentials.setKey(key);
            response = client.target(uri).request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).post(Entity.xml(cloudCredentials));
        } catch (ResponseProcessingException ux) {
            throw KeyStoneResponseWrapper.buildFaultMessage(ux.getResponse());
        }

        if (response != null && response.getStatus() == KeyStoneConstants.ACCEPTED || response.getStatus() == KeyStoneConstants.NON_AUTHORATIVE) {
            return (AuthData) response.getEntity();
        }

        if (response != null && response.getStatus() == KeyStoneConstants.UNAUTHORIZED) {
            return (AuthData) response.getEntity();
        }

        throw new KeyStoneException("The response could not be processed", null, response.getStatus());
    }


    /**
     * This method will use the authUrl associated with the client
     *
     * @param username the username used for authentication
     * @param key      the key used for authentication
     * @return AuthData
     * @throws KeyStoneException
     * @throws URISyntaxException
     */
    public AuthData authenticateUser(String username, String key) throws KeyStoneException, URISyntaxException {
        return authenticateUser(authUrl, username, key);
    }

}
