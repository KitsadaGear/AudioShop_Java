package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ForgetPasswordController {

    @FXML
    private TextField textField1 , textField2 ;


    @FXML
    private void click(MouseEvent event) throws Exception{
        sent();
    }

    public String getUsername() {
        return textField1.getText();
    }
    @FXML
    public void handleReturn(MouseEvent event) {
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
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
    String a ;
    String b ;

    public void clicked(){
        a = textField1.getText();
        b = textField2.getText();
    }
    Connection con;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null ;
    @FXML
    public void sent(){
        clicked();
        try {
            String sql = "Select Password FROM customer WHERE Username = ? AND Email = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,a);
            preparedStatement.setString(2,b);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Your Password is " + resultSet.getString("Password"));
                alert.showAndWait();
                resultSet.getString("Password");
                textField1.setText("");
                textField2.setText("");
            }else if (!resultSet.next()){
                textField1.setText("");
                textField2.setText("");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Username & Email");
                alert.showAndWait();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }catch (RuntimeException ex){
            ex.printStackTrace();
        }
    }

    public ForgetPasswordController(){
        con = ConnectDatabase.ConnectDB();
    }
}
