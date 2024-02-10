package baekjoon.startandlink;

import java.io.*;
import java.util.*;

public class Solution_bj_14889_스타트와링크_서울_20반_우경찬 {

	static int N, min, startScore, linkScore;
	static int[][] S;
	static int[] startTeam, linkTeam, startPair, linkPair;
	static boolean[] v, inStartTeam;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		S = new int[N + 1][N + 1];
		startTeam = new int[N / 2];
		linkTeam = new int[N / 2];
		inStartTeam = new boolean[N + 1];
		StringTokenizer st;
		min = Integer.MAX_VALUE;
		for (int i = 1; i < N + 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 1; j < N + 1; j++) {
				S[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		comb(0, 1);
		System.out.println(min);
		br.close();
	}

	static void comb(int cnt, int start) {
		if (cnt == N / 2) {
			int idx = 0;
			for (int i = 1; i < N + 1; i++) {
				if (!inStartTeam[i]) {
					linkTeam[idx++] = i; 
				}
			}
			v = new boolean[N / 2];
			getTeamScore(true);
			v = new boolean[N / 2];
			getTeamScore(false);
			min = Math.min(min, Math.abs(startScore - linkScore));
			return;
		}

		for (int i = start; i < N + 1; i++) {
			inStartTeam[i] = true;
			startTeam[cnt] = i;
			comb(cnt + 1, i + 1);
			inStartTeam[i] = false;
		}
	}

	static void getTeamScore(boolean isStartTeam) {
		if (isStartTeam) {
			startScore = 0;
			startPair = new int[2];
			perm(0, isStartTeam);
		} else {
			linkScore = 0;
			linkPair = new int[2];
			perm(0, isStartTeam);
		}

	}

	static void perm(int cnt, boolean isStartTeam) {
		if (cnt == 2) {
			if (isStartTeam) {
				startScore += S[startPair[0]][startPair[1]];
			}
			else {
				linkScore += S[linkPair[0]][linkPair[1]];
			}
			return;
		}

		for (int i = 0; i < N / 2; i++) {
			if (v[i])
				continue;
			v[i] = true;
			if (isStartTeam)
				startPair[cnt] = startTeam[i];
			else
				linkPair[cnt] = linkTeam[i];
			perm(cnt + 1, isStartTeam);
			v[i] = false;
		}
	}

}
