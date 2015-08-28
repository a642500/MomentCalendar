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

/**
 * Created by yyz on 7/19/15.
 */
public class DayView extends View {

    private Paint mBackgroundPaint;
    private TextPaint mTextPaint;
    private String day;
    private Rect mTextRect;
    private TimeStatus mTimeStatus = TimeStatus.FUTURE;
    private int BLACK = getResources().getColor(R.color.colorBlack);
    private int WHITE = getResources().getColor(R.color.colorWhite);
    private int GRAY = getResources().getColor(R.color.colorGray);
    private int ORANGE = getResources().getColor(R.color.colorOrange);
    private float mTextSize = getResources().getDimension(R.dimen.MMV_dayNumTextSize);

    public DayView(Context context, int day) {
        super(context);
        init(day);
    }

    public DayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(12);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(12);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(12);
    }

    public void setTimeStatus(TimeStatus time) {
        this.mTimeStatus = time;
        invalidate();
    }

    private void init(int day) {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(ORANGE);

        this.day = String.valueOf(day);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int h = getMeasuredHeight();
//        int w = getMeasuredWidth();

        mTextPaint.getTextBounds(day, 0, day.length(), mTextRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final float ox = canvas.getWidth() / 2;
        final float oy = canvas.getHeight() / 2;
        final float r = Math.min(ox, oy);

        if (!isEnabled() || mTimeStatus == TimeStatus.FUTURE) {
            // today should be enable
            mTextPaint.setColor(GRAY);
        } else if (isSelected()) {
            // if can be selected, it of priority to others
            mTextPaint.setColor(WHITE);
            canvas.drawCircle(ox, oy, r, mBackgroundPaint);
        } else if (mTimeStatus == TimeStatus.TODAY)
            mTextPaint.setColor(ORANGE);
        else {
            mTextPaint.setColor(BLACK);
        }


        final float x = ox - mTextRect.width() / 2;
        final float y = oy + mTextRect.height() / 2;

        canvas.drawText(day, x, y, mTextPaint);
    }

    public enum TimeStatus {
        TODAY, PAST, FUTURE
    }
}
