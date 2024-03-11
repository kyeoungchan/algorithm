package test.a1;

import java.io.*;
import java.util.*;


public class Solution2 {

	static int N, ANS;
	static int[][] houseInfo;
	static List<Integer>[][] idMap;
	static int[][] cntMap, distanceMap;
	static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
	static boolean[] charge;
	
	public static void main(String[] args) throws Exception {
	
		System.setIn(new FileInputStream("res/input2.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
	
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			N = Integer.parseInt(br.readLine());
			houseInfo = new int[N + 1][3];
			for (int i = 1; i < N + 1; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				houseInfo[i][0] = Integer.parseInt(st.nextToken()) + 15;
				houseInfo[i][1] = Integer.parseInt(st.nextToken()) + 15;
				houseInfo[i][2] = Integer.parseInt(st.nextToken());
			}
			idMap = new List[31][31];
			cntMap = new int[31][31];
			distanceMap = new int[31][31];
			
			int max = 0;
			List<int[]> maxPoses = new ArrayList<>();
			for (int i = 0; i < 31; i++) {
				for (int j = 0; j < 31; j++) {
					for (int id = 1; id < N + 1; id++) {
						int hi = houseInfo[id][0];
						int hj = houseInfo[id][1];
						int dist = getDistance(hi, hj, i, j);
						if (dist > 0 && dist <= houseInfo[id][2]) {
							if (idMap[i][j] == null) {
								idMap[i][j] = new ArrayList<>();
							}
							idMap[i][j].add(id);
							cntMap[i][j]++;
							distanceMap[i][j] += dist;
							if (max < cntMap[i][j]) {
								max = cntMap[i][j];
								maxPoses.clear();
								maxPoses.add(new int[] {i, j});
							} else if (max == cntMap[i][j]) {
								maxPoses.add(new int[] {i, j});
							}
						}
					}
				}
			}
			ANS = Integer.MAX_VALUE;
			if (max == N) {
				// 하나의 충전소로 모든 집이 충전 가능한 경우
				for (int i = 0; i < maxPoses.size(); i++) {
					int ni = maxPoses.get(i)[0];
					int nj = maxPoses.get(i)[1];
					ANS = Math.min(ANS, distanceMap[ni][nj]);
				}
			} else {
				for (int i = 0; i < maxPoses.size(); i++) {
					int ni = maxPoses.get(i)[0];
					int nj = maxPoses.get(i)[1];
					List<Integer> ids = idMap[ni][nj];
					charge = new boolean[N + 1];
					for (int id : ids) {
						charge[id] = true;
					}
					boolean sndPossible = true;
					
					for (int ii = 0; ii < 31; ii++) {
						for (int jj = 0; jj < 31; jj++) {
							if (idMap[ii][jj] == null) continue;
							sndPossible = true;
							for (int id = 1; id < N + 1; id++) {
								if (!charge[id]) {
									if (!idMap[ii][jj].contains(id)) {
										sndPossible = false;
										break;
									}
								}
							}
							if (sndPossible) {
								int temp = 0;
								for (int id = 1; id < N + 1; id++) {
									int hi = houseInfo[id][0];
									int hj = houseInfo[id][1];
									int a = getDistance(ni, nj, hi, hj);
									int b = getDistance(ii, jj, hi, hj);
									if (a != 0 && b != 0) temp += Math.min(a, b);
									else if (a == 0) temp += b;
									else if (b == 0) temp += a;
								}
								ANS = Math.min(ANS, temp);
							}
						}
					}
					
					for (int id : ids) {
						charge[id] = false;
					}
				}
				if (ANS == Integer.MAX_VALUE) ANS = -1;
			}
			sb.append("#").append(tc).append(" ").append(ANS).append("\n");
			
		}
		System.out.println(sb.toString());
		br.close();
	}
	
	static int getDistance(int fromI, int fromJ, int toI, int toJ) {
		return Math.abs(fromI - toI) + Math.abs(fromJ - toJ);
	}

}
