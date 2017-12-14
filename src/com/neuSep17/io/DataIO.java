package com.neuSep17.io;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import com.neuSep17.dto.Incentive;
import com.neuSep17.dto.Vehicle;

public class DataIO {
	
	
	
	public static void saveToFile(File file, LinkedHashMap map) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(file));
			
			if(map.values() instanceof Vehicle) {
				writer.println("id~webId~category~year~make~model~trim~type~price~photo");
			}
			else if(map.values() instanceof Incentive) {
				writer.println("incentiveId~webId~title~discount~startDate~endDate~id,category,year,make,model,trim,type,price~description");
			}
			else {
				writer.close();
			}
			for (Object object : map.values()) {
				writer.println(object);
			}
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
