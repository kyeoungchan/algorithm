import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int K = Integer.parseInt(br.readLine());
            String str = br.readLine();
            if (str.length() < K) {
                // 만약 K번째 문자열이 존재하지 않는다면, “none”을 출력한다.
                sb.append("#").append(tc).append(" ").append("none").append("\n");
                continue;
            }

            List<String> strList = new ArrayList<>();
            for (int i = 0; i < str.length(); i++) {
                strList.add(str.substring(i, str.length()));
            }
            Collections.sort(strList);
            sb.append("#").append(tc).append(" ").append(strList.get(K - 1)).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}