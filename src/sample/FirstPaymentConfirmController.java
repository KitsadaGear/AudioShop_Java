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

public class FirstPaymentConfirmController {

    Connection con ;
    PreparedStatement preparedStatement , preparedStatement2 , preparedStatement5 , preparedStatement6 , preparedStatement7 , preparedStatement4;
    ResultSet resultSet , resultSet2 ,resultSet5 ,resultSet6 ,resultSet7;

    public FirstPaymentConfirmController() {
        con = ConnectDatabase.ConnectDB();
    }

    double sum ;
    @FXML
    Button confirm , select , wrong , back ;
    @FXML
    TextField textField ,textField2;
    @FXML
    ImageView imageView ;
    @FXML
    TableView tableIDs;

    @FXML
            Label labelShow , textField1 ;
    ArrayList<String> idArray = new ArrayList<>();

    public void initialize(){
        labelShow.setVisible(false);
        textField1.setVisible(false);
        textField2.setVisible(false);
        TableColumn<String, detailID> column1 = new TableColumn<>("Payment ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column1.setMinWidth(200);
        tableIDs.getColumns().add(column1);
        getsID();
        loopID();

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
                String sql = "SELECT `First_proof`FROM `payment` WHERE project_id = ? " ;
                try {
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1,textField.getText());
                    resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        textField2.setText(textField.getText());
                        System.err.println(textField2.getText());
                        Image image = new Image(resultSet.getString(1));
                        imageView.setImage(image);
                        textField1.setText(textField.getText());
                        textField.setText("");
                        textField1.setVisible(true);
                        labelShow.setVisible(true);
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
                if (result.get() == ButtonType.OK) {
                    try {
                        String sql = "Update payment set first_status = ? where project_id = ? ";
                        preparedStatement = con.prepareStatement(sql);
                        preparedStatement.setString(1,"Payment Confirm");
                        preparedStatement.setString(2 , textField1.getText());
                        preparedStatement.executeUpdate();
                        System.err.println("Confirm");

                        String sql2 = "Update project set project_status =  ? where project_id = ? " ;
                        preparedStatement2 = con.prepareStatement(sql2);
                        preparedStatement2.setString(1, "Working");
                        preparedStatement2.setString(2,textField1.getText());
                        preparedStatement2.executeUpdate();

                        String sql3 = "Update project_detail set project_status =  ? where project_id = ? " ;
                        preparedStatement5 = con.prepareStatement(sql3);
                        preparedStatement5.setString(1, "Working");
                        preparedStatement5.setString(2,textField1.getText());
                        preparedStatement5.executeUpdate();


                        textField1.setText("");
                        textField1.setVisible(false);
                        labelShow.setText("");
                        labelShow.setVisible(false);
                        imageView.setImage(null);
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
                alert.showAndWait();

                }


        });
    }

    public void getsID() {
        String sql = "SELECT `project_id` FROM `project` where project_status = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,"ConfirmOrder & WaitPayment");
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
