package casc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


/*  Which problems were solved by NO SYSTEM?
Which problems were solved by ONE SYSTEM?
Which problems were solved by ALL SYSTEM?

Which systems solved NO PROBLEMS?
Which systems solved ONE PROBLEM?
Which systems solved ALL PROBLEMS?

Which systems solved superset of another system?

What is the best combination that has the maximum problems solved by A+B+C?
*/
/**
 * 
 * ReadSheet.java
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
 * <h3>ReadSheet class:</h3>
 * <ul>
 * <li>Read tables</li>
 * <li>Analyze the data</li>
 * <li>Find out desired result</li>
 * </ul>
 * 
 * @author Jiajun Chen
 *
 */
public class ReadSheet {
	
	public File file = null;
	public String title = null;
	
	public static String[][] bigSheet;
	
	private int pCount = 0;
	private Queue<String> pNO = new LinkedList<String>();
	private Queue<String> pOne = new LinkedList<String>();
	private Queue<String> pAll = new LinkedList<String>();
	
	private int[] sCount;
	private Queue<String> sNO = new LinkedList<String>();
	private Queue<String> sOne = new LinkedList<String>();
	private Queue<String> sAll = new LinkedList<String>();

	private String[][] competitors;
	private ArrayList<String[]> list = new ArrayList<String[]>();
	
	private int timeLimit = 240;
	private double[] avgTime;
	private int row = 0;
	
	private BufferedWriter out;
	private static ArrayList<String[]> compareDesk;
	private static ArrayList<String[]> eqlSolved = new ArrayList<String[]>();
	private static ArrayList<String[]> uneqlSolved = new ArrayList<String[]>();
	
	private static Sheet sheet;
	
	
	

	/**
	 * <h3>ReadSheet</h3>
	 * 			<ul>
	 * 			<li>Initialize several variables and count valid number of rows</li>
	 * 			<li>Iterate the table, count how many non numeric contents in the table</li>
	 * 			<li>Add the counting result into different queues based on pCount</li>
	 * 			<li>Check if the table has summary rows</li>
	 * 			</br>
	 * 			<li>(no summary rows) Add counting result into different queues based on sCount</li>
	 * 			<li>(summary rows) read the data in the summary rows</li>
	 * 			<li>Separate the table into several individual string arrays without null values</li>
	 * 			<li>(no summary rows) Calculate the average CPU time of each competitor</li>
	 * 			<li>(summary rows) read the average CPU time data of each competitor in the summary rows</li>
	 * 			</br>
	 * 			<li>When the time is equally divided, count the number of problems a competitor solved in different combinations</li>
	 * 			<li>Add the counting result into a 2D array</li>
	 * 			<li>Sort the numbers in the array and find out the best combination</li>
	 * 			</br>
	 * 			<li>Calculate the time limit for each competitor when the time is not equally separated</li>
	 * 			<li>When the time is not equally separated, count the number of problems a competitor solved in different combinations</li>
	 * 			<li>Find out the best combination</li>  
	 * 			</ul>
	 * @param book
	 * @param theFile
	 * @param sheetNo
	 */
	public ReadSheet(Workbook book,File theFile,int sheetNo,BufferedWriter theOut) {
		
		file = theFile;
		
		this.out = theOut;
		
		readData(book,sheetNo);
		
		}
	
