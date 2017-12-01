package com.neuSep17.ui.manageIncentives;

import java.util.ArrayList;
import java.util.EventObject;

public class IncentiveFilterEvent extends EventObject {
    private ArrayList<String> filterList;

    public IncentiveFilterEvent(Object source) {
        super(source);
    }

    public IncentiveFilterEvent(Object source, ArrayList<String> filterList){
        super(source);
        this.filterList = filterList;
    }

    public void setFilterList(ArrayList<String> filterList) {
        this.filterList = filterList;
    }

    public ArrayList<String> getFilterList() {
        return filterList;
    }
}
