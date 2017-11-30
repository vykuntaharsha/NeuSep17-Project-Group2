package com.neuSep17.dto;

import java.util.EventListener;

public interface IncentiveSortListener extends EventListener {
    void sortEventOccurred(IncentiveSortEvent ise);
}
