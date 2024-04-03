//package a0226;

import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		
		int[][] g = new int[N + 1][N + 1];
		int[] minEdge = new int[N + 1];
		for (int i = 1; i < N + 1; i++) {
			for (int j = 1; j < N + 1; j++) {
				g[i][j] = -1;
			}
			minEdge[i] = Integer.MAX_VALUE;
		}
		boolean[] v = new boolean[N + 1];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			if (g[a][b] == -1)
				g[a][b] = c;
			else
				g[a][b] = Math.min(g[a][b], c);
		}
		st = new StringTokenizer(br.readLine(), " ");
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		v[start] = true;
		for (int j = 1; j < N + 1; j++) {
			if (g[start][j] != -1)
				minEdge[j] = g[start][j];
		}
		minEdge[start] = 0;
		for (int i = 0; i < N - 1; i++) {
			int minVertex = -1;
			int min = Integer.MAX_VALUE;
			for (int j = 1; j < N + 1; j++) {
				if (!v[j] && min > minEdge[j]) {
					minVertex = j;
					min = minEdge[j];
				}
			}
			if (minVertex == -1) break;
			v[minVertex] = true;
			for (int j = 1; j < N + 1; j++) {
				if (g[minVertex][j] != -1 && minEdge[j] > minEdge[minVertex] + g[minVertex][j]) {
					minEdge[j] = g[minVertex][j] + minEdge[minVertex];
				}
			}
		}
		System.out.println(minEdge[end]);
		br.close();
	}

}
