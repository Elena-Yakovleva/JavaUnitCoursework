import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import page.HeaderSection;
import page.PayPage;

import static com.codeborne.selenide.Selenide.open;

public class PayCardTest {

    static HeaderSection headerSection = new HeaderSection();
    static PayPage payPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    void cleanAll() {
        SQLHelper.cleanDataBase();
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080/");
        headerSection.findHeader();
        payPage = headerSection.clickPayButton();

    }

    @Test
    @DisplayName("Тест 1. Успешная оплата по карте")
    public void shouldApprovePurchase() {
        payPage.getEnterCard(DataHelper.getApprovedCard());
        payPage.clickContinueButton();
        payPage.findNotificationApproved();
        Assertions.assertEquals("APPROVED", SQLHelper.getStatusPay());
    }

    @Test
    @DisplayName("Тест 2. Отказ банка у валидной карты")
    public void shouldDeclinedPurchaseForValidCard() {
        payPage.getEnterCard(DataHelper.getDeclinedCard());
        payPage.clickContinueButton();
        payPage.findNotificationDeclined();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusPay());
    }

    @Test
    @DisplayName("Тест 3. Отказ в оплате по номеру несуществующей карты")
    public void shouldDeclinedPurchaseForNotValidCard() {
        payPage.getEnterCard(DataHelper.getInvalidCard());
        payPage.clickContinueButton();
        payPage.findNotificationDeclined();
        Assertions.assertNull(SQLHelper.getStatusPay());
    }

    // Пустые поля
    @Test
    @DisplayName("Тест 4. Отправка пустой формы")
    public void shouldNotSentEmptyForm() {
        payPage.getEnterCard(DataHelper.getEmptyCard());
        payPage.clickContinueButton();
        payPage.submitFormWithEmptyFields();
    }

    @Test
    @DisplayName("Тест 5. Отправка формы с пустым полем Номер карты")
    public void shouldNotSubmitFormWithoutCardNumber() {
        payPage.getEnterCard(DataHelper.getFormWithoutNumber());
        payPage.clickContinueButton();
        payPage.submitFormWithErrorInFieldInCard();
    }

    @Test
    @DisplayName("Тест 6. Отправка формы с пустым полем Месяц")
    public void shouldNotSubmitFormWithoutMonth() {
        payPage.getEnterCard(DataHelper.getFormWithoutMonth());
        payPage.clickContinueButton();
        payPage.submitFormWithErrorInFieldInMonth();
    }

    @Test
    @DisplayName("Тест 7. Отправка формы с пустым полем Год")
    public void shouldNotSubmitFormWithoutYear() {
        payPage.getEnterCard(DataHelper.getFormWithoutYear());
        payPage.clickContinueButton();
        payPage.submitFormWithErrorInFieldInYear();
    }

    @Test
    @DisplayName("Тест 8. Отправка формы с пустым полем Владелец")
    public void shouldNotSubmitFormWithoutHolder() {
        payPage.getEnterCard(DataHelper.getFormWithoutHolder());
        payPage.clickContinueButton();
        payPage.submitFormWithErrorInFieldInHolder();
    }

    @Test
    @DisplayName("Тест 9. Отправка формы с пустым полем CVС")
    public void shouldNotSubmitFormWithoutCVC() {
        payPage.getEnterCard(DataHelper.getFormWithoutCVC());
        payPage.clickContinueButton();
        payPage.submitFormWithErrorInFieldInCVC();
    }


    // Поле "Номер карты"
    @ParameterizedTest
    @CsvSource({
            "0000000000000000",
            "111122223333444",
            "aaaabbbbccccdddd",
            "!@#$%^&*()_-?№=",
            "您不应提交卡号不正确的表格"
    })
    @DisplayName("Тесты 10-14. Невалидные данные в поле с номером карты")
    public void shouldNotSubmitFormWithIncorrectCardNumber(String card) {
        payPage.getEnterCard(DataHelper.getInvalidNumber(card));
        payPage.clickContinueButton();
        payPage.submitFormWithErrorInFieldInCard();
    }

    @Test
    @DisplayName("Тест 15. Ввод в поле Номер карты 17 цифр")
    public void shouldEnter17digitsInFieldCardNumber() {
        payPage.getEnterCard(DataHelper.getInvalidNumber("11112222333344445"));
        String actual = payPage.getCardNumberValue();
        Assertions.assertEquals("1111 2222 3333 4444", actual);
    }

    // Поле "Месяц"

    @ParameterizedTest
    @CsvSource({
            "00",
            "13"
    })
    @DisplayName("Тесты 16-17. Невалидные граничные данные в поле месяц")
    public void shouldInvalidMonthBoundaryValue(String month) {
        payPage.getEnterCard(DataHelper.getInvalidMonth(month));
        payPage.clickContinueButton();
        payPage.submitFormWithErrorDeadlineIncorrectInFieldInMonth();
    }

    @Test
    @DisplayName("Тест 18. Ввод прошедшего месяца в поле месяц")
    public void shouldInputLastMonth () {
        payPage.getEnterCard(DataHelper.getLastMonth());
        payPage.clickContinueButton();
        payPage.submitFormWithErrorDeadlineIncorrectInFieldInMonth();
    }

    @ParameterizedTest
    @CsvSource({
            "0",
            "aa",
            "-@",
            "卡号"
    })
    @DisplayName("Тесты 19-22. Невалидные данные в поле с месяцем")
    public void shouldNotSubmitFormWithIncorrectMonth(String month) {
        payPage.getEnterCard(DataHelper.getInvalidMonth(month));
        payPage.clickContinueButton();
        payPage.submitFormWithErrorInFieldInMonth();
    }
    @Test
    @DisplayName("Тест 23. Ввод в поле Месяц трех цифр")
    public void shouldEnterThreeDigitsInFieldMonth() {
        payPage.getEnterCard(DataHelper.getInvalidMonth("123"));
        String actual = payPage.getCardMonthValue();
        Assertions.assertEquals("12", actual);
    }

    // Поле "Год"
    @ParameterizedTest
    @CsvSource({
            "00"
    })
    @DisplayName("Тест 24.  Два нуля в поле год")
    public void shouldNotSubmitFormWithTwoZeroInYear(String year) {
        payPage.getEnterCard(DataHelper.getInvalidYear(year));
        payPage.clickContinueButton();
        payPage.submitFormWithErrorDeadlineExpiredInFieldInYear();
    }

    @Test
    @DisplayName("Тест 25. Ввод прошедшего года в поле год")
    public void shouldInputLastYear() {
        payPage.getEnterCard(DataHelper.getLastYear());
        payPage.clickContinueButton();
        payPage.submitFormWithErrorDeadlineExpiredInFieldInYear();
    }

    @Test
    @DisplayName("Тест 26. Ввод поле значения больше, чем максимальный год")
    public void shouldInputYearGreaterThanMax() {
        payPage.getEnterCard(DataHelper.getEnterYearOverMax());
        payPage.clickContinueButton();
        payPage.submitFormWithErrorDeadlineIncorrectInFieldInYear();
    }

    @ParameterizedTest
    @CsvSource({
            "0",
            "1",
            "aa",
            "#!",
            "卡号"
    })
    @DisplayName("Тесты 27-30. Невалидные данные в поле год")
    public void shouldNotSubmitFormWithIncorrectYear(String year) {
        payPage.getEnterCard(DataHelper.getInvalidYear(year));
        payPage.clickContinueButton();
        payPage.submitFormWithErrorInFieldInYear();
    }

    @Test
    @DisplayName("Тест 31. Ввод в поле Год трех цифр")
    public void shouldEnterThreeDigitsInFieldYear() {
        payPage.getEnterCard(DataHelper.getInvalidYear("225"));
        String actual = payPage.getCardYearValue();
        Assertions.assertEquals("22", actual);
    }

    // Поле "Владелец"
    @Test
    @DisplayName("Тест 32. Две буквы на латинице в поле Владелец")
    public void shouldNotSubmitFormWithTwoLettersInsideHolders() {
        var holder = "ab";
        payPage.getEnterCard(DataHelper.getInvalidHolder(holder));
        payPage.clickContinueButton();
        payPage.submitFormWithErrorInFieldInHolder();
    }

    @Test
    @DisplayName("Тест 33. Имя длинной в 21 символ на латинице в поле Владелец")
    public void shouldNotSubmitFormWith21LettersInsideHolders() {
        var holder = "Sammiele Walter-Skott";
        payPage.getEnterCard(DataHelper.getInvalidHolder(holder));
        payPage.clickContinueButton();
        payPage.enter21LetterInHolderField();
    }

    @ParameterizedTest
    @CsvSource({
            "12345678900987654321",
            "Денис Скородубчиков",
            "!@#$%^&*()_-?№=!@#$%",
            "您不应提交卡号不正确的表格不应提交卡号不"
    })
    @DisplayName("Тесты 34-37. Невалидные данные в поле Владелец")
    public void shouldNotSubmitFormWithIncorrectHolder(String holder) {
        payPage.getEnterCard(DataHelper.getInvalidHolder(holder));
        payPage.clickContinueButton();
        payPage.submitFormWithErrorIncorrectFormatInFieldInHolder();
    }

    @Test
    @DisplayName("Тест 38. Ввод только пробелов")
    public void shouldEnterOnlySpaces() {
        payPage.getEnterCard(DataHelper.getInvalidHolder("               "));
        payPage.clickContinueButton();
        payPage.submitFormWithErrorInFieldInHolder();
        Assertions.assertEquals("               ", payPage.getCardHolderValue());
    }

    // Поле "CVC/CVV"
    @ParameterizedTest
    @CsvSource({
            "000",
            "00",
            "asc",
            "!@#",
            "号不正"
    })
    @DisplayName("Тесты 39-43. Невалидные данные в поле CVC")
    public void shouldNotSubmitFormWithIncorrectCVC(String cvc) {
        payPage.getEnterCard(DataHelper.getInvalidCVC(cvc));
        payPage.clickContinueButton();
        payPage.submitFormWithErrorInFieldInCVC();

    }

    @Test
    @DisplayName("Тест 44. Проверка отказа вводе 4 цифры")
    public void shouldEnter4NumberInFieldCVC() {
        payPage.getEnterCard(DataHelper.getInvalidCVC("1234"));
        String actualCVC = payPage.getCVCValue();
        Assertions.assertEquals("123", actualCVC );

    }












}