	/**
	 * <h3>readData</h3>
	 * 		
	 * @param book
	 * @param sheetNo
	 */
	private void readData(Workbook book,int sheetNo){
		
		/* Read the local file*/
		//File file = new File("/Users/carsonchen/Desktop/Excel.xls");
		 
		//System.setOut(new PrintStream(ps));
		
	      
		
		/* open the file as a workbook and read the relevant sheet based on the sheetNo */
		//Workbook book = Workbook.getWorkbook(file);
		sheet = book.getSheet(sheetNo);
		
		
		/* get the title of the table*/
		title = sheet.getCell(0, 0).getContents();
		
		
		/* METHOD: */
		
		/* initialize an array to count for the number of problems that a system solved*/
		 /*   +-------------------++-------------++--------------++--------------++-------------++----------------++-------------+
		   *  |Satallax‑MaLeS  1.2||Satallax  2.7||Isabelle  2013||Isabelle  2012||LEO‑II  1.6.0||TPS  3.120601S1b||cocATP  0.1.8|
		   *  +-------------------++-------------++--------------++--------------++-------------++----------------++-------------+
		   *  |       119		  ||   116       ||     108      ||      98      ||      76     ||      47        ||    14       |
		   *  +-------------------++-------------++--------------++--------------++-------------++----------------++-------------+
		   *  */
		  sCount = new int[sheet.getColumns()-1];
		  avgTime = new double[sheet.getColumns()-1];
		  
		  /* Go over the table and count for valid number of rows in the current table */
		  int count=0; // count for the valid number of the rows in the current table
		  row = 1;
		  boolean summaryRow = false;
		  
		  while(row<sheet.getRows() && !sheet.getCell(0, row).getContents().contains("Solved")){
			  
			  count++;
			  row++;
			  
			  
		  }
		  
		  if(sheet.getCell(0, row).getContents().contains("Solved")){
			  summaryRow = true;
		  }
		  
		  /*=============================================*/
		  
		  
		  /*Initialize a 2D array to store the results of all the competitors*/
		  bigSheet = new String[sheet.getColumns()][row-1];
		  competitors = new String[sheet.getColumns()-1][row];
		  
		  
		  /*  +-------------------++-------------++--------------++--------------++-------------++----------------++-------------+
		   *  |Satallax‑MaLeS  1.2||Satallax  2.7||Isabelle  2013||Isabelle  2012||LEO‑II  1.6.0||TPS  3.120601S1b||cocATP  0.1.8|
		   *  +-------------------++-------------++--------------++--------------++-------------++----------------++-------------+
		   *  |     data		  ||  data       ||   data       ||    data      ||    data     ||    data        ||    data     |
		   *  +-------------------++-------------++--------------++--------------++-------------++----------------++-------------+
		   * */
		  
		 /* Initialize the name of the competitors in the 2D array*/
		  for(int p=1;p<sheet.getColumns();p++){
			
			  
			  competitors[p-1][0] = sheet.getCell(p, 0).getContents().replaceAll("&#160;", " ");// Java represents the &nbsp as \u00a0, &nbsp can be represented as &#160 in ISO-8859-1 
			  
		      
			  
		  }
		  
		  for(int a=1;a<row;a++){
			  for(int b=0;b<sheet.getColumns();b++){
				  bigSheet[b][a-1] = sheet.getCell(b, a).getContents();
			  }
		  }
		  
		
		  
		  /* 	This part can solve:
		   		Which problems were solved by NO SYSTEM?
				Which problems were solved by ONE SYSTEM?
				Which problems were solved by ALL SYSTEM?

				Which systems solved NO PROBLEMS?
				Which systems solved ONE PROBLEM?
				Which systems solved ALL PROBLEMS?
			*/
		
		/*Count the UNK and TMO results in the table*/
		  for(int i=1;i<row;i++){
				
			  
				  for(int j=1;j<sheet.getColumns();j++){
					  
					  Cell cell = sheet.getCell(j, i);
					
				/*
				 *  If the content has non numeric string,it means the question does not solved by all systems;
				 *  Vise Versa
				 *  
				 *  If the specific content has non numeric string, it means that system does not solved that question
				 *  Vise Versa
				 *  */
					  	if(cell.getContents().toUpperCase().contains("NO")){ //----
					  		pCount++;
					  		
					  		if(summaryRow == false){
					  			sCount[j-1] +=1;
					  		}
					  		
					  	}else{
					  		competitors[j-1][i] = cell.getContents();
					  	}
				
				  }//---for(int j=1;j<sheet.getColumns();j++)
				
			
				  /*
				   * (1).If the pCount equals to the number of the systems, it means no one solves this question;
				   * (2).If the pCount is one less than the number of the systems, it means there is one system who solves this question;
				   * (3).If the pCount is 0, it means this question was solved by all system;
				   * After iterated each line of the table, reset the pCount to 0;
				   *  */
				  if(pCount == sheet.getColumns()-1){
					  pNO.offer(sheet.getCell(0, i).getContents());
				  }else if(pCount == sheet.getColumns()-2){
					  pOne.offer(sheet.getCell(0, i).getContents());	
				  }else if(pCount == 0 ){
					  pAll.offer(sheet.getCell(0, i).getContents());
				  }
				  pCount=0; 		

		}
		  
		  
		  
		
		
		  /* Based on if there is a row that looks like:
		   * +------------++---------++---------++---------++--------++--------++--------++--------+
		   * | Solved/150 || 119/150 || 116/150 || 108/150 || 98/150 || 76/150 || 47/150 || 14/150 |
		   * +------------++---------++---------++---------++--------++--------++--------++--------+
		   * 
		   * */
		  
		if(summaryRow == false){
			
			/* Go over the sCount array and see if there is any system that satisfied:
			 * (1). If the number of problems that a system did not solved is equal to the number of problem, it means it does not solved any problems;
			 * (2). If the number of problems that a system did not solved is one less to the number of problem, it means it solves only one problem;
			 * (3). If the number of problems that a system did not solved is zero,it means it solved all problems
			 * */
			for(int k=0;k<sCount.length;k++){
				
				if(sCount[k] == count-1){
					sNO.offer(sheet.getCell(k, 0).getContents());
				}else if(sCount[k] == count-2){
					sOne.offer(sheet.getCell(k, 0).getContents());
				}else if(sCount[k] == 0){
					sAll.offer(sheet.getCell(k, 0).getContents());
				}
			}
			
			
		
		}else{
		
		  /* Read the line: "Solved/n :    " and get information*/
		  for(int a=1;a<sheet.getColumns();a++){
			  
			  String s= sheet.getCell(a, row).getContents();
			  
			  String sf = s.substring(0, s.indexOf("/"));
			  String sb = s.substring(s.indexOf("/")+1);
			
			  int sfi = Integer.parseInt(sf);
			  int sbi = Integer.parseInt(sb);
			  
			  sCount[a-1]=sbi-sfi;
			  
			  if(sfi == 0){
				  
				sNO.offer(sheet.getCell(a, 0).getContents());
				
			  }else if(sfi == sbi-1){
				  
				sOne.offer(sheet.getCell(a, 0).getContents());
				
			  }else if(sfi == sbi){
				  
				sAll.offer(sheet.getCell(a, 0).getContents());
				
			  }
		  }  
		}
		
		
		/*This is the end of METHOD */
		printResult();
		
		/* Separate the table into several individual string arrays without null values*/  
		String[] trans = null;
		for(int a=0;a<competitors.length;a++){

			int length = row - sCount[a];
			trans = new String[length];
			int index=0;
			
			for(int b=0;b<competitors[a].length;b++){
				
				if(competitors[a][b] != null){
					trans[index] = competitors[a][b];
					index++;
				}
			}
			index=0;
			list.add(trans);
		}
		
		
		/**/
		if(summaryRow == false){
			
			/*  Calculate the average CPU time for each competitor*/
			double sum = 0.0;
			for(int l=0;l<list.size();l++){
				for(int m=1;m<list.get(l).length;m++){
					sum +=Double.parseDouble(list.get(l)[m]);
				}
				avgTime[l]=sum/list.get(l).length;
				sum=0.0;
			}
			
		}else{
			
			/* Read the line: "AV. CPU Time :    " and get information*/
		  	  for(int b=1;b<sheet.getColumns();b++){
		  		  
		  		  double avg = -1;
		  		  String s=sheet.getCell(b, row+1).getContents();
		  		  
		  		  if(s.equals("-")){
		  			  avg = 0;
		  		  }else{
		  		   avg = Double.parseDouble(s);
		  		  }
		  		  
		  		  avgTime[b-1]=avg;
		  	  }
		  	  
		}
		
		/**/
		
		/*
		 * This part can solve:
		 * What is the best combination that has the maximum problems solved by A+B+C?
		 * */
		
		/* The following part is trying to find out the best combination when the time limits are equally divided*/
		
		/* Initialize an array of numbers as the pointers*/
		
		String[] arr = new String[competitors.length];
		
		/* Suppose there are 7 competitors, so the array is [0,1,2,3,4,5,6] */
		for(int l=0;l<competitors.length;l++){
			
			arr[l]=Integer.toString((l));
			
		}
			
		/* use the getCombination method to get all the possibilites of combination */
		/* Suppose for 3 competitors [0,1,2], the ArrayList is: [01],[02],[12],[012] */
		ArrayList<String> arrTD = getCombination(arr);
		String[] player = null;
		String[] store = null;
		
		try{
			/* A loop to go over the ArrayList, take [012] as an example */
			for(int i=0;i<arrTD.size();i++){
				
				/* arrTmp = 123 */
				String arrTmp = arrTD.get(i);
				
				/* */
				/*
				* Store the result like:
				* 
				* +-------------++--------------------++---------------++------------++---------------++------------+
				* |NO Of Systems||Total Problem Solved||CompetitorName1||CN1-Problems||CompetitorName2||CN2-Problems| etc.
				* +-------------++--------------------++---------------++------------++---------------++------------+
				* 
				* The length of the array is 2(nos+1);
				*/
				/* */
				
				/*  an integer to count*/
				int ct=0;
				int len = 2*(arrTmp.length()+1);
				
				/* An array to store result */
				store = new String[len];
				store[0]=Integer.toString(arrTmp.length());
				
				/* Get the time limit */
				double time = (timeLimit/arrTmp.length());
				compareDesk = new ArrayList<String[]>();
				
				/* A loop to go over the element, in example, [012] */
				while(ct<arrTmp.length()){
					
					/* Get the pointer, first [0],then [1], then [2] */
					int pointer=Character.getNumericValue(arrTmp.charAt(ct));
					
					/* Record the name of the current competitor */
					store[2*(ct+1)] = sheet.getCell(pointer+1, 0).getContents();
					
					/* Go through the table and see what problems are solved by the current competitor */
					player = goThrough(pointer,time);
					
					
					
					/* Record the number of problems that are solved by the current competitor */
					store[(2*(ct+1)+1)] = Integer.toString(player.length);
					
					compareDesk.add(player);
					
					player=null;
					
					ct++;
				}
				
				int totalProblem = union(compareDesk).length;
				
				store[1] = Integer.toString(totalProblem);
				
				eqlSolved.add(store);

		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
      		out.write("===============================================\n");
      		out.write("8.What is the best combination that has the maximum problems solved by A+B+C when the time limits are equally divided?\n");
      		out.write("===============================================\n");
      		out.write("\n");
      	}catch(IOException e){
      		e.printStackTrace();
      	}
		
		/* Print out the best combination */
		int best =-1;
		try{
		for(int n =2;n<=arr.length;n++){
			best = findBest(n,eqlSolved);
			double time = (timeLimit/n);
			out.write("*The best combination of "+n+" systems with time limit "+time+" is: ");
			out.write("\n");
			out.write("  Number of Systems: "+eqlSolved.get(best)[0]+" ");
			out.write("\n");
			out.write("  Time limit: "+time+"s ");
			out.write("\n");
			out.write("\n");
			for(int m=2;m<eqlSolved.get(best).length;m+=2){
				out.write("  Name: "+eqlSolved.get(best)[m]+" ");
				out.write("  Solved: "+eqlSolved.get(best)[m+1]+" ");
				out.write("\n");
			}
			out.write("\n");
			out.write("  This group solves: "+eqlSolved.get(best)[1]+" problems in total");
			out.write("\n");
			out.write("\n");
			
		}
		
		
		
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		
	/**/
		
		/*
		 * This part can solve:
		 * What is the best combination that has the maximum problems solved by A+B+C?
		 * */
		
		/* The following part is trying to find out the best combination when the time limits are unequally divided*/
		
		try{
      		out.write("===============================================\n");
      		out.write("\n");
      		out.write("===============================================\n");
      		out.write("9.What is the best combination that has the maximum problems solved by A+B+C when the time limits are unequally divided?\n");
      		out.write("===============================================\n");
      		out.write("\n");
      	}catch(IOException e){
      		e.printStackTrace();
      	}
		
		
		
		/* */
		/*
		* Store the result like:
		* 
		* +-------------++--------------------++---------------++------------++-------------++---------------++------------+
		* |NO Of Systems||Total Problem Solved||CompetitorName1||CN1-Problems||CN1-TimeLimit||CompetitorName2||CN2-Problems| etc.
		* +-------------++--------------------++---------------++------------++-------------++---------------++------------+
		* 
		* The length of the array is 3(nos+1);
		*/
		/* */
		double[] limit;
		String[] player2 = null;
		String[] store2 = null;

		try{
			/* A loop to go over the ArrayList, take [012] as an example */
			for(int i=0;i<arrTD.size();i++){
				
				/* arrTmp = 123 */
				String arrTmp = arrTD.get(i);
				
				
				/*  an integer to count*/
				int ct=0;
				int len = (3*arrTmp.length()+2);
				
				/* An array to store result */
				store2 = new String[len];
				store2[0]=Integer.toString(arrTmp.length());
				
				/* Get the time limit */
				limit = getTimeLimit(arrTmp);
				compareDesk = new ArrayList<String[]>();
				
				/* A loop to go over the element, in example, [012] */
				while(ct<arrTmp.length()){
					
					/* Get the pointer, first [0],then [1], then [2] */
					int pointer=Character.getNumericValue(arrTmp.charAt(ct));
					
					/* Get the relevant time limit */
					double time = limit[ct];
				
					/* Record the time limit */
					store2[(3*ct+4)] = String.format("%.2f", time);
					
					/* Record the name of the current competitor */
					store2[3*ct+2] = sheet.getCell(pointer+1, 0).getContents();
					
					/* Go through the table and see what problems are solved by the current competitor */
					player2 = goThrough(pointer,time);
					
					/* Record the number of problems that are solved by the current competitor */
					store2[(3*ct+3)] = Integer.toString(player2.length);
					
					compareDesk.add(player2);
					
					player2=null;
					
					ct++;
				}
				
				int totalProblem = union(compareDesk).length;
				
				store2[1] = Integer.toString(totalProblem);
				
				uneqlSolved.add(store2);

		}
		}catch(Exception e){
			e.printStackTrace();
		}

		/* Print out the best combination */
		best =-1;
		try{
		for(int n =2;n<=arr.length;n++){
			best = findBest(n,uneqlSolved);
			out.write("*The best combination of "+n+" systems with unequally time limit is: ");
			out.write("\n");
			out.write("  Number of Systems: "+uneqlSolved.get(best)[0]+" ");
			out.write("\n");
			out.write("\n");
			
			for(int m=2;m<uneqlSolved.get(best).length;m+=3){
				out.write("  Name: "+uneqlSolved.get(best)[m]+" ");
				out.write("  Solved: "+uneqlSolved.get(best)[m+1]+" ");
				out.write("  Time limit: "+uneqlSolved.get(best)[m+2]+"s ");
				out.write("\n");
			}
			out.write("\n");
			out.write("  This group solves: "+uneqlSolved.get(best)[1]+" problems in total");
			out.write("\n");
			out.write("\n");
			
		}
		
		
		}catch(IOException e){
			e.printStackTrace();
		}
		
		/* Clear the ArrayLists */
		eqlSolved.clear();
		uneqlSolved.clear();
	}
	

	
	
	/**
	 * <h3>union</h3>
	 * 
	 * 		<ul>
	 * 		<li>a function that use the property that a set has no redundant elements to get the total problem solved by a combination</li>
	 *		</ul>
	 *
	 * @param arr
	 * @return set.toArray(result)
	 */
	private static String[] union(ArrayList<String[]> arr){
		
		Set<String> set = new HashSet<String>();
		
		for(int a=0;a<arr.size();a++){
			for(int b=0;b<arr.get(a).length;b++){
				
				set.add(arr.get(a)[b]);
				
			}
		}
		
		String[] result={};
		
		return set.toArray(result);
		
	}
	
	/**
	 * <h3>goThrough</h3>
	 * 
	 * 		<ul>
	 * 		<li>a function that goes through the whole table and count the number of problems are solved by a competitor </li>
	 *		</ul>
	 *
	 * @param curCompetitor
	 * @param time
	 * @return solveS
	 */
	private static String[] goThrough(int curCompetitor,double time){
		
		Queue<String> solveQ = new LinkedList<String>();
		double data;
		
		for(int a=0;a<bigSheet[curCompetitor+1].length;a++){
			
			if(!bigSheet[curCompetitor+1][a].contains("No")){
				data = Double.parseDouble(bigSheet[curCompetitor+1][a]);
				if(data <= time){
					solveQ.add(bigSheet[0][a]);
				}
			}
			
		}
		
		String[] solveS = new String[solveQ.size()];
		int count=0;
		String str;
		
		while((str=solveQ.poll())!=null){
			solveS[count]=str;
			count++;
		}
		
		
		return solveS;
		
		
	}
	
	/**
	 *  <h3>getCombination</h3>
	 * 
	 * 		<ul>
	 * 		<li>a function to get all the combinations by moving bits</li>
	 *		</ul>
	 * @param arr
	 * @return
	 */
	public static ArrayList<String> getCombination(String[] arr){
		
		ArrayList<String> list = new ArrayList<String>();
		String ar = null;
        int all = arr.length;
        int nbit = 1 << all;

        for (int i = 0; i < nbit; i++) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < all; j++) {
                if ((i & (1 << j)) != 0) {
                    sb.append(arr[j]);
                }
            }
            ar = sb.toString();
            if(ar.length() >1){
            list.add(ar);
            }

        }
        
        return list;
	}
	
	

	
	/**
	 * <h3>printResult</h3>
	 * 
	 * 		<ul>
	 * 		<li>a function to print out all the results</li>
	 *		</ul>
	 */
	public void printResult(){
		
		String str;
		
		try{
      	
      	out.write("\n");
      	out.write("===============================================\n");
      	out.write("\n");
      	out.write("In table < "+title+" >\n");
      	out.write("\n");
      	out.write("===============================================\n");
      	out.write("1.Which problems were solved by NO SYSTEM? \n");
      	out.write("===============================================\n");
      	out.write("\n");
		}catch(IOException e){
			e.printStackTrace();
		}
      	
      	/* print out the problems that are solved by NO SYSTEM */
      	if(pNO.size() == 0){
      		try{
      			out.write(" NO PROBLEM is solved by NO SYSTEM\n");
      		}catch(IOException e){
      			e.printStackTrace();
      		}
      	}else{
      	
      		while((str=pNO.poll()) !=null){
	      		try{
	      			out.write(" \""+str+"\""+" is solved by NO SYSTEM\n");
	      		}catch(IOException e){
	      			e.printStackTrace();
	      		}

      		}
      	}
      	
	      	try{
	      		out.write("\n");
	      		out.write("===============================================\n");
	      		out.write("2.Which problems were solved by ONE SYSTEM?\n");
	      		out.write("===============================================\n");
	      		out.write("\n");
	      	}catch(IOException e){
	      		e.printStackTrace();
	      	}
      	
      	
      	/* print out the problems that are solved by ONE SYSTEM */
      	if(pOne.size() == 0){

	      		try{
	          		out.write(" NO PROBLEM is solved by ONE SYSTEM\n");
	          	}catch(IOException e){
	          		e.printStackTrace();
          	}
      	}else{
      	
      		while((str=pOne.poll())!=null){
	      		try {
	      			String sys = proSolvdByOne(str);
					out.write(" \""+str+"\""+" is only solved by "+sys+" \n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
      		}
      	}
      	
	      	try{
	      		out.write("\n");
	      		out.write("===============================================\n");
	      		out.write("3.Which problems were solved by ALL SYSTEM?\n");
	      		out.write("===============================================\n");
	      		out.write("\n");
	      	}catch(IOException e){
	      		e.printStackTrace();
	      	}
      	
      	/* print out the problems that are solved by ALL SYSTEM */
      	if(pAll.size() == 0){

	      		try{
	          		out.write(" NO PROBLEM is solved by ALL SYSTEM\n");
	          	}catch(IOException e){
	          		e.printStackTrace();
          	}
      	}else{
      		
      		while((str=pAll.poll())!=null){

	      		try{
	          		out.write(" \""+str+"\""+" is solved by ALL SYSTEM\n");
	          	}catch(IOException e){
	          		e.printStackTrace();
          	}
      		}
      	}

	      	try{
	      		out.write("\n");
	      		out.write("===============================================\n");
	      		out.write("4.Which systems solved NO PROBLEMS?\n");
	      		out.write("===============================================\n");
	      		out.write("\n");
	      	}catch(IOException e){
	      		e.printStackTrace();
	      	}
      	
      	/* print out the systems that solved NO PROBLEMS*/
      	if(sNO.size() == 0){

	      		try{
	          		out.write(" NO SYSTEM solves NO PROBLEMS\n");
	          	}catch(IOException e){
	          		e.printStackTrace();
	          	}
      	}else{
      		
      		while((str=sNO.poll())!=null){

	      		try{
	          		out.write(" \""+str+"\""+" solves NO PROBLEMS\n");
	          	}catch(IOException e){
	          		e.printStackTrace();
	          	}
      		}
      	}
      	

	      	try{
	      		out.write("\n");
	      		out.write("===============================================\n");
	      		out.write("5.Which systems solved ONE PROBLEM?\n");
	      		out.write("===============================================\n");
	      		out.write("\n");
	      	}catch(IOException e){
	      		e.printStackTrace();
	      	}
      	
      	/* print out the systems that solved ONE PROBLEMS*/
      	if(sOne.size() == 0){
      		
	      		try{
	          		out.write(" NO SYSTEM solves ONE PROBLEM\n");
	          	}catch(IOException e){
	          		e.printStackTrace();
	          	}
      	}else{
      		
      		while((str=sOne.poll())!=null){

	      		try{
	      			String pro = sysSolvedOne(str);
	          		out.write(" \""+str+"\""+" only solves "+pro+" \n");
	          	}catch(IOException e){
	          		e.printStackTrace();
	          	}
      		}
      	}

	      	try{
	      		out.write("\n");
	      		out.write("===============================================\n");
	      		out.write("6.Which systems solved ALL PROBLEMs?\n");
	      		out.write("===============================================\n");
	      		out.write("\n");
	      	}catch(IOException e){
	      		e.printStackTrace();
	      	}
      	
      	/* print out the systems that solved ALL PROBLEMS*/
      	if(sAll.size() == 0){
      		
		      		try{
		          		out.write(" NO SYSTEM solves ALL PROBLEMS\n");
		          	}catch(IOException e){
		          		e.printStackTrace();
		          	}
      	}else{
      		
      		while((str=sAll.poll())!=null){
	      		try{
	          		out.write(" \""+str+"\""+" solves ALL PROBLEMS\n");
	          	}catch(IOException e){
	          		e.printStackTrace();
	          	}
      		}
      	}
      	

      	try{
      		out.write("===============================================\n");
      		out.write("\n");
      		out.write("===============================================\n");
      		out.write("7.Which systems solved superset of another system?\n");
      		out.write("===============================================\n");
      		out.write("\n");
      	}catch(IOException e){
      		e.printStackTrace();
      	}
      	

      	/* find out and print out which system solved superset of another system*/
      	int count = 0;
      	for(int i=0; i<competitors.length-1;i++){
      		
      		for(int j=i++;j<competitors.length;j++){
      			
      			if(isSubset(competitors[i],competitors[j],row-sCount[i],row-sCount[j]) == true){
      				String name1 = competitors[i][0];
      				String name2 = competitors[j][0];
	      				try{
	      		      		out.write(name1+ " is a superset of "+name2+"\n");
	      		      	}catch(IOException e){
	      		      		e.printStackTrace();
	      		      	}
      				count++;
      			}
      		}
      	}
      	
      	if(count == 0){
	      	try{
	      		out.write("There is no system solved superset of another system.\n");
	      	}catch(IOException e){
	      		e.printStackTrace();
	      	}
      	}
      	
      	try{
      		out.write("\n");
      		out.write("===============================================\n");
      		out.write("\n");
      	}catch(IOException e){
      		e.printStackTrace();
      	}
      	
      	
	}
	
	/**
	 *  <h3>proSolvedByOne</h3>
	 * 		<ul>
	 * 		<li>Find the name of the system which is the only one that solves the problem</li>
	 * 		</ul>
	 * @param problem
	 * @return system
	 */
	private static String proSolvdByOne(String problem){
		
		int count = -1;
		String content = null;
		String system = null;
		
		for(int j=1;j<sheet.getRows();j++){
			content = sheet.getCell(0, j).getContents();
			if(content.equals(problem) || content.contains(problem)){
				count = j;
			}
		}
		

		for(int i=1;i<sheet.getColumns();i++){

			content = sheet.getCell(i, count).getContents();
			if(!content.toUpperCase().contains("NO")){
				system = sheet.getCell(i, 0).getContents();
			}
			
		}
		
		return system;
		
	}
	
	/**
	 * <h3>sysSolvedOne</h3>
	 * 		<ul>
	 * 		<li>Find the name of the problem which is the only one that the system solved</li>
	 * 		</ul>
	 * @param system
	 * @return problem
	 */
	private static String sysSolvedOne(String system){
		
		int count = -1;
		String content = null;
		String problem = null;
		
		for(int j=1;j<sheet.getColumns();j++){
			content = sheet.getCell(j, 0).getContents();
			if(content.equals(system) || content.contains(system)){
				count = j;
			}
		}
		
		for(int i=1;i<sheet.getRows();i++){
			
			content = sheet.getCell(count, i).getContents();
			if(!content.toUpperCase().contains("NO")){
				problem = sheet.getCell(0, i).getContents();
			}
		}
		
		return problem;
		
	}
	
	/**
	 * <h3>isSubset</h3>
	 * 		<ul>
	 * 		<li>Check if the problem that a competitor solved is a subset of the problem that another competitor solved</li>
	 * 		</ul>
	 * @param list1
	 * @param list2
	 * @param list1No
	 * @param list2No
	 * @return true/false
	 */
	/* estimate whether the resulting data of a competitor is the subset(superset) of another competitor */
	private boolean isSubset(String[] list1, String[] list2,int list1No,int list2No){
		
		int count = 0;
		int num = (list1No>=list2No?list2No:list1No);
		
		for(int i=1;i<list1.length;i++){
			
				if(list1[i]!=null && list2[i]!=null){
					count++;
				}
		
		}
		
		if(count == num){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * <h3>getTimeLimit</h3>
	 * 		<ul>
	 * 		<li>Calculate the different time limit for each competitor in different combination</li>
	 * 		</ul>
	 * @param str
	 * @return result
	 */
	private double[] getTimeLimit(String str){
		
		int num = str.length();
		double[] result = new double[num];
		double total = 0;
		int count = 0;
		
		while(count<num){
			total +=avgTime[Character.getNumericValue(str.charAt(count))];
			count++;	
		}
		count = 0;
		
		while(count<num){
			result[count] = (avgTime[Character.getNumericValue(str.charAt(count))]/total)*timeLimit;
			count++;
		}
		
		return result;
	}
	
	/**
	 * <h3>findBest</h3>
	 * 		<ul>
	 * 		<li>Find out the best group in each number of systems</li>
	 * 		</ul>
	 * @param nos(number of systems)
	 * @return bestGroup
	 */
	private int findBest(int nos, ArrayList<String[]> list){
		
		int bestGroup = -1;
		int bestNo = -1;
		
		for(int i=0;i<list.size();i++){
			if(list.get(i)[0].equals(Integer.toString(nos))){
				if(Integer.parseInt(list.get(i)[1])>bestNo){
					bestNo = Integer.parseInt(list.get(i)[1]);
					bestGroup = i;
				}
			}
		}
		
		return bestGroup;
	}
	
	

      
}


