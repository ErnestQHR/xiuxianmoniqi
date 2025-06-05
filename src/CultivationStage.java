package src;

import java.util.*;

public class CultivationStage {
    private static final Map<String, CultivationStage> STAGES = new LinkedHashMap<>();
    
    static {
        // 初始化所有境界
        STAGES.put("凡人", new CultivationStage("凡人", 0, 0, 1.0));
        STAGES.put("练气初期", new CultivationStage("练气初期", 100, 1, 1.2));
        STAGES.put("练气中期", new CultivationStage("练气中期", 200, 2, 1.4));
        STAGES.put("练气后期", new CultivationStage("练气后期", 300, 3, 1.6));
        STAGES.put("筑基初期", new CultivationStage("筑基初期", 400, 4, 1.8));
        STAGES.put("筑基中期", new CultivationStage("筑基中期", 500, 5, 2.0));
        STAGES.put("筑基后期", new CultivationStage("筑基后期", 600, 6, 2.2));
        STAGES.put("金丹初期", new CultivationStage("金丹初期", 800, 7, 2.5));
        STAGES.put("金丹中期", new CultivationStage("金丹中期", 1000, 8, 2.8));
        STAGES.put("金丹后期", new CultivationStage("金丹后期", 1200, 9, 3.1));
        STAGES.put("元婴初期", new CultivationStage("元婴初期", 1500, 10, 3.5));
        STAGES.put("元婴中期", new CultivationStage("元婴中期", 1800, 11, 3.9));
        STAGES.put("元婴后期", new CultivationStage("元婴后期", 2100, 12, 4.3));
        STAGES.put("化神初期", new CultivationStage("化神初期", 2500, 13, 4.8));
        STAGES.put("化神中期", new CultivationStage("化神中期", 3000, 14, 5.3));
        STAGES.put("化神后期", new CultivationStage("化神后期", 3500, 15, 5.8));
    }
    
    private final String name;
    private final int requiredSpirit;
    private final int level;
    private final double cultivationEfficiency;
    
    private CultivationStage(String name, int requiredSpirit, int level, double cultivationEfficiency) {
        this.name = name;
        this.requiredSpirit = requiredSpirit;
        this.level = level;
        this.cultivationEfficiency = cultivationEfficiency;
    }
    
    public String getName() {
        return name;
    }
    
    public int getRequiredSpirit() {
        return requiredSpirit;
    }
    
    public int getLevel() {
        return level;
    }
    
    public double getCultivationEfficiency() {
        return cultivationEfficiency;
    }
    
    public CultivationStage next() {
        List<String> stages = new ArrayList<>(STAGES.keySet());
        int currentIndex = stages.indexOf(name);
        if (currentIndex < stages.size() - 1) {
            return STAGES.get(stages.get(currentIndex + 1));
        }
        return this; // 已经是最高境界
    }
    
    public static CultivationStage getInitialStage() {
        return STAGES.get("凡人");
    }
    
    public static CultivationStage getStage(String name) {
        return STAGES.get(name);
    }
} 