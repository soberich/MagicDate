package soberich.magicdate7.model.repo.remote.stub;

/**
 *
 * Created by soberich on 30.04.17.
 */
import org.joda.time.LocalDate;

public abstract class IMagicDate {
    private LocalDate _Date;
    //public IMagicDate() {}/* = delete*/ // TODO Qt rudiment

    IMagicDate(LocalDate __d) {
        _Date = __d;
    }
    abstract int getIncrementInDays();
    public LocalDate getResLocalDate() {
        return _Date;
    }
    public void Step() {
        this._Date = this._Date.plusDays(this.getIncrementInDays());
    }
    void Run(int __times) {
        for (int i = 0; i < __times; i++) {
            this.Step();
        }
    }
}
