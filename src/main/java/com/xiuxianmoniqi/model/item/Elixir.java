package com.xiuxianmoniqi.model.item;

import com.xiuxianmoniqi.model.character.Cultivator;

public class Elixir extends Item {
    private final int cost;
    private final Effect effect;
    
    public Elixir(String name, String description, int cost, Effect effect) {
        super(name, description);
        this.cost = cost;
        this.effect = effect;
    }
    
    public int getCost() {
        return cost;
    }
    
    public Effect getEffect() {
        return effect;
    }
    
    public static Elixir createJujiDan() {
        return new Elixir(
            "聚气丹",
            "增加100点修为",
            100,
            new SpiritIncreaseEffect(100)
        );
    }
    
    public static Elixir createZhujiDan() {
        return new Elixir(
            "筑基丹",
            "增加500点修为",
            500,
            new SpiritIncreaseEffect(500)
        );
    }
    
    public static Elixir createJindanDan() {
        return new Elixir(
            "金丹丹",
            "增加2000点修为",
            2000,
            new SpiritIncreaseEffect(2000)
        );
    }
    
    @Override
    public String toString() {
        return name + " - " + description + " (价格: " + cost + "灵石)";
    }
} 