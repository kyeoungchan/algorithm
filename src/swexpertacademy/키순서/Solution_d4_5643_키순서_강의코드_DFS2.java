package swexpertacademy.키순서;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_d4_5643_키순서_강의코드_DFS2 {

    static int N, M, cnt;
    static int[][] adj, radj;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_5643.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine()); // (2<= N <= 500)
            M = Integer.parseInt(br.readLine()); // 0 <= M <= N * (N - 1) / 2
            adj = new int[N + 1][N + 1]; // 자신보다 큰 관계 표현
            radj = new int[N + 1][N + 1]; // 자신보다 작은 관계 표현
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                radj[b][a] = adj[a][b] = 1; // a보다 b가 크다. b보다 a가 키가 작다.
            }

            int ans = 0;
            for (int i = 1; i < N + 1; i++) {
                cnt = 0;
                dfs(i, new boolean[N + 1], adj);
                dfs(i, new boolean[N + 1], radj);
                if (cnt == N - 1) ans++;
            }
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void dfs(int cur, boolean[] visited, int[][] adj) {
        visited[cur] = true;
        for (int i = 1; i < N + 1; i++) {
            if (adj[cur][i] == 1 && !visited[i]) {
                // 나보다 큰 애 카운팅 / 나보다 작은 애 카운팅
                cnt++;
                dfs(i, visited, adj);
            }
        }
    }
}
