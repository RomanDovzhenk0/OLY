package com.serviceLib.common;

import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;

public class CategoryChangerController {
    private static final Menu[] MENUS = {
            new Menu("Any Category"),
            new Menu("Child's world"),
            new Menu("Real estate"),
            new Menu("Auto"),
            new Menu("Spare parts for transport"),
            new Menu("Job"),
            new Menu("Animals"),
            new Menu("House & garden"),
            new Menu("Electronics"),
            new Menu("Business & Services"),
            new Menu("Fashion & Style"),
            new Menu("Hobbies, recreation and sports")
    };

    private static final String[][] MENU_ITEM_ARRAYS = {
            {"All in Children's World", "Baby clothes", "Children's shoes", "Baby strollers", "Child car seats", "Children's furniture", "Toys", "Children's transport", "Goods for feeding", "Goods for schoolchildren", "Other baby products"},
            {"All in Real Estate", "Apartments", "Rooms", "Houses", "Earth", "Commercial real estate", "Garages, parking", "Daily rental housing"},
            {"All in auto", "Cars", "Trucks", "Buses", "Moto", "Agricultural transport", "Water transport", "Air Transport", "Trailers / Motor-homes", "Other transport"},
            {"All in Spare Parts", "Auto parts and accessories", "Tires and wheels", "Spare parts for special transport", "Motorcycle parts and accessories", "Other spare parts"},
            {"All in Work", "Retail", "Logistics", "Building", "Call Centers / Telecommunications", "HR", "Security", "Cleaning", "Beauty / Fitness", "Education / Translation", "Culture / Art / Entertainment", "Medicine / Pharmaceuticals", "IT / Computers", "Banking / Finance", "Real estate", "Advertising", "Manufacturing", "Agriculture and forestry", "Students"},
            {"All in Animals", "Dogs", "Cats", "Aquarium", "Birds", "Rodents", "Reptiles", "Farm animals", "Other animals", "Pet supplies"},
            {"All in Home & Garden", "Cantilever / Consumables", "Furniture", "Food / Drinks", "Garden", "Interior items", "Construction", "Instruments", "Houseplants", "Crockery / Kitchen utensils", "Garden tools", "Household chemicals", "Other household goods"},
            {"All in Electronics", "Phones and accessories", "Computers and accessories", "Photo / Video", "TV / Video equipment", "Audio", "Games and game consoles", "Tablets / El. books and accessories", "Laptops and accessories", "Home Appliances", "Kitchen Appliances", "Climatic equipment", "Individual care", "Accessories and components", "Other electronics"},
            {"All in Business & Services", "Construction / Renovation / Cleaning", "Financial services", "Transportation", "Advertising / Marketing / Internet", "Babysitters / Nurses", "Raw materials / Materials", "Beauty / Health", "Education / Sports", "Services for animals", "Entertainment / Art / Photo / Video", "Tourism", "Legal services", "Other services"},
            {"All in Fashion & Style", "Clothes / Shoes", "For Wedding", "Wrist watch", "Accessories", "Gifts", "Beauty / Health", "Fashion"},
            {"All in Hobbies, Recreation and sports", "Antiques", "Musical instruments", "Sports / Recreation", "Books / Magazines", "Tickets"},
    };

    public static String[] getArrayOfCategory(String category) {
        for (String[] menuItemArray : MENU_ITEM_ARRAYS) {
            if (menuItemArray[0].equals(category)) {
                return menuItemArray;
            }
        }
        return new String[]{"Any Category"};
    }

    public static void addCategories(Button categorySetButton, ContextMenu categoryContextMenu) {

        categoryContextMenu.getItems().addAll(MENUS);

        MENUS[0].setOnAction(event -> {
            categorySetButton.setText("Any Category");
            categoryContextMenu.hide();
        });

        for (int i = 1 ; i < MENUS.length; i++) {
            MENUS[i].getItems().clear();
            for (int j = 0; j < MENU_ITEM_ARRAYS[i - 1].length; j++) {
                String category = MENU_ITEM_ARRAYS[i - 1][j];
                CheckMenuItem checkMenuItem = new CheckMenuItem(category);
                checkMenuItem.setOnAction(event -> {
                    categorySetButton.setText(category);
                    checkMenuItem.setSelected(false);
                });
                MENUS[i].getItems().add(checkMenuItem);
            }
        }

        categorySetButton.setContextMenu(categoryContextMenu);

        categorySetButton.setOnAction(event -> {
            categorySetButton.getContextMenu().show(categorySetButton.getScene().getWindow());
        });
    }
}