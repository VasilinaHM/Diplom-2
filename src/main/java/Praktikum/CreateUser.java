package Praktikum;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CreateUser {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site/";

    public CreateUser() {
        RestAssured.baseURI = BASE_URI;
    }

    @Step("Создание пользователя POST api/auth/register")
    public ValidatableResponse create(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("api/auth/register")
                .then();
    }
    @Step("Авторизация пользователя POST api/auth/login")
    public ValidatableResponse login(LoginUser loginUser) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginUser)
                .when()
                .post("api/auth/login")
                .then();
    }
    @Step("Удаление пользователя DELETE api/auth/user")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .header("Authorization",accessToken)
                .and()
                .when()
                .delete("api/auth/user")
                .then();
    }
    @Step("Изменение данных пользователя с авторизацией PATCH api/auth/user")
    public ValidatableResponse patch(String accessToken, User user) {
        return given()
                .header("Authorization",accessToken)
                .and()
                .body(user)
                .when()
                .patch("api/auth/user")
                .then();
    }
}
