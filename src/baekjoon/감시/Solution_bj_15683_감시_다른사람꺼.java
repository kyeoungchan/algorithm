package baekjoon.감시;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Solution_bj_15683_감시_다른사람꺼 {
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    static int N, M, map[][];
    static List<CCTV> list;
    static List<int[]> walls;
    static boolean[][] isWatch;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        List<Integer> result = new ArrayList<>();
        int cnt = 0;

        list = new ArrayList<>();
        walls = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 6)
                    walls.add(new int[]{i, j});
                if (map[i][j] < 6 && map[i][j] > 0) {
                    list.add(new CCTV(map[i][j], i, j));
                }
            }
        } //input


        for (int k = 0; k < 4; k++) {
            isWatch = new boolean[N][M];
            cnt = 0;
            Collections.sort(list);
            for (int i = 0; i < list.size(); i++) {
                cctvFun(list.get(i).row, list.get(i).col, list.get(i).num, k);

            }
            for (int i = 0; i < walls.size(); i++) {
                isWatch[walls.get(i)[0]][walls.get(i)[1]] = true;

            }

            for (boolean[] bs : isWatch) {
                for (boolean b : bs) {
                    if (!b)
                        cnt++;
                }
            }
            result.add(cnt);

            cnt = 0;
            isWatch = new boolean[N][M];
            Collections.reverse(list);
            for (int i = 0; i < list.size(); i++) {
                cctvFun(list.get(i).row, list.get(i).col, list.get(i).num, k);

            }
            for (int i = 0; i < walls.size(); i++) {
                isWatch[walls.get(i)[0]][walls.get(i)[1]] = true;

            }

            for (boolean[] bs : isWatch) {
                for (boolean b : bs) {
                    if (!b)
                        cnt++;
                }
            }
            result.add(cnt);
        }
        System.out.println(Collections.min(result));

    }

    static class CCTV implements Comparable<CCTV> {
        int num;
        int row;
        int col;

        public CCTV(int num, int row, int col) {
            super();
            this.num = num;
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(CCTV o) {
            // TODO Auto-generated method stub
            return this.num - o.num;
        }


    }

    public static void cctvFun(int r, int c, int cctv, int s) {
        int max = 0;
        int mIdx = 0;
        switch (cctv) {
            case 1:
                for (int i = s; i < 4 + s; i++) {
                    int temp = count(r, c, i);
                    if (max < temp) {
                        mIdx = i;
                        max = temp;
                    }
                }
                flag(r, c, mIdx);
                break;
            case 2:
                for (int i = s; i < 2 + s; i++) {
                    int temp = count(r, c, i) + count(r, c, i + 2);
                    if (max < temp) {
                        mIdx = i;
                        max = temp;
                    }
                }
                flag(r, c, mIdx);
                flag(r, c, mIdx + 2);
                break;
            case 3:
                for (int i = s; i < 4 + s; i++) {
                    int temp = count(r, c, i) + count(r, c, i + 1);
                    if (max < temp) {
                        mIdx = i;
                        max = temp;
                    }
                }
                flag(r, c, mIdx);
                flag(r, c, mIdx + 1);

                break;
            case 4:

                for (int i = +s; i < 4 + s; i++) {
                    int temp = count(r, c, i) + count(r, c, i + 1) + count(r, c, i + 2);
                    if (max < temp) {
                        mIdx = i;
                        max = temp;
                    }
                }
                flag(r, c, mIdx);
                flag(r, c, mIdx + 1);
                flag(r, c, mIdx + 2);
                break;
            default:
                flag(r, c, 0);
                flag(r, c, 1);
                flag(r, c, 2);
                flag(r, c, 3);
        }
    }

    public static int count(int r, int c, int d) {
        int count = 0;
        int row = r;
        int col = c;

        while (true) {
            row += dir[d][0];
            col += dir[d][1];
            if (row > N - 1 || col > M - 1 || row < 0 || col < 0)
                break;
            if (map[row][col] == 6) {
                break;
            }
            if (map[row][col] == 0 && !isWatch[row][col]) {
                count++;
            }
        }
        return count;

    }

    public static void flag(int r, int c, int d) {
        int row = r;
        int col = c;
        isWatch[row][col] = true;
        while (true) {
            row += dir[d][0];
            col += dir[d][1];
            if (row > N - 1 || col > M - 1 || row < 0 || col < 0)
                break;
            if (map[row][col] == 6) {
                break;
            }
            if (map[row][col] == 0 && !isWatch[row][col]) {
                isWatch[row][col] = true;
            }
        }

    }
}