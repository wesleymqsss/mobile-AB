package com.example.ajudabrasil;

public class Doacao {
    private int id;
    private int userIdDoador;
    private int ongIdRecebedora;
    private double valor;
    private String data;
    private String ongNomeRecebedora;



    // Construtor para novas doações (sem ID ainda)
    public Doacao(int userIdDoador, int ongIdRecebedora, double valor, String data, String ongNomeRecebedora) {
        this.userIdDoador = userIdDoador;
        this.ongIdRecebedora = ongIdRecebedora;
        this.valor = valor;
        this.data = data;
        this.ongNomeRecebedora = ongNomeRecebedora;
    }

    // Construtor completo (ex: ao ler do banco)
    public Doacao(int id, int userIdDoador, int ongIdRecebedora, double valor, String data, String ongNomeRecebedora) {
        this.id = id;
        this.userIdDoador = userIdDoador;
        this.ongIdRecebedora = ongIdRecebedora;
        this.valor = valor;
        this.data = data;
        this.ongNomeRecebedora = ongNomeRecebedora;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserIdDoador() {
        return userIdDoador;
    }

    public int getOngIdRecebedora() {
        return ongIdRecebedora;
    }

    public double getValor() {
        return valor;
    }

    public String getData() {
        return data;
    }

    // Setters (opcional, mas bom ter)
    public void setId(int id) {
        this.id = id;
    }

    public void setUserIdDoador(int userIdDoador) {
        this.userIdDoador = userIdDoador;
    }

    public void setOngIdRecebedora(int ongIdRecebedora) {
        this.ongIdRecebedora = ongIdRecebedora;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOngNomeRecebedora() {
        return ongNomeRecebedora;
    }

    public void setOngNomeRecebedora(String ongNomeRecebedora) {
        this.ongNomeRecebedora = ongNomeRecebedora;
    }
}
