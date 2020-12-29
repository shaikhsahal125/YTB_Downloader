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
    private Process p;

    public void download(ActionEvent event) throws IOException {
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

        System.out.println("Starting");
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder("python ", getInfoScript, inputUrl); // Arrays.asList("python ", getInfoScript+" ", inputUrl)
//
//            Process process = processBuilder.start();
//
//            System.out.println("ran it");
//        } catch (Exception e) {
//            System.out.println("in catch");
//            e.printStackTrace();
//        }

        ProcessBuilder processBuilder = new ProcessBuilder("python3", getInfoScript, inputUrl);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        Reader reader = new InputStreamReader(process.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        String s;
        while ((s = bufferedReader.readLine()) != null){
            if(s.startsWith("Name") || s.startsWith("Views") || s.startsWith("Likes") || s.startsWith("Dislikes") || s.startsWith("Thumbnail")){
                stringBuilder.append(s + "\n");
            }
        }
        if (stringBuilder.toString().equals("")){
            System.out.println("Errorrrrrrrr");
        }
        System.out.println(stringBuilder.toString());

        System.out.println("Ended");

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
