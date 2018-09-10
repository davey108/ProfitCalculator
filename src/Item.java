/* A class to indicate the item from mall
 * Cost = mallPrice
 * Profit = inGamePrice
 */
public class Item {
	private int weight;
	private String name;
	private int value;
	private int priority;
	
	/*public Item(String name, int mallPrice, int inGamePrice){
		this.name = name;
		this.mallPrice = mallPrice;
		this.inGamePrice = inGamePrice;
	}*/
	public int getWeight(){
		return this.weight;
	}
	public void setWeight(int mallPrice){
		this.weight = mallPrice;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public int getValue(){
		return this.value;
	}
	public void setValue(int newGamePrice){
		this.value = newGamePrice;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
