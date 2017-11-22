package soberich.magicdate7.model.repo.remote.stub;

import org.joda.time.LocalDate;

/**
 * Created by soberich on 03.05.17.
 */

public class MarsCalculus extends IMagicDate {

    private static final double _YEARS_RATIO_;
    private static final double _DAYS_IN_YEAR_RATIO_;
    private static final int    _WEEKS_SEARCH_CRITERIA_;
    static {
        _YEARS_RATIO_ = 1.8808476;
        _DAYS_IN_YEAR_RATIO_ = 686.98;
        _WEEKS_SEARCH_CRITERIA_ = 100;
    }

    private double _modulo;

    @Override
    int getIncrementInDays() {
        double intpart = (int)MarsCalculus._DAYS_IN_YEAR_RATIO_;
        this._modulo += MarsCalculus._DAYS_IN_YEAR_RATIO_ - intpart;
        int result = (int)(intpart + this._modulo);
        this._modulo -= result - (int)MarsCalculus._DAYS_IN_YEAR_RATIO_;
        return result;
    }

    public MarsCalculus(LocalDate __d)
    {
        super(__d);
        this._modulo = 0;
    }


}
