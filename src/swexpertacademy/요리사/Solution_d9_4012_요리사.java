package swexpertacademy.요리사;

import java.io.*;
import java.util.*;

/**
 * 음식의 맛은 식재료들의 조합에 따라 다르다.
 */
public class Solution_d9_4012_요리사 {
    static int N, min;
    static int[][] S;
    static boolean[] isA;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_4012.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            // N개의 식재료
            N = Integer.parseInt(br.readLine());
            S = new int[N][N];
            // 시너지 정보 Sij
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
        // 각각 N/2개씩 나누어 두 개의 요리를 하려고 한다.
        if (cnt == N / 2) {
            calculate();
            return;
        }

        for (int i = start; i < N / 2 + cnt + 1; i++) {
            // N이 4
            // cnt가 0 -> 0~2
            // cnt가 1 -> 1~3
            // N이 6
            // cnt가 0 -> 0~3
            // cnt가 1 -> 1~4
            // cnt가 2 -> 2~5
//            if (N - i + cnt < N / 2) break;
            // N이 8, N/2이 4인데 i가 6이라고 치자. 그리고 cnt는 현재 0이다.
            // 그러면 굳이 재귀호출을 할 필요가 없이 더이상 N/2개를 더 뽑을 방법이 없다는 것을 알고 메서드를 종료시키는 게 더 낫다.
            // 앞으로 이 반복문으로 재귀호출을 할 수 있는 횟수: N - i번
            // cnt는 그 재귀호출 횟수만큼 1씩 더해진다. cnt가 2라면, i -> 6, cnt -> 3 / i -> 7, cnt -> 4 / 종료조건에 들어갈 수 있다.
            isA[i] = true;
            comb(cnt + 1, i + 1);
            isA[i] = false;
        }
    }

    static void calculate() {
        // 두 명의 손님에게 음식 제공
        // nC2 정도의 조합은 이중반복문으로 해결할 수 있다.
        int a = 0;
        int b = 0;
        // 각 음식의 맛은 시너지들의 합이다.
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
        // A음식과 B음식의 맛의 차이가 최소가 되도록 재료를 배분
        min = Math.min(min, Math.abs(a - b));
    }
}
