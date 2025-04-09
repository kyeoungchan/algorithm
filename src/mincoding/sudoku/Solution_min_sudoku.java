package mincoding.sudoku;

import java.io.*;
import java.util.*;

public class Solution_min_sudoku {
    static final int N = 9;
    static int[][] grid; // 스도쿠 판
    static List<int[]> empty; // 공백 공간이 어디에 있는지를 찾아주는 좌표 정보 저장
    static boolean[] numbersCheck; // 숫자들이 1~9까지 한번씩만 들어갔는지 체크하기 위한 배열
    static boolean complete;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        grid = new int[N][N];
        empty = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
                if (grid[i][j] == 0) {
                    empty.add(new int[]{i, j});
                }
            }
        }

        setNumbers(0, 1);
        printGrid();
        br.close();
    }

    private static void setNumbers(int idx, int number) {
        if (idx == empty.size()) {
            complete = true;
            return;
        }

        int r = empty.get(idx)[0];
        int c = empty.get(idx)[1];
        for (int i = number; i < 10; i++) {
            grid[r][c] = i;
            if (!checkRow(r) || !checkCol(c) || !checkGroup(r, c)) {
                grid[r][c] = 0;
                continue;
            }
            setNumbers(idx + 1, 1);
            if (complete) {
                return;
            }
            grid[r][c] = 0;
        }
    }

    private static boolean checkRow(int r) {
        numbersCheck = new boolean[N + 1];
        for (int c = 0; c < N; c++) {
            int value = grid[r][c];
            if (value == 0) continue; // 0으로 세팅돼있는 경우는 일단 맞다고 친다.
            if (numbersCheck[value]) {
                return false;
            }
            numbersCheck[value] = true;
        }
        return true;
    }

    private static boolean checkCol(int c) {
        numbersCheck = new boolean[N + 1];
        for (int r = 0; r < N; r++) {
            int value = grid[r][c];
            if (value == 0) continue;
            if (numbersCheck[value]) {
                return false;
            }
            numbersCheck[value] = true;
        }
        return true;
    }

    private static boolean checkGroup(int r, int c) {
        // r=0~2 / 3~5 / 6~8
        // c=0~2 / 3~5 / 6~8
        numbersCheck = new boolean[N + 1];
        int startR = (r / 3) * 3;
        int startC = (c / 3) * 3;
        for (int groupR = startR; groupR < startR + 3; groupR++) {
            for (int groupC = startC; groupC < startC + 3; groupC++) {
                int value = grid[groupR][groupC];
                if (value == 0) continue;
                if (numbersCheck[value]) {
                    return false;
                }
                numbersCheck[value] = true;
            }
        }
        return true;
    }
    private static void printGrid() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(grid[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}
