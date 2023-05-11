package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        Configuration.holdBrowserOpen = true;

        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе

        open("http://localhost:9999");
        DataGenerator.UserInfo userInfo = validUser;
        $("[data-test-id=city] input").setValue(userInfo.getCity());                                            //заполнение поля город
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);                                     //очистка поля дата
        $("[data-test-id=date] input").setValue(firstMeetingDate);                                              //заполнение поля дата
        $("[data-test-id=name] input").setValue(userInfo.getName());                                            //заполнение поля имя
        $("[data-test-id=phone] input").setValue(userInfo.getPhone());                                          //заполнение поля телефон
        $("[data-test-id=agreement]").click();                                                                  //заполнение чек бокса
        $(".button").shouldHave(Condition.text("Запланировать")).click();                                       //нажатие кнопки "Запланировать"
        $("[data-test-id=success-notification]").shouldBe(Condition.visible)                                    //проверка popup
                .shouldHave(Condition.text("Успешно! Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);                                     //очистка поля дата
        $("[data-test-id=date] input").setValue(secondMeetingDate);                                             //заполнение поля дата
        $(".button").shouldHave(Condition.text("Запланировать")).click();                                       //нажатие кнопки "Запланировать"
        $("[data-test-id=replan-notification]").shouldBe(Condition.visible)                                     //проверка popup
                .shouldHave(Condition.text("Необходимо подтверждение" +
                                " У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=replan-notification] .button").shouldHave(Condition.text("Перепланировать")).click();  //нажатие кнопки "перепланировать"
        $("[data-test-id=success-notification]").shouldBe(Condition.visible)                                    //проверка popup
                .shouldHave(Condition.text("Успешно! Встреча успешно запланирована на " + secondMeetingDate));
    }
}
