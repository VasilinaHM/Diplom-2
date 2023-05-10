import Praktikum.CreateUser;
import Praktikum.RegisterUserBody;
import Praktikum.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Praktikum.LoginUser.credsFrom;
import static Praktikum.UserGenerator.randomUser;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;


public class CreateUserTest {
    private User user;
    private CreateUser createUser;
    private String accessToken;

    @Before
    public void setUp() {
        user = randomUser();
        createUser = new CreateUser();
    }

    @After
    public void tearDown() {
        ValidatableResponse loginResponse = createUser.login(credsFrom(user));
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse deleteResponse = createUser.delete(accessToken);
        deleteResponse.assertThat().statusCode(202).body("message", is("User successfully removed"));
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createNewUserAndCheckResponse() {
        ValidatableResponse  response = createUser.create(user);
        response.assertThat().statusCode(200).body("success", is(true))
                .extract().response().as(RegisterUserBody.class);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createDoubleUserAndCheck() {
        createUser.create(user);
        ValidatableResponse response = createUser.create(user);
        assertEquals("Статус код неверный при попытке создания пользователя, который уже зарегистрирован",
                HttpStatus.SC_FORBIDDEN, response.extract().statusCode());
        response.assertThat().body("message", is("User already exists"));
    }
}