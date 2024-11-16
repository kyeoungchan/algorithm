import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int N = Integer.parseInt(st.nextToken()); // 레이스 트랙의 길이
        // 1 ~ 1_000_000
        int M = Integer.parseInt(st.nextToken()); // 심판의 수
        // 1 ~ K
        int K = Integer.parseInt(st.nextToken()); // 심판을 배치시키려는 곳의 개수
        // 2 ~ 50

        int[] positions = new int[K]; // 심판을 배치시킬 수 있는 위치들
        // 각 위치는 0 ~ N
        // 오름차순으로 주어진다.
        st = new StringTokenizer(br.readLine(), " ");
        int left = N;
        for (int i = 0; i < K; i++) {
            positions[i] = Integer.parseInt(st.nextToken());
            if (i != 0) left = Math.min(left, positions[i] - positions[i - 1]);
        }

        int right = positions[K - 1] - positions[0];

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (!canSet(mid, positions, M, K)) right = mid - 1;
            else left = mid + 1;
         }
        System.out.println(set(right, positions, M, K));
        br.close();
    }

    static boolean canSet(int mid, int[] positions, int M, int K) {
        M--;
        int lastPos = positions[0];
        for (int i = 1; i < K; i++) {
            if (positions[i] - lastPos >= mid) {
                M--;
                lastPos = positions[i];
                if (M == 0) return true;
            }
        }
        return false;
    }

    static StringBuilder set(int mid, int[] positions, int M, int K) {
        StringBuilder sb = new StringBuilder();
        sb.append(1);
        M--;
        int lastPos = positions[0];
        for (int i = 1; i < K; i++) {
            if (M > 0 && positions[i] - lastPos >= mid) {
                M--;
                lastPos = positions[i];
                sb.append(1);
            } else {
                sb.append(0);
            }
        }
        return sb;
    }
}