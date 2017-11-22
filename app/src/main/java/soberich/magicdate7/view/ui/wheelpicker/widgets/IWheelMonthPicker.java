package soberich.magicdate7.view.ui.wheelpicker.widgets;

/**
 * Interface of WheelMonthPicker
 * @author soberich 2017-07-12
 * @version 1
 */
public interface IWheelMonthPicker {
    /**
     * @return
     */
    int getSelectedMonth();

    /**
     * @param month
     */
    void setSelectedMonth(int month);

    /**
     * @return
     */
    int getCurrentMonth();
}
