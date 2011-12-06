package org.openstack.client.keystone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Properties;


public class KeyStoneConstants {
    private static final Log logger = LogFactory.getLog(KeyStoneAdminClient.class);

    /** HTTP Header token that identifies Accepted status **/
    public static final int ACCEPTED = 200;
    /** HTTP Header token that identifies Create status **/
    public static final int CREATED = 201;
    /** HTTP Header token that identifies OK status **/
    public static final int OK = 202;
    /** HTTP Header token that identifies OK status **/
    public static final int NON_AUTHORATIVE = 203;
    /** HTTP Header token that identifies OK status **/
    public static final int NO_CONTENT = 204;
    /** HTTP Header token that identifies BadRequest status **/
    public static final int BAD_REQUEST = 400;
    /** HTTP Header token that identifies UnAuthorized status **/
    public static final int UNAUTHORIZED = 401;
    /** HTTP Header token that identifies Forbidden status **/
    public static final int FORBIDDEN = 403;
    /** HTTP Header token that identifies NotFound status **/
    public static final int NOT_FOUND = 404;
    /** HTTP Header token that identifies MethodNotFound status **/
    public static final int NOT_PERMITTED = 405;
    /** HTTP Header token that identifies NameConflict status **/
    public static final int NAME_CONFLICT = 409;
    /** HTTP Header token that identifies ServiceUnAvailable status **/
    public static final int SERVICE_UNAVAILABLE = 503;
    /** HTTP Header token that identifies AuthFault status **/
    public static final int AUTH_FAULT = 500;

    /** Constants used for performing queries against the KeyStone service **/
    public static final String CLOUD_TYPE = "cloud";
    public static final String NAST_TYPE = "nast";
    public static final String MOSSO_TYPE = "mosso";
    public static final String TOKEN_PATH = "token";
    public static final String USER_PATH = "users";
    public static final String AUTH_PATH = "auth";
    public static final String KEY_PATH = "key";

    /** Default error responses **/
    public static final String MISSING_PROP = "One or more values necessary for this request could not be found, please check the request and try again.";

     public static Properties MIMETYPES = new Properties ();

    static {
    	try
        {
    		MIMETYPES.load (KeyStoneConstants.class.getResourceAsStream("MIME.types"));
        }
        catch (IOException err)
        {
            logger.warn("Could not load MIME.types all refrences to FilesConstants.MIMETYPES will return null.", err);
        }
    }

    /**
     * Convenience method to get a MIME Type.  If none is found it will return "application/octet-stream"
     *
     * @param fileExt
     * @return The suggested MIME type for the file extention.
     */
    public static String getMimetype (String fileExt)
    {
    	return KeyStoneConstants.MIMETYPES.getProperty(fileExt.toLowerCase(), "application/octet-stream");
    }

}
