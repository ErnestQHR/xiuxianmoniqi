package com.xiuxianmoniqi.model.item;

import com.xiuxianmoniqi.model.character.Cultivator;

public class SpiritIncreaseEffect implements Effect {
    private final int amount;
    
    public SpiritIncreaseEffect(int amount) {
        this.amount = amount;
    }
    
    @Override
    public void apply(Cultivator cultivator) {
        cultivator.addSpirit(amount);
    }
} 