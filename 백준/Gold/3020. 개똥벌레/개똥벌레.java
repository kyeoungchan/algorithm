import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());
        int cnt = 0;
        int min = Integer.MAX_VALUE;
        int[] up = new int[H];
        int[] down = new int[H];
        for (int j = 0; j < N / 2; j++) {
            int downVal = Integer.parseInt(br.readLine());
            int upVal = Integer.parseInt(br.readLine());
            down[downVal]++;
            up[upVal]++;
        }

        for (int i = H - 1; i > 0; i--) {
            down[i - 1] += down[i];
            up[i - 1] += up[i];
        }

        for (int i = 0; i < H; i++) {
            int conflict = 0;
            if (i != H - 1)
                conflict += up[i + 1];
            if (i != 0)
                conflict += down[H - i];
            if (conflict < min) {
                min = conflict;
                cnt = 1;
            } else if (conflict == min) {
                cnt++;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(min).append(" ").append(cnt);
        System.out.println(sb.toString());

        br.close();
    }
}