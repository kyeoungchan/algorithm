package mincoding.초콜릿선물;

import java.io.*;
import java.util.*;

public class Solution_min_초콜릿선물 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TreeSet<Character> ts = new TreeSet<>();
        String str = br.readLine();
        for (int i = 0; i < str.length(); i++)
            ts.add(str.charAt(i));
        StringBuilder sb = new StringBuilder();
        while (!ts.isEmpty()) {
            sb.append(ts.pollFirst());
        }
        System.out.println(sb.toString());
        br.close();
    }
}
