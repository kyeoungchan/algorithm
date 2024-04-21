package  baekjoon.테트리스게임;

import java.util.*;
import java.io.*;

/**
 * 테트리스는 5가지 조각으로 이루어져있다.
 * NxN크기의 격자
 * 블록 하나를 표에 놓아서 숫자의 합의 최댓값을 구하는 프로그램
 * 모든 테트리스는 90도씩 회전시킬 수 있다.
 */
public class Solution_bj_4920_테트리스게임 {

    static int N, max;
    static int[] window;
    static int[][] grid;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int tc = 0;
        while (true) {
            N = Integer.parseInt(br.readLine().trim());
            if (N == 0) break;
            tc++;
            grid = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            max = Integer.MIN_VALUE;
            play();
            sb.append(tc).append(". ").append(max).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void play() {
        window = new int[4];
        firstShape();
        secondShape();
        thirdShape();
        fourthShape();
        fifthShape();
    }

    static void firstShape() {
        int temp;
        for (int i = 0; i < N; i++) {
            temp = 0;
            for (int j = 0; j < 4; j++) {
                temp += grid[i][j];
            }
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 0, 3);
        }

        for (int j = 0; j < N; j++) {
            // 90도 회전 후
            temp = 0;
            for (int i = 0; i < 4; i++) {
                temp += grid[i][j];
            }
            max = Math.max(max, temp);
            slidingWindowH(temp, j, 0, 3);
        }
    }

    static void secondShape() {
        int temp;
        for (int i = 0; i < N - 1; i++) {
            temp = grid[i][0] + grid[i][1] + grid[i + 1][1] + grid[i + 1][2];
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 0, 1, i + 1, 1, 2);
        }

        // 90도 회전
        for (int i = 0; i < N - 2; i++) {
            temp = grid[i][1] + grid[i + 1][0] + grid[i + 1][1] + grid[i + 2][0];
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 1, 1, i + 1, 0, 1, i + 2, 0, 0);
        }

        // 2번째 모양은 180도 회전과 회전시키지 않을 때와 모양이 같다.
    }

    static void thirdShape() {
        int temp;
        for (int i = 0; i < N - 1; i++) {
            temp = grid[i][0] + grid[i][1] + grid[i][2] + grid[i + 1][2];
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 0, 2, i + 1, 2, 2);
        }

        // 90도 회전
        for (int i = 0; i < N - 2; i++) {
            temp = grid[i][1] + grid[i + 1][1] + grid[i + 2][0] + grid[i + 2][1];
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 1, 1, i + 1, 1, 1, i + 2, 0, 1);
        }

        // 180도 회전
        for (int i = 0; i < N - 1; i++) {
            temp = grid[i][0] + grid[i + 1][0] + grid[i + 1][1] + grid[i + 1][2];
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 0, 0, i + 1, 0, 2);
        }

        // 270도 회전
        for (int i = 0; i < N - 2; i++) {
            temp = grid[i][0] + grid[i][1] + grid[i + 1][0] + grid[i + 2][0];
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 0, 1, i + 1, 0, 0, i + 2, 0, 0);
        }
    }

    static void fourthShape() {
        int temp;
        for (int i = 0; i < N - 1; i++) {
            temp = grid[i][0] + grid[i][1] + grid[i][2] + grid[i + 1][1];
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 0, 2, i + 1, 1, 1);
        }

        // 90도 회전
        for (int i = 0; i < N - 2; i++) {
            temp = grid[i][1] + grid[i + 1][0] + grid[i + 1][1] + grid[i + 2][1];
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 1, 1, i + 1, 0, 1, i + 2, 1, 1);
        }

        // 180도 회전
        for (int i = 0; i < N - 1; i++) {
            temp = grid[i][1] + grid[i + 1][0] + grid[i + 1][1] + grid[i + 1][2];
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 1, 1, i + 1, 0, 2);
        }

        // 270도 회전
        for (int i = 0; i < N - 2; i++) {
            temp = grid[i][0] + grid[i + 1][0] + grid[i + 1][1] + grid[i + 2][0];
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 0, 0, i + 1, 0, 1, i + 2, 0, 0);
        }
    }

    static void fifthShape() {
        // 얘는 회전시킬 필요 없다! ㅎㅎ
        int temp;
        for (int i = 0; i < N - 1; i++) {
            temp = grid[i][0] + grid[i][1] + grid[i + 1][0] + grid[i + 1][1];
            max = Math.max(max, temp);
            slidingWindowR(temp, i, 0, 1, i + 1, 0, 1);
        }
    }

    static void slidingWindowR(int temp, int h, int rS, int rE) {
        // temp라는 변수에서 슬라이딩 윈도우를 적용하는데, h은 첫 번째 i행
        // rS~rE까지 h 행에 있어서 열의 범위
        // 오버로딩
        while (rE < N - 1) {
            temp -= grid[h][rS];
            rS++;
            rE++;
            temp += grid[h][rE];
            max = Math.max(max, temp);
        }
    }

    static void slidingWindowR(int temp, int h1, int r1S, int r1E, int h2, int r2S, int r2E) {
        // temp라는 변수에서 슬라이딩 윈도우를 적용하는데, h1은 첫 번째 i행, h2는 두 번째 i행
        // r1S~r1E까지 h1 행에 있어서 열의 범위, r2S~r2E까지 h2 행에 있어서 열의 범위
        while (r1E < N - 1 && r2E < N - 1) {
            temp -= (grid[h1][r1S] + grid[h2][r2S]);
            r1S++;
            r1E++;
            r2S++;
            r2E++;
            temp += (grid[h1][r1E] + grid[h2][r2E]);
            max = Math.max(max, temp);
        }
    }

    static void slidingWindowR(int temp, int h1, int r1S, int r1E, int h2, int r2S, int r2E, int h3, int r3S, int r3E) {
        while (r1E < N - 1 && r2E < N - 1 && r3E < N - 1) {
            temp -= (grid[h1][r1S] + grid[h2][r2S] + grid[h3][r3S]);
            r1S++;
            r1E++;
            r2S++;
            r2E++;
            r3S++;
            r3E++;
            temp += (grid[h1][r1E] + grid[h2][r2E] + grid[h3][r3E]);
            max = Math.max(max, temp);
        }
    }

    static void slidingWindowH(int temp, int r, int hS, int hE) {
        // temp라는 변수에서 슬라이딩 윈도우를 적용하는데, h은 첫 번째 i행
        // rS~rE까지 h 행에 있어서 열의 범위
        // 오버로딩
        while (hE < N - 1) {
            temp -= grid[hS][r];
            hS++;
            hE++;
            temp += grid[hE][r];
            max = Math.max(max, temp);
        }
    }

/*
    static void debug(int p1, int p2, int p3, int p4) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i * N + j == p1) || (i * N + j == p2) || (i * N + j == p3) || (i * N + j == p4)) {
                    System.out.print("[" + grid[i][j] + "]");
                } else {
                    System.out.print(" " + grid[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
*/
}