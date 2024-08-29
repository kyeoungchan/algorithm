package mincoding.물류허브;

import java.io.*;
import java.util.*;

class Main {
    private final static int CMD_INIT 	= 1;
    private final static int CMD_ADD 	= 2;
    private final static int CMD_COST 	= 3;
    private final static int MAX_N 		= 1400;

    private final static Solution_min_물류허브 usersolution = new Solution_min_물류허브();

    private static boolean run(BufferedReader br) throws Exception {
        StringTokenizer st;
        int Q;
        int N;
        int[] sCityArr = new int[MAX_N];
        int[] eCityArr = new int[MAX_N];
        int[] mCostArr = new int[MAX_N];
        int sCity, eCity, mCost, mHub;
        int cmd, ans, userAns;
        boolean isCorrect = false;

        st = new StringTokenizer(br.readLine());
        Q = Integer.parseInt(st.nextToken());

        for (int q = 0; q <Q; ++q) {
            st = new StringTokenizer(br.readLine());
            cmd = Integer.parseInt(st.nextToken());

            switch(cmd) {
                case CMD_INIT:
                    isCorrect = true;
                    N = Integer.parseInt(st.nextToken());
                    for(int i = 0; i < N; i++) {
                        st = new StringTokenizer(br.readLine());
                        sCityArr[i] = Integer.parseInt(st.nextToken());
                        eCityArr[i] = Integer.parseInt(st.nextToken());
                        mCostArr[i] = Integer.parseInt(st.nextToken());
                    }
                    st = new StringTokenizer(br.readLine());
                    ans = Integer.parseInt(st.nextToken());
                    userAns = usersolution.init(N, sCityArr, eCityArr, mCostArr);
                    if(userAns != ans)
                        isCorrect = false;
                    break;
                case CMD_ADD:
                    sCity = Integer.parseInt(st.nextToken());
                    eCity = Integer.parseInt(st.nextToken());
                    mCost= Integer.parseInt(st.nextToken());
                    usersolution.add(sCity, eCity, mCost);
                    break;
                case CMD_COST:
                    mHub = Integer.parseInt(st.nextToken());
                    ans = Integer.parseInt(st.nextToken());
                    userAns = usersolution.cost(mHub);
                    if(userAns != ans)
                        isCorrect = false;
                    break;
                default:
                    isCorrect = false;
                    break;
            }
        }
        return isCorrect;
    }

    public static void main(String[] args) throws Exception {
        int TC, MARK;

        System.setIn(new java.io.FileInputStream("res/input_min_pro9.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        TC = Integer.parseInt(st.nextToken());
        MARK = Integer.parseInt(st.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase) {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }
        br.close();
    }
}
