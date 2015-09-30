package com.abercrombiefitch;

import android.content.Intent;
import android.support.test.espresso.web.webdriver.DriverAtoms;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.abercrombiefitch.ui.WebViewAcitivity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.web.assertion.WebViewAssertions.webContent;
import static android.support.test.espresso.web.matcher.DomMatchers.hasElementWithId;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.clearElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WebViewActivityTest {

    // Load the html file from assets folder for testing purposes
    private static final String BUTTON_TARGET = "file:///android_asset/web_test.html";

    private static final String EMAIL_INPUT = "test@gmail.com";
    private static final String PASSWORD_INPUT = "abercrombie";

    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test. This is a replacement
     * for {@link ActivityInstrumentationTestCase2}.
     *
     * initialTouchMode and launchActivity parameter set to false since Activity is started with Intent
     **/
    @Rule
    public ActivityTestRule<WebViewAcitivity> mActivityTestRule =
            new ActivityTestRule<WebViewAcitivity>(WebViewAcitivity.class, false, false) {
                @Override
                protected void afterActivityLaunched() {
                    // Since the only way to automate WebViews is through javascript, it must be enabled.
                    // WebViewActivity has javascript turned on by default, however,
                    // Other WebViews in your app may have javascript turned off
                    onWebView().forceJavascriptEnabled();
                }
            };

    /**
     * Checks for different html elements with their ids
     */
    @Test
    public void checkElements() {
        mActivityTestRule.launchActivity(intentForWeb()); // launch the activity with the intent

        onWebView().check(webContent(hasElementWithId("email_input")));
        onWebView().check(webContent(hasElementWithId("password_input")));
        onWebView().check(webContent(hasElementWithId("submit_button")));
    }



    @Test
    public void testWebLoad() {
        mActivityTestRule.launchActivity(intentForWeb()); // launch the activity with the intent

        // selects the webview, since there is only one in the layout
        onWebView()
                // find the element by id
                .withElement(findElement(Locator.ID, "email_input"))
                // clear any input
                .perform(clearElement())
                // enter text
                .perform(DriverAtoms.webKeys(EMAIL_INPUT))
                // find button
                .withElement(findElement(Locator.ID, "submit_button"))
                // invoke javascript click function
                .perform(webClick());

        onWebView()
                // find the element by id
                .withElement(findElement(Locator.ID, "password_input"))
                        // clear any input
                .perform(clearElement())
                        // enter text
                .perform(DriverAtoms.webKeys(PASSWORD_INPUT))
                        // find button
                .withElement(findElement(Locator.ID, "submit_button"))
                        // invoke javascript click function
                .perform(webClick());
    }


    /**
     * @return intent for the webview
     */
    private static Intent intentForWeb() {
        Intent intent = new Intent();
        intent.putExtra(WebViewAcitivity.KEY_URL_TO_LOAD, BUTTON_TARGET);
        return intent;
    }
}
