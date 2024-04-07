package baekjoon.마법사상어와복제;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main2 {

    static int N = 4;
    static int M, S, sx, sy;

    static int[] fdx = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] fdy = {-1, -1, 0, 1, 1, 1, 0, -1};

    static int[] sdx = {0, 1, 0, -1};
    static int[] sdy = {1, 0, -1, 0};

    static int[][][] board = new int[N][N][8];
    static int[][][] temp = new int[N][N][8];
    static int[][] smell = new int[N][N];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        int fx, fy, d;

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            fx = Integer.parseInt(st.nextToken()) - 1;
            fy = Integer.parseInt(st.nextToken()) - 1;
            d = Integer.parseInt(st.nextToken()) - 1;
            board[fx][fy][d] += 1;
        }

        st = new StringTokenizer(br.readLine());
        sx = Integer.parseInt(st.nextToken()) - 1;
        sy = Integer.parseInt(st.nextToken()) - 1;

        for (int i = 0; i < S; i++) {
            play();
        }

        int answer = totalFish();

        System.out.println(answer);

    }

    static void play() {
        magic();
        moveFish();
        resetSmell();
        moveShark();
        duplicate();
    }

    static void magic() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 8; k++) {
                    temp[i][j][k] = board[i][j][k];
                    board[i][j][k] = 0;
                }
            }
        }
    }

    static void moveFish() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 8; k++) {
                    if (temp[i][j][k] == 0) {
                        continue;
                    }

                    int nd = findFishDir(i,  j,  k);

                    if (nd == -1) {
                        board[i][j][k] += temp[i][j][k];
                        continue;
                    }

                    int nfx = i + fdx[nd];
                    int nfy = j + fdy[nd];
                    board[nfx][nfy][nd] += temp[i][j][k];
                }
            }
        }
    }

    static int findFishDir(int x, int y, int d) {
        for (int i = 0; i < 8; i++) {
            int nd = (d - i);
            if (nd < 0) {
                nd += 8;
            }
            int nx = x + fdx[nd];
            int ny = y + fdy[nd];

            if (!inRange(nx, ny) || smell[nx][ny] > 0 || (nx == sx && ny == sy)) {
                continue;
            }

            return nd;
        }

        return -1;
    }

    static void resetSmell() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                smell[i][j] -= 1;
            }
        }
    }

    static void moveShark() {
        int[] dirs = findSharkDir();

        for (int d = 0; d < 3; d++) {
            sx += sdx[dirs[d]];
            sy += sdy[dirs[d]];

            if (isFish(sx, sy)) {
                eatFish(sx, sy);
                smell[sx][sy] = 2;
            }
        }
    }

    static int[] findSharkDir() {
        int max_cnt = 0;
        int nx1, nx2, nx3;
        int ny1, ny2, ny3;
        int cnt1, cnt2, cnt3;
        int[] dirs = new int[3];

        for (int i = 0; i < 4; i++) {
            nx1 = sx + sdx[i];
            ny1 = sy + sdy[i];

            if (!inRange(nx1, ny1)) {
                continue;
            }

            cnt1 = countFish(nx1, ny1);

            for (int j = 0; j < 4; j++) {
                nx2 = nx1 + sdx[j];
                ny2 = ny1 + sdy[j];

                if (!inRange(nx2, ny2)) {
                    continue;
                }

                cnt2 = countFish(nx2, ny2);

                for (int k = 0; k < 4; k++) {
                    nx3 = nx2 + sdx[k];
                    ny3 = ny2 + sdy[k];

                    if (!inRange(nx3, ny3)) {
                        continue;
                    }

                    if (nx1 == nx3 && ny1 == ny3) {
                        cnt3 = 0;
                    } else {
                        cnt3 = countFish(nx3, ny3);
                    }

                    if (max_cnt <= (cnt1 + cnt2 + cnt3)) {
                        max_cnt = cnt1 + cnt2 + cnt3;
                        dirs[0] = i;
                        dirs[1] = j;
                        dirs[2] = k;
                    }
                }
            }
        }

        return dirs;
    }

    static int countFish(int x, int y) {
        int cnt = 0;
        for (int d = 0; d < 8; d++) {
            cnt += board[x][y][d];
        }
        return cnt;
    }

    static boolean isFish(int x, int y) {
        for (int d = 0; d < 8; d++) {
            if (board[x][y][d] > 0) {
                return true;
            }
        }
        return false;
    }

    static void eatFish(int x, int y) {
        for (int d = 0; d < 8; d++) {
            board[x][y][d] = 0;
        }
    }

    static void duplicate() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 8; k++) {
                    board[i][j][k] += temp[i][j][k];
                }
            }
        }
    }

    static int totalFish() {
        int total = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 8; k++) {
                    total += board[i][j][k];
                }
            }
        }
        return total;
    }

    static boolean inRange(int x, int y) {
        if (0 <= x && x < N && 0 <= y && y < N) {
            return true;
        }
        return false;
    }

}
