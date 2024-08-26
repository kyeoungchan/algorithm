package mincoding.전자상가;

import java.io.*;
import java.util.*;

class Main
{
    private static final int CMD_INIT				= 0;
    private static final int CMD_STOCK				= 1;
    private static final int CMD_ORDER				= 2;

    public static final class Result
    {
        int mPrice;
        int mPerformance;

        Result() {
            mPrice = 0;
            mPerformance = 0;
        }
    }

    private static Solution_min_전자상가 usersolution = new Solution_min_전자상가();
    static  int tc;

    private static boolean run(BufferedReader br) throws Exception
    {
        int Q;

        StringTokenizer stdin = new StringTokenizer(br.readLine(), " ");

        Q = Integer.parseInt(stdin.nextToken());

        boolean okay = false;

        for (int q = 0; q < Q; ++q)
        {
            stdin = new StringTokenizer(br.readLine(), " ");
            int cmd = Integer.parseInt(stdin.nextToken());
            int ret, ans, ans2, in, in2, in3, in4;
            Result res;

            switch(cmd)
            {
                case CMD_INIT:
                    in = Integer.parseInt(stdin.nextToken());
                    usersolution.init(in);
                    okay = true;
                    break;
                case CMD_STOCK:
                    in = Integer.parseInt(stdin.nextToken());
                    in2 = Integer.parseInt(stdin.nextToken());
                    in3 = Integer.parseInt(stdin.nextToken());
                    in4 = Integer.parseInt(stdin.nextToken());
                    ret = usersolution.stock(in, in2, in3, in4);
                    ans = Integer.parseInt(stdin.nextToken());
                    if (ret != ans)
                        okay = false;
                    break;
                case CMD_ORDER:
                    in = Integer.parseInt(stdin.nextToken());
                    res = usersolution.order(in);
                    ans = Integer.parseInt(stdin.nextToken());
                    ans2 = Integer.parseInt(stdin.nextToken());
                    if (res.mPrice != ans || res.mPerformance != ans2)
                        okay = false;
                    break;
                default:
                    okay = false;
                    break;
            }

        }

        return okay;
    }

    public static void main(String[] args) throws Exception
    {
         System.setIn(new java.io.FileInputStream("res/input_min_pro5.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");

        int TC = Integer.parseInt(stinit.nextToken());
        int MARK = Integer.parseInt(stinit.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase)
        {
            tc = testcase;
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        br.close();
    }
}