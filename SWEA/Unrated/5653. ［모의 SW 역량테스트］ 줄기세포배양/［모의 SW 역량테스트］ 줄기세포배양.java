import java.io.*;
import java.util.*;

/**
 * 초기 상태에는 줄기세포들은 비활성 상태다.
 * 생명력 수치가 X인 줄기세포들는 X시간 동안 비활성 상태고, X시간 지난 후 활성 상태가 된다.
 * 줄기 세포가 활성 상태가 되면 X 시간 살아날 수 있고, X 시간이 지나면 세포는 죽는다.
 * 죽은 상태로 그리드 셀은 차지한다.
 * 활성화된 줄기 세포는 첫 1시간 동안 4방으로 번식한다.
 * 번식하는 방향에 이미 줄기 세포가 존재하는 경우 해당 방향으로 번식하지 않는다.
 * K시간 후 살아남은 줄기 세포들의 개수를 구하는 프로그램
 */
public class Solution {

    static class Cell implements Comparable<Cell> {
        int r, c;
        int level;
        int inActiveTime, activeTime;

        public Cell(int r, int c, int level) {
            this.r = r;
            this.c = c;
            this.level = level;
            inActiveTime = level;
        }


        @Override
        public int compareTo(Cell o) {
            // 생명력이 강한 애가 우선순위
            // 두 개 이상의 줄기 세포가 하나의 그리드 셀에 동시에 번식하려고 하는 경우 생명력 수치가 높은 줄기 세포가 해당 그리드 셀을 혼자서 차지하게 된다.
            return Integer.compare(o.level, level);
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "r=" + r +
                    ", c=" + c +
                    ", level=" + level +
                    ", inActiveTime=" + inActiveTime +
                    ", activeTime=" + activeTime +
                    '}';
        }
    }

    static int N, M, K;
    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
    static int[][] map;
    static List<Cell> cells;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            //  (1≤N≤50, 1≤M≤50)
            // 1~N, 1~M
            // 1-K ~ N+K, 1-K ~ M+K
            // 0 ~ N + 2K - 1, 0 ~ M + 2K - 1
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            map = new int[N + 2 * K][M + 2 * K];
            cells = new ArrayList<>();
            for (int i = 1; i < N + 1; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int r = i + K - 1;
                for (int j = 1; j < M + 1; j++) {
                    int c = j + K - 1;
                    int value = Integer.parseInt(st.nextToken());
                    map[r][c] = value;
                    if (value != 0) cells.add(new Cell(r, c, value));
                }
            }
            simulate();
            int ans = cells.size();
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void simulate() {
        PriorityQueue<Cell> pq = new PriorityQueue<>();
        for (int time = 0; time < K; time++) {

//            debug(time);
            for (int i = 0; i < cells.size(); i++) {
                Cell cell = cells.get(i);
                if (cell.inActiveTime > 0) {
                    cell.inActiveTime--;
                    if (cell.inActiveTime == 0) {
                        cell.activeTime = cell.level;
                    }
                } else if (cell.activeTime > 0) {
                    if (cell.activeTime == cell.level) {
                        pq.offer(cell);
                    }
                    cell.activeTime--;
                    if (cell.activeTime == 0) {
                        cells.remove(i);
                        i--;
                    }
                }
            }

            while (!pq.isEmpty()) {
                Cell cur = pq.poll();
                for (int d = 0; d < 4; d++) {
                    int nr = cur.r + di[d];
                    int nc = cur.c + dj[d];
                    if (map[nr][nc] != 0) continue;
                    map[nr][nc] = cur.level;
                    cells.add(new Cell(nr, nc, cur.level));
                }
            }
        }
    }

    static void debug(int time) {
        System.out.println("cells.size() = " + cells.size());
        System.out.println("time = " + time);
        for (Cell c : cells) {
            System.out.println(c);
        }
        for (int i = 0; i < N + 2 * K; i++) {
            for (int j = 0; j < M + 2 * K; j++) {
                if (map[i][j] == 0) {
                    System.out.print(" - ");
                } else {
                    boolean printed = false;
                    for (Cell cell : cells) {
                        if (cell.r == i && cell.c == j) {
                            printed = true;
                            if (cell.inActiveTime > 0) System.out.print("(" + map[i][j] + ")");
                            else  System.out.print("\"" + map[i][j] + "\"");
                            break;
                        }
                    }
                    if (!printed) {
                        System.out.print(" - ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}