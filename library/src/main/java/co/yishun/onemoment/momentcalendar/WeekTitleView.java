package co.yishun.onemoment.momentcalendar;

import android.content.Context;
import android.support.annotation.IntDef;
import android.widget.TextView;

/**
 * Created by Carlos on 2015/8/28.
 */
public class WeekTitleView extends TextView {

    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 7;
    private @Week int mWeek;

    public WeekTitleView(Context context, @Week int week) {
        super(context);
        mWeek = week;

        setText(getResources().getStringArray(R.array.day_of_week)[mWeek]);
    }

    @IntDef({SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY})
    public @interface Week {

    }
}
