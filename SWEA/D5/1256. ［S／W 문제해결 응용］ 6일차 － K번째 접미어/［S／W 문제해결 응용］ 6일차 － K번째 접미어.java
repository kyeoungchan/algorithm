import java.io.*;
import java.util.*;

public class Solution {

    static class Trie {
        char alphabet;
        boolean last;
        int cnt;
        Map<Character, Trie> children = new HashMap<>();

        public Trie() {
        }

        public Trie(char alphabet) {
            this.alphabet = alphabet;
        }
    }

    static boolean searched;
    static int K, tc;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (tc = 1; tc < T + 1; tc++) {
            K = Integer.parseInt(br.readLine());
            String s = br.readLine();
            if (s.length() < K) {
                sb.append("#").append(tc).append(" ").append("none\n");
                continue;
            }

            Trie head = new Trie();

            for (int i = 0; i < s.length(); i++) {
                Trie idxTrie = head;
                for (int j = i; j < s.length(); j++) {
                    char cur = s.charAt(j);
                    if (!idxTrie.children.containsKey(cur)) idxTrie.children.put(cur, new Trie(cur));
                    idxTrie = idxTrie.children.get(cur);
                    idxTrie.cnt++;
                }
                idxTrie.last = true;
            }

            searched = false;
            StringBuilder tempSb = new StringBuilder();
            dfs(head, tempSb);
            sb.append("#").append(tc).append(" ").append(tempSb.toString()).append("\n");

        }
        System.out.print(sb.toString());
        br.close();
    }

    static void dfs(Trie idxTrie, StringBuilder tempSb) {
        if (idxTrie.last) {
            K--;
            if (K == 0) {
                searched = true;
                return;
            }
        }

        for (char c = 'a'; c <= 'z'; c++) {
            if (!idxTrie.children.containsKey(c)) continue;
/*
            if (idxTrie.children.get(c).cnt < K) {
                K -= idxTrie.children.get(c).cnt;
                continue;
            }
*/
            tempSb.append(c);
            dfs(idxTrie.children.get(c), tempSb);
            if (searched) return;
            tempSb.deleteCharAt(tempSb.toString().length() - 1);
        }
    }
}