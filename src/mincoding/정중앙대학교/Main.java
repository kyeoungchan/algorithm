package mincoding.정중앙대학교;

import java.io.*;
import java.util.*;

class Main {
    private final static int CMD_INIT 		= 100;
    private final static int CMD_ENROLL		= 200;
    private final static int CMD_GET 		= 300;

    private final static Solution_min_정중앙대학교 usersolution = new Solution_min_정중앙대학교();
    private final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static boolean run() throws Exception {

        StringTokenizer st;

        int cmd, ans, ret;
        int score1, score2;
        boolean okay = false;
        String strTmp;

        st = new StringTokenizer(br.readLine());
        int q = Integer.parseInt(st.nextToken());

        for (int i = 0; i < q; ++i) {
            st = new StringTokenizer(br.readLine());
            cmd = Integer.parseInt(st.nextToken());

            switch (cmd) {
                case CMD_INIT:
                    score1 = Integer.parseInt(st.nextToken());
                    usersolution.init(score1);
                    okay = true;
                    break;
                case CMD_ENROLL :
                    score1 = Integer.parseInt(st.nextToken());
                    score2 = Integer.parseInt(st.nextToken());
                    usersolution.enroll(score1, score2);
                    break;
                case CMD_GET:
                    ret = usersolution.get();
                    ans = Integer.parseInt(st.nextToken());
                    if(ret != ans)
                        okay = false;
                    break;
                default:
                    okay = false;
                    break;
            }
        }
        return okay;
    }

    public static void main(String[] args) throws Exception {
        int TC, MARK;
        System.setIn(new FileInputStream("res/input_min_정중앙대학교.txt"));

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        TC = Integer.parseInt(st.nextToken());
        MARK = Integer.parseInt(st.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase) {
            int score = run() ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }
        br.close();
    }
}
