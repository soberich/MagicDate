package soberich.magicdate7.model.repo.remote;

/**
 * Handling class
 * Created by soberich on 09.05.17.
 */

import android.content.Context;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import soberich.magicdate7.R;
import soberich.magicdate7.model.repo.remote.stub.IMagicDate;
import soberich.magicdate7.model.repo.remote.stub.Magic19;
import soberich.magicdate7.model.repo.remote.stub.MarsCalculus;
import soberich.magicdate7.model.repo.remote.stub.RoundNDays;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(Context context, LocalDate localDate) {
        HashMap<String, List<String>> expListWholeSet = new HashMap<String, List<String>>();

        IMagicDate magic19 = new Magic19(localDate);
        LocalDate now = LocalDate.now();
        List<String> magic19List = new ArrayList<>();
        int magic19StepСount = 0;
        String prefix = new String("");
        while (magic19.getResLocalDate().plusDays(1000).isBefore(now)) {
            magic19.Step();
            magic19StepСount++;
            /**
             * if statement checks preferred level of concurrency (in common sense meaning) in LocalDates
             */
            boolean isBdayExactelyAtM19 = magic19.getResLocalDate().getDayOfMonth() == localDate.getDayOfMonth()
                    && magic19.getResLocalDate().getMonthOfYear() == localDate.getMonthOfYear();
            if (isBdayExactelyAtM19){
                prefix = "Exactely at ";
            } else {
                prefix = "Just " +
                        String.valueOf(Math.abs(Days.daysBetween(magic19.getResLocalDate(),
                                localDate.plusYears(21 * magic19StepСount)).getDays()))
                        + " day(s) away from ";
            }
        }
        String magic19StepCountStr = String.valueOf(19 * magic19StepСount);
        magic19List.add(context.getResources().getString(R.string.if_magic19_equals_today_text) + "Marvelous! " + prefix + "YOUR BIRTHDAY you will be also as old as "
        + magic19StepCountStr + " years " + magic19StepCountStr + " months " + magic19StepCountStr + " weeks and " + magic19StepCountStr + " days ! BOOM!!  Rare, Ha?");

        expListWholeSet.put(magic19.getResLocalDate().toString() + " "
                + String.valueOf(21 * magic19StepСount) + (magic19StepСount == 1 ? "st" : (magic19StepСount == 2 ? "nd" : "th"))
                + " birthday is\n"
                + " \u2014>  "
                + magic19StepCountStr +     " years\n "
                + "  +  " + magic19StepCountStr +  " months\n "
                + "  +  " + magic19StepCountStr + " weeks\n "
                + "  +  " + magic19StepCountStr + " days =) ", magic19List);

        ////////////////////////////////////////////////////////////////
        IMagicDate marsCalculus = new MarsCalculus(localDate);
        List<String> marsCalculusList = new ArrayList<>();

        int marsStepCount = 0;
        while (marsCalculus.getResLocalDate().plusDays(150).isBefore(now)) {

            marsCalculus.Step();
            marsStepCount++;
        }
        marsCalculusList.add(context.getResources().getString(R.string.if_marsCalc_equals_today_text));

        String marsStepCountStr = String.valueOf(marsStepCount);
        expListWholeSet.put(marsCalculus.getResLocalDate().toString() + "  \u2014>  "
                + marsStepCountStr
                + " Martian years!",
                marsCalculusList);

        //////////////////////////////////////////////////////////////////
        IMagicDate roundNDays = new RoundNDays(localDate, 1000);
        List<String> roundNDaysList = new ArrayList<>();
        int roundNDaysCount = 0;
        while (roundNDays.getResLocalDate().plusDays(100).isBefore(now)) {
            roundNDays.Step();
            roundNDaysCount++;
        }
        roundNDaysList.add(context.getResources().getString(R.string.if_roundNDays_equals_today_text));

        String roundNDaysCountStr = String.valueOf(roundNDaysCount * 1000);
        expListWholeSet.put(roundNDays.getResLocalDate().toString() + "  \u2014>  "
                + roundNDaysCountStr
                + " days to you! )",
                roundNDaysList);


        return expListWholeSet;
    }
}