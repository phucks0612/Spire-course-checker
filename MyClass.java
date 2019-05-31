package newpackage;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;

public class MyClass {

	public static void when_open(String class_name, WebElement tr_element) {
		/**
		 *  This is called when a class you searched for is open. The check is currenly done every 30 seconds and so this will
  			be called every 30 seconds when the class is open. If this method is called, you might want to enroll into the class
  			and try and get back to the 'Enrollment: Add' page as it can then continue looking for other classes that might be
  			open.
  			:param class_name: The name of the class that opened up. This is the same as the name that was passed as program
                   arguments.
  			:param tr_element: The element that represents the row for the class that just opened up. You can use this to tick
                   the checkbox and then click on enroll, or just read other info about the class and add events like
                   call/message yourself to notify of this opening.
		 */
		System.out.println(class_name);
	}
	
	public static void start_checking() throws Exception{
        // declaration and instantiation of objects/variables
		System.setProperty("webdriver.chrome.driver","/Users/phucks0612/Documents/chromedriver");
		WebDriver driver = new ChromeDriver();
    	
		String baseUrl = "https://www.spire.umass.edu/psp/heproda/?cmd=login&languageCd=ENG";
        String expectedTitle = "SPIRE Logon";
        String actualTitle = "";

        // launch Fire fox and direct it to the Base URL
        driver.get(baseUrl);

        // get the actual value of the title
        actualTitle = driver.getTitle();
        
        if (!actualTitle.contentEquals(expectedTitle)) {
        	System.out.println("Wrong Page");
        	driver.close();
        	return;
        }
        
        WebElement login = driver.findElement(By.id("userid"));
        login.clear();
        login.sendKeys("****"); // input your username here
        
        WebElement pwd = driver.findElement(By.id("pwd"));
        pwd.clear();
        pwd.sendKeys("****"); // input your password here
        
        driver.findElement(By.name("Submit")).click();
        
        TimeUnit.SECONDS.sleep(5);
        
        expectedTitle = "Student Center";
        actualTitle = driver.getTitle();

        if (!actualTitle.contentEquals(expectedTitle)) {
        	System.out.println("Wrong password");
        	driver.close();
        	return;
        }
        
        driver.switchTo().frame(driver.findElement(By.id("ptifrmtgtframe")));
        driver.findElement(By.xpath("//select[@id='DERIVED_SSS_SCL_SSS_MORE_ACADEMICS']/option[text()='Enrollment: Add']")).click();
        driver.findElement(By.id("DERIVED_SSS_SCL_SSS_GO_1")).click();
        
        TimeUnit.SECONDS.sleep(5);
        
        driver.findElement(By.cssSelector("input[type='radio'][value='1']")).click();
        driver.findElement(By.id("DERIVED_SSS_SCT_SSR_PB_GO")).click();
        
        String[] class_names = {"MATH 233","COMSCI 230"};
        
        // never stop :v, you must turn it off yourself
        while (true) {
        	for (String class_name : class_names) {
        		String temp =  "//table[@id='SSR_REGFORM_VW$scroll$0']//a[text()[contains(., " + class_name + ")]]/../../../../td[7]/div/div/img";
        		
        		try {
        			WebElement img = driver.findElement(By.xpath(temp));
        			if (img.getAttribute("alt").equals("Open")) {
        				when_open(class_name,img.findElement(By.xpath("//ancestor::tr")));
        			}
        		}
        		catch(NoSuchElementException a){
        			continue;
        		}
        	}
        	driver.findElement(By.xpath("//span[@title='Delete Selected']/a")).click();
        }
        
        //never reaches, but whatever
       // driver.close();
	}
    public static void main(String[] args) {
    	try {
    		start_checking();
    	}
    	catch(Exception a) {
    		
    	}
    }
}
