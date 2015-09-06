package casc;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 
 * ReadExcel.java
 * 
 * <br/>
 * 
 * <h3>Note:</h3>
 * <ul>
 * <li>This is a Java program for the CADE ATP System Competition</li>
 * <li>All honor credit to Dr.Geoff Sutcliffe</li>
 * <li>All data is collected from <a href="http://www.cs.miami.edu/~tptp/CASC/">CASC Official Website</a></li>
 * </ul>
 * 
 * <br/>
 *   
 * <h3>ReadExcel class:</h3>
 * <ul>
 * <li>Grab data from the website</li>
 * <li>Read local files</li>
 * <li>Show data from previous competition</li>
 * </ul>
 * 
 * @author Jiajun Chen
 *
 */

public class ReadExcel {
	
	private static int SheetNO = 0;
	public static File readFile = null;
	public static File output = new File("output.txt");
	public static BufferedWriter out ;
	public static int page = 0;

	
	/**
	 * <h3>preComp</h3>
	 *    <ul>
	 *    <li>Receives the year number from the user</li>
	 *    <li>find the url of that specific year</li>
	 *    <li>pass the url of the competition in the selected year to the readFromWeb function</li>
	 *    </ul>
	 *    
	 * @param year
	 */
	public static void preComp(int year){
		
		String yearURL=null;
		
		if(year>=2004 && year<= 2013){
		
			switch(year){
			
			case 1996:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/13/Results.html";
						break;
			case 1997:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/14/CASC-14Results.html";
						break;
			case 1998:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/15/CASC-15FullResults.html";
						break;
			case 1999:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/16/WWWResults/Results.html";
						break;
			case 2000:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/17/WWWResults/Results.html";
						break;
			case 2001:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/JC/WWWFiles/Results.html";
						break;
			case 2002:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/18/WWWFiles/Results.html";
						break;
			case 2003:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/19/WWWFiles/Results.html";
						break;
			case 2004:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/J2/WWWFiles/Results.html";
						break;
			case 2005:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/20/WWWFiles/Results.html";
						break;
			case 2006:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/J3/WWWFiles/Results.html";
						break;
			case 2007:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/21/WWWFiles/Results.html";
						break;
			case 2008:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/J4/WWWFiles/Results.html";
						break;
			case 2009:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/22/WWWFiles/Results.html";
						break;
			case 2010:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/J5/WWWFiles/Results.html";
						break;
			case 2011:	
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/23/WWWFiles/Results.html";
						break;
			case 2012:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/J6/TuringWWWFiles/Results.html";
						break;
			case 2013:
						yearURL = "http://www.cs.miami.edu/~tptp/CASC/24/WWWFiles/Results.html";
						break;
			default:
						System.out.println("Error :( ");
						break;
			}
			
			readFromWeb(yearURL);
		
		}else{
			System.out.println("Sorry, we cannot find any data from the year that you have entered :(");
		}
	}
	
