package com.esgi.presentation.menus;

import lombok.Getter;

@Getter
public abstract class MenuItem {
    private final String title;

    public MenuItem(String title) {
        this.title = title;
    }

    public abstract void execute();
}