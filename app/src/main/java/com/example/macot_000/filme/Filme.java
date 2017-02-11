package com.example.macot_000.filme;

/**
 * Created by maiko on 07/05/16.
 */
public class Filme {

    private Integer id;
    private String nome;
    private String genero;

    public Filme(Integer id, String nome, String genero) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
    }

    public Filme(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.nome + " : " + this.genero;
    }
}
