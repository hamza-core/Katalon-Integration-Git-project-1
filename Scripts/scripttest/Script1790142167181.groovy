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

WebUI.openBrowser('')

WebUI.navigateToUrl('http://demoaut.katalon.com/')

WebUI.click(findTestObject('webmanualobjects/Page_CURA Healthcare Service/a_Make Appointment'))

WebUI.setText(findTestObject('webmanualobjects/Page_CURA Healthcare Service/input_Username_txt-username'), 'John Doe')

WebUI.setText(findTestObject('webmanualobjects/Page_CURA Healthcare Service/input_Password_txt-password'), 'ThisIsNotAPassword')

WebUI.click(findTestObject('webmanualobjects/Page_CURA Healthcare Service/button_Login'))


WebUI.selectOptionByValue(findTestObject('Object Repository/webmanualobjects/Page_CURA Healthcare Service/Page_CURA Healthcare Service/select_Tokyo CURA Healthcare Center                            Hongkong CURA Healthcare Center                            Seoul CURA Healthcare Center'), 'Seoul CURA Healthcare Center', true)

WebUI.click(findTestObject('webmanualobjects/Page_CURA Healthcare Service/input_Apply for hospital readmission_chk_ho_9218d5'))

WebUI.click(findTestObject('webmanualobjects/Page_CURA Healthcare Service/input_Medicaid_radio_program_medicaid'))

//WebUI.setText(findTestObject('webmanualobjects/Page_CURA Healthcare Service/input_Visit Date (Required)_visit_date'), '24/09/2026')

WebUI.click(findTestObject('Object Repository/webmanualobjects/Page_CURA Healthcare Service/div_Visit Date (Required)_input-group-addon'))

WebUI.click(findTestObject('webmanualobjects/Page_CURA Healthcare Service/td_24'))

WebUI.setText(findTestObject('webmanualobjects/Page_CURA Healthcare Service/textarea_Comment_txt_comment'), 'Something')

WebUI.click(findTestObject('webmanualobjects/Page_CURA Healthcare Service/button_Book Appointment'))

WebUI.click(findTestObject('webmanualobjects/Page_CURA Healthcare Service/a_Go to Homepage'))

WebUI.closeBrowser()


