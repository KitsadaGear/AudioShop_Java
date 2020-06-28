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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;


public class StaffController {
    @FXML
    public Label label1, label2, label3;
    @FXML
    public Button btn1, confirm, Complete, reset, deposit, full;
    @FXML
    TextField textField1, tf;

    @FXML
    Image image;
    @FXML
    TableView tableIDs, tableView, ta;
    @FXML
    ImageView imageView;
    Connection con;
    PreparedStatement preparedStatement, preparedStatement2, preparedStatement3, preparedStatement4, preparedStatement5, preparedStatement6 , preparedStatement7;
    ResultSet resultSet, resultSet2, resultSet3, resultSet4, resultSet5, resultSet6 , resultSet7;


    public StaffController() {

        con = ConnectDatabase.ConnectDB();
    }

    public void setFromLogin(String username) {
        String user = username;
        String sql = "Select emp_id From employee Where Username = ? ";
        String sql2 = "Select FirstName From employee Where Username = ? ";
        String sql3 = "Select Lastname From employee Where Username = ? ";
        String sql4 = "Select emp_picture From employee Where Username = ? ";
        String sql5 = "Select emp_type from employee Where Username = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement2 = con.prepareStatement(sql2);
            preparedStatement3 = con.prepareStatement(sql3);
            preparedStatement4 = con.prepareStatement(sql4);
            preparedStatement5 = con.prepareStatement(sql5);
            preparedStatement.setString(1, user);
            preparedStatement2.setString(1, user);
            preparedStatement3.setString(1, user);
            preparedStatement4.setString(1, user);
            preparedStatement5.setString(1, user);
            resultSet = preparedStatement.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            resultSet3 = preparedStatement3.executeQuery();
            resultSet4 = preparedStatement4.executeQuery();
            resultSet5 = preparedStatement5.executeQuery();
            if (resultSet.next()) {
                label1.setText(resultSet.getString(1));
            }
            if (resultSet2.next()) {
                label2.setText(resultSet2.getString(1));
            }
            if (resultSet3.next()) {
                label3.setText(resultSet3.getString(1));
            }
            if (resultSet4.next()) {
                image = new Image(resultSet4.getString(1));
                imageView.setDisable(false);
                imageView.setImage(image);
            }
            if (resultSet5.next()) {
                if (resultSet5.getString(1).equals("E")) {
                    btn1.setDisable(true);
                    reset.setDisable(true);
                    tableIDs.setDisable(true);
                    confirm.setDisable(true);
                    tableView.setDisable(true);
                    deposit.setDisable(true);
                    full.setDisable(true);
                    textField1.setDisable(true);
                    TableColumn<String, detailID> column1 = new TableColumn<>("Project ID");
                    column1.setCellValueFactory(new PropertyValueFactory<>("id"));
                    column1.setMinWidth(200);
                    ta.getColumns().add(column1);
                    getIDConfirm();
                    loopIDConfirm();
                    Complete.setDisable(false);
                    System.err.println("Engineer");
                }
                else if (resultSet5.getString(1).equals("S")) {
                    tf.setDisable(true);
                    Complete.setDisable(true);
                    ta.setDisable(true);
                    deposit.setDisable(false);
                    full.setDisable(false);
                    if (tableIDs.getColumns().isEmpty()) {
                        btn1.setDisable(true);
                        reset.setDisable(true);
                        confirm.setDisable(true);
                        textField1.setDisable(true);
                        tf.setDisable(true);
                        Complete.setDisable(true);
                        ta.setDisable(true);
                    } else {
                        btn1.setDisable(false);
                        reset.setDisable(false);
                        confirm.setDisable(false);
                        textField1.setDisable(false);
                        tf.setDisable(true);
                        Complete.setDisable(true);
                        ta.setDisable(true);
                    }
                    System.err.println("Sales");
                }

            }
        } catch (SQLException r){
            r.printStackTrace();
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
        if (result.get() == ButtonType.OK) {
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
        } else {
            ;
        }
    }

