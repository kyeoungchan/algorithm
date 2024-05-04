import java.io.*;
import java.util.*;

/**
 * 두 명의 손님에게 음식 제공
 * N개의 식재료
 * 각각 N/2개씩 나누어 두 개의 요리를 하려고 한다.
 * A음식과 B음식의 맛의 차이가 최소가 되도록 재료를 배분
 * 음식의 맛은 식재료들의 조합에 따라 다르다.
 * 시너지 정보 Sij
 * 각 음식의 맛은 시너지들의 합이다.
 */
public class Solution {
    static int N, min;
    static int[][] S;
    static boolean[] isA;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            S = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    S[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            isA = new boolean[N];
            min = Integer.MAX_VALUE;
            comb(0, 0);
            sb.append("#").append(tc).append(" ").append(min).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void comb(int cnt, int start) {
        if (cnt == N / 2) {
            calculate();
            return;
        }

        for (int i = start; i < N / 2 + cnt + 1; i++) {
            isA[i] = true;
            comb(cnt + 1, i + 1);
            isA[i] = false;
        }
    }

    static void calculate() {
        int a = 0;
        int b = 0;
        for (int i = 0; i < N; i++) {
            if (isA[i]) {
                for (int j = i + 1; j < N; j++) {
                    if (!isA[j]) continue;
                    a += S[i][j] + S[j][i];
                }
            } else {
                for (int j = i + 1; j < N; j++) {
                    if (isA[j]) continue;
                    b += S[i][j] + S[j][i];
                }
            }
        }
        min = Math.min(min, Math.abs(a - b));
    }
}