	/**
	 * <h3>readFromWeb</h3>
	 *     <ul>
	 *     <li>Gets url address from outside </li>
	 *     <li>Connect the website</li>
	 *     <li>Grab data from the url</li>
	 *     <li>save the data into a local file</li>
	 *     </ul> 
	 * 
	 * @param url
	 */
	public static void readFromWeb(String url){
		
		
		/*    First, get data from the website     */
		
		Document doc;
		
		try {
			
			doc = Jsoup.parse(new URL(url).openStream(),"ISO-8859-1",url); 
			
			String title = doc.title();
			System.out.println("Title is:" + title);
			
			
			/* The following saves the data into a local excel file */
			
			String fileName = "Excel.xls";
			File theFile = new File(fileName);
			
			WritableWorkbook book = Workbook.createWorkbook(theFile);
			
			/*
		     *<TABLE>
		     * <TR>
		     * 		<TH>
		     * 		</TH>
		     * 
		     * 		<TD>
		     * 		</TD>
		     * </TR>
		     * </TABLE>
		     */
			
			Elements tables = doc.getElementsByTag("TABLE");
			
			SheetNO = tables.size();
			
			for(int n=0;n<tables.size();n++){
			
				WritableSheet sheet = book.createSheet("Page "+(n+1), n);
			
				Element table = tables.get(n);
			
				Elements rows = table.getElementsByTag("TR");
		    
				//----rows
				for(int rowNo=0;rowNo<rows.size();rowNo++){
		    	
					Element tr = rows.get(rowNo); //---the ith row
					Elements head = tr.getElementsByTag("th");//---select the contents with th tags in that row
					Elements col = tr.getElementsByTag("td"); //---select the contents with td tags in that row
		    	
		    	
		    	
					if(head.size() !=0){
		    	
						for(int headNo=0;headNo<head.size();headNo++){
		    		
							Element hd = head.get(headNo);//----get the head element
		    		
							//Label(colNo, rowNo, content)
							Label label = new Label(headNo,rowNo,hd.text());//---create a label for the head
		    		
							try {
								sheet.addCell(label);
							} catch (RowsExceededException e) {
								
								e.printStackTrace();
								new ErrorHandler();
					    		ErrorHandler.main(null);
					    		return;
						
							} catch (WriteException e) {
						
								e.printStackTrace();
								new ErrorHandler();
					    		ErrorHandler.main(null);
					    		return;
						
							}//---add the label to the sheet
		    		
						}
		    	
					}
		    	
		    	
					if(col.size() != 0){
						
						for(int colNo=0;colNo<col.size();colNo++){
		    		
							Element td = col.get(colNo);//---get the column element
		    		
							Label label1 = new Label(colNo+head.size(),rowNo,td.text());
		    		
							try {
								
								sheet.addCell(label1);
								
							} catch (RowsExceededException e) {
						
								e.printStackTrace();
								new ErrorHandler();
					    		ErrorHandler.main(null);
					    		return;
								
							} catch (WriteException e) {
								
								e.printStackTrace();
								new ErrorHandler();
					    		ErrorHandler.main(null);
					    		return;
							}
						}
		    	
					}
				}
			}
		   
			/* Try to write the book Object, when it fails, use the error handler */
				try {
					
				book.write();
				
				} catch(Exception e) {
					
					e.printStackTrace();
		    		new ErrorHandler();
		    		ErrorHandler.main(null);
		    		return;
				}
	    	
		    	try {
		    	
		    	book.close();
		    	
		    	} catch (Exception e) {
				
		    		e.printStackTrace();
		    		new ErrorHandler();
		    		ErrorHandler.main(null);
		    		return;
		    	}
		    	
		    	JOptionPane.showMessageDialog(null,"Online data has been saved to the local file");
		    	
		    	readFile = new File("Excel.xls");
		    	
		
		
			}catch(IOException e){
				
				System.out.println("ERROR exists or no Internet access, message: "+e.getMessage());
				JOptionPane.showMessageDialog(null, "ERROR exists or no Internet access");
				new OpenURL();
				OpenURL.main(null);
				
				
		    }

		
		
		
	}
	
	/**
	 * <h3>readLocalFile</h3>
	 *     <ul>
	 *     <li>Pop a console and ask for the local file</li>
	 *     <li>import the file</li>
	 *     <li>if the file is not null,save it to the theFile</li>
	 */
	public static void readLocalFile(){
		
		JFileSelector choose = new JFileSelector();
		
		File localFile = choose.openFile();
		
		if (!localFile.exists() || localFile.isDirectory() || localFile == null){
			new Menu();
			Menu.main(null);
		}else{
			readFile = localFile;
		}
		
	}
	
	/**
	 * <h3>Main</h3>
	 * @param args
	 */
	public static void main(String[] args,int num) {

		page = num;
		
		Workbook book = null;
				
		if(readFile != null){
		
		try {
			book = Workbook.getWorkbook(readFile);
			SheetNO = book.getNumberOfSheets();
			output.createNewFile();
			out = new BufferedWriter(new FileWriter(output));
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new ErrorHandler();
    		ErrorHandler.main(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new ErrorHandler();
    		ErrorHandler.main(null);
		}
		
		new Output();
		Output.sheetNum = SheetNO;
			
			new ReadSheet(book,readFile,page,out);

		
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorHandler();
    		ErrorHandler.main(null);
		}
		
		
		Output.main(null);
		Output.check = true;
		output.deleteOnExit();
		
		}	
	}

}
