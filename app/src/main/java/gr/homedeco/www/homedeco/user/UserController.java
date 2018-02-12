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

    /**
     * Returns if a user is currently logged in
     *
     * @return TRUE if a user is logged in
     *         FALSE if a user is not logged in
     */
    public boolean isUserLoggedIn() {
        return localDatabase.isLoggedIn();
    }

    /**
     * Saves user's details to Local storage
     *
     * @param user a User object containing the user's details
     */
    public void setUserDetails(User user) {
        localDatabase.setUserDetails(user);
    }

    /**
     * Fetches user's details from Local storage
     *
     * @return a User object containing the user's details
     */
    public User getUserDetails() {
        return localDatabase.getUserDetails();
    }

    /**
     * Returns if a user has chosen for his/her login credentials to be remembered
     *
     * @return TRUE if user's credentials are remembered
     *         FALSE if user's credentials are not remembered
     */
    public boolean isRemembered() {
        User user = localDatabase.getRememberMe();
        return !user.getUsername().isEmpty();
    }

    /**
     * Saves user's login credentials to Local storage
     *
     * @param user a User object containing the user's login credentials
     */
    public void setRemembered(User user) {
        localDatabase.setRememberMe(user);
    }

    /**
     * Returns user's login credentials from Local storage
     *
     * @return  a User object containing the user's login credentials
     */
    public User getRemembered() {
        return localDatabase.getRememberMe();
    }

    /**
     * Marks a user as logged in and saves his/her authentication token to Local storage
     *
     * @param authToken an alphanumeric authentication token
     *
     * @return TRUE if user was marked as logged in successfully
     *         FALSE if user was not marked as logged in successfully
     */
    public boolean loginUser(String authToken) {
        localDatabase.setLoggedIn(true, authToken);
        return localDatabase.isLoggedIn();
    }

    /**
     * Logs user out
     *
     * @return TRUE if user was logged out successfully
     *         FALSE if user was logged out successfully
     */
    public boolean logoutUser() {
        localDatabase.setLoggedIn(false, "");
        return !localDatabase.isLoggedIn();
    }
}
