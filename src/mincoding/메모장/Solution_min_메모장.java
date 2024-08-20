package mincoding.메모장;

import java.io.*;
import java.util.*;

public class Solution_min_메모장 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        ArrayList<Character> chars = new ArrayList<>(s.length());
        int cursor = 0;
        for (int i = 0; i < s.length(); i++) {
            char command = s.charAt(i);
            if (command >= 'A' && command <= 'Z') {
                if (cursor < chars.size())
                    chars.add(cursor++, command);
                else {
                    chars.add(command);
                    cursor++;
                }
            } else if (command == '>') {
                if (cursor < chars.size())
                    cursor++;
            } else if (command == '<') {
                if (cursor > 0)
                    cursor--;
            } else if (command == 'd') {
                if (cursor < chars.size())
                    chars.remove(cursor);
            } else if (command == 'b') {
                if (cursor > 0)
                    chars.remove(--cursor);
            } else if (command == 'e') {
                cursor = chars.size();
            } else if (command == 'h') {
                cursor = 0;
            }
        }

        printChars(chars);
        br.close();
    }

    static void printChars(ArrayList<Character> chars) {
        for (int i = 0; i < chars.size(); i++) {
            System.out.print(chars.get(i));
        }
        System.out.println();
    }
}
