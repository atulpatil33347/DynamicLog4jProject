package com.sentinel.test;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sentinel.databaseUtility.ProjectDatabaseQueries;
import com.sentinel.driver.WebDriverTestBase;
import com.sentinel.pages.SignInPage;

public class SignUpTestSuite extends WebDriverTestBase {
	String stringValue = null, dbValue = null, temp = null;
	int intValue = 0, intValue1 = 0;
	String className = null;

	@BeforeClass(alwaysRun = true)
	public void beofreClass() {
		className = this.getClass().getName();
		className = className.replace("com.sentinel.test.", "");
	}

	@AfterMethod(alwaysRun = true)
	public void afterTest(ITestResult result) {
		stringValue = result.getMethod().getMethodName();
		WebDriverTestBase.classNameMethodNameList.put(stringValue, className);
	}

	@Test(enabled = true, groups = { "Sprint1" }, priority = 1)
	public void verifySentinalSignUpPageLayout() throws InterruptedException {

		SignInPage signIn = new SignInPage();
		signIn.verifyFaviconPageTitle();
		// signIn.verifyLogoSignUpPanel();
		// signIn.verifySignInBoxElementsDisplayed();
		// signIn.verifySignInPageFooter();

	}

	@Test(enabled = true, groups = { "Sprint1" }, priority = 2)
	public void verifyErrorMessages(Method method) throws InterruptedException {

		SignInPage signIn = new SignInPage();
		// verify login page blank error messages
		signIn.verifyBlankSignInErrorMessages();
		// verify login page error messages on invalid input
		// signIn.VerifyWrongLoginErrorMessages(method.getName());
	}

	@Test(enabled = true, groups = { "Sprint1" }, priority = 3)
	public void verifyForgetUsernameOrPasswordPageElements() throws IOException {

		SignInPage signIn = new SignInPage();
		signIn.navigateToForgetUsernameOrPasswordPage();
		signIn.verifyPaneTitle();
		// signIn.verifyRadioButtonlabels();
		// signIn.verifyDefaultRadioButtonSelected();
		// signIn.verifyTextBoxesAndTextPlaceholders();
		// signIn.verifyDNDLogoAndToolTip();
		// signIn.verifySubmitAndCancelButtons();
		// signIn.verifySubmitButtonFunctionalityWithInvalidEmailIdAndPassword();
		// signIn.verifyBlankEmailAddressAndMobileNumErrorMessages();
		// signIn.verifyWithNonRegisteredEmailAndMobileNumber();
	}

	@Test(enabled = true, groups = { "Sprint1" }, priority = 4)
	public void verifyResendUserName() throws IOException {

		SignInPage signIn = new SignInPage();
		ProjectDatabaseQueries projectDatabaseQueries = new ProjectDatabaseQueries();

		// Get the Email Id from the DB
		dbValue = projectDatabaseQueries.updateAndGetEmailAddressDataBase();
		signIn.navigateToLoginPage();
		signIn.testProirityFourTC();
		// signIn.navigateToForgetUsernameOrPasswordPage();
		// Send email to users email id for resend username
		// signIn.verifyResendUsernameFunctionality(dbValue);
		// Get the username from the user's Email
		// stringValue = signIn.getvalueFromMailBox(dbValue);
		// verify with the DataBase the emailed username and db value
		// dbValue = projectDatabaseQueries.getUserName(dbValue);
		// signIn.VerifyEmailedUserNameWithDB(stringValue, dbValue);

	}

	@Test(enabled = false, groups = { "Sprint1" }, priority = 5)
	public void verifyResetPasswordFunctionality() throws IOException {

		stringValue = "Reset Password";
		SignInPage signIn = new SignInPage();
		ProjectDatabaseQueries projectDatabaseQueries = new ProjectDatabaseQueries();

		// Get the Email Id from the DB
		dbValue = projectDatabaseQueries.updateAndGetEmailAddressDataBase();
		signIn.navigateToLoginPage();
		// Click on 'Reset Password' Radio Button
		signIn.navigateToForgetUsernameOrPasswordPage(stringValue);
		// check the number of times reset password email id is send . Maximum 5
		// times is allowed. If more than 5 delete all related to id
		projectDatabaseQueries.checkContactIdCountInOTPTableUpdateIfNeeded(dbValue);
		// send otp mail
		signIn.verifysendOTPFunctionality(dbValue);
		// get the otp value from the email
		stringValue = signIn.getvalueFromMailBox(dbValue);
		// verify email otp value withe the db value
		dbValue = projectDatabaseQueries.getOTPFromDB(dbValue);
		signIn.VerifyEmailedOTPWithDatabaseOTPvalue(stringValue, dbValue);
		// verify otp page layout
		signIn.verifyEnterOneTimePasswordLayout();
		signIn.verifyEnteringOTPReceivedOnEmailed(stringValue);
		// verify change password page layout
		signIn.verifyNewPasswordLayout();
		// enter new password and confirm password
		stringValue = signIn.verifyNewPasswordConfirmPasswordFuntionality("Don't change to default password");
		dbValue = projectDatabaseQueries.getUsername();
		// login with the email and new password
		signIn.verifySuccessfullyLoginOnPasswordChange(dbValue, stringValue);
		signIn.logoutTheApplication();
	}

