package com.serviceLib.services;

import com.server.dbHandler.DBHandler;
import com.serviceLib.interfaces.Service;
import com.serviceLib.models.Ad;
import com.serviceLib.models.User;
import java.util.List;

public class ServerService implements Service {

    private static DBHandler dbHandler;

    public ServerService(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    public void addAd(Ad ad, int userId) {
        dbHandler.adRepository.addAd(ad, userId);
    }

    @Override
    public void updateAd(Ad ad) {
        dbHandler.adRepository.updateAd(ad);
    }

    @Override
    public void deleteAd(Ad ad) {
        dbHandler.adRepository.deleteAd(ad);
    }

    @Override
    public List<Ad> getAds() {
        return dbHandler.adRepository.getAds();
    }

    @Override
    public List<Ad> getAdsByAuthorId(int authorId) {
        return dbHandler.adRepository.getAdsByAuthorId(authorId);
    }

    @Override
    public List<Ad> getAdsByFilter(String heading, String category, int minPrice, int maxPrice, String cityName, String regionName, String orderType) {
        return  dbHandler.adRepository.getAdsByFilter(heading, category, minPrice, maxPrice, cityName, regionName, orderType);
    }

    @Override
    public User getUser(String username) {
        return dbHandler.userRepository.getUser(username);
    }

    @Override
    public User getUser(int id) {
        return dbHandler.userRepository.getUser(id);
    }

    @Override
    public void addUser(User user) {
        dbHandler.userRepository.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        dbHandler.userRepository.updateUser(user);
    }
}
