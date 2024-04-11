package test.a2;

import java.util.*;
import java.io.*;

public class Solution2 {

    static final int INF = 10000000;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/a2input2.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int W = Integer.parseInt(st.nextToken());
            int H = Integer.parseInt(st.nextToken());
            int[][] map = new int[H][W];
            boolean[] impossibleH = new boolean[H];
            boolean[] impossibleW = new boolean[W];
            List<int[]> houses = new ArrayList<>();
            for (int i = 0; i < H; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < W; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if (map[i][j] == 1) {
                        impossibleH[i] = true;
                        impossibleW[j] = true;
                        houses.add(new int[]{i, j});
                    }
                }
            }
            int answer = INF;
            int max = 0;
            for (int i = 0; i < H; i++) {
                if (impossibleH[i]) continue;
                for (int[] house : houses) {
                    max = Math.max(max, Math.abs(house[0] - i));
                }
                answer = Math.min(answer, max);
            }

            for (int j = 0; j < W; j++) {
                if (impossibleW[j]) continue;
                max = 0;
                for (int[] house : houses) {
                    max = Math.max(max, Math.abs(house[1] - j));
                }
                answer = Math.min(answer, max);
            }

            boolean fail = false;
            int[] dist = new int[houses.size()];
            for (int j = 1; j < W; j++) {
                Arrays.fill(dist, INF);
                max = 0;
                fail = false;
                for (int k = 0; k < Math.min(j + 1, H); k++) {
                    if (map[k][j - k] == 1) {
                        fail = true;
                        break;
                    }
                    int idx = 0;
                    for (int[] house : houses) {
                        dist[idx] = Math.min(dist[idx], getDistance(house[0], house[1], k, j - k));
                        idx++;
                    }
                }
                if (!fail) {
                    for (int i = 0; i < houses.size(); i++) {
                        max = Math.max(max, dist[i]);
                    }
                    answer = Math.min(answer, max);
                }
            }

            for (int i = 0; i < H - 1; i++) {
                Arrays.fill(dist, INF);
                max = 0;
                fail = false;
                for (int k = 0; k < Math.min(H - i, W); k++) {
                    if (map[i + k][W - 1 - k] == 1) {
                        fail = true;
                        break;
                    }
                    int idx = 0;
                    for (int[] house : houses) {
                        dist[idx] = Math.min(dist[idx], getDistance(house[0], house[1], i + k, W - 1 - k));
                        idx++;
                    }
                }
                if (!fail) {
                    for (int ii = 0; ii < houses.size(); ii++) {
                        max = Math.max(max, dist[ii]);
                    }
                    answer = Math.min(answer, max);
                }
            }

            for (int i = 0; i < H - 1; i++) {
                Arrays.fill(dist, INF);
                max = 0;
                fail = false;
                for (int k = 0; k < Math.min(H - i, W); k++) {
                    if (map[i + k][k] == 1) {
                        fail = true;
                        break;
                    }
                    int idx = 0;
                    for (int[] house : houses) {
                        dist[idx] = Math.min(dist[idx], getDistance(house[0], house[1], i + k, k));
                        idx++;
                    }
                }
                if (!fail) {
                    for (int ii = 0; ii < houses.size(); ii++) {
                        max = Math.max(max, dist[ii]);
                    }
                    answer = Math.min(answer, max);
                }
            }


            for (int j = 0; j < W - 1; j++) {
                Arrays.fill(dist, INF);
                max = 0;
                fail = false;
                for (int k = 0; k < Math.min(W - j, H); k++) {
                    if (map[k][j + k] == 1) {
                        fail = true;
                        break;
                    }
                    int idx = 0;
                    for (int[] house : houses) {
                        dist[idx] = Math.min(dist[idx], getDistance(house[0], house[1], k, j + k));
                        idx++;
                    }
                }
                if (!fail) {
                    for (int i = 0; i < houses.size(); i++) {
                        max = Math.max(max, dist[i]);
                    }
                    answer = Math.min(answer, max);
                }
            }

            if (answer == INF) answer = -1;
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static int getDistance(int r, int c, int i, int j) {
        return Math.abs(r - i) + Math.abs(c - j);
    }
}
