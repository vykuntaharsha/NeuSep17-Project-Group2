package com.neuSep17.dto;

import java.util.ArrayList;                                                                                                                 //~VIN,category,........................................,price~  this is the criterion part     
// Note: Incentive.java class should be made following the format of "incentives.txt" file: incentiveId~webId~title~discount~startDate~endDate~VIN(or all, or no),category,year,make,model,trim,type,price~description
public class Incentive {

    private String id;
    private String webId;
    private String title;
    private Float discount;
    private String startDate;
    private String endDate;
    private ArrayList<String> criterion = new ArrayList<String>();
    private String description;
    
    
    Incentive(String[] arr) {
        this.id = arr[0];
        this.webId = arr[1];
        this.title = arr[2];
        this.discount = Float.parseFloat(arr[3]);
        this.startDate = arr[4];
        this.endDate =arr[5];
        	
        String[] criterionArr = arr[6].split(",");
        for(String data : criterionArr) {
          criterion.add(data.trim());
        }
        
        this.description = arr[7];
        
    }
    
    @Override
    public String toString() {
    		String str= this.id+"~"+this.webId+"~"+this.title+"~"+this.discount+"~"+this.startDate
    				+"~"+this.endDate+"~"+this.criterion.toString().replaceAll("[\\[|\\]| ]", "")+"~"+this.description;
    		return str;
    }

	public String getId() {
		return id;
	}

	public String getWebId() {
		return webId;
	}

	public String getTitle() {
		return title;
	}

	public Float getDiscount() {
		return discount;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public ArrayList<String> getCriterion() {
		return criterion;
	}

	public String getDescription() {
		return description;
	}

 
}
