package co.yishun.onemoment.momentcalendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import java.util.Calendar;

/**
 * TODO: document your custom view class.
 */
public class MomentMonthView extends AdapterView<MonthAdapter> {
    public static final int DAY_NUM_OF_WEEK = 7;
    private String[] dayOfWeekTitle;
    private boolean ignoreLandscape = false;


    private int textColorStateList = R.color.day_text_color;
    private int bgColorStateList = R.drawable.day_bg_selector;

    private float dayTextSize = 12;
    private float titleTextSize = 12;

    private float dayTextHeight;
    private float dayViewLength;


    Calendar mMonth = Calendar.getInstance();

    public MomentMonthView(Context context) {
        super(context);
    }

    public MomentMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MomentMonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) public MomentMonthView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override public MonthAdapter getAdapter() {
        return null;
    }

    @Override public void setAdapter(MonthAdapter adapter) {

    }

    @Override public View getSelectedView() {
        return null;
    }

    @Override public void setSelection(int position) {

    }


//    public MomentMonthView(Context context) {
//        super(context);
//        init(null, 0);
//    }
//
//    public MomentMonthView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(attrs, 0);
//    }
//
//    public MomentMonthView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init(attrs, defStyle);
//    }
//
//    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//    }
//
//    private void init(AttributeSet attrs, int defStyle) {
//        // Load attributes
//        final TypedArray a = getContext().obtainStyledAttributes(
//                attrs, R.styleable.MomentMonthView, defStyle, 0);
//
//        mExampleString = a.getString(
//                R.styleable.MomentMonthView_exampleString);
//        disableTextColor = a.getColor(
//                R.styleable.MomentMonthView_exampleColor,
//                disableTextColor);
//        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
//        // values that should fall on pixel boundaries.
//        dayTextSize = a.getDimension(
//                R.styleable.MomentMonthView_exampleDimension,
//                dayTextSize);
//
//        if (a.hasValue(R.styleable.MomentMonthView_dayOfWeekTitle)) {
//            CharSequence[] s = a.getTextArray(R.styleable.MomentMonthView_dayOfWeekTitle);
//            if (dayOfWeekTitle.length != DAY_NUM_OF_WEEK)
//                throw new IllegalArgumentException("You must provide" +
//                        " seven titles of days of week");
//            dayOfWeekTitle = new String[DAY_NUM_OF_WEEK];
//            for (int i = 0; i < DAY_NUM_OF_WEEK; i++) {
//                dayOfWeekTitle[i] = s[i].toString();
//            }
//        } else {
//            dayOfWeekTitle = getResources().getStringArray(R.array.day_of_week);
//        }
//
//        a.recycle();
//
//        // Set up a default TextPaint object
//        mDayTextPaint = new TextPaint();
//        mDayTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        mDayTextPaint.setTextAlign(Paint.Align.CENTER);
//
//
//        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements();
//    }
//
//    private void invalidateTextPaintAndMeasurements() {
//        mDayTextPaint.setTextSize(dayTextSize);
//        Paint.FontMetrics fontMetrics = mDayTextPaint.getFontMetrics();
//        dayTextHeight = fontMetrics.bottom;
//
//        mWeekTitleTextPaint.setTextSize(titleTextSize);
//        for (int i = 0; i < DAY_NUM_OF_WEEK; i++) {
//            titleWidths[i] = mWeekTitleTextPaint.measureText(dayOfWeekTitle[i]);
//        }
//        titleHeight = mWeekTitleTextPaint.getFontMetrics().bottom;
//    }
//
//    private float[] titleWidths = new float[DAY_NUM_OF_WEEK];
//    private float titleHeight;
//
//    private int paddingLeft;
//    private int paddingTop;
//    private int paddingRight;
//    private int paddingBottom;
//
//    private int contentWidth;
//    private int contentHeight;
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        paddingLeft = getPaddingLeft();
//        paddingTop = getPaddingTop();
//        paddingRight = getPaddingRight();
//        paddingBottom = getPaddingBottom();
//
//        contentWidth = getWidth() - paddingLeft - paddingRight;
//        contentHeight = getHeight() - paddingTop - paddingBottom;
//
//        boolean isPortrait = ignoreLandscape || contentWidth > contentHeight;
//        if (isPortrait) {
//            dayViewLength = Math.max(contentHeight, contentWidth) / DAY_NUM_OF_WEEK;
//        }
//
//
//    }
//
//
//    private TextPaint mDayTextPaint;
//    private TextPaint mWeekTitleTextPaint;
//    private TextPaint mDayDisableTextPaint;
//    private Paint mDayBackgroundPaint;

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//
//        for (int i = 0; i < dayOfWeekTitle.length; i++) {
//            String title = dayOfWeekTitle[i];
//            canvas.drawText(title,
//                    paddingLeft + dayViewLength * i + (dayViewLength - titleWidths[i]) / 2,
//                    paddingTop,
//                    mWeekTitleTextPaint);
//        }
//
//
//        int start = mMonth.getFirstDayOfWeek();
//        for (int i = 0; i < mMonth.getMaximum(Calendar.DAY_OF_MONTH); i++) {
//            drawDay(canvas, i + 1, i % 7 + start, (i - start) / 7, false, false);
//        }
//
//
//        // Draw the text.
//        canvas.drawText(mExampleString, paddingLeft + (contentWidth - mTextWidth) / 2, paddingTop
//                + (contentHeight - dayTextHeight) / 2, mDayTextPaint);
//
//        // Draw the example drawable on top of the text.
//        if (mExampleDrawable != null) {
//            mExampleDrawable.setBounds(paddingLeft, paddingTop,
//                    paddingLeft + contentWidth, paddingTop + contentHeight);
//            mExampleDrawable.draw(canvas);
//        }
//    }

//    /**
//     * Notice: day of week start with Sunday whose evaluate 1
//     *
//     * @param dayOfWeek
//     * @param enable
//     * @param selected
//     */
//    private void drawDay(Canvas canvas, int num, int dayOfWeek, int line, boolean enable, boolean
//            selected) {
//        String text = String.valueOf(num);
//        mDayTextPaint.setColor(enable ? enableTextColor : disableTextColor);
//        float dayTextWidth = mDayTextPaint.measureText(text);
//
//        canvas.drawText(text, paddingLeft + dayViewLength * (dayOfWeek - 1) + (dayViewLength -
//                        dayTextWidth) / 2, paddingTop + titleHeight + line * dayViewLength,
//                mDayTextPaint);
//
//        if (selected) {
//
//        }
//    }
//
//    private void drawToday(Canvas canvas, int dayOfWeek, int line) {
//        //TODO            mDayBackgroundPaint.setStyle(Paint.Style.FILL);
//        mDayBackgroundPaint.setColor(mSelectedBackgroundColor);
//        canvas.drawCircle(paddingLeft + dayViewLength * (dayOfWeek - 1) + dayViewLength / 2,
//                paddingTop + titleHeight + line * dayViewLength + dayViewLength / 2,
//                dayViewLength / 2,
//                mDayBackgroundPaint);
//    }
}
