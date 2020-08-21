package br.univali.poo.karoline.view.component;

import br.univali.poo.karoline.view.lib.Settings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class Paper extends VBox {

    public Paper(double width, double height){
        Settings.generateShadow(this, 3, 1);
        setBackground(Settings.generateBackground(Color.WHITE));
        setMaxWidth(width);
        setMaxHeight(height);
        setAlignment(Pos.CENTER);
        setSpacing(35);
    }

    public VBox generateHeaders(String title,double setEndX,int sizeOfFont,String font,String familyStyle){
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(2);
        vBox.getChildren().addAll(generateTitle(title,sizeOfFont,font,familyStyle),generateHorizontalLine(setEndX));
        return vBox;
    }

    private Node generateTitle(String title,int sizeOfFont,String font,String familyStyle){
        return Settings.generateLabelWithWeight(title,font,sizeOfFont,familyStyle);
    }

    public Line generateHorizontalLine(double setEndX){
        Line line = new Line();
        line.setStartX(0.0f);
        line.setStartY(0.0f);
        line.setEndX(setEndX);
        line.setEndY(0.0f);
        line.setOpacity(0.4);
        return line;
    }
}
