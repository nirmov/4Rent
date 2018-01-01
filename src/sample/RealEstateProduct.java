package sample;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Created by User on 12/29/2017.
 */
public class RealEstateProduct {
    public String Address;
    public String ProductName;
    public String AmountOfPeople;
    public String Picture;
    public String price;
    public String Type;
    public String Priority;
    public String UserName;
    public RealEstateProduct(String address,String Username, String nameof, String amountOfPeople, String picture, String price, String type, String priority) {
        Address = address;
        this.ProductName = nameof;
        AmountOfPeople = amountOfPeople;
        Picture = picture;
        this.price = price;
        Type = type;
        Priority = priority;
        UserName=Username;
    }

}