    @FXML
    public void initialize() {
        TableColumn<String, detailID> column1 = new TableColumn<>("Project ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column1.setMinWidth(200);
        tableIDs.getColumns().add(column1);
        getsID();
        loopID();

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i = 0 ; i < idArray.size() ; i++){
                    if (textField1.getText().equals(idArray.get(i))){
                        getDetail1();
                        getDetail2();
                        getDetail3();
                        loopDetail();
                        btn1.setDisable(true);
                        confirm.setDisable(false);
                        break;
                    }else{
                        if(i == (idArray.size()-1)) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("");
                            alert.setContentText("Not Found ID");
                            alert.setHeaderText("");
                            alert.showAndWait();
                            textField1.clear();
                        }
                    }
                }
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tableView.getItems().clear();
                textField1.clear();
                btn1.setDisable(false);
            }
        });

        deposit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FirstPaymentConfirm.fxml"));
                    Parent root = (Parent) loader.load();
                    FirstPaymentConfirmController firstPaymentConfirmController = loader.getController();
                    stage.setScene(new Scene(root, 721, 625));
                    stage.show();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });
        full.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FullPaymentConfirm.fxml"));
                    Parent root = (Parent) loader.load();
                    stage.setScene(new Scene(root, 721, 625));
                    stage.show();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }

        });


        TableColumn<String, detailProduct> columnA = new TableColumn<>("Product name");
        columnA.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        columnA.setMinWidth(150);
        tableView.getColumns().add(columnA);

        TableColumn<Integer, detailProduct> columnB = new TableColumn<>("Product price");
        columnB.setCellValueFactory(new PropertyValueFactory<>("product_price"));
        columnB.setMinWidth(130);
        tableView.getColumns().add(columnB);

        TableColumn<Integer, detailProduct> columnC = new TableColumn<>("Product amount");
        columnC.setCellValueFactory(new PropertyValueFactory<>("product_amount"));
        columnC.setMinWidth(130);
        tableView.getColumns().add(columnC);

        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Double sum = 0.0 ;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("");
                alert.setContentText("Please Check");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    String sql = "UPDATE `project_detail` SET `project_status`= ? WHERE project_id = ? ";
                    String sql2 = "INSERT INTO `project_assign`(`emp_id`, `Project_id`, `emp_type`) VALUES (?,?,?)";
                    String sql3 = "UPDATE `project` SET `project_status`= ? WHERE project_id = ? ";

                    try {
                        preparedStatement = con.prepareStatement(sql);
                        preparedStatement.setString(1, "ConfirmOrder");
                        preparedStatement.setString(2, textField1.getText());
                        preparedStatement.executeUpdate();

                        preparedStatement2 = con.prepareStatement(sql2);
                        preparedStatement2.setString(1, label1.getText());
                        preparedStatement2.setString(2, textField1.getText());
                        preparedStatement2.setString(3, "S");
                        preparedStatement2.executeUpdate();

                        preparedStatement3 = con.prepareStatement(sql3);
                        preparedStatement3.setString(1, "ConfirmOrder");
                        preparedStatement3.setString(2, textField1.getText());
                        preparedStatement3.executeUpdate();

                                 idArray.remove(textField1.getText());
                                System.err.println("Complete");
                                textField1.setText("");
                                tableView.getItems().clear();
                                btn1.setDisable(false);

                    } catch (SQLException e) {
                        e.fillInStackTrace();
                    }
                }
            }
        });

        Complete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sql = "UPDATE `project` SET `project_Status`= ? WHERE project_id = ? " ;
                String sql2 = "Update project_detail SET project_status = ? WHERE project_id = ?";
                try {
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1,"Complete");
                    preparedStatement.setString(2,tf.getText());
                    preparedStatement.executeUpdate();

                    preparedStatement2 = con.prepareStatement(sql2);
                    preparedStatement2.setString(1,"Complete");
                    preparedStatement2.setString(2,tf.getText());
                    preparedStatement2.executeUpdate();
                    ta.getItems().remove(tf.getText());
                    tf.setText("");
                    Complete.setDisable(false);
                }catch (SQLException e){
                    e.fillInStackTrace();
                }
            }
        });
    }

   ArrayList<String> idArray = new ArrayList<>();
    ArrayList<String> arrayCount1 = new ArrayList<>();
    ArrayList<String> arrayCount2 = new ArrayList<>();
    ArrayList<String> arrayCount3 = new ArrayList<>();
    ArrayList<String> getIDs = new ArrayList<>();


    public void getsID() {
        String sql = "SELECT `project_id` FROM `project_detail` where project_status = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,"Assign");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                        idArray.add(resultSet.getString(1));
                }

        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }



   public void getDetail1(){
        String sql = "SELECT `product_name` FROM `project_detail` WHERE project_id = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,textField1.getText());
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                arrayCount1.add(resultSet.getString(1));
            }

        }catch (SQLException e){
            e.fillInStackTrace();
        }
   }
    public void getDetail2(){
        String sql = "SELECT `product_price` FROM `project_detail` Where project_id = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,textField1.getText());
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                arrayCount2.add(resultSet.getString(1));
            }

        }catch (SQLException e){
            e.fillInStackTrace();
        }
    }
    public void getDetail3(){
        String sql = "SELECT `product_amount` FROM `project_detail` Where project_id = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,textField1.getText());
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
            tableView.getItems().add(new detailProduct(arrayCount1.get(i) , arrayCount2.get(i) , arrayCount3.get(i)));
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

    public void getIDConfirm() {
        String sql = "SELECT `project_id` FROM `project_detail` where project_status = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,"Working");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                getIDs.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }
    public void loopIDConfirm() {
        for (int i = 0; i < getIDs.size() ; i ++) {
            Set<String> s = new LinkedHashSet<String>(getIDs);
            getIDs.clear();
            getIDs.addAll(s);
            ta.getItems().add(new detailID(getIDs.get(i)));
        }
    }

}



