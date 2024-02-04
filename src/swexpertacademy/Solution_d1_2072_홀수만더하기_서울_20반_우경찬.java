package swexpertacademy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_d1_2072_홀수만더하기_서울_20반_우경찬 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d1_2072.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int ans = 0;

            while (st.hasMoreTokens()) {
                int num = Integer.parseInt(st.nextToken());
                if (num % 2 == 1) {
                    ans += num;
                }
            }
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }

}
