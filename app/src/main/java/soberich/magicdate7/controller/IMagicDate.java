package soberich.magicdate7.controller;

/**
 * Created by soberich on 30.04.17.
 */
import org.joda.time.LocalDate;

abstract class IMagicDate {
    private LocalDate _Date;
    //public IMagicDate() {}/* = delete*/

    IMagicDate(LocalDate __d) {
        _Date = __d;
    }
    abstract int getIncrementInDays();
    public LocalDate getResLocalDate() {
        return _Date;
    }
    void Step() {
        this._Date = this._Date.plusDays(this.getIncrementInDays());
    }
    void Run(int __times) {
        for (int i = 0; i < __times; i++) {
            this.Step();
        }
    }
}
