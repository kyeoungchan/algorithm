class Solution {
    
    static class Result {
        boolean win;
        int move;
        
        Result(boolean win, int move) {
            this.win = win;
            this.move = move;
        }
    }
    
    int n, m, INF = 123456789;
    int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[] {0, 1, 0, -1};
    int[][] board;
    public int solution(int[][] board, int[] aloc, int[] bloc) {
        n = board.length;
        m = board[0].length;
        this.board = board;
        return dfs(aloc[0], aloc[1], bloc[0], bloc[1], 0).move;
    }
    
    Result dfs(int ar, int ac, int br, int bc, int move) {
        boolean isATurn = move % 2 == 0;
        int r = isATurn ? ar : br;
        int c = isATurn ? ac : bc;
        if (board[r][c] == 0) {
            return new Result(false, move);
        }
        boolean canWin = false;
        int winMove = INF;
        int loseMove = -1;
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (nr < 0 || nr >= n || nc < 0 || nc >= m || board[nr][nc] == 0) continue;
            board[r][c] = 0;
            Result next;
            if (isATurn) {
                next = dfs(nr, nc, br, bc, move + 1);
            } else {
                next = dfs(ar, ac, nr, nc, move + 1);
            }
            board[r][c] = 1;
            if (!next.win) {
                // 상대방이 졌다면 내가 이긴 경우
                canWin = true;
                winMove = Math.min(winMove, next.move);
            } else {
                // 상대방이 이겼다면 내가 진 경우
                loseMove = Math.max(loseMove, next.move);
            }
        }
        if (canWin) {
            return new Result(true, winMove);
        } else if (loseMove == -1) {
            return new Result(false, move);
        } else {
            return new Result(false, loseMove);
        }
    }
}