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
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoWithTestDataUser1();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.verifyVerificationPage();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldErrorIfUserNotInDB() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification();
    }

    @Test
    void shouldGetErrorIfInvalidPassword() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoWithIncorrectPass();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification();
    }

    @Test
    void shouldGetErrorIfIncorrectCode() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoWithTestDataUser2();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.verifyVerificationPage();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode);
        verificationPage.verifyErrorNotification();
    }

    @Test
    void shouldBlockWhenThreeInvalidPasswords() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoWithIncorrectPass();
        loginPage.validLogin(authInfo);
        loginPage.clickButton2Times();
        loginPage.blockedSystemNotification();

    }


}