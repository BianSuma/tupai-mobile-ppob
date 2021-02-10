package com.pajakku.tupaimobile.adapter;

public class SpinnerModel {
    private  String ProviderName="";
    private  String Image="";

    /*********** Set Methods ******************/
    public void setProviderName(String ProviderName)
    {
        this.ProviderName = ProviderName;
    }

    public void setImage(String Image)
    {
        this.Image = Image;
    }


    /*********** Get Methods ****************/
    public String getProviderName()
    {
        return this.ProviderName;
    }

    public String getImage()
    {
        return this.Image;
    }
}
