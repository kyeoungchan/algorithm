package baekjoon.arrayrotate4;

import java.util.*;
import java.io.*;

public class Solution_bj_17406_배열돌리기4_서울_20반_우경찬 {

    static int N, M, K, min;
    static int[][] A, tempA;
    static int[] di = {1, 0, -1, 0}, dj = {0, 1, 0, -1}; // 반시계방향
    static int[] operators;
    static boolean[] v;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        int[][] commands = new int[K][3];
        A = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 3; j++) {
                commands[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        operators = new int[K];
        min = Integer.MAX_VALUE;
        v = new boolean[K];
        perm(0, commands);

        System.out.println(min);
        br.close();
    }

    static void perm(int cnt, int[][] commands) {
        if (cnt == K) {
            tempA = new int[N][M];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    tempA[i][j] = A[i][j];
                }
            }
            for (int idx : operators) {
                rotate(commands[idx]);
            }
            min = Math.min(min, getArrayValue());
            return;
        }

        for (int i = 0; i < K; i++) {
            if (v[i]) continue;
            v[i] = true;
            operators[cnt] = i;
            perm(cnt + 1, commands);
            v[i] = false;
        }
    }

    static void rotate(int[] command) {
        int startI = command[0] - command[2] - 1;
        int startJ = command[1] - command[2] - 1;
        int endI = command[0] + command[2] - 1;
        int endJ = command[1] + command[2] - 1;
        int border = 2 * command[2] + 1; // 결국 작은 배열의 길이는 홀수일 수밖에 없다.
        for (int t = 0; t < border / 2; t++) {
            int i = t + startI, j = t + startJ;
            int temp = tempA[i][j];
            for (int d = 0; d < 4; d++) {
                while (true) {
                    int ni = i + di[d];
                    int nj = j + dj[d];
                    if (t + startI <= ni && ni <= endI - t && t + startJ <= nj && nj <= endJ - t) {
                        tempA[i][j] = tempA[ni][nj];
                        i = ni;
                        j = nj;
                    } else {
                        break;
                    }
                }
            }
            tempA[startI + t][startJ + t + 1] = temp;
        }
    }

    static int getArrayValue() {
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int rowV = 0;
            for (int j = 0; j < M; j++) {
                rowV += tempA[i][j];
            }
            result = Math.min(result, rowV);
        }
        return result;
    }
}
