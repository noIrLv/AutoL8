package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanDatabase;

public class LoginTest {
    LoginPage loginPage = new LoginPage();
    VerificationPage verificationPage = new VerificationPage();

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @AfterAll
    static void cleanDB() {
        cleanDatabase();
    }

    @Test
    void shouldLoginValidData() {
//        loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestDataUser1();
        loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPage();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldErrorIfUserNotInDB() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification();
    }

    @Test
    void shouldGetErrorIfInvalidPassword() {
        var authInfo = DataHelper.getAuthInfoWithIncorrectPass();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification();
    }

    @Test
    void shouldGetErrorIfIncorrectCode() {
        var authInfo = DataHelper.getAuthInfoWithTestDataUser2();
        loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPage();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode);
        verificationPage.verifyErrorNotification();
    }

    @Test
    void shouldBlockWhenThreeInvalidPasswords() {
        var authInfo = DataHelper.getAuthInfoWithIncorrectPass();
        loginPage.validLogin(authInfo);
        loginPage.clickButton2Times();
        loginPage.blockedSystemNotification();

    }


}
