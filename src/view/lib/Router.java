package br.univali.poo.karoline.view.lib;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class Router{
    public interface Routable{
        Parent getRoot();
    }

    public static Scene SCENE;

    public Router(Routable router){
        if(SCENE == null){
            SCENE = new Scene(router.getRoot());
        }else {
            SCENE.setRoot(router.getRoot());
        }
    }

    public Scene getScene(){
        return SCENE;
    }
}
