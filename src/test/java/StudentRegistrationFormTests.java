import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class StudentRegistrationFormTests {

    String name = "Cat";
    String lastName = "Caters";
    String email = "cat.caters@mail.com";
    String gender = "Male";
    String phoneNumber = "1234567891";

    @BeforeAll
    static void beforeAll() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1980x1080";
        Configuration.baseUrl = "https://demoqa.com";

        open("https://demoqa.com/automation-practice-form");
    }

    @BeforeEach
     void beforeEach() {
        Selenide.refresh();
    }

    @Test
    void fillOnlyRequiredFields() {
        $("#firstName").setValue(name);
        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        $("#submit").pressEnter();
        $("[class=table-responsive]").shouldBe(Condition.visible);
    }

    void fillOnlyRequiredFieldsAndEmail() {
        $("#firstName").setValue(name);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        $("#submit").pressEnter();
        $("[class=table-responsive]").shouldBe(Condition.visible);
    }

}
