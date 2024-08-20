package mincoding.어설픈모내기;

import java.io.*;
import java.util.*;

public class Solution_min_어설픈모내기 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        int Height = Integer.parseInt(st.nextToken());
        int Width = Integer.parseInt(st.nextToken());
        for (int i = 0; i < Height; i++) {
            String s = br.readLine();
            int sum = 0;
            for (int j = 0; j < Width; j++) {
                sum += s.charAt(j) - '0';
            }
            sb.append(sum).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}
