package baekjoon.goodbyefinedust;

import java.util.*;
import java.io.*;

public class Solution_bj_17144_미세먼지안녕_서울_20반_우경찬 {

    static int R, C, T, cleanerUpI, cleanerDownI, cleanerJ, restDust;
    static int[][] A;
    static int[] dCI = {-1, 0, 1, 0}, dCJ = {0, 1, 0, -1}; // 상우하좌
    static int[] dRcI = {1, 0, -1, 0}, dRcJ = {0, 1, 0, -1}; // 하우상좌

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        A = new int[R + 1][C + 1];
        restDust = 0;
        for (int i = 1; i < R + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 1; j < C + 1; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
                if (A[i][j] == -1) {
                    if (cleanerUpI == 0/* || cleanerUpJ == 0*/) {
                        cleanerUpI = i;
                        cleanerJ = j;
                    } else {
                        cleanerDownI = i;
                    }
                } else if (A[i][j] != 0) {
                    restDust += A[i][j];
                }
            }
        }

        for (int i = 0; i < T; i++) {
            propagation();
            generateCleaner();
        }
        System.out.println(restDust);
        br.close();
    }

    static void propagation() {
        int[][] tempA = new int[R + 1][C + 1];

        for (int i = 1; i < R + 1; i++) {
            for (int j = 1; j < C + 1; j++) {
                if (A[i][j] != 0 && A[i][j] != -1) {
                    // 미세먼지라면
                    int newVolume = A[i][j] / 5;
                    int cnt = 0;
                    for (int d = 0; d < 4; d++) {
                        int ni = i + dCI[d];
                        int nj = j + dCJ[d];
                        if (0 < ni && ni < R + 1 && 0 < nj && nj < C + 1 && A[ni][nj] != -1) {
                            tempA[ni][nj] += newVolume;
                            cnt++;
                        }
                    }
                    tempA[i][j] += A[i][j] - newVolume * cnt;
                } else if (A[i][j] == -1) {
                    tempA[i][j] = -1;
                }
            }
        }
        A = tempA;
    }

    static void generateCleaner() {
        iterateRClock();
        iterateClock();
    }

    static void iterateRClock() {
        int i = cleanerUpI;
        int j = cleanerJ;
        for (int d = 0; d < 4; d++) {
            while (true) {
                int ni = i + dCI[d];
                int nj = j + dCJ[d];
                if (0 < ni && ni < cleanerUpI + 1 && 0 < nj && nj < C + 1) {
                    A[i][j] = A[ni][nj];
                    i = ni;
                    j = nj;
                } else {
                    break;
                }
            }
        }
        if (A[cleanerUpI][cleanerJ] != 0) {
            restDust -= A[cleanerUpI][cleanerJ];
        }
        A[cleanerUpI][cleanerJ] = -1;
        A[cleanerUpI][cleanerJ + 1] = 0;
    }

    static void iterateClock() {
        int i = cleanerDownI;
        int j = cleanerJ;
        for (int d = 0; d < 4; d++) {
            while (true) {
                int ni = i + dRcI[d];
                int nj = j + dRcJ[d];
                if (cleanerDownI - 1 < ni && ni < R + 1 && 0 < nj && nj < C + 1) {
                    A[i][j] = A[ni][nj];
                    i = ni;
                    j = nj;
                } else {
                    break;
                }
            }
        }
        if (A[cleanerDownI][cleanerJ] != 0) {
            restDust -= A[cleanerDownI][cleanerJ];
        }
        A[cleanerDownI][cleanerJ] = -1;
        A[cleanerDownI][cleanerJ + 1] = 0;

    }

    static void printA() {
        for (int i = 1; i < R + 1; i++) {
            for (int j = 1; j < C + 1; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
