package co.yishun.onemoment.momentcalendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * TODO: document your custom view class.
 */
public class MomentMonthView extends AdapterView<MonthAdapter> {
    private MonthAdapter mAdapter;

    private int mItemLength = 0;

    private int GRAY = getResources().getColor(R.color.colorGray);
    private int ORANGE = getResources().getColor(R.color.colorOrange);

    private Paint mWeekTitlePaint;
    private String[] mWeekTitleArray;
    private float mWeekTitlePadding = getResources().getDimension(R.dimen.MMV_weekTitlePadding);
    private float mWeekTitleSize = getResources().getDimension(R.dimen.MMV_weekTitleSize);
    private Rect mWeekTextMeasureRect;
    private float mWeekTitleHeight;

    private Paint mMonthTitlePaint;
    private String mMonthTitle;
    private float mMonthTitlePadding = getResources().getDimension(R.dimen.MMV_monthTitlePadding);
    private float mMonthTitleSize = getResources().getDimension(R.dimen.MMV_monthTitleSize);
    private Rect mMonthTextMeasureRect;
    private float mMonthTitleHeight;

    private Calendar mCalendar;
    private int mWeekNum;
    // for DayView
    private LayoutParams mItemParams;

    public MomentMonthView(Context context, Calendar calendar) {
        super(context);
        mCalendar = calendar;
        init();
    }

    public MomentMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCalendar = Calendar.getInstance();
        init();
    }

    public MomentMonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCalendar = Calendar.getInstance();
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MomentMonthView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mCalendar = Calendar.getInstance();
        init();
    }

    public void init() {
        setWillNotDraw(false);
        mMonthTitle = new SimpleDateFormat("yyyy/MM", Locale.getDefault()).format(mCalendar.getTime());
        mMonthTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mMonthTitlePaint.setTextSize(mMonthTitleSize);
        mMonthTitlePaint.setColor(ORANGE);
        mMonthTextMeasureRect = new Rect();

        mWeekTitleArray = getResources().getStringArray(R.array.day_of_week);
        mWeekTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mWeekTitlePaint.setTextSize(mWeekTitleSize);
        mWeekTitlePaint.setColor(GRAY);
        mWeekTextMeasureRect = new Rect();

        mWeekNum = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

        mItemParams = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        if (isInEditMode()) {
            setAdapter(new MonthAdapter(mCalendar) {
                @Override
                public void onBindView(Calendar calendar, DayView dayView) {

                }
            });
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int rw = MeasureSpec.getSize(widthMeasureSpec);
//        int rh = MeasureSpec.getSize(heightMeasureSpec);
        int w = rw - getPaddingLeft() - getPaddingRight();

        mItemLength = w / 7;
        mMonthTitlePaint.getTextBounds(mMonthTitle, 0, mMonthTitle.length(), mMonthTextMeasureRect);
        mMonthTitleHeight = mMonthTextMeasureRect.height() + mMonthTitlePadding * 2;
        mWeekTitlePaint.getTextBounds(mWeekTitleArray[0], 0, 1, mWeekTextMeasureRect);
        mWeekTitleHeight = mWeekTextMeasureRect.height() + mWeekTitlePadding * 2;

        float h = mItemLength * mWeekNum + mMonthTitleHeight + mWeekTitleHeight + getPaddingTop() + getPaddingBottom();

        mItemParams.width = mItemLength;
        mItemParams.height = mItemLength;

        setMeasuredDimension(w, (int) h);
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        if (mAdapter == null) {
            return;
        }

        if (getChildCount() == 0) {
            int position = 0;
            while (position < mAdapter.getCount()) {
                View child = mAdapter.getView(position, null, this);
                addViewInLayout(child, -1, mItemParams, true);
                child.measure(MeasureSpec.EXACTLY | mItemLength, MeasureSpec.EXACTLY);
                position++;
            }
        }

        positionItems();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        float ox = canvas.getWidth() / 2 - mMonthTextMeasureRect.width() / 2;
        float oy = mMonthTextMeasureRect.height() + mMonthTitlePadding;
        canvas.drawText(mMonthTitle, ox, oy, mMonthTitlePaint);

        float y = mMonthTitleHeight + mWeekTextMeasureRect.height() + mWeekTitlePadding;
        for (int i = 0; i < mWeekTitleArray.length; i++) {
            float width = mWeekTitlePaint.measureText(mWeekTitleArray[i]);
            canvas.drawText(mWeekTitleArray[i], i * mItemLength + (mItemLength - width) / 2, y, mWeekTitlePaint);
        }
    }

    /**
     * Positions the children at the "correct" positions
     */
    private void positionItems() {
        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            mCalendar.set(Calendar.DAY_OF_MONTH, index + 1);
            int column = mCalendar.get(Calendar.DAY_OF_WEEK);// start 1 == Sunday
            int row = mCalendar.get(Calendar.WEEK_OF_MONTH);// start 1

            int mLeft = getPaddingLeft() + (column - 1) * mItemLength;
            int mTop = (int) (getPaddingTop() + mMonthTitleHeight + mWeekTitleHeight + (row - 1) * mItemLength);

            child.layout(mLeft, mTop, mLeft + mItemLength, mTop + mItemLength);
        }
    }

    @Override
    public MonthAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(MonthAdapter adapter) {
        mAdapter = adapter;
        removeAllViewsInLayout();
        requestLayout();
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

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
