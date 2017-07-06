package soberich.magicdate7.controller;

import org.joda.time.LocalDate;

/**
 * Created by soberich on 03.05.17.
 */

class RoundNDays extends IMagicDate {
    @Override
    int getIncrementInDays() {
        return (int)_multiplctr;
    }
    private int _multiplctr;
    RoundNDays(LocalDate __d, int __multiplctr) {
        super(__d);
        _multiplctr = __multiplctr;
    }
}
