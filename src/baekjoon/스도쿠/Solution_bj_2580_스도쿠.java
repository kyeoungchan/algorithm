package baekjoon.스도쿠;

import java.io.*;
import java.util.*;

public class Solution_bj_2580_스도쿠 {

    static List<int[]> poses;
    static int[][] grid;
    static boolean complete;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        grid = new int[9][9];
        poses = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
                if (grid[i][j] == 0) poses.add(new int[]{i, j});
            }
        }

        complete = false;
        setNumber(0);
        printGrid();
        br.close();
    }

    private static void printGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void setNumber(int cnt) {
        if (cnt == poses.size()) {
            complete = true;
            return;
        }

        int r = poses.get(cnt)[0];
        int c = poses.get(cnt)[1];
        for (int i = 1; i < 10; i++) {
            grid[r][c] = i;
            if (checkRow(r) && checkCol(c) && checkGroup(r, c)) {
//                printGrid();
                setNumber(cnt + 1);
                if (complete) return;
            }
        }
        // 이 부분을 조심하자! 백트래킹을 하는 경우 원점으로 돌려놔야한다!
        grid[r][c] = 0;
    }

    static boolean checkRow(int r) {
        boolean[] numberCheck = new boolean[10];
        for (int c = 0; c < 9; c++) {
            int number = grid[r][c];
            if (number == 0) continue;
            if (numberCheck[number]) return false;
            numberCheck[number] = true;
        }
        return true;
    }

    static boolean checkCol(int c) {
        boolean[] numberCheck = new boolean[10];
        for (int r = 0; r < 9; r++) {
            int number = grid[r][c];
            if (number == 0) continue;
            if (numberCheck[number]) return false;
            numberCheck[number] = true;
        }
        return true;
    }

    static boolean checkGroup(int r, int c) {
        // 0, 1, 2 // 3, 4, 5 // 6, 7, 8
        int groupR = r / 3 * 3;
        int groupC = c / 3 * 3;
        boolean[] numberCheck = new boolean[10];
        for (int i = groupR; i < groupR + 3; i++) {
            for (int j = groupC; j < groupC + 3; j++) {
                int number = grid[i][j];
                if (number == 0) continue;
                if (numberCheck[number]) return false;
                numberCheck[number] = true;
            }
        }
        return true;
    }
}
