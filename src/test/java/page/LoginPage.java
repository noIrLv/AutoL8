package page;

import com.codeborne.selenide.Condition;
import data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage {

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        return page(VerificationPage.class);
    }

    public void verifyErrorNotification() {
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    public void clickButton2Times() {
        $("[data-test-id=action-login]").click();
        $("[data-test-id=action-login]").click();
    }

    public void blockedSystemNotification() {
        $("[data-test-id=error-notification]")
                .shouldHave(text("Доступ заблокирован"))
                .shouldBe(Condition.visible);
    }
}
