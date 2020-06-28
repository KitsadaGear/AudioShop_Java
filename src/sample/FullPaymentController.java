package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class FullPaymentController {

    Connection con;
    PreparedStatement preparedStatement, preparedStatement2 , preparedStatement3;
    ResultSet resultSet, resultSet2 , resultSet3;

    public FullPaymentController() {
        con = ConnectDatabase.ConnectDB();
    }

    @FXML
    FileChooser fileChooser = new FileChooser();
    @FXML
    Button openButton = new Button("Open a Picture...");
    @FXML
    TextField hidden;
    @FXML
    Button confirm, close;
    @FXML
    TableView tableView;
    @FXML
    Label label1 , labelID;
    @FXML
    TextField showPrize  , showFullPrize;
    ArrayList<String> arrayCountID = new ArrayList<>();
    ArrayList<String> arrayCount1 = new ArrayList<>();
    ArrayList<String> arrayCount2 = new ArrayList<>();
    ArrayList<String> arrayCount3 = new ArrayList<>();
    public void initialize() {

        showFullPrize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        start();
        if (hidden.getText().isEmpty()) {
            confirm.setDisable(true);
        }


        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("CONFIRMATION");
                alert.setHeaderText(null);
                alert.setContentText("Confirm ? ");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (hidden.getText().contains("jpg") || hidden.getText().contains("png") || hidden.getText().contains("psd") || hidden.getText().contains("gif")) {
                        sentProof();
                        sentStatus();
                    }
                } else if (result.get() == ButtonType.CANCEL) {
                    hidden.clear();
                }
            }
        });

        TableColumn<String, paymentDetail> columnA = new TableColumn<>("Product ID");
        columnA.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        columnA.setMinWidth(130);
        tableView.getColumns().add(columnA);

        TableColumn<String, paymentDetail> columnB = new TableColumn<>("Product name");
        columnB.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        columnB.setMinWidth(230);
        tableView.getColumns().add(columnB);

        TableColumn<String, paymentDetail> columnC = new TableColumn<>("Product price");
        columnC.setCellValueFactory(new PropertyValueFactory<>("product_price"));
        columnC.setMinWidth(130);
        tableView.getColumns().add(columnC);

        TableColumn<String, paymentDetail> columnD = new TableColumn<>("Product amount");
        columnD.setCellValueFactory(new PropertyValueFactory<>("product_amount"));
        columnD.setMinWidth(150);
        tableView.getColumns().add(columnD);

    }

    public void sentProof(){
        try {
            String sql = "UPDATE `payment`SET `last_proof` = ? where project_id = ? ";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, hidden.getText());
            preparedStatement.setString(2,labelID.getText());
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void sentStatus(){
        try {

            String sql2 = "UPDATE `payment`SET `last_status` = ? where project_id = ?" ;
            preparedStatement2 = con.prepareStatement(sql2);
            preparedStatement2.setString(1,"WaitConfirm");
            preparedStatement2.setString(2,labelID.getText());
            preparedStatement2.executeUpdate();

            Stage stage = (Stage) confirm.getScene().getWindow();
            stage.close();



        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void start() {
        Stage stage = new Stage();
        stage.setTitle("File Chooser");
        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    hidden.setText(file.toURI().toString());
                    confirm.setDisable(false);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeight(100);
                    alert.setWidth(200);
                    alert.setTitle("Validate Picture");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Type");
                    alert.showAndWait();
                }
            }
        });
    }

    public void getID(String username){

        labelID.setText(username);
        try {
            String sql1 = "Select payment_id from payment where project_id = ?";
            String sql = "Select first_cost from payment where payment_id = ?";
            String sql2 = "Select last_cost from payment where payment_id = ?" ;
            preparedStatement = con.prepareStatement(sql1);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                preparedStatement2 = con.prepareStatement(sql);
                preparedStatement2.setString(1,resultSet.getString(1));
                resultSet2 = preparedStatement2.executeQuery();

                preparedStatement3 = con.prepareStatement(sql2);
                preparedStatement3.setString(1,resultSet.getString(1));
                resultSet3 = preparedStatement3.executeQuery();
                if(resultSet2.next() && resultSet3.next()){
                    showPrize.setText(String.valueOf(resultSet3.getDouble(1)));
                    showFullPrize.setText(String.valueOf((resultSet2.getDouble(1)) + (resultSet3.getDouble(1))));
                }
            }
        }catch (SQLException ex){
            ex.fillInStackTrace();
        }

        getIDs();
        getDetail1();
        getDetail2();
        getDetail3();
        loopDetail();
    }


    public void getIDs(){
        String sql = "SELECT `product_id` FROM `project_detail` WHERE project_id = ? AND project_status = \"Complete\" ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,labelID.getText());
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                arrayCountID.add(resultSet.getString(1));
            }

        }catch (SQLException e){
            e.fillInStackTrace();
        }
    }

    public void getDetail1(){
        String sql = "SELECT `product_name` FROM `project_detail` WHERE project_id = ? AND project_status = \"Complete\" ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,labelID.getText());
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                arrayCount1.add(resultSet.getString(1));
            }

        }catch (SQLException e){
            e.fillInStackTrace();
        }
    }
    public void getDetail2(){
        String sql = "SELECT `product_price` FROM `project_detail` Where project_id = ? AND project_status = \"Complete\"";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,labelID.getText());
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                arrayCount2.add(resultSet.getString(1));
            }

        }catch (SQLException e){
            e.fillInStackTrace();
        }
    }
    public void getDetail3(){
        String sql = "SELECT `product_amount` FROM `project_detail` Where project_id = ? AND project_status = \"Complete\"";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,labelID.getText());
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                arrayCount3.add(resultSet.getString(1));
            }

        }catch (SQLException e){
            e.fillInStackTrace();
        }
    }
    public void loopDetail(){
        for(int i = 0 ; i <arrayCount1.size();i++){
            tableView.getItems().add(new paymentDetail(arrayCountID.get(i),arrayCount1.get(i) , arrayCount2.get(i) , arrayCount3.get(i)));
        }
    }

}
