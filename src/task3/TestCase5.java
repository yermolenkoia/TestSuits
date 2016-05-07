package task3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestCase5 {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private List<String> data;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://demosite.center/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Yermolenko\\IdeaProjects\\TestSuits\\src\\resources\\data.txt")))
        {
            String str = new String();
            Integer i = 0;
            while ((str = br.readLine()) != null) {
                data.set(i++, str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCase5() throws Exception {
        driver.get(baseUrl + "/wordpress/wp-login.php");
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys(data.get(0));
        driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys(data.get(1));
        driver.findElement(By.id("wp-submit")).click();
        // Warning: verifyTextPresent may require manual changes
        try {
            assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Howdy, admin[\\s\\S]*$"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.linkText("Posts")).click();
        driver.findElement(By.cssSelector("a.add-new-h2")).click();
        driver.findElement(By.id("title")).clear();
        driver.findElement(By.id("title")).sendKeys("Selenium Demo Post");
        driver.findElement(By.id("publish")).click();
        // Warning: verifyTextPresent may require manual changes
        try {
            assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Post published[\\s\\S]*$"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
