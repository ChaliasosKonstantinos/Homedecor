package gr.homedeco.www.homedeco.server.callbacks;

import gr.homedeco.www.homedeco.user.User;

public interface GetUserDetailsCallback {
    void done(User returnedUser);
}
