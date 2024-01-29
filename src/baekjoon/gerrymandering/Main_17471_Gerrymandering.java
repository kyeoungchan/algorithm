package baekjoon.gerrymandering;

import java.util.*;
import java.io.*;

/**
 * 전략: 이거 파라메트릭 서치 알고리즘으로 풀어야한다. 내일 풀자..
 */
public class Main_17471_Gerrymandering {

    static int N;
    static int[] populations;
    static int[][] graph;
    static int[] parents;

    static int findParents(int x, int[] parents) {
        if (parents[x] != x) {
            parents[x] = findParents(parents[x], parents);
        }
        return parents[x];
    }

    static void unionParents(int a, int b, int[] parents) {
        a = findParents(a, parents);
        b = findParents(b, parents);

        if (a < b) {
            parents[b] = a;
        } else {
            parents[a] = b;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());
        populations = new int[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i <= N; i++) {
            populations[i] = Integer.parseInt(st.nextToken());
        }
        graph = new int[N + 1][];
        parents = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            parents[i] = i;
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int adj = Integer.parseInt(st.nextToken());
            graph[i] = new int[adj];
            for (int j = 0; j < adj; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        br.close();
    }
}
