package baekjoon.벽부수고이동하기4;

import java.util.*;
import java.io.*;

/**
 * NxM 행렬로 표현되는 맵. 0은 이동 가능, 1은 이동 불가능한 벽
 * 한 칸에서 다른 칸으로 이동하려면, 두 칸이 인접해야한다. 상하좌우 이동 얘기인듯
 * 벽의 위치에서 이동할 수 있는 칸의 개수를 세어본다? 자기 자신을 포함해서 인접한 0의 개수를 세는듯
 * 그러면 일단 원래 map에서 인접한 0의 개수를 카운트하는 맵을 만들자.
 * 이차원 배열을 하나 더 만들어서 해당 자리에 idx를 적어두고, 그 idx는 0의 개수를 갖는 이차원 배열을 또 만들자.
 * 0번 인덱스는 0개를 카운트한다.(벽의 자리)
 * 벽을 허무는 순간 자기 자신 + 1, 그리고 각 인접한 idx들의 값을 더한 값이다.
 */
public class Solution_bj_16946_벽부수고이동하기4 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][M];
        List<Integer> cntForIdx = new ArrayList<>();
        cntForIdx.add(0); // 0번 인덱스는 0
        cntForIdx.add(0); // 1번 인덱스도 0 => 벽을 나타낸다.
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j) - '0';
            }
        }

        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};

        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] v = new boolean[N][M];
        int idx = 1;
        int zeroCnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 1 || v[i][j]) continue;
                idx++;
                q.offer(new int[]{i, j});
                v[i][j] = true;
                map[i][j] = idx;
                zeroCnt++;
                while (!q.isEmpty()) {
                    int[] cur = q.poll();
                    for (int d = 0; d < 4; d++) {
                        int ni = cur[0] + di[d];
                        int nj = cur[1] + dj[d];
                        if (ni < 0 || ni > N - 1 || nj < 0 || nj > M - 1 || v[ni][nj] || map[ni][nj] == 1) continue;
                        q.offer(new int[]{ni, nj});
                        v[ni][nj] = true;
                        map[ni][nj] = idx;
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
                if (map[i][j] != 1) { // 벽이 아닌 경우
                    sb.append(0);
                    continue;
                }
                checked = new boolean[cntForIdx.size()];
                int cnt = 1; // 자기자신
                for (int d = 0; d < 4; d++) {
                    int ni = i + di[d];
                    int nj = j + dj[d];
                    if (ni < 0 || ni > N - 1 || nj < 0 || nj > M - 1 || checked[map[ni][nj]]) continue;
                    checked[map[ni][nj]] = true;
                    cnt += cntForIdx.get(map[ni][nj]);
                    cnt %= 10;
                }
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
