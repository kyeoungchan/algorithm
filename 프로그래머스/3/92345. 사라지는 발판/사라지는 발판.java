import java.util.*;

class Solution {
    
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static int[][] map;
    static int R, C;

    static class Result {
        boolean win;
        int count;
        Result(boolean win, int count) {
            this.win = win;
            this.count = count;
        }
    }

    public int solution(int[][] board, int[] aloc, int[] bloc) {
        map = board;
        R = board.length;
        C = board[0].length;
        return dfs(aloc[0], aloc[1], bloc[0], bloc[1], 0).count;
    }

    private Result dfs(int ar, int ac, int br, int bc, int turn) {
        // 현재 턴의 플레이어 위치가 발판이 사라진 경우 패배
        boolean curTurnA = (turn % 2 == 0);
        int r = curTurnA ? ar : br;
        int c = curTurnA ? ac : bc;
        
        if (map[r][c] == 0) return new Result(false, turn);

        boolean canMove = false;
        int minWin = Integer.MAX_VALUE;
        int maxLose = 0;

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if (!isValid(nr, nc) || map[nr][nc] == 0) continue;
            canMove = true;

            // 발판 소모 처리
            map[r][c] = 0;
            Result next;
            if (curTurnA) {
                next = dfs(nr, nc, br, bc, turn + 1);
            } else {
                next = dfs(ar, ac, nr, nc, turn + 1);
            }
            // 원복
            map[r][c] = 1;

            // 미니맥스 로직
            if (!next.win) { // 상대가 지면 내가 이김
                minWin = Math.min(minWin, next.count);
            } else { // 상대가 이기면 내가 짐
                maxLose = Math.max(maxLose, next.count);
            }
        }

        if (!canMove) return new Result(false, turn);
        if (minWin != Integer.MAX_VALUE) return new Result(true, minWin);
        return new Result(false, maxLose);
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C;
    }
    
    
}