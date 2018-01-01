package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by User on 12/28/2017.
 */

public class EditProducts {
    public ChoiceBox PackageList;
     public ChoiceBox ProductList;
    static String choose;
    public Button Home;
    @FXML
    public void initialize() throws SQLException {
        String name=Main.User.getValue().toString();
        choose="";
        name=name.substring(name.indexOf(" ")+1);
        String sql="select DISTINCT id from RealEstate WHERE Username = '"+name+"'";
        ResultSet rs = Main.stat.executeQuery(sql);
        while (rs.next()) {
            ProductList.getItems().add(rs.getString("id"));
        }
        String sql1="select DISTINCT PackageName from Packages WHERE Username = '"+name+"'";
        ResultSet rs1 = Main.stat.executeQuery(sql1);
        while (rs1.next()) {
            PackageList.getItems().add(rs1.getString("PackageName"));
        }

    }
    public void home()
    {
        Stage window = (Stage) Home.getScene().getWindow();
        window.close();
    }
    public void done()
    {
        Stage window = (Stage) Home.getScene().getWindow();
        window.close();
    }
    public void edit() throws SQLException, IOException {
        try {
            choose = ProductList.getValue().toString();
            Stage Login = new Stage();
            Parent root2 = FXMLLoader.load(getClass().getResource("Edit.fxml"));
            Scene s2 = new Scene(root2, 720, 400);
            s2.getStylesheets().add(getClass().getResource("LoginStyle.css").toExternalForm());
            Login.setScene(s2);
            Login.show();
            home();
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eror");
            alert.setHeaderText("Please chose product before press Edit button");
            alert.showAndWait();
        }
    }
    public void DeleteProductFromPuc() throws SQLException {
        try {
            String choose = ProductList.getValue().toString();
            String name = Main.User.getValue().toString();
            name = name.substring(name.indexOf(" ") + 1);
            String packageName = "";
            int ProductPric = 0;
            ResultSet rs = Main.stat.executeQuery("SELECT * from RealEstate WHERE id = '" + choose + "' AND Username = '" + name + "'");
            while (rs.next()) {
                packageName = rs.getString("packageName");
                ProductPric = Integer.parseInt(rs.getString("price"));
            }
            String PackagePrice = "";
            ResultSet rs1 = Main.stat.executeQuery("SELECT * from Packages WHERE PackageName = '" + packageName + "'");
            int amountOfprod = 0;
            while (rs1.next()) {
                PackagePrice = rs1.getString("price");
                int PackagePrice1 = Integer.parseInt(PackagePrice) - ProductPric;
                PackagePrice = "" + PackagePrice1;
                amountOfprod = Integer.parseInt(rs.getString("amountOfProducts"));
            }
            if (amountOfprod == 1) {

                PreparedStatement statement = Main.conn.prepareStatement("DELETE from Packages WHERE PackageName = ?");
                statement.setString(1, packageName);
                statement.executeUpdate();
                PackageList.getItems().remove(packageName);
            } else {
                PreparedStatement statement1 = Main.conn.prepareStatement("UPDATE Packages SET price = ?, amountOfProducts=? WHERE PackageName=?");
                statement1.setString(1, PackagePrice);
                statement1.setString(2, "" + amountOfprod);
                statement1.setString(3, packageName);
                statement1.executeUpdate();
            }
            PreparedStatement statement = Main.conn.prepareStatement("DELETE from RealEstate WHERE id = ? AND Username = ?");
            statement.setString(1, choose);
            statement.setString(2, name);
            statement.executeUpdate();
            ProductList.getItems().remove(choose);
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eror");
            alert.setHeaderText("Please chose product before press delete Product button");
            alert.showAndWait();
        }

    }
    public void DeletePackage() throws SQLException {
        try {
            String packageName = PackageList.getValue().toString();
            PreparedStatement statement = Main.conn.prepareStatement("DELETE from Packages WHERE PackageName = ?");
            statement.setString(1, packageName);
            statement.executeUpdate();
            PreparedStatement statement1 = Main.conn.prepareStatement("DELETE from RealEstate WHERE packageName = ?");
            statement1.setString(1, packageName);
            statement1.executeUpdate();
            ProductList.getItems().remove(packageName);
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eror");
            alert.setHeaderText("Please chose Package before press Delete Package button");
            alert.showAndWait();
        }
        //PackageList.getItems().removeAll();
    }
}
