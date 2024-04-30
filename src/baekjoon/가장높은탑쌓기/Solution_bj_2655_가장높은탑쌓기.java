/*
package baekjoon.가장높은탑쌓기;

import java.io.*;
import java.util.*;

public class Solution_bj_2655_가장높은탑쌓기 {

    static class Brick implements Comparable<Brick> {
        int id;
        int bottom;
        int height;
        int weight;

        public Brick(int id, int bottom, int height, int weight) {
            this.id = id;
            this.bottom = bottom;
            this.height = height;
            this.weight = weight;
        }


        @Override
        public int compareTo(Brick o) {
            return Integer.compare(o.bottom, bottom);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(br.readLine());
        PriorityQueue<Brick> pq = new PriorityQueue<>();
        int max = 0;
        for (int id = 1; id < N + 1; id++) {
            st = new StringTokenizer(br.readLine(), " ");
            int b = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            pq.offer(new Brick(id, b, h, w));
            max += w;
        }

        int[] dp = new int[max + 1];

        for (int w = 0; w < max + 1; w++) {
            if (pq.isEmpty()) {
                break;
            }
            if (pq.peek().weight == w) {
                Brick b = pq.poll();
                dp[w] = Math.max(b.weight, )
            }
        }

        System.out.print(sb.toString());
        br.close();
    }

}
*/
