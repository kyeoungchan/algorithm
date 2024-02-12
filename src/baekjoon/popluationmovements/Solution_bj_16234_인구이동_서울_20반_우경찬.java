package baekjoon.popluationmovements;

import java.util.*;
import java.io.*;

public class Solution_bj_16234_인구이동_서울_20반_우경찬 {

    static int N, L, R;
    static int[][] land;
    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};

    static int[] findParent(int xi,int xj, int[][][] parents) {
        int[] tempParents = null;
        if (parents[xi][xj][0] != xi || parents[xi][xj][1] != xj) {
            tempParents = findParent(parents[xi][xj][0], parents[xi][xj][1], parents);
            parents[xi][xj][0] = tempParents[0];
            parents[xi][xj][1] = tempParents[1];
            parents[xi][xj][2] = tempParents[2];
            parents[xi][xj][3] = tempParents[3];
        }
        return tempParents != null ? parents[tempParents[0]][tempParents[1]] : parents[xi][xj];
    }

    static void unionParent(int ai, int aj, int bi, int bj, int[][][] parents) {
        int[] tempAParent = findParent(ai, aj, parents);
        ai = tempAParent[0];
        aj = tempAParent[1];
        int[] tempBParent = findParent(bi, bj, parents);
        bi = tempBParent[0];
        bj = tempBParent[1];

        if (ai == bi ? aj < bj : ai < bi) {
            tempBParent[0] = ai;
            tempBParent[1] = aj;
            tempAParent[2] += tempBParent[2];
            tempAParent[3] += tempBParent[3];
        } else {
            tempAParent[0] = bi;
            tempAParent[1] = bj;
            tempBParent[2] += tempAParent[2];
            tempBParent[3] += tempAParent[3];
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        land = new int[N + 1][N + 1];
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 1; j < N + 1; j++) {
                land[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        int T = 0;
        boolean check = false;
        while (true) {
            int[][][] parents = new int[N + 1][N + 1][4];
            for (int i = 1; i < N + 1; i++) {
                for (int j = 1; j < N + 1; j++) {
                    parents[i][j][0] = i;
                    parents[i][j][1] = j;
                    parents[i][j][2] = 1;
                    parents[i][j][3] = land[i][j];
                }
            }

            check = false;
            for (int i = 1; i < N + 1; i++) {
                for (int j = 1; j < N + 1; j++) {
                    for (int d = 0; d < 4; d++) {
                        int ni = i + di[d];
                        int nj = j + dj[d];
                        if (0 < ni && ni < N + 1 && 0 < nj && nj < N + 1) {
                            int diff = Math.abs(land[i][j] - land[ni][nj]);
                            if (L <= diff && diff <= R) {
                                if (findParent(i, j, parents) != findParent(ni, nj, parents)) {
                                    unionParent(i, j, ni, nj, parents);
                                    check = true;
                                }
                            }
                        }
                    }
                }
            }
/*
            for (int i = 1; i < N + 1; i++) {
                for (int j = 1; j < N + 1; j++) {
                    System.out.print(Arrays.toString(parents[i][j]) + " ");
                }
                System.out.println();
            }
            System.out.println();
*/

            for (int i = 1; i < N + 1; i++) {
                for (int j = 1; j < N + 1; j++) {
                    int[] tempParent = findParent(i, j, parents);
                    land[i][j] = tempParent[3] / tempParent[2];
                }
            }
/*
            for (int i = 1; i < N + 1; i++) {
                for (int j = 1; j < N + 1; j++) {
                    System.out.print(land[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
*/
            if (!check) {
                break;
            }
            T++;

        }
        System.out.println(T);
        br.close();
    }
}
