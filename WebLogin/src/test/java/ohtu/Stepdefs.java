package ohtu;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {

    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:4567";

    @Given("^login is selected$")
    public void login_selected() throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("login"));
        element.click();
    }
    
    @Given("^command new user is selected$")
    public void new_selected() throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();
    }
    
    @Given("^user with username \"([^\"]*)\" with password \"([^\"]*)\" is successfully created$")
    public void new_selected_with_valid(String username, String password) throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();
        newUserWith(username, password, password);
    }
    
    @Given("^user with username \"([^\"]*)\" and password \"([^\"]*)\" is tried to be created$")
    public void new_selected_with_invalid(String username, String password) throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();
        newUserWith(username, password, password);
    }

    @When("^correct username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_correct_and_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @When("^correct username \"([^\"]*)\" and incorrect password \"([^\"]*)\" are given$")
    public void username_and_incorrect_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @When("^incorrect username \"([^\"]*)\" and incorrect password \"([^\"]*)\" are given$")
    public void incorrect_username_and_incorrect_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }
    
    @When("^a valid username \"([^\"]*)\" and password \"([^\"]*)\" and matching password confirmation are entered$")
    public void valid_username_and_password_are_given(String username, String password) throws Throwable {
        newUserWith(username, password, password);
    }
    
    @When("^an invalid username \"([^\"]*)\" and valid password \"([^\"]*)\" and matching password confirmation are entered$")
    public void invalid_username_and_valid_and_password_are_given(String username, String password) throws Throwable {
        newUserWith(username, password, password);
    }
    
    @When("^a valid username \"([^\"]*)\" and invalid password \"([^\"]*)\" are entered$")
    public void valid_username_and_invalid_and_password_are_given(String username, String password) throws Throwable {
        newUserWith(username, password, password);
    }
    
    @When("^a valid username \"([^\"]*)\" and password \"([^\"]*)\" and different password confirmation are entered$")
    public void valid_username_and_password_and_different_confirmation_are_given(String username, String password) throws Throwable {
        newUserWith(username, password, password+"lll");
    }

    @Then("^user is logged in$")
    public void user_is_logged_in() throws Throwable {
        pageHasContent("Ohtu Application main page");
    }

    @Then("^user is not logged in and error message is given$")
    public void user_is_not_logged_in_and_error_message_is_given() throws Throwable {
        pageHasContent("invalid username or password");
        pageHasContent("Give your credentials to login");
    }

    @Then("^user is not logged in and login page is shown with an error message$")
    public void user_is_not_logged_in_and_error_message_is_displayed() throws Throwable {
        pageHasContent("invalid username or password");
        pageHasContent("Give your credentials to login");
    }
    
    @Then("^a new user is created$")
    public void user_is_created() throws Throwable {
        pageHasContent("Welcome to Ohtu Application");
    }
    
    @Then("^user is not created and error \"([^\"]*)\" is reported$")
    public void user_is_not_create_and_error_message_is_displayed(String expectedOutput) throws Throwable {
        pageHasContent(expectedOutput);
        pageHasContent("Create username and give password");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /* helper methods */
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void logInWith(String username, String password) {
        assertTrue(driver.getPageSource().contains("Give your credentials to login"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();
    }

    private void newUserWith(String username, String password, String confirmation) {
        assertTrue(driver.getPageSource().contains("Create username and give password"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys(confirmation);
        element = driver.findElement(By.name("signup"));
        element.submit();
    }
}
