package com.comicsqueeze.comicsqueeze.controller.API;

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

@RestController
public class reportSeriesController {

    @Autowired
    private ComicSeriesService seriesService;
    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/reportSeries", method = RequestMethod.GET)
    public Series reportSeries(Model model, @RequestParam(value = "reportBody") String reportBody,
                               @RequestParam(value = "reportSeriesTitle") String seriesTitle, @RequestParam(value = "reportSeriesUser") String username, @RequestParam(value = "type")
                               String type){
        System.out.println("Got to report controller");
        Series seriesToReport = seriesService.findSeriesByTitle(username,seriesTitle);
        notificationService.storeNotification(username, seriesTitle, reportBody,type);

        return seriesToReport;
    }



}
