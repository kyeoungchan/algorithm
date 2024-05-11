package baekjoon.색종이_3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_bj_2571_색종이3_다른사람코드 {
    private static final int SQUARE_LENGTH = 100;
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 13);
    private static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        int n = Integer.parseInt(br.readLine());
        int[][] arr = new int[SQUARE_LENGTH + 1][SQUARE_LENGTH + 1];
        for (int[] row : arr)    // -무한대로 초기화
            Arrays.fill(row, -10001);
        while (n-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            for (int i = r; i < r + 10; i++) {
                for (int j = c; j < c + 10; j++) {
                    arr[i][j] = 1;
                }
            }
        }

        int[][] prefixSum = new int[SQUARE_LENGTH + 1][SQUARE_LENGTH + 1];
        for (int i = 1; i <= SQUARE_LENGTH; i++) {    // 2차원 prefix sum
            for (int j = 1; j <= SQUARE_LENGTH; j++) {
                prefixSum[i][j] = arr[i][j] + prefixSum[i - 1][j] + prefixSum[i][j - 1] - prefixSum[i - 1][j - 1];
            }
        }

        int answer = 0;
        for (int i = 1; i <= SQUARE_LENGTH; i++) {
            for (int j = 1; j <= SQUARE_LENGTH; j++) {    // (i,j)는 직사각형의 좌측 상단
                for (int ib = i + 1; ib <= SQUARE_LENGTH; ib++) {
                    for (int jb = j + 1; jb <= SQUARE_LENGTH; jb++) {    // (ib,jb)는 직사각형의 우측 하단
                        int area = prefixSum[ib][jb] - prefixSum[i - 1][jb] - prefixSum[ib][j - 1] + prefixSum[i - 1][j - 1];
                        if (area < 0) break;    // j<jb이고 jb는 항상 증가하므로 한번이라도 음수라면 그 뒤로 전부 음수이므로 무시한다.
                        answer = Math.max(area, answer);
                    }
                }
            }
        }
        System.out.println(answer);
    }
}