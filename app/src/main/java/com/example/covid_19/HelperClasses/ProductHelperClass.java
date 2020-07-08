package com.example.covid_19.HelperClasses;

import java.io.Serializable;

public class ProductHelperClass implements Serializable {

    Integer ID, ProductPrice;
    String ProductName, ProductImage, ProductDescription;

    public ProductHelperClass(Integer ID, Integer productPrice, String productName, String productImage, String productDescription) {
        this.ID = ID;
        ProductPrice = productPrice;
        ProductName = productName;
        ProductImage = productImage;
        ProductDescription = productDescription;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(Integer productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }
}
