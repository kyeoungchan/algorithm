package baekjoon.레이스;

import java.io.*;
import java.util.*;

public class Solution_bj_1508_레이스 {
    static int pos1, pos2;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 레이스 트랙 길이
        // 1~1_000_000
        int M = Integer.parseInt(st.nextToken()); // 심판 수
        // 1~K
        int K = Integer.parseInt(st.nextToken()); // 심판이 정해진 위치의 개수
        // 2~50
        int[] positions = new int[K];
        st = new StringTokenizer(br.readLine());
        int min = Integer.MAX_VALUE, max = 0;
        for (int i = 0; i < K; i++) {
            positions[i] = Integer.parseInt(st.nextToken());
            min = Math.min(min, positions[i]);
            max = Math.max(max, positions[i]);
        }
        int left = 0, right = max - min;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (!canPlace(mid, positions, M, K)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        System.out.println("left = " + left);
        int[] setStatus = new int[K];

        int lastPos = positions[0];
        setStatus[0] = 1;
        M--;
        for (int i = 1; i < K && M > 0; i++) {
            if (positions[i] - lastPos >= left) {
                lastPos = positions[i];
                M--;
                setStatus[i] = 1;
            }
        }

        for (int i = 0; i < K; i++) {
            System.out.print(setStatus[i]);
        }
        System.out.println();
        br.close();
    }

    static boolean canPlace(int minDist, int[] positions, int M, int K) {
        int lastPos = positions[0];
        M--;
        for (int i = 1; i < K && M > 0; i++) {
            if (positions[i] - lastPos >= minDist) {
                lastPos = positions[i];
                M--;
            }
        }
        return M == 0;
    }
}
