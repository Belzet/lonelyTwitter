package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by sboulet on 9/15/15.
 */
public class HappyMood extends Mood {

    public HappyMood() {
    }

    public HappyMood(Date date) {
        super(date);
    }

    public String formatMood() {
        return "happy";
    }
}
