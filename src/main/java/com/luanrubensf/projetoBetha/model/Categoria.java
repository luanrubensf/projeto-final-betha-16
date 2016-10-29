
package com.luanrubensf.projetoBetha.model;

import com.luanrubensf.projetoBetha.utils.Parseable;
import com.luanrubensf.projetoBetha.utils.Utils;
import java.util.Map;

/**
 *
 * @author Rubens
 */
public class Categoria implements Parseable {
 
    private Long id;

    private String descricao;

    public Long getId() {        
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%s, \"descricao\":\"%s\"}", id, descricao);
    }
    
    @Override
    public void parse(Map<String, String> dados) {
        id = Utils.parseLong(dados.get("id"));
        descricao = dados.get("descricao");
    }   
}
