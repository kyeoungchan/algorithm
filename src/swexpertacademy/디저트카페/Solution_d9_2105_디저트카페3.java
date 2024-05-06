package swexpertacademy.디저트카페;

import java.io.*;
import java.util.*;

/**
 * 디저트카페_풀고만다 코드와 거의 동일함
 */
public class Solution_d9_2105_디저트카페3 {

    static int N, maxKind;
    static int[] di = {1, 1, -1, -1}, dj = {1, -1, -1, 1};
    static int[][] cafes;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_2105.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            cafes = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    cafes[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            maxKind = 0;
            for (int i = 0; i < N - 2; i++) {
                for (int j = 1; j < N - 1; j++) {
                    checkPath(i, j);
                }
            }
            if (maxKind == 0) maxKind--;
            sb.append("#").append(tc).append(" ").append(maxKind).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void checkPath(int startI, int startJ) {
        // startI + x <= N - 2
        // startJ + x <= N - 1
        int maxA = Math.min(N - 2 - startI, N - 1 - startJ);
        for (int a = 1; a <= maxA; a++) {
            int secondI = startI + di[0] * a;
            int secondJ = startJ + dj[0] * a;
            // secondI + b <= N - 1
            // secondJ - b >= a
            int maxB = Math.min(N - 1 - secondI, secondJ - a);
            for (int b = 1; b <= maxB; b++) {
                go(startI, startJ, a, b);
            }
        }
    }

    static void go(int startI, int startJ, int a, int b) {
        List<Integer> desserts = new ArrayList<>();
        int r = startI;
        int c = startJ;
        for (int d = 0; d < 4; d++) {
            if (d % 2 == 0) {
                for (int i = 0; i < a; i++) {
                    r += di[d];
                    c += dj[d];
                    if (desserts.contains(cafes[r][c])) return;
                    desserts.add(cafes[r][c]);
//                    debug(r, c, d);
                }
            } else {
                for (int i = 0; i < b; i++) {
                    r += di[d];
                    c += dj[d];
                    if (desserts.contains(cafes[r][c])) return;
                    desserts.add(cafes[r][c]);
//                    debug(r, c, d);
                }
            }
        }
        maxKind = Math.max(maxKind, desserts.size());
    }

    static void debug(int r, int c, int d) {
        System.out.println("d = " + d);
        System.out.println("di[d] = " + di[d]);
        System.out.println("dj[d] = " + dj[d]);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == r && j == c)
                    System.out.print("[" + cafes[i][j] + "]");
                else
                    System.out.print(" " + cafes[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
