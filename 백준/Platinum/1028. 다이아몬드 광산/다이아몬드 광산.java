import java.io.*;
import java.util.*;

public class Main {

    private static int N, M;
    private static int[] dr = new int[]{1, 1, -1, -1}, dc = new int[]{1, -1, -1, 1};
    private static int[][] map;
    private static int[][][] dp;

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            map = new int[N][M];
            dp = new int[N][M][4];

            for (int i = 0; i < N; i++) {
                String s = br.readLine();
                for (int j = 0; j < M; j++) {
                    map[i][j] = s.charAt(j) - '0';
                }
            }

            // 크기가 1X1인 경우
            if (N == 1 && M == 1) {
                System.out.println(map[0][0]);
                return;
            }

            int maxSize = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (map[i][j] == 1) {
                        for (int d = 0; d < 4; d++) {
                            dp[i][j][d] = getSize(i, j, d);
                        }

                        // 왼쪽 위 변과 오른쪽 위 변이 maxSize를 넘는 경우
                        if (dp[i][j][2] > maxSize && dp[i][j][3] > maxSize) {
                            int len = Math.min(dp[i][j][2], dp[i][j][3]);

                            // 최대 길이가 maxSize까지 다이아몬드 모양이 되는지 확인
                            for (int tempLen = len; tempLen > maxSize; tempLen--) {
                                int upI = i - 2 * (tempLen - 1);
                                if (upI < 0) continue;

                                if (dp[upI][j][0] >= tempLen && dp[upI][j][1] >= tempLen) {
                                    maxSize = tempLen;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(maxSize);
        }
    }

    /*0: 위 꼭짓점 기준 오른쪽 아래
    * 1: 위 꼭짓점 기준 왼쪽 아래
    * 2: 아래 꼭짓점 기준 왼쪽 위
    * 3: 아래 꼭짓점 기준 오른쪽 위*/
    private static int getSize(int r, int c, int d) {
        int size = 1;

        while (true) {
            int nr = r + dr[d];
            int nc = c + dc[d];

            // 범위를 벗어나거나 값이 0인 경우
            if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || map[nr][nc] == 0) {
                break;
            }

            size++;
            r = nr;
            c = nc;
        }

        return size;
    }
}