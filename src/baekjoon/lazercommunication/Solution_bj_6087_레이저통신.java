package baekjoon.lazercommunication;

import java.util.*;
import java.io.*;

/**
 * 'C'로 표시되어 있는 두 칸을 레이저로 통신하기 위해 설치해야하는 거울 개수의 최솟값
 * 레이저는 C에서만 발사할 수 있다.
 * 거울 / \을 통해서 방향을 90도 회전시킬 수 있다.
 */
public class Solution_bj_6087_레이저통신 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int W = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());
        char[][] map = new char[H][W];
        int[][][] dist = new int[H][W][4];
        int startI = -1, startJ = -1, endI = -1, endJ = -1;
        for (int i = 0; i < H; i++) {
            String s = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == 'C') {
                    if (startI == -1) {
                        startI = i;
                        startJ = j;
                    } else {
                        endI = i;
                        endJ = j;
                    }
                }
                for (int d = 0; d < 4; d++) {
                    dist[i][j][d] = Integer.MAX_VALUE;
                }
            }
        }

        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));
        for (int d = 0; d < 4; d++) {
            pq.offer(new int[]{startI, startJ, 0, d});
            dist[startI][startJ][d] = 0;
        }
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int ci = cur[0];
            int cj = cur[1];
            int min = cur[2];
            int cd = cur[3];

            if (dist[ci][cj][cd] < min) continue;

            int ni = ci + di[cd];
            int nj = cj + dj[cd];
            if (ni < 0 || ni > H - 1 || nj < 0 || nj > W - 1 || map[ni][nj] == '*') continue;
            int nd1 = (cd + 1) % 4;
            if (dist[ni][nj][nd1] > min + 1) {
                dist[ni][nj][nd1] = min + 1;
                pq.offer(new int[]{ni, nj, dist[ni][nj][nd1], nd1});
            }

            int nd2 = (cd + 3) % 4;
            if (dist[ni][nj][nd2] > min + 1) {
                dist[ni][nj][nd2] = min + 1;
                pq.offer(new int[]{ni, nj, dist[ni][nj][nd2], nd2});
            }

            if (dist[ni][nj][cd] > min) {
                dist[ni][nj][cd] = min;
                pq.offer(new int[]{ni, nj, min, cd});
            }
        }
        int ans = dist[endI][endJ][0];
        for (int d = 1; d < 4; d++) {
            ans = Math.min(ans, dist[endI][endJ][d]);
        }
        System.out.println(ans);
        br.close();
    }
}
