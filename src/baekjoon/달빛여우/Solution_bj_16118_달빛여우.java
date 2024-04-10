package baekjoon.달빛여우;

import java.io.*;
import java.util.*;

/**
 * 관악산
 * - 1~N 번호가 붙은 나무 그루터기
 * - 그루터기들 사이: M개의 오솔길
 *  - 두 그루터기 사이에 두 개 이상의 오솔길 X
 * - 여우와 늑대는 1번 그루터기에서 살고 있다.
 * - 늑대: 오솔길 하나: 여우의 두 배, 그 후에는 여우의 절반의 속도
 * 해결 프로세스
 * dist[]를 여우와 늑대 각각 할당
 * 여우는 단순 다익스트라
 * 늑대는 2차원 다익스트라, 소수점 이하를 버리라는 조건이 없었으므로 double형 사용
 */
public class Solution_bj_16118_달빛여우 {

    static class Node implements Comparable<Node> {
        int foxOrWolf; // fox면 0, wolf면 1
        int vertex; // 그루터기 번호
        int time; // 그루터기까지 오는데 걸린 시간
        int speed;

        Node(int foxOrWolf, int vertex, int time) {
            this.foxOrWolf = foxOrWolf;
            this.vertex = vertex;
            this.time = time;
        }

        Node(int foxOrWolf, int vertex, int time, int speed) {
            this.foxOrWolf = foxOrWolf;
            this.vertex = vertex;
            this.time = time;
            this.speed = speed;
        }

        @Override
        public int compareTo(Node o) {
            return (int) (this.time - o.time);
        }
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        List<int[]>[] edges = new List[N + 1];
        int[] distFox = new int[N + 1];
        int[][] distWolf = new int[N + 1][2];
        int INF = 987654321;
        for (int i = 1; i < N + 1; i++) {
            edges[i] = new ArrayList<>();
            distFox[i] = INF;
            distWolf[i][0] = INF;
            distWolf[i][1] = INF;
        }
        distFox[1] = 0;
        distWolf[1][0] = 0;
//        distWolf[1][1] = 0.0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken()) * 2; // 2를 해서 나눠도 소수가 나오지 않게 한다.
            edges[a].add(new int[]{b, d});
            edges[b].add(new int[]{a, d});
        }
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(0, 1, 0));
        pq.offer(new Node(1, 1, 0, 0)); // 늑대는 처음에는 빠르게 달려야하므로, 처음 출발은 느리게 도착한 것으로 설정한다.
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int minVertex = cur.vertex;
            int min = cur.time;
            if (cur.foxOrWolf == 0) {
                // 여우라면
                if (distFox[minVertex] < min) continue;
                for (int[] edge : edges[minVertex]) {
                    int nextVertex = edge[0];
                    int nextD = edge[1];
                    if (distFox[nextVertex] > min + nextD) {
                        distFox[nextVertex] = min + nextD;
                        pq.offer(new Node(0, nextVertex, distFox[nextVertex]));
                    }
                }
            } else {
                // 늑대라면
                int speed = cur.speed;
                if (distWolf[minVertex][speed] < min) continue;
                int nextSpeed = 1 ^ speed; // 0이면 1로, 1이면 0으로
                if (nextSpeed == 1) {
                    // 빠르게 갈 차례
                    for (int[] edge : edges[minVertex]) {
                        int nextVertex = edge[0];
                        int nextD = edge[1];
                        if (distWolf[nextVertex][nextSpeed] > min + nextD / 2) {
                            distWolf[nextVertex][nextSpeed] = min + nextD / 2;
                            pq.offer(new Node(1, nextVertex, distWolf[nextVertex][nextSpeed], nextSpeed));
                        }
                    }
                } else {
                    // 느리게 갈 차례
                    for (int[] edge : edges[minVertex]) {
                        int nextVertex = edge[0];
                        int nextD = edge[1];
                        if (distWolf[nextVertex][nextSpeed] > min + nextD * 2) {
                            distWolf[nextVertex][nextSpeed] = min + nextD * 2;
                            pq.offer(new Node(1, nextVertex, distWolf[nextVertex][nextSpeed], nextSpeed));
                        }
                    }
                }
            }
        }
//        System.out.println(Arrays.toString(distFox));
//        for (int i = 1; i < N + 1; i++) {
//            System.out.println(Arrays.toString(distWolf[i]));
//        }
        int answer = 0;
        for (int i = 2; i < N + 1; i++) {
            double wolf = Math.min(distWolf[i][0], distWolf[i][1]);
            if (distFox[i] < wolf) answer++;
        }
        System.out.println(answer);
        br.close();
    }
}
