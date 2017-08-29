package gr.homedeco.www.homedeco.server.response;

public class ServerResponse {

    private String message, emailError, usernameError;

    public ServerResponse() {
        this.message = "";
        this.emailError = "";
        this.usernameError = "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }
}
