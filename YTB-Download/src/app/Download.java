package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Download extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/frontPage.fxml"));

        AnchorPane root = loader.load();
//        FrontPageController fpc = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("YouTube Downloader!");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
