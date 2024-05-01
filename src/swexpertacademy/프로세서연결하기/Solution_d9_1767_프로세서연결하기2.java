package swexpertacademy.프로세서연결하기;

import java.io.*;
import java.util.*;

public class Solution_d9_1767_프로세서연결하기2 {

    static int N, maxConnected, minLen;
    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
    static int[][] board;
    static List<int[]> processors;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_1767.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            board = new int[N][N];
            processors = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                    // 가장자리에 있는 프로세서는 생략한다.
                    if (i == 0 || i == N - 1 || j == 0 || j == N - 1) continue;
                    // 프로세서 좌표 정보를 리스트에 저장해준다.
                    if (board[i][j] == 1) processors.add(new int[]{i, j});
                }
            }

            maxConnected = 0;
            minLen = 0;
            checkProcessor(0, maxConnected, minLen);
            sb.append("#").append(tc).append(" ").append(minLen).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void checkProcessor(int cnt, int connected, int len) {
        if (cnt == processors.size()) {
            if (connected > maxConnected) {
                maxConnected = connected;
                minLen = len;
            } else if (connected == maxConnected && minLen > len) {
                minLen = len;
            }
            return;
        }

        // 현재 연결한 것에서 나머지 모든 프로세서들을 연결해도 maxConnected에 도달할 수 없다면 의미없으므로 가지치기
        if (processors.size() - cnt + connected < maxConnected) return;

        int r = processors.get(cnt)[0];
        int c = processors.get(cnt)[1];

        for (int d = 0; d < 4; d++) {
            int nr = r + di[d];
            int nc = c + dj[d];
            int add = 0;
            boolean isConnected = true;
            while (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                if (board[nr][nc] == 1) {
                    isConnected = false;
                    break;
                }
                add++;
                board[nr][nc] = 1;
                nr += di[d];
                nc += dj[d];
            }

            if (isConnected) {
                checkProcessor(cnt + 1, connected + 1, len + add);
            }

            nr = r + di[d];
            nc = c + dj[d];
            for (int i = 0; i < add; i++) {
                board[nr][nc] = 0;
                nr += di[d];
                nc += dj[d];
            }
        }
        checkProcessor(cnt + 1, connected, len);
    }
}
