package com.comicsqueeze.comicsqueeze.controller.API;

import com.comicsqueeze.comicsqueeze.object.Member;
import com.comicsqueeze.comicsqueeze.object.Series;
import com.comicsqueeze.comicsqueeze.repository.SeriesRepo;
import com.comicsqueeze.comicsqueeze.service.ComicSeriesService;
import com.comicsqueeze.comicsqueeze.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class reportSeriesController {

    @Autowired
    private ComicSeriesService seriesService;
    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/reportSeries", method = RequestMethod.GET)
    public Series reportSeries(Model model, @RequestParam(value = "reportBody") String reportBody,
                               @RequestParam(value = "reportSeriesTitle") String seriesTitle, @RequestParam(value = "curUser") String username, @RequestParam(value = "type")
                               String type, @RequestParam(value = "link") String link, @RequestParam("usernameto") String usernameto, @RequestParam("read") Boolean read, @RequestParam("adminread") Boolean adminread){
        System.out.println("Got to report controller");
        Series seriesToReport = seriesService.findSeriesByTitle(username,seriesTitle);
        notificationService.storeNotification(username, reportBody, link,type,usernameto,read,adminread,seriesTitle);
        return seriesToReport;
    }

    @RequestMapping(value = "/notifyGroup", method = RequestMethod.GET)
    public void notifyGroup(Model model, @RequestParam(value = "group")String collabGroup,@RequestParam(value="seriesCreator") String seriesCreator,
                              @RequestParam(value = "seriesTitle") String seriesTitle){


        Member tempMember = new Member();
        tempMember.setUsername(seriesCreator);
        ArrayList<Series> newGroupSeries = seriesService.queryforGroupSeries(tempMember);
        List<String> splits = Arrays.asList(collabGroup.split(","));
        Boolean read = false;
        String link = "series/" + seriesCreator + "/" + seriesTitle;
        System.out.println("In notify group method");
        System.out.println(collabGroup);
        System.out.println(splits);
        for(int i =0; i<splits.size(); i++){
            //System.out.println("Split loop");
            //System.out.println(splits.get(i));
            notificationService.storeNotification(seriesCreator, "Notifying collaborators", link , "group",splits.get(i), read,false, seriesTitle);
        }




    }

}
