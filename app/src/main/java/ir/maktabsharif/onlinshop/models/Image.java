package ir.maktabsharif.onlinshop.models;

import com.squareup.moshi.Json;

public class Image {

    @Json(name = "id")
    private long mID;

    @Json(name = "src")
    private String mURL;

    public long getID() {
        return mID;
    }

    public void setID(long ID) {
        mID = ID;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String URL) {
        mURL = URL;
    }

    @Override
    public String toString() {
        return "Image{" +
                "mID=" + mID +
                ", mURL='" + mURL + '\'' +
                '}';
    }
}
