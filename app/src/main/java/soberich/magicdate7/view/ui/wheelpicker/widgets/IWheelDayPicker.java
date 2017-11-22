package soberich.magicdate7.view.ui.wheelpicker.widgets;

/**
 * Interface of WheelMonthPicker
 * @author soberich 2017-07-12
 * @version 1
 */
public interface IWheelDayPicker {
    /**
     * @return
     */
    int getSelectedDay();

    /**
     * @param day
     */
    void setSelectedDay(int day);

    /**
     * @return
     */
    int getCurrentDay();

    /**
     * @param year
     * @param month
     */
    void setYearAndMonth(int year, int month);

    /**
     * @return
     */
    int getYear();

    /**
     * @param year ...
     */
    void setYear(int year);

    /**
     * @return
     */
    int getMonth();

    /**
     * @param month
     */
    void setMonth(int month);
}