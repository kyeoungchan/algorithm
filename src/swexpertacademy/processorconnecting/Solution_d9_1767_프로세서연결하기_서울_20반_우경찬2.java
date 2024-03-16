package swexpertacademy.processorconnecting;

import java.io.*;
import java.util.*;

/**
 * dfs로 상하좌우 방향의로 연결선을 잇다가 교차되면 다시 연결선을 취소하는 방향으로 구현한다.
 */
public class Solution_d9_1767_프로세서연결하기_서울_20반_우경찬2 {

    static int N, maxConnected, minLen, di[] ={-1, 0, 1, 0}, dj[] = {0, 1, 0, -1}, tempConnected, tempLen;
    static int[][] board;
    static List<int[]> cores;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_1767.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            board = new int[N][N];
            cores = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                    if (board[i][j] == 1) {
                        if (i > 0 && i < N - 1 && j > 0 && j < N - 1) {
                            cores.add(new int[]{i, j});
                        }
                    }
                }
            }
            maxConnected = 0;
            minLen = Integer.MAX_VALUE;
            tempConnected = 0;
            tempLen = 0;

            dfs(0);
            if (minLen == Integer.MAX_VALUE) minLen = 0;
            sb.append("#").append(tc).append(" ").append(minLen).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void dfs(int cnt) {
        if (cores.size() - cnt + tempConnected < maxConnected) {
            // 지금까지 연결한 코어의 개수와 앞으로 체크할 코어를 다 연결한다고 가정했을 때 다 합친 수가 이전에 이미 max로 찍은 코어의 수보다 적다면 재귀호출을 멈춘다.
            return;
        }
        if (cnt == cores.size()) {
            if (tempConnected > maxConnected) {
                maxConnected = tempConnected;
                minLen = tempLen;
            } else if (tempConnected == maxConnected && tempLen < minLen) {
                minLen = tempLen;
            }
            return;
        }

        int[] cur = cores.get(cnt);
        for (int d = 0; d < 4; d++) {
            setStatus(cur[0], cur[1], d, cnt);
        }
        dfs(cnt + 1);
    }

    static void setStatus(int startI, int startJ, int d, int cnt) {
        int k = 1;
        boolean success = false;
        while (true) {
            int ni = startI + di[d] * k;
            int nj = startJ + dj[d] * k;
            if (ni < 0 || ni > N - 1 || nj < 0 || nj > N - 1) {
                success = true;
                tempConnected++;
                break;
            }
            if (board[ni][nj] == 1) {
                break;
            }
            board[ni][nj] = 1;
            tempLen++;
            k++;
        }

        if (success) {
            // 연결에 성공했다면 원상 복구하기 전에 재귀 호출!
            dfs(cnt + 1);
        }

        if (success) tempConnected--;
        while (k > 1) {
            k--;
            int ni = startI + di[d] * k;
            int nj = startJ + dj[d] * k;
            board[ni][nj] = 0;
            tempLen--;
        }
    }
}
