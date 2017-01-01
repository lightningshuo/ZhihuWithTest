package com.lightning.zhihulite;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.ListView;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<> (MainActivity.class);
    private Solo solo;

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    @Test
    public void testScroll() throws Exception{
        solo.unlockScreen();
        solo.sleep(3000);
        solo.scrollToSide(Solo.RIGHT);
        solo.sleep(500);
        solo.scrollToSide(Solo.RIGHT);
        solo.sleep(500);
        solo.goBack();
        solo.sleep(2000);
        solo.assertCurrentActivity("not found the activity", "MainActivity");
        solo.clickOnScreen(900, 147);
        solo.sleep(500);
        solo.goBack();
        solo.sleep(2000);
        solo.assertCurrentActivity("not found the activity", "MainActivity");

    }
    //Click through all this listview
    @Ignore
    public void testListView() throws Exception{
        solo.unlockScreen();
        solo.sleep(2000);
        ListView lv = (ListView)solo.getView("lv");
        int first = lv.getFirstVisiblePosition();
        int last = lv.getLastVisiblePosition();
        int max = lv.getCount();
        //遍历listview上的item，逐个点击
        if (last != 0 && last != max){
            int timeScroll = max/last;
            int finalStep = max%last;
            for(int i = 1; i<= timeScroll; i++){
                for (int j = 0; j < last; j++){
                    solo.clickOnView(lv.getChildAt(j));
                    solo.sleep(500);
                }
                solo.scrollListToLine(lv, last * i);
                solo.sleep(2000);
            }
            for (int k = last - finalStep + 1; k <= last; k++){
                solo.clickOnView(lv.getChildAt(k));
            }
            solo.sleep(500);
            solo.scrollToTop();
            solo.drag(300, 300, 500, 1000, 10);
            solo.sleep(6000);
            solo.assertCurrentActivity("not found the activity", "MainActivity");
        }
    }

}
