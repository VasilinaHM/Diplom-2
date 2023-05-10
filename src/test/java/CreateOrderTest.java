import Praktikum.CreateOrder;
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

public class CreateOrderTest {
    private CreateOrder createOrder;
    private User user;
    private CreateUser createUser;
    private String accessToken;

    @Before
    public void setUp() {
        user = randomUser();
        createOrder = new CreateOrder();
        createUser = new CreateUser();
        ValidatableResponse response = createUser.create(user);
        accessToken = response.extract().path("accessToken");
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createNewOrderWithAuthorizationAndCheckResponse() {
        String json = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa73\",\"61c0c5a71d1f82001bdaaa6d\"]}";
        ValidatableResponse response = createOrder.create(accessToken, json);
        response.assertThat().statusCode(200).body("success", is(true));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов с авторизацией")
    public void createNewOrderWithoutIngredientsAndWithAuthorization() {
        String json = "{\"ingredients\": []}";
        ValidatableResponse response = createOrder.create(accessToken, json);
        response.assertThat().statusCode(400).body("message", is("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createNewOrderNoValidIngredients() {
        String json = "{\"ingredients\": [\"6666\"]}";
        ValidatableResponse response = createOrder.create(accessToken, json);
        response.assertThat().statusCode(500);
    }

    @Test
    @DisplayName("Получение заказа авторизованного пользователя")
    public void getOrderWithAuthorization() {
        String json = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa73\",\"61c0c5a71d1f82001bdaaa6d\"]}";
        createOrder.create(accessToken, json);
        ValidatableResponse responses = createOrder.get(accessToken);
        responses.assertThat().statusCode(200).body("success", is(true));
    }

    @After
    public void tearDown() {
        ValidatableResponse loginResponse = createUser.login(credsFrom(user));
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse deleteResponse = createUser.delete(accessToken);
        deleteResponse.assertThat().statusCode(202).body("message", is("User successfully removed"));
    }
}
