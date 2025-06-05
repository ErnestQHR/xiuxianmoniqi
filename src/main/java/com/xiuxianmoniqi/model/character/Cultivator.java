package com.xiuxianmoniqi.model.character;

import com.xiuxianmoniqi.model.cultivation.CultivationStage;
import com.xiuxianmoniqi.model.cultivation.CultivationMethod;
import com.xiuxianmoniqi.model.item.Inventory;
import com.xiuxianmoniqi.model.item.Item;
import com.xiuxianmoniqi.model.item.Elixir;
import com.xiuxianmoniqi.model.achievement.Achievement;
import java.util.*;

public class Cultivator extends Character {
    private Inventory inventory;
    private List<Achievement> achievements;
    private boolean isDead = false;
    private int totalCultivationTime = 0;
    private int totalBreakthroughs = 0;
    private int highestStage = 1;
    
    public Cultivator(String name) {
        super(name);
        this.stage = CultivationStage.getInitialStage();
        this.method = CultivationMethod.getInitialMethod();
        this.inventory = new Inventory();
        this.achievements = new ArrayList<>();
    }
    
    @Override
    public void cultivate() {
        if (isDead) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║            修仙之路已终结            ║");
            System.out.println("║          你已陨落，无法继续修炼      ║");
            System.out.println("╚══════════════════════════════════════╝");
            return;
        }
        
        if (!isAlive()) {
            die();
            return;
        }
        
        // 计算修炼效率
        double efficiency = stage.getCultivationEfficiency() * method.getCultivationEfficiency();
        
        // 增加修为
        int spiritGain = (int)(10 * efficiency);
        addSpirit(spiritGain);
        
        // 消耗寿命
        lifespan--;
        totalCultivationTime++;
        
        // 检查是否解锁成就
        checkAchievements();
        
        // 触发随机事件
        if (Math.random() < 0.2) { // 20%概率触发事件
            triggerRandomEvent();
        }
    }
    
    private void die() {
        isDead = true;
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║            修仙之路终结              ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ 道号: " + getName());
        System.out.println("║ 最终境界: " + stage.getName());
        System.out.println("║ 修炼时长: " + totalCultivationTime + "年");
        System.out.println("║ 突破次数: " + totalBreakthroughs + "次");
        System.out.println("║ 最高境界: " + CultivationStage.getStageByLevel(highestStage).getName());
        System.out.println("║ 获得成就: " + achievements.size() + "个");
        System.out.println("║ 剩余灵石: " + spiritStones);
        System.out.println("║ 死亡原因: 寿命耗尽");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║          修仙失败，请重新开始        ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    private void triggerRandomEvent() {
        double random = Math.random();
        if (random < 0.1) { // 10%概率获得奇遇
            int spiritGain = (int)(Math.random() * 200) + 100;
            addSpirit(spiritGain);
            System.out.println("【奇遇】遇到天地异象，修为大增！获得" + spiritGain + "点修为");
        } else if (random < 0.2) { // 10%概率遇到危险
            int damage = (int)(Math.random() * 10) + 5;
            lifespan -= damage;
            System.out.println("【危险】修炼走火入魔，损失" + damage + "年寿命！");
        }
    }
    
    @Override
    public boolean breakthrough() {
        if (isDead) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║            修仙之路已终结            ║");
            System.out.println("║          你已陨落，无法继续突破      ║");
            System.out.println("╚══════════════════════════════════════╝");
            return false;
        }
        
        if (!isAlive()) {
            die();
            return false;
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
            addLifespan(50); // 寿命增加50年
            totalBreakthroughs++;
            highestStage = Math.max(highestStage, stage.getLevel());
            
            // 解锁成就
            if (stage.getLevel() >= 7) { // 金丹期
                unlockAchievement("百年金丹");
            }
        } else {
            // 突破失败
            spirit = spirit / 2; // 修为减半
            lifespan -= 10; // 寿命减少10年
            unlockAchievement("劫后余生");
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
        sb.append("心法: ").append(method.getName())
          .append("(效率+").append((method.getCultivationEfficiency() - 1) * 100).append("%)\n");
        sb.append("修炼时长: ").append(totalCultivationTime).append("年\n");
        sb.append("突破次数: ").append(totalBreakthroughs).append("次\n");
        
        // 添加成就信息
        sb.append("成就: ");
        if (achievements.isEmpty()) {
            sb.append("暂无\n");
        } else {
            sb.append("\n");
            for (Achievement achievement : achievements) {
                sb.append("  - ").append(achievement.getName())
                  .append(": ").append(achievement.getDescription()).append("\n");
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
        
        if (stage.getLevel() >= 13) { // 化神期
            unlockAchievement("化神之资");
        }
    }
    
    private void unlockAchievement(String achievementId) {
        Achievement achievement = Achievement.getAchievement(achievementId);
        if (achievement != null && !achievements.contains(achievement)) {
            achievements.add(achievement);
            System.out.println("解锁成就：" + achievement.getName());
        }
    }
    
    public void explore() {
        if (isDead) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║            修仙之路已终结            ║");
            System.out.println("║          你已陨落，无法继续探索      ║");
            System.out.println("╚══════════════════════════════════════╝");
            return;
        }
        
        if (!isAlive()) {
            die();
            return;
        }
        
        // 消耗寿命
        lifespan--;
        
        // 随机事件
        double random = Math.random();
        if (random < 0.3) { // 30%概率获得灵石
            int spiritStonesGain = (int)(Math.random() * 1000) + 100;
            spiritStones += spiritStonesGain;
            System.out.println("探索发现灵石矿脉，获得" + spiritStonesGain + "灵石！");
        } else if (random < 0.5) { // 20%概率获得修为
            int spiritGain = (int)(Math.random() * 100) + 50;
            addSpirit(spiritGain);
            System.out.println("探索发现灵脉，获得" + spiritGain + "点修为！");
        } else if (random < 0.7) { // 20%概率遇到危险
            int damage = (int)(Math.random() * 20) + 10;
            lifespan -= damage;
            System.out.println("探索遇到危险，损失" + damage + "年寿命！");
        } else { // 30%概率无事发生
            System.out.println("探索一无所获...");
        }
        
        // 检查是否解锁成就
        checkAchievements();
    }
    
    public boolean learnMethod(String methodName) {
        CultivationMethod newMethod = CultivationMethod.getMethod(methodName);
        if (newMethod == null) {
            return false;
        }
        
        // 检查境界要求
        if (stage.getLevel() < newMethod.getRequiredStage()) {
            return false;
        }
        
        // 检查灵石消耗
        int cost = newMethod.getRequiredStage() * 1000;
        if (spiritStones < cost) {
            return false;
        }
        
        // 消耗灵石并学习心法
        spiritStones -= cost;
        method = newMethod;
        
        // 解锁成就
        if (newMethod.getCultivationEfficiency() >= 2.0) {
            unlockAchievement("心法大师");
        }
        
        return true;
    }
    
    public String getAvailableMethods() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 可学习的心法 ===\n");
        
        for (CultivationMethod method : CultivationMethod.getAllMethods().values()) {
            if (method.getRequiredStage() <= stage.getLevel()) {
                int cost = method.getRequiredStage() * 1000;
                sb.append(String.format("%s (消耗%d灵石)\n", method, cost));
            }
        }
        
        return sb.toString();
    }
    
    public boolean isDead() {
        return isDead;
    }
    
    // Getters
    public Inventory getInventory() { return inventory; }
    public List<Achievement> getAchievements() { return achievements; }
} 