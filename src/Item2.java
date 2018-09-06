public class Item2{
    protected String name = "";
    // in game price
    protected int value = 0;
    // mall price
    protected int weight = 0;
 
    public Item2() {
    }
 
    public Item2(String name, int value, int weight) {
        setName(name);
        setValue(value);
        setWeight(weight);
    }
 
    public int getValue() {
        return value;
    }
 
    public void setValue(int value) {
        this.value = Math.max(value, 0);
    }
 
    public int getWeight() {
        return weight;
    }
 
    public void setWeight(int weight) {
        this.weight = Math.max(weight, 0);
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
    	return "{Name: " + getName() + ", Value: " + getValue() + ", Weight: " + getWeight() + "}";
    }
    
 
}
