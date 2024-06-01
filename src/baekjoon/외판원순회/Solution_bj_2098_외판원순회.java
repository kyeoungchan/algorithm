package baekjoon.외판원순회;

import java.io.*;
import java.util.*;

/**
 * 1~N번까지 번호가 매겨진 도시가 있다.
 * N개의 도시를 모두 거쳐 다시 원래의 도시로 돌아오는 순회 여행 경로를 계획중이다.
 * 한 번 갔던 도시로는 다시 갈 수 없다.
 * 맨 마지막에 출발 도시로 돌아오는 것은 예외다.
 * 가장 적은 비용을 들이는 여행 계획을 세우고자 한다.
 * W[i][j] 형태로 비용이 주어지고, 비대칭이고, 갈 수 없는 경우는 0으로 주어진다.
 */
public class Solution_bj_2098_외판원순회 {

    static final int INF = 987654321;
    static int N;
    static int[][] W, costs;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        W = new int[N][N];
        costs = new int[N][1 << N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) W[i][j] = Integer.parseInt(st.nextToken());
            Arrays.fill(costs[i], -1);
        }

        // 어디서 시작하든지 결과는 다 똑같다. 순회의 모양이 복잡하다고 생각해도 결국에는 순환 형태이기 때문이다.
        travel(0, 1);
        System.out.println(costs[0][1]);
        br.close();
    }

    private static void travel(int cur, int status) {
        if (status == (1 << N) - 1) {
            if (W[cur][0] != 0)
                costs[cur][status] = W[cur][0];
            else
                costs[cur][status] = INF;
            return;
        }

        if (costs[cur][status] != -1) return;
        costs[cur][status] = INF;

        for (int i = 0; i < N; i++) {
            if (W[cur][i] == 0 || ((1 << i) & status) > 0) continue;
            int nStatus = (1 << i) | status;
            travel(i, nStatus);
            costs[cur][status] = Math.min(costs[i][nStatus] + W[cur][i], costs[cur][status]);
        }
    }
}
