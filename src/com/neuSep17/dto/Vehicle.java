package com.neuSep17.dto;

import java.net.MalformedURLException;
import java.net.URL;

public class Vehicle
{

    private String id;
    private String webId;
    private Integer year;
    private String make;
    private String model;
    private String trim;
    private Category category;
    private String bodyType;
    private Float price;
    private URL photoUrl;

    public Vehicle(String[] arr)
    {
        this.id = arr[0];
        this.webId = arr[1];
        this.category = Category.getCategory(arr[2].toLowerCase());
        this.year = Integer.parseInt(arr[3]);
        this.make = arr[4];
        this.model = arr[5];
        this.trim = arr[6];
        this.bodyType = arr[7];
        this.price = Float.parseFloat(arr[8]);
        try
        {
            this.photoUrl = new URL(arr[9]);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        String str = this.id + "~" + this.webId + "~" + this.category + "~" + this.year + "~" + this.make + "~"
                + this.model + "~" + this.trim + "~" + this.bodyType + "~" + this.price + "~" + this.photoUrl;
        return str;
    }

    public String toSearchContent(){
        return this.webId + "~" + this.category + "~" + this.year + "~" + this.make + "~"
        + this.model + "~" + this.trim + "~" + this.bodyType + "~" + this.price;
    }

    public String getId()
    {
        return id;
    }

    public String getWebId()
    {
        return webId;
    }

    public Integer getYear()
    {
        return year;
    }

    public String getMake()
    {
        return make;
    }

    public String getModel()
    {
        return model;
    }

    public String getTrim()
    {
        return trim;
    }

    public Category getCategory()
    {
        return category;
    }

    public String getBodyType()
    {
        return bodyType;
    }

    public Float getPrice()
    {
        return price;
    }

    public URL getPhotoUrl()
    {
        return photoUrl;
    }

}
