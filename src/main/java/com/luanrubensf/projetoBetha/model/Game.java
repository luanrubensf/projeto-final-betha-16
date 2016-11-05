package com.luanrubensf.projetoBetha.model;

import com.luanrubensf.projetoBetha.utils.Parseable;
import com.luanrubensf.projetoBetha.utils.Utils;
import java.util.Map;

/**
 *
 * @author Rubens
 */
public class Game implements Parseable {

    private Long id;
    private String nome;
    private String descricao;
    private Integer anoLancamento;
    private boolean finalizado;

    private Categoria categoria;
    
    public Game(){}
    
    public Game(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return String.format("{\"id\": %s,\"nome\": \"%s\",\"descricao\":\"%s\","
                + "\"finalizado\": %s,\"ano\": %s, \"categoria\": %s }",
                id, nome, descricao, finalizado, anoLancamento, categoria);
    }

    @Override
    public void parse(Map<String, String> dados) {
        id = Utils.parseLong(dados.get("id"));
        nome = dados.get("nome");
        descricao = dados.get("descricao");
        anoLancamento = Utils.parseInteger(dados.get("ano"));
        finalizado = Utils.parseBoolean(dados.get("finalizado"));
        categoria = new Categoria(Utils.parseLong(dados.get("categoria")));
    }
}