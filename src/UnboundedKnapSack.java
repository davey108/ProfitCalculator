import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UnboundedKnapSack {
	public Item[] items;
	
	class Pair{
		// how much is the value of this pair
		int value;
		// what is the items in this pair
		Item[] itemsList;
		public Pair(){
			this.value = 0;
			this.itemsList = new Item[0];
		}
		public void addItems(Item item){
			Item[] newList = new Item[itemsList.length + 1];
			for(int i = 0; i < newList.length; i++){
				if(i < itemsList.length)
					newList[i] = itemsList[i];
				else
					newList[newList.length-1] = item;
			}
			this.itemsList = newList;
		}
	}
    public static void main(String[] args) {
    	String fpath = "C:/Users/David1234/Desktop/Nostale Vendetta Mall Item List";
    	String ext = ".xlsx";
    	UnboundedKnapSack cal = new UnboundedKnapSack();
    	try {
			cal.setUp(fpath + ext);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	int capacity = 25000;
    	int numItems = cal.items.length;
    	int p = cal.unboundedKnapSack(capacity,numItems);
    	System.out.println(p);
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
        // dp[i] is going to store maximum value
        // with knapsack capacity i.
       int dp[] = new int[W+1];
        // Fill dp[] using above recursive formula
        for(int i = 0; i <= W; i++){
            for(int j = 0; j < n; j++){
                if(items[j].getMallPrice() <= i){                	
                	dp[i] = max(dp[i],dp[i - items[j].getMallPrice()] +  items[j].getGamePrice());
                }
            }
        }
        return dp[W];
    }
    public int max(int first, int second){
    	return first > second? first: second;
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
		    // always ignore first row
		    
		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    // Figure out the maximum # of cells (cols) that we will need to read table (since some rows will
		    // have different number of cells, so we take the largest)
		    for(int i = 1; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = row.getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		            System.out.println(tmp);
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
		                		items[r-1].setMallPrice((int)cell.getNumericCellValue());
		                	else if(c==2)
		                		items[r-1].setGamePrice((int)cell.getNumericCellValue());
		                	else
		                		System.out.println("Unexpected column! Review Sheet!");
		                }
		            }
		            System.out.println("Row: " + r + " " + items[r-1].getName() + " " + 
		            		items[r-1].getMallPrice() + " " + items[r-1].getGamePrice());
		        }
		    }
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
