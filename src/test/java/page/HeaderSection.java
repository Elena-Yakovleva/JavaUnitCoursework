package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Selenide.$;

public class HeaderSection {

    private SelenideElement header = $("h2");
    private SelenideElement buttonPay = $(Selectors.byText("Купить"));
    private SelenideElement buttonCreditPay = $(Selectors.byText("Купить в кредит"));

    public void findHeader() {
        header.should(Condition.visible);
    }

    public PayPage clickPayButton() {
        buttonPay.click();
        return new PayPage();
    }

    public PayPage clickButtonCreditPay() {
        buttonCreditPay.click();
        return new PayPage();
    }

}
