package baekjoon.일요일의아침데이트;

import java.io.*;
import java.util.*;

/**
 * g: 쓰레기가 있는 위치
 * .: 아무것도 없는 깨끗한 칸
 * S와 F는 세지 않는다.
 * S, F는 반드시 하나씩
 * 가장 최적의 방법으로 숲을 지나갈 때 지나가는 쓰레기의 최소의 개수와 쓰레기 옆을 지나가는 칸의 개수를 반환하라.
 */
public class Solution_bj_1445_일요일의아침데이트 {

    static class DateCourse implements Comparable<DateCourse> {
        int r, c;
        int garbagePassing, garbageAdj;

        public DateCourse(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public DateCourse(int r, int c, int garbagePassing, int garbageAdj) {
            this.r = r;
            this.c = c;
            this.garbagePassing = garbagePassing;
            this.garbageAdj = garbageAdj;
        }

        // 이미 온 방향을 제외하고 3방향으로 쓰레기가 근처인지 판단한다.
        // 만약 쓰레기를 지나온 경우는 이 메서드를 굳이 호출하지 않고 1을 더해준다.
        // 여기에서 쓰레기 위에 올라가있는 경우는 주변을 탐색을 하지 않는다!!!
        public void judgeGarbageAdj(int d) {
            int reverseD = (d + 2) % 4;
            for (int nd = 0; nd < 4; nd++) {
                if (nd == reverseD) continue;
                int nr = r + dr[nd];
                int nc = c + dc[nd];
                if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1) continue;
                if (map[nr][nc] == 'g') {
                    garbageAdj++;
                    return;
                }
            }
        }

        // 쓰레기로 차있는 칸을 되도록이면 적게 지나가자
        // 만약 되도록 적게 지나가는 경우의 수가 여러개라면, 쓰레기 옆을 지나가는 칸의 개수를 최소화해라.
        @Override
        public int compareTo(DateCourse o) {
            return garbagePassing == o.garbagePassing ?
                    Integer.compare(garbageAdj, o.garbageAdj) :
                    Integer.compare(garbagePassing, o.garbagePassing);
        }
    }

    static int N, M;
    static int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
    static char[][] map;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        // 숲의 크기: N, M (범위: 3 ~ 50)
        map = new char[N][M];
        int startR = -1;
        int startC = -1;
        int[][][] dist = new int[N][M][2];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j);
                // S: 형택이와 여자친구의 데이트 시작 장소
                if (map[i][j] == 'S') {
                    startR = i;
                    startC = j;
                } else {
                    dist[i][j][0] = Integer.MAX_VALUE;
                    dist[i][j][1] = Integer.MAX_VALUE;
                }
            }
        }


        PriorityQueue<DateCourse> pq = new PriorityQueue<>();
        pq.offer(new DateCourse(startR, startC));
        int finalGarbagePassing = -1;
        int finalGarbageAdj = -1;
        while (!pq.isEmpty()) {
            DateCourse cur = pq.poll();
            int r = cur.r;
            int c = cur.c;
            if (map[r][c] == 'F') {
                finalGarbagePassing = cur.garbagePassing;
                finalGarbageAdj = cur.garbageAdj;
                break;
            }
            if (dist[r][c][0] < cur.garbagePassing) continue;
            if (dist[r][c][0] == cur.garbagePassing && dist[r][c][1] < cur.garbageAdj) continue;
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1) continue;
                DateCourse add = new DateCourse(nr, nc, cur.garbagePassing, cur.garbageAdj);

                if (map[nr][nc] == 'S') continue;
                else if (map[nr][nc] == 'F') {
                    // F: 꽃이 있는 위치
                    if (dist[nr][nc][0] < add.garbagePassing) continue;
                    if (dist[nr][nc][0] == add.garbagePassing && dist[nr][nc][1] <= add.garbageAdj) continue;
                    dist[nr][nc][0] = add.garbagePassing;
                    dist[nr][nc][1] = add.garbageAdj;
                    pq.offer(add);
                } else if (map[nr][nc] == 'g') {
                    int newGarbagePassing = cur.garbagePassing + 1;
                    if (dist[nr][nc][0] < newGarbagePassing) continue;
                    if (dist[nr][nc][0] == newGarbagePassing && dist[nr][nc][1] <= add.garbageAdj) continue;
                    dist[nr][nc][0] = newGarbagePassing;
                    dist[nr][nc][1] = add.garbageAdj;
                    add.garbagePassing++;
                    pq.offer(add);
                } else {
                    // 만약 어떤 칸이 비어있는데, 인접한 칸에 쓰레기가 있으면 쓰레기 옆을 지나는 것!!!!
                    if (map[r][c] == 'g') {
                        // 이미 쓰레기를 지나왔다면 인접에 더해준다.
                        add.garbageAdj++;
                    } else {
                        // 그 외에는 주변에 쓰레기가 있는지 탐색한다.
                        add.judgeGarbageAdj(d);
                    }
                    if (dist[nr][nc][0] < add.garbagePassing) continue;
                    if (dist[nr][nc][0] == add.garbagePassing && dist[nr][nc][1] <= add.garbageAdj) continue;
                    dist[nr][nc][0] = add.garbagePassing;
                    dist[nr][nc][1] = add.garbageAdj;
                    pq.offer(add);
                }
            }
        }
        sb.append(finalGarbagePassing).append(" ").append(finalGarbageAdj);
        System.out.println(sb.toString());
        br.close();
    }
}
