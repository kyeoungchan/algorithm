import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int bit = (1 << N) - 1;
            sb.append("#").append(tc).append(" ");
            if ((bit & M) == bit) sb.append("ON");
            else sb.append("OFF");
            sb.append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}