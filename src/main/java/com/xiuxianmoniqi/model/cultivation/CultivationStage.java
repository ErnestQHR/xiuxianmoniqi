package com.xiuxianmoniqi.model.cultivation;

import java.util.*;

public class CultivationStage {
    private static final Map<Integer, CultivationStage> stages = new HashMap<>();
    
    static {
        // 练气期
        stages.put(1, new CultivationStage("练气初期", 1, 100, 1.0));
        stages.put(2, new CultivationStage("练气中期", 2, 300, 1.1));
        stages.put(3, new CultivationStage("练气后期", 3, 600, 1.2));
        
        // 筑基期
        stages.put(4, new CultivationStage("筑基初期", 4, 1000, 1.3));
        stages.put(5, new CultivationStage("筑基中期", 5, 2000, 1.4));
        stages.put(6, new CultivationStage("筑基后期", 6, 4000, 1.5));
        
        // 金丹期
        stages.put(7, new CultivationStage("金丹初期", 7, 8000, 1.6));
        stages.put(8, new CultivationStage("金丹中期", 8, 16000, 1.7));
        stages.put(9, new CultivationStage("金丹后期", 9, 32000, 1.8));
        
        // 元婴期
        stages.put(10, new CultivationStage("元婴初期", 10, 64000, 1.9));
        stages.put(11, new CultivationStage("元婴中期", 11, 128000, 2.0));
        stages.put(12, new CultivationStage("元婴后期", 12, 256000, 2.1));
        
        // 化神期
        stages.put(13, new CultivationStage("化神初期", 13, 512000, 2.2));
        stages.put(14, new CultivationStage("化神中期", 14, 1024000, 2.3));
        stages.put(15, new CultivationStage("化神后期", 15, 2048000, 2.4));
    }
    
    private final String name;
    private final int level;
    private final int requiredSpirit;
    private final double cultivationEfficiency;
    
    private CultivationStage(String name, int level, int requiredSpirit, double cultivationEfficiency) {
        this.name = name;
        this.level = level;
        this.requiredSpirit = requiredSpirit;
        this.cultivationEfficiency = cultivationEfficiency;
    }
    
    public static CultivationStage getInitialStage() {
        return stages.get(1);
    }
    
    public static CultivationStage getStageByLevel(int level) {
        return stages.get(level);
    }
    
    public CultivationStage next() {
        return stages.get(level + 1);
    }
    
    public String getName() {
        return name;
    }
    
    public int getLevel() {
        return level;
    }
    
    public int getRequiredSpirit() {
        return requiredSpirit;
    }
    
    public double getCultivationEfficiency() {
        return cultivationEfficiency;
    }
    
    @Override
    public String toString() {
        return name;
    }
} 