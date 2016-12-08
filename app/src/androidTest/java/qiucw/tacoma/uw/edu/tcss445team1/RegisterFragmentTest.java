package qiucw.tacoma.uw.edu.tcss445team1;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import qiucw.tacoma.uw.edu.tcss445team1.authenticate.SignInActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
/**
 * Sally Budack
 * RegisterFragmentTest
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterFragmentTest {

    private String userId;
    private String password;
    private String confirmPassword;

    private boolean loggedIn;
    private SharedPreferences prefs;

    /**
     * test rule
     */
    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>
            (SignInActivity.class);

    /**
     * log out before registering a new user.
     */
    @Before
    public void logoutFragmentTest() {
        prefs = mActivityTestRule.getActivity().getSharedPreferences("qiucw.tacoma.uw.edu.tcss445team1"
                , Context.MODE_PRIVATE);
        if (prefs.getBoolean("loggedin", false)){
            loggedIn = false;
            ViewInteraction actionMenuItemView = onView(
                    allOf(withId(R.id.action_logout), withText("logout"), withContentDescription
                            ("logout"), isDisplayed()));
            actionMenuItemView.perform(click());
        }

    }

    /**
     * generate new userId and password before registering.
     */
    @Before
    public void setUser(){
        Random random = new Random();
        int length = random.nextInt(10) + 6;
        char data = ' ';
        String dat = "";
        for (int i=0; i<=length; i++) {
            data = (char)(random.nextInt(25)+97);
            dat = data + dat;
        }
        userId = dat;

        length = random.nextInt(10) + 6;
        data = ' ';
        dat = "";
        for (int i=0; i<=length; i++) {
            data = (char)(random.nextInt(25)+97);
            dat = data + dat;
        }
        password = dat;
        confirmPassword = password;
    }

    /**
     * test register
     */
    @Test
    public void registerFragmentTest() {
        if(!loggedIn) {
            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.reg_button), withText("Register"), isDisplayed()));
            appCompatButton.perform(click());

            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.userid_edit), isDisplayed()));
            appCompatEditText.perform(replaceText(userId), closeSoftKeyboard());

            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.userid_edit), withText(userId), isDisplayed()));
            appCompatEditText2.perform(pressImeActionButton());

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.pwd_edit), isDisplayed()));
            appCompatEditText3.perform(replaceText(confirmPassword), closeSoftKeyboard());

            ViewInteraction appCompatEditText4 = onView(
                    allOf(withId(R.id.pwd_edit), withText(confirmPassword), isDisplayed()));
            appCompatEditText4.perform(pressImeActionButton());

            ViewInteraction appCompatEditText5 = onView(
                    allOf(withId(R.id.pwd_edit_com), isDisplayed()));
            appCompatEditText5.perform(replaceText(confirmPassword), closeSoftKeyboard());

            ViewInteraction appCompatEditText6 = onView(
                    allOf(withId(R.id.pwd_edit_com), withText(confirmPassword), isDisplayed()));
            appCompatEditText6.perform(pressImeActionButton());

            ViewInteraction appCompatButton2 = onView(
                    allOf(withId(R.id.reg_button), withText("Register"), isDisplayed()));
            appCompatButton2.perform(click());

            ViewInteraction appCompatButton5 = onView(
                    allOf(withId(R.id.login_button), withText("Login"), isDisplayed()));
            appCompatButton5.perform(click());

            ViewInteraction appCompatEditText13 = onView(
                    allOf(withId(R.id.userid_edit), isDisplayed()));
            appCompatEditText13.perform(click());

            ViewInteraction appCompatEditText14 = onView(
                    allOf(withId(R.id.userid_edit), isDisplayed()));
            appCompatEditText14.perform(replaceText(userId), closeSoftKeyboard());

            ViewInteraction appCompatEditText15 = onView(
                    allOf(withId(R.id.userid_edit), withText(userId), isDisplayed()));
            appCompatEditText15.perform(pressImeActionButton());

            ViewInteraction appCompatEditText16 = onView(
                    allOf(withId(R.id.pwd_edit), isDisplayed()));
            appCompatEditText16.perform(replaceText(confirmPassword), closeSoftKeyboard());

            ViewInteraction appCompatEditText17 = onView(
                    allOf(withId(R.id.pwd_edit), withText(confirmPassword), isDisplayed()));
            appCompatEditText17.perform(pressImeActionButton());

            ViewInteraction appCompatButton6 = onView(
                    allOf(withId(R.id.login_button), withText("Login"), isDisplayed()));
            appCompatButton6.perform(click());
        }

    }



    /**
     * Android generated testing method
     *
     * @param parentMatcher parent for matching
     * @param position postition matched
     * @return view matched
     */
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}