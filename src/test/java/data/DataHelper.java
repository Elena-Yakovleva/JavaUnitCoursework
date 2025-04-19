package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    static Faker faker = new Faker(new Locale("en"));

    // номер карты
    public static String approvedCard() {
        return ("1111222233334444");
    }

    public static String declinedCard() {
        return ("5555666677778888");
    }

    // месяц
    public static String enterMonth(int month) {
        return LocalDate.now().plusMonths(month).format(DateTimeFormatter.ofPattern("MM"));
    }

    // год
    public static String enterYear(int year) {
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }


    // имя владельца
    public static String generateNameHolder() {
        String name = faker.name().firstName() + " " + faker.name().lastName();
        return name.substring(0, Math.min(name.length(), 20));
    }

    // cvc
    public static String generateCVC() {
        String cvc;
        do {
            cvc = faker.numerify("###");
        } while (cvc.equals("000"));
        return cvc;
    }


    // Виды заполненных
    @Value
    public static class CardDetails {
        String cardNumber;
        String cardMonth;
        String cardYear;
        String cardHolder;
        String cardCVC;
    }

    public static CardDetails getApprovedCard() {
        return new CardDetails(approvedCard(), enterMonth(0), enterYear(0), generateNameHolder(), generateCVC());
    }

    public static CardDetails getDeclinedCard() {
        return new CardDetails(declinedCard(), enterMonth(0), enterYear(5), generateNameHolder(), generateCVC());
    }

    public static CardDetails getInvalidCard() {
        return new CardDetails("1234567898765432", enterMonth(0), enterYear(0), generateNameHolder(), generateCVC());
    }

    // пустые поля

    public static CardDetails getFormWithoutNumber() {
        return new CardDetails("", enterMonth(0), enterYear(0), generateNameHolder(), generateCVC());
    }

    public static CardDetails getFormWithoutMonth() {
        return new CardDetails(approvedCard(), "", enterYear(0), generateNameHolder(), generateCVC());
    }

    public static CardDetails getFormWithoutYear() {
        return new CardDetails(approvedCard(), enterMonth(0), "", generateNameHolder(), generateCVC());
    }

    public static CardDetails getFormWithoutHolder() {
        return new CardDetails(approvedCard(), enterMonth(0), enterYear(0), "", generateCVC());
    }

    public static CardDetails getFormWithoutCVC() {
        return new CardDetails(approvedCard(), enterMonth(0), enterYear(0), generateNameHolder(), "");
    }
    // поля с невалидными данными

    public static CardDetails getInvalidNumber(String card) {
        return new CardDetails(card, enterMonth(0), enterYear(0), generateNameHolder(), generateCVC());
    }

    public static CardDetails getInvalidMonth(String month) {
        return new CardDetails(approvedCard(), month, enterYear(0), generateNameHolder(), generateCVC());
    }

    public static CardDetails getLastMonth() {
        return new CardDetails(approvedCard(), enterMonth(-1), enterYear(0), generateNameHolder(), generateCVC());
    }

    public static CardDetails getInvalidYear(String year) {
        return new CardDetails(approvedCard(), enterMonth(0), year, generateNameHolder(), generateCVC());
    }

    public static CardDetails getLastYear() {
        return new CardDetails(approvedCard(), enterMonth(0), enterYear(-1), generateNameHolder(), generateCVC());
    }

    public static CardDetails getEnterYearOverMax() {
        return new CardDetails(approvedCard(), enterMonth(0), enterYear(6), generateNameHolder(), generateCVC());
    }

    public static CardDetails getInvalidHolder(String holder) {
        return new CardDetails(approvedCard(), enterMonth(0), enterYear(0), holder, generateCVC());
    }

    public static CardDetails getInvalidCVC(String cvc) {
        return new CardDetails(approvedCard(), enterMonth(0), enterYear(0), generateNameHolder(), cvc);
    }


}
