package src;

import java.util.*;

public class Achievement {
    private static final Map<String, Achievement> ACHIEVEMENTS = new LinkedHashMap<>();
    
    static {
        // 初始化所有成就
        ACHIEVEMENTS.put("百年金丹", new Achievement("百年金丹", "在100年内突破至金丹期"));
        ACHIEVEMENTS.put("灵石大亨", new Achievement("灵石大亨", "累计获得10000灵石"));
        ACHIEVEMENTS.put("劫后余生", new Achievement("劫后余生", "突破失败后存活"));
        ACHIEVEMENTS.put("化神之资", new Achievement("化神之资", "突破至化神期"));
        ACHIEVEMENTS.put("丹道大师", new Achievement("丹道大师", "炼制100颗丹药"));
    }
    
    private final String id;
    private final String name;
    private final String description;
    
    private Achievement(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static Achievement getAchievement(String id) {
        return ACHIEVEMENTS.get(id);
    }
    
    public static List<Achievement> getAllAchievements() {
        return new ArrayList<>(ACHIEVEMENTS.values());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Achievement that = (Achievement) o;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
} 