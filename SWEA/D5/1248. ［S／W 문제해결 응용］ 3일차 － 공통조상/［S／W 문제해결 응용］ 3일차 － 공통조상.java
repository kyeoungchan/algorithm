import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        ArrayDeque<Integer> q = new ArrayDeque<>();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int[] parents = new int[V + 1];
            int[][] children = new int[V + 1][2];
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < E; i++) {
                // 간선은 항상 “부모 자식” 순서로 표기
                int parent = Integer.parseInt(st.nextToken());
                int child = Integer.parseInt(st.nextToken());
                parents[child] = parent;
                if (children[parent][0] == 0) children[parent][0] = child;
                else children[parent][1] = child;
            }

            boolean[] visited = new boolean[V + 1];
            visited[a] = true;
            visited[b] = true;
            int root = 0;
            while (true) {
                if (parents[a] != 0) {
                    a = parents[a];
                    if (visited[a]) {
                        root = a;
                        break;
                    }
                    visited[a] = true;
                }
                if (parents[b] != 0) {
                    b = parents[b];
                    if (visited[b]) {
                        root = b;
                        break;
                    }
                    visited[b] = true;
                }
            }

            q.clear();
            q.offer(root);
            int answer = 0;
            while (!q.isEmpty()) {
                int cur = q.poll();
                answer++;
                if (children[cur][0] != 0) {
                    q.offer(children[cur][0]);
                    if (children[cur][1] != 0) q.offer(children[cur][1]);
                }
            }
            sb.append("#").append(tc).append(" ").append(root).append(" ").append(answer).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}