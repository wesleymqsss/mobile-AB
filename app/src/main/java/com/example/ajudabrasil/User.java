package com.example.ajudabrasil;

public class User {
    private int id;
    private String username;

    private String password;

    private String tipoCadastro;

    public User() {

    }

    public User(String username, String password, String tipoCadastro){
        this.username = username;
        this.password = password;
        this.tipoCadastro = tipoCadastro;
    }

    public User(int id, String username, String password, String tipoCadastro){
        this.id = id;
        this.username = username;
        this.password = password;
        this.tipoCadastro = tipoCadastro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoCadastro() {
        return tipoCadastro;
    }

    public void setTipoCadastro(String tipoCadastro) {
        this.tipoCadastro = tipoCadastro;
    }

}
