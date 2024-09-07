package mincoding.박테리아;

import java.io.*;
import java.util.*;

class Main
{
    private final static int MAX_BCNT		= 100;
    private final static int MAX_NAME		= 10;

    private static char bname[][] = new char [MAX_BCNT][MAX_NAME];
    private static int halftime[] = new int [MAX_BCNT];

    private final static int CMD_INIT		= 0;
    private final static int CMD_ADD		= 1;
    private final static int CMD_OUT		= 2;
    private final static int CMD_CHECK 		= 3;

    private static Solution_min_박테리아 usersolution = new Solution_min_박테리아();

    private static void String2Char(String s, char[] b)
    {
        int n = s.length();
        for (int i = 0; i < n; ++i) b[i] = s.charAt(i);
        for (int i = n; i < MAX_NAME; ++i) b[i] = '\0';
    }

    private static boolean run (BufferedReader br) throws Exception
    {
        int time, life, cnt, cmd, ans, ret;

        int Q = 0;
        boolean okay = false;

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        Q = Integer.parseInt(st.nextToken());

        for (int i = 0; i < Q; ++i)
        {
            st = new StringTokenizer(br.readLine(), " ");
            cmd = Integer.parseInt(st.nextToken());
            switch (cmd)
            {
                case CMD_INIT:
                    cnt = Integer.parseInt(st.nextToken());
                    for (int k = 0; k < cnt; k++) {
                        st = new StringTokenizer(br.readLine(), " ");
                        String2Char(st.nextToken(), bname[k]);
                        halftime[k] = Integer.parseInt(st.nextToken());
                    }
                    usersolution.init(cnt, bname, halftime);
                    okay = true;
                    break;
                case CMD_ADD:
                    time = Integer.parseInt(st.nextToken());
                    String2Char(st.nextToken(), bname[0]);
                    life = Integer.parseInt(st.nextToken());
                    cnt = Integer.parseInt(st.nextToken());
                    usersolution.addBacteria(time, bname[0], life, cnt);
                    break;
                case CMD_OUT:
                    time = Integer.parseInt(st.nextToken());
                    cnt = Integer.parseInt(st.nextToken());
                    ans = Integer.parseInt(st.nextToken());
                    ret = usersolution.takeOut(time, cnt);
/*
                    System.out.println(ans + " : " + ret);
                    System.out.println();
*/
                    if (ans != ret)
                        okay = false;
                    break;
                case CMD_CHECK:
                    time = Integer.parseInt(st.nextToken());
                    String2Char(st.nextToken(), bname[0]);
                    ans = Integer.parseInt(st.nextToken());
                    ret = usersolution.checkBacteria(time, bname[0]);
/*
                    System.out.println(ans + " : " + ret);
                    System.out.println();
*/
                    if (ans != ret)
                        okay = false;
                    break;
                default:
                    okay = false;
            }
        }

        return okay;
    }

    public static void main(String[] args) throws Exception
    {

        System.setIn(new java.io.FileInputStream("res/input_min_pro8.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(br.readLine(), " ");

        int TC = Integer.parseInt(line.nextToken());
        int MARK = Integer.parseInt(line.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase)
        {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        br.close();

    }
}
