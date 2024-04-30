package baekjoon.감시;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution_bj_15683_감시_다른사람꺼2 {

    static int[] di = { -1, 0, 1, 0 };
    static int[] dj = { 0, 1, 0, -1 };
    static int N, M, zeroCnt, cctvCnt;
    static int[][] map;
    static int minDeadZone;
    static List<Point> list;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        list = new ArrayList<>();

        zeroCnt = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int tmp = Integer.parseInt(st.nextToken());

                map[i][j] = tmp;
                if (tmp >= 1 && tmp <= 5) {
                    list.add(new Point(i, j, tmp));
                } else if (tmp == 0) {
                    zeroCnt++;
                }
            }
        }

        cctvCnt = list.size();

        minDeadZone = Integer.MAX_VALUE;
        search(0, 0);
        System.out.println(minDeadZone);
    }

    static void search(int depth, int coverage) {

        if (depth == cctvCnt) {
            minDeadZone = Math.min(minDeadZone, zeroCnt - coverage);
        } else {
            Point p = list.get(depth);
            int cc;

            if (p.type == 1) {

                for (int d = 0; d < 4; d++) {
                    cc = go(p, d, depth);
                    search(depth + 1, coverage + cc);
                    ungo(p, d, depth);
                }

            } else if (p.type == 2) {

                for (int d = 0; d < 2; d++) {
                    cc = go(p, d, depth) + go(p, d + 2, depth);
                    search(depth + 1, coverage + cc);
                    ungo(p, d, depth);
                    ungo(p, d + 2, depth);
                }

            } else if (p.type == 3) {

                for (int d = 0; d < 4; d++) {
                    cc = go(p, d, depth) + go(p, (d + 1) % 4, depth);
                    search(depth + 1, coverage + cc);
                    ungo(p, d, depth);
                    ungo(p, (d + 1) % 4, depth);
                }

            } else if (p.type == 4) {

                for (int d = 0; d < 4; d++) {
                    cc = go(p, d, depth) + go(p, (d + 1) % 4, depth) + go(p, (d + 2) % 4, depth);
                    search(depth + 1, coverage + cc);
                    ungo(p, d, depth);
                    ungo(p, (d + 1) % 4, depth);
                    ungo(p, (d + 2) % 4, depth);
                }

            } else if (p.type == 5) {

                {
                    cc = go(p, 0, depth) + go(p, 1, depth) + go(p, 2, depth) + go(p, 3, depth);
                    search(depth + 1, coverage + cc);
                    ungo(p, 0, depth);
                    ungo(p, 1, depth);
                    ungo(p, 2, depth);
                    ungo(p, 3, depth);
                }
            }
        }
    }

    static class Point {
        int i;
        int j;
        int type;

        Point(int i, int j, int type) {
            this.i = i;
            this.j = j;
            this.type = type;
        }
    }

    static int go(Point p, int d, int depth) {
        int ni = p.i + di[d];
        int nj = p.j + dj[d];
        int cc = 0;

        while (check(ni, nj)) {
            if (map[ni][nj] == 0) {
                cc++;
                map[ni][nj] = depth + 10;
            } else if (map[ni][nj] == 6) {
                break;
            }

            ni = ni + di[d];
            nj = nj + dj[d];
        }

        return cc;
    }

    static void ungo(Point p, int d, int depth) {
        int ni = p.i + di[d];
        int nj = p.j + dj[d];

        while (check(ni, nj)) {
            if (map[ni][nj] == depth + 10) {
                map[ni][nj] = 0;
            } else if (map[ni][nj] == 6) {
                break;
            }

            ni = ni + di[d];
            nj = nj + dj[d];
        }
    }

    static boolean check(int i, int j) {
        return i > -1 && i < N && j > -1 && j < M;
    }

}

