package sample;


import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;


public class LoadingController {
    @FXML
    private ProgressBar jfxProgressBar ;
    @FXML
    private Button btnStart ;

    @FXML
    private void handlerEventButton(ActionEvent event){
        Task task = taskWorker(30);
        jfxProgressBar.progressProperty().unbind();
        jfxProgressBar.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(e->{
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Parent root = FXMLLoader.load(getClass().getResource("MainLogin.fxml"));
                stage.setScene(new Scene(root, 620, 400));
                stage.show();
            }catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        });
        Thread th = new Thread(task);
        th.start();
    }

    private Task taskWorker(int seconds){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for(int i = 0 ; i < seconds ; i++){
                    updateProgress(i+1,seconds);
                    Thread.sleep(50);
                }
                return null ;
            }
        };
    }

    public void initialize() {
        btnStart.setVisible(false);
        btnStart.fire();
    }
}

