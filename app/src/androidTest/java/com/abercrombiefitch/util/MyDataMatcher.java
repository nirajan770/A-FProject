package com.abercrombiefitch.util;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.Toolbar;

import com.abercrombiefitch.api.model.Promotion;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MyDataMatcher {

    /**
     * Compares the string with the Toolbar title
     */
    public static ViewInteraction matchToolbarTitle(CharSequence title) {
        return onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(title))));
    }
    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }

    /**
     * Compares the title of a Promotion item with the parameter passed
     *
     */
    public static Matcher<Object> checkDataModelTitle(String titleInput) {
        return compareTitle(equalTo(titleInput));
    }

    private static Matcher<Object> compareTitle(final Matcher<String> expectedObject) {
        return new BoundedMatcher<Object, Promotion>(Promotion.class) {
            /**
             * Generates a description of the object.  The description may be part of a
             * a description of a larger object of which this is just a component, so it
             * should be worded appropriately.
             *
             * @param description The description to be built or appended to.
             */
            @Override
            public void describeTo(Description description) {
                description.appendText("Promotion object title");
            }

            @Override
            protected boolean matchesSafely(Promotion promotion) {
                if( expectedObject.matches(promotion.getTitle()))
                    return true;
                else
                    return false;
            }
        };
    }
}
