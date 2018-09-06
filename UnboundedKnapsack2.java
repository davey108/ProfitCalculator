import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UnboundedKnapsack2 {
	// stop the function from going too long
	protected int globalCount = 0;
    protected Item2 [] items;
    protected int n; // the number of items
    protected Item2 sack = new Item2("sack", 0, 15000);
    protected Item2  best = new Item2("best", 0, 0);
    protected int  []  maxIt;  // maximum number of items
    protected int  []    iIt;  // current indexes of items
    protected int  [] bestAm;  // best amounts
 
    public UnboundedKnapsack2(String file) {
    	try {
			setUp(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	n = items.length;
    	maxIt = new int[n];
    	iIt = new int[n];
    	bestAm = new int[n];
        // initializing:
        for (int i = 0; i < n; i++) {
            maxIt [i] = (int)(sack.getWeight() / items[i].getWeight());
        } // for (i)
        // print out the amount of max # of each item we can buy if the sack only contains multiple copies of 1 item
        /*for(int i = 0; i < maxIt.length; i++){
        	System.out.println(items[i] + ":" + maxIt[i]);
        }*/
        // calc the solution:
        calcWithRecursion(0);
 
        // Print out the solution:
        NumberFormat nf = NumberFormat.getInstance();
        System.out.println("Maximum value achievable is: " + best.getValue());
        System.out.print("This is achieved by carrying (one solution): ");
        for (int i = 0; i < n; i++) {
            System.out.print(bestAm[i] + " " + items[i].getName() + ", ");
        }
        System.out.println();
        System.out.println("The weight to carry is: " + nf.format(best.getWeight()));
 
    }
 
    // calculation the solution with recursion method
    // item : the number of item in the "items" array
    public void calcWithRecursion(int item) {
    	
        for (int i = 0; i <= maxIt[item]; i++) {
            iIt[item] = i;
            if(globalCount == 33) return;
            if (item < n-1) {
                calcWithRecursion(item+1);
            } else {
                int    currVal = 0;   // current value
                int currWei = 0; // current weight
                for (int j = 0; j < n; j++) {
                	// constraints
                	// make it realistic, we can't sell more than 1 stacks quickly
                	if(iIt[j] > 99) continue;
                	if(iIt[j] > 3 && items[j].getValue() > 10000000) continue;
                	if(iIt[j] > 5 && items[j].getValue() > 5000000) continue;
                    currVal += iIt[j] * items[j].getValue();
                    currWei += iIt[j] * items[j].getWeight();
                }
 
                if (currVal > best.getValue() && currWei <= sack.getWeight())
                {
                	globalCount ++;
                    best.setValue (currVal);
                    best.setWeight(currWei);
                    for (int j = 0; j < n; j++) bestAm[j] = iIt[j];
                    System.out.println("Global: " + globalCount + "," + best.getValue());
                } // if (...)
            } // else
        } // for (i)
    } // calcWithRecursion()
 
    // the main() function:
    public static void main(String[] args) {
    	String fpath = "C:/Users/David1234/Desktop/Nostale Vendetta Mall Item List";
    	String ext = ".xlsx";
        new UnboundedKnapsack2(fpath + ext);
    } // main()
    
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
		    this.items = new Item2[rows-1];
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
		        	items[r-1] = new Item2();
		            for(int c = 0; c < cols; c++) {
		                cell = row.getCell((short)c);
		                if(cell != null) {
		                	// name
		                	if(c == 0)
		                		items[r-1].setName(cell.getStringCellValue());
		                	else if(c==1)
		                		items[r-1].setWeight((int)cell.getNumericCellValue());
		                	else if(c==2)
		                		items[r-1].setValue((int)cell.getNumericCellValue());
		                	else
		                		System.out.println("Unexpected column! Review Sheet!");
		                }
		            }
		            /*System.out.println("Row: " + r + " " + items[r-1].getName() + " " + 
		            		items[r-1].getWeight() + " " + items[r-1].getValue());*/
		        }
		    }
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
 
} // class
