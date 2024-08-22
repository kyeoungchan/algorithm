package mincoding.인구조사;

import java.io.*;
import java.util.*;

public class Solution_min_인구조사 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        Map<String, Integer> countryHashMap = new HashMap<>();
        int countryIdx = 0;
        int[][] countryChildren = new int[201][201];

        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            temp.clear();
            st = new StringTokenizer(br.readLine(), " ");
            int countryCnt = Integer.parseInt(st.nextToken());
            for (int j = 0; j < countryCnt; j++) {
                String name = st.nextToken();
                if (!countryHashMap.containsKey(name)) countryHashMap.put(name, countryIdx++);
                if (temp.contains(countryIdx)) continue;
                temp.add(countryHashMap.get(name));
            }
            for (int r = 0; r < temp.size() - 1; r++) {
                int c1 = temp.get(r);
                for (int c = r + 1; c < temp.size(); c++) {
                    int c2 = temp.get(c);
                    countryChildren[c1][c2]++;
                    countryChildren[c2][c1]++;
                }
            }
        }


        StringBuilder sb = new StringBuilder();
        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            String name1 = st.nextToken();
            String name2 = st.nextToken();
            if (!countryHashMap.containsKey(name1) || !countryHashMap.containsKey(name2)) continue;
            int c1 = countryHashMap.get(name1);
            int c2 = countryHashMap.get(name2);
            sb.append(countryChildren[c1][c2]).append(" ");
        }
        System.out.println(sb.toString());
        br.close();
    }
}
