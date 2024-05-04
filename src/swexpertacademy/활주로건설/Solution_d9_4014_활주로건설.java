package swexpertacademy.활주로건설;

import java.io.*;
import java.util.*;

/**
 *  NxN 크기의 절벽지대
 *  활주로는 높이가 동일한 구간에서 건설이 가능하다.
 *  경사로: 길이 x, 높이 1
 */
public class Solution_d9_4014_활주로건설 {

    static int N, X;
    static int[][] map;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_4014.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());
            map = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            boolean[] installed;
            int ans = 0;
            boolean succeeded;
            for (int i = 0; i < N; i++) {
                succeeded = true;
                installed = new boolean[N];
                for (int j = 0; j < N - 1; j++) {
                    if (map[i][j] == map[i][j + 1]) continue;
                    if (Math.abs(map[i][j] - map[i][j + 1]) > 1) {
                        succeeded = false;
                        break;
                    } else if (map[i][j] + 1 == map[i][j + 1]) {
                        // 좌측으로 경사로를 세워야하는 경우
                        if (canSetRow(i, j, -1, installed)) {
                            // 그냥 순차적으로 j가 1씩 증가하게 냅둔다.
                        } else {
                            succeeded = false;
                            break;
                        }
                    } else if (map[i][j] == map[i][j + 1] + 1) {
                        // 우측으로 경사로를 세워야하는 경우
                        if (canSetRow(i, j + 1, 1, installed)) {
                            // 경사로를 세우는 데 성공했다면 X-1만큼 더 가서 체크해준다.
                            // X가 3이고, j가 0이었다면, 3부터 우측으로 체크를 해야한다.
                            // 위에 for문에서 j++이 되므로 X-1만큼만 더해줘서 2로 이동시킨다.
                            j += (X - 1);
                        } else {
                            succeeded = false;
                            break;
                        }
                    }
                }
                if (succeeded) ans++;
            }

            for (int j = 0; j < N; j++) {
                succeeded = true;
                installed = new boolean[N];
                for (int i = 0; i < N - 1; i++) {
                    if (map[i][j] == map[i + 1][j]) continue;
                    if (Math.abs(map[i][j] - map[i + 1][j]) > 1) {
                        succeeded = false;
                        break;
                    } else if (map[i][j] + 1 == map[i + 1][j]) {
                        // 위쪽으로 경사로를 세워야하는 경우
                        if (!canSetCol(i, j, -1, installed)) {
                            succeeded = false;
                            break;
                        }
                    } else if (map[i][j] == map[i + 1][j] + 1) {
                        // 아래쪽으로 경사로를 세워야하는 경우
                        if (canSetCol(i + 1, j, 1, installed)) {
                            // X-1만큼 i를 아래로 내려줘야한다.
                            // X가 3이고, i가 0이라면 => i는 X-1만큼 더해준 2로 가야한다.
                            i += (X - 1);
                        } else {
                            succeeded = false;
                            break;
                        }
                    }
                }
                if (succeeded) ans++;
            }

            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static boolean canSetCol(int r, int c, int dir, boolean[] installed) {
        if (installed[r]) return false;
        // X가 3이라면, 위아래로 r,c와 높이가 맞는지 2번 확인해야한다.
        for (int k = 1; k < X; k++) {
            int nr = r + dir * k;
            if (nr < 0 || nr > N - 1) return false;
            if (map[r][c] != map[nr][c] || installed[nr]) return false;
        }
        for (int k = 0; k < X; k++) {
            int nr = r + dir * k;
            installed[nr] = true;
        }
        return true;
    }

    static boolean canSetRow(int r, int c, int dir, boolean[] installed) {
        // X가 3이라면, 좌우측으로 r,c와 높이가 맞는지 2번 확인해야한다.
        if (installed[c]) return false;
        for (int k = 1; k < X; k++) {
            int nc = c + dir * k;
            if (nc < 0 || nc > N - 1) return false;
            if (map[r][c] != map[r][nc] || installed[nc]) return false;
        }
        for (int k = 0; k < X; k++) {
            int nc = c + dir * k;
            installed[nc] = true;
        }
        return true;
    }
}
