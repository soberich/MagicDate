package soberich.magicdate7.controller;

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

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(Context context, LocalDate localDate) {
        HashMap<String, List<String>> expListWholeSet = new HashMap<String, List<String>>();
        //////////////////////////////////////////////////////////////
        Magic19 magic19 = new Magic19(localDate);
        LocalDate now = LocalDate.now();
        List<String> magic19List = new ArrayList<>();
        int magic19StepСount = 0;
        String prefix = new String("");
        while (magic19.getResLocalDate().plusDays(1000).isBefore(now)) {
            magic19.Step();
            magic19StepСount++;
            //if statement checks preffered level of concurrency in LocalDates
            if (magic19.getResLocalDate().getDayOfMonth() == localDate.getDayOfMonth()
                    && magic19.getResLocalDate().getMonthOfYear() == localDate.getMonthOfYear()){
                prefix = "Exactely at ";
            } else {
                prefix = "Just " +
                        String.valueOf(Math.abs(Days.daysBetween(magic19.getResLocalDate(),
                                localDate.plusYears(21 * magic19StepСount)).getDays()))
                        + " days away from ";
            }
        }
        String magic19StepCountStr = String.valueOf(19 * magic19StepСount);
        magic19List.add(context.getResources().getString(R.string.if_magic19_equals_today_text) + "Marvelous! " + prefix + "YOUR BIRTHDAY you will be also as old as "
        + magic19StepCountStr + " years " + magic19StepCountStr + " months " + magic19StepCountStr + " weeks " + magic19StepCountStr + " days !!!! rare, Ha?");

        expListWholeSet.put(magic19.getResLocalDate().toString() + "  \u2014  "
                + magic19StepCountStr + " years\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
                + magic19StepCountStr + " months\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
                + magic19StepCountStr + " weeks\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
                + magic19StepCountStr + " days =)", magic19List);

        ////////////////////////////////////////////////////////////////
        MarsCalculus marsCalculus = new MarsCalculus(localDate);
        List<String> marsCalculusList = new ArrayList<>();

        int marsStepCount = 0;
        while (marsCalculus.getResLocalDate().plusDays(150).isBefore(now)) {

            marsCalculus.Step();
            marsStepCount++;
        }
        marsCalculusList.add(context.getResources().getString(R.string.if_marsCalc_equals_today_text));

        String marsStepCountStr = String.valueOf(marsStepCount);
        expListWholeSet.put(marsCalculus.getResLocalDate().toString() + "  \u2014  "
                + marsStepCountStr
                + " Martian years!",
                marsCalculusList);

        //////////////////////////////////////////////////////////////////
        RoundNDays roundNDays = new RoundNDays(localDate, 1000);
        List<String> roundNDaysList = new ArrayList<>();
        int roundNDaysCount = 0;
        while (roundNDays.getResLocalDate().plusDays(100).isBefore(now)) {
            roundNDays.Step();
            roundNDaysCount++;
        }
        roundNDaysList.add(context.getResources().getString(R.string.if_roundNDays_equals_today_text));

        String roundNDaysCountStr = String.valueOf(roundNDaysCount * 1000);
        expListWholeSet.put(roundNDays.getResLocalDate().toString() + "  \u2014  "
                + roundNDaysCountStr
                + " days to you! )",
                roundNDaysList);


        return expListWholeSet;
    }
}