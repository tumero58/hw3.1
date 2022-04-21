import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;

public class LoginPage {
    private WebDriver driver;
    private By reset_linktext = By.linkText("Forgot password?");
    private By email_input_xpath = By.xpath("//input[@type='text']");
    private By password_input_xpath = By.xpath("//input[@type='password']");
    private By login_button = By.tagName("button");


    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void goToForgotPasswordPage(){
        driver.findElement(reset_linktext).click();
    }

    public void writeEmail(String email){
        driver.findElement(email_input_xpath).sendKeys(email);
    }

    public void writePassword(String password){
        driver.findElement(password_input_xpath).sendKeys(password);
    }

    public void login(){
       driver.findElements(login_button).get(1).click();
    }

    public void authWithCreds(String email,String password){
        writeEmail(email);
        writePassword(password);
        login();
    }



}
