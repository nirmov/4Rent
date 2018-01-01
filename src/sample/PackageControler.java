package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/28/2017.
 */
public class PackageControler {
    public Label TypeLabel;
    public ChoiceBox TypeOption;
    public Button Addpackage;
    public static int amountofproducts;
    public static int price;
    public Label ProductsAmount;
    public Label ProductsPrice;
    public TextField nameofPackage;
    public TextField Discount;
    static StringProperty amount;
    public ChoiceBox Area;
    public ChoiceBox TypeOfPackage;
    static StringProperty pricebind;
    static List<RealEstateProduct> realEstateProducts;
    @FXML
    public void initialize()
    {
        realEstateProducts=new ArrayList<>();
        amount=new SimpleStringProperty("0");
        pricebind=new SimpleStringProperty("0");
        ProductsAmount.textProperty().bind(amount);
        ProductsPrice.textProperty().bind(pricebind);
        amountofproducts=0;
        price=0;
    }

    public void RealEstateFunc () throws IOException {
        Stage RealEstate = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("RealEstate.fxml"));
        Scene s = new Scene(root2, 720, 400);
        s.getStylesheets().add(getClass().getResource("LoginStyle.css").toExternalForm());
        RealEstate.setScene(s);
        RealEstate.show();
    }
    public void PetsFunc () throws IOException {
        Stage Pets = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("Pets.fxml"));
        Scene s = new Scene(root2, 720, 400);
        Pets.setScene(s);
        Pets.show();
    }
    public void   SecondHandFunc() throws IOException {
        Stage SecondHand = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("SecondHand.fxml"));
        Scene s = new Scene(root2, 720, 400);
        SecondHand.setScene(s);
        SecondHand.show();
    }
    public void   VehiclerFunc() throws IOException {
        Stage Vehicle = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("Vehicle.fxml"));
        Scene s = new Scene(root2, 720, 400);
        Vehicle.setScene(s);
        Vehicle.show();
    }
    public void home()
    {
        Stage window = (Stage) Addpackage.getScene().getWindow();
        window.close();
    }
    public void AddPackage () throws SQLException {
        String discount =Discount.getText();
        String PackageName=nameofPackage.getText();
        String area=Area.getValue().toString();
        String type=TypeOfPackage.getValue().toString();
        String price=ProductsPrice.getText();
        int pri=Integer.parseInt(price)*(100-Integer.parseInt(discount))/100;
        System.out.println("Price is  "+pri);
        String amount=ProductsAmount.getText();
        String name=Main.User.getValue().toString();
        name=name.substring(name.indexOf(" ")+1);
        WriteToRealEstate(Integer.parseInt(ProductsAmount.getText()),PackageName);
        PreparedStatement prep = Main.conn.prepareStatement(
                "insert into Packages values (?, ?,?, ?,?, ?);");
        prep.setString(1,PackageName);
        prep.setString(2, name);
        prep.setString(3, ""+pri);
        prep.setString(4, area);
        prep.setString(5, type);
        prep.setString(6, amount);
        prep.addBatch();
        prep.executeBatch();
        System.out.println("insert to Packages 1");
        Stage window = (Stage) Addpackage.getScene().getWindow();
        window.close();

    }
    public static void WriteToRealEstate(int amount,String packagename) throws SQLException {
        for (int i=0;i<amount;i++) {
            RealEstateProduct real=realEstateProducts.get(i);
            PreparedStatement prep = Main.conn.prepareStatement(
                    "insert into RealEstate values (?, ?,?, ?,?, ?,?,?,?);");
            prep.setString(1, real.UserName);
            prep.setString(2, real.ProductName);
            prep.setString(3, real.price);
            prep.setString(4, real.Address);
            prep.setString(5, real.AmountOfPeople);
            prep.setString(6, real.Picture);
            prep.setString(7, real.Priority);
            prep.setString(8, real.Type);
            prep.setString(9, packagename);
            prep.addBatch();
            prep.executeBatch();
            System.out.println("insert to RealEstat 1");
        }
    }
    public void TypeFunc()
    {
       String type=TypeOfPackage.getValue().toString();
       if (type.equals("Conservative"))
       {
           TypeLabel.setVisible(true);
           TypeOption.setVisible(true);
           TypeLabel.setText("Min Level Of Rank:");
           TypeOption.getItems().add("1");     TypeOption.getItems().add("2");     TypeOption.getItems().add("3");
           TypeOption.getItems().add("4");     TypeOption.getItems().add("5");     TypeOption.getItems().add("6");
           TypeOption.getItems().add("7");     TypeOption.getItems().add("8");     TypeOption.getItems().add("9");
           TypeOption.getItems().add("10");
       }
       else
       {
               TypeLabel.setVisible(false);
               TypeOption.setVisible(false);
       }
    }
}
