package ru.netology.sql.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.*;
import ru.netology.sql.page.*;

import static ru.netology.sql.data.SQLHelper.cleanDatabase;
import static com.codeborne.selenide.Selenide.*;

public class BankLoginTest {
    @AfterAll
    static void tearDown () {
        cleanDatabase();
    }


    @Test
    public void shouldBeSuccessfulLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        //verificationPage.VerificationPage();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    public void shouldRandomUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.getError();
    }

    @Test
    public void shouldInvalidLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = new DataHelper.AuthInfo(DataHelper.getRandomUser().getLogin(), DataHelper.getAuthInfoWithTestData().getPassword());
        loginPage.validLogin(authInfo);
        loginPage.getError();
    }

    @Test
    public void shouldInvalidPassword() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = new DataHelper.AuthInfo(DataHelper.getAuthInfoWithTestData().getLogin(), DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authInfo);
        loginPage.getError();
    }

    @Test
    public void shouldInvalidVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.VerificationPage();
        var verificationCode = DataHelper.getVerificationCode().getCode();
        //verificationPage.validVerify(verificationCode);
        verificationPage.verify(verificationCode);
        verificationPage.getError();
    }
}
