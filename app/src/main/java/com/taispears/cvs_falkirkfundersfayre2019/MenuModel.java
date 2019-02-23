package com.taispears.cvs_falkirkfundersfayre2019;

public class MenuModel {

    public String menuName, itemID;
    public boolean hasChildren, isGroup;


    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, String itemID) {

        this.menuName = menuName;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.itemID = itemID;
    }


    public String getItemID() {
        return itemID;
    }


}