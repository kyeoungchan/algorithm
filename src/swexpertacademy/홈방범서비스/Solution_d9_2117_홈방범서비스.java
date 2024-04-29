package swexpertacademy.홈방범서비스;

import java.util.*;
import java.io.*;

public class Solution_d9_2117_홈방범서비스 {
    static int N, M;
    static int[][] map;
    static List<int[]> houses;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_2117.txt"));
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
                    // 만약 현재 범위로는 집들(정답 수)에 도달했더라도 비용이 문제여서 현재 범위보다 적게 그 집들에 도달했을 때 비용이 문제가 안 되는 상황이라면
                    // 그 집들보다 더 적은 집들에는 그 범위로 도달해도 비용이 문제가 안 될 리가 없다.
                    // 따라서 범위를 더 넓게 했는데 정답을 지나치고 더 적은 집들을 카운트한 것이 정답으로 나올 리가 없다.
                    // 그러므로 범위가 가장 넓을 때 도달한 집들이 비용이 문제가 안 될 때가 정답이 된다.
                    answer = maxHouseCnt;
                    break;
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
