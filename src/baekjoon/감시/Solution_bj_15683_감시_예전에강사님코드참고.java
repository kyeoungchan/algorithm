package baekjoon.감시;

//package a0222;

import java.util.*;
import java.io.*;

public class Solution_bj_15683_감시_예전에강사님코드참고 {

    static int N, M, answer, dark, cctvCnt;
    static int[][] map;
    static List<int[]> cctv;
    static int[] di = new int[] {-1, 0, 1, 0}, dj = new int[] {0, 1, 0, -1};
    static int[][] dCctv = new int[][] {
            {0}, {0, 2}, {0, 1}, {0, 1, 3}, {0, 1, 2, 3}
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        dark = 0;
        answer = Integer.MAX_VALUE;
        cctv = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 0)
                    dark++;
                else if (map[i][j] != 6){
                    cctv.add(new int[] {i, j, map[i][j]});
                }
            }
        }
        cctvCnt = cctv.size();
        dfs(0);

        System.out.println(answer);
        br.close();
    }

    static void dfs(int cnt) {
        if (cnt == cctvCnt) {
            answer = Math.min(dark, answer);
            return;
        }

        int cctvKind = cctv.get(cnt)[2];
        if (cctvKind == 5) {
            watch(cctv.get(cnt), 0, true);
            dfs(cnt + 1);
            watch(cctv.get(cnt), 0, false);
        } else if (cctvKind == 2) {
            for (int d = 0; d < 2; d++) {
                watch(cctv.get(cnt), d, true);
                dfs(cnt + 1);
                watch(cctv.get(cnt), d, false);
            }
        } else {
            for (int d = 0; d < 4; d++) {
                watch(cctv.get(cnt), d, true);
                dfs(cnt + 1);
                watch(cctv.get(cnt), d, false);
            }
        }
    }

    static void watch(int[] cctvInfo, int dir, boolean isOn) {
        int ci = cctvInfo[0];
        int cj = cctvInfo[1];
        int cctvKind = cctvInfo[2];
        int value = isOn ? -1 : 1;
        for (int d = 0; d < dCctv[cctvKind-1].length; d++) {
            int ndir = (dir + dCctv[cctvKind-1][d]) % 4;
            int k = 1;
            while (true) {
                int ni = ci + k * di[ndir];
                int nj = cj + k * dj[ndir];
                k++;
                if (ni < 0 || ni >= N || nj < 0 || nj >= M || map[ni][nj] == 6) {
                    break;
                }
                if (map[ni][nj] <= 0) {
                    if (isOn && map[ni][nj] == 0)
                        dark += value;
                    map[ni][nj] += value;
                    if (!isOn && map[ni][nj] == 0)
                        dark += value;

                }
            }
        }
    }

}

