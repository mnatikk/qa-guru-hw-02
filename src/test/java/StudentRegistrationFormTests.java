import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class StudentRegistrationFormTests {
    Faker faker = new Faker(new Locale("ru"));
    Faker fakerEmail = new Faker(new Locale("en"));

    String firstname = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = fakerEmail.internet().emailAddress();
    String gender = "gender-radio-"+ (faker.number().numberBetween(1,3)) ;
    String genderName = "";
    String phoneNumber = faker.phoneNumber().subscriberNumber(10);
    String dateOfBirth = "10 May,1980";
    String subj = "Math";
    String hobby = "Sports";
    File picture = new File("src/test/resources/wall-e.png");
    String address = faker.address().fullAddress();
    String state = "NCR";
    String city = "Delhi";

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
        $("#firstName").setValue(firstname);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);

        genderName = $("label[for='"+ gender+"']").getText();
        $("label[for='"+ gender+"']").click();

        $("#userNumber").setValue(phoneNumber);

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOptionByValue("4");
        $(".react-datepicker__year-select").selectOptionByValue("1980");
        $(".react-datepicker__day--010").click();

        $("#subjectsInput").setValue(subj).pressEnter();
        $("#hobbiesWrapper").$(byText(hobby)).click();
        $("#uploadPicture").uploadFile(picture);
        $("#currentAddress").setValue(address);
        $("#react-select-3-input").setValue(state).pressEnter();
        $("#react-select-4-input").setValue(city).pressEnter();
        $("#submit").pressEnter();


        //Check Results Table
        $(".table-responsive").shouldBe(Condition.visible);
        checkTableResults("Student Name", firstname +" " +lastName);
        checkTableResults("Student Email", email);
        checkTableResults("Gender", genderName);
        checkTableResults("Mobile", phoneNumber);
        checkTableResults("Date of Birth", dateOfBirth);
        checkTableResults("Subjects", subj);
        checkTableResults("Hobbies", hobby);
        checkTableResults("Picture", picture.getName());
        checkTableResults("Address", address);
        checkTableResults("State and City", state + " " + city);
    }

    public SelenideElement checkTableResults(String key, String value){
        return $(".table-responsive table").$(byText(key))
                .parent().shouldHave(Condition.textCaseSensitive(value));
    }
}
