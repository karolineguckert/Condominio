package br.univali.poo.karoline.view.screen;

import br.univali.poo.karoline.view.lib.Settings;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Dialog {
    private final Alert dialog;
    private final String primaryTitle;

    public Dialog(String primaryTitle) {
        this.primaryTitle = primaryTitle;
        dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setHeaderText(null);
        dialog.getButtonTypes().clear();

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(Settings.generateImage("../images/icon.png",256/2,256/2,getClass()));
        ButtonType okButton = new ButtonType("Ok", ButtonType.YES.getButtonData());
        dialog.getButtonTypes().add(okButton);
    }

    public void confirmationOk(String msg) {
        dialog.setContentText(msg);
        dialog.setTitle(primaryTitle);
        dialog.showAndWait();
    }
}
