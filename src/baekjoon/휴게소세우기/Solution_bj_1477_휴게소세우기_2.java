package baekjoon.휴게소세우기;

import java.io.*;
import java.util.*;

public class Solution_bj_1477_휴게소세우기_2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken()); // 현재 휴게소의 수
        int M = Integer.parseInt(st.nextToken()); // 더 지으려고하는 휴게소의 수
        int L = Integer.parseInt(st.nextToken()); // 고속도로의 길이
        int[] curPoses = new int[N];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            curPoses[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(curPoses);
        List<Integer> dists = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (i == 0) {
                dists.add(curPoses[i]);
            } else {
                dists.add(curPoses[i] - curPoses[i - 1]);
            }
            if (i == N - 1) {
                dists.add(L - curPoses[i]);
            }
        }
        if (N == 0) dists.add(L);
        dists.sort(Collections.reverseOrder());

        int left = 1, right = L;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (!canSet(mid, dists, M)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println(left);
        br.close();
    }

    static boolean canSet(int curPos, List<Integer> dists, int M) {
        for (int dist : dists) {
            M -= dist / curPos;
            if (dist % curPos == 0) M++;
            if (M < 0) return false;
        }
        return true;
    }
}
