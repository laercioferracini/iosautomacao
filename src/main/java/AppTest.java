import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppTest {
    public static URL url;
    public static DesiredCapabilities capabilities;
    public static IOSDriver<IOSElement> driver;

    //1
    @BeforeSuite
    public void setupAppium() throws MalformedURLException {
        //2
        final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
        url = new URL(URL_STRING);

        //3
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone XR");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.1");
//        capabilities.setCapability(MobileCapabilityType.APP, "/Users/lasicin/IdeaProjects/iosteste/Teste/build/Release-iphonesimulator/Teste.app");
//        capabilities.setCapability(MobileCapabilityType.APP, "/Users/lasicin/IdeaProjects/iosteste/TabTeste/build/Release-iphonesimulator/TabTeste.app");
        capabilities.setCapability("bundleId", "com.apple.Preferences");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability("showIOSLog", false);
        capabilities.setCapability("useNewWDA", false);
        //4
        driver = new IOSDriver<IOSElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//        driver.resetApp();
    }

    //5
//    @AfterSuite
//    public void uninstallApp() throws InterruptedException {
//        driver.removeApp("br.com.teste");
//    }

    @Test
    public void scrollTest() {
        driver.findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' and name CONTAINS[c] 'Desenvolvedor' ")).click();
//        driver.findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' and name CONTAINS[c] 'Sobre' ")).click();
//        driver.findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' and name CONTAINS[c] 'Legal' ")).click();
//        driver.findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' and name CONTAINS[c] 'Aviso' ")).click();
        HashMap<String, String> scrollObject = new HashMap<>();
        scrollObject.put("predicateString", "value CONTAINS[c] 'TV Providers'");
        driver.executeScript("mobile: scroll", scrollObject);
        driver.findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' and name CONTAINS[c] 'TV Providers' ")).getText();
        Assert.assertEquals("TV Providers", driver.findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' and name CONTAINS[c] 'TV Providers' ")).getText());
    }

    //6
    @Test(enabled = true)
    public void myFirstTest() throws InterruptedException {
        Map<String, Object> status = driver.getStatus();

        for (Map.Entry e : status.entrySet()) {
            System.out.printf("Driver Status Key: %s -> Value: %s %n", e.getKey(), e.getValue());
        }

        Map<String, Object> settings = driver.getSettings();

        for (Map.Entry e : status.entrySet()) {
            System.out.printf("settings Key: %s -> Value: %s %n", e.getKey(), e.getValue());
        }

        List<Map<String, Object>> allSessionDetails = driver.getAllSessionDetails();

        for (Map<String, Object> m : allSessionDetails) {
            for (Map.Entry e : m.entrySet()) {
                System.out.printf("allSessionDetails Key: %s -> Value: %s %n", e.getKey(), e.getValue());
            }
        }
        System.out.printf("getPageSource Key: %s %n", driver.getPageSource());

        driver.resetApp();
    }

    @Test(description = "Clicar na segunda aba e validar o texto")
    public void acessarSegundaAba() {
//        driver.findElementByAccessibilityId("Second").click();
//        Assert.assertEquals("Second View", driver.findElementByAccessibilityId("Second View").getText());
    }

    @Test(description = "Clicar na primeira aba e validar o texto")
    @Description("Clicar na primeira aba e validar o texto")
    public void acessarPrimeiraAba() {
//        driver.findElementByAccessibilityId("First").click();
//        Assert.assertEquals("First View", driver.findElementByAccessibilityId("First View").getText());
    }

    @Test
    @Description("Terminate app and activate App")
    public void teste() {
//        driver.terminateApp("br.com.teste");
//        driver.unlockDevice();
//        driver.activateApp("br.com.teste");
    }

    private void showNotifications() {
        manageNotifications(true);
    }

    private void hideNotifications() {
        manageNotifications(false);
    }

    private void manageNotifications(Boolean show) {

        Dimension screenSize = driver.manage().window().getSize();

        int yMargin = 5;
        int xMid = screenSize.width / 2;
        PointOption top = PointOption.point(xMid, yMargin);
        PointOption bottom = PointOption.point(xMid, screenSize.height - yMargin);

        TouchAction action = new TouchAction(driver);
        if (show) {
            action.press(top);
        } else {
            action.press(bottom);
        }
        action.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)));
        if (show) {
            action.moveTo(bottom);
        } else {
            action.moveTo(top);
        }
        action.perform();
    }
}