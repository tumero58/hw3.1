import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SimpleTest {
    private static WebDriver driver;
    protected MainPage mainPage;
    protected LoginPage loginPage;
    protected ResetPage resetPage;
    private static String url_main = "https://www.w3schools.com/";
    private static String driverProperty = "webdriver.chrome.driver";
    private static String driverPath = "src/driver/chromedriver";
    String pathname_login = "log-in";
    String pathname_reset = "reset";
    String returnToLoginUrl = "https://profile.w3schools.com/";

    @BeforeClass
    @Parameters("browser")
    public void setUp(String browser) throws MalformedURLException {
        System.setProperty(driverProperty,driverPath);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(browser);
        driver = new RemoteWebDriver(new URL("https://localhost:4444/"), caps);
//        driver = new ChromeDriver();
        driver.get(url_main);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        resetPage = new ResetPage(driver);
    }

    public void goToResetPage(){
        mainPage.goToLogin();
        loginPage.goToForgotPasswordPage();
    }

    public void goToResetAndReturnToLoginPage(){
        goToResetPage();
        resetPage.returnToLogin();
    }

    public void auth(String email,String password){
        mainPage.goToLogin();
        loginPage.authWithCreds(email,password);
    }

    @BeforeMethod
    public void refresh(){
        driver.get(url_main);
    }

    @AfterMethod
    public void recordFailure(ITestResult result){
        if(ITestResult.FAILURE == result.getStatus()) {
            var camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            try{
                Files.move(screenshot, new File("resources/screenshots/" + result.getName() + ".png"));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void checkLoginPage(){
        mainPage.goToLogin();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(pathname_login));
    }

    @Test
    public void checkResetPage(){
        goToResetPage();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(pathname_reset));
    }

    @Test
    public void returnToLogin(){
        goToResetAndReturnToLoginPage();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.equals(returnToLoginUrl));
    }
    @Test
    public void checkAuthInvalidEmail(){
        auth("test@test.com","12345678");
    }




//    @Test
//    public void getLoginInputAndType(){
//        driver.findElement(By.linkText(linktext_auth)).click();
//        driver.findElement(By.id(login_input_id)).sendKeys(mail_login);
//        String inputValue = driver.findElement(By.id(login_input_id)).getAttribute(attributeValue);
//        Assert.assertEquals(mail_login,inputValue);
//    }
//
//    @Test
//    public void getPasswordInputAndType(){
//        driver.findElement(By.linkText(linktext_auth)).click();
//        driver.findElement(By.id(password_input_id)).sendKeys(password);
//        String inputValue = driver.findElement(By.id(password_input_id)).getAttribute(attributeValue);
//        Assert.assertEquals(password,inputValue);
//    }
//
//    @Test
//    public void login(){
//        driver.findElement(By.linkText(linktext_auth)).click();
//        driver.findElement(By.id(login_input_id)).sendKeys(mail_login);
//        driver.findElement(By.id(password_input_id)).sendKeys(password);
//        driver.findElement(By.className(button_classname)).click();
//        String currentUrl = driver.getCurrentUrl();
//        assert currentUrl.contains(pathname_secure);
//    }
//
//    @Test
//    public void logout(){
//        driver.findElement(By.linkText(linktext_auth)).click();
//        driver.findElement(By.id(login_input_id)).sendKeys(mail_login);
//        driver.findElement(By.id(password_input_id)).sendKeys(password);
//        driver.findElement(By.className(button_classname)).click();
//        driver.findElement(By.cssSelector(logout_button_selector)).click();
//        String currentUrl = driver.getCurrentUrl();
//        Assert.assertFalse(currentUrl.contains(pathname_secure));
//    }

    @AfterClass
    public void cleanUp(){
        driver.quit();
    }
}
