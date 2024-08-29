package mincoding.세균번식;

import java.io.*;
import java.util.*;

public class Main {
    private final static int CMD_INIT 	= 100;
    private final static int CMD_DROP 	= 200;
    private final static int CMD_CLEAN	= 300;
    private final static int MAX_N = 100;

    private final static Solution_min_세균번식 usersolution = new Solution_min_세균번식();
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static boolean run() throws Exception {

        StringTokenizer st;

        int Q;
        st = new StringTokenizer(br.readLine());
        Q = Integer.parseInt(st.nextToken());

        int userAns, ans;
        int N, R, C;
        int targetType, totalEnergy;
        boolean isCorrect = false;
        int[][] dish = new int[MAX_N][MAX_N];
        int cmd;

        for (int q = 0; q < Q; ++q) {
            st = new StringTokenizer(br.readLine());
            cmd = Integer.parseInt(st.nextToken());

            switch (cmd) {
                case CMD_INIT:
                    N = Integer.parseInt(st.nextToken());
                    for(int i = 0; i < N; i++) {
                        st = new StringTokenizer(br.readLine());
                        for(int j = 0; j < N; j++)
                            dish[i][j] = Integer.parseInt(st.nextToken());
                    }
                    usersolution.init(N, dish);
                    isCorrect = true;
                    break;
                case CMD_DROP:
                    targetType = Integer.parseInt(st.nextToken());
                    R = Integer.parseInt(st.nextToken());
                    C = Integer.parseInt(st.nextToken());
                    totalEnergy = Integer.parseInt(st.nextToken());
                    userAns = usersolution.dropMedicine(targetType, R, C, totalEnergy);
                    ans = Integer.parseInt(st.nextToken());
                    if(userAns != ans)
                        isCorrect = false;
                    break;
                case CMD_CLEAN:
                    R = Integer.parseInt(st.nextToken());
                    C = Integer.parseInt(st.nextToken());
                    userAns = usersolution.cleanBacteria(R, C);
                    ans = Integer.parseInt(st.nextToken());
                    if (userAns != ans)
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

         System.setIn(new java.io.FileInputStream("res/input_min_pro7.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

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