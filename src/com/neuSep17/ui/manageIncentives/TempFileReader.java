package com.neuSep17.ui.manageIncentives;

import com.neuSep17.dto.Incentive;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TempFileReader {

    public ArrayList<Incentive> read() throws IOException {
        ArrayList<Incentive> data = new ArrayList<>();
        String filename = "data/IncentiveSample.txt";
        FileReader reader = new FileReader(filename);
        BufferedReader in = new BufferedReader(reader);
        String line;

        line = in.readLine();
        int count = 0;
        while (line != null){
            if (count != 0){
                String[] s= line.split("~");
                Incentive incentive = new Incentive(s);
                data.add(incentive);
            }
            count++;
            line = in.readLine();
        }
        in.close();
        reader.close();
        return data;
    }

}
