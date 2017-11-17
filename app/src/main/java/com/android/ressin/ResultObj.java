package com.android.ressin;

/**
 * Created by prashanth kurella on 11/15/2017.
 */

public class ResultObj {

    String title;
    String link;
    String distance;

    public ResultObj(String title, String link, String location) {
        this.title = title;
        this.link = link;
        this.distance = location;
    }

    public ResultObj() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
