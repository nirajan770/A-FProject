package com.abercrombiefitch;

import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerMatchers;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.abercrombiefitch.ui.MainActivity;
import com.abercrombiefitch.util.MyDataMatcher;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.closeDrawer;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Tests to verify {@link MainActivity} behavior
 *
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    /**
    * A JUnit {@link Rule @Rule} to launch your activity under test. This is a replacement
    * for {@link ActivityInstrumentationTestCase2}.
    **/

    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    /**
     * Test for the toolbar
     */
    @Test
    public void toolbarTitle() {
        CharSequence title = InstrumentationRegistry.getTargetContext().getString(R.string.app_name);
        MyDataMatcher.matchToolbarTitle(title);
    }



    @Test
    public void testNavigationDrawer() {
        //open and close Drawer
        openDrawer(R.id.drawer);
        onView(withId(R.id.drawer)).check(ViewAssertions.matches(isOpen()));
        closeDrawer(R.id.drawer);
        onView(withId(R.id.drawer)).check(ViewAssertions.matches(DrawerMatchers.isClosed()));
        // Open drawer and click an item
        openDrawer(R.id.drawer);
        onView(allOf(withText(R.string.drawer_item_help))).perform(click());
        // clicking an item should close the drawer
        onView(withId(R.id.drawer)).check(ViewAssertions.matches(isClosed()));
    }

    /*@Test
    public void testRecyclerView() {
        onView(withId(R.id.content_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.card_view)));
    }
    private static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                if (v != null) {
                    v.performClick();
                }
            }
        };
    }*/



}
