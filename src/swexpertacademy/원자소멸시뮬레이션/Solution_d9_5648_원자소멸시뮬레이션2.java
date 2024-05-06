package swexpertacademy.원자소멸시뮬레이션;

import java.util.*;
import java.io.*;

/**
 * 원자의 최초 위치는 2차원 평면상의 [x, y] 이다.
 * 두 개 이상의 원자가 동시에 충돌 할 경우 충돌한 원자들은 모두 보유한 에너지를 방출하고 소멸된다.
 */
public class Solution_d9_5648_원자소멸시뮬레이션2 {

    static class Atom {
        int x, y, d, k;
        boolean extinguished;

        public Atom(int x, int y, int d, int k) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.k = k;
            extinguished = false;
        }
    }

    static class Event implements Comparable<Event> {
        // 두 원자가 충돌할 수 있다면 Event 객체로 관리
        int atom1, atom2, time;

        public Event(int atom1, int atom2, int time) {
            this.atom1 = atom1;
            this.atom2 = atom2;
            this.time = time;
        }

        public boolean validate() {
            // 두 원자 모두가 소멸되지 않아야 이 이벤트는 유효하다
            return !atoms[atom1].extinguished && !atoms[atom2].extinguished;
        }

        public void extinguish() {
            // 동시간대에 소멸되는 원자가 발생할 수 있으므로 소멸시키기 전에 소멸된 원자인지 여부를 확인해주어야한다.
            if (!atoms[atom1].extinguished) {
                atoms[atom1].extinguished = true;
                totalEnergy += atoms[atom1].k;
            }
            if (!atoms[atom2].extinguished) {
                atoms[atom2].extinguished = true;
                totalEnergy += atoms[atom2].k;
            }
        }

        @Override
        public int compareTo(Event o) {
            return Integer.compare(time, o.time);
        }
    }

    static int N, totalEnergy;
    /*  - 상: y 가 증가하는 방향
     *  - 하: y 가 감소하는 방향
     *  - 좌: x 가 감소하는 방향
     *  - 우: x 가 증가하는 방향
     */
    // 원자들의 이동 방향은 상(0), 하(1), 좌(2), 우(3)로 주어진다.
    static int[] dx = {0, 0, -1, 1}, dy = {1, -1, 0, 0};
    static Atom[] atoms;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_5648.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            atoms = new Atom[N];
            for (int i = 0; i < N; i++) {
                // 다음 N개의 줄에는 원자들의 x 위치, y 위치, 이동 방향, 보유 에너지 K가 주어진다.
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()) * 2;
                int y = Integer.parseInt(st.nextToken()) * 2;
                int d = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());
                atoms[i] = new Atom(x, y, d, k);
            }

            totalEnergy = 0;
            simulate();
            sb.append("#").append(tc).append(" ").append(totalEnergy).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void simulate() {
        PriorityQueue<Event> pq = new PriorityQueue<>();
        // 두 원자 조합
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                addEvent(i, j, pq);
            }
        }
        int time = 0;
        List<Event> events = new ArrayList<>();
        while (!pq.isEmpty()) {
            time++;
            while (!pq.isEmpty() && pq.peek().time == time) {
                // 뽑는 동안 pq가 빌 수도 있으므로 조건 추가
                Event event = pq.poll();
                if (!event.validate()) continue;
                events.add(event);
            }

            for (Event event : events) {
                event.extinguish();
            }
        }
    }

    static void addEvent(int atom1Idx, int atom2Idx, PriorityQueue<Event> pq) {
        // 원자들의 최초 위치는 서로 중복되지 않는다.
        // 두 원자가 만날 수 있는 경우
        Atom atom1 = atoms[atom1Idx];
        Atom atom2 = atoms[atom2Idx];
        int xDiff = Math.abs(atom1.x - atom2.x);
        int yDiff = Math.abs(atom1.y - atom2.y);
        /*
        원자들의 이동 방향은 상(0), 하(1), 좌(2), 우(3)로 주어진다.
        static int[] dx = {0, 0, -1, 1}, dy = {1, -1, 0, 0};
        */
        // x좌표가 같다면, y좌표가 큰 애는 d=1, y좌표가 작은 애는 d=0
        if (xDiff == 0) {
            if (atom1.d == 2 || atom1.d == 3 || atom2.d == 2 || atom2.d == 3) return;
            if ((atom1.y > atom2.y && atom1.d == 1 && atom2.d == 0) || (atom1.y < atom2.y && atom1.d == 0 && atom2.d == 1)) {
                int time = yDiff / 2;
                pq.offer(new Event(atom1Idx, atom2Idx, time));
            }
            return;
        }
        // y좌표가 같다면, x좌표가 작은 애는 d=3, x좌표가 큰 애는 d=2
        if (yDiff == 0) {
            if (atom1.d == 0 || atom1.d == 1 || atom2.d == 0 || atom2.d == 1) return;
            if ((atom1.x > atom2.x && atom1.d == 2 && atom2.d == 3) || (atom1.x < atom2.x && atom1.d == 3 && atom2.d == 2)) {
                int time = xDiff / 2;
                pq.offer(new Event(atom1Idx, atom2Idx, time));
            }
            return;
        }
        // 시간 계산은 x좌표가 같다면 y좌표 차이 / 2 + 차이 % 2
        // => 그러면 5칸 차이가 난다면 3초만에 부딪힐 수 있게 된다.
        // x좌표와 y좌표의 차이가 같다면 직각으로 만날 수도 있다.
        if (xDiff != yDiff) return;
        int time = xDiff; // yDiff와 같다.
        int a1x = atom1.x + dx[atom1.d] * time;
        int a1y = atom1.y + dy[atom1.d] * time;
        int a2x = atom2.x + dx[atom2.d] * time;
        int a2y = atom2.y + dy[atom2.d] * time;
        if ((a1x == a2x) && (a1y == a2y)) pq.offer(new Event(atom1Idx, atom2Idx, time));
    }
}
