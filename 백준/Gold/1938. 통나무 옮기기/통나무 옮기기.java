import java.util.*;
import java.io.*;

public class Main {

    static int N, ei1, ej1, ei2, ej2;
    static int[] di = {-1, 0, 1 ,0}, dj = {0, 1, 0, -1};
    static char[][] map;
    static boolean[][][] v;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new char[N][N];
        String s;

        int bi1 = -1, bj1 = -1, bi2 = -1, bj2 = -1;
        ei1 = -1; ej1 = -1;
        ei2 = -1; ej2 = -1;
        // 시작지점의 양끝 좌표와 끝 지점의 양끝 좌표
        int bCnt = 0, eCnt = 0;
        for (int i = 0; i < N; i++) {
            s = br.readLine();
            for (int j = 0; j < N; j++) {
                char c = s.charAt(j);
                if (c == 'B') {
                    if (bCnt == 0) {
                        bi1 = i;
                        bj1 = j;
                    }
                    else if (bCnt == 2){
                        bi2 = i;
                        bj2 = j;
                    }
                    map[i][j] = '0';
                    bCnt++;
                } else if (c == 'E') {
                    if (eCnt == 0) {
                        ei1 = i;
                        ej1 = j;
                    } else if (eCnt == 2) {
                        ei2 = i;
                        ej2 = j;
                    }
                    map[i][j] = '0';
                    eCnt++;
                } else {
                    map[i][j] = c;
                }
            }
        }

        v = new boolean[N][N][4];

        int d = -1;
        if (bi1 == bi2) {
            // 가로로 있는 상태라면
            v[bi1][bj1][1] = true;
            v[bi2][bj2][3] = true;
            d = 1;
        } else {
            // 세로로 있는 상태라면
            v[bi1][bj1][2] = true;
            v[bi2][bj2][0] = true;
            d = 2;
        }
        int answer = bfs(bi1, bj1, d);
        System.out.println(answer);
        br.close();
    }

    static int bfs(int bi, int bj, int d) {
        int result = Integer.MAX_VALUE;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{bi, bj, d, 0});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int ci = cur[0];
            int cj = cur[1];
            int curD = cur[2];
            int cnt = cur[3];

            if (arrivedGoal(ci, cj, curD)) {
                result = cnt;
                break;
            }

            int ci2 = ci + di[curD] * 2;
            int cj2 = cj + dj[curD] * 2;
            for (int actionD = 0; actionD < 4; actionD++) {
                if (canAction(ci, cj, ci2, cj2, curD, actionD)) {
                    int ni = ci + di[actionD];
                    int nj = cj + dj[actionD];
                    v[ni][nj][curD] = true;
                    v[ci2 + di[actionD]][cj2 + dj[actionD]][(curD + 2) % 4] = true;
                    q.offer(new int[]{ni, nj, curD, cnt + 1});
//                    debug(ni, nj, curD, cnt + 1, actionD);
                }
            }

            if (canRotate(ci, cj, curD)) {
                if (curD % 2 == 0) {
                    int ni = ci + di[curD];
                    int nj = cj - 1;
                    q.offer(new int[]{ni, nj, 1, cnt + 1});
                    v[ni][nj][1] = true;
                    v[ni][cj + 1][3] = true;
                } else {
                    int ni = ci + 1;
                    int nj = cj + dj[curD];
                    q.offer(new int[]{ni, nj, 0, cnt + 1});
                    v[ni][nj][0] = true;
                    v[ci - 1][nj][2] = true;
                }
            }
        }
        if (result == Integer.MAX_VALUE) result = 0;
        return result;
    }

    static void debug(int ci, int cj, int curD, int cnt, int actionD) {
        System.out.println("cnt = " + cnt);
        System.out.println("actionD = " + actionD);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i == ci && j == cj) || (i == ci + di[curD] && j == cj + dj[curD]) || (i == ci + di[curD] * 2 && j == cj + dj[curD] * 2))
                    System.out.print("[" + map[i][j] + "]");
                else
                    System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static boolean canRotate(int i, int j, int curD) {
        if (curD % 2 == 1) {
            if (i - 1 < 0 || i + 1 > N - 1) return false;
            for (int k = 0; k < 3; k++) {
                if (map[i - 1][j + dj[curD] * k] == '1' || map[i + 1][j + dj[curD] * k] == '1') return false;
            }
            if (v[i - 1][j + dj[curD]][2]) return false;
            return true;
        } else {
            if (j - 1 < 0 || j + 1 > N - 1) return false;
            for (int k = 0; k < 3; k++) {
                if (map[i + di[curD] * k][j - 1] == '1' || map[i + di[curD] * k][j + 1] == '1') return false;
            }
            if (v[i + di[curD]][j + 1][3]) return false;
            return true;
        }
    }

    static boolean canAction(int i, int j, int i2, int j2, int curD, int actionD) {
        if (actionD == curD) {
            int ni2 = i2 + di[actionD];
            int nj2 = j2 + dj[actionD];
            return ni2 >= 0 && ni2 < N && nj2 >= 0 && nj2 < N && map[ni2][nj2] == '0' && !v[ni2][nj2][(curD + 2) % 4];
        } else if (actionD == (curD + 2) % 4) {
            int ni = i + di[actionD];
            int nj = j + dj[actionD];
            return ni >= 0 && ni < N && nj >= 0 && nj < N && map[ni][nj] == '0' && !v[ni][nj][curD];
        } else {
            int ni = i + di[actionD];
            int nj = j + dj[actionD];
                
            if (ni < 0 || ni > N - 1 || nj < 0 || nj > N - 1 || map[ni][nj] == '1' || v[ni][nj][curD]) return false;
            for (int k = 1; k < 3; k++) {
                int nni = i + di[curD] * k + di[actionD];
                int nnj = j + dj[curD] * k + dj[actionD];
                if (nni < 0 || nni > N - 1 || nnj < 0 || nnj > N - 1 || map[nni][nnj] == '1')
                    return false;
            }
            return true;

        }
    }

    static boolean arrivedGoal(int i, int j, int d) {
        int i2 = i + di[d] * 2;
        int j2 = j + dj[d] * 2;
        return ((i == ei1 && j == ej1) && (i2 == ei2 && j2 == ej2)) || ((i == ei2 && j == ej2) && (i2 == ei1 && j2 == ej1));
    }
}