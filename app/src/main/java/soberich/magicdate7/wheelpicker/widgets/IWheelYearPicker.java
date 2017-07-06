package soberich.magicdate7.wheelpicker.widgets;

/**
 * Interface of WheelYearPicker
 * @author soberich 2017-07-12
 * @version 1
 */
public interface IWheelYearPicker {
    /**
     * @param start
     * @param end
     */
    void setYearFrame(int start, int end);

    /**
     * @return
     */
    int getYearStart();

    /**
     * @param start
     */
    void setYearStart(int start);

    /**
     * @return
     */
    int getYearEnd();

    /**
     * @param end
     */
    void setYearEnd(int end);

    /**
     * @return
     */
    int getSelectedYear();

    /**
     * @param year
     */
    void setSelectedYear(int year);

    /**
     * @return
     */
    int getCurrentYear();
}