package mincoding.비용과시간;

import java.io.*;
import java.util.*;

class Main {
    private static final int CMD_INIT 	= 100;
    private static final int CMD_ADD 	= 200;
    private static final int CMD_COST 	= 300;

    private static Solution_min_비용과시간 userSolution = new Solution_min_비용과시간();

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    private static boolean run(BufferedReader br) throws Exception {

        int Q;
        int n, k, m;
        int[] sCityArr, eCityArr, mCostArr, mTimeArr;
        int sCity, eCity, mCost, mTime;
        int cmd, ans, ret;
        boolean okay = false;

        st = new StringTokenizer(br.readLine(), " ");
        Q = Integer.parseInt(st.nextToken());

        for (int q = 0; q < Q; ++q) {

            st = new StringTokenizer(br.readLine(), " ");
            cmd = Integer.parseInt(st.nextToken());

            switch(cmd) {
                case CMD_INIT:
                    n = Integer.parseInt(st.nextToken());
                    k = Integer.parseInt(st.nextToken());
                    sCityArr = new int[k];
                    eCityArr = new int[k];
                    mCostArr = new int[k];
                    mTimeArr = new int[k];
                    for(int i = 0; i < k; i++) {
                        st = new StringTokenizer(br.readLine());
                        sCityArr[i] = Integer.parseInt(st.nextToken());
                        eCityArr[i] = Integer.parseInt(st.nextToken());
                        mCostArr[i] = Integer.parseInt(st.nextToken());
                        mTimeArr[i] = Integer.parseInt(st.nextToken());
                    }
                    userSolution.init(n, k, sCityArr, eCityArr, mCostArr, mTimeArr);
                    okay = true;
                    break;

                case CMD_ADD:
                    sCity = Integer.parseInt(st.nextToken());
                    eCity = Integer.parseInt(st.nextToken());
                    mCost = Integer.parseInt(st.nextToken());
                    mTime = Integer.parseInt(st.nextToken());
                    userSolution.add(sCity, eCity, mCost, mTime);
                    break;

                case CMD_COST:
                    m = Integer.parseInt(st.nextToken());
                    sCity = Integer.parseInt(st.nextToken());
                    eCity = Integer.parseInt(st.nextToken());
                    ans = Integer.parseInt(st.nextToken());
                    ret = userSolution.cost(m, sCity, eCity);
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
         System.setIn(new java.io.FileInputStream("res/input_min_pro10.txt"));

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