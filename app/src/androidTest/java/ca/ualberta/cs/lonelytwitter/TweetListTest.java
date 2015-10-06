package ca.ualberta.cs.lonelytwitter;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by sboulet on 9/29/15.
 */
public class TweetListTest extends ActivityInstrumentationTestCase2 implements MyObserver{

    public TweetListTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }
    private Boolean weWereNotNotified;

    public void myNotify(MyObservable observable) {
        weWereNotNotified = Boolean.TRUE;
    }


    public void testObservable() {
        TweetList list = new TweetList();
        //needs an add observer
        list.addObserver(this);
        Tweet tweet = new NormalTweet("test");
        weWereNotNotified = Boolean.FALSE;
        list.add(tweet);
        //we should have been notified here
        assertTrue(weWereNotNotified);
    }

    public void testModifyTweetList() {
        TweetList list = new TweetList();
        //needs an add observer
        list.addObserver(this);
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        weWereNotNotified = Boolean.FALSE;
        tweet.setText("different text");
        //we should have been notified here
        assertTrue(weWereNotNotified);
    }


    public void testHoldsStuff() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        assertSame(list.getMostRecentTweet(), tweet);
    }

    public void testHoldsManyThings() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        assertEquals(list.getCount(), 1);
        list.add(new NormalTweet("test2"));
        assertEquals(list.getCount(), 2);
    }

    public void testHasTweet() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        assertEquals(list.hasTweet(tweet), (Boolean) true);
    }

    //@Test(expected=IllegalArgumentException.class)
    public void testDuplicate() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        try {
            list.add(tweet);
            fail("Didn't cach duplicate.");
        } catch (IllegalArgumentException e) {

        }
    }

    public void testOrder() {
        TweetList list = new TweetList();
        Tweet tweet1 = new NormalTweet("test");
        list.add(tweet1);
        Date date1 = list.getMostRecentTweet().getDate();
        Tweet tweet2 = new NormalTweet("test2");
        list.add(tweet2);
        Date date2 = list.getMostRecentTweet().getDate();
        assert(date2.after(date1));
    }

    public void testRemoveTweet() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        list.removeTweet(tweet);
        assertEquals(list.hasTweet(tweet), (Boolean)false);
    }

    public void testGetCount() {
        TweetList list = new TweetList();
        Tweet tweet1 = new NormalTweet("test1");
        list.add(tweet1);
        assertSame(list.getCount(), 1);
        Tweet tweet2 = new NormalTweet("test2");
        list.add(tweet2);
        assertSame(list.getCount(), 2);
    }

}