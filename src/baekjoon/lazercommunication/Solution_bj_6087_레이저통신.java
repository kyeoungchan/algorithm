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
        // 빛의 방향에 따른 도달 거리의 최솟값을 담아줄 3차원 배열
        int[][][] dist = new int[H][W][4];
        int startI = -1, startJ = -1, endI = -1, endJ = -1;
        for (int i = 0; i < H; i++) {
            String s = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == 'C') {
                    if (startI == -1) {
                        // 시작점 저장
                        startI = i;
                        startJ = j;
                    } else {
                        // 도착점 저장
                        endI = i;
                        endJ = j;
                    }
                }
                for (int d = 0; d < 4; d++) {
                    // 4방 모두 가장 큰 수로 초기화
                    dist[i][j][d] = Integer.MAX_VALUE;
                }
            }
        }

        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));
        for (int d = 0; d < 4; d++) {
            // 4방으로 탐색을 시작시킨다.
            pq.offer(new int[]{startI, startJ, 0, d});
            dist[startI][startJ][d] = 0;
        }
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int ci = cur[0];
            int cj = cur[1];
            int min = cur[2];
            int cd = cur[3];

            // 이미 방문처리된 노드라면 건너뛴다.
            if (dist[ci][cj][cd] < min) continue;

            // 본 방향대로 한 칸 이동
            int ni = ci + di[cd];
            int nj = cj + dj[cd];
            // 범위 밖으로 나가거나 벽에 부딪힌다면 pq에 다시 안 담고 건너뛴다.
            if (ni < 0 || ni > H - 1 || nj < 0 || nj > W - 1 || map[ni][nj] == '*') continue;
            // 거울을 설치하여 방향이 시계방향 90도로 꺾인 경우
            int nd1 = (cd + 1) % 4;
            if (dist[ni][nj][nd1] > min + 1) {
                // 방문처리가 안 됐다면 방문 처리 및 pq에 추가
                dist[ni][nj][nd1] = min + 1;
                pq.offer(new int[]{ni, nj, dist[ni][nj][nd1], nd1});
            }

            // 거울을 설치하여 방향이 반시계방향 90도로 꺾인 경우
            int nd2 = (cd + 3) % 4;
            if (dist[ni][nj][nd2] > min + 1) {
                // 방문처리가 안 됐다면 방문 처리 및 pq에 추가
                dist[ni][nj][nd2] = min + 1;
                pq.offer(new int[]{ni, nj, dist[ni][nj][nd2], nd2});
            }

            // 거울을 설치하지 않은 경우
            if (dist[ni][nj][cd] > min) {
                // 방문처리가 안 됐다면 방문 처리 및 pq에 추가
                dist[ni][nj][cd] = min;
                pq.offer(new int[]{ni, nj, min, cd});
            }
        }
        // 도착지점에 도달한 결과 중에서도 빛의 방향에 따라서 어떤 값이 거울을 최소로 사용했는지 다르므로 4개의 값 중에서 최솟값을 꺼내서 반환한다.
        int ans = dist[endI][endJ][0];
        for (int d = 1; d < 4; d++) {
            ans = Math.min(ans, dist[endI][endJ][d]);
        }
        System.out.println(ans);
        br.close();
    }
}
