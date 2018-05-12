package com.android.chrishsu.who_r_theyquiz;

import android.widget.CheckBox;

/**
 * Created by chrishsu on 4/1/18.
 */

public class Questions {

    private String mQuestions[] = {
            "Who is this person?",
            "Do you know who is this?",
            "What's Bill's last name?",
            "Who is \"it\"? (Check all apply)",
            "Who is he?",
            "Guess who?",
            "Who is this?",
            "Who is he? (Check all apply)",
            "What app did he create?",
            "Who is this gentleman?"

    };

    private String mChoice[][] = {
            {"Rajeev Suri", "Sundar Pichai", "Satya Nadella"},
            {"Steve Wozniak", "Steve Jobs", "Steve Ballmer"},
            {""},
            {"Android", "MobileOS", "Robot"},
            {"John Hancock","John Lennon","John L. Flannery"},
            {"Jeff Dunham","Jeff Bezos","Jeff Perry"},
            {"Mark Zuckerberg", "Mark Parker", "Mark Twain"},
            {"Tesla CEO","Xspace CEO", "XYZ CEO"},
            {"WeChat", "Whatsapp", "SnapChat"},
            {"Larry Page", "Larry Bird", "Larry King"}

    };

    private String mImages[] = {
            "p1",
            "p2",
            "p3",
            "p4",
            "p5",
            "p6",
            "p7",
            "p8",
            "p9",
            "p10"

    };

    private String mQuestionType[] = {
            "radiobutton",
            "radiobutton",
            "edittext",
            "checkbox",
            "radiobutton",
            "radiobutton",
            "radiobutton",
            "checkbox",
            "radiobutton",
            "radiobutton"

    };

    private int mMaxAnswer[] = {
            1,
            1,
            1,
            3,
            1,
            1,
            1,
            2,
            1,
            1

    };

    private String mCorrectAnswer[] = {
            "Sundar Pichai",
            "Steve Jobs",
            "Gates",
            "Android,MobileOS,Robot",
            "John L. Flannery",
            "Jeff Bezos",
            "Mark Parker",
            "Tesla CEO,Xspace CEO",
            "SnapChat",
            "Larry Page"

    };

    public String getQuestion(int q) {
        String question = mQuestions[q];
        return question;
    }

    public String[] getChoice(int q) {
        String[] choice = mChoice[q];
        return choice;
    }

    public String getImage(int q) {
        String img = mImages[q];
        return img;
    }

    public String getType(int q) {
        String type = mQuestionType[q];
        return type;
    }

    public int getMaxAnswer(int q) {
        int maxanswer = mMaxAnswer[q];
        return maxanswer;
    }
    public int getLength() {
        return mQuestions.length;
    }

    public String getCorrectAnswer(int q) {
        String correctans = mCorrectAnswer[q];
        return correctans;
    }


}
