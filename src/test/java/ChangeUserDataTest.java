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

public class ChangeUserDataTest {
    private User user;
    private CreateUser createUser;
    private String accessToken;


    @Before
    public void setUp() {
        user = randomUser();
        createUser = new CreateUser();
        createUser.create(user);
        ValidatableResponse loginResponse = createUser.login(credsFrom(user));
        accessToken = loginResponse.extract().path("accessToken");
    }
    @After
    public void tearDown() {
        ValidatableResponse deleteResponse = createUser.delete(accessToken);
        deleteResponse.assertThat().statusCode(202).body("message", is("User successfully removed"));
    }
    @Test
    @DisplayName("Изменение email пользователя с авторизацией")
    public void changeUserEmailWithAuthorization() {
        User users = new User("katy1234567@yandex.ru",user.getPassword(),user.getName());
        ValidatableResponse response = createUser.patch(accessToken, users);
        response.assertThat().statusCode(200).body("success", is(true));
    }
    @Test
    @DisplayName("Изменение password пользователя с авторизацией")
    public void changeUserPasswordWithAuthorization() {
        User users = new User(user.getEmail(), "12345678",user.getName());
        ValidatableResponse response = createUser.patch(accessToken, users);
        response.assertThat().statusCode(200).body("success", is(true));
    }
    @Test
    @DisplayName("Изменение name пользователя с авторизацией")
    public void changeUserNamedWithAuthorization() {
        User users = new User(user.getEmail(), user.getPassword(), "fjgdks56");
        ValidatableResponse response = createUser.patch(accessToken, users);
        response.assertThat().statusCode(200).body("success", is(true));
    }
    @Test
    @DisplayName("Изменение email пользователя без авторизации")
    public void changeUserEmailWithoutAuthorization() {
        User users = new User("katy1234567@yandex.ru",user.getPassword(),user.getName());
        ValidatableResponse response = createUser.patch("", users);
        response.assertThat().statusCode(401).body("message", is("You should be authorised"));
    }
    @Test
    @DisplayName("Изменение password пользователя без авторизации")
    public void changeUserPasswordWithoutAuthorization() {
        User users = new User(user.getEmail(), "12345678",user.getName());
        ValidatableResponse response = createUser.patch("", users);
        response.assertThat().statusCode(401).body("message", is("You should be authorised"));
    }
    @Test
    @DisplayName("Изменение name пользователя без авторизации")
    public void changeUserNameWithoutAuthorization() {
        User users = new User(user.getEmail(), user.getPassword(), "fjgdks56");
        ValidatableResponse response = createUser.patch("", users);
        response.assertThat().statusCode(401).body("message", is("You should be authorised"));
    }
}
