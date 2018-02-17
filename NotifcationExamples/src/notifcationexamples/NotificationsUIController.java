/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;
import java.lang.Thread;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    private boolean flag1 = false;
    private boolean flag2 = false;
    private boolean flag3 = false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    public void handleTask1(ActionEvent event) {
        if(flag1){
            if(task1 != null) {
                flag1 = false;
                button1.setText("Start Task1");
                task1.end();
                task1 = null;
            }
        } else {
            if(task1 == null) {
                task1 = new Task1(2147483647, 1000000);
                task1.setNotificationTarget(this);
                task1.start();
                button1.setText("End Task1");
                flag1 = true;
            }
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void handleTask2(ActionEvent event) {
        if(flag2){
            if(task2 != null) {
                flag2 = false;
                button2.setText("Start Task2");
                task2.end();
                task2 = null;
            }
        } else {
            if(task2 == null) {
                task2 = new Task2(2147483647, 1000000);
                task2.setOnNotification((String message) -> {
                    textArea.appendText(message + "\n");
                });
                task2.start();
                button2.setText("End Task2");
                flag2 = true;
            }
        }        
    }
    
    @FXML
    public void handleTask3(ActionEvent event) {
        if(flag3){
            if(task3 != null) {
                flag3 = false;
                button3.setText("Start Task3");
                task3.end();
                task3 = null;
            }
        } else {
            if(task3 == null) {
                task3 = new Task3(2147483647, 1000000);
                task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                    if(evt.getNewValue().equals("Task3 done.")){
                        button3.setText("Start Task3");
                        task3 = null;
                        flag3 = false;
                    }
                    textArea.appendText((String) evt.getNewValue() + "\n");
                });
                task3.start();
                button3.setText("End Task3");
                flag3 = true;
            }
        }
    } 
}
