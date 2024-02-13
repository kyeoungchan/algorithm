package baekjoon.chickendelivery;

import java.util.*;
import java.io.*;

public class Solution_bj_15686_치킨배달_서울_20반_우경찬 {

	static int N, M, result;
	static int[][] map;
	static boolean[] isExists;
	static List<int[]>  chickenHouse, house;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		result = Integer.MAX_VALUE;
		chickenHouse = new ArrayList<>();
        house = new ArrayList<>();
		map = new int[N + 1][N + 1];
		for (int i = 1; i < N + 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 1; j < N + 1; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 2) {
					chickenHouse.add(new int[] { i, j });
				} else if (map[i][j] == 1) {
					house.add(new int[]{i, j});
				}
			}
		}

		isExists = new boolean[chickenHouse.size()];
		comb(0, 0);
        System.out.println(result);
		br.close();
	}

	static void comb(int cnt, int start) {
		if (cnt == M) {
			result = Math.min(result, getDistance());
			return;
		}

		for (int i = start; i < chickenHouse.size(); i++) {
			isExists[i] = true;
			comb(cnt + 1, i + 1);
			isExists[i] = false;
		}
	}

	static int getDistance() {
		int distance = 0;
		for (int[] housePos : house) {
			int singleDistance = Integer.MAX_VALUE;
            for (int i = 0; i < chickenHouse.size(); i++) {
                if (!isExists[i]) continue;
                singleDistance = Math.min(singleDistance, Math.abs(housePos[0] - chickenHouse.get(i)[0]) + Math.abs(housePos[1] - chickenHouse.get(i)[1]));
            }
            distance += singleDistance;
		}
        return distance;
	}

}
