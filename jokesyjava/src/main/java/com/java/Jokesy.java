package com.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jokesy {
    public static String getJoke(){

        List<String> jokesList = new ArrayList<String>();
        jokesList.add("This joke was too shy to present itself");
        jokesList.add("Let's not take this too seriously");
        jokesList.add("This is a never ending joke");
        jokesList.add("I didn't understand this joke");
        Random rand = new Random();
        return jokesList.get(rand.nextInt(jokesList.size()));
    }
}
