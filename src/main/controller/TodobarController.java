package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.EditTaskDemo;
import ui.ListView;
import ui.PomoTodoApp;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static ui.PomoTodoApp.setScene;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoOptionsPopUpFile = new File(todoOptionsPopUpFXML);
    private File todoActionsPopUpFile = new File(todoActionsPopUpFXML);

    @FXML
    private Label descriptionLabel = null;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private Task task = null;

    private JFXPopup todoOptions;
    private JFXPopup todoActions;

    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodoOptionsPopUp();
        loadTodoOptionsPopUpActionListener();
        loadTodoActionsPopUp();
        loadTodoActionsPopUpActionListener();
    }

    // EFFECTS: load options pop up (setting, exit)
    private void loadTodoOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsPopUpFile.toURI().toURL());
            fxmlLoader.setController(new TodoOptionsController());
            todoOptions = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: load view selector pop up (list view, priority view, status view)
    private void loadTodoActionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsPopUpFile.toURI().toURL());
            fxmlLoader.setController(new TodoActionsController());
            todoActions = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: show view selector pop up when its icon is clicked
    private void loadTodoOptionsPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoActions.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    // EFFECTS: show options pop up when its icon is clicked
    private void loadTodoActionsPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoOptions.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // Inner class: view selector pop up controller
    class TodoActionsController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodobarActionsPopUpController", "no functionality has been implemented for it");
                    break;
                case 1:
                    Logger.log("TodobarActionsPopUpController", "no functionality has been implemented for it");
                    break;
                case 2:
                    Logger.log("TodobarActionsPopUpController", "no functionality has been implemented for it");
                    break;
                case 3:
                    Logger.log("TodobarActionsPopUpController", "no functionality has been implemented for it");
                    break;
                default:
                    Logger.log("TodobarActionsPopUpController", "no functionality has been implemented for it");
            }
            todoActions.hide();
        }
    }

    // Inner class: option pop up controller
    class TodoOptionsController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodobarOptionsPopUpController", "Edit Task");
                    setScene(new EditTask(task));
                    break;
                case 1:
                    Logger.log("TodobarOptionsPopUpController", "Delete task successfully");
                    PomoTodoApp.getTasks().remove(task);
                    JsonFileIO.write(PomoTodoApp.getTasks());
                    setScene(new ListView(PomoTodoApp.getTasks()));
                    break;
                default:
                    Logger.log("TodobarOptionsPopUpController", "No action is implemented for the selected option");
            }
            todoOptions.hide();
        }
    }
}

