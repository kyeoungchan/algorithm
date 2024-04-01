package baekjoon.travelsalesman;

import java.util.*;
import java.io.*;

public class Solution_bj_2098_외판원순회_서울_20반_우경찬 {

    static int N, W[][], dp[][], ANS, INF = 987654321;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        W = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new int[N][1 << N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }
        ANS = Integer.MAX_VALUE;

        // 현재 노드, 현재까지 가중치의 합, 현재 방문한 노드들
        System.out.println(travel(0, 1));
        br.close();
    }

    static int travel(int city, int visited) {
        if (visited == (1 << N) - 1) {
            if (W[city][0] == 0) {
                return INF;
            }
            return W[city][0];
        }

        if (dp[city][visited] != -1) {
            return dp[city][visited];
        }

        dp[city][visited] = INF;

        for (int i = 0; i < N; i++) {
            if (W[city][i] == 0 || (visited & (1 << i)) != 0) continue;
            dp[city][visited] = Math.min(dp[city][visited], travel(i, visited | (1 << i)) + W[city][i]);
        }
        return dp[city][visited];
    }
}
