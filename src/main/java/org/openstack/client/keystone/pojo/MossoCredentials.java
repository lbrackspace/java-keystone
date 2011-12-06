package org.openstack.client.keystone.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://docs.rackspacecloud.com/auth/api/v1.1")
public class MossoCredentials extends KeyStoneCredentials {
    private String mossoId;

    /**
     * The mossoId
     *
     * @return
     */
    @XmlAttribute(name = "mossoId")
    public String getMossoId() {
        return mossoId;
    }

    /**
     *
     * @param mossoId
     */
    public void setMossoId(String mossoId) {
        this.mossoId = mossoId;
    }
}
