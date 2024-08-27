package mincoding.식당의가치;

import java.io.*;
import java.util.*;

class Main {
    private final static int CMD_INIT			= 1;
    private final static int CMD_ADD_RESTAURANT	= 2;
    private final static int CMD_ADD_VALUE		= 3;
    private final static int CMD_BEST_VALUE		= 4;
    private final static int CMD_REGIONAL_VALUE	= 5;

    private final static Solution_min_식당의가치 usersolution = new Solution_min_식당의가치();

    private static void String2Char(char[] buf, String str) {
        for (int k = 0; k < str.length(); ++k)
            buf[k] = str.charAt(k);
    }

    private static int[][] mRoads = new int[50][2];

    private static boolean run(BufferedReader br) throws Exception
    {
        StringTokenizer st;

        int numQuery;
        int N, M, mCityID, mScore, mDist;
        int userAns, ans;
        String name;
        char[] mName;
        char[] mStr;

        boolean isCorrect = false;

        numQuery = Integer.parseInt(br.readLine());

        for (int q = 0; q < numQuery; ++q)
        {
            st = new StringTokenizer(br.readLine(), " ");

            int cmd;
            cmd = Integer.parseInt(st.nextToken());

            switch (cmd)
            {
                case CMD_INIT:
                    N = Integer.parseInt((st.nextToken()));
                    M = Integer.parseInt((st.nextToken()));
                    for (int i = 0; i < M; i++) {
                        mRoads[i][0] = Integer.parseInt((st.nextToken()));
                        mRoads[i][1] = Integer.parseInt((st.nextToken()));
                    }
                    usersolution.init(N, M, mRoads);
                    isCorrect = true;
                    break;
                case CMD_ADD_RESTAURANT:
                    mCityID = Integer.parseInt((st.nextToken()));
                    name = st.nextToken();
                    mName = new char[name.length()];
                    String2Char(mName, name);
                    usersolution.addRestaurant(mCityID, mName);
                    break;
                case CMD_ADD_VALUE:
                    name = st.nextToken();
                    mName = new char[name.length()];
                    String2Char(mName, name);
                    mScore = Integer.parseInt((st.nextToken()));
                    usersolution.addValue(mName, mScore);
                    break;
                case CMD_BEST_VALUE:
                    name = st.nextToken();
                    mStr = new char[name.length()];
                    String2Char(mStr, name);
                    userAns = usersolution.bestValue(mStr);
                    ans = Integer.parseInt((st.nextToken()));
/*
                    System.out.println("userAns: " + userAns);
                    System.out.println("ans = " + ans);
                    System.out.println();
*/
                    if (userAns != ans)
                    {
                        isCorrect = false;
                    }
                    break;
                case CMD_REGIONAL_VALUE:
                    mCityID = Integer.parseInt((st.nextToken()));
                    mDist = Integer.parseInt((st.nextToken()));
                    userAns = usersolution.regionalValue(mCityID, mDist);
                    ans = Integer.parseInt((st.nextToken()));
/*
                    System.out.println("userAns = " + userAns);
                    System.out.println("ans = " + ans);
                    System.out.println();
*/
                    if (userAns != ans)
                    {
                        isCorrect = false;
                    }
                    break;
                default:
                    isCorrect = false;
                    break;
            }
        }
        return isCorrect;
    }

    public static void main(String[] args) throws Exception
    {
        int TC, MARK;

        System.setIn(new java.io.FileInputStream("res/input_min_pro6.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        TC = Integer.parseInt(st.nextToken());
        MARK = Integer.parseInt(st.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase)
        {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        br.close();
    }
}
