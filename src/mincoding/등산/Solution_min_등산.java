package mincoding.등산;

import java.io.*;
import java.util.*;

public class Solution_min_등산 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        boolean upExist = false;
        int preNumber = -1;
        int preHeight = Integer.parseInt(br.readLine());
        int maxSub = 0;
        for (int i = 2; i < N + 1; i++) {
            int curHeight = Integer.parseInt(br.readLine());
            if (curHeight - preHeight > maxSub) {
                upExist = true;
                maxSub = curHeight - preHeight;
                preNumber = i - 1;
            }
            preHeight = curHeight;
        }
        if (!upExist) System.out.println(0);
        else System.out.println(preNumber + " " + (preNumber + 1));
        br.close();
    }
}
