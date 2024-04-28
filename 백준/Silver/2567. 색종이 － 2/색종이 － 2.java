import java.util.*;
import java.io.*;

/**
 * 원래 좌표로 돌아오면 그 전까지의 길이를 갖고 반복문을 끝낸다.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][] board = new int[100][100];
        int papers = Integer.parseInt(br.readLine());
        for (int p = 0; p < papers; p++) {
            st = new StringTokenizer(br.readLine(), " ");
            int c = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            // 색종이를 붙일 때마다 i가 가장 작은 값, j가 가장 작은 값을 업데이트해준다.
            for (int i = r; i < r + 10; i++) {
                for (int j = c; j < c + 10; j++) {
                    board[i][j] = 1;
                }
            }
        }
        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
        int len = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (board[i][j] == 1) {
                    for (int d = 0; d < 4; d++) {
                        int ni = i + di[d];
                        int nj = j + dj[d];
                        if (ni < 0 || ni > 99 || nj < 0 || nj > 99 || board[ni][nj] == 0) len++;
                    }
                }
            }
        }
        System.out.println(len);
        br.close();
    }
}