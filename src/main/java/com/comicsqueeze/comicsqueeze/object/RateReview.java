package com.comicsqueeze.comicsqueeze.object;

public class RateReview {

    private String rater;
    private String seriesTitle;
    private String seriesCreator;
    private double rating;
    private String review;

    public String getRater() {
        return rater;
    }

    public void setRater(String rater) {
        this.rater = rater;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
