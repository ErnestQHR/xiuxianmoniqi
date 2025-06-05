package src;

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