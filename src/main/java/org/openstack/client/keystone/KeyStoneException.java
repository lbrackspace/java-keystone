package org.openstack.client.keystone;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://docs.rackspacecloud.com/auth/api/v1.1")
public class KeyStoneException extends Exception {
    public String message;
    public String details;
    public int code;

    public KeyStoneException() {
    }

    /**
     * Create a new KeyStoneException
     *
     * @param message
     * @param details
     * @param code
     */
    public KeyStoneException(String message, String details, int code) {
        this.message = message;
        this.details = details;
        this.code = code;
    }

    /**
     * Get the message of the KetStoneException
     *
     * @return the message for this exception
     */
    @XmlAttribute(name = "message")
    public String getMessage() {
        return message;
    }

    /**
     * Set the message for KeyStoneException
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the details of the KetStoneException
     *
     * @return the details for this exception
     */
    @XmlAttribute(name = "details")
    public String getDetails() {
        return details;
    }

    /**
     * Set the details for KeyStoneException
     *
     * @param details
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Get the code of the KetStoneException
     *
     * @return the code for this exception
     */
    @XmlAttribute(name = "code")
    public int getCode() {
        return code;
    }

    /**
     * Set the code for KeyStoneException
     *
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }
}
