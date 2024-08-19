package mincoding.영어시간;

import java.io.*;
import java.util.*;

public class Solution_min_영어시간 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int h = Integer.parseInt(br.readLine());
        int minutes = Integer.parseInt(br.readLine());
        if (minutes == 0) {
            System.out.println(getHour(h) + " o' clock");
        } else if (minutes <= 30) {
            if (minutes == 15) {
                System.out.println("quarter past " + getHour(h));
            } else if (minutes == 30) {
                System.out.println("half past " + getHour(h));
            } else if (minutes == 1) {
                System.out.println(getMinutes(minutes) + " minute past " + getHour(h));
            } else {
                System.out.println(getMinutes(minutes) + " minutes past " + getHour(h));
            }
        } else {
            h++;
            minutes = 60 - minutes;
            if (minutes == 15) {
                System.out.println("quarter to " + getHour(h));
            } else if (minutes == 1) {
                System.out.println(getMinutes(minutes) + " minute to " + getHour(h));
            } else {
                System.out.println(getMinutes(minutes) + " minutes to " + getHour(h));
            }
        }
        br.close();
    }

    static String getHour(int hour) {
        switch (hour) {
            case 1:
                return "one";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            case 6:
                return "six";
            case 7:
                return "seven";
            case 8:
                return "eight";
            case 9:
                return "nine";
            case 10:
                return "ten";
            case 11:
                return "eleven";
            case 12:
                return "twelve";
        }
        return "Error";
    }

    static String getMinutes(int minutes) {
        if (minutes < 13) {
            return getHour(minutes);
        }
        if (minutes / 10 == 1) {
            switch (minutes) {
                case 13:
                    return "thirteen";
                case 14:
                    return "fourteen";
                case 15:
                    return "fifteen";
                default:
                    return getHour(minutes % 10) + "teen";
            }
        } else if (minutes / 10 == 2) {
            return "twenty " + getHour(minutes % 10);
        }
        return "ERROR";
    }
}
