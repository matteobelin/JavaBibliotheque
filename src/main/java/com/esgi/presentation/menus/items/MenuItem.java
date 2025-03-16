package com.esgi.presentation.menus.items;

public interface  MenuItem {
    String getTitle();
    void execute() throws Exception;
    boolean needsAdmin();
}