package baekjoon.마법사상어와복제;

import java.io.*;
import java.util.*;

/**
 * 물고기에게 주어지는 정보는 좌표와 방향이 다다. 따라서 방향만 같다면 같은 좌표에 있는 물고기는 하나나 마찬가지게 된다.
 * 그래서 리스트로 표현하지 않고, 해당 좌표에 어떤 방향의 물고기가 몇마리씩있는지 표현하는 것만으로 충분하다.
 * 이것을 문제를 읽을 때 잘 파악하는 것이 좋다.
 */
public class Solution_bj_23290_마법사상어와복제2 {

    static int[][][] map, copied;
    static int[][] smell;
    static int[] dsi = {-1, 0, 1, 0}, dsj = {0, -1, 0, 1}, dfi = {0, -1, -1, -1, 0, 1, 1, 1}, dfj = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int si, sj, answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        map = new int[5][5][8];
        copied = new int[5][5][8];
        smell = new int[5][5];

        int M = Integer.parseInt(st.nextToken()); // 물고기 수
        answer = M;
        int S = Integer.parseInt(st.nextToken()); // 상어가 마법을 연습한 횟수
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken()) - 1; // 0~7로 방향정보 받기
            map[r][c][d]++;
        }
        st = new StringTokenizer(br.readLine());
        si = Integer.parseInt(st.nextToken());
        sj = Integer.parseInt(st.nextToken());

        for (int time = 0; time < S; time++) {
//            debug();
            copy();
//            debug();
            moveFish();
//            debug();
            updateSmell();
//            debug();
            moveShark();
//            System.out.println("si:" + si + " sj:" + sj);
//            System.out.println("answer = " + answer);
//            debug();
            paste();
//            System.out.println("answer = " + answer);
//            debug();
        }
        System.out.println(answer);
        br.close();
    }

    static void paste() {
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                for (int d = 0; d < 8; d++) {
                    if (copied[i][j][d] == 0) continue;
                    map[i][j][d] += copied[i][j][d];
                    answer += copied[i][j][d];
                    copied[i][j][d] = 0;
                }
            }
        }
    }
    static void moveShark() {
        int[] sharkTrace = new int[3];
        int totalAteFish = -1;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{si, sj, 0, 0, -1}); // 상어의 좌표, 잡아먹은 물고기의 수, 몇 번째 움직인 건지, 처음 도달한 물고기의 좌표를 하나의 숫자로 나타내기!
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int ci = cur[0];
            int cj = cur[1];
            int ateFish = cur[2];
            int moveCnt = cur[3];
            for (int d = 0; d < 4; d++) {
                int ni = ci + dsi[d];
                int nj = cj + dsj[d];
                if (ni < 1 || ni > 4 || nj < 1 || nj > 4) continue;
                int nMoveCnt = moveCnt + 1;
                int nAteFish = ateFish;
//                System.out.println("Arrays.toString(cur) = " + Arrays.toString(cur));
                if (!(nMoveCnt == 3 && cur[4] == (ni - 1) * 4 + nj - 1)) {
                    for (int df = 0; df < 8; df++) {
                        nAteFish += map[ni][nj][df];
                    }
                }
//                System.out.println("nAteFish = " + nAteFish);
//                System.out.println("ni:"+ni+" nj:"+nj);

                if (nMoveCnt == 3) {
                    if (totalAteFish < nAteFish) {
//                        System.out.println("nAteFish = " + nAteFish);
//                        System.out.println("Arrays.toString(cur) = " + Arrays.toString(cur));
//                        System.out.println("ni:"+ni+" nj:"+nj);
                        totalAteFish = nAteFish;
                        sharkTrace[0] = cur[4];
                        sharkTrace[1] = (ci - 1) * 4 + cj - 1;
                        sharkTrace[2] = (ni - 1) * 4 + nj - 1;
                    }
                } else if (nMoveCnt == 1) {
                    int pos = (ni - 1) * 4 + nj - 1;
                    q.offer(new int[]{ni, nj, nAteFish, nMoveCnt, pos});
                } else {
                    q.offer(new int[]{ni, nj, nAteFish, nMoveCnt, cur[4]});
                }
            }
        }

//        System.out.println("totalAteFish = " + totalAteFish);
        answer -= totalAteFish;
        for (int move = 0; move < 3; move++) {
            int pos = sharkTrace[move];
            int i = pos / 4 + 1;
            int j = pos % 4 + 1;
            for (int d = 0; d < 8; d++) {
                if (map[i][j][d] == 0) continue;
                smell[i][j] = 2;
                map[i][j][d] = 0;
            }
        }
        si = sharkTrace[2] / 4 + 1;
        sj = sharkTrace[2] % 4 + 1;
    }

    static void updateSmell() {
        // 한 턴마다 냄새가 1씩 얕아진다.
        // 상어가 지나가고 나면 다시 2씩 짙어져야하므로 상어가 지나가기 전에 업데이트를 해준다.
        // 만약 상어가 지나가고나서 업데이트를 한다면 상어가 물고기를 잡아먹자마자 냄새의 정도가 1로 변한다.
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                if (smell[i][j] == 0) continue;
                smell[i][j]--;
            }
        }
    }
    static void copy() {
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                for (int d = 0; d < 8; d++) {
                    if (map[i][j][d] == 0) continue;
                    // 복제를 해두고, 원래 있던 map에는 물고기를 삭제해둔다.
                    // 그래야 빈 칸에 새롭게 물고기를 이동시키면서 겹치지 않게 놓을 수 있다.
                    copied[i][j][d] += map[i][j][d];
                    map[i][j][d] = 0;
                }
            }
        }
    }

    static void moveFish() {
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                for (int d = 0; d < 8; d++) {
                    if (copied[i][j][d] == 0) continue;
                    moveGroupFish(i, j, d, copied[i][j][d]);
                }
            }
        }
    }

    static void moveGroupFish(int i, int j, int d, int fish) {
        for (int k = 0; k < 8; k++) {
            int nd = d - k;
            if (nd < 0) nd += 8;
            int ni = i + dfi[nd];
            int nj = j + dfj[nd];
            if (ni < 1 || ni > 4 || nj < 1 || nj > 4 || (ni == si && nj == sj) || smell[ni][nj] != 0) continue;
            // 움직이는 데 성공한 경우 바로 움직여주고 반복문 탈출
            map[ni][nj][nd] += fish;
            return;
        }
        // 어느 방향으로도 물고기 군집이 움직이지 못한 경우
        map[i][j][d] += fish;
    }

    static void debug() {
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                System.out.print("[");
                for (int d = 0; d < 8; d++) {
                    if (map[i][j][d] == 0) continue;
//                    System.out.print(d + ",");
                    System.out.print(d+":"+map[i][j][d] + " ");
                }
                System.out.print("]");
            }
            System.out.println();
        }
        System.out.println();
    }
}
