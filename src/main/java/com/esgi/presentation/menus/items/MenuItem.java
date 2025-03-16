package com.esgi.presentation.menus.items;

import com.esgi.presentation.menus.Menu;

public interface  MenuItem {
    String getTitle();
    void execute(Menu menu);
    boolean needsAdmin();
}