package swexpertacademy.원자소멸시뮬레이션;

import java.io.*;
import java.util.*;

public class Solution_d9_5648_원자소멸시뮬레이션_다른사람코드 {
    static final double INF = Double.MAX_VALUE;
    static final int[][] MOV = new int[][] {
            {0,1}, {0,-1}, {-1,0}, {1,0}
    };

    static int N;
    static Point[] points = new Point[1000];

    public static void main(String[] args) throws Exception {
        for (int i=0; i<1000; i++) {
            points[i] = new Point();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int T = Integer.parseInt(br.readLine());
        StringBuilder output = new StringBuilder();

        for (int t=1; t<=T; t++) {
            input(br);
            int answer = 0;

            Queue<Event> eventQ = calcEvents();
            while (!eventQ.isEmpty()) {
                Event e = eventQ.poll();
                // 이미 제거된 원자를 포함한 Event라면 continue
                if (e.removed()) continue;

                List<Event> todo = new ArrayList<>();
                todo.add(e);
                while (!eventQ.isEmpty() && eventQ.peek().required == e.required) {
                    // 다음 PQ에 있는 이벤트들도 모두 동시에 일어나는 이벤트들인지 확인해보고 그렇다면 한 번에 뽑아서 리스트에 넣는다.
                    Event n = eventQ.poll();
                    if (!n.removed()) {
                        todo.add(n);
                    }
                }

                for (Event event : todo) {
                    answer += event.score();
                    event.remove();
                }
            }
            output.append(String.format("#%d %d\n", t, answer));
        }

        System.out.print(output);
    }

    static PriorityQueue<Event> calcEvents() {
        PriorityQueue<Event> res = new PriorityQueue<>();

        for (int i=0; i<N-1; i++) {
            for (int j=i+1; j<N; j++) {
                // 두 원자를 뽑는다.
                double required = calcTime(points[i], points[j]);
                if (required != INF) {
                    // 만나는 경우라면 PQ에 넣는다.
                    res.add(new Event(points[i], points[j], required));
                }
            }
        }

        return res;
    }

    static double calcTime(Point p1, Point p2) {
        // 같은 방향을 간다면 두 원자는 만날 리가 없다.
        if (p1.d == p2.d) return INF;

        double xDiff = Math.abs(p1.x - p2.x);
        double yDiff = Math.abs(p1.y - p2.y);

        if (xDiff == 0 || yDiff == 0) {
            // 같은 행이나 같은 열에 위치할 경우
            double x1 = p1.x + MOV[p1.d][0] * xDiff / 2;
            double y1 = p1.y + MOV[p1.d][1] * yDiff / 2;

            double x2 = p2.x + MOV[p2.d][0] * xDiff / 2;
            double y2 = p2.y + MOV[p2.d][1] * yDiff / 2;

            // 두 점의 거리의 반씩 움직였을 때 만났다면
            // 가로가 같은 상태라면 y 좌표 차이의 절반만큼의 시간, 세로가 같은 상태라면 x 좌표 차이의 절반만큼의 시간이 걸린다.
            if (x1 == x2 && y1 == y2) return (xDiff == 0)? yDiff/2 : xDiff/2;
            else return INF;
        }

        // 남은 가능성은 직각으로 만나는지 여부인데, x좌표의 차이와 y좌표의 차이가 같지 않다면 만날 리가 없다.
        if (xDiff != yDiff) return INF;

        double x1 = p1.x + MOV[p1.d][0] * xDiff;
        double y1 = p1.y + MOV[p1.d][1] * yDiff;
        double x2 = p2.x + MOV[p2.d][0] * xDiff;
        double y2 = p2.y + MOV[p2.d][1] * yDiff;

        // 직각으로 만나는 경우
        if (x1 == x2 && y1 == y2) return xDiff;

        // 방향이 엇갈려서 직각으로 만나지 않게 된 경우
        return INF;
    }

    static void input(BufferedReader br) throws IOException {
        N = Integer.parseInt(br.readLine());
        for (int i=0; i<N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            points[i].x = Integer.parseInt(st.nextToken());
            points[i].y = Integer.parseInt(st.nextToken());
            points[i].d = Integer.parseInt(st.nextToken());
            points[i].k = Integer.parseInt(st.nextToken());
            points[i].removed = false;
        }
    }

    static class Event implements Comparable<Event> {
        double required;
        Point p1, p2;

        Event(Point p1, Point p2, double r) {
            this.required = r;
            this.p1 = p1;
            this.p2 = p2;
        }

        int score() {
            // 하나의 시퀀스에서 원자들 중 제거된 애들이 있을 수도 있으므로 제거되지 않은 애들만 에너지를 출력한다.
            int res = 0;
            if (!p1.removed) res += p1.k;
            if (!p2.removed) res += p2.k;
            return res;
        }

        void remove() {
            // 해당 원자들을 끝냈음을 나타낸다.
            p1.removed = true;
            p2.removed = true;
        }

        boolean removed() {
            return p1.removed || p2.removed;
        }

        // PQ에 사용하기 위한 compareTo로, 충돌하는 데 걸리는 시간이 적은 원자들부터 나온다.
        @Override
        public int compareTo(Event o) {
            return Double.compare(required, o.required);
        }
    }

    static class Point {
        int x, y, d;
        int k;
        boolean removed = false;
    }
}
