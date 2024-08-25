package mincoding.액체괴물놀이;

import java.io.*;
import java.util.*;

public class Solution_min_액체괴물놀이 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            pq.offer(Integer.parseInt(st.nextToken()));
        }
        int result = 0;
        while (pq.size() != 1) {
            int a = pq.poll();
            int b = pq.poll();
            pq.offer(a + b);
            result += a + b;
        }
        System.out.println(result);
        br.close();
    }
}
