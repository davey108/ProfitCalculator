/* A class to indicate the item from mall
 * Cost = mallPrice
 * Profit = inGamePrice
 */
public class Item {
	private int mallPrice;
	private String name;
	private int inGamePrice;
	
	/*public Item(String name, int mallPrice, int inGamePrice){
		this.name = name;
		this.mallPrice = mallPrice;
		this.inGamePrice = inGamePrice;
	}*/
	public int getMallPrice(){
		return this.mallPrice;
	}
	public void setMallPrice(int mallPrice){
		this.mallPrice = mallPrice;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public int getGamePrice(){
		return this.inGamePrice;
	}
	public void setGamePrice(int newGamePrice){
		this.inGamePrice = newGamePrice;
	}
	
}
