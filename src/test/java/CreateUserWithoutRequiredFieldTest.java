import Praktikum.CreateUser;
import Praktikum.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static Praktikum.UserGenerator.randomUser;
import static org.hamcrest.Matchers.is;

public class CreateUserWithoutRequiredFieldTest {
    private User user;
    private CreateUser createUser;

    @Before
    public void setUp() {
        user = randomUser();
        createUser = new CreateUser();
    }
    @Test
    @DisplayName("Создание пользователя без указания email")
    public void createUserNoEmail() {
        User users = new User("",user.getPassword(),user.getName());
        ValidatableResponse response = createUser.create(users);
        response.assertThat().statusCode(403).body("message", is("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без указания password")
    public void createUserNoPassword() {
        User users = new User(user.getEmail(),"",user.getName());
        ValidatableResponse response = createUser.create(users);
        response.assertThat().statusCode(403).body("message", is("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Создание пользователя без указания name")
    public void createUserNoName() {
        User users = new User(user.getEmail(),user.getPassword(),"");
        ValidatableResponse response = createUser.create(users);
        response.assertThat().statusCode(403).body("message", is("Email, password and name are required fields"));
    }
}
