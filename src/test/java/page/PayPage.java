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
    private final SelenideElement fieldCardNumber = $x("//*[@id=\"root\"]/div/form/fieldset/div[1]/span/span/span[2]/input");
    private final SelenideElement fieldCardMonth = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[1]/span/span/span[2]/input");
    private final SelenideElement fieldCardYear = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[2]/span/span/span[2]/input");
    private final SelenideElement fieldCardHolder = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[1]/span/span/span[2]/input");
    private final SelenideElement fieldCardCVC = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[2]/span/span/span[2]/input");

    // Ошибки полей ввода
    private final SelenideElement errorEnterCard = $x("//*[@id=\"root\"]/div/form/fieldset/div[1]/span/span/span[3]");
    private final SelenideElement errorEnterMonth = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[1]/span/span/span[3]");
    private final SelenideElement errorEnterYear = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[2]/span/span/span[3]");
    private final SelenideElement errorEnterHolder = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[1]/span/span/span[3]");
    private final SelenideElement errorEnterCVC = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[2]/span/span/span[3]");
    private final SelenideElement errorIncorrectMonth = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[1]/span/span/span[3]");
    private final SelenideElement errorIncorrectYear = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[2]/span/span/span[3]");

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
        notificationApproved.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void findNotificationDeclined() {
        notificationDeclined.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void submitFormWithEmptyFields() {
        errorEnterCard.shouldBe(Condition.visible, Condition.text("Неверный формат"));
        errorEnterMonth.shouldBe(Condition.visible, Condition.text("Неверный формат"));
        errorEnterYear.shouldBe(Condition.visible, Condition.text("Неверный формат"));
        errorEnterHolder.shouldBe(Condition.visible, Condition.text("Поле обязательно для заполнения"));
        errorEnterCVC.shouldBe(Condition.visible, Condition.text("Неверный формат"));
    }
    // Номер
    public void submitFormWithErrorInFieldInCard() {
        errorEnterCard.shouldBe(Condition.visible, Condition.text("Неверный формат"));
    }
    // Месяц
    public void submitFormWithErrorInFieldInMonth() {
        errorEnterMonth.shouldBe(Condition.visible, Condition.text("Неверный формат"));
    }
    public void submitFormWithErrorDeadlineIncorrectInFieldInMonth() {
        errorIncorrectMonth.shouldBe(Condition.visible, Condition.text("Неверно указан срок действия карты"));
    }
    // Год
    public void submitFormWithErrorInFieldInYear() {
        errorEnterYear.shouldBe(Condition.visible, Condition.text("Неверный формат"));
    }
    public void submitFormWithErrorDeadlineIncorrectInFieldInYear() {
        errorIncorrectYear.shouldBe(Condition.visible, Condition.text("Неверно указан срок действия карты"));
    }
    public void submitFormWithErrorDeadlineExpiredInFieldInYear() {
        errorIncorrectYear.shouldBe(Condition.visible, Condition.text("Истёк срок действия карты"));
    }

    // Владелец
    public void submitFormWithErrorInFieldInHolder() {
        errorEnterHolder.shouldBe(Condition.visible, Condition.text("Поле обязательно для заполнения"));
    }
    public void submitFormWithErrorIncorrectFormatInFieldInHolder() {
        errorEnterHolder.shouldBe(Condition.visible, Condition.text("Неверный формат"));
    }
    public void enter21LetterInHolderField() {
        errorEnterHolder.shouldBe(Condition.visible, Condition.text("Длина не может быть больше 20 символов"));
    }

    // CVC
    public void submitFormWithErrorInFieldInCVC() {
        errorEnterCVC.shouldBe(Condition.visible, Condition.text("Неверный формат"));
    }

    //
    public String getCardNumberValue() {
        return fieldCardNumber.getValue();}

    public String getCardMonthValue() {
        return fieldCardMonth.getValue();}

    public String getCardYearValue() {
        return fieldCardYear.getValue();}

    public String getCardHolderValue() {
        return fieldCardHolder.getValue();}

    public String getCVCValue() {
        return fieldCardCVC.getValue();}














}
