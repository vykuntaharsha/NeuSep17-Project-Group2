package com.neuSep17.dto;

public enum Category {

    NEW, USED, CERTIFIED;
        
        
    public static Category getCategory(String cat){
        switch (cat){
           case "used": return USED;
           case "new": return NEW;
           case "certified": return CERTIFIED;
           default: throw new IllegalArgumentException();
       }
    }

    @Override
    public String toString() {
        switch (this){
           case NEW: return "NEW";
           case USED: return "USED";
           case CERTIFIED: return "CERTIFIED";
           default: throw new IllegalArgumentException();
        }
    }
}
