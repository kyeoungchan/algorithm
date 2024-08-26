package mincoding.발레공연;

import java.io.*;
import java.util.*;

/**
 * 모든 N명의 발레리나들을 한명씩 스테이지에서 자유롭게 자신을 부각시킬 수 있는 연출을 포함시키려고 한다.
 * 각 발레리나들에게 등장 순서를 주었고, 그녀들이 추고자 하는 안무에 따라 스테이지에 머물 수 있는 시간 d를 주었다.
 * 연극에서 해당 연출로 활용할 수 있는 시간은 T
 * 최소한의 인원 K명을 한 번에 스테이지에서 춤을 추도록 하려고 한다.
 * K를 최소화시켜 최대한 각각의 발레리나들이 주목을 받을 수 있도록 할 계획이다.
 * 1번부터 K번까지의 발레리나들이 먼저 춤을 추다가 한명이 끝나면 K+1번의 발레리나가 나와서 춤을 춘다.
 */
public class Solution_min_발레공연 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());
        int[] d= new int[N];
        for (int i = 0; i < N; i++) d[i] = Integer.parseInt(br.readLine());
        int left = 1;
        int right = N;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canPerform(mid, d, N, T)) right = mid - 1;
            else left = mid + 1;
        }
        System.out.println(left);
        br.close();
    }

    static class Ballerina implements Comparable<Ballerina> {
        int playTime, startTime;
        int factor;

        public Ballerina(int playTime, int startTime) {
            this.playTime = playTime;
            this.startTime = startTime;
            factor = playTime + startTime; // 공연시간이 길수록, 늦게 들어왔을수록 늦게까지 한다.
        }

        @Override
        public int compareTo(Ballerina o) {
            return Integer.compare(factor, o.factor);
        }
    }

    static boolean canPerform(int mid, int[] d, int N, int T) {
        PriorityQueue<Ballerina> pq = new PriorityQueue<>();
        int time = 0;
        for (int i = 0; i < mid; i++) pq.offer(new Ballerina(d[i], 0));
        int idx = mid;
        while (!pq.isEmpty()) {
            Ballerina min = pq.poll();
            time = min.factor;
            if (idx < N) pq.offer(new Ballerina(d[idx++], min.factor));
        }
        return time <= T;
    }
}
