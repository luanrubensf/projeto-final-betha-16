package com.luanrubensf.projetoBetha.model;

/**
 *
 * @author Rubens
 */
public class ItemEmprestimo {
    private Long id;
    private Game game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return String.format("{\"id\": \"%s\", \"game\": \"%s\"}", id, game);
    }
}
