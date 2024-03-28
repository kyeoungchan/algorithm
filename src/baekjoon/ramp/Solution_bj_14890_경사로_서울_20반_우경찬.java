package baekjoon.ramp;

import java.util.*;
import java.io.*;

public class Solution_bj_14890_경사로_서울_20반_우경찬 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int ANS = 2 * N;
        boolean[] hasBuilt;
        boolean allSucceed;
        for (int i = 0; i < N; i++) {
            hasBuilt = new boolean[N];
            allSucceed = true;
            end: for (int j = 0; j < N - 1; j++) {
                if (Math.abs(map[i][j] - map[i][j + 1]) > 1) {
                    allSucceed = false;
                    break;
                } else if (map[i][j] + 1 == map[i][j + 1]) {
                    int lower = map[i][j];
                    for (int k = 0; k < L; k++) {
                        int idx = j - k;
                        if (idx < 0 || map[i][idx] != lower || hasBuilt[idx]) {
                            allSucceed = false;
                            break end;
                        }
                    }
                    for (int k = 0; k < L; k++) {
                        int idx = j - k;
                        hasBuilt[idx] = true;
                    }
                } else if (map[i][j] == map[i][j + 1] + 1) {
                    int lower = map[i][j + 1];
                    for (int k = 0; k < L; k++) {
                        int idx = j + 1 + k;
                        if (idx > N - 1 || map[i][idx] != lower || hasBuilt[idx]) {
                            allSucceed = false;
                            break end;
                        }
                    }
                    for (int k = 0; k < L; k++) {
                        int idx = j + 1 + k;
                        hasBuilt[idx] = true;
                    }
                }
            }
            if (!allSucceed) {
                ANS--;
            }
        }

        for (int j = 0; j < N; j++) {
            hasBuilt = new boolean[N];
            allSucceed = true;
            end: for (int i = 0; i < N - 1; i++) {
                if (Math.abs(map[i][j] - map[i + 1][j]) > 1) {
                    allSucceed = false;
                    break;
                } else if (map[i][j] + 1 == map[i + 1][j]) {
                    int lower = map[i][j];
                    for (int k = 0; k < L; k++) {
                        int idx = i - k;
                        if (idx < 0 || map[idx][j] != lower || hasBuilt[idx]) {
                            allSucceed = false;
                            break end;
                        }
                    }
                    for (int k = 0; k < L; k++) {
                        int idx = i - k;
                        hasBuilt[idx] = true;
                    }
                } else if (map[i][j] == map[i + 1][j] + 1) {
                    int lower = map[i + 1][j];
                    for (int k = 0; k < L; k++) {
                        int idx = i + 1 + k;
                        if (idx > N - 1 || map[idx][j] != lower || hasBuilt[idx]) {
                            allSucceed = false;
                            break end;
                        }
                    }
                    for (int k = 0; k < L; k++) {
                        int idx = i + 1 + k;
                        hasBuilt[idx] = true;
                    }
                }
            }
            if (!allSucceed) {
                ANS--;
            }
        }
        System.out.println(ANS);
        br.close();
    }
}
