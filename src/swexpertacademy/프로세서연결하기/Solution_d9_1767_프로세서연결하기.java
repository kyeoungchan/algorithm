package swexpertacademy.프로세서연결하기;

import java.util.*;
import java.io.*;

public class Solution_d9_1767_프로세서연결하기 {

    static int N, maxConnected, minLen, totalProcessors;
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
                    if (i == 0 || i == N - 1 || j == 0 || j == N - 1) continue;
                    if (board[i][j] == 1) processors.add(new int[]{i, j});
                }
            }

            totalProcessors = processors.size();

            maxConnected = 0;

            // check again!
            minLen = 0;
            // 하... 당연한 얘기지만 처음에 minlen을 전달할 때 0으로 전달해야한다.
            // 나는 어차피 연결된 코어의 개수가 수정되면 minLen도 업데이트되기 때문에 상관없다고 생각했다.
            // 하지만 minLen은 더해서 전달되기 때문에 엄청 커져서 전달되게 된다.
            goCheck(0, maxConnected, minLen);
            sb.append("#").append(tc).append(" ").append(minLen).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void goCheck(int cnt, int connected, int len) {
        if (cnt == totalProcessors) {
            if (connected > maxConnected) {
                maxConnected = connected;
                minLen = len;
            } else if (connected == maxConnected && len < minLen) {
                minLen = len;
            }
            return;
        }

        if (totalProcessors - cnt < maxConnected - connected) {
            // 현재까지 연결된 프로세서에서 남은 프로세서들을 다 연결해도 이미 max로 찍힌 연결 수만큼 도달할 수 없다면 포기한다.
            return;
        }

        int[] cur = processors.get(cnt);
        int i = cur[0];
        int j = cur[1];
        for (int d = 0; d < 4; d++) {
            if (canGo(i, j, d)) {
                int addLen = setStatus(i, j, d, 2);
                goCheck(cnt + 1, connected + 1, len + addLen);
                setStatus(i, j, d, -2);
            }
        }
        goCheck(cnt + 1, connected, len);
    }

    static int setStatus(int i, int j, int d, int status) {
        int len = 0;
        while (i > 0 && i < N - 1 && j > 0 && j < N - 1) {
            i += di[d];
            j += dj[d];
            board[i][j] += status;
            len++;
        }
        return len;
    }

    static boolean canGo(int i, int j, int d) {
        while (i > 0 && i < N - 1 && j > 0 && j < N - 1) {
            i += di[d];
            j += dj[d];
            if (board[i][j] != 0) return false;
        }
        return true;
    }

    static void debug() {
        System.out.println("maxConnected = " + maxConnected);
        System.out.println("minLen = " + minLen);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
