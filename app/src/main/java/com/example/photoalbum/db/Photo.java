package com.example.photoalbum.db;

public class Photo {
    private String absolutePath;
    private String folder;
    private String displayName;
    private String dateAdded;
    private String dateModified;
    private double height;
    private double width;
    private String id;
    private String isFavorite;
    private String isTrash;

    public Photo(String absolutePath, String folder, String displayName,
     String dateAdded, String dateModified, String height, String width, String id, String isFavorite, String isTrash) {
        this.absolutePath = absolutePath;
        this.folder = folder;
        this.displayName = displayName;
        this.dateAdded = dateAdded;
        this.dateModified = dateModified;
        this.height = Double.parseDouble(height);
        this.width = Double.parseDouble(width);
        this.id = id;
        this.isFavorite = isFavorite;
        this.isTrash = isTrash;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public String getFolder() {
        return folder;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getDateModified() {
        return dateModified;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public String getId() { return id; }

    public String getIsFavorite() { return isFavorite; }

    public String getIsTrash() { return isTrash; }
}
