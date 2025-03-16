package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.loans.AddLoanItem;
import com.esgi.presentation.menus.items.loans.ReturnBookAdminItem;
import com.esgi.presentation.menus.items.loans.SeeHistoryAdminItem;
import com.esgi.presentation.menus.items.loans.SeeLoansAdminItem;

public class ManageLoansMenu extends Menu {
    public ManageLoansMenu(Menu previousMenu) {
        super("Manage Loans", "Loans", previousMenu);

        addItem(new AddLoanItem());
        addItem(new ReturnBookAdminItem());
        addItem(new SeeHistoryAdminItem());
        addItem(new SeeLoansAdminItem());
    }
}