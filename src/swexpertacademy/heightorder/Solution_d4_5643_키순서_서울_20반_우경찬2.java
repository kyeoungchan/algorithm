package swexpertacademy.heightorder;

import java.util.*;
import java.io.*;

public class Solution_d4_5643_키순서_서울_20반_우경찬2 {

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_5643.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            int M = Integer.parseInt(br.readLine());
            List<Integer>[] lowers = new List[N + 1];
            List<Integer>[] highers = new List[N + 1];
            for (int i = 1; i < N + 1; i++) {
                lowers[i] = new ArrayList<>();
                highers[i] = new ArrayList<>();
            }
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int l = Integer.parseInt(st.nextToken());
                int h = Integer.parseInt(st.nextToken());

                highers[l].add(h);
                lowers[h].add(l);
            }

            int ANS = 0;
            int[] counts = new int[N + 1];
            boolean[] counted = null;
            for (int i = 1; i < N + 1; i++) {
                counted = new boolean[N + 1];
                countLowers(i, i, counted, counts, lowers);
                counted = new boolean[N + 1];
                countHighers(i, i, counted, counts, highers);
                if (counts[i] == N - 1) {
                    ANS++;
                }
            }

            sb.append("#").append(tc).append(" ").append(ANS).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    private static void countHighers(int number, int searchNode, boolean[] counted, int[] counts, List<Integer>[] highers) {
        for (int hNum : highers[searchNode]) {
            if (counted[hNum]) continue;
            counted[hNum] = true;
            counts[number]++;
            countLowers(number, hNum, counted, counts, highers);
        }

    }

    private static void countLowers(int number, int searchNode, boolean[] counted, int[] counts, List<Integer>[] lowers) {
        for (int lNum : lowers[searchNode]) {
            if (counted[lNum]) continue;
            counted[lNum] = true;
            counts[number]++;
            countLowers(number, lNum, counted, counts, lowers);
        }
    }

}
