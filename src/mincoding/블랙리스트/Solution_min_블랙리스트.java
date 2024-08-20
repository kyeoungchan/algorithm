package mincoding.블랙리스트;

import java.io.*;
import java.util.*;

public class Solution_min_블랙리스트 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int height = Integer.parseInt(st.nextToken());
        int width = Integer.parseInt(st.nextToken());
        int[] idCnt = new int[100001];
        int normal = height * width;
//        int[][] apartment = new int[height][width];
        for (int i = 0; i < height; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < width; j++) {
//                apartment[i][j] = Integer.parseInt(st.nextToken());
                int id = Integer.parseInt(st.nextToken());
                idCnt[id]++;
            }
        }
        st = new StringTokenizer(br.readLine(), " ");
        int blackH = Integer.parseInt(st.nextToken());
        int blackW = Integer.parseInt(st.nextToken());
        boolean[] checked = new boolean[100001];
        int blackCnt = 0;
        for (int i = 0; i < blackH; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < blackW; j++) {
                int blackId = Integer.parseInt(st.nextToken());
                if (checked[blackId]) continue;
                checked[blackId] = true;
                blackCnt += idCnt[blackId];
                normal -= idCnt[blackId];
            }
        }
        System.out.println(blackCnt);
        System.out.println(normal);
        br.close();
    }
}
