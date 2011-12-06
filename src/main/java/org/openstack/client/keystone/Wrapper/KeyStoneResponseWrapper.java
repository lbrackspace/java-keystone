package org.openstack.client.keystone.Wrapper;

import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack.client.keystone.KeyStoneConstants;
import org.openstack.client.keystone.KeyStoneException;
import org.openstack.client.keystone.faults.*;

public class KeyStoneResponseWrapper {
    private static final Log logger = LogFactory.getLog(KeyStoneResponseWrapper.class);

    public static KeyStoneException buildFaultMessage(ClientResponse response) throws KeyStoneException {
        if (response != null) {
            logger.info("ResponseWrapper, response status code is: " + response.getStatus());
            try {
                if (response.getStatus() == KeyStoneConstants.BAD_REQUEST) {
                    BadRequestFault e = response.getEntity(BadRequestFault.class);
                    return new KeyStoneException(e.getMessage(), e.getDetails(), e.getCode());
                }
                if (response.getStatus() == KeyStoneConstants.UNAUTHORIZED) {
                    UnauthorizedFault e = response.getEntity(UnauthorizedFault.class);
                    return new KeyStoneException(e.getMessage(), e.getDetails(), e.getCode());
                }
                if (response.getStatus() == KeyStoneConstants.FORBIDDEN) {
                    ForbiddenFault e = response.getEntity(ForbiddenFault.class);
                    if (e != null) {
                        return new KeyStoneException(e.getMessage(), e.getDetails(), e.getCode());
                    } else {
                        UserDisabledFault udf = response.getEntity(UserDisabledFault.class);
                        return new KeyStoneException(udf.getMessage(), udf.getDetails(), udf.getCode());
                    }
                }
                if (response.getStatus() == KeyStoneConstants.NOT_FOUND) {
                    ItemNotFoundFault e = response.getEntity(ItemNotFoundFault.class);
                    return new KeyStoneException(e.getMessage(), e.getDetails(), e.getCode());
                }
                if (response.getStatus() == KeyStoneConstants.NOT_PERMITTED) {
                    return new KeyStoneException("Operation not allowed", "The requested resource or operation could not be found", KeyStoneConstants.NOT_PERMITTED);
                }
                if (response.getStatus() == KeyStoneConstants.NAME_CONFLICT) {
                    UsernameConflictFault e = response.getEntity(UsernameConflictFault.class);
                    return new KeyStoneException(e.getMessage(), e.getDetails(), e.getCode());
                }
                if (response.getStatus() == KeyStoneConstants.SERVICE_UNAVAILABLE) {
                    ServiceUnavailableFault e = response.getEntity(ServiceUnavailableFault.class);
                    return new KeyStoneException(e.getMessage(), e.getDetails(), e.getCode());
                }
                if (response.getStatus() == KeyStoneConstants.AUTH_FAULT) {
                    AuthFault e = response.getEntity(AuthFault.class);
                    return new KeyStoneException(e.getMessage(), e.getDetails(), e.getCode());
                }
            } catch (Exception ex) {
                logger.error("Exception was thrown for response with status: " + response.getStatus());
                if (ex instanceof UnsupportedOperationException) {
                    throw new KeyStoneException("There was an error communicating with the auth service...", ex.getLocalizedMessage(), response.getStatus());
                }
                logger.error("Exception was thrown and could not be handled by the client. Response status code: " + response.getStatus());
                throw new KeyStoneException(ex.getMessage(), ex.getLocalizedMessage(), response.getStatus());
            }
        }
        return new KeyStoneException("Unable to process request.", "There was an unexpected error communicating with the auth service.", KeyStoneConstants.AUTH_FAULT);
    }
}
