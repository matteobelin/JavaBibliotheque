package com.esgi.presentation.menus;

import com.esgi.domain.AppContext;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.AppLoggerColorEnum;
import com.esgi.presentation.menus.items.MenuItem;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Menu {
    @Getter
    private final String title;
    private final List<MenuItem> items;
    private final List<String> navbar;
    private final Menu previousMenu;

    public Menu(String title) {
        this.title = title;
        this.items = new ArrayList<>();
        this.navbar = new ArrayList<>();
        this.previousMenu = null;
    }

    public Menu(String title, String navTitle) {
        this.title = title;
        this.items = new ArrayList<>();
        this.navbar = new ArrayList<>();
        this.navbar.add(navTitle);
        this.previousMenu = null;
    }

    public Menu(String title, String navTitle, Menu previousMenu) {
        this.title = title;
        this.items = new ArrayList<>();
        this.navbar = new ArrayList<>(previousMenu.navbar);
        this.navbar.add(navTitle);
        this.previousMenu = previousMenu;
    }

    public void display() {
        StringBuilder subMenus = new StringBuilder();
        StringBuilder header = new StringBuilder();
        AtomicInteger counter = new AtomicInteger();
        List<MenuItem> displayedList;

        header.append("-".repeat(30)).append("\n")
                .append(title).append("\n")
                .append(String.join(" > ", navbar))
                .append("\n")
                .append("-".repeat(30));


        displayedList = items
                .stream()
                .filter(menuItem -> AppContext.getInstance().isAdmin() || !menuItem.needsAdmin()).toList();

        displayedList.forEach(menuItem -> {
            int value = counter.incrementAndGet();
            String newLine = String.format("%s. %s\n", value, menuItem.getTitle());
            subMenus.append(newLine);
        });

        if (previousMenu != null) {
            subMenus.append("0. cancel");
        } else {
            subMenus.append("0. exit");
        }

        AppLogger.write(AppLoggerColorEnum.BLUE, header.toString());
        AppLogger.info(subMenus.toString());
        handleUserInput(displayedList);
    }

    private void handleUserInput(List<MenuItem> displayedList) {
        int choice;
        Scanner scanner = new Scanner(System.in);

        AppLogger.info("Enter your choice: ");
        String input = scanner.nextLine();
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            AppLogger.error("Invalid input. Please try again.");
            handleUserInput(displayedList);
            return;
        }

        if (choice == 0){
            if (previousMenu != null) {
                previousMenu.display();
                return;
            }
            AppLogger.success("See you soon!");
            return;
        }

        if (choice < 1 || choice > displayedList.size()) {
            AppLogger.error("Invalid choice. Please try again.");
            handleUserInput(displayedList);
            return;
        }

        try {
            displayedList.get(choice - 1).execute();
        } catch (Exception e) {
            AppLogger.error("An error occurred. Please try again.");
            display();
        }
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }
}
