package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by sboulet on 9/15/15.
 */

public class ImportantTweet extends Tweet {

    public ImportantTweet(String text) {
        super(text);
    }

    public ImportantTweet(String tweet, Date date) {
        super(tweet, date);
    }

    public Boolean isImportant() {
        return Boolean.TRUE;
    }

    @Override
    public String getText() {
        return "important: " + super.getText();
    }
}
