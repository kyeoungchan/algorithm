package baekjoon.lab;

import java.util.*;
import java.io.*;

public class Solution_bj_14502_연구소_서울_20반_우경찬 {

    static int N, M, map[][], di[] = new int[]{-1, 0, 1, 0}, dj[] = new int[]{0, 1, 0, -1}, maxSafeArea;
    static List<int[]> blankPos, virusPos;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        blankPos = new ArrayList<>();
        virusPos = new ArrayList<>();

        maxSafeArea = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 0) {
                    blankPos.add(new int[]{i, j});
                } else if (map[i][j] == 2) {
                    virusPos.add(new int[]{i, j});
                }
            }
        }

        setWall(0, 0);
        System.out.println(maxSafeArea);
        br.close();
    }

    static void setWall(int cnt, int start) {
        if (cnt == 3) {
            infect();
            return;
        }

        for (int i = start; i < blankPos.size(); i++) {
            int[] pos = blankPos.get(i);
            int ni = pos[0];
            int nj = pos[1];
            map[ni][nj] = 1;
            setWall(cnt + 1, i + 1);
            map[ni][nj] = 0;
        }
    }

    static void infect() {

        int[][] tempMap = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                tempMap[i][j] = map[i][j];
            }
        }

        ArrayDeque<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < virusPos.size(); i++) {
            int[] pos = virusPos.get(i);
            int ni = pos[0];
            int nj = pos[1];
            q.offer(new int[]{ni, nj});
        }
        
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int i = cur[0];
            int j = cur[1];
            for (int d = 0; d < 4; d++) {
                int ni = i + di[d];
                int nj = j + dj[d];
                if (ni < 0 || ni >= N || nj < 0 || nj >= M || tempMap[ni][nj] != 0) continue;
                tempMap[ni][nj] = 2;
                q.offer(new int[]{ni, nj});
            }
        }

        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (tempMap[i][j] == 0) {
                    cnt++;
                }
            }
        }
        maxSafeArea = Math.max(maxSafeArea, cnt);
    }
}
