import Praktikum.CreateUser;
import Praktikum.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Praktikum.LoginUser.credsFrom;
import static Praktikum.UserGenerator.randomUser;
import static org.hamcrest.Matchers.is;

public class AuthorizationUserNoValidDataTest {
    private User user;
    private CreateUser createUser;
    private String accessToken;
    @Before
    public void setUp() {
        user = randomUser();
        createUser = new CreateUser();
        createUser.create(user);
    }
    @Test
    @DisplayName("Авторизация пользователя с неверным email")
    public void authorizationUserNoValidEmail() {
        User users = new User("6",user.getPassword(),user.getName());
        ValidatableResponse loginResponse = createUser.login(credsFrom(users));
        loginResponse.assertThat().statusCode(401).body("message", is("email or password are incorrect"));
    }
    @Test
    @DisplayName("Авторизация пользователя с неверным password")
    public void authorizationUserNoValidPassword() {
        User users = new User(user.getEmail(),"6",user.getName());
        ValidatableResponse loginResponse = createUser.login(credsFrom(users));
        loginResponse.assertThat().statusCode(401).body("message", is("email or password are incorrect"));
    }
    @After
    public void tearDown() {
        ValidatableResponse loginResponse = createUser.login(credsFrom(user));
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse deleteResponse = createUser.delete(accessToken);
        deleteResponse.assertThat().statusCode(202).body("message", is("User successfully removed"));
    }
}