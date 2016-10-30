package com.luanrubensf.projetoBetha.model;

/**
 *
 * @author Rubens
 */
public class Game {

    private Long id;
    private String name;
    private String descricao;
    private Integer anoLancamento;
    private boolean finalizado;

    private Categoria categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                + "\"finalizado\": %s,\"ano\": %s, \"categoria\": %s}",
                id, name, descricao, finalizado, anoLancamento, categoria);
    }
}