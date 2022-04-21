import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private WebDriver driver;
    private By js_button = By.linkText("Learn JavaScript");
    private By search_field = By.id("search2");
    private By login_id = By.id("w3loginbtn");


    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    public void goToLogin(){
        driver.findElement(login_id).click();
    }



}
