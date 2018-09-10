import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UnboundedKnapSack {
	public Item[] items;
	public Pair[] itemsCt;
	public HashMap<String,Item> itemsMap;
	class Pair{
		// what is the items in this pair
		ArrayList<StoredItem> itemsList;
		public Pair(){
			this.itemsList = new ArrayList<StoredItem>();
		}
		/* There are 2 cases for changes: addition and replacement
		 * Addition is when a new item is added and there were pre-existing items
		 * Replacement is when all pre-exisitng item is replaced with a new item
		 */
		public void addition(Item item){
			int exist = exist(item);
			// if a new item
			if(exist == -1){
				itemsList.add(new StoredItem(item.getName(),1));
			}
			else{
				itemsList.get(exist).count += 1;
			}
		}
		
		public void replacement(Item item){
			this.itemsList.clear();
			itemsList.add(new StoredItem(item.getName(),1));
		}
		/* return the index of the item if exist, else -1*/
		public int exist(Item item){
			if(item.getName() != "" || item.getName() != null){
				for(int i = 0; i < itemsList.size(); i++){
					if(itemsList.get(i).name.equals(item.getName())){
						return i;
					}
				}
			}
			return -1;
		}
		public void copyList(ArrayList<StoredItem> list){
			this.itemsList.clear();
			for(int i = 0; i < list.size();i ++){
				this.itemsList.add(list.get(i));
			}
		}
		public void printList(){
			int cost = 0;
			for(int i = 0; i < itemsList.size(); i++){
				System.out.println(itemsList.get(i).name + " x " + itemsList.get(i).count);
				for(int j = 0; j < items.length; j++){
					if(itemsList.get(i).name.equals(items[j].getName())){
						cost += items[j].getWeight() * itemsList.get(i).count;
					}
				}
			}
			System.out.println("Cost: " + cost);
		}
	}
	class StoredItem{
		String name;
		int count;
		public StoredItem(String name, int count){
			this.name = name;
			this.count = count;
		}
		
	}
    public static void main(String[] args) {
    	String fpath = "C:/Users/DavidPC/Desktop/Nostale Vendetta Mall List";
    	String ext = ".xlsx";
    	UnboundedKnapSack cal = new UnboundedKnapSack();
    	try {
			cal.setUp(fpath + ext);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	int capacity = 15000;
    	int numItems = cal.items.length;
    	int p = cal.unboundedKnapSack(capacity,numItems);
    	System.out.println(p);
    	UnboundedKnapSack.Pair list = cal.itemsCt[capacity];
    	list.printList();
    	
    	
    }
    
    // Returns the maximum value with knapsack
    // of W capacity
    /*
     * W is the capacity
     * n is the number of items
     * val is the array of values
     * wt is the array of weight
     */
    public int unboundedKnapSack(int W, int n) {
    	itemsCt = new Pair[W+1];
    	for(int i = 0; i < itemsCt.length; i++){
    		itemsCt[i] = new Pair();
    	}
        // dp[i] is going to store maximum value
        // with knapsack capacity i.
       int dp[] = new int[W+1];
       
        // Fill dp[] using above recursive formula
        for(int i = 0; i <= W; i++){
            for(int j = 0; j < n; j++){
            	if(items[j].getWeight()<= i){
            		//System.out.println("Old value of dp[" + i + "]: " + dp[i]);
            		// if exceed the priority cap, don't add!
            		/*if(numItems[j] > 99*2) continue;
                	if(numItems[j] > 10 && items[j].getPriority() == 3) continue;
                	if(numItems[j] > 30 && items[j].getPriority() == 2) continue;*/
	            	if(dp[i - items[j].getWeight()] +  items[j].getValue() < dp[i]){
	            		dp[i] = dp[i];
					}
	            	else{
	            		//System.out.println("j: "  + j);
	            		//System.out.println("Checking dp index: " + (i-items[j].getWeight()));
	            		//System.out.println("Passed. Value of dp: " + dp[i - items[j].getWeight()] + " item val: " + items[j].getValue() + " item name: " + items[j].getName());
						dp[i] = dp[i - items[j].getWeight()] +  items[j].getValue();
						// if 0 and dp[i] changed, then its a replacement of just the item
						// just a single item, no way it can exceed cap
						if(dp[i - items[j].getWeight()] == 0){
							itemsCt[i].replacement(items[j]);
							//System.out.println("itemsCt[" + i + "]" + " replaced with: " + items[j].getName());
							//itemsCt[i].printList();
							//System.out.println("------" + "List before adding item");
						}
						// else , it is the combination of the dp[i] and the new item
						else if(dp[i - items[j].getWeight()] != 0){
							itemsCt[i].copyList(itemsCt[i - items[j].getWeight()].itemsList);
							//System.out.println("------" + "List before adding item");
							itemsCt[i].addition(items[j]);
							//System.out.println("itemsCt[" + i + "]" + " added with: " + items[j].getName());
							//itemsCt[i].printList();
						}
						//System.out.println("New value of dp[" + i + "]: " + dp[i]);
	            	}
            	}
            }
        }
        return dp[W];
    }
    
    public ArrayList<StoredItem> getList(ArrayList<StoredItem> items, Item item){
    	ArrayList<StoredItem> checkedList = new ArrayList<StoredItem>();
    	items.forEach(checkedList::add);
    	// check if exist
    	items.forEach(checkedList::addition);
		return checkedList;
    	
    }
        
    
    /*
     * Set up the array
     */
	public void setUp(String file) throws IOException {
		XSSFWorkbook wb = null;
		try {
			OPCPackage pkg = OPCPackage.open(new File(file));
		    wb = new XSSFWorkbook(pkg);
		    XSSFSheet sheet = wb.getSheetAt(0);
		    XSSFRow row;
		    XSSFCell cell;

		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();
		    
		    int cols = 0; // No of columns
		    int tmp = 0;
		    
		    // 1 less than rows since we ignored 1st row (row 0)
		    this.items = new Item[rows-1];
		    this.itemsMap = new HashMap<String,Item>();
		    // always ignore first row
		    
		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    // Figure out the maximum # of cells (cols) that we will need to read table (since some rows will
		    // have different number of cells, so we take the largest)
		    for(int i = 1; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = row.getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		           
		        }
		    }
		    for(int r = 1; r < rows; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
		        	items[r-1] = new Item();
		            for(int c = 0; c < cols; c++) {
		                cell = row.getCell((short)c);
		                if(cell != null) {
		                	// name
		                	if(c == 0)
		                		items[r-1].setName(cell.getStringCellValue());
		                	else if(c==1)
		                		items[r-1].setWeight((int)cell.getNumericCellValue());
		                	/*// price in market
		                	else if(c==2)
		                		items[r-1].setValue((int)cell.getNumericCellValue());*/
		                	/* Priority */
		                	else if (c==3)
		                		items[r-1].setPriority((int)cell.getNumericCellValue());
		                	/* Current resell price*/
		                	else if (c==4)
		                		items[r-1].setValue((int)cell.getNumericCellValue());
		                	else if (c > 4)
		                		System.err.println("Error! Check excel file");
		                	itemsMap.put(items[r-1].getName(), items[r-1]);
		                }
		            }
		        }
		    }
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
