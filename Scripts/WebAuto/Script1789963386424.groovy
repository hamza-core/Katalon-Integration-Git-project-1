import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.llm.keyword.LlmKeywords as LLM
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.configuration.RunConfiguration // <-- ADDED THIS IMPORT

// Set Incognito mode
RunConfiguration.setWebDriverPreferencesProperty("args", ["--incognito"])
WebUI.openBrowser(null)

WebUI.navigateToUrl('http://demoaut.katalon.com/')

WebUI.click(findTestObject('Page_CURA Healthcare Service/a_btn-make-appointment'))

WebUI.click(findTestObject('Page_CURA Healthcare Service/input_Username'))

WebUI.setText(findTestObject('Page_CURA Healthcare Service/input_Username_1'), 'John Doe')

WebUI.click(findTestObject('Page_CURA Healthcare Service/input_Password'))

WebUI.setEncryptedText(findTestObject('Page_CURA Healthcare Service/input_Password_1'), 'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')

WebUI.click(findTestObject('Page_CURA Healthcare Service/button_btn-login'))

WebUI.click(findTestObject('Page_CURA Healthcare Service/label_Apply for hospital readmission'))

WebUI.click(findTestObject('Page_CURA Healthcare Service/label_Medicaid'))

WebUI.click(findTestObject('Page_CURA Healthcare Service/span_glyphicon glyphicon-calendar'))

WebUI.click(findTestObject('Page_CURA Healthcare Service/td_24'))

WebUI.setText(findTestObject('Page_CURA Healthcare Service/textarea_Comment'), 'something')

WebUI.click(findTestObject('Page_CURA Healthcare Service/button_btn-book-appointment'))

WebUI.click(findTestObject('Page_CURA Healthcare Service/a_Go to Homepage'))

WebUI.closeBrowser()

