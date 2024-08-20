package mincoding.입력과출력;

import java.util.*;
import java.io.*;

public class Solution_min_입력과출력 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int type = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        int n;
        switch (type) {
            case 1:
                n = Integer.parseInt(br.readLine());
                int sum = 0;
                long cardinal = 1L;
                st = new StringTokenizer(br.readLine(), " ");
                for (int i = 0; i < n; i++) {
                    int value = Integer.parseInt(st.nextToken());
                    sum += value;
                    cardinal *= value;
                }
                sb.append(sum).append(" ").append(cardinal).append("\n");
                break;
            case 2:
                n = Integer.parseInt(br.readLine());
                String s = br.readLine();
                String longest = s;
                String shortest = s;
                for (int i = 0; i < n - 1; i++) {
                    s = br.readLine();
                    if (s.length() > longest.length()) longest = s;
                    if (s.length() < shortest.length()) shortest = s;
                }
                sb.append(longest).append("\n").append(shortest).append("\n");
                break;
            case 3:
                st = new StringTokenizer(br.readLine(), " ");
                int y = Integer.parseInt(st.nextToken());
                int x = Integer.parseInt(st.nextToken());
                int min = 100;
                int cnt = 1;
                for (int i = 0; i < y; i++) {
                    st = new StringTokenizer(br.readLine(), " ");
                    for (int j = 0; j < x; j++) {
                        int value = Integer.parseInt(st.nextToken());
                        if (value < min) {
                            min = value;
                            cnt = 1;
                        }  else if (value == min) {
                            cnt++;
                        }
                    }
                }
                sb.append(min).append("\n").append(cnt).append("개\n");
                break;
        }
        System.out.print(sb.toString());
        br.close();
    }
}
