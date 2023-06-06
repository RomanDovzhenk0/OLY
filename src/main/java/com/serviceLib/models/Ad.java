package com.serviceLib.models;

import javafx.scene.image.Image;

import java.io.*;
import java.sql.Date;

public class Ad implements Serializable {
    private int id;
    private int authorId;
    private String heading;
    private String description;
    private String category;
    private int price;
    private int likesCount;
    private int viewsCount;
    private int telephoneAndEmailCheckCount;
    private File imageFile;
    transient private Image image;
    private Date createdAt;

    public Ad(String heading, String description, String category, int price, File imageFile) {
        this.heading = heading;
        this.description = description;
        this.category = category;
        this.price = price;
        this.imageFile = imageFile;
    }

    public Ad(int id, int authorId, String heading, String description, String category, int price, int likesCount, int viewsCount, int telephoneAndEmailCheckCount, Image image, Date createdAt) {
        this.id = id;
        this.authorId = authorId;
        this.heading = heading;
        this.description = description;
        this.category = category;
        this.price = price;
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
        this.telephoneAndEmailCheckCount = telephoneAndEmailCheckCount;
        this.image = image;
        this.createdAt = createdAt;
    }

    public Ad(int id, int authorId, String heading, String description, String category, int price, int likesCount, int viewsCount, int telephoneAndEmailCheckCount, File imageFile, Date createdAt) {
        this.id = id;
        this.authorId = authorId;
        this.heading = heading;
        this.description = description;
        this.category = category;
        this.price = price;
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
        this.telephoneAndEmailCheckCount = telephoneAndEmailCheckCount;
        this.imageFile = imageFile;
        this.createdAt = createdAt;
    }

    public Image getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public int getTelephoneAndEmailCheckCount() {
        return telephoneAndEmailCheckCount;
    }

    public File getImageFile() {
        return imageFile;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public void setTelephoneAndEmailCheckCount(int telephoneAndEmailCheckCount) {
        this.telephoneAndEmailCheckCount = telephoneAndEmailCheckCount;
    }

    private void writeObject(ObjectOutputStream os) {
        try {
            os.defaultWriteObject();
            if(image != null) {
                SerializableImage serializableImage = new SerializableImage();
                serializableImage.setImage(image);
                os.writeObject(serializableImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readObject(ObjectInputStream is) {
        try {
            is.defaultReadObject();
            this.image = ((SerializableImage) is.readObject()).getImage();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
