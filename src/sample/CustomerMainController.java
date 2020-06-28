package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;


public class CustomerMainController {
    @FXML
    public Label project_id , project_idshow , time, name1, price1, name2, price2,label1, label2, label3, label4, total, labelName, labelSurname, labelEmail, labelPhone, projectStatus , labelID;
    @FXML
    public Button DepositReport, DepositPayment, FullpaymentReport, Fullpayment, WorkingStatus, PaymentStatus, staff1, staff2, staff3, staff4, select1, select2, select3, select4, select5, select6, logout, add1, del1, add2, del2, confirm1, confirm2,   show, undo, assign;
    @FXML
    public Image image;
    @FXML
    public ImageView imageView1, imageView, imageViewProfile ;
    @FXML
    public File file;
    @FXML
    public TextArea amount1, amount2,showSelected;

    public double prices1 = 0, prices2 = 0, prices3 = 0, prices4 = 0, prices5 = 0,prices7 = 0, prices8 = 0, prices9 = 0, prices10 = 0;

    public int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0 ,count7 = 0, count8 = 0, count9 = 0, count10 = 0;

    public double sum;
    Connection con;
    PreparedStatement preparedStatement, preparedStatement2, preparedStatement3, preparedStatement4, preparedStatement5 , preparedStatement6 , preparedStatement7;
    ResultSet resultSet, resultSet2, resultSet3, resultSet4, resultSet5 , resultSet6 ,resultSet7;
    Storage storage = new Storage();

    public CustomerMainController() {

        con = ConnectDatabase.ConnectDB();

    }

    public int random() {
        Random random = new Random();
        return random.nextInt(88888888) + 11111111;
    }

    Integer id = random();
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    public void initialize() {

        project_id.setVisible(false);
        project_idshow.setVisible(false);
        showSelected.setEditable(false);


        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);


        name1.setVisible(false);
        name2.setVisible(false);


        price1.setVisible(false);
        price2.setVisible(false);


        add1.setVisible(false);
        add2.setVisible(false);


        del1.setVisible(false);
        del2.setVisible(false);

        amount1.setVisible(false);
        amount2.setVisible(false);

        confirm1.setVisible(false);
        confirm2.setVisible(false);

        amount1.setText(String.valueOf(0));
        amount2.setText(String.valueOf(0));
        projectStatus.setTextFill(Color.RED);


