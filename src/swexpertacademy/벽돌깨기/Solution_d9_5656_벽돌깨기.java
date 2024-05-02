package swexpertacademy.벽돌깨기;

import java.io.*;
import java.util.*;

/**
 * WxH의 배열
 * 벽돌은 1~9
 * 떨어뜨릴 벽돌: N개
 * 최대한 많은 벽돌을 제거하려고 할 때, 남은 벽돌의 개수를 구하라!
 */
public class Solution_d9_5656_벽돌깨기 {

    static int N, W, H, maxBroke;
    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1}, dropW;
    static int[][] board, tempBoard;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_5656.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());
            board = new int[H][W];
            tempBoard = new int[H][W];
            int totalBricks = 0;
            for (int i = 0; i < H; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < W; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                    if (board[i][j] != 0) totalBricks++;
                }
            }
            dropW = new int[N];
            maxBroke = 0;
            selectDrop(0);

            sb.append("#").append(tc).append(" ").append(totalBricks - maxBroke).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void selectDrop(int cnt) {
        if (cnt == N) {
            play();
            return;
        }

        for (int i = 0; i < W; i++) {
            dropW[cnt] = i;
            selectDrop(cnt + 1);
        }
    }

    static void play() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                tempBoard[i][j] = board[i][j];
            }
        }
        int broke = 0;
        for (int i = 0; i < N; i++) {
            broke += shoot(dropW[i]);
            drop();
        }
        maxBroke = Math.max(maxBroke, broke);
    }

    static void drop() {
        ArrayDeque<Integer> qr = new ArrayDeque<>();
        for (int j = 0; j < W; j++) {
            for (int i = H - 1; i > -1; i--) {
                if (tempBoard[i][j] == 0) qr.offer(i);
                else if (!qr.isEmpty()) {
                    // 0이 아닌 벽돌을 찾았는데 아래에서 빈칸이 있었다면
                    tempBoard[qr.poll()][j] = tempBoard[i][j];
                    tempBoard[i][j] = 0;
                    qr.offer(i);
                }
            }
            qr.clear();
        }
    }

    static int shoot(int startJ) {
        int cnt = 0;
        // 이번 턴에 깨뜨린 벽돌의 수
        for (int i = 0; i < H; i++) {
            if (tempBoard[i][startJ] != 0) {
                ArrayDeque<int[]> q = new ArrayDeque<>();
                q.offer(new int[]{i, startJ, tempBoard[i][startJ]});
                tempBoard[i][startJ] = 0;
                while (!q.isEmpty()) {
                    int[] cur = q.poll();
                    cnt++;
                    int range = cur[2];
                    for (int d = 0; d < 4; d++) {
                        for (int k = 1; k < range; k++) {
                            // 3이라면 2만큼 뻗어나간다.
                            int ni = cur[0] + di[d] * k;
                            int nj = cur[1] + dj[d] * k;
                            if (ni < 0 || ni > H - 1 || nj < 0 || nj > W - 1) continue;
                            if (tempBoard[ni][nj] != 0) {
                                q.offer(new int[]{ni, nj, tempBoard[ni][nj]});
                                tempBoard[ni][nj] = 0;
                            }
                        }
                    }
                }
                // 0이 아닌 벽돌을 만나면 연쇄작용 발생 후 반복문 탈출
                break;
            }
        }
        return cnt;
    }
}
