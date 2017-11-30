package com.neuSep17.dto;

import java.util.EventListener;

public interface IncentiveFilterListener extends EventListener{
    void filterEventOccurred(IncentiveFilterEvent fe);
}
