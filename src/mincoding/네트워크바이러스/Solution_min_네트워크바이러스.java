package mincoding.네트워크바이러스;

import java.io.*;
import java.util.*;

public class Solution_min_네트워크바이러스 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int computerCnt = Integer.parseInt(br.readLine());
        int[][] graph = new int[computerCnt + 1][computerCnt + 1];
        int edgeCnt = Integer.parseInt(br.readLine());
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[computerCnt + 1];
        visited[1] = true;
        int result = 0;
        for (int i = 0; i < edgeCnt; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph[a][b] = 1;
            graph[b][a] = 1;
            if (a == 1 && !visited[b]) {
                queue.offer(b);
                visited[b] = true;
                result++;
            } else if (b == 1 && !visited[a]) {
                queue.offer(a);
                visited[a] = true;
                result++;
            }
        }
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = 1; i < computerCnt + 1; i++) {
                if (graph[cur][i] == 0 || visited[i]) continue;
                visited[i] = true;
                queue.offer(i);
                result++;
            }
        }
        System.out.println(result);
        br.close();
    }
}
