package swexpertacademy.줄기세포배양;

import java.io.*;
import java.util.*;

public class Solution_d9_5653_줄기세포배양2 {

    static class Cell implements Comparable<Cell> {
        int r, c, level, inactiveTime, activeTime;

        public Cell(int r, int c, int level) {
            this.r = r;
            this.c = c;
            this.level = level;
            inactiveTime = level;
            activeTime = 0;
        }

        @Override
        public int compareTo(Cell o) {
            // 생명력 내림차순
            return Integer.compare(o.level, level);
        }
    }

    static int gridRSize, gridCSize;
    static int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
    static int[][] grid;
    static List<Cell> cells;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_5653.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            // 줄기 세포의 크기에 비해 배양 용기의 크기가 매우 크기 때문에 시뮬레이션에서 배양 용기의 크기는 무한하다고 가정한다.
            // (1≤N≤50, 1≤M≤50)
            // 1 ~ N, 1 ~ M => 1 - K ~ N + K, 1 - K ~ M + K => 0 ~ N + 2K - 1, 0 ~ M + 2K - 1
            gridRSize = N + 2 * K;
            gridCSize = M + 2 * K;
            grid = new int[gridRSize][gridCSize];
            cells = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                int r = i + K - 1;
                for (int j = 0; j < M; j++) {
                    int c = j + K - 1;
                    grid[r][c] = Integer.parseInt(st.nextToken());
                    if (grid[r][c] != 0) cells.add(new Cell(r, c, grid[r][c]));
                }
            }

            simulate(K);
            sb.append("#").append(tc).append(" ").append(cells.size()).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void simulate(int K) {
        PriorityQueue<Cell> pq = new PriorityQueue<>();
        for (int time = 0; time < K; time++) {
            for (int i = 0; i < cells.size(); i++) {
                Cell cell = cells.get(i);
                // 초기 상태에서 줄기 세포들은 비활성 상태이며 생명력 수치가 X인 줄기 세포의 경우 X시간 동안 비활성 상태
                if (cell.inactiveTime > 0) {
                    cell.inactiveTime--;
                    if (cell.inactiveTime == 0) {
                        // X시간이 지나는 순간 활성 상태가 된다.
                        cell.activeTime = cell.level;
                    }
//                } else if (cell.activeTime > 0) {
                } else {
                    if (cell.activeTime == cell.level) {
                        for (int d = 0; d < 4; d++) {
                            pq.offer(new Cell(cell.r + dr[d], cell.c + dc[d], cell.level));
                        }
                    }
                    cell.activeTime--;
                    if (cell.activeTime == 0) {
                        // 줄기 세포가 활성 상태가 되면 X시간 동안 살아있을 수 있으며 X시간이 지나면 세포는 죽게 된다.
                        cells.remove(i);
                        i--;
                    }
                }/* else {
                    System.out.println("who are you? " + cell);
                }*/
            }

            while (!pq.isEmpty()) {
                Cell cell = pq.poll();
                if (grid[cell.r][cell.c] == 0) {
                    grid[cell.r][cell.c] = cell.level;
                    cells.add(cell);
                }
            }
        }
    }
}
