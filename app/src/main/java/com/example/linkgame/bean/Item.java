package com.example.linkgame.bean;

public class Item {
    private int id;
    private boolean eliminated;
    private boolean select;

    public Item(int id, boolean eliminated, boolean select) {
        this.id = id;
        this.eliminated = eliminated;
        this.select = select;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
