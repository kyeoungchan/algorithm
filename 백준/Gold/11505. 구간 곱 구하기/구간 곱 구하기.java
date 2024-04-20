import java.util.*;
import java.io.*;

public class Main {

    static final long mod = 1_000_000_007;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = Integer.parseInt(br.readLine());
        }
        int h = (int) Math.ceil(Math.log(N) / Math.log(2));
        int treeSize = 1 << (h + 1);
        long[] tree = new long[treeSize];
        init(tree, a, 1, 0, N - 1);
        for (int com = 0; com < M + K; com++) {
            st = new StringTokenizer(br.readLine(), " ");
            int command = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            switch (command) {
                case 1:
                    b--;
                    update(tree, a, 1, 0, N - 1, b, c);
                    break;
                case 2:
                    b--;
                    c--;
                    sb.append(query(tree, 1, 0, N - 1, b, c)).append("\n");
            }
        }
        System.out.println(sb.toString());
        br.close();

    }

    static long query(long[] tree, int node, int start, int end, int left, int right) {
        if (end < left || start > right) {
            return 1;
        }
        if (left <= start && end <= right) {
            return tree[node];
        }
        long lQuery = query(tree, 2 * node, start, (start + end) / 2, left, right);
        long rQuery = query(tree, 2 * node + 1, (start + end) / 2 + 1, end, left, right);
        return (lQuery * rQuery) % mod;
    }

    static void update(long[] tree, int[] a, int node, int start, int end, int index, int val) {
        if (index < start || index > end) {
            return;
        }
        if (start == end) {
            a[index] = val;
            tree[node] = val;
            return;
        }
        update(tree, a, 2 * node, start, (start + end) / 2, index, val);
        update(tree, a, 2 * node + 1, (start + end) / 2 + 1, end, index, val);
        tree[node] = (tree[2 * node] * tree[2 * node + 1]) % mod;
    }

    static void init(long[] tree, int[] a, int node, int start, int end) {
        if (start == end) {
            tree[node] = a[start];
            return;
        }
        init(tree, a, 2 * node, start, (start + end) / 2);
        init(tree, a, 2 * node + 1, (start + end) / 2 + 1, end);
        tree[node] = (tree[node * 2] * tree[node * 2 + 1]) % mod;
    }
}