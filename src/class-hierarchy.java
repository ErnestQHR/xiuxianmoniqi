import java.util.*;
import java.io.*;

// 基础角色类
public abstract class Character {
    protected String name;
    protected int lifespan;
    protected int spirit; // 修为值
    protected int stones; // 灵石
    
    public Character(String name) {
        this.name = name;
        this.lifespan = 100; // 初始寿命100年
        this.spirit = 0;
        this.stones = 100; // 初始灵石
    }
    
    // 获取基本属性的方法
    public String getName() { return name; }
    public int getLifespan() { return lifespan; }
    public int getSpirit() { return spirit; }
    public int getStones() { return stones; }
    
    // 设置基本属性的方法
    public void setName(String name) { this.name = name; }
    public void setLifespan(int lifespan) { this.lifespan = lifespan; }
    public void setSpirit(int spirit) { this.spirit = spirit; }
    public void setStones(int stones) { this.stones = stones; }
    
    // 抽象方法，子类必须实现
    public abstract void cultivate(); // 修炼方法
    public abstract boolean breakthrough(); // 突破方法
    public abstract String getStatusInfo(); // 获取状态信息

    public Achievement getRealm() {
    }
}

// 修仙者类，继承自Character
public class Cultivator extends Character {
    private String name;
    private CultivationStage stage;
    private CultivationMethod method;
    private int spirit;
    private int lifespan;
    private int spiritStones;
    private Inventory inventory;
    private List<Achievement> achievements;
    
    public Cultivator(String name) {
        super(name);
        this.name = name;
        this.stage = CultivationStage.getInitialStage();
        this.method = CultivationMethod.getInitialMethod();
        this.spirit = 0;
        this.lifespan = 100;
        this.spiritStones = 100;
        this.inventory = new Inventory();
        this.achievements = new ArrayList<>();
    }
    
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
    
    public boolean attemptBreakthrough() {
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
    public String getName() { return name; }
    public CultivationStage getStage() { return stage; }
    public CultivationMethod getMethod() { return method; }
    public int getSpirit() { return spirit; }
    public int getLifespan() { return lifespan; }
    public int getSpiritStones() { return spiritStones; }
    public Inventory getInventory() { return inventory; }
    public List<Achievement> getAchievements() { return achievements; }
    
    public void setStage(CultivationStage stage) { this.stage = stage; }
    public void setMethod(CultivationMethod method) { this.method = method; }
    public void setSpirit(int spirit) { this.spirit = spirit; }
    public void setLifespan(int lifespan) { this.lifespan = lifespan; }
    public void addSpiritStones(int amount) { this.spiritStones += amount; }
    public void addSpirit(int amount) { this.spirit += amount; }
    public void addLifespan(int years) { this.lifespan += years; }
    
    public void fullRestore() {
        this.spirit = stage.getRequiredSpirit();
        this.lifespan = 100 + (stage.getLevel() * 50);
    }
}

// 高级修仙者，继承自Cultivator，拥有特殊能力
public class AdvancedCultivator extends Cultivator {
    private List<SpecialAbility> abilities; // 特殊能力列表
    private int energyPoints; // 能量点数，用于释放特殊能力
    
    public AdvancedCultivator(String name) {
        super(name);
        this.abilities = new ArrayList<>();
        this.energyPoints = 0;
    }
    
    // 当突破到金丹期及以上时，可以解锁特殊能力
    public void unlockSpecialAbility(SpecialAbility ability) {
        if (getStage().ordinal() >= CultivationStage.GOLDEN_CORE_EARLY.ordinal()) {
            abilities.add(ability);
        } else {
            throw new InsufficientCultivationException("必须达到金丹期才能解锁特殊能力");
        }
    }
    
    // 使用特殊能力
    public void useSpecialAbility(SpecialAbility ability) {
        if (!abilities.contains(ability)) {
            throw new AbilityNotFoundException("未解锁该特殊能力");
        }
        
        if (energyPoints < ability.getEnergyCost()) {
            throw new InsufficientEnergyException("能量点数不足");
        }
        
        energyPoints -= ability.getEnergyCost();
        ability.activate(this);
    }
    
    @Override
    public void cultivate() {
        super.cultivate();
        // 高级修仙者在修炼时额外获得能量点数
        energyPoints += 1;
    }
    
    @Override
    public String getStatusInfo() {
        StringBuilder sb = new StringBuilder(super.getStatusInfo());
        
        // 添加特殊能力信息
        sb.append("能量点数: ").append(energyPoints).append("\n");
        sb.append("特殊能力: ");
        if (abilities.isEmpty()) {
            sb.append("暂无\n");
        } else {
            sb.append("\n");
            for (SpecialAbility ability : abilities) {
                sb.append("  - ").append(ability.getName())
                  .append("(消耗: ").append(ability.getEnergyCost()).append(")\n");
            }
        }
        
        return sb.toString();
    }
    
    // 高级修仙者特有的方法
    public int getEnergyPoints() { return energyPoints; }
    public void setEnergyPoints(int energyPoints) { this.energyPoints = energyPoints; }
    
    public List<SpecialAbility> getAbilities() { return abilities; }
}

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

public abstract class Item {
    protected String name;
    protected String description;
    
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public abstract String toString();
}

public class Elixir extends Item {
    private int cost;
    private ElixirEffect effect;
    
    public Elixir(String name, int cost, ElixirEffect effect) {
        super(name, "丹药");
        this.cost = cost;
        this.effect = effect;
    }
    
    public int getCost() {
        return cost;
    }
    
    public ElixirEffect getEffect() {
        return effect;
    }
    
    @Override
    public String toString() {
        return name + " - " + description + " (价格: " + cost + "灵石)";
    }
}

public interface ElixirEffect {
    void apply(Cultivator cultivator);
}

public class SpiritIncreaseEffect implements ElixirEffect {
    private int amount;
    
    public SpiritIncreaseEffect(int amount) {
        this.amount = amount;
    }
    
    @Override
    public void apply(Cultivator cultivator) {
        cultivator.addSpirit(amount);
        System.out.println("修为增加" + amount + "点");
    }
}

public class Inventory {
    private List<Item> items;
    
    public Inventory() {
        this.items = new ArrayList<>();
    }
    
    public void addItem(Item item) {
        items.add(item);
    }
    
    public void removeItem(Item item) {
        items.remove(item);
    }
    
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
    
    public int getItemCount() {
        return items.size();
    }
    
    public String toString() {
        if (items.isEmpty()) {
            return "背包为空";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== 背包物品 ===\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append(i + 1).append(". ").append(items.get(i)).append("\n");
        }
        return sb.toString();
    }
}
