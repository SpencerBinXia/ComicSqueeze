package com.comicsqueeze.comicsqueeze.object;

public class Subscription {

    private String subscriber;
    private String seriesTitle;
    private String seriesCreator;

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public String getSeriesCreator() {
        return seriesCreator;
    }

    public void setSeriesCreator(String seriesCreator) {
        this.seriesCreator = seriesCreator;
    }
}
