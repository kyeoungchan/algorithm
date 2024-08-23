package mincoding.동물애호가;

import java.io.*;
import java.util.*;

class Main {

    private final static int CMD_INIT		= 100;
    private final static int CMD_ADOPT		= 200;
    private final static int CMD_GET 		= 300;
    private final static int CMD_VACCINATE  = 400;

    private static boolean run(BufferedReader br) throws IOException {

        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        int Q = Integer.parseInt(st.nextToken());

        boolean isCorrect = true;
        int cmd, time, id, life;
        int ans, userAns;

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            cmd = Integer.parseInt(st.nextToken());
            switch(cmd)
            {
                case CMD_INIT:
                    isCorrect = true;
                    usersolution.init();
                    break;

                case CMD_ADOPT :
                    time = Integer.parseInt(st.nextToken());
                    id = Integer.parseInt(st.nextToken());
                    life = Integer.parseInt(st.nextToken());
                    userAns = usersolution.adopt(time, id, life);
                    ans = Integer.parseInt(st.nextToken());
                    if(userAns != ans) {
                        isCorrect = false;
                    }
                    break;

                case CMD_GET :
                    time = Integer.parseInt(st.nextToken());
                    id = Integer.parseInt(st.nextToken());
                    userAns = usersolution.getLife(time, id);
                    ans = Integer.parseInt(st.nextToken());
                    if(userAns != ans) {
                        isCorrect = false;
                    }
                    break;

                case CMD_VACCINATE :
                    time = Integer.parseInt(st.nextToken());
                    id = Integer.parseInt(st.nextToken());
                    life = Integer.parseInt(st.nextToken());
                    userAns = usersolution.vaccinate(time, id, life);
                    ans = Integer.parseInt(st.nextToken());
                    if(userAns != ans) {
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

    private final static Solution_min_동물애호가 usersolution = new Solution_min_동물애호가();

    public static void main(String[] args) throws Exception {

         System.setIn(new java.io.FileInputStream("res/input_min_pro3.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int TC = Integer.parseInt(st.nextToken());
        int MARK = Integer.parseInt(st.nextToken());

        for (int testcase = 1; testcase <= TC; testcase++) {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        br.close();
    }
}