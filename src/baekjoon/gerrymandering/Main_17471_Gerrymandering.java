package com.ssafy.study.algor;

import java.util.*;
import java.io.*;

/**
 * 전략
 * 1. 먼저 DFS로 지역을 하나하나 1 혹은 2 선거구에 넣는다.
 * 2. 다 담았으면 BFS를 통해 연결된 수를 구한다.
 * 	- visited[]와 인접된 노드의 선거구 번호가 일치하는지 여부를 통해 연결여부를 확인한다.
 * 	- BFS가 끝나면 link를 더해준다. => 연결된 선거구의 개수가 2 초과 혹은 2 미만이 나올 수 있게 된다.
 * 3. 전역변수로 선언한 min을 link가 2인 경우의 두 선거구의 인구수의 차와 비교하여 최솟값을 업데이트한다.
 */
public class Main_17471_Gerrymandering {

    static int N;
    static int[] populations, area;
    static int[][] graph;
    static boolean[] visited;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        populations = new int[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        for (int i = 1; i <= N; i++) {
            populations[i] = Integer.parseInt(st.nextToken());
        }
        graph = new int[N + 1][];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int adj = Integer.parseInt(st.nextToken());
            graph[i] = new int[adj];
            for (int j = 0; j < adj; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        area = new int[N + 1]; // 각 지역구가 속한 선거구 저장. 1 또는 2
        dfs(1); // 뽑을 수 있는 모든 지역구를 뽑는 dfs 탐색

        if (min == Integer.MAX_VALUE)
            System.out.println("-1");
        else
            System.out.println(min);
        br.close();
    }

    static void dfs(int k) {
        if (k == N + 1) { // 모든 지역 다 뽑았다면
            int area1 = 0;
            int area2 = 0;
            visited = new boolean[N + 1];

            int link = 0; // 연결된 지역들의 개수를 셈
            for (int i = 1; i <= N; i++) {
                if (!visited[i]) {
                    bfs(i, area[i]); // 연결된 지역들을 모두 방문표시하는 bfs 탐색
                    link++;
                }
            }

            // 지역이 2개로 나눠졌고, 두 지역 안에서 서로 연결되어있다면 최솟값 계산
            if (link == 2) {
                for (int i = 1; i <= N; i++) {
                    if (area[i] == 1)
                        area1 += populations[i];
                    else
                        area2 += populations[i];
                }
                min = Math.min(min, Math.abs(area1 - area2));
            }
            return;
        }
        area[k] = 1; // k지역 1번 선거구에 할당
        dfs(k + 1);

        area[k] = 2; // k지역 2번 선거구에 할당
        dfs(k + 1);
    }

    static void bfs(int idx, int areaNum) {
        Deque<Integer> queue = new ArrayDeque<>();
        visited[idx] = true;
        queue.offer(idx);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int i = 0; i < graph[current].length; i++) {
                int next = graph[current][i];
                if (area[next] == areaNum && !visited[next]) {
                    queue.offer(next);
                    visited[next] = true;
                }
            }
        }
    }
}
