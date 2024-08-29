package mincoding.여행가이드;

import java.io.*;
import java.util.*;


public class Solution_min_여행가이드 {

    static int N, M, C;
    static int[] travelers;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken()); // 여행객의 수
        M = Integer.parseInt(st.nextToken()); // 버스의 수
        C = Integer.parseInt(st.nextToken()); // 각 버스에 탑승 가능한 최대 인원

        int left = 0;
        int right = 0;
        travelers = new int[N];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            travelers[i] = Integer.parseInt(st.nextToken());
            right = Math.max(right, travelers[i]);
        }
        Arrays.sort(travelers);

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (possible(mid)) right = mid - 1;
            else left = mid + 1;
        }
        System.out.println(left);
        br.close();
    }

    static boolean possible(int mid) {
        int m = M, capacity = C;
        int startTime = travelers[0];
        m--;
        capacity--;
        for (int i = 1; i < N; i++) {
            if (travelers[i] - startTime > mid || capacity == 0) {
                if (m == 0) return false;
                m--;
                startTime = travelers[i];
                capacity = C;
            }
            capacity--;
        }
        return true;
    }
}
