package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class FirstPaymentController {

    Connection con;
    PreparedStatement preparedStatement, preparedStatement2 , preparedStatement3 , preparedStatement4,preparedStatement5 , preparedStatement6 , preparedStatement7;
    ResultSet resultSet, resultSet2 , resultSet3 , resultSet5 ,resultSet6 , resultSet7 , resultSet4;

    public FirstPaymentController() {
        con = ConnectDatabase.ConnectDB();
    }
    Storage storage = new Storage() ;
    public double sum ;
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

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("CONFIRMATION");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to close ? ");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                } else {
                    ;
                }
            }
        });

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
                        try {

                                String sql5 = "Select payment_id from project where project_id = ? ";
                                preparedStatement5 = con.prepareStatement(sql5);
                                preparedStatement5.setString(1,labelID.getText());
                                resultSet5 = preparedStatement5.executeQuery();
                                if(resultSet5.next()) {
                                    String sqlPayment = "INSERT INTO `payment`(`project_id`, `payment_id`, `First_cost`, `Last_cost`, `First_proof`, `Last_proof`, `First_status`, `Last_status`) " +
                                            "VALUES (?,?,?,?,?,?,?,?)";

                                    preparedStatement4 = con.prepareStatement(sqlPayment);
                                    preparedStatement4.setString(1, labelID.getText());
                                    preparedStatement4.setString(2, resultSet5.getString(1)); // Payment_id
                                    preparedStatement4.setString(3, String.valueOf(0.2 * sum));
                                    preparedStatement4.setString(4, String.valueOf(sum - (0.2 * sum)));
                                    preparedStatement4.setString(5, hidden.getText());
                                    preparedStatement4.setString(6, "NULL");
                                    preparedStatement4.setString(7, "WaitConfirm");
                                    preparedStatement4.setString(8, "Not Payment");
                                    preparedStatement4.executeUpdate();
                                    System.err.println("Update 1");
                                    String sql = "Update project_detail set project_status = ? where project_id = ? " ;
                                    preparedStatement = con.prepareStatement(sql);
                                    preparedStatement.setString(1,"ConfirmOrder & WaitPayment");
                                    preparedStatement.setString(2,labelID.getText());
                                    preparedStatement.executeUpdate();
                                    System.err.println("Update 2");
                                    String sql2 = "Update project set project_status = ? where project_id = ? " ;
                                    preparedStatement2 = con.prepareStatement(sql2);
                                    preparedStatement2.setString(1,"ConfirmOrder & WaitPayment");
                                    preparedStatement2.setString(2,labelID.getText());
                                    preparedStatement2.executeUpdate();
                                    System.err.println("Update 3");

                                    Stage stage = (Stage) confirm.getScene().getWindow();
                                    stage.close();
                                }
                            }
                         catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } else if (result.get() == ButtonType.CANCEL) {
                        hidden.clear();
                    }
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
        getIDs();
        getDetail1();
        getDetail2();
        getDetail3();
        loopDetail();
        System.err.println("First");
        setPrice();

    }


    public void getIDs(){
        String sql = "SELECT `product_id` FROM `project_detail` WHERE project_id = ? AND  project_status = \"ConfirmOrder\" OR project_status = \"ConfirmOrder & WaitPayment\"";
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
        String sql = "SELECT `product_name` FROM `project_detail` WHERE project_id = ? AND  project_status = \"ConfirmOrder\" OR project_status = \"ConfirmOrder & WaitPayment\"";
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
        String sql = "SELECT `product_price` FROM `project_detail` Where project_id = ? AND  project_status = \"ConfirmOrder\" OR project_status = \"ConfirmOrder & WaitPayment\"";
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
        String sql = "SELECT `product_amount` FROM `project_detail` Where project_id = ? AND  project_status = \"ConfirmOrder\" OR project_status = \"ConfirmOrder & WaitPayment\"";
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


    public void setPrice(){
        try {
            String sql = "Select product_price from project_detail where project_id = ? " ;
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,labelID.getText());
            resultSet = preparedStatement.executeQuery();

            String sql2 = "Select product_amount from project_detail where project_id = ? ";
            preparedStatement2 = con.prepareStatement(sql2);
            preparedStatement2.setString(1,labelID.getText());
            resultSet2 = preparedStatement2.executeQuery();

            while(resultSet.next() && resultSet2.next()){
                storage.price.add(resultSet.getDouble(1));
                storage.amount.add(resultSet2.getInt(1));
            }

            for(int i = 0 ; i < storage.price.size() ; i++){
                sum += (storage.price.get(i) * storage.amount.get(i));
            }

            showFullPrize.setText(String.valueOf(sum));
            showPrize.setText(String.valueOf(0.2 * sum));


        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}





