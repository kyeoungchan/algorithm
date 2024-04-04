package baekjoon.cuttingtrees;

import java.util.*;
import java.io.*;

/**
 * 높이로 접근하려고 했는데, 그러기에는 배열을 전부 잘라야하므로 시간초과가 뜰 것 같다.
 * 한 번 정렬을 하고, 인덱스로 접근하는 방식이 더 효율적일듯하다.
 */
public class Solution_bj_2805_나무자르기 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] trees = new int[N];
        st = new StringTokenizer(br.readLine());
        int low = 0, high = 0;
        for (int i = 0; i < N; i++) {
            trees[i] = Integer.parseInt(st.nextToken());
            high = Math.max(high, trees[i]);
        }

        while (low <= high) {
            int mid = (low + high) / 2;
            if (cut(trees, mid) < M) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        System.out.println(high);
        br.close();
    }

    static long cut(int[] trees, int mid) {
        long result = 0L;
        for (int tree : trees) {
            if (tree > mid) result += tree - mid;
        }
        return result;
    }
}
