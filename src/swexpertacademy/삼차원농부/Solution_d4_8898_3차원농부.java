package swexpertacademy.삼차원농부;

import java.io.*;
import java.util.*;

/**
 * N마리의 소와 M마리의 말
 * 각 가축들은 3차원 좌표상의 점. 모든 소는 x=c1, y=0 직선상에 존재, 모든 말들은 x=c2, y=0 직선상에 존재한다.
 * 소와 말이 가까이 있을 경우 엄청난 이산화탄소 발생
 * 두 동물간의 거리는 맨하탄 거리.
 * 지민이의 분석: 모든 소와 말 쌍에 대해서 가장 가까운 쌍의 거리와 이러한 최소 거리를 가지는 쌍의 개수
 */
public class Solution_d4_8898_3차원농부 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_8898.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            // 1 ≤ N, M ≤ 500_000
            int N = Integer.parseInt(st.nextToken()); // 소의 수
            int M = Integer.parseInt(st.nextToken()); // 말의 수
            st = new StringTokenizer(br.readLine(), " ");

            int c1 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());

            int[] cowZ = new int[N];
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++)
                cowZ[i] = Integer.parseInt(st.nextToken());
            Arrays.sort(cowZ);

            int min = Integer.MAX_VALUE;
            int count = 0;

            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < M; i++) {
                int horseZ = Integer.parseInt(st.nextToken());
                int horseIdx = binarySearch(horseZ, cowZ, N);
                int dz = Math.abs(horseZ - cowZ[horseIdx]);
                if (min > dz) {
                    min = dz;
                    count = 1;
                } else if (min == dz) {
                    count++;
                }
                if (horseIdx < N - 1) {
                    dz = Math.abs(horseZ - cowZ[horseIdx + 1]);
                    if (min > dz) {
                        min = dz;
                        count = 1;
                    } else if (min == dz) {
                        count++;
                    }
                }
            }
            sb.append("#").append(tc).append(" ").append(min + Math.abs(c1 - c2)).append(" ").append(count).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static int binarySearch(int horseZ, int[] cowZ, int N) {
        int left = 0, right = N - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (horseZ < cowZ[mid]) right = mid - 1;
            else left = mid + 1;
        }
        if (right < 0) return 0;
        else if (right > N - 1) return N - 1;
        return right;
    }
}
