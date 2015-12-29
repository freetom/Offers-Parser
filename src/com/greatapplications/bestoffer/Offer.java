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

import java.util.ArrayList;

public class Offer {

    public String operator;
    public int credits;
    public short min_vs_same_operator;
    public short min;
    public short sms_vs_same_operator;
    public short sms;
    public short dataTraffic;
    public short maxAge;
    public boolean youAndMe;
    public short portability;
    public short billWithoutChange; //in cent
    public short[] freeOptions;

    public short price; //in cent
    public short riseOfPriceAfterPeriod; //in cent
    public float monthBeforeIncreasing;
    public String offerName;
    public String urlOffer;
    public short[] options; //indicates the ids of the options
    public String moreInfos;

    public boolean error;
	
	//constructor by cloning
	public Offer(Offer o){
		this.operator=o.operator;
		this.credits=o.credits;
		this.min_vs_same_operator=o.min_vs_same_operator;
		this.min=o.min;
		this.sms_vs_same_operator=o.sms_vs_same_operator;
		this.sms=o.sms;
		this.dataTraffic=o.dataTraffic;
		this.maxAge=o.maxAge;
		this.youAndMe=o.youAndMe;
		this.portability=o.portability;
		this.billWithoutChange=o.billWithoutChange;
		if(o.freeOptions!=null){
			this.freeOptions=new short[o.freeOptions.length];
			for(int i=0;i<o.freeOptions.length;i++)
				this.freeOptions[i]=o.freeOptions[i];
		}
		this.price=o.price;
		this.riseOfPriceAfterPeriod=o.riseOfPriceAfterPeriod;
		this.monthBeforeIncreasing=o.monthBeforeIncreasing;
		this.offerName=o.offerName;
		this.urlOffer=o.urlOffer;
		if(o.options!=null){
			this.options=new short[o.options.length];
			for(int i=0;i<o.options.length;i++)
				this.options[i]=o.options[i];
		}
		this.error=o.error;
	}
	

	public Offer(String offer){
		error=false;
		
		String[] fields=offer.split(" ");
		try{
			operator=fields[0];
			min_vs_same_operator=Short.parseShort(fields[1]);
			min=Short.parseShort(fields[2]);
			sms_vs_same_operator=Short.parseShort(fields[3]);
			sms=Short.parseShort(fields[4]);
			dataTraffic=Short.parseShort(fields[5]);
			maxAge=Short.parseShort(fields[6]);
			youAndMe=(Short.parseShort(fields[7])==1);
			portability=Short.parseShort(fields[8]);
			
			short i,counter=0;
			if(fields[9].charAt(0)=='*'){
				billWithoutChange=Short.parseShort(fields[9].substring(1));
				i=10;
			}
			else{
				billWithoutChange=0;
				i=9;
			}
			
			while(fields[i].charAt(0)=='$'){
				counter++; i++;
			}
			if(counter>0){
				freeOptions=new short[counter];
				short j=0;
				while(j<counter){
					freeOptions[j]=Short.parseShort(fields[i-counter+j].substring(1));
					j++;
				}
			}
			else freeOptions=new short[0];
			
			
			price=Short.parseShort(fields[i++]);
			riseOfPriceAfterPeriod=Short.parseShort(fields[i++]);
			monthBeforeIncreasing=Float.parseFloat(fields[i++]);
			
			
			credits=Integer.parseInt(fields[i++]);
			
			
			offerName=fields[i++].replace('_', ' ');
			urlOffer=fields[i++];
			
			short j=i; short cont=0;
			if(j<fields.length)
				while(fields[j++].charAt(0)=='$'){
					cont++;
					
					if(j<fields.length)
						;
					else
						break;
				}
			
			if(cont<=0) options=new short[0];
			else{
				options=new short[cont];
				j=0;
				while(j<cont){
					options[j++]=Short.parseShort(fields[i++].substring(1));
				}
			}
			
			//concat the free options, with the options, to semplify the code that calculate all the offers
			options=concat(options,freeOptions);
			
			
			if(i>=fields.length)
				return;
			
			moreInfos=fields[i];
			
		}
		catch(Exception e){
			
			error=true;
		}
		
		
	}
	
	private static short[] concat(short[] array1, short[] array2){
		
		short[] ret=new short[array1.length+array2.length];
		int i;
		for(i=0;i<array1.length;i++)
			ret[i]=array1[i];
		
		for(int j=0;j<array2.length;j++)
			ret[i++]=array2[j];
		
		return ret;
	}
	
	private static int contains(short[] array, short value){
		
		//special case for options always included
		if(value<-1)
			return value;
		
		for(int i=0;i<array.length;i++){
			if(array[i]==value)
				return i;
		}
		return -1;
	}
	
	private static ArrayList<Option> copy(ArrayList<Option> al){
		ArrayList<Option> ret=new ArrayList<Option>();
		for(int i=0;i<al.size();i++) ret.add(al.get(i));
		return ret;
	}
	
	
}