        add1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (projectStatus.getText().equals("Not Assign")) {
                    int a = Integer.parseInt(amount1.getText());
                    int b = a ;
                    if (name1.getText().contains("Shure GLXD24RA/SM58-Z2")) {
                        if (count1 == 50){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Warning");
                            alert.setContentText("Invalid amount ( >50 )");
                            alert.showAndWait();
                            amount1.setText(String.valueOf(b));
                        }else{
                            count1 += 1;
                            b = a + 1;
                            amount1.setText(String.valueOf(b));
                        }

                    } else if (name1.getText().contains("YAMAHA EMX5")) {
                        if (count3 == 50){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Warning");
                            alert.setContentText("Invalid amount ( >50 )");
                            alert.showAndWait();
                            amount1.setText(String.valueOf(b));
                        }else{
                            count3 += 1;
                            b = a + 1;
                            amount1.setText(String.valueOf(b));
                        }

                    } else if (name1.getText().contains("TOA A-1724")) {
                        if (count4 == 50){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Warning");
                            alert.setContentText("Invalid amount ( >50 )");
                            alert.showAndWait();
                            amount1.setText(String.valueOf(b));
                        }else{
                            count4 += 1;
                            b = a + 1;
                            amount1.setText(String.valueOf(b));
                        }
                    } else if (name1.getText().contains("Numark MP103USB")) {
                        if (count7 == 50){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Warning");
                            alert.setContentText("Invalid amount ( >50 )");
                            alert.showAndWait();
                            amount1.setText(String.valueOf(b));
                        }else{
                            count7 += 1;
                            b = b + 1 ;
                            amount1.setText(String.valueOf(b));
                        }
                    } else if (name1.getText().contains("Canare L-2T2S")) {
                        if (count8 == 50){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Warning");
                            alert.setContentText("Invalid amount ( >50 )");
                            alert.showAndWait();
                            amount1.setText(String.valueOf(b));
                        }else{
                            count8 += 1;
                            b = b + 1 ;
                            amount1.setText(String.valueOf(b));
                        }
                    } else if (name1.getText().contains("K&M21070")) {
                        if (count10 == 50){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Warning");
                            alert.setContentText("Invalid amount ( >50 )");
                            alert.showAndWait();
                            amount1.setText(String.valueOf(b));
                        }else{
                            b = b + 1 ;
                            amount1.setText(String.valueOf(b));
                        }
                    }
                }
            }
        });
        add2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (projectStatus.getText().equals("Not Assign")) {
                    int a = Integer.parseInt(amount2.getText());
                    int b = a ;
                    if (name2.getText().contains("SHURE SM58-LC")) {
                        if (count2 == 50){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Warning");
                            alert.setContentText("Invalid amount ( >50 )");
                            alert.showAndWait();
                            amount2.setText(String.valueOf(b));
                        }else{
                            count2 += 1;
                            b = b + 1 ;
                            amount2.setText(String.valueOf(b));
                        }
                    } else if (name2.getText().contains("YAMAHA VXS8")) {
                        if (count5 == 50){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Warning");
                            alert.setContentText("Invalid amount ( >50 )");
                            alert.showAndWait();
                            amount2.setText(String.valueOf(b));
                        }else{
                            count5 += 1;
                            b = b + 1 ;
                            amount2.setText(String.valueOf(b));
                        }
                    } else if (name2.getText().contains("Belden 8473 14AWG-305M")) {
                        if (count9 == 50){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Warning");
                            alert.setContentText("Invalid amount ( >50 )");
                            alert.showAndWait();
                            amount2.setText(String.valueOf(b));
                        }else{
                            count9 += 1;
                            b = b + 1 ;
                            amount2.setText(String.valueOf(b));
                        }
                    }
                }
            }
        });

        del1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (projectStatus.getText().equals("Not Assign")) {
                    int a = Integer.parseInt(amount1.getText());
                    if (a > 0) {
                        if (name1.getText().contains("Shure GLXD24RA/SM58-Z2")) {
                            count1 -= 1;
                        } else if (name1.getText().contains("YAMAHA EMX5")) {
                            count3 -= 1;

                        } else if (name1.getText().contains("TOA A-1724")) {
                            count4 -= 1;
                        } else if (name1.getText().contains("Numark MP103USB")) {
                            count7 -= 1;
                        } else if (name1.getText().contains("Canare L-2T2S")) {
                            count8 -= 1;
                        } else if (name1.getText().contains("K&M21070")) {
                            count10 -= 1;
                        }
                        int b = a - 1;
                        amount1.setText(String.valueOf(b));
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid Amount (<0)");
                        alert.showAndWait();
                    }
                }
            }
        });
        del2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (projectStatus.getText().equals("Not Assign")) {
                    int a = Integer.parseInt(amount2.getText());
                    if (a > 0) {
                        if (name2.getText().contains("SHURE SM58-LC")) {
                            count2 -= 1;
                        } else if (name2.getText().contains("YAMAHA VXS8")) {
                            count5 -= 1;
                        } else if (name2.getText().contains("Belden 8473 14AWG-305M")) {
                            count9 -= 1;
                        }
                        int b = a - 1;
                        amount2.setText(String.valueOf(b));
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid Amount (<0)");
                        alert.showAndWait();
                    }
                }
            }
        });
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeight(100);
                alert.setWidth(200);
                alert.setTitle("CONFIRMATION");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure ");
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
        });
        select1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    try {
                        Image imageMC1 = new Image(String.valueOf(new URI("file:/C:/Users/Administrator/Desktop/Computer%20Science/SA/project/src/image/1426558167.jpg")));
                        imageView.setImage(imageMC1);
                        Image imageMC2 = new Image(String.valueOf(new URI("file:/C:/Users/Administrator/Desktop/Computer%20Science/SA/project/src/image/SHURE_SM58.jpg")));
                        imageView1.setImage(imageMC2);
                        imageView.setVisible(true);
                        imageView1.setVisible(true);
                        String sql = "SELECT product_name FROM `product_storage` WHERE `product_catalog` = \"Microphone\" ";
                        String sql2 = "SELECT product_id FROM `product_storage` WHERE `product_catalog` = \"Microphone\" ";
                        preparedStatement = con.prepareStatement(sql);
                        resultSet = preparedStatement.executeQuery();
                        preparedStatement2 = con.prepareStatement(sql2);
                        resultSet2 = preparedStatement2.executeQuery();
                        while (resultSet.next() && resultSet2.next()) {
                            label1.setVisible(true);
                            label2.setVisible(true);
                            label3.setVisible(true);
                            label4.setVisible(true);
                            name1.setVisible(true);
                            name2.setVisible(true);
                            price1.setVisible(true);
                            price2.setVisible(true);
                            add1.setVisible(true);
                            add2.setVisible(true);
                            del1.setVisible(true);
                            del2.setVisible(true);
                            amount1.setVisible(true);
                            amount2.setVisible(true);
                            amount1.setText(String.valueOf(0));
                            amount2.setText(String.valueOf(0));
                            confirm1.setVisible(true);
                            confirm2.setVisible(true);
                            storage.productMC.add(resultSet.getString(1));
                            storage.IdMC.add(resultSet2.getString(1));
                        }
                        name1.setText(storage.IdMC.get(0) + "." + " " + storage.productMC.get(0));
                        name2.setText(storage.IdMC.get(1) + "." + " " + storage.productMC.get(1));
                        try {
                            String sql1 = "SELECT product_price FROM `product_storage` WHERE `product_catalog` = \"Microphone\" ";
                            preparedStatement = con.prepareStatement(sql1);
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                storage.priceMC.add(resultSet.getString(1));
                            }
                            price1.setText(storage.priceMC.get(0));
                            price2.setText(storage.priceMC.get(1));
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

            }
        });
        select2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Image imagePX = new Image(String.valueOf(new URI("file:/C:/Users/Administrator/Desktop/Computer%20Science/SA/project/src/image/EMX5-large.jpg")));
                    imageView.setImage(imagePX);
                    imageView.setVisible(true);
                    imageView1.setVisible(false);
                    String sql = "SELECT product_name FROM `product_storage` WHERE `product_catalog` = \"Power Mixer\" ";
                    String sql2 = "SELECT product_id FROM `product_storage` WHERE `product_catalog` = \"Power Mixer\" ";
                    preparedStatement = con.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                    preparedStatement2 = con.prepareStatement(sql2);
                    resultSet2 = preparedStatement2.executeQuery();
                    while (resultSet.next()&& resultSet2.next()) {
                        label1.setVisible(true);
                        label2.setVisible(true);
                        label3.setVisible(false);
                        label4.setVisible(false);
                        name1.setVisible(true);
                        name2.setVisible(false);
                        price1.setVisible(true);
                        price2.setVisible(false);
                        add1.setVisible(true);
                        add2.setVisible(false);
                        del1.setVisible(true);
                        del2.setVisible(false);
                        amount1.setVisible(true);
                        amount2.setVisible(false);
                        amount1.setText(String.valueOf(0));
                        amount2.setText(String.valueOf(0));
                        confirm1.setVisible(true);
                        confirm2.setVisible(false);
                        storage.productPM.add(resultSet.getString(1));
                        storage.IdPM.add(resultSet2.getString(1));
                    }
                    name1.setText(storage.IdPM.get(0) + "." + " " +storage.productPM.get(0));
                    try {
                        String sql1 = "SELECT product_price FROM `product_storage` WHERE `product_catalog` = \"Power Mixer\" ";
                        preparedStatement = con.prepareStatement(sql1);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            storage.pricePM.add(resultSet.getString(1));
                        }
                        price1.setText(storage.pricePM.get(0));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        select3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Image imageSC1 = new Image(String.valueOf(new URI("file:/C:/Users/Administrator/Desktop/Computer%20Science/SA/project/src/image/TOA_A-1724.jpg")));
                    imageView.setImage(imageSC1);
                    Image imageSC2 = new Image(String.valueOf(new URI("file:/C:/Users/Administrator/Desktop/Computer%20Science/SA/project/src/image/YAMAHA_VXS8.jpg")));
                    imageView1.setImage(imageSC2);
                    imageView.setVisible(true);
                    imageView1.setVisible(true);
                    String sql = "SELECT product_name FROM `product_storage` WHERE `product_catalog` = \"Speaker cabinet\" ";
                    String sql2 = "SELECT product_id FROM `product_storage` WHERE `product_catalog` = \"Speaker cabinet\" ";
                    preparedStatement = con.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                    preparedStatement2 = con.prepareStatement(sql2);
                    resultSet2 = preparedStatement2.executeQuery();
                    while (resultSet.next()&& resultSet2.next()) {
                        label1.setVisible(true);
                        label2.setVisible(true);
                        label3.setVisible(true);
                        label4.setVisible(true);
                        name1.setVisible(true);
                        name2.setVisible(true);
                        price1.setVisible(true);
                        price2.setVisible(true);
                        add1.setVisible(true);
                        add2.setVisible(true);
                        del1.setVisible(true);
                        del2.setVisible(true);
                        amount1.setVisible(true);
                        amount2.setVisible(true);
                        amount1.setText(String.valueOf(0));
                        amount2.setText(String.valueOf(0));
                        confirm1.setVisible(true);
                        confirm2.setVisible(true);
                        storage.productSC.add(resultSet.getString(1));
                        storage.IdSC.add(resultSet2.getString(1));
                    }
                    name1.setText(storage.IdSC.get(0) + "." + " " +storage.productSC.get(0));
                    name2.setText(storage.IdSC.get(1) + "." + " " +storage.productSC.get(1));
                    try {
                        String sql1 = "SELECT product_price FROM `product_storage` WHERE `product_catalog` = \"Speaker cabinet\" ";
                        preparedStatement = con.prepareStatement(sql1);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            storage.priceSC.add(resultSet.getString(1));
                        }
                        price1.setText(storage.priceSC.get(0));
                        price2.setText(storage.priceSC.get(1));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        select4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Image imageDVD = new Image(String.valueOf(new URI("file:/C:/Users/Administrator/Desktop/Computer%20Science/SA/project/src/image/Numark_MP103USB.jpg")));
                    imageView.setImage(imageDVD);
                    imageView.setVisible(true);
                    imageView1.setVisible(false);
                    String sql = "SELECT product_name FROM `product_storage` WHERE `product_catalog` = \"DVD Tuner\" ";
                    String sql2 = "SELECT product_id FROM `product_storage` WHERE `product_catalog` = \"DVD Tuner\" ";
                    preparedStatement = con.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                    preparedStatement2 = con.prepareStatement(sql2);
                    resultSet2 = preparedStatement2.executeQuery();
                    while (resultSet.next()&& resultSet2.next()) {
                        label1.setVisible(true);
                        label2.setVisible(true);
                        label3.setVisible(false);
                        label4.setVisible(false);
                        name1.setVisible(true);
                        name2.setVisible(false);
                        price1.setVisible(true);
                        price2.setVisible(false);
                        add1.setVisible(true);
                        add2.setVisible(false);
                        del1.setVisible(true);
                        del2.setVisible(false);
                        amount1.setVisible(true);
                        amount2.setVisible(false);
                        confirm1.setVisible(true);
                        confirm2.setVisible(false);
                        amount1.setText(String.valueOf(0));
                        amount2.setText(String.valueOf(0));
                        storage.productDVD.add(resultSet.getString(1));
                        storage.IdDVD.add(resultSet2.getString(1));
                    }
                    name1.setText(storage.IdDVD.get(0) + "." + " " +storage.productDVD.get(0));
                    try {
                        String sql1 = "SELECT product_price FROM `product_storage` WHERE `product_catalog` = \"DVD Tuner\" ";
                        preparedStatement = con.prepareStatement(sql1);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            storage.priceDVD.add(resultSet.getString(1));
                        }
                        price1.setText(storage.priceDVD.get(0));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        select5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Image imageCC = new Image(String.valueOf(new URI("file:/C:/Users/Administrator/Desktop/Computer%20Science/SA/project/src/image/Canare_L-2T2S.jpg")));
                    imageView.setImage(imageCC);
                    Image imageCC2 = new Image(String.valueOf(new URI("file:/C:/Users/Administrator/Desktop/Computer%20Science/SA/project/src/image/Belden_8473_14AWG-305M.jpg")));
                    imageView1.setImage(imageCC2);
                    imageView.setVisible(true);
                    imageView1.setVisible(true);
                    String sql = "SELECT product_name FROM `product_storage` WHERE `product_catalog` = \"Cable Connector\" ";
                    String sql2 = "SELECT product_id FROM `product_storage` WHERE `product_catalog` = \"Cable Connector\" ";
                    preparedStatement = con.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                    preparedStatement2 = con.prepareStatement(sql2);
                    resultSet2 = preparedStatement2.executeQuery();
                    while (resultSet.next()&& resultSet2.next()) {
                        label1.setVisible(true);
                        label2.setVisible(true);
                        label3.setVisible(true);
                        label4.setVisible(true);
                        name1.setVisible(true);
                        name2.setVisible(true);
                        price1.setVisible(true);
                        price2.setVisible(true);
                        add1.setVisible(true);
                        add2.setVisible(true);
                        del1.setVisible(true);
                        del2.setVisible(true);
                        amount1.setVisible(true);
                        amount2.setVisible(true);
                        confirm1.setVisible(true);
                        confirm2.setVisible(true);
                        amount1.setText(String.valueOf(0));
                        amount2.setText(String.valueOf(0));
                        storage.productCC.add(resultSet.getString(1));
                        storage.IdCC.add(resultSet2.getString(1));
                    }
                    name1.setText(storage.IdCC.get(0) + "." + " " +storage.productCC.get(0));
                    name2.setText(storage.IdCC.get(1) + "." + " " +storage.productCC.get(1));
                    try {
                        String sql1 = "SELECT product_price FROM `product_storage` WHERE `product_catalog` = \"Cable Connector\" ";
                        preparedStatement = con.prepareStatement(sql1);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            storage.priceCC.add(resultSet.getString(1));
                        }
                        price1.setText(storage.priceCC.get(0));
                        price2.setText(storage.priceCC.get(1));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        select6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Image imageO = new Image(String.valueOf(new URI("file:/C:/Users/Administrator/Desktop/Computer%20Science/SA/project/src/image/K&M21070.png")));
                    imageView.setImage(imageO);
                    imageView.setVisible(true);
                    imageView1.setVisible(false);
                    String sql = "SELECT product_name FROM `product_storage` WHERE `product_catalog` = \"Other\" ";
                    String sql2 = "SELECT product_id FROM `product_storage` WHERE `product_catalog` = \"Other\" ";
                    preparedStatement = con.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                    preparedStatement2 = con.prepareStatement(sql2);
                    resultSet2 = preparedStatement2.executeQuery();
                    while (resultSet.next()&& resultSet2.next()) {
                        label1.setVisible(true);
                        label2.setVisible(true);
                        label3.setVisible(false);
                        label4.setVisible(false);
                        name1.setVisible(true);
                        name2.setVisible(false);
                        price1.setVisible(true);
                        price2.setVisible(false);
                        add1.setVisible(true);
                        add2.setVisible(false);
                        del1.setVisible(true);
                        del2.setVisible(false);
                        amount1.setVisible(true);
                        amount2.setVisible(false);
                        confirm1.setVisible(true);
                        confirm2.setVisible(false);
                        amount1.setText(String.valueOf(0));
                        amount2.setText(String.valueOf(0));
                        storage.productOther.add(resultSet.getString(1));
                        storage.IdOther.add(resultSet2.getString(1));
                    }
                    name1.setText(storage.IdOther.get(0) + "." + " " +storage.productOther.get(0));
                    try {
                        String sql1 = "SELECT product_price FROM `product_storage` WHERE `product_catalog` = \"Other\" ";
                        preparedStatement = con.prepareStatement(sql1);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            storage.priceOther.add(resultSet.getString(1));
                        }
                        price1.setText(storage.priceOther.get(0));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        staff1.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent e) {
                                   Desktop d1 = Desktop.getDesktop();
                                   try {
                                       d1.browse(new URI("https://www.facebook.com/Gear.Bintaruban"));
                                   } catch (IOException e1) {
                                       e1.printStackTrace();
                                   } catch (URISyntaxException e1) {
                                       e1.printStackTrace();
                                   }
                               }
                           }
        );
        staff2.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent e) {
                                   Desktop d = Desktop.getDesktop();
                                   try {
                                       d.browse(new URI("https://www.facebook.com/frong.natdanai"));
                                   } catch (IOException e2) {
                                       e2.printStackTrace();
                                   } catch (URISyntaxException e2) {
                                       e2.printStackTrace();
                                   }
                               }
                           }
        );
        staff3.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent e) {
                                   Desktop d = Desktop.getDesktop();
                                   try {
                                       d.browse(new URI("https://www.facebook.com/suwunnapa123"));
                                   } catch (IOException e1) {
                                       e1.printStackTrace();
                                   } catch (URISyntaxException e1) {
                                       e1.printStackTrace();
                                   }
                               }
                           }
        );
        staff4.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent e) {
                                   Desktop d = Desktop.getDesktop();
                                   try {
                                       d.browse(new URI("https://www.facebook.com/bass.zaa.16"));
                                   } catch (IOException e1) {
                                       e1.printStackTrace();
                                   } catch (URISyntaxException e1) {
                                       e1.printStackTrace();
                                   }
                               }
                           }
        );
        confirm1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (projectStatus.getText().equals("Not Assign")) {
                    if (!amount1.getText().equals("0")) {
                        if (name1.getText().contains("Shure GLXD24RA/SM58-Z2")) {
                            prices2 = prices2 + (Double.parseDouble(price1.getText()) * Double.parseDouble(amount1.getText()));
                        } else if (name1.getText().contains("YAMAHA EMX5")) {
                            prices3 = prices3 + (Double.parseDouble(price1.getText()) * Double.parseDouble(amount1.getText()));
                        } else if (name1.getText().contains("TOA A-1724")) {
                            prices4 = prices4 + (Double.parseDouble(price1.getText()) * Double.parseDouble(amount1.getText()));
                        } else if (name1.getText().contains("Numark MP103USB")) {
                            prices7 = prices7 + (Double.parseDouble(price1.getText()) * Double.parseDouble(amount1.getText()));
                        } else if (name1.getText().contains("Canare L-2T2S")) {
                            prices8 = prices8 + (Double.parseDouble(price1.getText()) * Double.parseDouble(amount1.getText()));
                        } else if (name1.getText().contains("K&M21070")) {
                            prices10 = prices10 + (Double.parseDouble(price1.getText()) * Double.parseDouble(amount1.getText()));
                        }
                        showSelected.appendText(name1.getText() + " " + amount1.getText() + " Prize\n");
                        amount1.setText("0");


                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error Input");
                        alert.setHeaderText("");
                        alert.setContentText("Invalid amount");
                        alert.showAndWait();
                    }
                }
            }
        });
        confirm2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (projectStatus.getText().equals("Not Assign")) {
                    if (!amount2.getText().equals("0")) {
                        if (name2.getText().contains("SHURE SM58-LC")) {
                            prices1 = prices1 + Double.parseDouble(price2.getText()) * Double.parseDouble(amount2.getText());
                        } else if (name2.getText().contains("YAMAHA VXS8")) {
                            prices5 = prices5 + (Double.parseDouble(price2.getText()) * Double.parseDouble(amount2.getText()));
                        } else if (name2.getText().contains("Belden 8473 14AWG-305M")) {
                            prices9 = prices9 + (Double.parseDouble(price2.getText()) * Double.parseDouble(amount2.getText()));
                        }
                        showSelected.appendText(name2.getText() + " " + amount2.getText() + " Prize\n");
                        amount2.setText("0");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error Input");
                        alert.setHeaderText("");
                        alert.setContentText("Invalid amount");
                        alert.showAndWait();
                    }
                }
            }
        });
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (projectStatus.getText().equals("Not Assign")) {
                    showSelected.undo();
                    storage.productMC.clear();
                    storage.priceMC.clear();
                    storage.productSC.clear();
                    storage.priceSC.clear();
                    storage.productCC.clear();
                    storage.priceCC.clear();
                    storage.productDVD.clear();
                    storage.priceDVD.clear();
                    storage.productPM.clear();
                    storage.pricePM.clear();
                    storage.productOther.clear();
                    storage.priceOther.clear();
                    storage.assigneds.clear();
                    storage.assignP.clear();
                    storage.IdOther.clear();
                    storage.IdCC.clear();
                    storage.IdDVD.clear();
                    storage.IdSC.clear();
                    storage.IdPM.clear();
                    storage.IdMC.clear();
                }
            }
        });
        assign.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!showSelected.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle(" ");
                    alert.setHeaderText("Please Check Your Order");
                    if (count1 > 0) {
                        String a = "Shure GLXD24RA/SM58-Z2  " + count1 + " Prize = " + prices2 + " Baht";
                        storage.assignP.add(a);
                    }
                    if (count2 > 0) {
                        String a = "SHURE SM58-LC                   " + count2 + " Prize = " + prices1 + " Baht";
                        storage.assignP.add(a);
                    }
                    if (count3 > 0) {
                        String a = "YAMAHA EMX5                    " + count3 + " Prize = " + prices3 + " Baht";
                        storage.assignP.add(a);
                    }
                    if (count4 > 0) {
                        String a = "TOA A-1724                          " + count4 + " Prize = " + prices4 + " Baht";
                        storage.assignP.add(a);
                    }
                    if (count5 > 0) {
                        String a = "YAMAHA VXS8                      " + count5 + " Prize = " + prices5 + " Baht";
                        storage.assignP.add(a);
                    }
                    if (count7 > 0) {
                        String a = "Numark MP103USB              " + count7 + " Prize = " + prices7 + " Baht";
                        storage.assignP.add(a);
                    }
                    if (count8 > 0) {
                        String a = "Canare L-2T2S                      " + count8+ " Prize = " + prices8 + " Baht";
                        storage.assignP.add(a);
                    }
                    if (count9 > 0) {
                        String a = "Belden 8473 14AWG-305M  " + count9 + " Prize = " + prices9+ " Baht";
                        storage.assignP.add(a);
                    }
                    if (count10 > 0) {
                        String a = "K&M21070                            " + count10 + " Prize = " + prices10 + " Baht";
                        storage.assignP.add(a);
                    }
                    assigned();
                    sum = prices1 + prices2 + prices3 + prices4 + prices5 + prices7 + prices8 + prices9 + prices10;
                    alert.setContentText(
                            getAssigned() + getAssigned2() + getAssigned3() + getAssigned4() + getAssigned5() + getAssigned6() + getAssigned7()
                                    + getAssigned8() + getAssigned9() + "All Price = " + sum + " Baht"
                    );
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        DepositPayment.setDisable(true);
                        DepositReport.setDisable(true);
                        Fullpayment.setDisable(true);
                        FullpaymentReport.setDisable(true);
                        WorkingStatus.setDisable(true);
                        PaymentStatus.setDisable(true);
                        AssignProject();

                        if (count1 > 0) {
                            String sql = "INSERT INTO `project_detail`(`project_id`, `product_id`, `product_name`, `product_price`, `product_amount`, `product_sumprice`, `project_status`) " +
                                    "VALUES (?,?,?,?,?,?,?)";
                            String sql2 = "Select product_price from product_storage where product_name = ? ";
                            try {
                                preparedStatement2 = con.prepareStatement(sql2);
                                preparedStatement2.setString(1,"SHURE SM58-LC");
                                resultSet2 = preparedStatement2.executeQuery();
                                if(resultSet2.next()) {
                                    preparedStatement = con.prepareStatement(sql);
                                    preparedStatement.setString(1, String.valueOf(id));
                                    preparedStatement.setString(2, "M01");
                                    preparedStatement.setString(3, "SHURE SM58-LC");
                                    preparedStatement.setString(4, resultSet2.getString(1));
                                    preparedStatement.setString(5, String.valueOf(count1));
                                    preparedStatement.setString(6, String.valueOf(prices2));
                                    preparedStatement.setString(7,"Assign");
                                    preparedStatement.executeUpdate();
                                }
                            }catch (SQLException ex){
                                ex.fillInStackTrace();
                            }
                        }
                        if (count2 > 0) {
                            String sql = "INSERT INTO `project_detail`(`project_id`, `product_id`, `product_name`, `product_price`, `product_amount`, `product_sumprice`, `project_status`) " +
                                    "VALUES (?,?,?,?,?,?,?)";
                            String sql2 = "Select product_price from product_storage where product_name = ? ";
                            try {
                                preparedStatement2 = con.prepareStatement(sql2);
                                preparedStatement2.setString(1,"Shure GLXD24RA/SM58-Z2");
                                resultSet2 = preparedStatement2.executeQuery();
                                if(resultSet2.next()) {
                                    preparedStatement = con.prepareStatement(sql);
                                    preparedStatement.setString(1, String.valueOf(id));
                                    preparedStatement.setString(2, "M02");
                                    preparedStatement.setString(3, "Shure GLXD24RA/SM58-Z2");
                                    preparedStatement.setString(4, resultSet2.getString(1));
                                    preparedStatement.setString(5, String.valueOf(count2));
                                    preparedStatement.setString(6, String.valueOf(prices1));
                                    preparedStatement.setString(7,"Assign");
                                    preparedStatement.executeUpdate();
                                }
                            }catch (SQLException ex){
                                ex.fillInStackTrace();
                            }
                        }
                        if (count3 > 0) {
                            String sql = "INSERT INTO `project_detail`(`project_id`, `product_id`, `product_name`, `product_price`, `product_amount`, `product_sumprice`, `project_status`) " +
                                    "VALUES (?,?,?,?,?,?,?)";
                            String sql2 = "Select product_price from product_storage where product_name = ? ";
                           try {
                                preparedStatement2 = con.prepareStatement(sql2);
                                preparedStatement2.setString(1,"YAMAHA EMX5");
                                resultSet2 = preparedStatement2.executeQuery();
                                if(resultSet2.next()) {
                                    preparedStatement = con.prepareStatement(sql);
                                    preparedStatement.setString(1, String.valueOf(id));
                                    preparedStatement.setString(2, "P01");
                                    preparedStatement.setString(3, "YAMAHA EMX5");
                                    preparedStatement.setString(4, resultSet2.getString(1));
                                    preparedStatement.setString(5, String.valueOf(count3));
                                    preparedStatement.setString(6, String.valueOf(prices3));
                                    preparedStatement.setString(7,"Assign");
                                    preparedStatement.executeUpdate();
                                }
                            }catch (SQLException ex){
                                ex.fillInStackTrace();
                            }
                        }
                        if (count4 > 0) {
                            String sql = "INSERT INTO `project_detail`(`project_id`, `product_id`, `product_name`, `product_price`, `product_amount`, `product_sumprice`, `project_status`) " +
                                    "VALUES (?,?,?,?,?,?,?)";
                            String sql2 = "Select product_price from product_storage where product_name = ? ";
                           try {
                                preparedStatement2 = con.prepareStatement(sql2);
                                preparedStatement2.setString(1,"TOA A-1724");
                                resultSet2 = preparedStatement2.executeQuery();
                                if(resultSet2.next()) {
                                    preparedStatement = con.prepareStatement(sql);
                                    preparedStatement.setString(1, String.valueOf(id));
                                    preparedStatement.setString(2, "S01");
                                    preparedStatement.setString(3, "TOA A-1724");
                                    preparedStatement.setString(4, resultSet2.getString(1));
                                    preparedStatement.setString(5, String.valueOf(count4));
                                    preparedStatement.setString(6, String.valueOf(prices4));
                                    preparedStatement.setString(7,"Assign");
                                    preparedStatement.executeUpdate();
                                }
                            }catch (SQLException ex){
                                ex.fillInStackTrace();
                            }
                        }
                        if (count5 > 0) {
                            String sql = "INSERT INTO `project_detail`(`project_id`, `product_id`, `product_name`, `product_price`, `product_amount`, `product_sumprice`, `project_status`) " +
                                    "VALUES (?,?,?,?,?,?,?)";
                            String sql2 = "Select product_price from product_storage where product_name = ? ";
                            try {
                                preparedStatement2 = con.prepareStatement(sql2);
                                preparedStatement2.setString(1,"YAMAHA_VXS8");
                                resultSet2 = preparedStatement2.executeQuery();
                                if(resultSet2.next()) {
                                    preparedStatement = con.prepareStatement(sql);
                                    preparedStatement.setString(1, String.valueOf(id));
                                    preparedStatement.setString(2, "S02");
                                    preparedStatement.setString(3, "YAMAHA_VXS8");
                                    preparedStatement.setString(4, resultSet2.getString(1));
                                    preparedStatement.setString(5, String.valueOf(count5));
                                    preparedStatement.setString(6, String.valueOf(prices5));
                                    preparedStatement.setString(7,"Assign");
                                    preparedStatement.executeUpdate();
                                }
                            }catch (SQLException ex){
                                ex.fillInStackTrace();
                            }
                        }
                        if (count7 > 0) {
                            String sql = "INSERT INTO `project_detail`(`project_id`, `product_id`, `product_name`, `product_price`, `product_amount`, `product_sumprice`, `project_status`) " +
                                    "VALUES (?,?,?,?,?,?,?)";
                            String sql2 = "Select product_price from product_storage where product_name = ? ";
                            try {
                                preparedStatement2 = con.prepareStatement(sql2);
                                preparedStatement2.setString(1,"Numark MP103USB");
                                resultSet2 = preparedStatement2.executeQuery();
                                if(resultSet2.next()) {
                                    preparedStatement = con.prepareStatement(sql);
                                    preparedStatement.setString(1, String.valueOf(id));
                                    preparedStatement.setString(2, "D01");
                                    preparedStatement.setString(3, "Numark MP103USB");
                                    preparedStatement.setString(4, resultSet2.getString(1));
                                    preparedStatement.setString(5, String.valueOf(count7));
                                    preparedStatement.setString(6, String.valueOf(prices7));
                                    preparedStatement.setString(7,"Assign");
                                    preparedStatement.executeUpdate();
                                }
                            }catch (SQLException ex){
                                ex.fillInStackTrace();
                            }
                        }
                        if (count8 > 0) {
                            String sql = "INSERT INTO `project_detail`(`project_id`, `product_id`, `product_name`, `product_price`, `product_amount`, `product_sumprice`, `project_status`) " +
                                    "VALUES (?,?,?,?,?,?,?)";
                            String sql2 = "Select product_price from product_storage where product_name = ? ";
                            try {
                                preparedStatement2 = con.prepareStatement(sql2);
                                preparedStatement2.setString(1,"Canare L-2T2S");
                                if(resultSet2.next()) {
                                    preparedStatement = con.prepareStatement(sql);
                                    preparedStatement.setString(1, String.valueOf(id));
                                    preparedStatement.setString(2, "C01");
                                    preparedStatement.setString(3, "Canare L-2T2S");
                                    preparedStatement.setString(4, resultSet2.getString(1));
                                    preparedStatement.setString(5, String.valueOf(count8));
                                    preparedStatement.setString(6, String.valueOf(prices8));
                                    preparedStatement.setString(7,"Assign");
                                    preparedStatement.executeUpdate();
                                }
                            }catch (SQLException ex){
                                ex.fillInStackTrace();
                            }
                        }
                        if (count9 > 0) {
                            String sql = "INSERT INTO `project_detail`(`project_id`, `product_id`, `product_name`, `product_price`, `product_amount`, `product_sumprice`, `project_status`) " +
                                    "VALUES (?,?,?,?,?,?,?)";
                            String sql2 = "Select product_price from product_storage where product_name = ? ";
                            try {
                                preparedStatement2 = con.prepareStatement(sql2);
                                preparedStatement2.setString(1,"Belden 8473 14AWG-305M");
                                resultSet2 = preparedStatement2.executeQuery();
                                if(resultSet2.next()) {
                                    preparedStatement = con.prepareStatement(sql);
                                    preparedStatement.setString(1, String.valueOf(id));
                                    preparedStatement.setString(2, "C02");
                                    preparedStatement.setString(3, "Belden 8473 14AWG-305M");
                                    preparedStatement.setString(4, resultSet2.getString(1));
                                    preparedStatement.setString(5, String.valueOf(count9));
                                    preparedStatement.setString(6, String.valueOf(prices9));
                                    preparedStatement.setString(7,"Assign");
                                    preparedStatement.executeUpdate();
                                }
                            }catch (SQLException ex){
                                ex.fillInStackTrace();
                            }
                        }
                        if (count10 > 0) {
                            String sql = "INSERT INTO `project_detail`(`project_id`, `product_id`, `product_name`, `product_price`, `product_amount`, `product_sumprice`, `project_status`) " +
                                    "VALUES (?,?,?,?,?,?,?)";
                            String sql2 = "Select product_price from product_storage where product_name = ? ";
                            try {
                                preparedStatement2 = con.prepareStatement(sql2);
                                preparedStatement2.setString(1,"K&M21070");
                                resultSet2 = preparedStatement2.executeQuery();
                                if(resultSet2.next()) {
                                    preparedStatement = con.prepareStatement(sql);
                                    preparedStatement.setString(1, String.valueOf(id));
                                    preparedStatement.setString(2, "O01");
                                    preparedStatement.setString(3, "K&M21070");
                                    preparedStatement.setString(4, resultSet2.getString(1));
                                    preparedStatement.setString(5, String.valueOf(count10));
                                    preparedStatement.setString(6, String.valueOf(prices10));
                                    preparedStatement.setString(7,"Assign");
                                    preparedStatement.executeUpdate();
                                }
                            }catch (SQLException ex){
                                ex.fillInStackTrace();
                            }
                        }
                        projectStatus.setText("Assign");
                        projectStatus.setTextFill(Color.GREEN);
                      }
                    else if (result.get() == ButtonType.CANCEL) {
                        prices1 = 0 ;
                        prices2 = 0 ;
                        prices3 = 0 ;
                        prices4 = 0 ;
                        prices5 = 0 ;
                        prices7 = 0  ;
                        prices8 = 0 ;
                        prices9= 0 ;
                        prices10 = 0 ;
                        count1 = 0 ;
                        count2 = 0 ;
                        count3 = 0 ;
                        count4 = 0 ;
                        count5 = 0 ;
                        count7 = 0;
                        count8 = 0 ;
                        count9 = 0 ;
                        count10 =0;
                        storage.productMC.clear();
                        storage.priceMC.clear();
                        storage.productSC.clear();
                        storage.priceSC.clear();
                        storage.productCC.clear();
                        storage.priceCC.clear();
                        storage.productDVD.clear();
                        storage.priceDVD.clear();
                        storage.productPM.clear();
                        storage.pricePM.clear();
                        storage.productOther.clear();
                        storage.priceOther.clear();
                        storage.assigneds.clear();
                        storage.assignP.clear();
                        storage.IdOther.clear();
                        storage.IdCC.clear();
                        storage.IdDVD.clear();
                        storage.IdSC.clear();
                        storage.IdPM.clear();
                        storage.IdMC.clear();

                    }
                    showSelected.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please Select Product");
                    alert.setTitle("");
                    alert.setHeaderText("");
                    alert.showAndWait();
                }
          }
        });
        DepositReport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                }
        });
        DepositPayment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FirstPayment.fxml"));
                    Parent root = (Parent) loader.load();
                    FirstPaymentController firstPaymentController = loader.getController();
                    firstPaymentController.getID(project_idshow.getText());
                    stage.setScene(new Scene(root, 721, 625));
                    stage.show();
                    WorkingStatus.setDisable(false);
                    PaymentStatus.setDisable(false);
                    DepositReport.setDisable(false);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Fullpayment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FullPayment.fxml"));
                    Parent root = (Parent) loader.load();
                    FullPaymentController fullPaymentController = loader.getController();
                    fullPaymentController.getID(project_idshow.getText());
                    stage.setScene(new Scene(root, 721, 625));
                    stage.show();
                    WorkingStatus.setDisable(false);
                    PaymentStatus.setDisable(false);
                    DepositReport.setDisable(false);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        PaymentStatus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sql = "Select project_id from project where customer_id = ?";
                try {
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, labelID.getText());
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        String sql2 = "Select first_status from payment where project_id = ? " ;
                        preparedStatement2 = con.prepareStatement(sql2);
                        preparedStatement2.setString(1,resultSet.getString(1));
                        resultSet2 = preparedStatement2.executeQuery();

                        String sql3 = "Select last_status from payment where project_id = ?" ;
                        preparedStatement3 = con.prepareStatement(sql3);
                        preparedStatement3.setString(1,resultSet.getString(1));
                        resultSet3 = preparedStatement3.executeQuery();
                        if(resultSet2.next() && resultSet3.next()){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Payment Status");
                            alert.setContentText("Your First Payment Status is : " + resultSet2.getString(1) + "" +
                                    "\n" + "Your Last Payment Status is : " + resultSet3.getString(1));
                            alert.showAndWait();
                        }
                    }
                } catch (SQLException e) {
                    e.fillInStackTrace();
                }
            }
        });
        WorkingStatus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sql = "Select project_status from project where customer_id = ?" ;
                try {
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1,labelID.getText());
                    resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Working Status");
                        alert.setContentText("Your Working Status is : " + resultSet.getString(1));
                        alert.showAndWait();
                    }
                }catch (SQLException e){
                    e.fillInStackTrace();
                }

            }
        });
    }

    private String user;

    public void setFromLogin(String username) {
        this.user = username;
        String sql = "Select Firstname From customer Where Username = ? ";
        String sql2 = "Select lastname From customer Where Username = ? ";
        String sql3 = "Select Email From customer Where Username = ? ";
        String sql4 = "Select PhoneNumber From customer Where Username = ? ";
        String sql5 = "Select customer_picture From customer Where Username = ? ";
        String sql6 = "Select customer_id From customer Where Username = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement2 = con.prepareStatement(sql2);
            preparedStatement3 = con.prepareStatement(sql3);
            preparedStatement4 = con.prepareStatement(sql4);
            preparedStatement5 = con.prepareStatement(sql5);
            preparedStatement6 = con.prepareStatement(sql6);
            preparedStatement.setString(1, user);
            preparedStatement2.setString(1, user);
            preparedStatement3.setString(1, user);
            preparedStatement4.setString(1, user);
            preparedStatement5.setString(1, user);
            preparedStatement6.setString(1,user);
            resultSet = preparedStatement.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            resultSet3 = preparedStatement3.executeQuery();
            resultSet4 = preparedStatement4.executeQuery();
            resultSet5 = preparedStatement5.executeQuery();
            resultSet6 = preparedStatement6.executeQuery();
            if (resultSet.next()) {
                labelName.setText(resultSet.getString(1));
            }
            if (resultSet2.next()) {
                labelSurname.setText(resultSet2.getString(1));
            }
            if (resultSet3.next()) {
                labelEmail.setText(resultSet3.getString(1));
            }
            if (resultSet4.next()) {
                labelPhone.setText(resultSet4.getString(1));
            }
            if (resultSet5.next()) {
                image = new Image(resultSet5.getString(1));
                imageViewProfile.setImage(image);
            }
            if(resultSet6.next()){
                labelID.setText(resultSet6.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void assigned() {
        for (int i = 0; i < storage.assignP.size(); i++) {
            String a = storage.assignP.get(i) + "\n";
            storage.assigneds.add(a);
        }
    }

    public String getAssigned() {
        for (int i = 0; i < storage.assigneds.size(); i++) {
            return storage.assigneds.get(i) + " \n";
        }
        return "";
    }

    public String getAssigned2() {
        for (int i = 1; i < storage.assigneds.size(); i++) {
            return storage.assigneds.get(i) + " \n";
        }
        return "";
    }

    public String getAssigned3() {
        for (int i = 2; i < storage.assigneds.size(); i++) {
            return storage.assigneds.get(i) + " \n";
        }
        return "";
    }

    public String getAssigned4() {
        for (int i = 3; i < storage.assigneds.size(); i++) {
            return storage.assigneds.get(i) + " \n";
        }
        return "";
    }

    public String getAssigned5() {
        for (int i = 4; i < storage.assigneds.size(); i++) {
            return storage.assigneds.get(i) + " \n";
        }
        return "";
    }

    public String getAssigned6() {
        for (int i = 5; i < storage.assigneds.size(); i++) {
            return storage.assigneds.get(i) + " \n";
        }
        return "";
    }

    public String getAssigned7() {
        for (int i = 6; i < storage.assigneds.size(); i++) {
            return storage.assigneds.get(i) + " \n";
        }
        return "";
    }

    public String getAssigned8() {
        for (int i = 7; i < storage.assigneds.size(); i++) {
            return storage.assigneds.get(i) + " \n";
        }
        return "";
    }

    public String getAssigned9() {
        for (int i = 8; i < storage.assigneds.size(); i++) {
            return storage.assigneds.get(i) + " \n";
        }
        return "";
    }



    public void AssignProject() {
        String sql = "INSERT INTO `project`(`project_id`, `customer_id`, `project_Status`, `project_date` ,payment_id) VALUES (?,?,?,?,?)";
        String sqlID = "Select customer_id FROM `customer` Where Username = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement2 = con.prepareStatement(sqlID);
            preparedStatement2.setString(1, user);
            resultSet2 = preparedStatement2.executeQuery();
            if (resultSet2.next()) {
                preparedStatement.setString(1, String.valueOf(id));
                preparedStatement.setString(2, resultSet2.getString(1));
                preparedStatement.setString(3, "Assign");
                preparedStatement.setDate(4,java.sql.Date.valueOf(java.time.LocalDate.now()));
                preparedStatement.setString(5, String.valueOf(random()));
                preparedStatement.executeUpdate();
                project_id.setVisible(true);
                project_idshow.setText(String.valueOf(id));
                project_idshow.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void disable(String username) {
        String sql = " SELECT`project_status`FROM `project` WHERE customer_id = ?";
        String sqlID = "Select customer_id FROM `customer` Where Username = ?";
        String sqlpID = " SELECT`project_id`FROM `project` WHERE customer_id = ?";
        try {
            preparedStatement2 = con.prepareStatement(sqlID);
            preparedStatement2.setString(1, username);
            resultSet2 = preparedStatement2.executeQuery();

            if (resultSet2.next()) {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, resultSet2.getString(1));
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                        if (resultSet.getString(1).equals("Assign") || resultSet.getString(1).equals("ConfirmOrder")
                                || resultSet.getString(1).equals("Working") || resultSet.getString(1).equals("Complete") || resultSet.getString(1).equals("ConfirmOrder & WaitPayment")) {

                            projectStatus.setTextFill(Color.GREEN);
                            projectStatus.setText("Assign");
                            add1.setDisable(true);
                            add2.setDisable(true);
                            del1.setDisable(true);
                            del2.setDisable(true);
                            confirm1.setDisable(true);
                            confirm2.setDisable(true);
                            assign.setDisable(true);
                            undo.setDisable(true);


                            preparedStatement4 = con.prepareStatement(sqlpID);
                            preparedStatement4.setString(1,resultSet2.getString(1));
                            resultSet4 = preparedStatement4.executeQuery();

                            if(resultSet4.next()) {

                                project_id.setVisible(true);
                                project_idshow.setText(resultSet4.getString(1));
                                project_idshow.setVisible(true);



                                String sql2 = "Select first_status From payment where project_id = ?" ;
                                preparedStatement3 = con.prepareStatement(sql2);
                                preparedStatement3.setString(1,project_idshow.getText());
                                resultSet3 = preparedStatement3.executeQuery();


                                String sqlLast = "Select last_status From payment where project_id = ?" ;
                                preparedStatement7 = con.prepareStatement(sqlLast);
                                preparedStatement7.setString(1,project_idshow.getText());
                                resultSet7 = preparedStatement7.executeQuery();


                                if(resultSet3.next()) {


                                   if (resultSet.getString(1).equals("Assign")){

                                       DepositReport.setDisable(true);
                                       Fullpayment.setDisable(true);
                                       DepositPayment.setDisable(true);
                                       FullpaymentReport.setDisable(true);
                                       WorkingStatus.setDisable(false);
                                       PaymentStatus.setDisable(true);
                                       System.err.println("1");

                                   }else if(resultSet.getString(1).equals("ConfirmOrder")){

                                       DepositReport.setDisable(true);
                                       Fullpayment.setDisable(true);
                                       DepositPayment.setDisable(false);
                                       FullpaymentReport.setDisable(true);
                                       WorkingStatus.setDisable(false);
                                       PaymentStatus.setDisable(false);
                                       System.err.println("2");

                                   }
                                   else if(resultSet.getString(1).equals("ConfirmOrder & WaitPayment") || resultSet.getString(1).equals("Working")){
                                       DepositReport.setDisable(true);
                                       Fullpayment.setDisable(true);
                                       DepositPayment.setDisable(false);
                                       FullpaymentReport.setDisable(true);
                                       WorkingStatus.setDisable(false);
                                       PaymentStatus.setDisable(false);
                                       System.err.println("Test");
                                   }
                                   else if (resultSet3.getString(1).equals("Payment Confirm") && resultSet.getString(1).equals("ConfirmOrder")){

                                       DepositReport.setDisable(false);
                                       Fullpayment.setDisable(true);
                                       DepositPayment.setDisable(true);
                                       FullpaymentReport.setDisable(true);
                                       WorkingStatus.setDisable(false);
                                       PaymentStatus.setDisable(false);
                                       System.err.println("3");

                                   }


                                    if(resultSet7.next()){
                                        if(resultSet3.getString(1).equals("Payment Confirm") &&resultSet7.getString(1).equals("WaitConfirm") && resultSet.getString(1).equals("Complete")){

                                            DepositReport.setDisable(false);
                                            Fullpayment.setDisable(true);
                                            DepositPayment.setDisable(true);
                                            FullpaymentReport.setDisable(true);
                                            WorkingStatus.setDisable(false);
                                            PaymentStatus.setDisable(false);
                                            System.err.println("4");

                                        }
                                        else if (resultSet3.getString(1).equals("Payment Confirm") && resultSet7.getString(1).equals("Payment Confirm") &&resultSet.getString(1).equals("Complete")){

                                            DepositReport.setDisable(false);
                                            Fullpayment.setDisable(true);
                                            DepositPayment.setDisable(true);
                                            FullpaymentReport.setDisable(false);
                                            WorkingStatus.setDisable(false);
                                            PaymentStatus.setDisable(false);
                                            System.err.println("5");

                                        }else if(resultSet.getString(1).equals("Complete") &&resultSet3.getString(1).equals("Payment Confirm")){
                                            DepositPayment.setDisable(true);
                                            DepositReport.setDisable(false);
                                            Fullpayment.setDisable(false);
                                            FullpaymentReport.setDisable(false);
                                            WorkingStatus.setDisable(false);
                                            PaymentStatus.setDisable(false);
                                            System.err.println("6");
                                        }
                                    }
                                }else{

                                    DepositReport.setDisable(true);
                                    Fullpayment.setDisable(true);
                                    DepositPayment.setDisable(false);
                                    FullpaymentReport.setDisable(true);
                                    WorkingStatus.setDisable(true);
                                    PaymentStatus.setDisable(true);
                                    System.err.println("7");
                                }

                            }
                        }
                    } else {

                        DepositReport.setDisable(true);
                        DepositPayment.setDisable(true);
                        FullpaymentReport.setDisable(true);
                        Fullpayment.setDisable(true);
                        WorkingStatus.setDisable(true);
                        PaymentStatus.setDisable(true);

                    }
                }else{
                DepositReport.setDisable(true);
                DepositPayment.setDisable(true);
                FullpaymentReport.setDisable(true);
                Fullpayment.setDisable(true);
                WorkingStatus.setDisable(true);
                PaymentStatus.setDisable(true);
            }


    }catch (SQLException e) {
            e.printStackTrace();
        }
        }




    }












