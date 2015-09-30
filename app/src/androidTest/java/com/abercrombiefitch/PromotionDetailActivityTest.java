package com.abercrombiefitch;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.abercrombiefitch.api.model.Button;
import com.abercrombiefitch.api.model.Promotion;
import com.abercrombiefitch.ui.PromotionDetailActivity;
import com.abercrombiefitch.util.MyDataMatcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class PromotionDetailActivityTest  {

    private Promotion item;
    private Button button;

    private static final String ITEM_TITLE = "Title";
    private static final String ITEM_DESCRIPTION = "Description";
    private static final String ITEM_URL = "http://anf.scene7.com/is/image/anf/anf-US-20150629-app-women-shorts";
    private static final String ITEM_FOOTER = "Footer";

    private static final String BUTTON_TARGET = "https://m.abercrombie.com";
    private static final String BUTTON_TITLE = "TEST BUTTON";

    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test. This is a replacement
     * for {@link ActivityInstrumentationTestCase2}.
     *
     * (third) launchActivity parameter set to false since Activity is started with Intent
     **/
    @Rule
    public ActivityTestRule<PromotionDetailActivity> mActivityTestRule =
            new ActivityTestRule<PromotionDetailActivity>(PromotionDetailActivity.class, true, false);

    @Before
    public void createPromotionObject() {
        item = new Promotion();
        item.setTitle(ITEM_TITLE);
        item.setDescription(ITEM_DESCRIPTION);
        item.setImage(ITEM_URL);
        item.setFooter(ITEM_FOOTER);
        button = new Button();
        button.setTitle(BUTTON_TITLE);
        button.setTarget(BUTTON_TARGET);
        List<Button> list = new ArrayList<>();
        list.add(button);
        // set the list of buttons
        item.setButton(list);
    }

    @Test
    public void startActivityWithIntentTest() throws JsonProcessingException {

        Context targetContext = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(targetContext, PromotionDetailActivity.class);
        intent.putExtra(PromotionDetailActivity.KEY_INTENT_PROMOTION_ITEM, new ObjectMapper().writeValueAsString(item));
        mActivityTestRule.launchActivity(intent); // launch the activity with the intent

        // check if title matches
        MyDataMatcher.matchToolbarTitle(ITEM_TITLE);

        // check the description text
        onView(withId(R.id.detail_item_description)).check(matches(withText(ITEM_DESCRIPTION)));
        // check the footer text
        onView(withId(R.id.detail_item_footer)).check(matches(withText(ITEM_FOOTER)));
        // check the button title
        onView(withId(R.id.promo_button)).check(matches(withText(BUTTON_TITLE)));
        // click the button
        onView(withId(R.id.promo_button)).perform(click());
        // Check if WebView Activity is started by comparing the toolbar title
        MyDataMatcher.matchToolbarTitle(targetContext.getString(R.string.title_activity_web_view_acitivity));
        // Navigate back to PromotionDetail Activity
        pressBack();

        // check the toolbar title again
        MyDataMatcher.matchToolbarTitle(ITEM_TITLE);

    }





}
