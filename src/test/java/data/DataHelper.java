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
    public static String approvedCard() {return ("1111222233334444"); }
    public static String declinedCard() {return ("5555666677778888");}

    // месяц
    public static String currentMonth() {return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));}
    public static String lastMonth() {return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));}

    // год
    public static String currentYear() {return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));}
    public static String lastYear() {return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));}
    public static String yearOverMax() {return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));}
    public static String maxYear() {return LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("yy"));}

    // имя владельца
    public static String generateNameHolder() {
        String name = faker.name().firstName() + " " + faker.name().lastName();
        return name.substring(0, Math.min(name.length(), 20));}

    // cvc
    public static String generateCVC() {
        var one = String.valueOf(faker.number().numberBetween(1, 9));
        var two = String.valueOf(faker.number().numberBetween(0, 9));
        var three = String.valueOf(faker.number().numberBetween(0, 9));
        return one + two + three;}


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
        return new CardDetails(approvedCard(), currentMonth(), currentYear(), generateNameHolder(), generateCVC());}

    public static CardDetails getDeclinedCard() {
        return new CardDetails(declinedCard(), currentMonth(), maxYear(), generateNameHolder(), generateCVC());}

    public static CardDetails getInvalidCard() {
        return new CardDetails("1234567898765432", currentMonth(), currentYear(), generateNameHolder(), generateCVC());}

    // пустые поля
    public static CardDetails getEmptyCard() {
        return new CardDetails("", "", "", "", "");}

    public static CardDetails getFormWithoutNumber(){
        return new CardDetails("", currentMonth(), currentYear(), generateNameHolder(), generateCVC());}

    public static CardDetails getFormWithoutMonth(){
        return new CardDetails(approvedCard(), "", currentYear(), generateNameHolder(), generateCVC());}

    public static CardDetails getFormWithoutYear(){
        return new CardDetails(approvedCard(), currentMonth(), "", generateNameHolder(), generateCVC());}

    public static CardDetails getFormWithoutHolder(){
        return new CardDetails(approvedCard(), currentMonth(), currentYear(), "", generateCVC());}

    public static CardDetails getFormWithoutCVC(){
        return new CardDetails(approvedCard(), currentMonth(), currentYear(), generateNameHolder(), "");}
    // поля с невалидными данными

    public static CardDetails getInvalidNumber(String card){
        return new CardDetails(card, currentMonth(), currentYear(), generateNameHolder(), generateCVC());}

    public static CardDetails getInvalidMonth(String month){
        return new CardDetails(approvedCard(), month, currentYear(), generateNameHolder(), generateCVC());}

    public static CardDetails getLastMonth() {
        return new CardDetails(approvedCard(), lastMonth(), currentYear(), generateNameHolder(), generateCVC());}

    public static CardDetails getInvalidYear(String year){
        return new CardDetails(approvedCard(), currentMonth(), year, generateNameHolder(), generateCVC());}

    public static CardDetails getLastYear() {
        return new CardDetails(approvedCard(), currentMonth(), lastYear(), generateNameHolder(), generateCVC());}

    public static CardDetails getEnterYearOverMax() {
        return new CardDetails(approvedCard(), currentMonth(), yearOverMax(), generateNameHolder(), generateCVC());}

    public static CardDetails getInvalidHolder(String holder) {
        return new CardDetails(approvedCard(), currentMonth(), currentYear(), holder, generateCVC());}

    public static CardDetails getInvalidCVC(String cvc) {
        return new CardDetails(approvedCard(), currentMonth(), currentYear(), generateNameHolder(), cvc);}


}
