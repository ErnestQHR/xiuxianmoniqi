package com.xiuxianmoniqi.model.character;

import com.xiuxianmoniqi.model.cultivation.CultivationStage;
import com.xiuxianmoniqi.model.cultivation.CultivationMethod;

public abstract class Character {
    protected String name;
    protected int spirit;
    protected int lifespan;
    protected int spiritStones;
    protected CultivationStage stage;
    protected CultivationMethod method;

    public Character(String name) {
        this.name = name;
        this.spirit = 0;
        this.lifespan = 100;
        this.spiritStones = 100;
    }

    public void addSpirit(int amount) {
        this.spirit += amount;
    }

    public void addLifespan(int years) {
        this.lifespan += years;
    }

    public void addSpiritStones(int amount) {
        this.spiritStones += amount;
    }

    public boolean isAlive() {
        return lifespan > 0;
    }

    // Getters
    public String getName() { return name; }
    public int getSpirit() { return spirit; }
    public int getLifespan() { return lifespan; }
    public int getSpiritStones() { return spiritStones; }
    public CultivationStage getStage() { return stage; }
    public CultivationMethod getMethod() { return method; }

    // Setters
    public void setStage(CultivationStage stage) { this.stage = stage; }
    public void setMethod(CultivationMethod method) { this.method = method; }

    public abstract void cultivate();
    public abstract boolean breakthrough();
    public abstract String getStatusInfo();
} 