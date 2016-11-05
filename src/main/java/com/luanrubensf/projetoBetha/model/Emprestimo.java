package com.luanrubensf.projetoBetha.model;

import com.luanrubensf.projetoBetha.utils.Parseable;
import com.luanrubensf.projetoBetha.utils.Utils;
import java.time.LocalDateTime;
import java.util.Map;

/**
 *
 * @author Rubens
 */
public class Emprestimo implements Parseable {
    
    private Long id;
    private String destino;
    private LocalDateTime emissao;
    private LocalDateTime devolucao;
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

    public LocalDateTime getEmissao() {
        return emissao;
    }

    public void setEmissao(LocalDateTime emissao) {
        this.emissao = emissao;
    }

    public LocalDateTime getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(LocalDateTime devolucao) {
        this.devolucao = devolucao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return String.format("{{\"id\":\"%s\", \"destino\": \"%s\", \"data\": \"%s\", \"dataDevolucao\": \"%s\"," + 
                "\"observacao\": \"%s\", \"itens\": \"%s\"}", 
                id, destino, Utils.nullString(emissao), Utils.nullString(devolucao), observacao, game);
    }
    
    @Override
    public void parse(Map<String, String> dados) {
        id = Utils.parseLong(dados.get("id"));
        destino = dados.get("destino");
        observacao = dados.get("observacao");
        emissao = Utils.parseDate(dados.get("emissao"), "dd/MM/yyyy HH:mm:ss");
        devolucao = Utils.parseDate(dados.get("aprovacao"), "dd/MM/yyyy HH:mm:ss");
        game = Utils.isEmpty(dados.get("game")) ? null : new Game(Utils.parseLong(dados.get("game")));
    }
}