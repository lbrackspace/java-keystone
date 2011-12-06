package org.openstack.client.keystone.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://docs.rackspacecloud.com/auth/api/v1.1", name = "credentials")
public class CloudCredentials extends KeyStoneCredentials {
}
