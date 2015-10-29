package no.ahoi.builditbigger;

import java.util.concurrent.ThreadLocalRandom;

public class JokeProvider {
    final String[] mJokes = {
        "A husband and wife are trying to set up a new password for their computer. " +
            "The husband puts, \"Mypenis,\" and the wife falls on the ground laughing because on " +
            "the screen it says, \"Error. Not long enough.\"",
        "A teacher is teaching a class and she sees that Johnny isn't paying " +
            "attention, so she asks him, \"If there are three ducks sitting on a fence, and " +
            "you shoot one, how many are left?\" Johnny says, \"None.\" The teacher asks, " +
            "\"Why?\" Johnny says, \"Because the shot scared them all off.\" The teacher " +
            "says, \"No, two, but I like how you're thinking.\" Johnny asks the teacher, " +
            "\"If you see three women walking out of an ice cream parlor, one is licking her " +
            "ice cream, one is sucking her ice cream, and one is biting her ice cream, which " +
            "one is married?\" The teacher says, \"The one sucking her ice cream.\" Johnny " +
            "says, \"No, the one with the wedding ring, but I like how you're thinking!\"",
        "Teacher: \"Kids,what does the chicken give you?\"\n" +
            "Student: \"Meat!\"\n" +
            "Teacher: \"Very good! Now what does the pig give you?\"\n" +
            "Student: \"Bacon!\"\n" +
            "Teacher: \"Great! And what does the fat cow give you?\"\n" +
            "Student: \"Homework!\"",
        "A child asked his father, \"How were people born?\" So his father said, \"Adam and " +
            "Eve made babies, then their babies became adults and made babies, and so " +
            "on.\" The child then went to his mother, asked her the same question and " +
            "she told him, \"We were monkeys then we evolved to become like we are " +
            "now.\" The child ran back to his father and said, \"You lied to me!\" His " +
            "father replied, \"No, your mom was talking about her side of the family.\"",
        "A teacher is teaching a class and she sees that Johnny isn't paying attention, so " +
            "she asks him, \"If there are three ducks sitting on a fence, and you shoot " +
            "one, how many are left?\" Johnny says, \"None.\" The teacher asks, \"Why?\" " +
            "Johnny says, \"Because the shot scared them all off.\" The teacher says, \"" +
            "No, two, but I like how you're thinking.\" Johnny asks the teacher, \"If " +
            "you see three women walking out of an ice cream parlor, one is licking her " +
            "ice cream, one is sucking her ice cream, and one is biting her ice cream, " +
            "which one is married?\" The teacher says, \"The one sucking her ice cream.\" " +
            "Johnny says, \"No, the one with the wedding ring, but I like how you're thinking!\""
    };
    final String mSource = "http://www.laughfactory.com/jokes";

    public String getJoke() {
        return mJokes[0] + "\n\nSource: " + mSource;
    }

    public String getRandomJoke() {
        return mJokes[ThreadLocalRandom.current().nextInt(0, mJokes.length)] +
                "\n\nSource: " + mSource;
    }

}
