import java.io.*;
import java.util.*;

/**
 * 최대한 긴 등산로를 만들 계획이다.
 * 등산로는 가장 높은 봉우리에서 시작한다.
 * 산으로 올라갈수록 반드시 높은 지형에서 낮은 지형으로 4방탐색으로 연결돼야한다.
 * 높이가 같아서도 안 된다.
 * 최대 K 깊이만큼 지형을 깎는 공사를 할 수 있다.
 * 가장 높은 봉우리는 여러 곳일 수도 있다
 * 필요에 따라서는 깍아서 지형을 1보다 낮출 수도 있다.
 * 3차원 visited 배열을 활용해서, 0-> 깎은 적 없음, 1-> 1만큼 깎음 식으로 방문처리를 하자.
 * 대신 0보다 크면 깎을 수 없고, 0이면 깎을 수 있는 기회가 주어진다.
 * boolean 타입으로 깎을 수 있고 없고를 결정하기 위해 Node 사용
 */
public class Solution {


    static int N, K, maxHeight, answer;
    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
    static int[][] map;
    static boolean[][] v;

    public static void main(String[] args) throws Exception {
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
            maxHeight = 0;
            int maxCnt = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    // 지형의 높이는 1 이상 20 이하다.
                    // 가장 높은 봉우리는 최대 5개다.
                    if (map[i][j] > maxHeight) {
                        maxHeight = map[i][j];
                        maxCnt = 1;
                    } else if (map[i][j] == maxHeight) {
                        maxCnt++;
                    }
                }
            }
            int[] maxPeeks = new int[maxCnt];
            maxCnt = 0;
            end: for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (maxHeight == map[i][j]) {
                        maxPeeks[maxCnt++] = i * N + j;
                    }
                    if (maxCnt == maxPeeks.length) {
                        break end;
                    }
                }
            }

            answer = 0;
            v = new boolean[N][N];
            for (int pos : maxPeeks) {
                int r = pos / N;
                int c = pos % N;
                v[r][c] = true;
                dfs(r, c, false, 1);
                v[r][c] = false;
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void dfs(int r, int c, boolean hasCut, int len) {
        boolean moved = false;
        for (int d = 0; d < 4; d++) {
            int ni = r + di[d];
            int nj = c + dj[d];
            if (ni < 0 || ni > N - 1 || nj < 0 || nj > N - 1 || v[ni][nj]) continue;
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
            answer = Math.max(answer, len);
        }
    }

    static boolean canCut(int r, int c, int nr, int nc, boolean hasCut) {
        if (hasCut) return false;
        return map[nr][nc] - map[r][c] < K;
    }

}