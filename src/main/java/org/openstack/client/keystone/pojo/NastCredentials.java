package org.openstack.client.keystone.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://docs.rackspacecloud.com/auth/api/v1.1")
public class NastCredentials extends KeyStoneCredentials {
    private String nastId;

    /**
     * The nastId
     *
     * @return
     */
    @XmlAttribute(name = "nastId")
    public String getNastId() {
        return nastId;
    }

    /**
     *
     * @param nastId
     */
    public void setNastId(String nastId) {
        this.nastId = nastId;
    }
}