	@Test(enabled = false, groups = { "Sprint1" }, priority = 6)
	public void verifyResetPasswordErrorMessages() throws IOException {

		stringValue = "Reset Password";
		SignInPage signIn = new SignInPage();
		// ProjectDatabaseQueries projectDatabaseQueries = new
		// ProjectDatabaseQueries();

		signIn.testProiritySixTC();

		/*
		 * // Get the Email Id from the DB dbValue =
		 * projectDatabaseQueries.updateAndGetEmailAddressDataBase(); // check
		 * the number of times reset password email id is send . Maximum 5 //
		 * times is allowed. If more than 5 delete all related to id
		 * projectDatabaseQueries.checkContactIdCountInOTPTableUpdateIfNeeded(
		 * dbValue); signIn.navigateToLoginPage(); // Click on 'Reset Password'
		 * Radio Button
		 * signIn.navigateToForgetUsernameOrPasswordPage(stringValue); // Blank
		 * Email and MObile Number error messages
		 * signIn.verifyBlankEmailAddressAndMobileNumErrorMessages();
		 * signIn.verifyWithNonRegisteredEmailAndMobileNumber();
		 */

	}

	@Test(enabled = false, groups = { "Sprint1" }, priority = 7)
	public void verifyOTPErrorMessagesAndInvalidOTPErrorMessages() throws IOException {

		stringValue = "Reset Password";
		SignInPage signIn = new SignInPage();
		ProjectDatabaseQueries projectDatabaseQueries = new ProjectDatabaseQueries();

		// Get the Email Id from the DB
		dbValue = projectDatabaseQueries.updateAndGetEmailAddressDataBase();
		signIn.navigateToLoginPage();
		signIn.navigateToForgetUsernameOrPasswordPage(stringValue);
		// check the number of times reset password email id is send . Maximum 5
		// times is allowed. If more than 5 delete all related to id
		projectDatabaseQueries.checkContactIdCountInOTPTableUpdateIfNeeded(dbValue);
		// send otp on email
		signIn.verifysendOTPFunctionality(dbValue);
		// Verify the balnk and other error messages for otp
		signIn.verifyBlankOTPErrorMessage();
		signIn.verifyOTPCharLimitErrorMessage();
		signIn.verifyInvalidOTPErrorMessage();
	}

	@Test(enabled = false, groups = { "Sprint1" }, priority = 8)
	public void verifyMaxLimitResetPasswordMail() throws IOException {

		stringValue = "Reset Password";
		SignInPage signIn = new SignInPage();
		ProjectDatabaseQueries projectDatabaseQueries = new ProjectDatabaseQueries();

		// Get the Email Id from the DB
		dbValue = projectDatabaseQueries.updateAndGetEmailAddressDataBase();
		// check the number of times reset password email id is send . Maximum 5
		// times is allowed. If more than 5 delete all related to id
		projectDatabaseQueries.checkContactIdCountInOTPTableUpdateIfNeeded(dbValue);
		signIn.navigateToLoginPage();
		signIn.navigateToForgetUsernameOrPasswordPage(stringValue);
		// Check no. of times mail send in the db ,
		// maximum 5 times allowed
		intValue = projectDatabaseQueries.GetOtpCountForEmailID(dbValue);
		// send otp maximum 5 times
		signIn.verifyMaximumSendOTPFunctionality(intValue, dbValue);
		// verify in the db that the value= 5
		intValue = projectDatabaseQueries.GetOtpCountForEmailID(dbValue);
		signIn.verifyOTPcountInDatabaseIsMaximum(intValue);

	}

	@Test(enabled = false, groups = { "Sprint1" }, priority = 9)
	public void verifyMaxLimitResendOTPS() throws IOException {

		stringValue = "Reset Password";
		SignInPage signIn = new SignInPage();
		ProjectDatabaseQueries projectDatabaseQueries = new ProjectDatabaseQueries();

		// Get the Email Id from the DB
		dbValue = projectDatabaseQueries.updateAndGetEmailAddressDataBase();
		temp = dbValue;
		signIn.navigateToLoginPage();
		signIn.navigateToForgetUsernameOrPasswordPage(stringValue);
		// check the number of times reset password email id is send . Maximum 5
		// times is allowed. If more than 5 delete all related to id
		projectDatabaseQueries.checkContactIdCountInOTPTableUpdateIfNeeded(dbValue);
		signIn.verifysendOTPFunctionality(dbValue);
		dbValue = projectDatabaseQueries.getNoOfAttemptsFromDB(temp);
		// send otp maximum 3 times and check the success message at 4th time
		// check the error message
		signIn.verifyEnteringOTPReceivedOnEmailedMaximumTimes(dbValue);
		// check the no_of_attempts coloumn value == 3
		dbValue = projectDatabaseQueries.getNoOfAttemptsFromDB(temp);
		signIn.verifyMaximumOTPValueInDatabase(dbValue);
	}

	@Test(enabled = false, groups = { "Sprint1" }, priority = 10)
	public void verifyMaxLimitValidUsernameInvalidPassword(Method method) throws IOException {

		SignInPage signIn = new SignInPage();
		ProjectDatabaseQueries projectDatabaseQueries = new ProjectDatabaseQueries();

		// Get the Email Id from the DB
		dbValue = projectDatabaseQueries.updateAndGetEmailAddressDataBase();
		signIn.navigateToLoginPage();
		// get the initial invalid attempts from the db
		intValue = projectDatabaseQueries.getInvalidLoginCountUpdateIfNeeded(dbValue);
		// check the error messages and valid login attempts on the ui
		signIn.VerifyWrongLoginErrorMessages(method.getName(), intValue);
		// verify the values changed in the Db
		intValue = projectDatabaseQueries.getInvalidLoginCountUpdateIfNeeded(dbValue);
		intValue1 = projectDatabaseQueries.getActiveColoumnValue(dbValue);
		signIn.verifyDbValuesPostInvalidPasswordAttempts(intValue, intValue1);
		// Again change the values to normal
		intValue = projectDatabaseQueries.getInvalidLoginCountUpdateIfNeeded(dbValue);
	}

}
