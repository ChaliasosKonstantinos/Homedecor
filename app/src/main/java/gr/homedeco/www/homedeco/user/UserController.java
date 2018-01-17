package gr.homedeco.www.homedeco.user;

import android.content.Context;

import gr.homedeco.www.homedeco.localDatabase.LocalDatabase;

public class UserController {

    private Context context;
    private LocalDatabase localDatabase;

    public UserController(Context context) {
        this.context = context;
        localDatabase = new LocalDatabase(context);
    }

    public boolean isUserLoggedIn() {
        return localDatabase.isLoggedIn();
    }

    public void setUserDetails(User user) {
        localDatabase.setUserDetails(user);
    }

    public User getUserDetails() {
        return localDatabase.getUserDetails();
    }

    public boolean isRemembered() {
        User user = localDatabase.getRememberMe();
        return !user.getUsername().isEmpty();
    }

    public void setRemembered(User user) {
        localDatabase.setRememberMe(user);
    }

    public User getRemembered() {
        return localDatabase.getRememberMe();
    }

    public boolean loginUser(String authToken) {
        localDatabase.setLoggedIn(true, authToken);
        return localDatabase.isLoggedIn();
    }

    public boolean logoutUser() {
        localDatabase.setLoggedIn(false, "");
        return !localDatabase.isLoggedIn();
    }
}
