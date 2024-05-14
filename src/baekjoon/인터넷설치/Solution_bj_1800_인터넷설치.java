package baekjoon.인터넷설치;

import java.io.*;
import java.util.*;

/**
 * 학생들의 번호: 1~N => 아무나 서로 인터넷 선이 연결되어있지 않다.
 * 각 선의 비용은 서로 다르다.
 * 나머지 컴퓨터는 연결되거나 안 돼도 된다.
 * 나머지 인터넷 선에 대해서는 남은 것 중 제일 가격이 비싼 것에 대해서만 가격을 받는다.
 */
public class Solution_bj_1800_인터넷설치 {

    static class Node implements Comparable<Node> {
        int vertex;
        int finalCost;
        int cnt;
        PriorityQueue<Integer> freePq = new PriorityQueue<>(); // 공짜로 받을 비용들을 저장하는 PQ => 최소 힙
//        PriorityQueue<Integer> costPq = new PriorityQueue<>(Collections.reverseOrder()); // 위의 PQ를 통해 K개 이상 연결했을 경우 가장 가격이 낮은 초과된 비용들을 뽑아내고, 그 중에서 제일 비싼 비용이 최종 가격이다. => 최대힙

        public Node(int vertex) {
            this.vertex = vertex;
            this.finalCost = -1;
        }

        public void copy(Node node) {
            this.finalCost = node.finalCost;
            this.cnt = node.cnt;
            this.freePq.addAll(node.freePq);
//            this.costPq.addAll(node.costPq);
        }

        public void add(int cost) {
//            if (vertex == v) return; // 자신에서 자신으로 가는 경우는 제외
            cnt++;
            freePq.offer(cost);
            // K개의 인터넷 선에 대해서는 공짜로 연결해준다. => 1번은 제외하고 K개!
            if (cnt > K) {
                // 넣었을 때 K개 이상이라면 공짜로 할당된 비용에서는 최소 비용으로 뽑아낸다.
//                int minCost = freePq.poll();
//                costPq.offer(minCost);
                // 원장 선생님이 내게 되는 최소의 값을 구하라.
//                finalCost = costPq.peek();
                int idx = 0;
                for (Integer i : freePq) {
                    idx++;
                    if (idx == cnt - K) {
                        finalCost = i;
                    }
                }
            }
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(finalCost, o.finalCost);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "vertex=" + vertex +
                    ", finalCost=" + finalCost +
                    ", cnt=" + cnt +
                    ", freePq=" + freePq +
                    '}';
        }
    }

    static int N, P, K;
    static List<int[]>[] infoes;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 목표 컴퓨터
        P = Integer.parseInt(st.nextToken()); // 케이블선의 개수
        K = Integer.parseInt(st.nextToken()); // 공짜로 제공하는 케이블선의 개수

        int[] dist = new int[N + 1];
        infoes = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            infoes[i] = new ArrayList<>();
            dist[i] = Integer.MAX_VALUE;
        }
        // 다음 P개의 줄에는 케이블이 연결하는 두 컴퓨터 번호와 그 가격이 차례로 들어온다.
        // 가격은 1 이상 1,000,000 이하
        for (int i = 0; i < P; i++) {
            // P개의 쌍만 서로 연결할 수 있다.
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            infoes[a].add(new int[] {b, cost});
            infoes[b].add(new int[] {a, cost});
        }

        int answer = -1;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        // 1번: 인터넷 서버와 바로 연결되어 있어 인터넷이 가능하다.
        pq.offer(new Node(1));
        dist[1] = 0;
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.vertex == N) {
                // N번 컴퓨터가 인터넷에 연결하는 것이 목표!
                answer = cur.finalCost != 1 ? cur.finalCost : 0;
                break;
            }

            if (dist[cur.vertex] < cur.finalCost) continue;

            for (int[] info : infoes[cur.vertex]) {
//                int newCnt = cur.cnt + 1;
                int newV = info[0];
                int nextCost = info[1];
                Node newNode = new Node(newV);
                newNode.copy(cur);
                newNode.add(nextCost);
                int finalCost = cur.finalCost;
                if (finalCost != -1) {
                    // K개를 초과해서 연결했으므로 가격을 메모해야하는 경우
                    if (dist[newV] > finalCost)
                        dist[newV] = finalCost;
                    else
                        continue;
                }

                pq.offer(newNode);
            }
        }
        System.out.println(answer);
        br.close();
    }
}