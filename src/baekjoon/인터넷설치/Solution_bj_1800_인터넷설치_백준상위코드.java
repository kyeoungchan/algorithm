package baekjoon.인터넷설치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 출처: 제출번호 30218792
public class Solution_bj_1800_인터넷설치_백준상위코드 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static class Node implements Comparable<Node> {
        int n, weight;
        Node next;

        public Node(int n, int weight, Node next) {
            this.n = n;
            this.weight = weight;
            this.next = next;
        }

        public Node(int n, int weight) {
            this.n = n;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    static int INF = 1000000;

    static Node[] list;
    static int[] dist;
    static int N, P, K;

    public static void main(String[] args) throws IOException {
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        P = Integer.parseInt(str[1]);
        K = Integer.parseInt(str[2]);

        list = new Node[N+1];
        dist = new int[N+1];

        for (int i=0; i<P; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            list[from] = new Node(to, weight, list[from]);
            list[to] = new Node(from, weight, list[to]);
        }

        System.out.println(BinarySearch());
    }

    static int BinarySearch() {
        int left = 0, right = INF, answer = -1;

        while (left <= right) {
            int mid = (left + right) >> 1;

            if (dijkstra(mid)) {
                right = mid - 1;
                answer = mid;
            }
            else {
                left = mid + 1;
            }
        }
        return answer;
    }

    static boolean dijkstra(int mid) {
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.offer(new Node(1, 0));
        dist[1] = 0;

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            for (Node next=list[node.n]; next != null; next = next.next) {
                int temp = node.weight;
                if (mid < next.weight) temp++;
                // mid 가격보다 비싼 개수를 메모

                if (dist[next.n] > temp) {
                    dist[next.n] = temp;
                    queue.offer(new Node(next.n, temp));
                }
            }
        }
        // 만약 mid 가격보다 비싼 랜선들이 K개보다 초과라면, K개만큼 공짜를 받고도 mid 가격으로 지불할 수 없게 된다.
        return dist[N] <= K;
    }
}

