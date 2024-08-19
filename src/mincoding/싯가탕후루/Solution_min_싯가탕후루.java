package mincoding.싯가탕후루;

import java.io.*;
import java.util.*;

public class Solution_min_싯가탕후루 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int[] priceCnt = new int[1_000_002];
        int max = 0;
        int min = 1_000_001;
        for (int i = 0; i < N; i++) {
            int price = Integer.parseInt(st.nextToken());
            priceCnt[price]++;
            max = Math.max(max, price);
            min = Math.min(min, price);
        }
        long benefit = 0L;
        int finalPrice = 0;
        for (int i = max; i >= min; i--) {
            priceCnt[i] += priceCnt[i + 1];
            long tempBenefit = (long) priceCnt[i] * i;
            if (benefit < tempBenefit) {
                benefit = tempBenefit;
                finalPrice = i;
            } else if (benefit == tempBenefit) {
                finalPrice = i;
            }
        }
        System.out.println(benefit + " " + finalPrice);
        br.close();
    }
}
