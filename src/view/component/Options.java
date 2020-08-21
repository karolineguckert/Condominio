package br.univali.poo.karoline.view.screen;

import br.univali.poo.karoline.view.component.Paper;
import br.univali.poo.karoline.view.lib.Settings;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Options {
    private String font;
    private int fontSize;
    private String fontFamily;
    private String diretoryOfImage;
    private int paperWidth;
    private int paperWeight;

    public Options (String font, int fontSize, String fontFamily, String diretoryOfImage,int paperWeight, int paperWidth){
        this.font = font;
        this.fontSize = fontSize;
        this.fontFamily = fontFamily;
        this.diretoryOfImage = diretoryOfImage;
        this.paperWeight = paperWeight;
        this.paperWidth = paperWidth;
    }

    public Paper insertOption(String optionText, String imageName, int requestedWidht, int requestedHeight, Class<?> getClass,EventHandler<MouseEvent> event){
        Paper paper = new Paper(paperWidth,paperWeight);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(Settings.generateImageView(diretoryOfImage + imageName + ".png", 35, 35, getClass),
                Settings.generateLabelWithWeight(optionText, font, fontSize, fontFamily));
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(8);
        paper.getChildren().add(hBox);
        paper.setAlignment(Pos.CENTER);
        paper.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        return paper;
    }
}
