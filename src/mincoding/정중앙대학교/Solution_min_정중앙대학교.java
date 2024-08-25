package mincoding.정중앙대학교;

import java.io.*;
import java.util.*;

public class Solution_min_정중앙대학교 {

    private PriorityQueue<Integer> lowers = new PriorityQueue<>(Collections.reverseOrder());
    private PriorityQueue<Integer> uppers = new PriorityQueue<>();

    public void init(int score) {
        lowers.clear();
        uppers.clear();
        lowers.add(score);
    }

    public void enroll(int scoreA, int scoreB) {
        int lowScore = Math.min(scoreA, scoreB);
        int highScore = Math.max(scoreA, scoreB);
        if (lowers.peek() > highScore) {
            lowers.add(lowScore);
            lowers.add(highScore);
            uppers.add(lowers.poll());
        } else if (!uppers.isEmpty() && uppers.peek() < lowScore) {
            uppers.add(highScore);
            uppers.add(lowScore);
            lowers.add(uppers.poll());
        } else {
            lowers.add(lowScore);
            uppers.add(highScore);
        }
    }

    public int get() {
        return lowers.peek();
    }
}
