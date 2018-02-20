package com.stfalcon.chatkit.commons.models;

/**
 * Created by shubhamdhabhai on 20/02/18.
 */

public interface IActionMessage {

    public int get$id();

    public int getProductID();


    public String getName();


    public String getCode();


    public String getImageURL();

    public String getShortDescription();


    public String getLongDescription();

    public int getCategoryID();


    public int getShippingCost();

    public int getStatus();


    public int getBrandID();

}
