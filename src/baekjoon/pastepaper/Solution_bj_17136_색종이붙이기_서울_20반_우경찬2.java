package baekjoon.pastepaper;

import java.io.*;
import java.util.*;

public class Solution_bj_17136_색종이붙이기_서울_20반_우경찬2 {

    static int minCnt, restSpace;
    static int[] papers;
    static int[][] board;
    static List<int[]> space;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        board = new int[10][10];
        space = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 10; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                if (board[i][j] == 1) {
                    space.add(new int[]{i, j});
                    restSpace++;
                }
            }
        }
        papers = new int[6]; // 1~5 인덱스 => 5장
        for (int i = 1; i < 6; i++) {
            papers[i] = 5;
        }
        minCnt = Integer.MAX_VALUE;
        paste(0);
        if (minCnt == Integer.MAX_VALUE) minCnt = -1;
        System.out.println(minCnt);
        br.close();
    }

    static void paste(int cnt) {
        if (cnt == space.size()) {
            if (restSpace == 0) {
                int usedPaperCnt = 25;
                for (int size = 1; size < 6; size++) {
                    usedPaperCnt -= papers[size];
                }
                minCnt = Math.min(minCnt, usedPaperCnt);
            }
            return;
        }

        int[] cur = space.get(cnt);
        int ci = cur[0];
        int cj = cur[1];
        if (board[ci][cj] != 1) {
            // 이미 어떤 종이와도 붙일 수 없으면 가지치기
            paste(cnt + 1);
            return;
        }

        for (int size = 1; size < 6; size++) {
            if (canPaste(ci, cj, size)) {
                setStatus(ci, cj, size, -2);
                paste(cnt + size); // 최소한 가로의 길이만큼은 건너뛸 수 있으므로 +size만큼을 더해서 재귀호출
                setStatus(ci, cj, size, 2);
            }
        }
    }

    static boolean canPaste(int startI, int startJ, int size) {
        if (papers[size] < 1) return false; // 해당 사이즈의 종이가 부족하면 바로 false 반환!
        for (int i = startI; i < startI + size; i++) {
            for (int j = startJ; j < startJ + size; j++) {
                if (i < 0 || i >= 10 || j < 0 || j >= 10 || board[i][j] != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    static void setStatus(int startI, int startJ, int size, int status) {
        // 종이를 붙일 때는 -2가 status로, 종이를 다시 뗄 때는 +2가 status로 들어온다.
        for (int i = startI; i < startI + size; i++) {
            for (int j = startJ; j < startJ + size; j++) {
                board[i][j] += status;
            }
        }
        papers[size] += (status / 2);
        restSpace += ((status / 2) * size * size);
    }
}
