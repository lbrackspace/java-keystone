package org.openstack.client.keystone.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://docs.rackspacecloud.com/auth/api/v1.1")
public class PasswordKeyStoneCredentials extends KeyStoneCredentials {
    private String password;

    /**
     * The password
     *
     * @return
     */
    @XmlAttribute(name = "password")
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
