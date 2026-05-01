import java.util.*;

class Solution {
    int h, n, m, k;
    String[] grid;
    int[][] panels;
    int[][] seqs;
    int[] elev; // 각 층에서 엘리베이터 위치 (행, 열) - 모든 층 동일
    int elevR, elevC;
    long[][] dist; // dist[i][j]: 패널 i -> 패널 j 이동 비용

    public int solution(int h, String[] grid, int[][] panels, int[][] seqs) {
        this.h = h;
        this.n = grid.length;
        this.m = grid[0].length();
        this.k = panels.length;
        this.grid = grid;
        this.panels = panels;
        this.seqs = seqs;

        // 엘리베이터 위치 찾기
        outer:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i].charAt(j) == '@') {
                    elevR = i;
                    elevC = j;
                    break outer;
                }
            }
        }

        // 패널 간 이동 비용 전처리 (BFS)
        dist = new long[k][k];
        for (long[] row : dist) Arrays.fill(row, Long.MAX_VALUE / 2);
        for (int i = 0; i < k; i++) {
            bfs(i);
        }

        // 안전 순서: prerequisite[b] = b를 활성화하기 전에 필요한 패널들의 마스크
        int[] prereq = new int[k];
        for (int[] seq : seqs) {
            int a = seq[0] - 1, b = seq[1] - 1;
            prereq[b] |= (1 << a);
        }

        // 비트마스크 DP
        // 시작: 1번 패널(인덱스 0) 위치에서 출발
        // dp[mask][cur] = mask 상태로 cur 패널에 있을 때 최소 시간
        int total = 1 << k;
        long[][] dp = new long[total][k];
        for (long[] row : dp) Arrays.fill(row, Long.MAX_VALUE / 2);

        // 시작 위치는 panels[0] 위치 (1번 패널)
        // 시작 시 아무 패널도 활성화 안 됨, 위치는 panels[0]
        // 시작 위치를 "가상 패널 -1"로 처리하거나, 초기 상태 설정
        // 초기: visited=0, 현재 위치=panels[0] 위치
        // panels[0]에서 각 패널로의 비용으로 초기화
        // 시작점 -> 패널 i 이동 비용 계산
        long[] startDist = bfsFrom(panels[0][0], panels[0][1], panels[0][2]);

        for (int i = 0; i < k; i++) {
            if ((prereq[i] & 0) == prereq[i]) { // 선행 조건 없음
                if (startDist[i] < Long.MAX_VALUE / 2) {
                    dp[1 << i][i] = startDist[i];
                }
            }
        }

        long ans = Long.MAX_VALUE;
        int fullMask = total - 1;

        for (int mask = 1; mask < total; mask++) {
            for (int cur = 0; cur < k; cur++) {
                if (dp[mask][cur] >= Long.MAX_VALUE / 2) continue;
                if ((mask & (1 << cur)) == 0) continue;

                if (mask == fullMask) {
                    ans = Math.min(ans, dp[mask][cur]);
                    continue;
                }

                // 다음 패널 선택
                for (int next = 0; next < k; next++) {
                    if ((mask & (1 << next)) != 0) continue;
                    // 선행 조건 체크
                    if ((prereq[next] & mask) != prereq[next]) continue;
                    // next로 이동
                    long cost = dist[cur][next];
                    if (cost >= Long.MAX_VALUE / 2) continue;
                    int newMask = mask | (1 << next);
                    if (dp[newMask][next] > dp[mask][cur] + cost) {
                        dp[newMask][next] = dp[mask][cur] + cost;
                    }
                }
            }
        }

        return (int) ans;
    }

    // 패널 i 위치에서 모든 다른 패널까지의 최단 거리
    void bfs(int from) {
        int ff = panels[from][0]; // 층 (1-based)
        int fr = panels[from][1] - 1; // 행 (0-based)
        int fc = panels[from][2] - 1; // 열 (0-based)

        long[] result = bfsFrom(ff, fr + 1, fc + 1);
        for (int i = 0; i < k; i++) {
            dist[from][i] = result[i];
        }
    }

    // (floor, row 1-based, col 1-based) 에서 각 패널까지 거리
    long[] bfsFrom(int startFloor, int startRow, int startCol) {
        // 상태: (floor, row, col) -> 0-based
        // floor: 0..h-1, row: 0..n-1, col: 0..m-1
        int[][][] visited = new int[h][n][m];
        for (int[][] a : visited) for (int[] b : a) Arrays.fill(b, Integer.MAX_VALUE);

        int sf = startFloor - 1, sr = startRow - 1, sc = startCol - 1;
        visited[sf][sr][sc] = 0;

        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{sf, sr, sc, 0});

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // BFS (층 이동은 엘리베이터에서만, 비용 = |층차|)
        // 층 이동 비용이 균일하지 않으므로 Dijkstra 사용
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(x -> x[3]));
        long[][][] dist3 = new long[h][n][m];
        for (long[][] a : dist3) for (long[] b : a) Arrays.fill(b, Long.MAX_VALUE);
        dist3[sf][sr][sc] = 0;
        pq.add(new long[]{sf, sr, sc, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int cf = (int) cur[0], cr = (int) cur[1], cc = (int) cur[2];
            long cd = cur[3];
            if (dist3[cf][cr][cc] < cd) continue;

            // 같은 층 이동
            for (int d = 0; d < 4; d++) {
                int nr = cr + dr[d], nc = cc + dc[d];
                if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue;
                if (grid[nr].charAt(nc) == '#') continue;
                long nd = cd + 1;
                if (dist3[cf][nr][nc] > nd) {
                    dist3[cf][nr][nc] = nd;
                    pq.add(new long[]{cf, nr, nc, nd});
                }
            }

            // 엘리베이터 이용: 현재 위치가 엘리베이터 칸일 때만
            if (cr == elevR && cc == elevC) {
                for (int nf = 0; nf < h; nf++) {
                    if (nf == cf) continue;
                    long nd = cd + Math.abs(nf - cf);
                    if (dist3[nf][elevR][elevC] > nd) {
                        dist3[nf][elevR][elevC] = nd;
                        pq.add(new long[]{nf, elevR, elevC, nd});
                    }
                }
            }
        }

        long[] result = new long[k];
        for (int i = 0; i < k; i++) {
            int pf = panels[i][0] - 1, pr = panels[i][1] - 1, pc = panels[i][2] - 1;
            result[i] = dist3[pf][pr][pc];
        }
        return result;
    }
}