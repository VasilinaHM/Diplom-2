package Praktikum;

public class RegisterUserBody {
    private boolean success;
    private UserData userData;
    private String accessToken;
    private String refreshToken;

    public RegisterUserBody(boolean success, UserData userData, String accessToken, String refreshToken) {
        this.success = success;
        this.userData = userData;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public RegisterUserBody(){
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

