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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;


public class SecondScreen implements Router.Routable {
    private BorderPane root;
    private String name;
    private String apartment;
    private StackPane lateralMenu;
    private StackPane centerScreen;
    private int condominumId;
    private Paper readPaper;
    private ConnexionRoute connexionRoute;
    private Options options;

    public SecondScreen(String name, String apartment) {
        root = new BorderPane();
        this.centerScreen = new StackPane();
        this.lateralMenu = new StackPane();
        this.name = name;
        this.apartment = apartment;
        this.options = new Options("Cambria",15,"bold","../images/icon - ",500,200);
        this.connexionRoute = new ConnexionRoute("localhost", "Condominio",
                "postgres", "","org.postgresql.Driver");
        root.setBackground(Settings.generateBackgroundImage("../images/background.png", 936, 622, getClass()));
        BorderPane.setAlignment(root, Pos.CENTER);
        root.setCenter(centerScreen);
        root.setLeft(generateLeft());
    }

    private Node generateLeft() {
        Paper paper = new Paper(500, 600);
        paper.setSpacing(30);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(Settings.generateImageView("../images/icon.png", 256 / 4, 256 / 4, getClass()),
                Settings.generateLabelWithWeight("Sol Nascente", "Constantia", 18, "bold"),
                paper.generateHeaders("  Bem-vindo(a) " + name, 125.0f, 14, "Cambria", "normal"));
        paper.getChildren().add(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(2);
        paper.setAlignment(Pos.CENTER);
        lateralMenu.getChildren().clear();
        firstOptions();
        paper.getChildren().add(lateralMenu);
        return paper;
    }

    private void firstOptions() {
        Paper paper = options.insertOption("  Reservar salão   ", "salon", 37, 37, getClass(), event -> {
                    lateralMenu.getChildren().clear();
                    secondOptions();
                 });
        lateralMenu.getChildren().add(paper);
    }

    private void secondOptions() {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                readOption(),
                createOption(),
                deleteOption(),
                updateOption(),
                backOption()
        );
        vBox.setSpacing(2);
        lateralMenu.getChildren().add(vBox);
    }

    private Paper deleteOption() {
        return options.insertOption("Deletar reserva","delete",35,35,getClass(),event -> {
            centerScreen.getChildren().clear();
            deleteBody();
        });
    }

    private Paper createOption() {
        return options.insertOption("Criar reserva","create",35,35,getClass(),event -> {
            centerScreen.getChildren().clear();
            createBody();
        });
    }

    private Paper readOption() {
        return options.insertOption("Ver reserva","read",35,35,getClass(),event -> {
            centerScreen.getChildren().clear();
            readAction();
        });
    }

    private Paper updateOption() {
        return options.insertOption("Atualizar reserva","update",37,37,getClass(),event -> {
            centerScreen.getChildren().clear();
            updateBody();
        });
    }

    private Paper backOption() {
        return options.insertOption("Voltar","back",40,20,getClass(),event -> {
            lateralMenu.getChildren().clear();
            firstOptions();
        });
    }

    private void deleteBody() {
        VBox vBox = new VBox();
        TextField date = new TextField();
        Paper paper = new Paper(440, 250);

        paper.getChildren().add(paper.generateHeaders("Deletar Reserva", 350.0f, 30, "Constantia", "bold"));
        vBox.getChildren().addAll(Settings.generateTextField("Insira a data: ", "Cambria", 16, date),
                Settings.generateButtonWithShadowAndEvent("Deletar", "Georgia", 16, "white", event -> {
                    deleteAction(date.getText());
                }));
        vBox.setSpacing(27);
        paper.getChildren().add(vBox);
        paper.setSpacing(43);
        centerScreen.getChildren().add(paper);
    }

    private void createBody() {
        VBox vBox = new VBox();
        Paper paper = new Paper(440, 250);
        TextField date = new TextField();
        paper.getChildren().add(paper.generateHeaders("Criar Reserva", 350.0f, 30, "Constantia", "bold"));
        vBox.getChildren().addAll(Settings.generateTextField("Insira a data: ", "Cambria", 16, date),
                Settings.generateButtonWithShadowAndEvent("Criar", "Georgia", 16, "white", event -> {
                    insertAction(date.getText());
                }));
        vBox.setSpacing(27);
        paper.getChildren().add(vBox);
        paper.setSpacing(43);
        centerScreen.getChildren().add(paper);
    }

    private void readBody() {
        VBox vBox = new VBox();
        readPaper = new Paper(440, 250);
        readPaper.getChildren().add(readPaper.generateHeaders("Ver Reserva", 350.0f, 30, "Constantia", "bold"));
        vBox.setSpacing(27);
        readPaper.getChildren().add(vBox);
        readPaper.setSpacing(43);
        centerScreen.getChildren().add(readPaper);
    }

