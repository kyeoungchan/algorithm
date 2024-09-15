package mincoding.쏟아지는별자리타일;

import java.io.*;
import java.util.*;

class Main {

    private final static int CMD_INIT 		= 0;
    private final static int CMD_CNT    	= 1;
    private final static int CMD_POSITION 	= 2;

    private final static Solution_min_쏟아지는별자리타일 usersolution = new Solution_min_쏟아지는별자리타일();

    private final static int[][] Map = new int[1000][1000];
    private final static int[][] Piece = new int[5][5];
    private final static int[] Data = new int[40000];

    private static void init_map(int N) {
        int idx = 0;

        int x = 0;
        for (int i = 0; i < (N / 25); i++) {
            for (int y = 0; y < N; y++) {
                int data = Data[idx++];
                int bit = 1;
                for (int m = 0; m < 25; m++) {
                    if ((data & bit) != 0) Map[y][x + m] = 1;
                    else Map[y][x + m] = 0;
                    bit <<= 1;
                }
            }
            x += 25;
        }

        int dcnt = N % 25;
        if (dcnt != 0) {
            for (int y = 0; y < N; y++) {
                int data = Data[idx++];
                int bit = 1;
                for (int m = 0; m < dcnt; m++) {
                    if ((data & bit) != 0) Map[y][x + m] = 1;
                    else Map[y][x + m] = 0;
                    bit <<= 1;
                }
            }
        }
    }

    private static void make_piece(int data) {
        int bit = 1;
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                if ((data & bit) != 0) Piece[i][k] = 1;
                else Piece[i][k] = 0;
                bit <<= 1;
            }
        }
    }

    private static boolean run(BufferedReader br) throws Exception {

        StringTokenizer st;
        boolean okay = false;
        int Q, N, row, col, cnt;
        int ret, ans;

        Q = Integer.parseInt(br.readLine());

        for (int q = 0; q < Q; ++q) {

            st = new StringTokenizer(br.readLine(), " ");
            int cmd = Integer.parseInt(st.nextToken());
            switch (cmd) {

                case CMD_INIT:
                    N = Integer.parseInt(st.nextToken());
                    cnt = Integer.parseInt(st.nextToken());
                    for(int i = 0; i < cnt; i++) {
                        Data[i] = Integer.parseInt(br.readLine());
                    }
                    init_map(N);
                    usersolution.init(N, Map);
                    okay = true;
                    break;

                case CMD_CNT:
                    Data[0] = Integer.parseInt(st.nextToken());
                    make_piece(Data[0]);
                    ret = usersolution.getCount(Piece);
                    ans = Integer.parseInt(st.nextToken());
                    if(ans != ret)
                        okay = false;
                    break;

                case CMD_POSITION:
                    row = Integer.parseInt(st.nextToken());
                    col = Integer.parseInt(st.nextToken());
                    ret = usersolution.getPosition(row, col);
                    ans = Integer.parseInt(st.nextToken());
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

        int TC, MARK;
        System.setIn(new java.io.FileInputStream("res/input_min_pro12.txt"));

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
