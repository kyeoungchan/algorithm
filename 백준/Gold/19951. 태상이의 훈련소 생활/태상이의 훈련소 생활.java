import java.io.*;
import java.util.*;

/**
 * N개의 칸으로 이루어져있다.
 * 각 칸은 1~N번으로 이루어져 있다.
 * 조교: 총 M명
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] H = new int[N + 1];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i < N + 1; i++) {
            H[i] = Integer.parseInt(st.nextToken());
        }
        int[] status = new int[N + 2];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            status[a] += k;
            status[b + 1] -= k;
        }

        StringBuilder sb = new StringBuilder();
        int singleStatus = 0;
        for (int i = 1; i < N + 1; i++) {
            singleStatus += status[i];
            sb.append(H[i] + singleStatus).append(" ");
        }
        System.out.println(sb.toString());
        br.close();
    }
}