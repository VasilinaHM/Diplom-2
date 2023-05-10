import Praktikum.CreateOrder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
public class CreateOrderWithoutAuthorizationTest {
    private CreateOrder createOrder;
    @Before
    public void setUp() {
        createOrder = new CreateOrder();
    }
    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createNewOrderWithoutAuthorizationAndCheckResponse() {
        String json = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6f\",\"61c0c5a71d1f82001bdaaa6d\"]}";
        ValidatableResponse response = createOrder.create("", json);
        response.assertThat().statusCode(200).body("success", is(true));
    }
    @Test
    @DisplayName("Создание заказа без авторизации и без ингредиентов")
    public void createNewOrderWithoutAuthorizationAndWithoutIngredients() {
        String json = "{\"ingredients\": []}";
        ValidatableResponse response = createOrder.create("", json);
        response.assertThat().statusCode(400).body("message", is("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("Получение заказа не авторизованного пользователя")
    public void getOrderWithoutAuthorization() {
        ValidatableResponse response = createOrder.get("");
        response.assertThat().statusCode(401).body("message", is("You should be authorised"));
    }
}
