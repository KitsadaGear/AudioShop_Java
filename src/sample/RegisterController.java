package sample;

import com.sun.glass.ui.CommonDialogs;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.annotation.Generated;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    ImageView imageView = new ImageView();
    @FXML
    TextField textField1, textField2, textField3, textField4, textField5, textField6, textField8 , hidden;
    @FXML
    Label label1;
    @FXML
    FileChooser fileChooser = new FileChooser();
    Connection con;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet ;
    @FXML
    Button openButton = new Button("Open a Picture...");
    public void start() {
        Stage stage = new Stage();
        stage.setTitle("File Chooser ");
        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                Stage stage = new Stage();
                stage.setTitle("File Chooser");
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                    hidden.setText(file.toURI().toString());
                }// Get URI IN DATABASE
            }
        });

    }

    public RegisterController() {
        con = ConnectDatabase.ConnectDB();
    }

    public int random() {
        Random random = new Random();
        return random.nextInt(88888888) + 11111111 ;
    }

    @FXML
    private void initialize(){
        start();
        label1.setVisible(false);
        hidden.setVisible(false);
    }

    @FXML
    public void handleButtonAction(MouseEvent event) {
        checkRePassword();
        if (label1.getText() == "Password match" && validateEmail() == true && validatePhone() == true && validateName() == true && validateSurName() == true &&
                validateUsername() == true && validatePassword() == true && validatePicture() == true) {
            if (checkDuplicateCustomerID() == true && checkDuplicateUsername() == true
                    && checkDuplicateEmail() == true && checkDuplicateNameAndSurname() == true && checkDuplicatePhoneNumber() == true) {
                if (textField8.getText().length() == 10) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeight(100);
                    alert.setWidth(200);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Register Complete");
                    alert.showAndWait();
                    register();
                    try {
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        Thread.sleep(1000);
                        Parent root = FXMLLoader.load(getClass().getResource("MainLogin.fxml"));
                        stage.setScene(new Scene(root, 620, 400));
                        stage.show();
                    } catch (Exception ex) {
                        System.err.println("Register Success");
                    }
                }
            }
        }
    }

    public void register() {
        String name = textField1.getText();
        String surname = textField2.getText();
        String email = textField3.getText();
        String username = textField4.getText();
        String password = textField5.getText();
        String phoneNumber = textField8.getText();
        String customer_id = "60" + random();
        String picture = hidden.getText();
        String sql = "INSERT INTO `customer`(`customer_id`,`FirstName`, `Lastname`,`PhoneNumber` ,`Email`, `Username`, `Password`, customer_picture) VALUES (?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, customer_id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, String.valueOf(phoneNumber));
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, username);
            preparedStatement.setString(7, password);
            preparedStatement.setString(8,picture);
            preparedStatement.executeUpdate();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @FXML
    private void checkRePassword() {
        if (textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty() || textField4.getText().isEmpty() ||
                textField5.getText().isEmpty() || textField6.getText().isEmpty() || textField8.getText().isEmpty() || imageView.getImage().toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeight(100);
            alert.setWidth(200);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill Complete Information");
            alert.showAndWait();
        }else{

            if (textField5.getText().equals(textField6.getText())) {
                label1.setTextFill(Color.GREEN);
                label1.setText("Password match");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Password not match");
                alert.showAndWait();
                label1.setText("Password not match");
                label1.setTextFill(Color.RED);
            }
        }
    }
    @FXML
    public void handleReturn(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeight(100);
        alert.setWidth(200);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to return ? ");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== ButtonType.OK) {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Thread.sleep(1000);
                Parent root = FXMLLoader.load(getClass().getResource("MainLogin.fxml"));
                stage.setScene(new Scene(root, 620, 400));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                System.err.println(ex.fillInStackTrace());
            }
        }else{
            ;
        }
    }


    public boolean checkDuplicateUsername() {
        String username = textField4.getText();
        String SqlCheckUsername = "SELECT * FROM `customer` WHERE Username = ? ";
        try {
            preparedStatement = con.prepareStatement(SqlCheckUsername);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Duplicate Username");
                alert.showAndWait();
            } else {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean checkDuplicateCustomerID() {
        String customer_id = "60" + random();
        String SqlCheckID = "SELECT * FROM `customer` WHERE customer_id = ? " ;
        try {
            preparedStatement = con.prepareStatement(SqlCheckID);
            preparedStatement.setString(1, customer_id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                random();
            } else {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean checkDuplicateNameAndSurname() {
        String name = textField1.getText();
        String surname = textField2.getText();
        String SqlCheckNameSurname = "SELECT * FROM `customer` WHERE FirstName = ? AND Lastname = ?" ;
        try {
            preparedStatement = con.prepareStatement(SqlCheckNameSurname);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Duplicate Person");
                alert.showAndWait();
            } else {
                return true ;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }return false ;
    }

    public boolean checkDuplicateEmail() {
        String email = textField3.getText();
        String SqlCheckEmail = "SELECT * FROM `customer` WHERE Email = ? " ;
        try {
            preparedStatement = con.prepareStatement(SqlCheckEmail);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Duplicate Email");
                alert.showAndWait();
            } else {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }return false ;
    }
    public boolean checkDuplicatePhoneNumber () {
        String phoneNumber = textField8.getText();
        String SqlCheckPhoneNumber = "SELECT * FROM `customer` WHERE PhoneNumber = ? " ;
        try {
            preparedStatement = con.prepareStatement(SqlCheckPhoneNumber);
            preparedStatement.setString(1, phoneNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Duplicate PhoneNumber");
                alert.showAndWait();
            } else {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean validateEmail(){
        Pattern p = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher m = p.matcher(textField3.getText());
        if(m.find() && m.group().equals(textField3.getText())){
            return true ;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeight(100);
            alert.setWidth(200);
            alert.setTitle("Validate Email");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Email");
            alert.showAndWait();
            return false ;
        }
    }
    public boolean validatePhone(){
        Pattern p = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");
        Matcher m = p.matcher(textField8.getText());
        if(m.find() && m.group().equals(textField8.getText())){
            return true ;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeight(100);
            alert.setWidth(200);
            alert.setTitle("Validate PhoneNumber");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid PhoneNumber");
            alert.showAndWait();
            return false ;
        }
    }

    public boolean validateName(){
        Pattern p = Pattern.compile("[A-Za-z_]+");
        Matcher m = p.matcher(textField1.getText());
        if(m.find() && m.group().equals(textField1.getText())){
            return true ;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeight(100);
            alert.setWidth(200);
            alert.setTitle("Validate Name");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Name");
            alert.showAndWait();
            return false ;
        }
    }

    public boolean validateSurName(){
        Pattern p = Pattern.compile("[A-Za-z_]+");
        Matcher m = p.matcher(textField2.getText());
        if(m.find() && m.group().equals(textField2.getText())){
            return true ;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeight(100);
            alert.setWidth(200);
            alert.setTitle("Validate Name");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Name");
            alert.showAndWait();
            return false ;
        }
    }
    public boolean validateUsername(){
        Pattern p = Pattern.compile("[A-Za-z0-9_]+");
        Matcher m = p.matcher(textField4.getText());
        if(m.find() && m.group().equals(textField4.getText())){
            return true ;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeight(100);
            alert.setWidth(200);
            alert.setTitle("Validate Username");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Username");
            alert.showAndWait();
            return false ;
        }
    }
    public boolean validatePassword(){
        Pattern p = Pattern.compile("[A-Za-z0-9_]+");
        Matcher m = p.matcher(textField5.getText());
        if(m.find() && m.group().equals(textField5.getText())){
            return true ;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeight(100);
            alert.setWidth(200);
            alert.setTitle("Validate Password");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Password");
            alert.showAndWait();
            return false ;
        }
    }
    public boolean validatePicture(){
        if(hidden.getText().contains("jpg") || hidden.getText().contains("png") || hidden.getText().contains("psd") || hidden.getText().contains("gif")){
            return true ;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeight(100);
            alert.setWidth(200);
            alert.setTitle("Validate Picture");
            alert.setHeaderText(null);
            alert.setContentText("Wrong Type");
            alert.showAndWait();
            return false ;
        }
    }

}





