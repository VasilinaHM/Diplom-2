package Praktikum;
import static Praktikum.Utils.randomString;
public class UserGenerator {
    public static User randomUser() {
        return new User(randomString(6) + "@yandex.ru", randomString(8), randomString(10));

    }
}


