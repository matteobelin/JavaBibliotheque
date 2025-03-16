package com.esgi.domain;

import com.esgi.domain.users.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppContext {
    private UserEntity loggedInUser;

    private AppContext(){}

    private static class Holder{
        private static final AppContext INSTANCE = new AppContext();
    }

    public static AppContext getInstance(){
        return Holder.INSTANCE;
    }

    public boolean isLoggedIn(){
        return loggedInUser != null;
    }
    public boolean isAdmin(){
        return isLoggedIn() && loggedInUser.isAdmin();
    }
    public String getUserName(){
        if(!isLoggedIn()){
            return "";
        }
        return loggedInUser.getName();
    }
}
