package com.ethvotingverifier.fragments.settings.activities.statistics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.ethvotingverifier.R;
import com.ethvotingverifier.database.DateConverter;
import com.ethvotingverifier.database.Vote;
import com.ethvotingverifier.models.Election;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChartView extends View {

    Paint mBarPaint, mGridPaint, mGuidelinePaint, mText;
    float mPadding = 60;
    ArrayList<Vote> votes;
    Date startDate, endDate;
    Calendar startDateCalendar, endDateCalendar;

    float data[];
    int dataCount = 7;

    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void addVotes(ArrayList<Vote> votes) {
        this.votes = votes;

        int[] votesPerDay = new int[7];
        int day = 0;
        Calendar firstDay = startDateCalendar;
        Calendar loopCalendar = Calendar.getInstance();
        Calendar endDateCalenderOneDayMore = endDateCalendar;
        endDateCalenderOneDayMore.add(Calendar.DATE, 1);
        if(votes != null) {
            while (!firstDay.equals(endDateCalenderOneDayMore)) {
                for (int i = 0; i < votes.size(); i++) {
                    loopCalendar.setTime(votes.get(i).checkedDate);
                    if (firstDay.get(Calendar.DAY_OF_YEAR) == loopCalendar.get(Calendar.DAY_OF_YEAR)) {
                        votesPerDay[day]++;
                    }
                }
                day++;
                firstDay.add(Calendar.DATE, 1);
            }
        }
        data = new float[dataCount];
        for(int i = 0 ; i< dataCount; i ++) {
            data[i] = (float)votesPerDay[i] / 50;
        }

    }

    private void init() {

        mBarPaint = new Paint();
        mBarPaint.setStyle(Paint.Style.FILL);
        mBarPaint.setColor(getResources().getColor(R.color.mediumBlue));

        mGridPaint = new Paint();
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setColor(Color.BLACK);
        mGridPaint.setStrokeWidth(10);

        mGuidelinePaint = new Paint();
        mGuidelinePaint.setStyle(Paint.Style.STROKE);
        mGuidelinePaint.setColor(Color.DKGRAY);
        mGuidelinePaint.setStrokeWidth(1f);

        mText = new Paint();
        mText.setColor(Color.BLACK);
        mText.setTextSize(32);

        endDateCalendar = Calendar.getInstance();

        startDateCalendar = Calendar.getInstance();
        startDateCalendar.add(Calendar.DATE, -6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int height = getHeight() - 120;
        final int width = getWidth();
        final float gridLeft = mPadding;
        final float gridBottom = height - mPadding;
        final float gridTop = mPadding;
        final float gridRight = width - mPadding;

        canvas.drawLine(gridLeft, gridBottom, gridLeft, gridTop, mGridPaint);
        canvas.drawLine(gridLeft, gridBottom, gridRight, gridBottom, mGridPaint);

        float noGuides = 10f;
        float guideLineSpacing = (gridBottom - gridTop) / noGuides;
        float y;
        int gridValue = 50;
        for (int i = 0; i < noGuides; i++) {
            y = gridTop + i * guideLineSpacing;
            canvas.drawLine(gridLeft, y, gridRight, y, mGuidelinePaint);

            canvas.drawText(String.valueOf(gridValue), gridLeft - mPadding, y + 16, mText);
            gridValue -= 5;
        }

        float columnWidth = (gridRight - gridLeft) / (dataCount * 2 + 1);
        float columnLeft = gridLeft + columnWidth;
        float columnRight = columnLeft + columnWidth;

        float startTextDayPosX = gridLeft + columnWidth / 1.2f;
        float textDayPosY = gridBottom + 40;

        Calendar firstDayCalendar = Calendar.getInstance();
        firstDayCalendar.add(Calendar.DATE, -6);

        for (float percentage : data) {

            // Calculate top of column based on percentage.
            float top = gridTop + guideLineSpacing * (1f - percentage) * 10;
            canvas.drawRect(columnLeft, top, columnRight, gridBottom, mBarPaint);

            // Shift over left/right column bounds
            columnLeft = columnRight + columnWidth;
            columnRight = columnLeft + columnWidth;

            String date = DateConverter.dateToDayAndMonth(firstDayCalendar.getTime());
            canvas.drawText(date, startTextDayPosX, textDayPosY, mText);
            startTextDayPosX += columnWidth * 2;
            firstDayCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }

    }
}
