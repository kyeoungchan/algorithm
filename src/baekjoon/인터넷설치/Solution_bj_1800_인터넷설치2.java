package baekjoon.인터넷설치;

import java.io.*;
import java.util.*;

public class Solution_bj_1800_인터넷설치2 {

    static int N, P, K;
    static List<Computer>[] computers;

    static class Computer implements Comparable<Computer> {
        int number, cost;

        public Computer(int number, int cost) {
            this.number = number;
            this.cost = cost;
        }

        @Override
        public int compareTo(Computer o) {
            return Integer.compare(cost, o.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        computers = new List[N + 1];
        for (int i = 1; i < N + 1; i++) {
            computers[i] = new ArrayList<>();
        }

        int end = 0;
        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());
            computers[a].add(new Computer(b, price));
            computers[b].add(new Computer(a, price));
            end = Math.max(end, price);
        }

        int ans = binarySearch(end);
        System.out.println(ans);
        br.close();
    }

    /**
     * 다익스트라로 접근하는 것은 맞지만 K개 이상을 찍으면 그때부터 가격이 유동적이므로 단순히 다익스트라로 접근하면 안 된다.
     * 부담하는 가격을 먼저 정하고, 그 가격으로 N번의 컴퓨터까지 도달할 수 있는지를 봐야한다.
     * 도달할 수 있는지 확인할 수 있는 방법은, 그 가격(mid)보다 비싼 컴퓨터들을 K개 이하로 찍고 N번 컴퓨터로 갈 수 있는지를 보는 것이다.
     */
    static int binarySearch(int end) {
        int ans = -1;
        int start = 0;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (canPay(mid)) {
                ans = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return ans;
    }

    static boolean canPay(int mid) {
        int[] dist = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        PriorityQueue<Computer> pq = new PriorityQueue<>();
        pq.offer(new Computer(1, 0));
        dist[1] = 0;
        while (!pq.isEmpty()) {
            Computer cur = pq.poll();
            if (dist[cur.number] < cur.cost) continue;
            for (Computer next : computers[cur.number]) {
                int nextCost = next.cost > mid ? 1 : 0;
                if (dist[next.number] > cur.cost + nextCost) {
                    dist[next.number] = cur.cost + nextCost;
                    pq.offer(new Computer(next.number, dist[next.number]));
                }
            }
        }
        return dist[N] <= K;
    }
}
