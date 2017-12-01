package com.neuSep17.ui.manageIncentives;

import java.util.EventListener;

public interface IncentiveFilterListener extends EventListener{
    void filterEventOccurred(IncentiveFilterEvent fe);
}
