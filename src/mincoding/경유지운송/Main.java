package mincoding.경유지운송;

import java.io.*;
import java.util.*;

class Main {
    private static final int CMD_INIT 	= 100;
    private static final int CMD_ADD 	= 200;
    private static final int CMD_CALC 	= 300;

    private static Solution_min_경유지운송_Param userSolution = new Solution_min_경유지운송_Param();
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static int MAX_E = 2000;
    private static int MAX_S = 3;

    private static boolean run(BufferedReader br) throws Exception {

        int q;
        st = new StringTokenizer(br.readLine(), " ");
        q = Integer.parseInt(st.nextToken());

        int n, m, k;
        String strTmp;
        int[] sCityArr = new int[MAX_E];
        int[] eCityArr = new int[MAX_E];
        int[] mLimitArr = new int[MAX_E];
        int[] mStopover = new int[MAX_S];
        int sCity, eCity, mLimit;
        int cmd, ans, ret;
        boolean okay = false;


        for (int i = 0; i < q; ++i) {

            st = new StringTokenizer(br.readLine(), " ");
            int query = Integer.parseInt(st.nextToken());
            strTmp = st.nextToken();

            System.out.println(i + 1 + "!!!!");
            switch (query) {

                case CMD_INIT:
                    okay = true;
                    st = new StringTokenizer(br.readLine(), " ");
                    strTmp = st.nextToken();
                    n = Integer.parseInt(st.nextToken());
                    strTmp = st.nextToken();
                    k = Integer.parseInt(st.nextToken());
                    for(int j = 0; j < k; ++j) {
                        st = new StringTokenizer(br.readLine(), " ");
                        strTmp = st.nextToken();
                        sCityArr[j] = Integer.parseInt(st.nextToken());
                        strTmp = st.nextToken();
                        eCityArr[j] = Integer.parseInt(st.nextToken());
                        strTmp = st.nextToken();
                        mLimitArr[j] = Integer.parseInt(st.nextToken());
                    }
                    userSolution.init(n,  k, sCityArr, eCityArr, mLimitArr);
                    break;
                case CMD_ADD:
                    st = new StringTokenizer(br.readLine(), " ");
                    strTmp = st.nextToken();
                    sCity = Integer.parseInt(st.nextToken());
                    strTmp = st.nextToken();
                    eCity = Integer.parseInt(st.nextToken());
                    strTmp = st.nextToken();
                    mLimit = Integer.parseInt(st.nextToken());
                    userSolution.add(sCity, eCity, mLimit);
                    break;
                case CMD_CALC:
                    st = new StringTokenizer(br.readLine(), " ");
                    strTmp = st.nextToken();
                    sCity = Integer.parseInt(st.nextToken());
                    strTmp = st.nextToken();
                    eCity = Integer.parseInt(st.nextToken());
                    strTmp = st.nextToken();
                    m = Integer.parseInt(st.nextToken());
                    for(int j = 0; j < m; j++) {
                        st = new StringTokenizer(br.readLine(), " ");
                        strTmp = st.nextToken();
                        mStopover[j] = Integer.parseInt(st.nextToken());
                    }
                    st = new StringTokenizer(br.readLine(), " ");
                    strTmp = st.nextToken();
                    ans = Integer.parseInt(st.nextToken());
                    ret = userSolution.calculate(sCity, eCity, m, mStopover);
                    System.out.println("ans = " + ans);
                    System.out.println("ret = " + ret);
                    System.out.println();
                    if(ans != ret)
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
         System.setIn(new java.io.FileInputStream("res/input_min_pro경유지운송.txt"));

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
