package com.esgi.presentation.menus;

import com.esgi.presentation.AppLogger;
import com.esgi.presentation.AppLoggerColorEnum;
import com.esgi.presentation.menus.items.SubMenuItem;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class Menus {
    private final String title;
    private final List<MenuItem> menuItems;
    private final String navBar;

    public Menus(String title, List<MenuItem> menuItems, String navBar) {
        this.title = title;
        this.menuItems = menuItems;
        this.navBar = navBar;
    }

    public Menus(String title, List<MenuItem> menuItems, String navBar, Supplier<Menus> backMenu) {
        this.title = title;
        this.menuItems = menuItems;
        this.navBar = navBar;

        this.menuItems.add(new SubMenuItem("Cancel", backMenu));
    }

    public void display() {
        StringBuilder subMenus = new StringBuilder();
        AtomicInteger counter = new AtomicInteger();

        String title = "-".repeat(30) + "\n" +
                this.title +
                "\n" + navBar +
                "-".repeat(30);

       menuItems.forEach(menuItem -> {
                   int value = counter.incrementAndGet();
                   String newLine = String.format("%s. %s\n", value, menuItem.getTitle());
                   subMenus.append(newLine);
               });

       AppLogger.write(AppLoggerColorEnum.BLUE, title);
       AppLogger.info(subMenus.toString());
       handleUserInput();
    }


    private void handleUserInput() {
        int choice;
        Scanner scanner = new Scanner(System.in);

        AppLogger.info("Enter your choice: ");
        choice = scanner.nextInt();

        if (choice < 1 || choice > this.menuItems.size()) {
            AppLogger.error("You have entered an invalid choice. Try again.");
            handleUserInput();
        }

        this.menuItems.get(choice - 1).execute();

    }
}
