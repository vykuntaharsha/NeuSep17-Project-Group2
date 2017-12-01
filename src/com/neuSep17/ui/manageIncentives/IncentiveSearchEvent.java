package com.neuSep17.ui.manageIncentives;

import java.util.EventObject;

public class IncentiveSearchEvent extends EventObject {

    private String searchContent;

    public IncentiveSearchEvent(Object source) {
        super(source);
    }

    public IncentiveSearchEvent(Object source, String searchContent) {
        super(source);
        this.searchContent = searchContent;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
}
