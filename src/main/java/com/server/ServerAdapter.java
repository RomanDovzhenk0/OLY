package com.server;

import com.serviceLib.interfaces.RemoteServerTarget;
import com.serviceLib.models.Ad;

import static com.serviceLib.interfaces.Service.deserialize;
import static com.serviceLib.interfaces.Service.serialize;
import com.serviceLib.models.User;
import com.serviceLib.services.ServerService;

public class ServerAdapter implements RemoteServerTarget {

    public static ServerService serverService;

    @Override
    public String getUser(String username) {
        return serialize(serverService.getUser(username));
    }

    @Override
    public String getUser(int id) {
        return serialize(serverService.getUser(id));
    }

    @Override
    public String addUser(String serializeUser) {
        serverService.addUser((User)deserialize(serializeUser));
        return "null";
    }

    @Override
    public String updateUser(String serializeUser) {
        serverService.updateUser((User)deserialize(serializeUser));
        return "null";
    }

    @Override
    public String addAd(String serializeAd, int userId) {
        serverService.addAd((Ad)deserialize(serializeAd), userId);
        return "null";
    }

    @Override
    public String updateAd(String serializeAd) {
        serverService.updateAd((Ad)deserialize(serializeAd));
        return "null";
    }

    @Override
    public String deleteAd(String serializeAd) {
        serverService.deleteAd((Ad)deserialize(serializeAd));
        return "null";
    }

    @Override
    public String getAds(String empty) {
        return serialize(serverService.getAds());
    }

    @Override
    public String getAdsByAuthorId(int authorId) {
        return serialize(serverService.getAdsByAuthorId(authorId));
    }

    @Override
    public String getAdsByFilter(String heading, String category, int minPrice, int maxPrice, String cityName, String regionName, String orderType) {
        if(heading.equals("@null@")) { heading = null;}
        if(category.equals("@null@")) { category = null;}
        if(cityName.equals("@null@")) { cityName = null;}
        if(regionName.equals("@null@")) { regionName = null;}
        if(orderType.equals("@null@")) { orderType = null;}
        return serialize(serverService.getAdsByFilter(heading, category, minPrice, maxPrice, cityName, regionName, orderType));
    }
}
