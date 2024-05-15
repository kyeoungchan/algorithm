import java.io.*;
import java.util.*;

/**
 * 학생들의 번호: 1~N => 아무나 서로 인터넷 선이 연결되어있지 않다.
 * 각 선의 비용은 서로 다르다.
 * 나머지 컴퓨터는 연결되거나 안 돼도 된다.
 * 나머지 인터넷 선에 대해서는 남은 것 중 제일 가격이 비싼 것에 대해서만 가격을 받는다.
 */
public class Main {

    static class Node implements Comparable<Node> {
        int vertex;
        int cost;
        Node next;

        public Node(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }

        public Node(int vertex, int cost, Node next) {
            this.vertex = vertex;
            this.cost = cost;
            this.next = next;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(cost, o.cost);
        }
    }

    static int N, P, K, INF = 1_000_000;
    static Node[] infoes;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 목표 컴퓨터
        P = Integer.parseInt(st.nextToken()); // 케이블선의 개수
        K = Integer.parseInt(st.nextToken()); // 공짜로 제공하는 케이블선의 개수

        infoes = new Node[N + 1];

        // 다음 P개의 줄에는 케이블이 연결하는 두 컴퓨터 번호와 그 가격이 차례로 들어온다.
        // 가격은 1 이상 1,000,000 이하
        for (int i = 0; i < P; i++) {
            // P개의 쌍만 서로 연결할 수 있다.
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            infoes[a] = new Node(b, cost, infoes[a]);
            infoes[b] = new Node(a, cost, infoes[b]);
        }

        int answer = parametricSearch();


        System.out.println(answer);
        br.close();
    }

    static int parametricSearch() {
        // 가격은 1 이상 1,000,000 이하
        int start = 0;
        int end = INF;
        int answer = -1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (dijkstra(mid)) {
                // 조건에 만족하면 end를 계속 왼쪽으로 옮긴다.
                // end가 start보다 왼쪽으로 가야 끝나므로 정답을 메모
                end = mid - 1;
                answer = mid;
            } else {
                start = mid + 1;
            }
        }
        // 연결할 수 없는 경우라면 start가 계속 오른쪽으로 옮겨졌으므로 answer는 그대로 -1
        return answer;
    }

    static boolean dijkstra(int mid) {
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(1, 0));
        dist[1] = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (dist[cur.vertex] < cur.cost) continue;

            for (Node next = infoes[cur.vertex]; next != null; next = next.next) {
                int curCnt = cur.cost;
                if (next.cost > mid) curCnt++;
                if (dist[next.vertex] > curCnt) {
                    dist[next.vertex] = curCnt;
                    pq.offer(new Node(next.vertex, curCnt));
                }
            }
        }
        return dist[N] <= K;
    }
}