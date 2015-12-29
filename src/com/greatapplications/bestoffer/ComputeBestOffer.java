/**

 Copyright 2014 Bortoli Tomas

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */

package com.greatapplications.bestoffer;

import java.io.*;
import java.util.ArrayList;


public class ComputeBestOffer {
	
	public static boolean checkOffers(String path) throws IOException{
		
		String str=null;
		try{
			
			
			//Read the file of offers
			File file = new File(path);
		    FileInputStream fis = new FileInputStream(file);
		    byte[] data = new byte[(int)file.length()];
		    fis.read(data);
		    fis.close();
		    //
		    str = new String(data);


		}
		catch(Exception e){ 
			System.out.println("Error in reading the file. Maybe does not exist? name:"+path);
			return false;
			}
		finally{
		}
		
	    //Initially, split the file in his three parts, rates, offers and options.
	    str=str.replace('\r', ' ');
	    String[] parts=str.split("&");
	    
	    if(parts.length<3){
	    	System.out.println("Invalid number of sections");
	    	return false;
	    }
	    
	    int line=1;
	    boolean error=false;
	    /***************rates************/
	    String[] tmp=parts[0].split("\n");
	    ArrayList<Rate> rates = new ArrayList<Rate>();
	    
	    for(int i=0;i<tmp.length;i++){
	    	rates.add(new Rate(tmp[i]));
	    	error|=rates.get(i).error;
	    	if(error) {
		    	System.out.println("error on line: "+line);
				return false;
			}
	    	line++;
	    }
	    
	    /***************offers************/
	    tmp=parts[1].split("\n");
	    ArrayList<Offer> offers = new ArrayList<Offer>();
	    line++;
	    for(int i=1;i<tmp.length;i++){
	    	offers.add(new Offer(tmp[i]));
	    	error|=offers.get(i-1).error;
	    	if(error) {
		    	System.out.println("error on line: "+line);
				return false;
			}
	    	line++;
	    }
	    
	    /***************options************/
	    tmp=parts[2].split("\n");
	    ArrayList<Option> options = new ArrayList<Option>();
	    
	    for(int i=1;i<tmp.length;i++){
	    	options.add(new Option(tmp[i]));
	    	error|=options.get(i-1).error;
	    	if(error) {
		    	System.out.println("error on line: "+line);
				return false;
			}
	    	line++;
	    }
	    
	    /**********************************/
	    
	    return true;
	}
	
}
