import java.util.*;
import java.io.*;

/**
 * NxN 크기의 핀볼게임 판
 * 블록번호: 1,2,3,4,5
 * 웜홀번호: 6,7,8,9,10
 * 블랙홀번호: -1
 * 게임이 끝나는 조건: 출발 위치로 돌아오거나, 블랙홀에 빠질 때
 * 점수: 벽이나 블록에 부딪힌 횟수
 * 게임에서 얻을 수 있는 점수의 최댓값 구하기
 */
public class Solution {

    static int N, map[][], di[] = {-1, 0, 1, 0}, dj[] = {0, 1, 0, -1};
    static int[][] wormhole;
    static final int UP = 0, RIGHT = 1, DOWN =2, LEFT = 3;
    static int[][] blockDir = { // 만난 블록의 번호, 현재 방향 => 바뀔 방향
            null,
            {DOWN, LEFT, RIGHT, UP},
            {RIGHT, LEFT, UP, DOWN},
            {LEFT, DOWN, UP, RIGHT},
            {DOWN, UP, LEFT, RIGHT},
            {DOWN, LEFT, UP, RIGHT}
    };

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine().trim());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine().trim());
            map = new int[N][N];
            wormhole = new int[11][2]; // 웜홀의 쌍마다 좌표를 저장할 이차원배열(좌표는 하나의 숫자로 변환 저장)
            for (int i = 6; i < 11; i++) {
                Arrays.fill(wormhole[i], -1);
            }
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine().trim(), " ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if (map[i][j] > 5 && map[i][j] < 11) {
                        // 웜홀에 만났다면 6 ~ 10
                        int wormHoleNum = map[i][j];
                        if (wormhole[wormHoleNum][0] == -1) {
                            wormhole[wormHoleNum][0] = i * N + j;
                        } else {
                            wormhole[wormHoleNum][1] = i * N + j;
                        }
                    }
                }
            }

            int finalScore = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] != 0) continue;
                    for (int d = 0; d < 4; d++) {
                        int score = play(i, j, d);
                        finalScore = Math.max(finalScore, score);
                    }
                }
            }
            sb.append("#").append(tc).append(" ").append(finalScore).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static int play(int r, int c, int d) {
        int sr = r;
        int sc = c;
        int score = 0;
        while (true) {
            r += di[d];
            c += dj[d];
            if (r < 0 || r > N - 1 || c < 0 || c > N - 1) { // 벽을 만났을 경우
                d = (d + 2) % 4;
                score++;
                continue;
            }
            if (r == sr && c == sc) break; // 원래 지점으로 돌아왔을 경우
            if (map[r][c] == -1) break; // 블랙홀 만났을 경우
            if (map[r][c] > 0 && map[r][c] < 6) { // 블록을 만났을 경우
                int blockNum = map[r][c];
                d = blockDir[blockNum][d];
                score++;
            } else if (map[r][c] > 5 && map[r][c] < 11) { // 웜홀을 만났을 경우
                int wormHoleNum = map[r][c];
                int pos = r * N + c;
                if (wormhole[wormHoleNum][0] == pos) {
                    int nextPos = wormhole[wormHoleNum][1];
                    r = nextPos / N;
                    c = nextPos % N;
                } else {
                    int nextPos = wormhole[wormHoleNum][0];
                    r = nextPos / N;
                    c = nextPos % N;
                }
            }
        }
        return score;
    }
}