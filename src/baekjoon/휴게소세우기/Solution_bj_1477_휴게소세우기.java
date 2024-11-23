package baekjoon.휴게소세우기;

import java.io.*;
import java.util.*;

public class Solution_bj_1477_휴게소세우기 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken()); // 현재 휴게소의 개수
        int M = Integer.parseInt(st.nextToken()); // 더 지으려고 하는 휴게소의 개수
        int L = Integer.parseInt(st.nextToken()); // 고속도로의 길이

        List<Integer> poses = new ArrayList<>();

        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            int pos = Integer.parseInt(st.nextToken());
            poses.add(pos);
        }

        Collections.sort(poses);
        List<Integer> dists = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (i == 0) {
                dists.add(poses.get(i));
            } else {
                dists.add(poses.get(i) - poses.get(i - 1));
            }
            if (i == N - 1) {
                dists.add(L - poses.get(i));
            }
        }
        if (N == 0) dists.add(L);
        dists.sort(Collections.reverseOrder());

        int left = 1, right = L;
        while (left <= right) {
            int mid = left + (right - left) / 2;
//            System.out.println("\nmid = " + mid);
            if (!canSet(mid, dists, M)) {
//                System.out.println("cannot");
                left = mid + 1;
            } else {
//                System.out.println("can");
                right = mid - 1;
            }
        }
        System.out.println(left);
        br.close();
    }

    static boolean canSet(int mid, List<Integer> dists, int M) {
        for (int dist : dists) {
/*
            System.out.println("dist = " + dist);
            System.out.println("(dist / mid) = " + (dist / mid));
*/
            M -= dist / mid;
            if (dist % mid == 0) M++;
//            System.out.println("M = " + M);
            if (M < 0) return false;
        }
        return true;
    }
}
