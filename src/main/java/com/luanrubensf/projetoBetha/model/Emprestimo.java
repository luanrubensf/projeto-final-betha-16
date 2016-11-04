package com.luanrubensf.projetoBetha.model;

import com.luanrubensf.projetoBetha.utils.Utils;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<ItemEmprestimo> itens;

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

    public List<ItemEmprestimo> getItens() {
        return itens;
    }

    public void setItens(List<ItemEmprestimo> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return String.format("{{\"id\":\"%s\", \"destino\": \"%s\", \"data\": \"%s\", \"dataDevolucao\": \"%s\"," + 
                "\"observacao\": \"%s\", \"itens\": \"%s\"}", 
                id, destino, Utils.nullString(data), Utils.nullString(dataDevolucao), observacao, itens);
    }
}