package swexpertacademy.키순서;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_d4_5643_키순서_강의코드_Floyd {

    static int N, M, cnt;
    static int[][] adj;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_5643.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine()); // (2<= N <= 500)
            M = Integer.parseInt(br.readLine()); // 0 <= M <= N * (N - 1) / 2
            adj = new int[N + 1][N + 1];

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                adj[a][b] = 1; // a보다 b가 크다.
            }

            int ans = 0;
            for (int k = 1; k < N + 1; k++) { // 경유
                for (int i = 1; i < N + 1; i++) { // 출발
                    if (i == k || adj[i][k] == 0) continue;
                    for (int j = 1; j < N + 1; j++) { // 도착
                        if (adj[i][j] == 1) continue;
                        adj[i][j] = adj[i][k] & adj[k][j];
                    }
                }
            }

            for (int i = 1; i < N + 1; i++) {
                for (int j = 1; j < N + 1; j++) {
                    adj[i][0] += adj[i][j]; // i보다 큰 j가 결국 카운트에 누적
                    adj[0][j] += adj[i][j]; // j보다 작은 i가 결국 카운트에 누적
                }
            }

            for (int k = 1; k < N + 1; k++) {
                if (adj[k][0] + adj[0][k] == N - 1) ans++;
            }
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void dfs(int cur) {
        for (int i = 1; i < N + 1; i++) {
            if (adj[cur][i] == 1) {
                // 나보다 큰 애 카운팅
                if (adj[i][0] == -1) { // 탐색이 안 된 학생 탐색하기
                    dfs(i);
                }
                // i학생보다 큰 학생이 있다면 그 관계들을 자신과의 관계에 반영
                if (adj[i][0] > 0) {
                    for (int j = 1; j < N + 1; j++) {
                        if (adj[i][j] == 1) { // i보다 큰 학생 j를 나 cur과의 관계 표현
                            adj[cur][j] = 1;
                        }
                    }
                }
            }
        }
        int cnt = 0;
        for (int i = 1; i < N + 1; i++) cnt += adj[cur][i]; // 자신보다 큰 학생 카운팅
        adj[cur][0] = cnt;
    }
}
