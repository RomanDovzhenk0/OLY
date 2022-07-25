package com.client;

import com.serviceLib.services.ClientService;
import com.serviceLib.interfaces.Service;
import static com.serviceLib.interfaces.Service.*;
import com.serviceLib.models.Ad;
import com.serviceLib.models.User;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ClientAdapter implements Service {

    private static ClientAdapter instance;

    private ClientService clientService;

    private ClientAdapter(ClientService clientService) {
        this.clientService = clientService;
    }

    public static ClientAdapter getInstance() {
        if(instance == null) {

            XmlRpcClientConfigImpl xmlRpcClientConfig = new XmlRpcClientConfigImpl();
            try {
                xmlRpcClientConfig.setServerURL(new URL("http://localhost/4305"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(xmlRpcClientConfig);

            instance = new ClientAdapter(new ClientService(client));
        }
        return instance;
    }

    @Override
    public User getUser(String username) {
        return (User) deserialize(clientService.getUser(username));
    }

    @Override
    public User getUser(int id) {
        return (User) deserialize(clientService.getUser(id));
    }

    @Override
    public void addUser(User user) {
        clientService.addUser(serialize(user));
    }

    @Override
    public void updateUser(User user) {
        clientService.updateUser(serialize(user));
    }

    @Override
    public void addAd(Ad ad, int userId) {
       clientService.addAd(serialize(ad), userId);
    }

    @Override
    public void updateAd(Ad ad) {
        clientService.updateAd(serialize(ad));
    }

    @Override
    public void deleteAd(Ad ad) {
        clientService.deleteAd(serialize(ad));
    }

    @Override
    public List<Ad> getAds() {
        return (List<Ad>) deserialize(clientService.getAds("Empty"));
    }

    @Override
    public List<Ad> getAdsByAuthorId(int authorId) {
        return (List<Ad>) deserialize(clientService.getAdsByAuthorId(authorId));
    }

    @Override
    public List<Ad> getAdsByFilter(String heading, String category, int minPrice, int maxPrice, String cityName, String regionName, String orderType) {
        return (List<Ad>) deserialize(clientService.getAdsByFilter(heading, category, minPrice, maxPrice, cityName, regionName, orderType));
    }
}
