package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by sboulet on 9/15/15.
 */
public class SadMood extends Mood {

    public SadMood() {
    }

    public SadMood(Date date) {
        super(date);
    }

    public String formatMood() {
        return "sad";
    }
}
