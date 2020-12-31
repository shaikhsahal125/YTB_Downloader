package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.script.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class FrontPageController {
    @FXML
    TextField inputTextField;

    @FXML
    RadioButton audioRBtn;

    @FXML
    RadioButton videoRBtn;

    @FXML
    Button downloadBtn;

    @FXML
    Text statusText;

    @FXML
    ProgressIndicator loader;

    private String inputUrl;
    private boolean audioSelected;
    private boolean videoSelected;
    private String getInfoScript = "src/controller/pythonScripts/getInfo.py";
    private String thumbnail;
    private String type;
    private String save_file_path;


    public void download(ActionEvent event) throws IOException, InterruptedException {

        statusText.setText("Processing...\nPlease wait, it may take a while" );
        statusText.setFill(Color.GREEN);
        loader.setVisible(true);

        inputUrl = inputTextField.getText().trim();
        audioSelected = audioRBtn.isSelected();
        videoSelected = videoRBtn.isSelected();

        if (inputUrl.equals("")){

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error!");
            error.setHeaderText("You did not enter any URL.");
            error.setContentText("Please enter a URL and try again.");

            statusText.setText("You must have to enter a URL");
            statusText.setFill(Color.INDIANRED);

            error.showAndWait();
            loader.setVisible(false);
            return;
        }


        if (audioSelected){
            System.out.println("audio selected");
            type = "audio";
        } else if (videoSelected){
            System.out.println("video selected");
            type = "video";
        } else {
            System.out.println("something is really wrong");
            loader.setVisible(false);
            return;
        }

        DirectoryChooser selectDestination = new DirectoryChooser();
        selectDestination.setTitle("Select your destination");
        File dirPath = selectDestination.showDialog(downloadBtn.getScene().getWindow());

        if (dirPath == null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("File selection ERROR!");
            error.setHeaderText("Make sure you select your destination folder");
            statusText.setText("You must select a save Destination");
            statusText.setFill(Color.RED);
            error.showAndWait();
            loader.setVisible(false);
            return;
        } else {
            save_file_path = dirPath.getPath();
        }

        ProcessBuilder processBuilder = new ProcessBuilder("python3", getInfoScript, inputUrl, type, save_file_path).redirectErrorStream(true);
        Process process = processBuilder.start();

        Reader reader = new InputStreamReader(process.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();

        String s;
        while ((s = bufferedReader.readLine()) != null){
            if (s.startsWith("Title") || s.startsWith("Views") || s.startsWith("Likes") || s.startsWith("Dislikes")) {
                stringBuilder.append(s + "\n");
            }
        }
        process.waitFor();
        System.out.println(process);


        if (stringBuilder.toString().equals("")){
            System.out.println("Error occurred while fetching data");
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Downloading error!");
            error.setHeaderText("Not able to download this content");
            error.setContentText("Please use other URLs or try again later");
            error.showAndWait();
            loader.setVisible(false);
            return;
        } else {
            statusText.setText("Almost Done..");
            statusText.setFill(Color.GREEN);
            System.out.println(stringBuilder.toString());
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Successfully Downloaded");
            success.setHeaderText("Your content information is below");
            success.setContentText(stringBuilder.toString() + "\nDestination: " + save_file_path);
            success.showAndWait();
        }


        loader.setVisible(false);
        System.out.println("downloaded...");
        statusText.setFill(Color.GREEN);
        statusText.setText("Successfully downloaded\nPlease enter other URL if you want to download more content");

    }
}
