package swexpertacademy.등산로조성;

import java.io.*;
import java.util.*;

public class Solution_d9_1949_등산로조성_참고 {

    static int N, K, answer;
    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
    static int[][] map;
    static boolean[][] v;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_1949.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            // * 등산로는 NxN이다.
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            map = new int[N][N];
            int maxHeight = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    // 지형의 높이는 1 이상 20 이하다.
                    map[i][j] = Integer.parseInt(st.nextToken());
                    // 가장 높은 봉우리는 최대 5개다.
                    // 등산로는 가장 높은 봉우리에서 시작한다.
                    if (map[i][j] > maxHeight) {
                        maxHeight = map[i][j];
                    }
                }
            }
            answer = 0;
            v = new boolean[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (maxHeight == map[i][j]) {
                        v[i][j] = true;
                        dfs(i, j, false, 1);
                        v[i][j] = false;
                    }
                }
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void dfs(int r, int c, boolean hasCut, int len) {
        // 가지치기! 현재의 봉우리의 높이만큼 계속 걸어가도 answer에 못 도달한다면 가지치기
        // 참고로 마지막에 0까지 다다르고 한번 깎아서 음수까지 도달한다쳐도 +1이면 answer랑 같아지는 것이 최선이므로 < 부등호가 맞다.
        if (len + map[r][c] < answer) return;

        boolean moved = false;
        for (int d = 0; d < 4; d++) {
            int ni = r + di[d];
            int nj = c + dj[d];
            if (ni < 0 || ni > N - 1 || nj < 0 || nj > N - 1 || v[ni][nj]) continue;
            // 산으로 올라갈수록 반드시 높은 지형에서 낮은 지형으로 4방탐색으로 연결돼야한다.
            // 높이가 같아서도 안 된다.
            if (map[ni][nj] < map[r][c]){
                moved = true;
                v[ni][nj] = true;
                dfs(ni, nj, hasCut, len + 1);
                v[ni][nj] = false;
            } else if (canCut(r, c, ni, nj, hasCut)) {
                int sub = map[ni][nj] - map[r][c];
                moved = true;
                for (int deep = sub + 1; deep < K + 1; deep++) {
                    map[ni][nj] -= deep;
                    v[ni][nj] = true;
                    dfs(ni, nj, true, len + 1);
                    v[ni][nj] = false;
                    map[ni][nj] += deep;
                }
            }
        }
        if (!moved) {
            // 최대한 긴 등산로를 만들 계획이다.
            answer = Math.max(answer, len);
        }
    }

    static boolean canCut(int r, int c, int nr, int nc, boolean hasCut) {
        if (hasCut) return false;
        // 최대 K 깊이만큼 지형을 깎는 공사를 할 수 있다.
        // 필요에 따라서는 깍아서 지형을 1보다 낮출 수도 있다.
        return map[nr][nc] - map[r][c] < K;
    }

}
