package swexpertacademy.요리사;

import java.util.*;
import java.io.*;

public class Solution_d9_4012_요리사_서울_20반_우경찬 {

    static int N, min;
    static int[][] S;
    static boolean[] isA;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_4012.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int tc = 1; tc < T + 1; tc++) {
            min = Integer.MAX_VALUE;
            N = Integer.parseInt(br.readLine());
            S = new int[N + 1][N + 1];
            isA = new boolean[N + 1];
            for (int i = 1; i < N + 1; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 1; j < N + 1; j++) {
                    S[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            perm(0, 0);
            sb.append("#").append(tc).append(" ").append(min).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void perm(int cnt, int start) {
        if (cnt == N / 2) {
            int aFlavor = 0;
            int bFlavor = 0;
            for (int i = 1; i < N; i++) {
                for (int j = i + 1; j < N + 1; j++) {
                    if (isA[i] && isA[j]) {
                        aFlavor += (S[i][j] + S[j][i]);
                    } else if (!isA[i] && !isA[j]) {
                        bFlavor += (S[i][j] + S[j][i]);
                    }
                }
            }
            min = Math.min(min, Math.abs(aFlavor - bFlavor));
            return;
        }

        for (int i = start; i < N; i++) {
            isA[i] = true;
            perm(cnt + 1, i + 1);
            isA[i] = false;
        }
    }
}
