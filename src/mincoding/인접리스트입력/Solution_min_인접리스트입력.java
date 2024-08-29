package mincoding.인접리스트입력;

import java.io.*;
import java.util.*;

public class Solution_min_인접리스트입력 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int T  = Integer.parseInt(st.nextToken());
        List<Integer>[] graph = new List[N + 1];
        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int from  = Integer.parseInt(st.nextToken());
            int to  = Integer.parseInt(st.nextToken());
            if (graph[from] == null) graph[from] = new ArrayList<>();
            graph[from].add(to);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < N + 1; i++) {
            if (graph[i] == null) continue;
            sb.append(i).append(" :");
            for (int to : graph[i]) sb.append(" ").append(to);
            sb.append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}
