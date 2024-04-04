import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        char[][] house = new char[N][N];
        int[][][] dist = new int[N][N][4];
        int startI = -1, startJ = -1, endI = -1, endJ = -1;
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < N; j++) {
                house[i][j] = s.charAt(j);
                if (house[i][j] == '#') {
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
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));
        for (int d = 0; d < 4; d++) {
            dist[startI][startJ][d] = 0;
            pq.offer(new int[]{startI, startJ, 0, d});
        }
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int ci = cur[0];
            int cj = cur[1];
            int cost = cur[2];
            int cd = cur[3];

            if (dist[ci][cj][cd] < cost) continue;
            int ni = ci + di[cd];
            int nj = cj + dj[cd];
            if (ni < 0 || ni > N - 1 || nj < 0 || nj > N - 1 || house[ni][nj] == '*') continue;
            if (house[ni][nj] == '.' && dist[ni][nj][cd] > cost) {
                dist[ni][nj][cd] = cost;
                pq.offer(new int[]{ni, nj, cost, cd});
            } else {
                int nd1 = (cd + 1) % 4;
                if (dist[ni][nj][nd1] > cost + 1) {
                    dist[ni][nj][nd1] = cost + 1;
                    pq.offer(new int[]{ni, nj, dist[ni][nj][nd1], nd1});
                }
                int nd2 = (cd + 3) % 4;
                if (dist[ni][nj][nd2] > cost + 1) {
                    dist[ni][nj][nd2] = cost + 1;
                    pq.offer(new int[]{ni, nj, dist[ni][nj][nd2], nd2});
                }
                if (dist[ni][nj][cd] > cost) {
                    dist[ni][nj][cd] = cost;
                    pq.offer(new int[]{ni, nj, cost, cd});
                }
            }
        }

        int ans = dist[endI][endJ][0];
        for (int i = 1; i < 4; i++) {
            ans = Math.min(ans, dist[endI][endJ][i]);
        }
        System.out.println(ans);
        br.close();
    }
}