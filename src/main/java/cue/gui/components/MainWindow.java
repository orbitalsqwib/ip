package cue.gui.components;

import cue.Cue;
import cue.errors.CueException;
import cue.gui.CommandableInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane implements CommandableInterface {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Cue cue;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/UserLogo.png"));
    private Image cueImage = new Image(this.getClass().getResourceAsStream("/images/CueLogo.png"));

    /**
     * Initializes the main window, and then greets the user.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        display("Hello, I'm Cue. What can I do for you?");
    }

    /** Injects the Duke instance */
    public void setCue(Cue c) {
        cue = c;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));
        try {
            cue.respond(input);
            userInput.clear();
        } catch (CueException error) {
            display(error.getMessage());
        }
    }

    @Override
    public void display(String output) {
        dialogContainer.getChildren().addAll(DialogBox.getCueDialog(output, cueImage));
    }
}

