package com.comicsqueeze.comicsqueeze;

import com.comicsqueeze.comicsqueeze.object.Page;
import com.comicsqueeze.comicsqueeze.service.ComicIssueService;
import com.comicsqueeze.comicsqueeze.service.WeeklyContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.TimeZone;

@Component
public class ScheduledTask {
    @Autowired
    private WeeklyContributionService weeklyContributionService;
    @Autowired
    private ComicIssueService issueService;
    @Scheduled(cron = "0 0 16 * * *")
    public void calculateAndReset(){
        String thisWeekIssue = issueService.queryForWeeklyIssue();
        Calendar cal = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("EST");
        cal.setTimeZone(tz);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        //Voting stops at 12 A.M EST
        int hours= cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        System.out.println(minutes);
        System.out.println(hours);
        // it is 12 A.M s

        if ((hours==15)&& (minutes==0)){
            // calculates max votes of day then sets the page with max votes to published true in weekly pages (meaning it is published in the issue for this weekly comic)
            Page maxVotes = weeklyContributionService.calculateBestPage(thisWeekIssue,dayOfWeek);
            if(maxVotes!=null) {
                weeklyContributionService.addMaxVotesToSeries(maxVotes);
            }
            // reset everyones voted boolean its the end of the day
            weeklyContributionService.setResetAllVoted();
        }
    }
}
