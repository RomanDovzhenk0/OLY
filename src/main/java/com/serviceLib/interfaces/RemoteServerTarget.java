package com.serviceLib.interfaces;

public interface RemoteServerTarget {

    String getUser(String username);

    String getUser(int id);

    String addUser(String serializeUser);

    String updateUser(String serializeUser);

    String addAd(String serializeAd, int userId);

    String updateAd(String serializeAd);

    String deleteAd(String serializeAd);

    String getAds(String empty);

    String getAdsByAuthorId(int authorId);

    String getAdsByFilter(String heading, String category, int minPrice, int maxPrice, String cityName, String regionName, String orderType);
}
