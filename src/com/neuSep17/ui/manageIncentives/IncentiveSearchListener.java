package com.neuSep17.ui.manageIncentives;

import java.util.EventListener;

public interface IncentiveSearchListener extends EventListener {
    void searchEventOccurred(IncentiveSearchEvent se);
}

