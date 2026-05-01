package programmers.발전소회로복구;

import java.util.*;

class Solution {

    static class Pos {
        int h, r, c, mask, time;

        public Pos(int h, int r, int c, int mask, int time) {
            this.h = h;
            this.r = r;
            this.c = c;
            this.mask = mask;
            this.time = time;
        }

        @Override
        public String toString() {
            return "h: " + h + ", r: " + r + ", c: " + c + ", mask: " + mask + ", time: " + time;
        }
    }

    int k;
    int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};
    int[][][] factory;
    boolean[][][][] visited;

    int[] needPanelMasks;


    public int solution(int h, String[] grid, int[][] panels, int[][] seqs) {
        int answer = 0;
        int n = grid.length;
        int m = grid[0].length();
        k = panels.length;
        // needPanels = new Set[k + 1];
        needPanelMasks = new int[k + 1];

        factory = new int[h + 1][n][m];
        visited = new boolean[h + 1][n][m][1 << k];

        for (int i = 1; i <= h; i++) {
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < m; c++) {
                    if (grid[r].charAt(c) == '#') {
                        // 벽은 -1로 표시
                        factory[i][r][c] = -1;
                    } else if (grid[r].charAt(c) == '@') {
                        // 엘베는 -2로 표시
                        factory[i][r][c] = -2;
                    }
                }
            }
        }
        for (int i = 0; i < k; i++) {
            // i + 1번 패널

            int num = i + 1;
            // needPanels[num] = new HashSet<>();
            factory[panels[i][0]][panels[i][1] - 1][panels[i][2] - 1] = num;
        }

        for (int[] seq : seqs) {
            //needPanels[seq[1]].add(seq[0]);
            needPanelMasks[seq[1]] = (needPanelMasks[seq[1]] | (1 << (seq[0] - 1)));
        }
        /*
        for (int i = 1; i <= h; i++) {
            for (int r = 0; r < factory[i].length; r++) {
                for (int c = 0; c < factory[i][r].length; c++) {
                    System.out.printf("%2d ", factory[i][r][c]);
                }
                System.out.println();
            }
            System.out.println();
        }
        */
        //System.out.println(Arrays.toString(needPanels));

        ArrayDeque<Pos> q = new ArrayDeque<>();

        Pos pos;
        if (!canActive(1, 0)) {
            // 만약 1번 패널이 바로 활성화 불가능하다면
            pos = new Pos(panels[0][0], panels[0][1] - 1, panels[0][2] - 1, 0, 0);

        } else {
            // 만약 1번 패널이 바로 활성화가 가능하다면
            pos = new Pos(panels[0][0], panels[0][1] - 1, panels[0][2] - 1, 1, 0);
            //deleteNeedPanel(1);
            if (pos.mask == (1 << k) - 1) {
                return pos.time;
            }
        }
        q.offer(pos);
        visited[pos.h][pos.r][pos.c][pos.mask] = true;

        //System.out.println(Arrays.toString(needPanels));

        while (!q.isEmpty()) {
            Pos cur = q.poll();
            //System.out.println(cur);
            int nTime = cur.time + 1;
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                int nMask = cur.mask;

                if (nr < 0 || nr >= n || nc < 0 || nc >= m || factory[cur.h][nr][nc] == -1 || visited[cur.h][nr][nc][nMask])
                    continue;
                if (factory[cur.h][nr][nc] > 0 &&
                        ((1 << (factory[cur.h][nr][nc] - 1)) & nMask) == 0) {
                    // 현재 위치가 패널인데 비활성화 패널이라면
                    int curNum = factory[cur.h][nr][nc];

                    // 활성화가 가능하다면
                    if (canActive(curNum, nMask)) {

                        // 활성화되기 전에는 방문 완료로 표시
                        visited[cur.h][nr][nc][nMask] = true;
                        nMask = (nMask | (1 << (curNum - 1)));

                        // 모두 활성화됐다면 종료
                        if (nMask == (1 << k) - 1) {
                            return nTime;
                        }

                    }
                }
                if (visited[cur.h][nr][nc][nMask]) continue;
                visited[cur.h][nr][nc][nMask] = true;
                Pos nPos = new Pos(cur.h, nr, nc, nMask, nTime);
                q.offer(nPos);
            }

            if (factory[cur.h][cur.r][cur.c] == -2) {
                // 현재 위치가 엘베라면 층 이동도 고려

                int nh = cur.h - 1;
                if (nh >= 1 && !visited[nh][cur.r][cur.c][cur.mask]) {
                    visited[nh][cur.r][cur.c][cur.mask] = true;
                    q.offer(new Pos(nh, cur.r, cur.c, cur.mask, nTime));
                }

                nh = cur.h + 1;
                if (nh <= h && !visited[nh][cur.r][cur.c][cur.mask]) {
                    visited[nh][cur.r][cur.c][cur.mask] = true;
                    q.offer(new Pos(nh, cur.r, cur.c, cur.mask, nTime));
                }

            }

        }

        return answer;
    }

    boolean canActive(int targetNum, int mask) {
        return (needPanelMasks[targetNum] & mask) == needPanelMasks[targetNum];
    }

}
