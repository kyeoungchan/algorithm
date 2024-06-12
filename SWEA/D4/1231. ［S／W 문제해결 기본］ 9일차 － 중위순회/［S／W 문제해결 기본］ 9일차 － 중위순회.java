import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc < 11; tc++) {
            int N = Integer.parseInt(br.readLine());
            char[] tree = new char[N + 1];
            for (int i = 1; i < N + 1; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int curNum = Integer.parseInt(st.nextToken());
                tree[i] = st.nextToken().charAt(0);
            }
            sb.append("#").append(tc).append(" ");
            preOrder(tree, sb, 1, N);
            sb.append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void preOrder(char[] tree, StringBuilder sb, int idx, int N) {
        int leftIdx = idx * 2;
        if (leftIdx < N + 1) {
            preOrder(tree, sb, leftIdx, N);
        }
        sb.append(tree[idx]);
        int right = idx * 2 + 1;
        if (right < N + 1) {
            preOrder(tree, sb, right, N);
        }
    }
}