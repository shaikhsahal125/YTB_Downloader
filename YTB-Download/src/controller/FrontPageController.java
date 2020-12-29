package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    Text status;

    private String inputUrl;
    private boolean audioSelected;
    private boolean videoSelected;
    private String getInfoScript = "src/controller/pythonScripts/getInfo.py";
    private String thumbnail;


    public void download(ActionEvent event) throws IOException, InterruptedException {
        inputUrl = inputTextField.getText().trim();
        audioSelected = audioRBtn.isSelected();
        videoSelected = videoRBtn.isSelected();

        if (inputUrl.equals("")){
            // TODO make alert box
            System.out.println("you must type the url");
            return;
        }

        File f = new File(getInfoScript);
        if (f.exists()){
            System.out.println("good");
        } else {
            System.out.println("not good");
            return;
        }


        ProcessBuilder processBuilder = new ProcessBuilder("python3", getInfoScript, inputUrl).redirectErrorStream(true);
        Process process = processBuilder.start();

        Reader reader = new InputStreamReader(process.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();

        String s;
        while ((s = bufferedReader.readLine()) != null){
            if (s.startsWith("Title") || s.startsWith("Views") || s.startsWith("Likes") || s.startsWith("Dislikes")) {
                stringBuilder.append(s + "\n");
            } else if (s.startsWith("Title")) {
                thumbnail = s;
            }
        }
        process.waitFor();
        System.out.println(process);


        if (stringBuilder.toString().equals("")){
            System.out.println("Error occurred while fetching data");
        } else {
            System.out.println(stringBuilder.toString());
        }


        if (audioSelected){
            System.out.println("audio selected");
        } else if (videoSelected){
            System.out.println("video selected");
        } else {
            System.out.println("something is really wrong");
        }

        System.out.println("downloading...");
    }
}
