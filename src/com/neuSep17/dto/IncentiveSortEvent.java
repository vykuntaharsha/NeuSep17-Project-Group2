package com.neuSep17.dto;

import java.util.EventObject;

public class IncentiveSortEvent extends EventObject {
    private int selectedIndex;

    public IncentiveSortEvent(Object source) {
        super(source);
    }

    public IncentiveSortEvent(Object source, int selectedIndex) {
        super(source);
        this.selectedIndex = selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}
