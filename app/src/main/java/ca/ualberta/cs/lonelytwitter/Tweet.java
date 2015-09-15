package ca.ualberta.cs.lonelytwitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sboulet on 9/15/15.
 */

public abstract class Tweet extends Object implements Tweetable{

    private String text;
    private Date date;
    private ArrayList<Mood> moodList;

    public Tweet(String text) {
        this.text = text;
        this.date = new Date();
    }

    public Tweet(String tweet, Date date) {
        text = tweet;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text.length() <= 140) {
            this.text = text;
        } else {
            throw new IllegalArgumentException("Tweets can't be that long, Shakespeare!");
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public abstract Boolean isImportant();
}
