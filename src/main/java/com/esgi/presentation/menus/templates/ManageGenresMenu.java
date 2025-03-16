package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.Genres.AddGenreItem;
import com.esgi.presentation.menus.items.Genres.EditGenreItem;
import com.esgi.presentation.menus.items.Genres.RemoveGenreItem;

public class ManageGenresMenu extends Menu {
    public ManageGenresMenu(Menu previousMenu) {
        super("Manage Genres", "Genres", previousMenu);

        addItem(new AddGenreItem());
        addItem(new EditGenreItem());
        addItem(new RemoveGenreItem());
    }
}
