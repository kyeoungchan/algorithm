package baekjoon.색종이_3;

import java.util.*;
import java.io.*;

/**
 * 가로 세로 크기가 100인 정사각형 흰색 도화지
 * 색종이는 크기가 각각 10
 * 검은색 직사각형의 최대 넓이를 구하라
 */
public class Solution_bj_2571_색종이3 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[100][100];
        int startI = 101;
        int startJ = 101;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            for (int dy = 0; dy < 10; dy++) {
                for (int dx = 0; dx < 10; dx++) {
                    map[y + dy][x + dx] = 1;
                    if (y < startI) {
                        startI = y;
                        startJ = x;
                    }
                }
            }
        }


        br.close();
    }
}
