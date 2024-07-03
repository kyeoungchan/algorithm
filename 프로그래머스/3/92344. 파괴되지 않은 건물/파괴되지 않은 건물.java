import java.util.*;

class Solution {
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;
        int N = board.length;
        int M = board[0].length;
        int[][] status = new int[N + 1][M + 1]; // 오른쪽 칸을 만들어주기 위해 M+1
        for (int i = 0; i < skill.length; i++) {
            int type = skill[i][0];
            int startR = skill[i][1];
            int startC = skill[i][2];
            int endR = skill[i][3];
            int endC = skill[i][4];
            int degree = skill[i][5];
            if (type == 1)
                // 적의 공격
                degree *= -1;
            status[startR][startC] += degree;
            status[startR][endC + 1] -= degree;
            status[endR + 1][startC] -= degree;
            status[endR + 1][endC + 1] += degree;
        }
        
        int singleStatus = 0;
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < M; j++) {
                status[i][j] += status[i-1][j];
            }
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < M; j++) {
                status[i][j] += status[i][j-1];
            }
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (status[i][j] + board[i][j] > 0) answer++;
            }
        }
        return answer;
    }
}