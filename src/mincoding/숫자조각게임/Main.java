package mincoding.숫자조각게임;

import java.io.*;
import java.util.*;

class Main {
    private static final int CMD_INIT = 1;
    private static final int CMD_PUT = 2;
    private static final int CMD_CLR = 3;

    private static Solution_pro_숫자조각게임 userSolution = new Solution_pro_숫자조각게임();
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    public static final class Result {
        int row;
        int col;
        Result() {
            this.row = -1;
            this.col = -1;
        }
    }

    private static boolean run(BufferedReader br) throws Exception {

        int query_num;
        st = new StringTokenizer(br.readLine(), " ");
        query_num = Integer.parseInt(st.nextToken());
        boolean ok = false;

        for (int q = 0; q < query_num; ++q) {

            st = new StringTokenizer(br.readLine(), " ");
            int query = Integer.parseInt(st.nextToken());

            switch (query) {

                case CMD_INIT:
                    int mRows = Integer.parseInt(st.nextToken());
                    int mCols = Integer.parseInt(st.nextToken());
                    int[][] mCells = new int[mRows][mCols];
                    for(int i = 0; i < mRows; i++) {
                        st = new StringTokenizer(br.readLine());
                        for(int j = 0; j < mCols; j++) {
                            mCells[i][j] = Integer.parseInt(st.nextToken());
                        }
                    }
                    userSolution.init(mRows, mCols, mCells);
                    ok = true;
                    break;
                case CMD_PUT:
                    String strPuzzle;
                    int[][]mPuzzle = new int[3][3];
                    strPuzzle = st.nextToken();
                    int cnt = 0;
                    for(int i = 0; i < 3; i++) {
                        for(int j = 0; j < 3; j++) {
                            mPuzzle[i][j] = strPuzzle.charAt(cnt) - '0';
                            cnt++;
                        }
                    }
                    Result ret = userSolution.putPuzzle(mPuzzle);
                    int ans_row = Integer.parseInt(st.nextToken());
                    int ans_col = Integer.parseInt(st.nextToken());
                    if(ans_row != ret.row || ans_col != ret.col)
                        ok = false;
                    break;
                case CMD_CLR:
                    userSolution.clearPuzzles();
                    break;
                default:
                    ok = false;
                    break;
            }
        }
        return ok;
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new java.io.FileInputStream("res/input_min_pro1.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");

        int TC = Integer.parseInt(stinit.nextToken());
        int MARK = Integer.parseInt(stinit.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase) {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }
        br.close();
    }
}