    private void updateBody() {
        VBox vBox = new VBox();
        VBox textfields = new VBox();
        TextField lastDate = new TextField();
        TextField newDate = new TextField();
        Paper paper = new Paper(440, 250);

        paper.getChildren().add(paper.generateHeaders("Atualizar Reserva", 350.0f, 30, "Constantia", "bold"));
        textfields.getChildren().addAll(
                Settings.generateTextField("Insira a antiga data: ", "Cambria", 16, lastDate),
                Settings.generateTextField("Insira a data nova:   ", "Cambria", 16, newDate)
        );

        textfields.setSpacing(10);
        vBox.getChildren().addAll(
                textfields,
                Settings.generateButtonWithShadowAndEvent("Atualizar", "Georgia", 16, "white", event -> {
                    updateAction(newDate.getText(), lastDate.getText());
                }));
        vBox.setSpacing(25);
        paper.getChildren().add(vBox);
        paper.setSpacing(40);
        centerScreen.getChildren().add(paper);
    }


    public void readAction(){
        readBody();
        Connexion connexion = new Connexion(connexionRoute) {
            @Override
            public void action() throws SQLException {
                while (getResultSet().next()){
                    condominumId = getResultSet().getInt("ID");
                }
            }
        };

        connexion.selectWithInnerJoinAndWhere(
                "condomino c",
                "torre t on t.\"ID\" = c.\"ID_torre\"",
                "c.\"ID\"",
                "c.nome = " + "'" + name + "'" + " AND t.apartamento = " + "'" + apartment + "'");


        Connexion connexionReservas = new Connexion(connexionRoute) {
            @Override
            public void action() throws SQLException {
                VBox subTitle = new VBox();
                VBox vBox = new VBox();
                subTitle.getChildren().addAll(Settings.generateLabel("Data das reservas:","Cambria",17),vBox);
                subTitle.setAlignment(Pos.CENTER);
                subTitle.setSpacing(20);
                while (getResultSet().next()){
                    Paper paper = new Paper(110,98);
                    paper.getChildren().add(Settings.generateLabel(getResultSet().getString("data"),"Cambria",16));
                    vBox.getChildren().add(paper);
                }
                vBox.setAlignment(Pos.CENTER);
                vBox.setSpacing(4);
                readPaper.getChildren().remove(1);
                readPaper.getChildren().addAll(subTitle);
            }
        };
        connexionReservas.selectWithWhere("\"Condominio\".public.reserva_salao", "r.data","r","\"ID_condomino\"=" + condominumId);
    }

    private void insertAction(String date) {
        Connexion connexionSelect = new Connexion(connexionRoute) {
            @Override
            public void action() throws SQLException {
                while (getResultSet().next()) {
                    condominumId = getResultSet().getInt("ID");
                }
            }
        };
        connexionSelect.selectWithInnerJoinAndWhere(
                "condomino c",
                "torre t on t.\"ID\" = c.\"ID_torre\"",
                "c.\"ID\"",
                "c.nome = " + "'" + name + "'" + " AND t.apartamento = " + "'" + apartment + "'");

        Connexion connexion = new Connexion(connexionRoute) {
            @Override
            public void action() throws SQLException {
            }
        };
        connexion.insert("reserva_salao", "\"ID_condomino\",data", "(" + condominumId + "," + "'" + date + "')");
    }


    private void deleteAction(String date) {
        Connexion connexionSelect = new Connexion(connexionRoute) {
            @Override
            public void action() throws SQLException {
                while (getResultSet().next()) {
                    if (getResultSet().getString("data").equals(date)) {
                        condominumId = getResultSet().getInt("ID_condomino");
                    }
                }
            }
        };

        getCondominumIdFromReserva(connexionSelect);


    Connexion connexion = new Connexion(connexionRoute) {
        @Override
        public void action() throws SQLException { }
    };
        connexion.deleteWithWhere("\"Condominio\".public.reserva_salao",
                "\"ID_condomino\"="+condominumId +" AND data="+"'"+date +"'");
}

    private void updateAction(String newDate, String lastDate) {
        Connexion connexionSelect = new Connexion(connexionRoute) {
            @Override
            public void action() throws SQLException {
                while (getResultSet().next()) {
                    condominumId = getResultSet().getInt("ID_condomino");
                }
            }
        };
        getCondominumIdFromReserva(connexionSelect);

        Connexion connexion = new Connexion(connexionRoute) {
            @Override
            public void action() throws SQLException {}
        };
        connexion.update("reserva_salao",
                "data= " + "'" + newDate + "'",
                "data= " + "'" + lastDate + "' AND \"ID_condomino\"= " + condominumId);
    }


    private void getCondominumIdFromReserva(Connexion connexion){
        connexion.selectWithInnerJoinAndWhere(
                "reserva_salao r",
                "condomino c on r.\"ID_condomino\" = c.\"ID\"" ,
                "torre t on t.\"ID\" = c.\"ID_torre\"",
                "r.\"ID_condomino\"",
                "c.nome = " + "'" + name + "'" + " AND t.apartamento = " + "'" + apartment + "'");
    }

    @Override
    public Parent getRoot() {
        return root;
    }
}
