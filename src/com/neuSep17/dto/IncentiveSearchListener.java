package com.neuSep17.dto;

import java.util.EventListener;

public interface IncentiveSearchListener extends EventListener {
    void searchEventOccurred(IncentiveSearchEvent se);
}

