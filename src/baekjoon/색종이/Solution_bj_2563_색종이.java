package baekjoon.색종이;

import java.io.*;
import java.util.*;

/**
 * 가로, 세로의 크기가 각각 100인 정사각형 모양의 흰색 도화지
 * 색종이: 세로, 가로의 크기가 각각 10이다.
 * 검은 영역의 넓이를 구하라.
 * 색종이를 붙일 때마다 0이면 1씩 해주고 그 값을 더해주면 될 거 같다.
 */
public class Solution_bj_2563_색종이 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int[][] board = new int[100][100];
        int answer = 0;
        for (int p = 0; p < n; p++) {
            st = new StringTokenizer(br.readLine(), " ");
            int c = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            for (int i = r; i < r + 10; i++) {
                for (int j = c; j < c + 10; j++) {
                    if (board[i][j] == 0) {
                        board[i][j]++;
                        answer++;
                    }
                }
            }
        }
        System.out.println(answer);
        br.close();
    }
}
