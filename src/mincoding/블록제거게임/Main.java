package mincoding.블록제거게임;

import java.io.*;
import java.util.*;

class Main {
    private static BufferedReader br;
    private static Solution_min_블록제거게임 usersolution = new Solution_min_블록제거게임();

    private final static int CMD_INIT = 100;
    private final static int CMD_DROP = 200;
    private final static int CMD_REMOVE = 300;

    private static boolean run() throws Exception {

        StringTokenizer stdin = new StringTokenizer(br.readLine(), " ");

        int query_num = Integer.parseInt(stdin.nextToken());
        int ret, ans;
        boolean ok = false;

        for (int q = 0; q < query_num; q++) {
            stdin = new StringTokenizer(br.readLine(), " ");
            int query = Integer.parseInt(stdin.nextToken());

            if (query == CMD_INIT) {
                int R = Integer.parseInt(stdin.nextToken());
                int C = Integer.parseInt(stdin.nextToken());
                usersolution.init(R, C);
                ok = true;
            } else if (query == CMD_DROP) {
                int mTimestamp = Integer.parseInt(stdin.nextToken());
                int mCol = Integer.parseInt(stdin.nextToken());
                int mLen = Integer.parseInt(stdin.nextToken());
                ans = Integer.parseInt(stdin.nextToken());
                ret = usersolution.dropBlocks(mTimestamp, mCol, mLen);
                if (ans != ret) {
                    ok = false;
                }
            } else if (query == CMD_REMOVE) {
                int mTimestamp = Integer.parseInt(stdin.nextToken());
                ans = Integer.parseInt(stdin.nextToken());
                ret = usersolution.removeBlocks(mTimestamp);
                if (ans != ret) {
                    ok = false;
                }
            }
        }
        return ok;
    }

    public static void main(String[] args) throws Exception {
        int T, MARK;

        // System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");
        T = Integer.parseInt(stinit.nextToken());
        MARK = Integer.parseInt(stinit.nextToken());

        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }

        br.close();
    }
}