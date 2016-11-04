package com.luanrubensf.projetoBetha.model;

import com.luanrubensf.projetoBetha.utils.Utils;
import java.time.LocalDateTime;

/**
 *
 * @author Rubens
 */
public class Emprestimo {
    
    private Long id;
    private String destino;
    private LocalDateTime data;
    private LocalDateTime dataDevolucao;
    private String observacao;
    private Game game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Game getItens() {
        return game;
    }

    public void setItens(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return String.format("{{\"id\":\"%s\", \"destino\": \"%s\", \"data\": \"%s\", \"dataDevolucao\": \"%s\"," + 
                "\"observacao\": \"%s\", \"itens\": \"%s\"}", 
                id, destino, Utils.nullString(data), Utils.nullString(dataDevolucao), observacao, game);
    }
}