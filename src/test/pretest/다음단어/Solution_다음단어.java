package test.pretest.다음단어;

import java.io.*;

public class Solution_다음단어 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(br.readLine());
        next: for (int i = 0; i < N; i++) {
            String s = br.readLine();
            if (s.length() == 1) {
                sb.append("no answer\n");
                continue;
            }
            for (int j = s.length() - 2; j >= 0; j--) {
                char cur = s.charAt(j);
                char next = s.charAt(j + 1);
                if (cur < next) {
                    sb.append(s, 0, j);
                    String subString = s.substring(j + 1);
                    int idxBigger = getIdx(subString, cur);
                    sb.append(subString.charAt(idxBigger));
                    for (int k = subString.length() - 1; k > idxBigger; k--)
                        sb.append(subString.charAt(k));
                    sb.append(cur);
                    for (int k = idxBigger - 1; k >= 0; k--)
                        sb.append(subString.charAt(k));
                    sb.append("\n");
                    continue next;
                }
            }
            sb.append("no answer\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static int getIdx(String s, char cur) {
        int left = 0, right = s.length() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (s.charAt(mid) <= cur) right = mid - 1;
            else left = mid + 1;
        }
        return right;
    }
}
