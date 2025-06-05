package src;

import java.util.*;

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