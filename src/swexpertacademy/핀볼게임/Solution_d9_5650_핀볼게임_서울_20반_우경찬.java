package swexpertacademy.핀볼게임;

import java.util.*;
import java.io.*;

public class Solution_d9_5650_핀볼게임_서울_20반_우경찬 {

    static int N, ANS;
    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
    static int[][] map, wormHolePos;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_5650.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine().trim());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine().trim());
            map = new int[N][N];
            wormHolePos = new int[5][4];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    wormHolePos[i][j] = -1;
                }
            }
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine().trim(), " ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    /*if (map[i][j] == -1) {
                        blackHolePos.add(new int[]{i, j});
                    } else */
                    if (5 < map[i][j] && map[i][j] < 11) {
                        int wormHoleIdx = map[i][j] - 6;
                        if (wormHolePos[wormHoleIdx][0] == -1) {
                            wormHolePos[wormHoleIdx][0] = i;
                            wormHolePos[wormHoleIdx][1] = j;
                        } else {
                            wormHolePos[wormHoleIdx][2] = i;
                            wormHolePos[wormHoleIdx][3] = j;
                        }
                    }
                }
            }
            ANS = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] == 0) {
                        play(i, j);
                    }
                }
            }

            sb.append("#").append(tc).append(" ").append(ANS).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void play(int i, int j) {
        for (int d = 0; d < 4; d++) {
            start(i, j, d);
        }
    }

    static void start(int i, int j, int d) {
        int curI = i;
        int curJ = j;
        int cnt = 0;
        do {
            int ni = curI + di[d];
            int nj = curJ + dj[d];

            if (ni < 0 || ni > N - 1 || nj < 0 || nj > N - 1) {
                // 벽에 부딪힌 경우 현재위치는 업데이트하지 않고 점수를 올려주고 방향만 반대로 바꾼다.
                cnt++;
                d = (d + 2) % 4;
                curI = ni;
                curJ = nj;
            } else if (map[ni][nj] > 0 && map[ni][nj] < 6) {
                cnt++;
                // 블록에 부딪힌 경우
                // 블록 번호 0, 1, 2, 3, 4로 변환
                int blockNum = map[ni][nj] - 1;
                // d=0 -> 0만나면 2, 1만나면 1, 2만나면 3, 3만나면 2, 4만나면 2
                // d=1 -> 0만나면 3, 1만나면 3, 2만나면 2, 3만나면 0, 4만나면 3
                // d=2 -> 0만나면 1, 1만나면 0, 2만나면 0, 3만나면 3, 4만나면 0
                // d=3 -> 0만나면 0, 1만나면 2, 2만나면 1, 3만나면 1, 4만나면 1
                if (blockNum == 0) {
                    if (d == 0 || d == 1) d += 2;
                    else if (d == 2) d = 1;
                    else d = 0;
                } else if (blockNum == 1) {
                    if (d == 1 || d == 2) d = (d + 2) % 4;
                    else if (d == 0) d = 1;
                    else d = 2;
                } else if (blockNum == 2) {
                    if (d == 2 || d == 3) d -= 2;
                    else if (d == 0) d = 3;
                    else d = 2;
                } else if (blockNum == 3) {
                    if (d == 0 || d == 3) d = (d + 2) % 4;
                    else if (d == 1) d = 0;
                    else d = 3;
                } else {
                    d = (d + 2) % 4;
                }
                curI = ni;
                curJ = nj;
            } else if (map[ni][nj] > 5) {
                int wormHoleIdx = map[ni][nj] - 6;
                if (ni == wormHolePos[wormHoleIdx][0] && nj == wormHolePos[wormHoleIdx][1]) {
                    curI = wormHolePos[wormHoleIdx][2];
                    curJ = wormHolePos[wormHoleIdx][3];
                } else {
                    curI = wormHolePos[wormHoleIdx][0];
                    curJ = wormHolePos[wormHoleIdx][1];
                }
            } else {
                curI = ni;
                curJ = nj;
            }
        } while (!((curI == i && curJ == j) || (curI >= 0 && curI < N && curJ >= 0 && curJ < N) && map[curI][curJ] == -1));
        ANS = Math.max(ANS, cnt);
    }
}
