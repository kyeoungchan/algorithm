package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d4_1251_하나로_서울_20반_우경찬 {

    static int[][] graph;
    static double E;

    static int findParent(int x, int[] parents) {
        if (parents[x] != x) {
            parents[x] = findParent(parents[x], parents);
        }
        return parents[x];
    }

    static void unionParent(int a, int b, int[] parents) {
        a = findParent(a, parents);
        b = findParent(b, parents);

        if (a < b) {
            parents[b] = a;
        } else {
            parents[a] = b;
        }
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_1251.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            graph = new int[N + 1][2];
            for (int j = 0; j < 2; j++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int i = 1; i < N + 1; i++) {
                    graph[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            E = Double.parseDouble(br.readLine());

            int[] parents = new int[N + 1];
            for (int i = 1; i < N + 1; i++) {
                parents[i] = i;
            }

            long totalCost = 0L;
            int shortestIsland = -1;
            while (shortestIsland != 0) {
                for (int i = 1; i < N; i++) {
                    long minCost = Long.MAX_VALUE;
                    shortestIsland = 0;
                    for (int j = i + 1; j < N + 1; j++) {
                        if (findParent(i, parents) == findParent(j, parents)) {
                            continue;
                        }
                        long cost = getCost(i, j);
                        if (minCost > cost) {
                            shortestIsland = j;
                            minCost = cost;
                        }
                    }
                    if (shortestIsland != 0) {
                        unionParent(i, shortestIsland, parents);
                        totalCost += minCost;
                    }
                }
            }
            sb.append("#").append(tc).append(" ").append(totalCost).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static long getCost(int i, int j) {
        long si = graph[i][0];
        long sj = graph[i][1];
        long ei = graph[j][0];
        long ej = graph[j][1];
        return Math.round(((long)Math.pow((si - ei), 2) + (long)Math.pow((sj - ej), 2)) * E);
    }
}
