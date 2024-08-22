package mincoding.일정관리;

import java.io.*;
import java.util.*;

public class Solution_min_일정관리2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int Q = Integer.parseInt(br.readLine());
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int result = 0;
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            Integer leftStart = map.floorKey(start);
            if (leftStart != null && start <= map.get(leftStart)) continue;
            Integer rightStart = map.ceilingKey(start);
            if (rightStart != null && rightStart <= end) continue;
            map.put(start, end);
            result++;
        }
        System.out.println(result);
        br.close();
    }
}
