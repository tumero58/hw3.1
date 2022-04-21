import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class ResetPage {
    private WebDriver driver;
    private By return_button_linktext = By.linkText("Return to login");


    public ResetPage(WebDriver driver){
        this.driver = driver;
    }

    public void returnToLogin(){
        driver.findElement(return_button_linktext).click();
    }



}
