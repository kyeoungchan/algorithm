import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        char[][] map = new char[N][M];
        String s;
        int startI = 0, startJ = 0;
        for (int i = 0; i < N; i++) {
            s = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == '0') {
                    map[i][j] = '.';
                    startI = i;
                    startJ = j;
                }
            }
        }
        boolean[][][] v = new boolean[N][M][1 << 6];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{startI, startJ, 0});
        v[startI][startJ][0] = true;
        int time = 0;
        boolean hasReached = false;

        end: while (!q.isEmpty()) {
            time++;
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                int[] cur = q.poll();
                int ci = cur[0];
                int cj = cur[1];
                int key = cur[2];
                for (int d = 0; d < 4; d++) {
                    int ni = ci + di[d];
                    int nj = cj + dj[d];
                    if (ni < 0 || ni > N - 1 || nj < 0 || nj > M - 1 || map[ni][nj] == '#' || v[ni][nj][key]) continue;
                    if (map[ni][nj] == '1') {
                        hasReached = true;
                        break end;
                    }
                    if (map[ni][nj] >= 'a' && map[ni][nj] <= 'f') {
                        int newKey = key | 1 << (map[ni][nj] - 'a');
                        v[ni][nj][newKey] = true;
                        q.offer(new int[]{ni, nj, newKey});
                    } else if (map[ni][nj] >= 'A' && map[ni][nj] <= 'F') {
                        boolean canGo = (key & 1 << (map[ni][nj] - 'A')) > 0;
                        if (!canGo) continue;
                        v[ni][nj][key] = true;
                        q.offer(new int[]{ni, nj, key});
                    } else {
                        v[ni][nj][key] = true;
                        q.offer(new int[]{ni, nj, key});
                    }
                }
            }
        }
        if (!hasReached) time = -1;
        System.out.println(time);
        br.close();
    }
}