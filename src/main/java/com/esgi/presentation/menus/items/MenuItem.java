package com.esgi.presentation.menus.items;

import com.esgi.presentation.AppLogger;
import com.esgi.presentation.menus.Menu;

import java.util.Scanner;

public interface  MenuItem {
    String getTitle();
    void execute(Menu menu) throws Exception;
    boolean needsAdmin();

    default void awaitUserInput(Menu menu){
        Scanner scanner = new Scanner(System.in);

        AppLogger.info("Press [ENTER] to continue...");
        scanner.nextLine();
        menu.display();
    }
}