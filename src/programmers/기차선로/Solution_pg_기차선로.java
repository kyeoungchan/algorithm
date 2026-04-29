package programmers.기차선로;

import java.util.*;

public class Solution_pg_기차선로 {
    public static void main(String[] args) {
        Solution_pg_기차선로 solutionPg_기차선로 = new Solution_pg_기차선로();
        System.out.println(solutionPg_기차선로.solution(new int[][]{
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 1}
        }));
        // 기댓값 644
    }

    // 0: 우, 1: 하, 2: 좌, 3: 상
    int[] dr = {0, 1, 0, -1};
    int[] dc = {1, 0, -1, 0};

    // 문제의 그림을 바탕으로 한 선로별 연결 방향 (정확한 매칭 필수)
    int[][] types = {
            {},
            {0, 2},       // 1: ↔
            {1, 3},       // 2: ↕
            {0, 1, 2, 3}, // 3: #
            {2, 3},       // 4: ┛ (좌, 상)
            {0, 3},       // 5: ┗ (우, 상)
            {0, 1},       // 6: ┏ (우, 하)
            {1, 2}        // 7: ┓ (좌, 하)
    };

    int N, M;
    int[][] map;
    int answer;

    public int solution(int[][] grid) {
        N = grid.length;
        M = grid[0].length;
        map = grid;
        answer = 0;

        int[][] visited = new int[N][M];
        // (0,0)은 항상 1번 선로이며, 문제 조건에 따라 오른쪽(0)으로 출발합니다.
        visited[0][0] = 1;

        // (0,0)에서 출발하여 다음 칸으로 이동 시작
        dfs(0, 0, 0, visited);

        return answer;
    }

    private void dfs(int r, int c, int outDir, int[][] visited) {
        int nr = r + dr[outDir];
        int nc = c + dc[outDir];

        // 범위를 벗어나거나 장애물(-1)인 경우
        if (nr < 0 || nr >= N || nc < 0 || nc >= M || map[nr][nc] == -1) return;

        // 목적지 (N-1, M-1) 도달 시
        if (nr == N - 1 && nc == M - 1) {
            if (canConnect(outDir, map[nr][nc])) {
                visited[nr][nc]++;
                if (isAllSatisfied(visited)) {
                    answer++;
                }
                visited[nr][nc]--;
            }
            return;
        }

        // 일반 칸 진행
        if (map[nr][nc] > 0) {
            int type = map[nr][nc];
            if (canConnect(outDir, type)) {
                if (type == 3 && visited[nr][nc] < 2) {
                    visited[nr][nc]++;
                    dfs(nr, nc, getNextDir(outDir, type), visited);
                    visited[nr][nc]--;
                } else if (type != 3 && visited[nr][nc] == 0) {
                    visited[nr][nc]++;
                    dfs(nr, nc, getNextDir(outDir, type), visited);
                    visited[nr][nc]--;
                }
            }
        } else {
            // 빈칸(0)에 선로를 놓는 모든 경우의 수 탐색
            for (int t = 1; t <= 7; t++) {
                if (canConnect(outDir, t)) {
                    map[nr][nc] = t;
                    visited[nr][nc]++;
                    dfs(nr, nc, getNextDir(outDir, t), visited);
                    visited[nr][nc]--;
                    map[nr][nc] = 0;
                }
            }
        }
    }

    // 현재 나가는 방향(outDir)이 다음 선로(nextType)와 연결되는지 확인
    private boolean canConnect(int outDir, int nextType) {
        int inDir = (outDir + 2) % 4;
        for (int d : types[nextType]) {
            if (d == inDir) return true;
        }
        return false;
    }

    // 다음 칸에서 기차가 나갈 방향 결정
    private int getNextDir(int outDir, int type) {
        if (type == 3) return outDir; // 교차로는 직진
        int inDir = (outDir + 2) % 4;
        for (int d : types[type]) {
            if (d != inDir) return d;
        }
        return -1;
    }

    // 조건 확인: 모든 선로 방문 & 3번 선로는 4방향 연결(2회 방문)
    private boolean isAllSatisfied(int[][] visited) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] > 0) {
                    if (map[i][j] == 3) {
                        if (visited[i][j] != 2) return false;
                    } else {
                        if (visited[i][j] == 0) return false;
                    }
                }
            }
        }
        return true;
    }
}
