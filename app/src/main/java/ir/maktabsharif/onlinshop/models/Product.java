package ir.maktabsharif.onlinshop.models;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class Product {
    @Json(name = "id")
    private long mID;

    @Json(name = "name")
    private String mName;

    @Json(name = "permalink")
    private String mLink;

    @Json(name = "date_created")
    private String mDate;

    @Json(name = "on_sale")
    private boolean isOnSale;

    @Json(name = "sale_price")
    private String mSalePrice;

    @Json(name = "description")
    private String mDescription;

    @Json(name = "regular_price")
    private String mPrice;

    @Json(name = "total_sales")
    private int TotalSales;

    @Json(name = "average_rating")
    private String mAverageRating;

    @Json(name = "rating_count")
    private int mRatingCount;

    @Json(name = "categories")
    private List<Category> mCategories = new ArrayList<>();

    @Json(name = "images")
    private List<Image> mImages = new ArrayList<>();

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

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public void setOnSale(boolean onSale) {
        isOnSale = onSale;
    }

    public String getSalePrice() {
        return mSalePrice;
    }

    public void setSalePrice(String salePrice) {
        mSalePrice = salePrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public int getTotalSales() {
        return TotalSales;
    }

    public void setTotalSales(int totalSales) {
        TotalSales = totalSales;
    }

    public String getAverageRating() {
        return mAverageRating;
    }

    public void setAverageRating(String averageRating) {
        mAverageRating = averageRating;
    }

    public int getRatingCount() {
        return mRatingCount;
    }

    public void setRatingCount(int ratingCount) {
        mRatingCount = ratingCount;
    }

    public List<Category> getCategories() {
        return mCategories;
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public void setImages(List<Image> images) {
        mImages = images;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    @Override
    public String toString() {
        return "Product{" +
                "mID=" + mID +
                ", mName='" + mName + '\'' +
                ", mDate='" + mDate + '\'' +
                ", isOnSale=" + isOnSale +
                ", mSalePrice='" + mSalePrice + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mPrice='" + mPrice + '\'' +
                ", TotalSales=" + TotalSales +
                ", mAverageRating='" + mAverageRating + '\'' +
                ", mRatingCount=" + mRatingCount +
                ", mCategories=" + mCategories +
                ", mImages=" + mImages +
                '}';
    }
}
