package br.univali.poo.karoline.view.screen;

import br.univali.poo.karoline.model.Connexion;
import br.univali.poo.karoline.view.component.Paper;
import br.univali.poo.karoline.view.lib.Router;
import br.univali.poo.karoline.view.lib.Settings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;


public class Login implements Router.Routable {
    private BorderPane root;
    private TextField name;
    private TextField apartment;
    private ConnexionRoute connexionRoute;

    public Login(){
        root = new BorderPane();
        this.name = new TextField();
        this.apartment = new TextField();
        this.connexionRoute = new ConnexionRoute("localhost", "Condominio",
                "postgres", "","org.postgresql.Driver");

        root.setBackground(Settings.generateBackgroundImage("../images/background.png",936,622,getClass()));
        BorderPane.setAlignment(root, Pos.CENTER);
        root.setCenter(generateCenter());
    }

    private Node generateCenter(){
        Paper paper = new Paper(440, 250);
        paper.getChildren().add(paper.generateHeaders("Bem-vindo(a) ao Sol Nascente",400.0f,25,"Constantia","bold"));
        VBox vBox = new VBox();
        VBox body = new VBox();
        vBox.getChildren().addAll(Settings.generateTextField("Insira seu primeiro nome: ", "Cambria",16, name),
                Settings.generateTextField("Insira o seu apartamento: ", "Cambria",16, apartment));

        vBox.setSpacing(12);
        body.getChildren().addAll(vBox, Settings.generateButtonWithShadowAndEvent("Entrar","Georgia",16,"white",Event ->{
            Connexion loginConnexion = new Connexion(connexionRoute) {
                @Override
                public void action() throws SQLException {
                    String condominiumName;
                    String condominiumApartment;
                    boolean foundIt = false;

                    while (getResultSet().next()){
                        condominiumName = getResultSet().getString("nome");
                        condominiumApartment = getResultSet().getString("apartamento");

                        if (condominiumName.equals(name.getText()) && condominiumApartment.equals(apartment.getText())) {
                            foundIt = true;
                             new Router(new SecondScreen(name.getText(),apartment.getText())).getScene();
                        }
                    }

                    if (!foundIt){
                        Dialog dialog = new Dialog("Informações incorretas!");
                        dialog.confirmationOk("Verifique se suas informações foram digitadas corretamente!");
                    }
                }
            };
            loginConnexion.selectWithInnerJoinAndWhere(
                    "condomino c",
                    "torre t on t.\"ID\" = c.\"ID_torre\"",
                    "c.nome, t.apartamento",
                    "c.nome = " + "'" + name.getText() + "'" +
                            " AND t.apartamento =" + "'" + apartment.getText() + "'");
        }));
        body.setSpacing(20);
        paper.setSpacing(20);
        paper.getChildren().add(body);
        return paper;
    }

    @Override
    public Parent getRoot() {
        return root;
    }
}
