package src;

public abstract class Character {
    protected String name;
    protected int spirit;
    protected int lifespan;
    
    public Character(String name) {
        this.name = name;
        this.spirit = 0;
        this.lifespan = 100;
    }
    
    public abstract void cultivate();
    public abstract boolean breakthrough();
    public abstract String getStatusInfo();
    
    public String getName() {
        return name;
    }
    
    public int getSpirit() {
        return spirit;
    }
    
    public int getLifespan() {
        return lifespan;
    }
    
    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }
    
    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }
} 