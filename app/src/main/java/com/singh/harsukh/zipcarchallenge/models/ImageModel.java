package com.singh.harsukh.zipcarchallenge.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harsukh on 3/22/16.
 */
public class ImageModel implements Parcelable {
    private String item_price;
    private String item_quantity;
    private String item_name;
    private Bitmap item_image;
    public ImageModel() {}
    public String getItem_price() {
        return item_price;
    }

    public String getItem_quantity() {
        return item_quantity;
    }

    public String getItem_name() {
        return item_name;
    }

    public Bitmap getItem_image() {
        return item_image;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public void setItem_quantity(String item_quantity) {
        this.item_quantity = item_quantity;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setItem_image(Bitmap item_image) {
        this.item_image = item_image;
    }

    protected ImageModel(Parcel in) {
        item_price = in.readString();
        item_quantity = in.readString();
        item_name = in.readString();
        item_image = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_price);
        dest.writeString(item_quantity);
        dest.writeString(item_name);
        dest.writeValue(item_image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ImageModel> CREATOR = new Parcelable.Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };
}
