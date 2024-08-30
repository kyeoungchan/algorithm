package mincoding.연락처DB;

import java.io.*;
import java.util.*;

class Main {
    private final static int CMD_INIT 	= 100;
    private final static int CMD_ADD 	= 200;
    private final static int CMD_REMOVE = 300;
    private final static int CMD_CALL 	= 400;
    private final static int CMD_SEARCH = 500;

    private final static int MAX_N = 5;

    private final static Solution_min_연락처DB usersolution = new Solution_min_연락처DB();

    public static class Result {
        String[] mNameList;
        String[] mTelephoneList;
        int size;

        public Result() {
            this.mNameList = new String[MAX_N];
            this.mTelephoneList = new String[MAX_N];
            this.size = 0;
        }
    }

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    private static boolean run(BufferedReader br) throws Exception {
        int qcnt = Integer.parseInt(br.readLine());

        int cmd, ans;
        Result ret = new Result();
        String mStr;
        boolean okay = true;

        for (int q = 0; q < qcnt; ++q) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            cmd = Integer.parseInt(st.nextToken());
            switch (cmd) {
                case CMD_INIT:
                    usersolution.init();
                    okay = true;
                    break;
                case CMD_ADD:
                    String mName = st.nextToken();
                    String mTelephone = st.nextToken();
                    usersolution.add(mName, mTelephone);
                    okay = true;
                    break;
                case CMD_REMOVE:
                    mStr = st.nextToken();
                    usersolution.remove(mStr);
                    okay = true;
                    break;
                case CMD_CALL:
                    mStr = st.nextToken();
                    usersolution.call(mStr);
                    okay = true;
                    break;
                case CMD_SEARCH:
                    mStr = st.nextToken();
                    ans = Integer.parseInt(st.nextToken());
                    ret = usersolution.search(mStr);
                    if (ret.size != ans) {
                    System.out.println(ans + " : " + ret.size);
                        okay = false;
                    }
                    for (int i = 0; i < ans; ++i) {
                        mStr = st.nextToken();
                        if (mStr.charAt(0) != '$' && !mStr.equals(ret.mNameList[i])) {
                            okay = false;
                        System.out.println(mStr + " : " + ret.mNameList[i]);
                        }
                        mStr = st.nextToken();
                        if (!mStr.equals(ret.mTelephoneList[i])) {
                            okay = false;
                        System.out.println(mStr + " : " + ret.mTelephoneList[i]);
                        }
                    }
                    if (!okay) System.out.println();
                    break;
                default:
                    okay = false;
                    break;
            }
        }
        return okay;
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_min_pro11.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        int T, MARK;

        st = new StringTokenizer(br.readLine());
        T = Integer.parseInt(st.nextToken());
        MARK = Integer.parseInt(st.nextToken());

        for (int tc = 1; tc <= T; tc++) {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }
        br.close();
    }
}