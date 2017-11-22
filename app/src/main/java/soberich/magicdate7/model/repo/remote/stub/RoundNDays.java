package soberich.magicdate7.model.repo.remote.stub;

import org.joda.time.LocalDate;

/**
 * Created by soberich on 03.05.17.
 */

public class RoundNDays extends IMagicDate {
    @Override
    int getIncrementInDays() {
        return (int)_multiplctr;
    }
    private int _multiplctr;
    public RoundNDays(LocalDate __d, int __multiplctr) {
        super(__d);
        _multiplctr = __multiplctr;
    }
}
