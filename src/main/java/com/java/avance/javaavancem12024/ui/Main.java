package com.java.avance.javaavancem12024.ui;

import com.java.avance.javaavancem12024.App;
import com.java.avance.javaavancem12024.components.AppCanvas;
import com.java.avance.javaavancem12024.components.ConfirmDialog;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ScrollPane scrooCanvas;

    @FXML
    private ToggleButton buttonDoor;

    @FXML
    private ToggleButton buttonDrawing;

    @FXML
    private ToggleButton buttonWindow;

    @FXML
    private ToggleButton buttonCursor;

    @FXML
    private ToggleButton buttonRectangle;

    @FXML
    private ToggleGroup optionGroup;

    @FXML
    private MenuItem menuArreter;

    @FXML
    private MenuItem menuDelete;

    @FXML
    private MenuItem menuQuiter;

    @FXML
    private MenuItem menuDown;

    @FXML
    private MenuItem menuLeft;

    @FXML
    private MenuItem menuRight;

    @FXML
    private MenuItem menuUp;

    @FXML
    private MenuItem menuSaveAs;

    @FXML
    private MenuItem menuNew;

    @FXML
    private Label labelAppName;

    private AppCanvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borderPane.prefWidthProperty().bind(root.widthProperty());
        borderPane.prefHeightProperty().bind(root.heightProperty());
        this._newCanvas();
        menuArreter.setOnAction(event -> { if (canvas != null) canvas.annuler(); });
        menuDelete.setOnAction(event -> { if (canvas != null) canvas.removeSelected(); });
        menuUp.setOnAction(event -> { if (canvas != null) canvas.up(); });
        menuDown.setOnAction(event -> { if (canvas != null) canvas.down(); });
        menuLeft.setOnAction(event -> { if (canvas != null) canvas.left(); });
        menuRight.setOnAction(event -> { if (canvas != null) canvas.right(); });
        menuQuiter.setOnAction(event -> quiter());
        menuNew.setOnAction(event -> _newCanvas());

        menuSaveAs.setOnAction(event -> {
            if(canvas == null) return;

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enrégistrer sous");

            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

            File f = fileChooser.showSaveDialog(root.getScene().getWindow());

            if(f != null){
                try {
                    canvas.unselect();
                    WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                    SnapshotParameters sp = new SnapshotParameters();
                    sp.setViewport( new Rectangle2D( canvas.getLayoutX(), canvas.getLayoutY(), canvas.getWidth(), canvas.getHeight()));
                    canvas.snapshot( sp, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", f);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        optionGroup.selectedToggleProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue == null && oldValue != null) optionGroup.selectToggle(oldValue);

            if (newValue == buttonDrawing && canvas != null) canvas.setState(AppCanvas.State.READY_LINE);
            if (newValue == buttonCursor && canvas != null) canvas.setState(AppCanvas.State.SELECTION);
            if (newValue == buttonWindow && canvas != null) canvas.setState(AppCanvas.State.WINDOW);
            if (newValue == buttonDoor && canvas != null) canvas.setState(AppCanvas.State.DOOR);
            if (newValue == buttonRectangle && canvas != null) canvas.setState(AppCanvas.State.READY_RECT);
        });

        optionGroup.selectToggle(buttonCursor);
    }

    public Label getLabelAppName() {
        return labelAppName;
    }

    private void _newCanvas() {
        canvas = new AppCanvas();

        canvas.stateProperty().addListener((observableValue, oldValue, newValue) -> {
            switch (newValue) {
                case WINDOW: optionGroup.selectToggle(buttonWindow); break;
                case DRAWING_LINE: case READY_LINE: optionGroup.selectToggle(buttonDrawing); break;
                case SELECTION: optionGroup.selectToggle(buttonCursor); break;
                case DOOR: optionGroup.selectToggle(buttonDoor); break;
                case DRAWING_RECT: optionGroup.selectToggle(buttonRectangle); break;
            }
        });

        scrooCanvas.setContent(canvas);
    }

    public void quiter() {
        if(App.root.lookupAll("#dialog-close").isEmpty()) {
            ConfirmDialog dialog = new ConfirmDialog("Quiter", "Etes vous sur de vouloir quiter l'application ?");
            dialog.setId("dialog-close");
            dialog.setOkText("Quiter");
            dialog.onConfirm(evt -> {
                System.exit(0);
            });
            dialog.show();
        }
    }

}
