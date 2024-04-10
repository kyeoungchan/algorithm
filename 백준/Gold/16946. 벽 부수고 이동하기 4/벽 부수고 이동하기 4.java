import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][M];
        int[][] idxMap = new int[N][M];
        List<Integer> cntForIdx = new ArrayList<>();
        cntForIdx.add(0); // 0번 인덱스는 0
        int[][] resultMap = new int[N][M];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j) - '0';
            }
        }

        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};

        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] v = new boolean[N][M];
        int idx = 0;
        int zeroCnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 1 || v[i][j]) continue;
                idx++;
                q.offer(new int[]{i, j});
                v[i][j] = true;
                idxMap[i][j] = idx;
                zeroCnt++;
                while (!q.isEmpty()) {
                    int[] cur = q.poll();
                    for (int d = 0; d < 4; d++) {
                        int ni = cur[0] + di[d];
                        int nj = cur[1] + dj[d];
                        if (ni < 0 || ni > N - 1 || nj < 0 || nj > M - 1 || v[ni][nj] || map[ni][nj] == 1) continue;
                        q.offer(new int[]{ni, nj});
                        v[ni][nj] = true;
                        idxMap[ni][nj] = idx;
                        zeroCnt++;
                    }
                }
                cntForIdx.add(zeroCnt % 10);
                zeroCnt = 0;
            }
        }
//        debug(N, M, idxMap, cntForIdx);

        boolean[] checked;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0) {
                    sb.append(0);
                    continue;
                }
                checked = new boolean[cntForIdx.size()];
                int cnt = 1; // 자기자신
                for (int d = 0; d < 4; d++) {
                    int ni = i + di[d];
                    int nj = j + dj[d];
                    if (ni < 0 || ni > N - 1 || nj < 0 || nj > M - 1 || checked[idxMap[ni][nj]]) continue;
                    checked[idxMap[ni][nj]] = true;
                    cnt += cntForIdx.get(idxMap[ni][nj]);
                    cnt %= 10;
                }
//                resultMap[i][j] = cnt;
                sb.append(cnt);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void debug(int N, int M, int[][] idxMap, List<Integer> cntForIdx) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(idxMap[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("cntForIdx = " + cntForIdx);
    }
}