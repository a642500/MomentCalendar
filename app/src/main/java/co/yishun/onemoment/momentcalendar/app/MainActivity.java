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
                dayView.setEnabled(calendar.get(Calendar.DAY_OF_MONTH) % 2 == 0
                                || calendar.get(Calendar.DAY_OF_MONTH) % 5 == 0
                                || calendar.get(Calendar.DAY_OF_MONTH) % 3 == 0
                );
                if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                        && calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                        && calendar.get(Calendar.DAY_OF_MONTH) + 1 == now.get(Calendar.DAY_OF_MONTH)
                        ) {
                    dayView.setEnabled(true);
                    dayView.setSelected(true);
                } else dayView.setSelected(false);
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
