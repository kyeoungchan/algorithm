package programmers.카카오앱정리하기;

import java.util.*;

public class Solution {

    // 앱은 정사각형 모양
    static class App {
        int id, size, r, c;

        App(int id, int size, int r, int c) {
            this.id = id;
            this.size = size;
            this.r = r;
            this.c = c;
        }

        @Override
        public String toString() {
            return "id: " + id + ", size: " + size + ", r: " + r + ", c: " + c + "\n";
        }
    }

    static class Overlapped {
        int id, dist;

        Overlapped(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public String toString() {
            return "id: " + id + ", dist: " + dist + "\n";
        }
    }

    int N, M;
    int[] dr = new int[] {0, 0, 1, 0, -1}, dc = new int[] {0, 1, 0, -1, 0};
    Map<Integer, App> appMap;

    public int[][] solution(int[][] board, int[][] commands) {
        N = board.length;
        M = board[0].length;
        appMap = new HashMap<>();

        for (int i = 0; i < N; i++) {
            int id = 0;
            int size = 0;
            for (int j = 0; j < M; j++) {
                if (id == 0) {
                    if (board[i][j] == 0) continue;
                    if (appMap.containsKey(board[i][j])) continue;
                    id = board[i][j];
                    size++;
                    if (j == M - 1) {
                        int r = i;
                        int c = j - size + 1;
                        appMap.put(id, new App(id, size, r, c));
                    }
                } else {
                    if (board[i][j] == 0) {
                        int r = i;
                        int c = j - size;
                        appMap.put(id, new App(id, size, r, c));
                        id = 0;
                        size = 0;
                    } else {
                        if (id == board[i][j]) {
                            size++;
                        } else {
                            int r = i;
                            int c = j - size;
                            appMap.put(id, new App(id, size, r, c));
                            if (appMap.containsKey(board[i][j])) {
                                id = size = 0;
                                continue;
                            }
                            id = board[i][j];
                            size = 1;
                        }
                        if (j == M - 1) {
                            int r = i;
                            int c = j - size + 1;
                            appMap.put(id, new App(id, size, r, c));
                        }
                    }
                }
            }
        }
        // System.out.println(appMap);
        // printGrid();
        for (int[] command: commands) {
            move(command[0], command[1], 1);
            // printGrid();
        }
        // System.out.println(appMap);
        int[][] answer = new int[N][M];
        for (Map.Entry<Integer, App> entry: appMap.entrySet()) {
            int r = entry.getValue().r;
            int c = entry.getValue().c;
            int size = entry.getValue().size;
            int id = entry.getKey();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    answer[r + i][c + j] = id;
                }
            }
        }
        // printGrid();
        return answer;
    }

    // d: 방향, dist: 이동 거리
    void move(int id, int d, int dist) {
        App target = appMap.get(id);
        int nr = target.r + dr[d] * dist;
        int nc = target.c + dc[d] * dist;
        int nd = d;
        int size = target.size;

        if (nr < 0) {
            nr = N - size;
        } else if (nr + size - 1 >= N) {
            nr = 0;
        } else if (nc < 0) {
            nc = M - size;
        } else if (nc + size - 1 >= M) {
            nc = 0;
        }
        target.r = nr;
        target.c = nc;
        List<Overlapped> overlappedList = getOverlappedList(id, d);

        for (Overlapped o: overlappedList) {
            move(o.id, d, o.dist);
        }

    }

    List<Overlapped> getOverlappedList(int id, int d) {
        App target = appMap.get(id);

        int ceiling = target.r;
        int bottom = target.r + target.size - 1;
        int left = target.c;
        int right = target.c + target.size - 1;

        List<Overlapped> result = new ArrayList<>();
        for (App val: appMap.values()) {
            if (val.id == id) continue;
            int c = val.r;
            int b = val.r + val.size - 1;
            int l = val.c;
            int r = val.c + val.size - 1;
            if (d == 1) {
                // 오른쪽으로 가는 중이라면 좌상이나 좌하를 기준으로 확인
                if (left <= l && l <= right) {
                    if ((ceiling <= c && c <= bottom) || (ceiling <= b && b <= bottom)) {
                        result.add(new Overlapped(val.id, right - l + 1));
                    }
                }
            } else if (d == 2) {
                // 아래쪽으로 가는 중이라면 좌상이나 우상을 기준으로 확인
                if (ceiling <= c && c <= bottom) {
                    if ((left <= l && l <= right) || (left <= r && r <= right)) {
                        result.add(new Overlapped(val.id, bottom - c + 1));
                    }
                }
            } else if (d == 3) {
                // 왼쪽으로 가는 중이라면 우상이나 우하를 기준으로 확인
                if (left <= r && r <= right) {
                    if ((ceiling <= c && c <= bottom) || (ceiling <= b && b <= bottom)) {
                        result.add(new Overlapped(val.id, r - left + 1));
                    }
                }
            } else {
                // 위쪽으로 가는 중이라면 좌하나 우하를 기준으로 확인
                if (ceiling <= b && b <= bottom) {
                    if ((left <= l && l <= right) || (left <= r && r <= right)) {
                        result.add(new Overlapped(val.id, b - ceiling + 1));
                    }
                }
            }
        }

        return result;
    }

    // 디버깅용
    void printGrid() {
        int[][] grid = new int[N][M];
        for (Map.Entry<Integer, App> entry: appMap.entrySet()) {
            int r = entry.getValue().r;
            int c = entry.getValue().c;
            int size = entry.getValue().size;
            int id = entry.getKey();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    grid[r + i][c + j] = id;
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.printf("%2d", grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
