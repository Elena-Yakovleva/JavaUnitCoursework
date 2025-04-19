package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class PayPage {
    private final SelenideElement pay = $(Selectors.byText("Оплата по карте"));
    private final SelenideElement button = $(Selectors.byText("Продолжить"));
    private final SelenideElement notificationApproved = $(Selectors.byText("Операция одобрена Банком."));
    private final SelenideElement notificationDeclined = $(Selectors.byText("Ошибка! Банк отказал в проведении операции."));

    // Поля для заполнения
    private final SelenideElement fieldCardNumber = $x("//span[text()='Номер карты']/..//input");
    private final SelenideElement fieldCardMonth = $x("//span[text()='Месяц']/..//input");
    private final SelenideElement fieldCardYear = $x("//span[text()='Год']/..//input");
    ;
    private final SelenideElement fieldCardHolder = $x("//span[text()='Владелец']/..//input");
    private final SelenideElement fieldCardCVC = $x("//span[text()='CVC/CVV']/..//input");

    // Ошибки полей ввода
    private final SelenideElement errorInvalidFormat = $x("//span[contains(text(), 'Неверный формат')]");
    private final SelenideElement errorEmptyForm = $x("//span[contains(text(), 'Поле обязательно для заполнения')]");
    private final SelenideElement errorIncorrectDeadline = $x("//span[contains(text(), 'Неверно указан срок действия карты')]");
    private final SelenideElement errorExpiredDeadline = $x("//span[contains(text(), 'Истёк срок действия карты')]");
    private final SelenideElement errorMaximumLength = $x("//span[contains(text(), 'Длина не может быть больше 20 символов')]");

    public PayPage() {
        pay.shouldBe(Condition.visible);
    }

    public void getEnterCard(DataHelper.CardDetails cardDetails) {
        fieldCardNumber.setValue(cardDetails.getCardNumber());
        fieldCardMonth.setValue(cardDetails.getCardMonth());
        fieldCardYear.setValue(cardDetails.getCardYear());
        fieldCardHolder.setValue(cardDetails.getCardHolder());
        fieldCardCVC.setValue(cardDetails.getCardCVC());
    }

    public void clickContinueButton() {
        button.click();
    }

    // Информационные сообщения

    public void findNotificationApproved() {
        notificationApproved.shouldBe(Condition.visible, Duration.ofSeconds(10)).shouldBe(Condition.text("Операция одобрена Банком."));
    }

    public void findNotificationDeclined() {
        notificationDeclined.shouldBe(Condition.visible, Duration.ofSeconds(10)).shouldBe(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    public void submitFormWithEmptyFields() {
        errorInvalidFormat.shouldBe(Condition.visible).shouldHave(Condition.text("Неверный формат"));
        errorEmptyForm.shouldBe(Condition.visible).shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    public void getErrorInvalidFormat() {
        errorInvalidFormat.shouldBe(Condition.visible).shouldHave(Condition.text("Неверный формат"));
    }

    public void getErrorEmptyForm() {
        errorEmptyForm.shouldBe(Condition.visible).shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    public void getErrorIncorrectDeadline() {
        errorIncorrectDeadline.shouldBe(Condition.visible).shouldHave(Condition.text("Неверно указан срок действия карты"));
    }

    public void getErrorExpiredDeadline() {
        errorExpiredDeadline.shouldBe(Condition.visible).shouldHave(Condition.text("Истёк срок действия карты"));
    }

    public void getErrorMaximumLength() {
        errorMaximumLength.shouldBe(Condition.visible).shouldHave(Condition.text("Длина не может быть больше 20 символов"));
    }

    // Ожидаемый результат
    public SelenideElement getCardNumberValue(String expectedNumber) {
        return fieldCardNumber.shouldHave(Condition.value(expectedNumber));
    }

    public SelenideElement getCardMonthValue(String expectedMonth) {
        return fieldCardMonth.shouldHave(Condition.value(expectedMonth));
    }

    public SelenideElement getCardYearValue(String expectedYear) {
        return fieldCardYear.shouldHave(Condition.value(expectedYear));
    }

    public SelenideElement getCardHolderValue(String expectedHolder) {
        return fieldCardHolder.shouldHave(Condition.value(expectedHolder));
    }

    public SelenideElement getCardCVCValue(String expectedCVC) {
        return fieldCardCVC.shouldHave(Condition.value(expectedCVC));
    }


}
