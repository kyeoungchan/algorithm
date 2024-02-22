package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d9_2112_보호필름_서울_20반_우경찬 {

    static int D, W, K, ANS, chemicCnt;
    static int[][] film;

    public static void main(String[] args) throws Exception {
        //System.setIn(new FileInputStream("res/input_d9_2112.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            D = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            film = new int[D][W];
            for (int i = 0; i < D; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < W; j++) {
                    film[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            chemicCnt = 0;
            ANS = Integer.MAX_VALUE;
            perm(0);
            sb.append("#").append(tc).append(" ").append(ANS).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void perm(int cnt) {
        if (cnt == D) {
            if (pass()) {
                ANS = Math.min(ANS, chemicCnt);
            }
            return;
        }

        for (int i = -1; i < 2; i++) {
            // i가 -1이면 그대로 두는 경우, 0이면 A 의약품 투여, 1이면 B 의약품 투여
            if (i != -1) {
                int[] temp = new int[W];
                for (int j = 0; j < W; j++) {
                    // 의약품 투여 전 복사
                    temp[j] = film[cnt][j];
                }
                for (int j = 0; j < W; j++) {
                    film[cnt][j] = i;
                }
                chemicCnt++;
                perm(cnt + 1);
                for (int j = 0; j < W; j++) {
                    // 의약품 투여 전 복사한 값으로 다시 복원
                    film[cnt][j] = temp[j];
                }
                chemicCnt--;
            } else {
                perm(cnt + 1);
            }
        }
    }

    static boolean pass() {
        for (int j = 0; j < W; j++) {
            int cnt = 0;
            boolean passed = false;
            for (int i = 0; i < D; i++ ) {
                if (i == 0) {
                    cnt++;
                } else if (film[i-1][j] == film[i][j]) {
                    cnt++;
                } else {
                    cnt = 1;
                }
                if (cnt == K) {
                    passed = true;
                    break;
                }
            }
            if (!passed)
                return false;
        }
        return true;
    }

}