import java.util.*;
import java.io.*;

/**
 * 각 일꾼마다 선택한 두 벌통의 합이 C를 넘기면 둘 중 하나를 선택해서 꿀을 채취한다.
 * 두 일꾼이 꿀을 채취해서 얻을 수 있는 수익의 합의 최대를 출력하라.
 */
public class Solution {

    static int N, M, C, max, gotHoney;
    static int[][] honeyBag;
    static int[][] map;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            map = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            max = 0;
            // 두 명의 일꾼
            // 꿀을 채취할 수 있는 벌통의 수 M
            honeyBag = new int[2][M];
            comb(0, 0, 0, 0);
            sb.append("#").append(tc).append(" ").append(max).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void comb(int cnt, int r, int c, int worker) {
        if (cnt == 2) {
            // 각각 worker들의 채취한 벌꿀들을 제곱수로 다 더해가며 비교해야한다.
            // 그냥 덧셈으로만 비교한다면 3,3과 2,4는 덧셈으로는 같지만 제곱수의 합은 달라지기 때문이다.
            if (max < worker) {
                max = worker;
            }
            return;
        }

        if (c + M > N) {
            // 만약 1 2 3 4로 돼있고, N이 4고, M이 2라면
            // 3에서는 가능하다. 2(idx) + 2(M) = 4(N)
            // 4에서는 안 된다. 3(idx) + 2(M) > 4(N)
            r++;
            c = 0;
            if (r > N - 1) return; // 만약 행의 범위를 벗어났다면 재귀호출 종료
        }
        int startJ = c;
        for (int i = r; i < N; i++) {
            startJ = i == r ? c : 0;
            for (int j = startJ; j < N - M + 1; j++) {
                // N이 4고, M이 2라면 최대 인덱스 2까지는 가야한다.
                int tempTotal = 0;
                // 일꾼은 각각 가로로 연속되도록 벌통을 선택한다.
                for (int k = 0; k < M; k++) {
                    // 벌통에 있는 꿀은 모두 한 번에 채취한다.
                    // 서로 다른 벌통에서 채취한 꿀은 서로 다른 용기에 담는다.
                    honeyBag[cnt][k] = map[i][j + k];
                    tempTotal += honeyBag[cnt][k];
                }
                int addWorker = 0;
                if (tempTotal > C) {
                    // 두 일꾼이 채취할 수 있는 꿀의 최대 양은 C
                    gotHoney = 0;
                    getMaxSubHoney(honeyBag[cnt], 0, 0, 0);
                    addWorker = gotHoney;
                } else {
                    for (int k = 0; k < M; k++) {
                        // 상품 가치는 각 꿀의 양의 제곱수 만큼
                        addWorker += honeyBag[cnt][k] * honeyBag[cnt][k];
                    }
                }

                // 선택한 벌통이 겹치면 안 된다.
                comb(cnt + 1, i, j + M, worker + addWorker);
            }
        }
    }

    static void getMaxSubHoney(int[] temp, int idx, int sum, int value) {
        if (sum > C) return; // 가지치기
        // 부분집합!
        if (idx == M) {
            if (sum <= C) {
                gotHoney = Math.max(gotHoney, value);
            }
            return;
        }

        getMaxSubHoney(temp, idx + 1, sum + temp[idx], value + temp[idx] * temp[idx]);
        getMaxSubHoney(temp, idx + 1, sum, value);
    }
}