import java.util.*;
import java.io.*;

public class Main {

    static List<int[]>[] list;
    static boolean[] visited;
    static int max, node;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int V = Integer.parseInt(br.readLine());
        list = new List[V + 1];
        for (int i = 1; i < V + 1; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < V; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int s = Integer.parseInt(st.nextToken()); // 번호
            while (true) {
                int to = Integer.parseInt(st.nextToken());
                if (to == -1) { // -1이 입력되면 to는 -2
                    break;
                }
                int cost = Integer.parseInt(st.nextToken());
                list[s].add(new int[]{to, cost});
            }
        }

        max = 0;
        // 임의의 노드(1)에서부터 가장 먼 노드를 찾는다. 이때 찾은 노드를 node에 저장한다.
        // 트리의 지름을 찾을 때 가장 먼 두 정점 중 하나는 어느 정점에서든 다 가장 멀리 있을 수 밖에 없다.
        // 0이랑 100 사이에 있는 무수한 수들을 떠올려보자. 그 무수한 수들과 0과 100을 포함하면 0과 100은 지름에 속하는 정점이고,
        // 그 무수한 정점들은 서로의 거리를 비교해봐도 0 혹은 100 중 하나랑 가장 멀 수밖에 없다. (물론 50은 0과 100 모두 동일하게 멀다.)
        // 따라서 첫 dfs로 찾은 가장 먼 정점에서 다시 제일 먼 정점과의 거리를 구하면 답이 된다.
        visited = new boolean[V + 1];
        dfs(1, 0);

        // node에서부터 가장 먼 노드까지의 거리를 구한다.
        visited = new boolean[V + 1];
        dfs(node, 0);

        System.out.println(max);
        br.close();
    }

    static void dfs(int x, int len) {
//        System.out.println("x:"+x+" len:"+len);
        if (len > max) {
            max = len;
            node = x;
        }
        visited[x] = true;

        for (int i = 0; i < list[x].size(); i++) {
            int[] n = list[x].get(i);
            int v = n[0];
            int cost = n[1];

            if (!visited[v]) {
                visited[v] = true;
                dfs(v, cost + len);
            }
        }
    }
}