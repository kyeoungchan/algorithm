package mincoding.전자사전;

import java.io.*;
import java.util.*;

class Main {

    private static final int CMD_INIT 		= 100;
    private static final int CMD_ADD 		= 200;
    private static final int CMD_ERASE	 	= 300;
    private static final int CMD_FIND 		= 400;
    private static final int CMD_GET_INDEX 	= 500;

    private static UserSolution userSolution = new UserSolution();
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    public static final class Result {
        int success;
        String word;

        Result() {
            this.success = 0;
            this.word = "";
        }
    }

    private static boolean run(BufferedReader br) throws Exception {

        int Q, N, mIndex;
        int ret = -1;
        int ans;
        Main.Result res;
        String mWord;
        String[] mWordList;
        boolean okay = false;

        st = new StringTokenizer(br.readLine());
        Q = Integer.parseInt(st.nextToken());

        for (int q = 0; q < Q; ++q) {

            st = new StringTokenizer(br.readLine(), " ");
            int cmd = Integer.parseInt(st.nextToken());

            switch (cmd) {

                case CMD_INIT:
                    N = Integer.parseInt(st.nextToken());
                    mWordList = new String[N];
                    int n = N;
                    int idx = 0;
                    while(n > 0) {
                        st = new StringTokenizer(br.readLine());
                        for(int i = idx; i < Math.min(idx + n, idx + 10); i++) {
                            mWordList[i] = st.nextToken();
                        }
                        idx += 10;
                        n -= 10;
                    }
                    int de = 1;
                    userSolution.init(N, mWordList);
                    okay = true;
                    break;

                case CMD_ADD:
                    mWord = st.nextToken();
                    ret = userSolution.add(mWord);
                    ans = Integer.parseInt(st.nextToken());
                    if(ret != ans)
                        okay = false;
//                    System.out.println(ans + " : " + ret);
                    break;

                case CMD_ERASE:
                    mWord = st.nextToken();
                    ret = userSolution.erase(mWord);
                    ans = Integer.parseInt(st.nextToken());
                    if(ret != ans)
                        okay = false;
//                    System.out.println(ans + " : " + ret);
                    break;

                case CMD_FIND:
                    mWord = st.nextToken();
                    mIndex = Integer.parseInt(st.nextToken());
                    res = userSolution.find(mWord.charAt(0), mIndex);
                    ans = Integer.parseInt(st.nextToken());
                    if(res.success != ans)
                        okay = false;
/*
                    System.out.println("res.success");
                    System.out.println(ans + " : " + res.success);
*/
                    if(ans == 1) {
//                        System.out.println("res.word");
                        mWord = st.nextToken();
                        if(!(res.word.equals(mWord)))
                            okay = false;
//                        System.out.println(mWord + " : " + res.word);
                    }
                    break;

                case CMD_GET_INDEX:
                    mWord = st.nextToken();
                    ret = userSolution.getIndex(mWord);
                    ans = Integer.parseInt(st.nextToken());
                    if(ret != ans)
                        okay = false;
//                    System.out.println(ans + " : " + ret);
                    break;

                default:
                    okay = false;
                    break;
            }
        }
        return okay;
    }

    public static void main(String[] args) throws Exception {
         System.setIn(new java.io.FileInputStream("res/input_min_pro15.txt"));

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
