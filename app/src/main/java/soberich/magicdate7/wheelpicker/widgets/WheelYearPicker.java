package soberich.magicdate7.wheelpicker.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import soberich.magicdate7.wheelpicker.WheelPicker;

/**
 * Picker for Years
 * @author soberich 2017-07-12
 * @version 1
 */
public class WheelYearPicker extends WheelPicker implements IWheelYearPicker {
    private int mYearStart = 1900, mYearEnd = 2100;
    private int mSelectedYear;

    /**
     * Shadows drawables
     */
    private GradientDrawable topShadow,
            bottomShadow;
    /**
     * Center Line
     */
    private Drawable centerDrawable;

    private static final int[] SHADOWS_COLORS = new int[]{0xFF111111,
            0x00AAAAAA, 0x00AAAAAA};

    public WheelYearPicker(Context context) {
        this(context, null);
    }

    public WheelYearPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        updateYears();
        mSelectedYear = Calendar.getInstance().get(Calendar.YEAR);
        updateSelectedYear();
    }

    private void updateYears() {
        List<Integer> data = new ArrayList<>();
        for (int i = mYearStart; i <= mYearEnd; i++)
            data.add(i);
        super.setData(data);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*if (centerDrawable == null) {
            centerDrawable = getContext().getDrawable(R.drawable.wheel_val);
        }

        if (topShadow == null) {
            topShadow = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                    SHADOWS_COLORS);
        }

        if (bottomShadow == null) {
            bottomShadow = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                    SHADOWS_COLORS);
        }
        int center = getHeight() / 2;
        int offset = (int) (getHeight() / 2 * 1.2);
        centerDrawable.setBounds(0, center - offset, getWidth(), center
                + offset);
        centerDrawable.draw(canvas);
        int height = (int) (1.5 * getHeight());
        topShadow.setBounds(0, 0, getWidth(), height);
        topShadow.draw(canvas);

        bottomShadow
                .setBounds(0, getHeight() - height, getWidth(), getHeight());
        bottomShadow.draw(canvas);

        setBackgroundResource(R.drawable.wheel_bg);*/
    }

    private void updateSelectedYear() {
        setSelectedItemPosition(mSelectedYear - mYearStart);
    }

    @Override
    public void setData(List data) {
        throw new UnsupportedOperationException("You can not invoke setData in WheelYearPicker");
    }

    @Override
    public void setYearFrame(int start, int end) {
        mYearStart = start;
        mYearEnd = end;
        mSelectedYear = getCurrentYear();
        updateYears();
        updateSelectedYear();
    }

    @Override
    public int getYearStart() {
        return mYearStart;
    }

    @Override
    public void setYearStart(int start) {
        mYearStart = start;
        mSelectedYear = getCurrentYear();
        updateYears();
        updateSelectedYear();
    }

    @Override
    public int getYearEnd() {
        return mYearEnd;
    }

    @Override
    public void setYearEnd(int end) {
        mYearEnd = end;
        updateYears();
    }

    @Override
    public int getSelectedYear() {
        return mSelectedYear;
    }

    @Override
    public void setSelectedYear(int year) {
        mSelectedYear = year;
        updateSelectedYear();
    }

    @Override
    public int getCurrentYear() {
        return Integer.valueOf(String.valueOf(getData().get(getCurrentItemPosition())));
    }
}