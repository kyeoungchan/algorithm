package programmers.파괴되지않은건물;

class Solution {
    public int solution(int[][] board, int[][] skill) {
        int N = board.length;
        int M = board[0].length;
        int[][] status = new int[N + 1][M + 1];
        for (int[] s: skill) {
            int type = s[0];
            int r1 = s[1];
            int c1 = s[2];
            int r2 = s[3];
            int c2 = s[4];
            int degree = s[5];
            if (type == 1) degree *= -1;
            status[r1][c1] += degree;
            status[r1][c2 + 1] -= degree;
            status[r2 + 1][c1] -= degree;
            status[r2 + 1][c2 + 1] += degree;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 1; j < M; j++) {
                status[i][j] += status[i][j - 1];
            }
        }

        for (int j = 0; j < M; j++) {
            for (int i = 1; i < N; i++) {
                status[i][j] += status[i - 1][j];
            }
        }
        int answer = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] += status[i][j];
                if (board[i][j] >= 1) answer++;
            }
        }

        return answer;
    }
}
