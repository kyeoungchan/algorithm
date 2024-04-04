import java.util.*;
import java.io.*;

public class Main {
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