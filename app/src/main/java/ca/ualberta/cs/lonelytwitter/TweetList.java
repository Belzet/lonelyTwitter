package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;

/**
 * Created by sboulet on 9/29/15.
 */

//want to make observable
public class TweetList implements MyObservable, MyObserver {
    private Tweet mostRecentTweet;
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    //volatile tells anything that saves TweetList that the volatile attribute doesn't need to be saved
    //good because observers only matter at runtime
    private volatile ArrayList<MyObserver> observers = new ArrayList<MyObserver>();

    public void addObserver(MyObserver observer) {
        observers.add(observer);
    }

    private void notifyAllObservers() {
        //for each object
        for (MyObserver observer: observers) {
            observer.myNotify(this);
        }
    }

    public void myNotify(MyObservable observable) {
        notifyAllObservers();
    }

    public void add(Tweet tweet) {
        if (this.hasTweet(tweet)) {
            throw new IllegalArgumentException("you've already tweeted this!");
        }
        else {
            mostRecentTweet = tweet;
            tweets.add(tweet);
            tweet.addObserver(this);
            notifyAllObservers();
        }
    }

    public Tweet getMostRecentTweet() {
        return mostRecentTweet;
    }

    public Boolean hasTweet(Tweet tweet) {
        if (tweets.contains(tweet)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void removeTweet(Tweet tweet) {
        if (this.hasTweet(tweet)) {
            tweets.remove(tweet);
        }
        else {

        }
    }

    public int getCount() {
        return tweets.size();
    }

}
