package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class FullPaymentConfirmController {

    Connection con ;
    PreparedStatement preparedStatement , preparedStatement2;
    ResultSet resultSet , resultSet2;

    public FullPaymentConfirmController() {
        con = ConnectDatabase.ConnectDB();
    }

    @FXML
    Button confirm , select , wrong  , back;
    @FXML
    TextField textField ,textField2;
    @FXML
    ImageView imageView ;
    @FXML
    TableView tableIDs;

    ArrayList<String> idArray = new ArrayList<>();

    public void initialize(){
        textField2.setVisible(false);
        TableColumn<String, detailID> column1 = new TableColumn<>("Payment ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column1.setMinWidth(200);
        tableIDs.getColumns().add(column1);
        getsID();
        loopID();
        System.err.println(idArray);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                stage.close();
            }
        });
        select.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sql = "SELECT `last_proof`FROM `payment` WHERE payment_id = ? " ;
                try {
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1,textField.getText());
                    resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        textField2.setText(textField.getText());
                        System.err.println(textField2.getText());
                        Image image = new Image(resultSet.getString(1));
                        imageView.setImage(image);
                        textField.setText("");
                    }
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("CONFIRMATION");
                alert.setHeaderText(null);
                alert.setContentText("Confirm ? ");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()== ButtonType.OK) {
                    try {
                        String sql =  "UPDATE payment SET last_status = \"Payment Confirm \"  WHERE payment_id = ? " ;
                        preparedStatement = con.prepareStatement(sql);
                        preparedStatement.setString(1,textField2.getText());
                        preparedStatement.executeUpdate();
                        System.err.println(textField2.getText());
                    }catch (SQLException ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
        wrong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("CONFIRMATION");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure ? ");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()== ButtonType.OK) {
                    Alert alert2 = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
                    alert.setHeight(100);
                    alert.setWidth(200);
                    alert.setTitle("CONFIRMATION");
                    alert.setHeaderText(null);
                    alert.setContentText("Please Check Again");
                    Optional<ButtonType> result2 = alert2.showAndWait();
                    if(result2.get() == ButtonType.OK){
                        String sql = "UPDATE `payment` SET `last_proof`= ? WHERE payment_id = ? " ;
                        String sql2 = "UPDATE `payment` SET `last_status`= ? WHERE payment_id = ? " ;
                        try {
                            preparedStatement = con.prepareStatement(sql);
                            preparedStatement.setString(1,"NULL");
                            preparedStatement.setString(2,textField2.getText());
                            preparedStatement.executeUpdate();

                            preparedStatement2 = con.prepareStatement(sql2);
                            preparedStatement2.setString(1,"Not Payment");
                            preparedStatement2.setString(2,textField2.getText());

                            preparedStatement2.executeUpdate();
                        }catch (SQLException ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }

        });
    }

    public void getsID() {
        String sql = "SELECT `payment_id` FROM `payment` where last_status = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,"WaitConfirm");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idArray.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }
    public void loopID() {
        for (int i = 0; i < idArray.size() ; i ++) {
            Set<String> s = new LinkedHashSet<String>(idArray);
            idArray.clear();
            idArray.addAll(s);
            tableIDs.getItems().add(new detailID(idArray.get(i)));
        }
    }
}
