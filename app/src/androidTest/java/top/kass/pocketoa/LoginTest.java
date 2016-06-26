package top.kass.pocketoa;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import top.kass.pocketoa.ui.activity.LoginActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    public static final String STRING_TO_BE_TYPED = "Espresso";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Test
    public void test() {
        onView(withId(R.id.etUsername)).perform(typeText("8088"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
    }

}
