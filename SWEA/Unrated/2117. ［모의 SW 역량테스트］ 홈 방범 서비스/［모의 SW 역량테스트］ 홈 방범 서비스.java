import java.util.*;
import java.io.*;

public class Solution {
    static int N, M;
    static int[][] map;
    static List<int[]> houses;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            map = new int[N][N];
            houses = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if (map[i][j] == 1) houses.add(new int[]{i, j});
                }
            }

            int answer = 0;
            for (int size = 2 * N - 1; size > 0; size--) {
                int cost = size * size + (size - 1) * (size - 1);
                if (cost > M * houses.size()) continue;
                // 모든 집에 다 공급하더라도 손해라면 건너뛴다.
                int maxHouseCnt = 0;
                int r = 0, c = 0;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        int houseCnt = getHouseCnt(i, j, size);
                        if (maxHouseCnt < houseCnt) {
                            maxHouseCnt = houseCnt;
                        }
                    }
                }
                int totalBenefit = M * maxHouseCnt - cost;


                if (totalBenefit >= 0) {
                    answer = Math.max(answer, maxHouseCnt);
                }
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static int getHouseCnt(int i, int j, int size) {
        int cnt = 0;
        for (int[] pos : houses) {
            if (Math.abs(i - pos[0]) + Math.abs(j - pos[1]) < size) {
                cnt++;
            }
        }
        return cnt;
    }

}