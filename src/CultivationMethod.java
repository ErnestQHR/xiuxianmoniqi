package src;

import java.util.*;

public class CultivationMethod {
    private static final Map<String, CultivationMethod> METHODS = new LinkedHashMap<>();
    
    static {
        // 初始化所有心法
        METHODS.put("基础吐纳术", new CultivationMethod("基础吐纳术", 1.0, 0.0));
        METHODS.put("九转玄功", new CultivationMethod("九转玄功", 1.2, 0.1));
        METHODS.put("太虚剑典", new CultivationMethod("太虚剑典", 1.3, 0.15));
        METHODS.put("混元一气功", new CultivationMethod("混元一气功", 1.4, 0.2));
        METHODS.put("太上忘情诀", new CultivationMethod("太上忘情诀", 1.5, 0.25));
    }
    
    private final String name;
    private final double cultivationEfficiency;
    private final double breakthroughBonus;
    
    private CultivationMethod(String name, double cultivationEfficiency, double breakthroughBonus) {
        this.name = name;
        this.cultivationEfficiency = cultivationEfficiency;
        this.breakthroughBonus = breakthroughBonus;
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
    
    public static CultivationMethod getInitialMethod() {
        return METHODS.get("基础吐纳术");
    }
    
    public static CultivationMethod getMethod(String name) {
        return METHODS.get(name);
    }
    
    public static List<String> getAllMethodNames() {
        return new ArrayList<>(METHODS.keySet());
    }
} 