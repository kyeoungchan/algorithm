package programmers.카카오앱정리하기;

import java.util.*;

class Solution {

    static class App {
        int id, r, c;

        App(int id, int r, int c) {
            this.id = id;
            this.r = r;
            this.c = c;
        }

        @Override
        public String toString() {
            return "id: " + id + ", r: " + r + ", c: " + c + "\n";
        }
    }

    int N, M;
    int[] dr = new int[] {0, 0, 1, 0, -1}, dc = new int[] {0, 1, 0, -1, 0};
    int[][] board;

    public int[][] solution(int[][] board, int[][] commands) {
        N = board.length;
        M = board[0].length;
        this.board = board;

        for (int[] command: commands) {
            move(command[0], command[1]);
        }

        return this.board;
    }

    void move(int id, int d) {
        // printBoard();
        Set<Integer> movingGroup = getGroup(id, d);
        moveGroup(movingGroup, d);

        while (true) {
            // printBoard();
            List<Integer> brokenApps = findBrokenApps(d);
            if (brokenApps.isEmpty()) break;

            movingGroup = getGroup(brokenApps.get(0), d);
            moveGroup(movingGroup, d);
        }
    }

    Set<Integer> getGroup(int id, int d) {
        Set<Integer> group = new HashSet<>();
        ArrayDeque<App> q = new ArrayDeque<>();

        group.add(id);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == id) q.offer(new App(board[i][j], i, j));
            }
        }

        while (!q.isEmpty()) {
            // System.out.println(q);
            App cur = q.poll();

            int nr = (cur.r + dr[d] + N) % N;
            int nc = (cur.c + dc[d] + M) % M;
            if (board[nr][nc] == 0 || group.contains(board[nr][nc])) continue;

            group.add(board[nr][nc]);
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (board[i][j] == board[nr][nc]) {
                        q.offer(new App(board[nr][nc], i, j));
                    }
                }
            }
        }

        return group;
    }

    void moveGroup(Set<Integer> group, int d) {
        // System.out.println(group);
        List<App> apps = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (group.contains(board[i][j])) {
                    apps.add(new App(board[i][j], i, j));
                }
            }
        }

        // 어플 지우기
        for (App app: apps) {
            board[app.r][app.c] = 0;
        }

        // 어플 이동시키기
        for (App app: apps) {
            int nr = (app.r + dr[d] + N) % N;
            int nc = (app.c + dc[d] + M) % M;
            board[nr][nc] = app.id;
        }
    }

    List<Integer> findBrokenApps(int d) {
        List<Integer> brokenApps = new ArrayList<>();

        if (d == 1 || d == 3) {
            // 좌우로 움직인 경우
            for (int i = 0; i < N; i++) {
                if (board[i][0] == 0 || board[i][0] != board[i][M - 1]) continue;
                boolean isBroken = false;
                for (int j = 1; j < M; j++) {
                    if (board[i][j] != board[i][0]) {
                        isBroken = true;
                        break;
                    }
                }
                if (isBroken) {
                    brokenApps.add(board[i][0]);
                    break;
                }
            }
        } else {
            // 상하로 움직인 경우
            for (int j = 0; j < M; j++) {
                if (board[0][j] == 0 || board[0][j] != board[N - 1][j]) continue;
                boolean isBroken = false;
                for (int i = 1; i < N; i++) {
                    if (board[i][j] != board[0][j]) {
                        isBroken = true;
                        break;
                    }
                }
                if (isBroken) {
                    brokenApps.add(board[0][j]);
                    break;
                }
            }
        }

        return brokenApps;
    }

    void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.printf("%2d", board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}