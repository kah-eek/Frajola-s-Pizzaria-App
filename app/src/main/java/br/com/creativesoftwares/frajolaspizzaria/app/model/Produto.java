package br.com.creativesoftwares.frajolaspizzaria.app.model;

import android.content.SharedPreferences;

import java.io.Serializable;

import br.com.creativesoftwares.frajolaspizzaria.app.dao.ProdutoDAO;

/**
 * Created by 16254840 on 28/11/2017.
 */

public class Produto {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private int id;
    private boolean avaliado;
    private String imagem;
    private String descricao;
    private String titulo;
    private String categoria;
    private String subcategoria;
    private double preco;
    private double avaliacao;

    // DEFAULT CONSTRUCTOR
    public Produto(SharedPreferences preferences){
        this.sharedPreferences = sharedPreferences;
        this.editor = preferences.edit();
    }

    // PERSONALIZED CONSTRUCTOR
    public Produto(SharedPreferences preferences, int id, String imagem, String descricao, String titulo, String categoria, String subcategoria, double preco, double classificacao){
        this.sharedPreferences = preferences;
        this.editor = preferences.edit();
        this.id = id;
        this.imagem = imagem;
        this.descricao = descricao;
        this.titulo = titulo;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.preco = preco;
        this.avaliacao= classificacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {

        this.avaliacao = avaliacao;

        // SET LIKE "ITEM WAS EVALUATED" INTO SharedPreferences
        this.editor.putBoolean(String.valueOf(this.id), true);
        this.editor.commit();
    }

    public boolean getAvaliado(){
        return sharedPreferences.getBoolean(String.valueOf(this.id) ,false);
    }
}
