package br.univali.poo.karoline.view.lib;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Settings{
    public static ImageView generateImageView(String fileDirectory, int requestWidth, int requestedHeight, Class<?> getClass){
        return new ImageView(new Image(getClass.getResource(fileDirectory).toExternalForm(),
                    requestWidth,requestedHeight,true,true));
    }

    public static Image generateImage(String fileDirectory, int requestWidth, int requestedHeight, Class<?> getClass){
        return new Image(getClass.getResource(fileDirectory).toExternalForm(),
                requestWidth,requestedHeight,true,true);
    }

    public static void generateLabelStyleWeight(Label label,String style){
        label.setStyle("-fx-font-weight:" + style);
    }

    public static Label generateLabel(String name, String font, int size){
        Label text = new Label();
        text.setText(name + "  ");
        text.setFont(new Font(font,size));
        return text;
    }

    public static Label generateLabelWithWeight(String name, String font, int size,String style){
        Label text = new Label();
        text.setText(name + "  ");
        text.setFont(new Font(font,size));
        text.setStyle("-fx-font-weight: " + style);
        return text;
    }

    public static Background generateBackground(Color color){
        return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public static Background generateBackgroundImage(String fileDirectory,int requestedWidth, int requestedHeight,Class<?> getClass){
        return new Background(new BackgroundImage(generateImage(fileDirectory,requestedWidth,requestedHeight,getClass),null,null, BackgroundPosition.CENTER,null));
    }

    public static void generateShadow(Node node, int size,int width){
        node.setStyle("-fx-effect: dropshadow(three-pass-box, black," + size + ", 0,"+ width +"," + width + ") ");
    }

    public static HBox generateTextField(String name, String font, int size, TextField textField){
        HBox hBox = new HBox();
        hBox.getChildren().add(Settings.generateLabel(name,font,size));
        hBox.getChildren().add(textField);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    public static Button generateButton(String name,String font,int size, String backgroundColor) {
        Button button = new Button();
        button.setText(name);
        button.setFont(new Font(font, size));
        button.setStyle("-fx-focus-color: transparent; " +
                        "-fx-base:" + backgroundColor
        );
        return button;
    }

    public static VBox generateButtonWithShadowAndEvent(String name, String font, int size, String backgroundColor, EventHandler<MouseEvent> mouseEvent){
        VBox vBox = new VBox();
        Button button = new Button();
        button.setText(name);
        button.setFont(new Font(font, size));
        button.setStyle("-fx-focus-color: transparent; " +
                "-fx-base:" + backgroundColor
        );
        button.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEvent);
        vBox.getChildren().add(button);
        generateShadow(vBox,3,1);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }
}
