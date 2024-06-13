import java.io.*;
import java.util.*;

/**
 * 이진트리
 * 임의의 두 정점의 가장 가까운 공통 조상을 찾고, 그 정점을 루트로 하는 서브 트리의 크기를 알아내는 프로그램을 작성하라
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int V = Integer.parseInt(st.nextToken());
            int E  = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int[] parents = new int[V + 1];
            int[][] children = new int[V + 1][2];
            for (int i = 1; i < V + 1; i++) {
                children[i][0] = -1;
                children[i][1] = -1;
            }

            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < E; i++) {
                // 간선은 항상 “부모 자식” 순서로 표기된다.
                int parent = Integer.parseInt(st.nextToken());
                int child = Integer.parseInt(st.nextToken());
                parents[child] = parent;
                if (children[parent][0] == -1) children[parent][0] = child;
                else children[parent][1] = child;
            }
            int root = -1;

            if (a == b) {
                root = a;
            } else {
                boolean[] visited = new boolean[V + 1];
                visited[a] = true;
                visited[b] = true;
                while (true) {
                    if (parents[a] != 0) {
                        // parents[a] == 0인 경우는 이미 루트 노드에 도달한 경우밖에 없다.
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
            }

            int answer = 0;
            ArrayDeque<Integer> q = new ArrayDeque<>();
            q.offer(root);
            while (!q.isEmpty()) {
                int cur = q.poll();
                answer++;
                if (children[cur][0] != -1) q.offer(children[cur][0]);
                if (children[cur][1] != -1) q.offer(children[cur][1]);
            }
            sb.append("#").append(tc).append(" ").append(root).append(" ").append(answer).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}