package page;

import com.codeborne.selenide.Condition;
import data.DataHelper;
import data.SQLHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class VerificationPage {

    public void verifyVerificationPage() {
        $("[data-test-id=code] input").shouldBe(Condition.visible);
    }

    public void verifyErrorNotification() {
        $("[data-test-id=error-notification]").shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        verify(verificationCode);
        return page(DashboardPage.class);
    }

    public void verify(DataHelper.VerificationCode verificationCode) {
        $("[data-test-id=code] input").setValue(verificationCode.getCode());
        $("[data-test-id=action-verify]").click();
    }
}
