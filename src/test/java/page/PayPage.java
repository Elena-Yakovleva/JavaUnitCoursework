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
    private final SelenideElement fieldCardNumber = $x("//fieldset/descendant::input[1]");  //$x("//span[text()='Номер карты']/..//input");
    private final SelenideElement fieldCardMonth = $x("//fieldset/descendant::input[2]");
    private final SelenideElement fieldCardYear = $x("//fieldset/descendant::input[3]");
    private final SelenideElement fieldCardHolder = $x("//fieldset/descendant::input[4]");
    private final SelenideElement fieldCardCVC = $x("//fieldset/descendant::input[5]");

    // Ошибки полей ввода
    private final SelenideElement errorFieldCardNumber = $x("//fieldset/descendant::input[1]/../following-sibling::span");
    private final SelenideElement errorFieldCardMonth = $x("//fieldset/descendant::input[2]/../following-sibling::span");
    private final SelenideElement errorFieldCardYear = $x("//fieldset/descendant::input[3]/../following-sibling::span");
    private final SelenideElement errorFieldCardHolder = $x("//fieldset/descendant::input[4]/../following-sibling::span");
    private final SelenideElement errorFieldCardCVC = $x("//fieldset/descendant::input[5]/../following-sibling::span");


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

    public void findCardNumberError(String error) {
        errorFieldCardNumber.shouldBe(Condition.visible, Condition.text(error));
    }
    public void findCardMonthError(String error) {
        errorFieldCardMonth.shouldBe(Condition.visible, Condition.text(error));
    }
    public void findCardYearError(String error) {
        errorFieldCardYear.shouldBe(Condition.visible, Condition.text(error));
    }
    public void findCardHolderError(String error) {
        errorFieldCardHolder.shouldBe(Condition.visible, Condition.text(error));
    }
    public void findCardCVCError(String error) {
        errorFieldCardCVC.shouldBe(Condition.visible, Condition.text(error));
    }

    public void findNotificationApproved() {
        notificationApproved.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void findNotificationDeclined() {
        notificationDeclined.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    // Проверка пустой формы
    public void errorsFromSubmitFormWithEmptyFields() {
        errorFieldCardNumber.shouldHave(Condition.text("Поле обязательно для заполнения"));
        errorFieldCardMonth.shouldHave(Condition.text("Поле обязательно для заполнения"));
        errorFieldCardYear.shouldHave(Condition.text("Поле обязательно для заполнения"));
        errorFieldCardHolder.shouldHave(Condition.text("Поле обязательно для заполнения"));
        errorFieldCardCVC.shouldHave(Condition.text("Поле обязательно для заполнения"));
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
