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

public class AuthorizationUserTest {
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
    @DisplayName("Авторизация пользователя")
    public void authorizationUserAndAndCheckResponse() {
        ValidatableResponse loginResponse = createUser.login(credsFrom(user));
        loginResponse.assertThat().statusCode(200).body("success", is(true));
        accessToken = loginResponse.extract().path("accessToken");
    }
    @After
    public void tearDown() {
        ValidatableResponse deleteResponse = createUser.delete(accessToken);
        deleteResponse.assertThat().statusCode(202).body("message", is("User successfully removed"));
    }
}
