package com.java.avance.javaavancem12024.components;

import com.java.avance.javaavancem12024.App;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class ConfirmDialog extends JFXDialog {

    private JFXButton okButton;
    private JFXButton cancelButton;
    private Label titleLabel;
    private Label contentLabel;

    public ConfirmDialog(){
        this.init("Confirm dialog", "");
    }

    public ConfirmDialog(String title, String contentText){
        this.init(title, contentText);
    }

    private void init(String title, String content){
        this.titleLabel = new Label(title);
        this.contentLabel = new Label(content);

        this.cancelButton = new JFXButton("Annuler");
        this.cancelButton.setOnAction(event -> {
            this.close();
        });

        this.okButton = new JFXButton("Confirmer");
        this.okButton.getStyleClass().add("button-danger");

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(this.titleLabel);
        layout.setBody(this.contentLabel);
        layout.setActions(this.cancelButton, this.okButton);

        this.setDialogContainer(App.root);
        this.setContent(layout);
        this.setTransitionType(DialogTransition.TOP);
    }

    public String getTitle() {
        return this.titleLabel.getText();
    }

    public void setTitle(String title) {
        this.titleLabel.setText(title);
    }

    public String getContentText() {
        return this.contentLabel.getText();
    }

    public void setContentText(String content) {
        this.contentLabel.setText(content);
    }

    public String getOkText() {
        return this.okButton.getText();
    }

    public void setOkText(String okText) {
        this.okButton.setText(okText);
    }

    public String getCancelText() {
        return this.cancelButton.getText();
    }

    public void setCancelText(String cancelText) {
        this.cancelButton.setText(cancelText);
    }

    public void onCancel(EventHandler<ActionEvent> event){
        this.cancelButton.setOnAction(event);
        this.close();
    }

    public void onConfirm(EventHandler<ActionEvent> event){
        this.okButton.setOnAction(event);
    }

}
