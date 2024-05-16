package baekjoon.인터넷설치;

import java.io.*;
import java.util.*;

public class Solution_bj_1800_인터넷설치2 {

    static class Host implements Comparable<Host> {
        int number, cost;
        Host next;

        public Host(int number, int cost) {
            this.number = number;
            this.cost = cost;
        }

        public Host(int number, int cost, Host next) {
            this.number = number;
            this.cost = cost;
            this.next = next;
        }

        @Override
        public int compareTo(Host o) {
            return Integer.compare(cost, o.cost);
        }

        @Override
        public String toString() {
            return "Host{" +
                    "number=" + number +
                    ", cost=" + cost +
                    ", next=" + next +
                    '}';
        }
    }

    static int N, K;
    static Host[] hosts;

    public static void main(String[] args) throws Exception {
        // 학생들의 번호가 1부터 N까지 붙여져 있다
        // P(P<=10,000)개의 쌍만이 서로 이어 질수 있으며 서로 선을 연결하는데 가격이 다르다.
        // 1번은 다행히 인터넷 서버와 바로 연결되어 있어 인터넷이 가능
        // 우리의 목표는 N번 컴퓨터가 인터넷에 연결하는 것
        // K개의 인터넷 선에 대해서는 공짜로 연결
        // 나머지 인터넷 선에 대해서는 남은 것 중 제일 가격이 비싼 것에 대해서만 가격을 받기로 하였다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        hosts = new Host[N + 1];

        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            hosts[a] = new Host(b, cost, hosts[a]);
            hosts[b] = new Host(a, cost, hosts[b]);
        }

        int answer = binarySearch();
        System.out.println(answer);
        br.close();
    }

    static int binarySearch() {
        int answer = -1;
        int start = 0; // start를 1로 하면 틀리고 0으로 하면 정답이 된다. 이유를 공부하자!!!!
        // 가격은 1 이상 1,000,000 이하
        int end = 1_000_000;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (canConnectN(mid)) {
                answer = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return answer;
    }

    static boolean canConnectN(int mid) {
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;
        PriorityQueue<Host> pq = new PriorityQueue<>();
        pq.offer(new Host(1, 0));
        while (!pq.isEmpty()) {
            Host cur = pq.poll();
            if (dist[cur.number] < cur.cost) continue;
            for (Host next = hosts[cur.number]; next != null; next = next.next) {
                int cnt = cur.cost;
                if (next.cost > mid) cnt++;
                if (dist[next.number] > cnt) {
                    dist[next.number] = cnt;
                    pq.offer(new Host(next.number, dist[next.number]));
                }
            }
        }
        return dist[N] <= K;
    }
}
