package wheelOfFortune;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class phraseGenerator {

	/* source textfile can't have more than
	 * 1000 possible phrases otherwise this breaks
	 */
	  	private String[] phrase;
	    private String[] category;
	    private int index;
	    private String thisphrase;
	    private int cataryindex;
	    private int phrasearyindex;
	    private int maxIndex;
	    private Random rn;
	    
	public phraseGenerator(String fileName) throws IOException {
		 Scanner sc = new Scanner(getClass().getResourceAsStream(fileName));
		 	index = 0;
		 	cataryindex=0;
		 	rn = new Random();
		 	phrasearyindex=0;
	        category = new String[1000];
	        phrase = new String[1000];

	        while(sc.hasNextLine()){
	            
	            String line = sc.nextLine();
	            Scanner scr = new Scanner(line);
	            scr.useDelimiter(";");
	            
	            while(scr.hasNext()){
	                thisphrase = scr.next();
	                
	                if (index % 2 == 0) //this is an even number index and is therefore a category
	                {
	                	category[cataryindex] = thisphrase;
	                    cataryindex++;
	                    index++;
	                }
	                else if (index % 2 != 0) //this is an odd number index and is therefore a phrase
	                {
	                    phrase[phrasearyindex] = thisphrase;
	                    phrasearyindex++;
	                    index++;
	                }
	                
	            }
	            scr.close();
	            maxIndex = (index/2);
	            
	        }
	     
		sc.close();
		
	}
	
	public void pickNewPhrase()
	{
		index = rn.nextInt(maxIndex);
	}
	
	public String getCategory()
	{

		return category[index];	
	}
	
	public String getPhrase()
	{
		return phrase[index];	
	}
	

}
