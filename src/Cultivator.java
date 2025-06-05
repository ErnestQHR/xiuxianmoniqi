package src;

import java.util.*;

public class Cultivator extends Character {
    private CultivationStage stage;
    private CultivationMethod method;
    private int spiritStones;
    private Inventory inventory;
    private List<Achievement> achievements;
    
    public Cultivator(String name) {
        super(name);
        this.stage = CultivationStage.getInitialStage();
        this.method = CultivationMethod.getInitialMethod();
        this.spiritStones = 100;
        this.inventory = new Inventory();
        this.achievements = new ArrayList<>();
    }
    
    @Override
    public void cultivate() {
        if (lifespan <= 0) {
            throw new RuntimeException("寿命已耗尽，无法修炼");
        }
        
        // 计算修炼效率
        double efficiency = stage.getCultivationEfficiency() * method.getCultivationEfficiency();
        
        // 增加修为
        int spiritGain = (int)(10 * efficiency);
        spirit += spiritGain;
        
        // 消耗寿命
        lifespan--;
        
        // 检查是否解锁成就
        checkAchievements();
    }
    
    @Override
    public boolean breakthrough() {
        if (lifespan <= 0) {
            throw new RuntimeException("寿命已耗尽，无法突破");
        }
        
        // 检查修为是否达到要求
        if (spirit < stage.getRequiredSpirit()) {
            return false;
        }
        
        // 计算突破概率
        double successRate = 0.6; // 基础60%成功率
        successRate += method.getBreakthroughBonus();
        
        // 限制最高成功率为95%
        successRate = Math.min(0.95, successRate);
        
        // 决定突破结果
        boolean success = Math.random() < successRate;
        
        if (success) {
            // 突破成功
            stage = stage.next();
            spirit = 0; // 修为清零
            lifespan += 50; // 寿命增加50年
            
            // 解锁成就
            if (stage.getLevel() >= 7) { // 金丹期
                unlockAchievement("百年金丹");
            }
        } else {
            // 突破失败
            spirit = spirit / 2; // 修为减半
            lifespan -= 10; // 寿命减少10年
        }
        
        return success;
    }
    
    @Override
    public String getStatusInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 当前状态 ===\n");
        sb.append("境界: ").append(stage.getName()).append("\n");
        sb.append("修为: ").append(spirit).append("/").append(stage.getRequiredSpirit()).append("\n");
        sb.append("寿命: ").append(lifespan).append("年\n");
        sb.append("灵石: ").append(spiritStones).append("\n");
        sb.append("心法: ").append(method.getName()).append("(效率+").append(method.getCultivationEfficiency()).append("%)\n");
        
        // 添加成就信息
        sb.append("成就: ");
        if (achievements.isEmpty()) {
            sb.append("暂无\n");
        } else {
            sb.append("\n");
            for (Achievement achievement : achievements) {
                sb.append("  - ").append(achievement.getName()).append("\n");
            }
        }
        
        return sb.toString();
    }
    
    public boolean refineElixir(Elixir elixir) {
        if (spiritStones < elixir.getCost()) {
            return false;
        }
        
        spiritStones -= elixir.getCost();
        inventory.addItem(elixir);
        return true;
    }
    
    public void useItem(Item item) {
        if (item instanceof Elixir) {
            Elixir elixir = (Elixir) item;
            elixir.getEffect().apply(this);
            inventory.removeItem(item);
        }
    }
    
    private void checkAchievements() {
        // 检查各种成就条件
        if (stage.getLevel() >= 7 && lifespan >= 50) {
            unlockAchievement("百年金丹");
        }
        
        if (spiritStones >= 10000) {
            unlockAchievement("灵石大亨");
        }
    }
    
    private void unlockAchievement(String achievementId) {
        Achievement achievement = Achievement.getAchievement(achievementId);
        if (achievement != null && !achievements.contains(achievement)) {
            achievements.add(achievement);
            System.out.println("解锁成就：" + achievement.getName());
        }
    }
    
    // Getters and setters
    public CultivationStage getStage() { return stage; }
    public CultivationMethod getMethod() { return method; }
    public int getSpiritStones() { return spiritStones; }
    public Inventory getInventory() { return inventory; }
    public List<Achievement> getAchievements() { return achievements; }
    
    public void setStage(CultivationStage stage) { this.stage = stage; }
    public void setMethod(CultivationMethod method) { this.method = method; }
    public void addSpiritStones(int amount) { this.spiritStones += amount; }
    public void addSpirit(int amount) { this.spirit += amount; }
    public void addLifespan(int years) { this.lifespan += years; }
    
    public void fullRestore() {
        this.spirit = stage.getRequiredSpirit();
        this.lifespan = 100 + (stage.getLevel() * 50);
    }
} 