package br.univali.poo.karoline.view.screen;

public class ConnexionRoute {
    private String conectionType;
    private String nameOfDataBase;
    private String userName;
    private String password;
    private String url;
    private String sgbdType;

    public ConnexionRoute(String conectionType,String nameOfDataBase,String userName,String password,String sgbdType){
        this.conectionType = conectionType;
        this.nameOfDataBase = nameOfDataBase;
        this.userName = userName;
        this.password = password;
        this.sgbdType = sgbdType;
        this.url = "jdbc:postgresql://"+ conectionType + "/"+
                nameOfDataBase + "?user="+ userName +"&password="+ password +"&ssl=false";
    }

    public String getSgbdType() {
        return sgbdType;
    }

    public String getGetUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }

    public String getConectionType() {
        return conectionType;
    }

    public String getNameOfDataBase() {
        return nameOfDataBase;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }
}
