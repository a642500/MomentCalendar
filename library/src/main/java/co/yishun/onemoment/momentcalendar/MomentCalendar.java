package co.yishun.onemoment.momentcalendar;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Carlos on 2015/8/28.
 */
public class MomentCalendar extends JazzyViewPager {
    CalendarAdapter mAdapter;


    public MomentCalendar(Context context) {
        super(context);
        init();
    }

    public MomentCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mAdapter = new CalendarAdapter(getContext(), this);
    }


}
