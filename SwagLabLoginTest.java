package Module1_Login_Test;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import LibraryFiles.BaseClass;
import LibraryFiles.UtilityClass;
import Module1_Login.SwagLabLoginPage;
import Module4_Orders.SwagLabHomePage;
import Module5_Menu.SwagLabMenuPage;

public class SwagLabLoginTest extends BaseClass
{
	SwagLabLoginPage login;
	SwagLabHomePage home;
	SwagLabMenuPage menu;
	int TCID;

	@BeforeClass
	public void openBrowser() throws EncryptedDocumentException, IOException 
	{					
		 initializeBrowser();		 
		 login=new SwagLabLoginPage(driver);   
		 home=new SwagLabHomePage(driver);
		 menu=new SwagLabMenuPage(driver);
	}
	
	
	@BeforeMethod
	public void loginToApp() throws InterruptedException, EncryptedDocumentException, IOException 
	{
		login.inpSwagLabLoginPageUsername(UtilityClass.readDataFromPF("UN"));
		login.inpSwagLabLoginPagePassword(UtilityClass.readDataFromPF("PWD"));
		login.clickSwagLabLoginPageLoginBtn();
		Thread.sleep(2000);
	}
	
	@Test(priority = 1)
	public void verifyLogo() 
	{
		TCID=101;
		boolean actResult =home.getSwagLabHomePageLogo();
		Assert.assertTrue(actResult,"Failed: act result is false");
	}
	
	@Test(priority = 2)
	public void verifyAddToCartBtnCovertToRemove() throws EncryptedDocumentException, IOException 
	{
		TCID=102;
		home.clickSwagLabHomePageAddToCart();
		
		String expText=UtilityClass.getTD(1, 0);
		String actText = home.getSwagLabHomePageRemove();
		Assert.assertEquals(actText, expText, "Failed102: both results are diff");
	}
	
	@AfterMethod
	public void logoutFromApp(ITestResult s1) throws InterruptedException, IOException
	{
		if(s1.getStatus()==ITestResult.FAILURE) 
		{
			UtilityClass.captureSS(driver, TCID);
		}
		
		home.clickSwagLabHomePageOpenMenu();
		Thread.sleep(2000);
		menu.clickSwagLabMenuPageLogout();
		Thread.sleep(2000);
	}
	
	@AfterClass
	public void closeBrowser()
	{
		
		driver.close();
	}

}
