package org.openstack.client.keystone.pojo;

import javax.xml.bind.annotation.XmlAttribute;

public class KeyStoneCredentials {
    private String username;
    private String key;


    public KeyStoneCredentials() {}

    /**
     * Create a new KeyStoneCredentials object
     *
     * @param username
     * @param key
     */

    public KeyStoneCredentials(String username, String key) {
        this.username = username;
        this.key = key;
    }

    /**
     * Get the username for KeyStoneCredentials
     *
     * @return the username
     */
    @XmlAttribute(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     * Set the username for KeyStoneCredentials
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the key for KeyStoneCredentials
     *
     * @return the key
     */
    @XmlAttribute(name = "key")
    public String getKey() {
        return key;
    }

    /**
     * Set the key for KeyStoneCredentials
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }
}
