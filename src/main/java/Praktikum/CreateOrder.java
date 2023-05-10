package Praktikum;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CreateOrder {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site/";

    public CreateOrder() {
        RestAssured.baseURI = BASE_URI;
    }
    @Step("Создание заказа  POST api/orders")
    public ValidatableResponse create(String accessToken, String json) {
        return given()
                .header("Authorization",accessToken)
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("api/orders")
                .then();
    }
    @Step("Получение заказа авторизованного пользователя GET api/orders")
    public ValidatableResponse get(String accessToken) {
        return given()
                .header("Authorization",accessToken)
                .header("Content-type", "application/json")
                .and()
                .get("api/orders")
                .then();
    }
    @Step("Получение списка ингредиентов заказа")
    public ValidatableResponse getList() {
        return given()
                .get("api/ingredients")
                .then();
    }
}