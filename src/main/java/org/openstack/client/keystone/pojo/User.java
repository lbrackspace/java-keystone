package org.openstack.client.keystone.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://docs.rackspacecloud.com/auth/api/v1.1")
public class User {
    private String id;
    private String key;
    private Integer mossoId;
    private String nastId;
    private boolean enabled;


    public User() {
    }

    /**
     * Create a new User object
     *
     * @param id
     * @param key
     * @param mossoId
     * @param nastId
     * @param enabled
     */
    public User(String id, String key, Integer mossoId, String nastId, boolean enabled) {
        this.id = id;
        this.key = key;
        this.mossoId = mossoId;
        this.nastId = nastId;
        this.enabled = enabled;
    }

    /**
     * Get the id
     *
     * @return the id
     */
    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    /**
     * Set the id for KeyStoneCredentials
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the key
     *
     * @return the key
     */
    @XmlAttribute(name = "key")
    public String getKey() {
        return key;
    }

    /**
     * Set the key
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return
     */
    @XmlAttribute(name = "mossoId")
    public Integer getMossoId() {
        return mossoId;
    }

    /**
     * Set the mossoId
     *
     * @param mossoId
     */
    public void setMossoId(Integer mossoId) {
        this.mossoId = mossoId;
    }

    /**
     * @return
     */
    @XmlAttribute(name = "nastId")
    public String getNastId() {
        return nastId;
    }

    /**
     * Set the nastId
     *
     * @param nastId
     */
    public void setNastId(String nastId) {
        this.nastId = nastId;
    }

    /**
     * @return
     */
    @XmlAttribute(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set the user is enabled
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
