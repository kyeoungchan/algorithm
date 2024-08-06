import java.io.*;
import java.util.*;

/**
 * 걷는 속도: 5m/s
 * 대포는 50m를 임의의 방향으로 날려준다.
 * 대포에 올라타고 발사되고 착륙하기까지는 정확히 2초가 걸린다.
 * 대포는 걸어가는 경로에 장애물이 되지 않는다.
 * 입력
 * 1. 당신이 위치한 X,Y 좌표(실수)
 * 2. 목적지의 X,Y 좌표(실수)
 * 3. 대포의 숫자 정수 n
 *
 * 한줄에 걸쳐 목적지에 다다르기 위해 가장 빠른 시간을 출력하라.
 * 실제 답과 0.001초 미만의 차이는 정답으로 인정한다.
 */
public class Main {

    static class Node implements Comparable<Node> {
        int number;
        double cost;

        public Node(int number, double cost) {
            this.number = number;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(this.cost, o.cost);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "number=" + number +
                    ", cost=" + cost +
                    '}';
        }
    }

    static class Cannon {
        double x, y;

        public Cannon(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        double startX = Double.parseDouble(st.nextToken());
        double startY = Double.parseDouble(st.nextToken());
        st = new StringTokenizer(br.readLine(), " ");
        double endX = Double.parseDouble(st.nextToken());
        double endY = Double.parseDouble(st.nextToken());
        int n = Integer.parseInt(br.readLine());

        double[][] graph = new double[n + 2][n + 2]; // 출발점과 도착점, 그리고 각 대포들간의 그래프
        // 0번: 출발점, n+1번: 도착점
        graph[0][n + 1] = graph[n + 1][0] = getDistance(startX, startY, endX, endY);

        double[] dist = new double[n + 2];
        Cannon[] nodes = new Cannon[n + 1];
        dist[n + 1] = Integer.MAX_VALUE;
        for (int i = 1; i < n + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            nodes[i] = new Cannon(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
            dist[i] = Integer.MAX_VALUE;
        }

        for (int i = 1; i < n + 1; i++) {
            graph[0][i] = graph[i][0] = getDistance(startX, startY, nodes[i].x, nodes[i].y);
            graph[n + 1][i] = graph[i][n + 1] = getDistance(endX, endY, nodes[i].x, nodes[i].y);
        }

        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j < n + 1; j++)
                graph[i][j] = graph[j][i] = getDistance(nodes[i].x, nodes[i].y, nodes[j].x, nodes[j].y);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(0, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

//            System.out.println("cur = " + cur);
            if (dist[cur.number] < cur.cost) continue;
            if (cur.number == n + 1) break;

            for (int i = 1; i < n + 2; i++) {
                if (cur.number == i) continue;
                double newCost = cur.cost + graph[cur.number][i] / 5;
                if (newCost < dist[i]) {
                    dist[i] = newCost;
                    pq.offer(new Node(i, newCost));
                }
                if (cur.number != 0) {
                    // 대포를 탄 경우
                    newCost = cur.cost + 2 + Math.abs(graph[cur.number][i] - 50) / 5;
                    if (newCost < dist[i]) {
                        dist[i] = newCost;
                        pq.offer(new Node(i, dist[i]));
                    }
                }
            }
        }
        System.out.println(dist[n + 1]);
        br.close();
    }

    static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}