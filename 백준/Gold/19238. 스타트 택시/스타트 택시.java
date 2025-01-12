import java.io.*;
import java.util.*;

/**
 * 스마트 택시는 연료가 바닥나면 그날의 업무는 중단
 * 오늘 M명의 손님을 태우는 것이 목표
 * 활동 영역: NxN
 * 칸은 비어있거나 벽이 놓여있다.
 * 택시의 위치에서 가장 가까운 손님을 태운다.
 * 그 승객이 여러명이면 행 번호가 작은 손님을, 그런 승객도 여러명이면 열 번호가 가장 작은 승객을 태운다.
 * 연료는 한 칸 이동할 때마다 1만큼 소모
 * 한 승객을 목적지로 이동시키는 데 성공하면, 이동하면서 소모한 연료 양의 두 배가 충전된다.
 * 연료가 도중에 바닥나면 이동에 실패하고, 그 날의 업무가 끝난다.
 * 승객을 목적지로 이동시킨 동시에 연료가 바닥나는 경우 실패한 것으로 간주하지 않는다.
 * 모두 이동시키고 남은 연료의 양 출력. 이동할 수 없으면 -1
 */
public class Main {
    static int N, M, gauge;
    static int[][] map;

    static class Passenger {
        int startR, startC, endR, endC;
        boolean completed;

        public Passenger(int startR, int startC, int endR, int endC) {
            this.startR = startR;
            this.startC = startC;
            this.endR = endR;
            this.endC = endC;
            completed = false;
        }

        public int getDistToEndPoint() {
            ArrayDeque<int[]> q = new ArrayDeque<>();
            boolean[][] visited = new boolean[N + 1][N + 1];
            visited[startR][startC] = true;
            q.offer(new int[]{startR, startC, 0});
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                int nDist = cur[2] + 1;
                for (int d = 0; d < 4; d++) {
                    int nr = cur[0] + dr[d];
                    int nc = cur[1] + dc[d];
                    if (nr == endR && nc == endC) return nDist;
                    if (nr < 1 || nr > N || nc < 1 || nc > N || map[nr][nc] == 1 || visited[nr][nc]) continue;
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr, nc, nDist});
                }
            }
            // 애초에 연료가 무한이더라도 목적지에 도달할 수 없을 경우 -1 반환
            return -1;
        }

        @Override
        public String toString() {
            return "Passenger{" +
                    "startR=" + startR +
                    ", startC=" + startC +
                    ", endR=" + endR +
                    ", endC=" + endC +
                    ", completed=" + completed +
                    '}';
        }
    }

    static class PassengerInfo {
        int pId, dist;

        public PassengerInfo(int pId, int dist) {
            this.pId = pId;
            this.dist = dist;
        }

        @Override
        public String toString() {
            return "PassengerInfo{" +
                    "pId=" + pId +
                    ", dist=" + dist +
                    '}';
        }
    }

    static class Node implements Comparable<Node> {
        int r, c, dist;

        public Node(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return dist == o.dist ? r == o.r ? Integer.compare(c, o.c) : Integer.compare(r, o.r) : Integer.compare(dist, o.dist);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "r=" + r +
                    ", c=" + c +
                    ", dist=" + dist +
                    '}';
        }
    }

    static Passenger[] passengers;
    static int[] dr = new int[] {-1, 0, 0, 1}, dc = new int[]{0, -1, 1, 0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        gauge = Integer.parseInt(st.nextToken());

        map = new int[N + 1][N + 1];
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 1; j < N + 1; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        passengers = new Passenger[M + 1];

        st = new StringTokenizer(br.readLine(), " ");
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        for (int i = 1; i < M + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int startR = Integer.parseInt(st.nextToken());
            int startC = Integer.parseInt(st.nextToken());
            map[startR][startC] = -i;
            int endR = Integer.parseInt(st.nextToken());
            int endC = Integer.parseInt(st.nextToken());
            passengers[i] = new Passenger(startR, startC, endR, endC);
        }

        int answer = -1;
        int remainPassengers = M;

        while (true) {
//            System.out.println("gauge = " + gauge);
//            printState(r, c);
            PassengerInfo closestP = getClosestPassenger(r, c);
//            System.out.println("closestP = " + closestP);
            if (closestP == null || closestP.dist > gauge) break;
            // 손님의 출발지까지 도달하지 못할 경우 종료
            Passenger target = passengers[closestP.pId];
//            System.out.println("target = " + target);
            gauge -= closestP.dist;
//            System.out.println("gauge = " + gauge);

            int distToEndPoint = target.getDistToEndPoint();
//            System.out.println("distToEndPoint = " + distToEndPoint);
            if (distToEndPoint == -1 || distToEndPoint > gauge) break;
            r = target.endR;
            c = target.endC;
            gauge += distToEndPoint;
            target.completed = true;
//            System.out.println("gauge = " + gauge);
//            printState(r, c);
            if (--remainPassengers == 0) {
                answer = gauge;
                break;
            }
/*
            System.out.println("remainPassengers = " + remainPassengers);
            System.out.println();
*/
        }

        System.out.println(answer);
        br.close();
    }

    static PassengerInfo getClosestPassenger(int r, int c) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[][] visited = new boolean[N + 1][N + 1];
        visited[r][c] = true;
        pq.offer(new Node(r, c, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
//            System.out.println("cur = " + cur);
            if (map[cur.r][cur.c] < 0 && !passengers[-map[cur.r][cur.c]].completed) {
                return new PassengerInfo(-map[cur.r][cur.c], cur.dist);
            }

            int nDist = cur.dist + 1;
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 1 || nr > N || nc < 1 || nc > N || map[nr][nc] == 1 || visited[nr][nc]) continue;
                visited[nr][nc] = true;
                pq.offer(new Node(nr, nc, nDist));
            }
        }
        return null;
    }

    static void printState(int r, int c) {
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                if (i == r && j == c) {
                    System.out.print("[" + map[i][j] + "]");
                } else {
                    System.out.print(" " + map[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

}