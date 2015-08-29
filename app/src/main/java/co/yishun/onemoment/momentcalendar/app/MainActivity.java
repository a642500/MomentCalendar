package co.yishun.onemoment.momentcalendar.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

import co.yishun.onemoment.momentcalendar.AnimationViewPager;
import co.yishun.onemoment.momentcalendar.DayView;
import co.yishun.onemoment.momentcalendar.MomentCalendar;
import co.yishun.onemoment.momentcalendar.MomentMonthView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Calendar now = Calendar.getInstance();
        MomentCalendar calendar = (MomentCalendar) findViewById(R.id.momentCalendar);
        calendar.setTransitionEffect(AnimationViewPager.TransitionEffect.CubeIn);
        calendar.setAdapter(new MomentMonthView.MonthAdapter() {
            @Override
            public void onBindView(Calendar calendar, DayView dayView) {
                if (calendar.compareTo(now) < 0) {
                    dayView.setEnabled(true);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
