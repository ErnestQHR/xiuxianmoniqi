package com.xiuxianmoniqi.model.cultivation;

import java.util.HashMap;
import java.util.Map;

public class CultivationMethod {
    private static final Map<String, CultivationMethod> methods = new HashMap<>();
    
    private final String name;
    private final double cultivationEfficiency;
    private final double breakthroughBonus;
    private final int requiredStage;
    private final String description;
    
    static {
        // 基础心法
        methods.put("基础吐纳术", new CultivationMethod(
            "基础吐纳术",
            1.0,
            0.0,
            1,
            "最基础的修炼心法，适合初学者"
        ));
        
        // 进阶心法
        methods.put("五行聚气诀", new CultivationMethod(
            "五行聚气诀",
            1.5,
            0.1,
            3,
            "通过五行相生相克之理，提升修炼效率"
        ));
        
        // 高级心法
        methods.put("太玄经", new CultivationMethod(
            "太玄经",
            2.0,
            0.2,
            5,
            "上古流传的修炼心法，蕴含天地至理"
        ));
        
        // 顶级心法
        methods.put("混沌大道经", new CultivationMethod(
            "混沌大道经",
            3.0,
            0.3,
            7,
            "传说中的无上心法，可参悟混沌大道"
        ));
    }
    
    private CultivationMethod(String name, double cultivationEfficiency, double breakthroughBonus, 
                            int requiredStage, String description) {
        this.name = name;
        this.cultivationEfficiency = cultivationEfficiency;
        this.breakthroughBonus = breakthroughBonus;
        this.requiredStage = requiredStage;
        this.description = description;
    }
    
    public static CultivationMethod getInitialMethod() {
        return methods.get("基础吐纳术");
    }
    
    public static CultivationMethod getMethod(String name) {
        return methods.get(name);
    }
    
    public static Map<String, CultivationMethod> getAllMethods() {
        return new HashMap<>(methods);
    }
    
    public String getName() {
        return name;
    }
    
    public double getCultivationEfficiency() {
        return cultivationEfficiency;
    }
    
    public double getBreakthroughBonus() {
        return breakthroughBonus;
    }
    
    public int getRequiredStage() {
        return requiredStage;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s (修炼效率+%.0f%%, 突破加成+%.0f%%)",
            name, description,
            (cultivationEfficiency - 1) * 100,
            breakthroughBonus * 100);
    }
} 