import java.io.*;
import java.util.*;

/**
 * 부분 문자열 구하기
 * 동일한 문자열은 중복 제거
 * love => l, o, v, e, lo, ov, ve, lov, ove, love
 * 이 문자열에 대해서 순서로 정렬하는 것을 고려해보자.
 * K번째에 나오는 문자열을 출력하는 프로그램
 */
public class Solution {

    static class Trie  {
        char alphabet;
        int cnt;
//        boolean completed; // 모든 부분 문자열을 다 봐야하므로 필요없는 필드다.
        TreeMap<Character, Trie> children = new TreeMap<>();
        Trie parent;

        public Trie() {
        }

        public Trie(char alphabet) {
            this.alphabet = alphabet;
        }
    }

    static int K;
    static boolean searched;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            K = Integer.parseInt(br.readLine());
            String s = br.readLine();

            Trie head = new Trie();
            int strLength = s.length();
            for (int i = 0; i < strLength; i++) {
                Trie cur = head;
                int cnt = 0;
                for (int j = i; j < strLength; j++) {
                    char c = s.charAt(j);
                    if (!cur.children.containsKey(c)) {
                        cur.children.put(c, new Trie(c));
                        cur.children.get(c).parent = cur;
                        cur = cur.children.get(c);
                        cnt++;
                    } else {
                        cur = cur.children.get(c);
                    }
                }

                if (cnt == 0) continue;
                for (int j = 0; j < strLength - i; j++) {
                    cur.cnt += Math.min(j + 1, cnt);
                    cur = cur.parent;
                }
                head.cnt += cnt;
            }
            if (head.cnt < K) {
                sb.append("#").append(tc).append(" none\n");
                continue;
            }

            StringBuilder result = new StringBuilder();
            Trie cur = head;
            while (true) {
                if (!cur.children.isEmpty()) {
                    cur = cur.children.pollFirstEntry().getValue();
                } else {
                    result.deleteCharAt(result.length() - 1);
                    cur = cur.parent;
                    continue;
                }

                if (cur.cnt < K) {
                    K -= cur.cnt;
                    cur = cur.parent;
                    continue;
                }
                result.append(cur.alphabet);
                K--;
                if (K == 0) {
                    break;
                }
            }
            sb.append("#").append(tc).append(" ").append(result).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

}