package soberich.magicdate7.model.repo.remote.stub;

import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 *
 * Created by soberich on 30.04.17.
 */

public class Magic19 extends IMagicDate {
    private LocalDate _origDate;
    private int _iterations;
    public Magic19(LocalDate __d) {
        super(__d);
        _origDate = __d;
        _iterations = 1;
    }
    @Override
    int getIncrementInDays() {
        int result = 0;
        result += Days.daysBetween(this._origDate, this._origDate.plusYears(19 * _iterations)).getDays();
        result += Days.daysBetween(this._origDate, this._origDate.plusMonths(19 * _iterations)).getDays();
        result += Days.daysBetween(this._origDate, this._origDate.plusDays(19 * 7 * _iterations)).getDays();
        result += Days.daysBetween(this._origDate, this._origDate.plusDays(19 * _iterations)).getDays();
        result = Days.daysBetween(this.getResLocalDate(), this._origDate.plusDays(result)).getDays();
        ++this._iterations;
        return result;
    }
}
