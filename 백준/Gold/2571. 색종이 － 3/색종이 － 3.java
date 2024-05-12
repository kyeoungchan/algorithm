import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        final int boardSize = 100;
        final int oneSizeLen = 10;
        int[][] board = new int[boardSize][boardSize];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int c = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            for (int dr = 0; dr < oneSizeLen; dr++) {
                for (int dc = 0; dc < oneSizeLen; dc++) {
                    board[r + dr][c + dc] = 1;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // 1인 경우에는 위에 누적된 높이를 더해줘서 자신의 높이를 메모한다.
                if (board[i][j] == 1) board[i][j] += board[i - 1][j];
            }
        }

        int maxSpace = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) continue;
                int minHeight = boardSize;
                for (int k = j; k < boardSize && board[i][k] != 0; k++) {
                    minHeight = Math.min(minHeight, board[i][k]);
                    int rowLen = k - j + 1;
                    maxSpace = Math.max(maxSpace, minHeight * rowLen);
                }
            }
        }

        System.out.println(maxSpace);
        br.close();
    }
}