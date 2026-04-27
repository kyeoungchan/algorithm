import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int g = Integer.parseInt(st.nextToken());
        int sLen = Integer.parseInt(st.nextToken());

        String W = br.readLine();
        String S = br.readLine();

        Map<Character, Integer> countMap = new HashMap<>();
        for (int i = 0; i < g; i++) {
            char ch = W.charAt(i);
            countMap.put(ch, countMap.getOrDefault(ch, 0) + 1);
        }

        int answer = 0;
        boolean passed = false;
        for (int i = 0; i < g; i++) {
            char ch = S.charAt(i);
            if (!countMap.containsKey(ch)) {
                passed = true;
                continue;
            }
            countMap.put(ch, countMap.get(ch) - 1);
        }
        if (!passed) {
            boolean valid = true;
            for (Integer count : countMap.values()) {
                if (count != 0) { valid = false; break; }
            }
            if (valid) answer++;
        }

        skip: for (int i = g; i < sLen; i++) {
            char chL = S.charAt(i - g);
            if (countMap.containsKey(chL)) {
                countMap.put(chL, countMap.get(chL) + 1);
            }

            char chR = S.charAt(i);
            if (!countMap.containsKey(chR)) continue;
            countMap.put(chR, countMap.get(chR) - 1);

            for (Integer count : countMap.values()) {
                if (count != 0) continue skip;
            }
            // 모든 count가 0인 경우
            answer++;
        }

        System.out.println(answer);
        br.close();
    }
}