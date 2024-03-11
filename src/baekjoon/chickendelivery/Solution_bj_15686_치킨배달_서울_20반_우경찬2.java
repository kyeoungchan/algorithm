package baekjoon.chickendelivery;

import java.util.*;
import java.io.*;

public class Solution_bj_15686_치킨배달_서울_20반_우경찬2 {

    static int N, M, map[][], ANS, picked[];
    static List<int[]> chickenHouses, houses;
    static boolean[] exist;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][N];
        chickenHouses = new ArrayList<>();
        houses = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1) {
                    houses.add(new int[]{i, j});
                } else if (map[i][j] == 2) {
                    chickenHouses.add(new int[]{i, j});
                }
            }
        }
        exist = new boolean[chickenHouses.size()];
        ANS = Integer.MAX_VALUE;
        picked = new int[M];
        comb(0, 0);
        System.out.println(ANS);
        br.close();
    }

/*
    static void comb(int cnt, int start) {
        if (cnt == M) {
            System.out.println(Arrays.toString(exist));
            int totalDist = 0;
            for (int i = 0; i < houses.size(); i++) {
                int[] housePos = houses.get(i);
                int tempDist = Integer.MAX_VALUE;
                for (int j = 0; j < chickenHouses.size(); j++) {
                    if (!exist[j]) continue;
                    int[] chickenPos = chickenHouses.get(j);
                    tempDist = Math.min(tempDist, getDistance(housePos[0], housePos[1], chickenPos[0], chickenPos[1]));
                }
                totalDist += tempDist;
            }
            ANS = Math.min(ANS, totalDist);
            return;
        }

        for (int i = start; i < chickenHouses.size(); i++) {
            exist[i] = true;
            comb(cnt + 1, i + 1);
            exist[i] = false;
        }
    }
*/

    static void comb(int cnt, int start) {
        if (cnt == M) {
            int tempTotalDist = 0;
            // 각 집마다 최소 치킨거리를 구해서 더한다.
            for (int[] house : houses) {
                int tempSingleDist = Integer.MAX_VALUE;
                for (int i = 0; i < M; i++) {
                    int chickIdx = picked[i];
                    int[] chickenHousePos = chickenHouses.get(chickIdx);
                    tempSingleDist = Math.min(tempSingleDist, getDistance(chickenHousePos[0], chickenHousePos[1], house[0], house[1]));
                }
                tempTotalDist += tempSingleDist;
            }
            ANS = Math.min(tempTotalDist, ANS);
            return;
        }

        for (int i = start; i < chickenHouses.size(); i++) {
            picked[cnt] = i;
            comb(cnt + 1, i + 1);
        }
    }

    static int getDistance(int fromI, int fromJ, int toI, int toJ) {
        return Math.abs(fromI - toI) + Math.abs(fromJ - toJ);
    }
}
