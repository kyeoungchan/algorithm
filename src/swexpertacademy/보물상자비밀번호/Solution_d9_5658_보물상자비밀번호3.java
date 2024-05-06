package swexpertacademy.보물상자비밀번호;

import java.io.*;
import java.util.*;

public class Solution_d9_5658_보물상자비밀번호3 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_5658.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            String code = br.readLine();
            int oneSide = N / 4;
            code += code.substring(0, oneSide - 1); // 처음 한 변의 문자들 중에서 마지막 꺼만 빼고 가져온다.
            TreeSet<String> ts = new TreeSet<>(Comparator.reverseOrder());
            for (int i = 0; i < N; i++) {
                ts.add(code.substring(i, i + oneSide));
            }
            List<String> list = new ArrayList<>(ts);
            int ans = Integer.parseInt(list.get(K - 1), 16);
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}
