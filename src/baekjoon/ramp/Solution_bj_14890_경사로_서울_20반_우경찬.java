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
            flag: for (int j = 0; j < N - 1; j++) {
                if (Math.abs(map[i][j] - map[i][j + 1]) > 1) {
                    allSucceed = false;
                    break;
                } else if (map[i][j] + 1 == map[i][j + 1]) {
                    int lower = map[i][j];
                    for (int k = 1; k < L; k++) {
                        int idx = j-k;
                        if (map[i][idx] != lower || hasBuilt[idx]) {
                            allSucceed = false;
                            break flag;
                        }
                    }
                    for (int k = 0; k < L; k++) {
                        int idx = j - k;
                        hasBuilt[idx] = true;
                    }
                } else if (map[i][j] == map[i][j + 1] + 1) {
                    int lower = map[i][j + 1];
                    for (int k = 0; k < L; k++) {
                        
                    }
                }
            }
            if (!allSucceed) {
                ANS--;
            }
        }
        br.close();
    }
}
