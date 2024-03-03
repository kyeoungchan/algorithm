package baekjoon.pastepaper;

import java.util.*;
import java.io.*;

public class Solution_bj_17136_색종이붙이기_서울_20반_우경찬 {

    static int ANS;
    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1}, remainPaper = {0, 5, 5, 5, 5, 5};
    static int[][] board = new int[10][10];
    static List<int[]> pastingStartPos;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        for (int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 10; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        boolean[][] v = new boolean[10][10];
        pastingStartPos = new ArrayList<>();
        ArrayDeque<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == 1 && !v[i][j]) {
                    pastingStartPos.add(new int[]{i, j});
                    q.offer(new int[]{i, j});
                    while (!q.isEmpty()) {
                        int[] cur = q.poll();
                        int ci = cur[0];
                        int cj = cur[1];
                        v[ci][cj] = true;
                        for (int d = 0; d < 4; d++) {
                            int ni = ci + di[d];
                            int nj = cj + dj[d];
                            if (ni < 0 || ni > 9 || nj < 0 || nj > 9 || board[ni][nj] == 0 || v[ni][nj]) continue;
                            q.offer(new int[]{ni, nj});
                        }
                    }
                }
            }
        }

        ANS = Integer.MAX_VALUE;
        paste(0);
        if (ANS == Integer.MAX_VALUE) ANS = -1;
        System.out.println(ANS);
        br.close();
    }

    static void paste(int idx) {
        if (idx == pastingStartPos.size()) {
            int count = 25;
            for (int p = 1; p < 6; p++) {
                count -= remainPaper[p];
            }
            ANS = Math.min(ANS, count);
            return;
        }

        boolean[][] alreadyPaste = new boolean[10][10];
        int[] startPos = pastingStartPos.get(idx);
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] v = new boolean[10][10];
        List<int[]> poses = new ArrayList<>();
        v[startPos[0]][startPos[1]] = true;
        q.offer(startPos);
        while (!q.isEmpty()) {
            int[] polled = q.poll();
            poses.add(polled);
            for (int d = 0; d < 4; d++) {

                int ni = polled[0] + di[d];
                int nj = polled[1] + dj[d];
                if (ni < 0 || ni > 9 || nj < 0 || nj > 9 || v[ni][nj] || board[ni][nj] == 0) continue;
                v[ni][nj] = true;
                q.offer(new int[]{ni, nj});
            }
        }
        Collections.sort(poses, Comparator.comparingInt((int[] p) -> p[0]).thenComparing(p -> p[1]));
        // 좌표 접근을 왼쪽 위부터 접근시킨다.
        pasteSingleArea(alreadyPaste, poses, 0, idx);
    }

    private static void pasteSingleArea(boolean[][] alreadyPaste, List<int[]> poses, int subIdx, int idx) {
        if (subIdx == poses.size()) {
            // 붙일 수 없는 경우는 계속 continue 시켰으므로 재귀호출이 발생하지 않는다. 따라서 여기까지 왔을 경우는 색종이를 붙이기를 성공한 경우가 된다.
            paste(idx + 1);
            return;
        }

        int ci = poses.get(subIdx)[0];
        int cj = poses.get(subIdx)[1];
        if (alreadyPaste[ci][cj]) {
            pasteSingleArea(alreadyPaste, poses, subIdx + 1, idx);
        }
        for (int p = 5; p > 0; p--) {
            if (!canCover(ci, cj, p, alreadyPaste)) continue;
            for (int i = ci; i < ci + p; i++) {
                for (int j = cj; j < cj + p; j++) {
                    alreadyPaste[i][j] = true;
                }
            }
            remainPaper[p]--;
            pasteSingleArea(alreadyPaste, poses, subIdx + 1, idx);
            remainPaper[p]++;
            for (int i = ci; i < ci + p; i++) {
                for (int j = cj; j < cj + p; j++) {
                    alreadyPaste[i][j] = false;
                }
            }
        }
    }

    static boolean canCover(int i, int j, int size, boolean[][] alreadyPaste) {
        if (remainPaper[size] == 0) {
            return false;
        }
        if (i + size - 1 > 9 || j + size - 1 > 9) {
            // 영역의 밖으로 튀어나가는 경우
            return false;
        }
        for (int ni = i; ni < i + size; ni++) {
            for (int nj = j; nj < j + size; nj++) {
                if (board[ni][nj] == 0 || alreadyPaste[ni][nj]) return false;
            }
        }
        return true;
    }
}
