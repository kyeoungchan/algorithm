package baekjoon.fishingking;

import java.io.*;
import java.util.*;

public class Solution_bj_17143_낚시왕_서울_20반_우경찬 {

	static int[] dI = { -1, 1, 0, 0 };
	static int[] dJ = { 0, 0, 1, -1 };
	static int R, C, M;
	static boolean[] isGone;
	static int[][] map; // 상어가 여러마리 들어갈 수 있으므로 좌표에 배열로 넣는다.

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int[][] sharkInfo = new int[M + 1][5]; // shark 정보는 인덱스로 접근가능하고, 속도, 이동방향, 크기로 주어진다.
		isGone = new boolean[M + 1];
		map = new int[R + 1][C + 1];
		int result = 0;
		for (int id = 1; id < M + 1; id++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken()); // 속도
			int d = Integer.parseInt(st.nextToken()) - 1; // 이동방향. 인덱스 접근을 위해서 -1
			int s = Integer.parseInt(st.nextToken()); // 크기
			sharkInfo[id][0] = r;
			sharkInfo[id][1] = c;
			sharkInfo[id][2] = v;
			sharkInfo[id][3] = d;
			sharkInfo[id][4] = s;
			map[r][c] = id;
		}
		int pJ = 0; // person Index로, 낚시왕이 이동한 인덱스를 뜻한다.
		while (pJ < C) {
			pJ++;
//            System.out.println();
//            for (int i = 1; i < M + 1; i++) {
//                System.out.println(Arrays.toString(sharkInfo[i]));
//            }
			result += getShark(sharkInfo, pJ);
			if (pJ == C) {
				break;
			}
			move(sharkInfo);
			eat(sharkInfo);
		}
		System.out.println(result);
		br.close();
	}

	static void move(int[][] sharkInfo) {
		for (int id = 1; id < M + 1; id++) {
			if (!isGone[id]) {
				int[] info = sharkInfo[id];
				int pI = info[0];
				int pJ = info[1];
				int d = info[3];
				int v = info[2];

				v = d < 2 ? v % (2 * (R - 1)) : v % (2 * (C - 1));
				int nI = pI + dI[d] * v;
				int nJ = pJ + dJ[d] * v;
                
				if (d < 2) {
					// 상하 방향으로만 움직이는 경우
					while (true) {
						if (nI < 1) {
							sharkInfo[id][3] = (sharkInfo[id][3] + 1) % 2; // 방향을 0과 1중 하나로 전환
							nI = 2 - nI;
						} else if (nI > R) {
							sharkInfo[id][3] = (sharkInfo[id][3] + 1) % 2; // 방향을 0과 1중 하나로 전환
							nI = 2 * R - nI;
						}
						if (nI > 0 && nI < R + 1) {
							break;
						}
					}
				} else {
					while (true) {
						// 좌우 방향으로만 움직이는 경우
						if (nJ < 1) {
							sharkInfo[id][3] = (sharkInfo[id][3] + 1) % 2 + 2; // 방향을 2와 3중 하나로 전환
							nJ = 2 - nJ;
						} else if (nJ > C) {
							sharkInfo[id][3] = (sharkInfo[id][3] + 1) % 2 + 2; // 방향을 2와 3중 하나로 전환
							nJ = 2 * C - nJ;
						}
						if (nJ > 0 && nJ < C + 1) {
							break;
						}
					}
				}
				sharkInfo[id][0] = nI;
				sharkInfo[id][1] = nJ;
//				System.out.println("nI:" + nI + " nJ:" + nJ);
				map[pI][pJ] = 0;
			}
		}
	}

	static void eat(int[][] sharkInfo) {
		for (int id = 1; id < M + 1; id++) {
			if (!isGone[id]) {
				int i = sharkInfo[id][0];
				int j = sharkInfo[id][1];
				if (map[i][j] == 0) {
					map[i][j] = id;
				} else {
					int anotherId = map[i][j];
					if (sharkInfo[id][4] > sharkInfo[anotherId][4]) {
						isGone[anotherId] = true;
						map[i][j] = id;
					} else {
						isGone[id] = true;
					}
				}
			}
		}
	}


	static int getShark(int[][] sharkInfo, int pJ) {
		int gettingId = 0;
		for (int i = 1; i < R + 1; i++) {
//			System.out.println("i:"+i+" pJ:"+pJ);
			if (map[i][pJ] != 0) {
				gettingId = map[i][pJ];
				isGone[gettingId] = true;
				map[i][pJ] = 0;
				return sharkInfo[gettingId][4];
			}
		}
		return 0;
	}

}
