package org.openstack.client.keystone.Wrapper;

import org.openstack.client.keystone.KeyStoneConstants;

public class KeyStonePropertyWrapper {
    public static String mapType(String type) {
        if (KeyStoneConstants.MOSSO_TYPE.compareToIgnoreCase(type) == 0) {
            return KeyStoneConstants.MOSSO_TYPE;
        }if (KeyStoneConstants.NAST_TYPE.compareToIgnoreCase(type) == 0) {
            return KeyStoneConstants.NAST_TYPE;
        }if (KeyStoneConstants.CLOUD_TYPE.compareToIgnoreCase(type) == 0) {
            return KeyStoneConstants.CLOUD_TYPE;
        }if (KeyStoneConstants.TOKEN_PATH.compareToIgnoreCase(type) == 0) {
            return KeyStoneConstants.TOKEN_PATH;
        }if (KeyStoneConstants.AUTH_PATH.compareToIgnoreCase(type) == 0) {
            return KeyStoneConstants.AUTH_PATH;
        }if (KeyStoneConstants.USER_PATH.compareToIgnoreCase(type) == 0) {
            return KeyStoneConstants.USER_PATH;
        }
        return null;
    }
}
