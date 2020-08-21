package br.univali.poo.karoline.view;

import br.univali.poo.karoline.view.lib.Router;
import br.univali.poo.karoline.view.lib.Settings;
import br.univali.poo.karoline.view.screen.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Condominio Sol Nascente");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Router(new Login()).getScene());
        primaryStage.setWidth(900);
        primaryStage.setHeight(512);
        primaryStage.getIcons().add(Settings.generateImage("images/icon.png",
                                                           256,
                                                           256,
                                                            getClass()));
        primaryStage.show();
    }
}
