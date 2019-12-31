package ir.maktabsharif.onlinshop.models;

import com.squareup.moshi.Json;

public class Category {

    @Json(name = "id")
    private long mID;

    @Json(name = "name")
    private String mName;

    @Json(name = "image")
    private Image mImage;

    @Json(name = "count")
    private int mCount;

    public long getID() {
        return mID;
    }

    public void setID(long ID) {
        mID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    @Override
    public String toString() {
        return "Category{" +
                "mID=" + mID +
                ", mName='" + mName + '\'' +
                ", mImage=" + mImage +
                ", mCount=" + mCount +
                '}';
    }
}
