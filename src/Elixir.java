package src;

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