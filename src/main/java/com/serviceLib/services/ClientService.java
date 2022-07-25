package com.serviceLib.services;

import com.serviceLib.interfaces.RemoteServerTarget;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

import java.util.Collections;
import java.util.Vector;

public class ClientService implements RemoteServerTarget {

    private XmlRpcClient client;

    public ClientService(XmlRpcClient client) {
        this.client = client;
    }

    @Override
    public String getUser(String username) {
        try {
            return (String) client.execute("ServerService.getUser", Collections.singletonList(username));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUser(int id) {
        try {
            return (String) client.execute("ServerService.getUser", Collections.singletonList(id));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String addUser(String serializedUser) {
        try {
            client.execute("ServerService.addUser", Collections.singletonList(serializedUser));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateUser(String serializedUser) {
        try {
            client.execute("ServerService.updateUser", Collections.singletonList(serializedUser));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String addAd(String serializedAd, int userId) {
        Vector vector = new Vector();
        vector.addElement(serializedAd);
        vector.addElement(userId);
        try {
            client.execute("ServerService.addAd", vector);
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateAd(String serializedAd) {
        try {
            client.execute("ServerService.updateAd", Collections.singletonList(serializedAd));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteAd(String serializedAd) {
        try {
            client.execute("ServerService.deleteAd", Collections.singletonList(serializedAd));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAds(String empty) {
        try {
            return (String) client.execute("ServerService.getAds", Collections.singletonList("Null"));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAdsByAuthorId(int authorId) {
        try {
            return (String) client.execute("ServerService.getAdsByAuthorId", Collections.singletonList(authorId));
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAdsByFilter(String heading, String category, int minPrice, int maxPrice, String cityName, String regionName, String orderType) {
        Vector vector = new Vector();
        vector.addElement(heading == null ? "@null@" : heading);
        vector.addElement(category == null ? "@null@" : category);
        vector.addElement(minPrice);
        vector.addElement(maxPrice);
        vector.addElement(cityName == null ? "@null@" : cityName);
        vector.addElement(regionName == null ? "@null@" : regionName);
        vector.addElement(orderType == null ? "@null@" : orderType);
        try {
            return (String) client.execute("ServerService.getAdsByFilter", vector);
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return null;
    }
}
