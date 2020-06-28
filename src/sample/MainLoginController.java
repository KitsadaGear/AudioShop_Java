package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainLoginController {
    @FXML
    public Label label2, time;

    @FXML
    public void initialize() {
    }

    @FXML
    public Button staffLogin, customerLogin;
    @FXML
    TextField textField1;
    @FXML
    PasswordField textField2;


    @FXML
    public void handleButtonActionStaff(ActionEvent event) {
        if (event.getSource() == staffLogin) {
            if (login().equals("Success")) {
                try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Staff.fxml"));
                    Parent root = (Parent) loader.load();
                    StaffController staffController = loader.getController();
                    staffController.setFromLogin(textField1.getText());
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        }
    }

    @FXML
    public void handleButtonActionCustomer(ActionEvent event) {
        if (event.getSource() == customerLogin) {
            if (loginCustomer().equals("Success")) {
                try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerMain.fxml"));
                    Parent root = (Parent) loader.load();
                    CustomerMainController customerMainController = loader.getController();
                    customerMainController.setFromLogin(textField1.getText());
                    customerMainController.disable(textField1.getText());
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        }
    }


    @FXML
    public void toRegister(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Thread.sleep(800);
            Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
            stage.setScene(new Scene(root, 665, 521));
            stage.show();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    public void forgetPassword(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Thread.sleep(800);
            Parent root = FXMLLoader.load(getClass().getResource("ForgetPassword.fxml"));
            stage.setScene(new Scene(root, 420, 300));
            stage.show();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public MainLoginController() {

        con = ConnectDatabase.ConnectDB();
    }

    Connection con;
    PreparedStatement preparedStatement, preparedStatement2;
    ResultSet resultSet, resultSet2;

    private String login() {
        String username = textField1.getText();
        String password = textField2.getText();
        String sql = "SELECT * FROM employee WHERE Username = ? AND Password = ?  AND emp_type = ? ";
        String sql2 = "Select emp_type from employee where Username = ? And Password = ? ";
        try {
            preparedStatement2 = con.prepareStatement(sql2);
            preparedStatement2.setString(1, username.toLowerCase());
            preparedStatement2.setString(2, password.toLowerCase());
            resultSet2 = preparedStatement2.executeQuery();
            if (resultSet2.next()) {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, username.toLowerCase());
                preparedStatement.setString(2, password.toLowerCase());
                preparedStatement.setString(3,resultSet2.getString(1));
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    System.err.println("Wrong Login");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeight(100);
                    alert.setWidth(200);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Username / Password");
                    alert.showAndWait();
                    textField1.clear();
                    textField2.clear();
                    return "Error";
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeight(100);
                    alert.setWidth(200);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Success");
                    alert.showAndWait();
                    return "Success";
                }

            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Username / Password");
                alert.showAndWait();
                textField1.clear();
                textField2.clear();
                return  "Error" ;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return "Exception";
        }
    }





    private String loginCustomer(){
        String username = textField1.getText();
        String password = textField2.getText();
        String sql = "SELECT * FROM customer WHERE Username = ? AND Password = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,username.toLowerCase());
            preparedStatement.setString(2,password.toLowerCase());
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                System.err.println("Wrong Login");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Username / Password");
                alert.showAndWait();
                textField1.clear();
                textField2.clear();
                return "Error" ;
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Login Success");
                alert.showAndWait();
                return "Success";

            }
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
            return "Exception" ;
        }
    }
}
