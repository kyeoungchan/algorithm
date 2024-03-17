package example;

import java.util.*;
import java.io.*;

public class DijkstraMain {

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/dijkstra_input.txt"));
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[][] g = new int[N][N];
		boolean[] v = new boolean[N];
		int[] dist = new int[N]; // 다익스트라 d[]
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				g[i][j] = sc.nextInt();
			}
			dist[i] = Integer.MAX_VALUE;
		}
		
		// step1. MST에 포함된 정점으로 부터 각 점의 최소 가중치 찾기
//		int result = 0, cnt = 0;
		dist[0] = 0;
		for (int i = 0; i < N; i++) {
			int minVertex = -1;
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < N; j++) {
				if (!v[j] && min > dist[j]) {
					         minVertex = j;
					         min = dist[j];
				}
			}
			
			// step2. 방문처리
			v[minVertex] = true;
//			result += min;
			if (minVertex == N - 1) break;
			
			// step3. 갱신
			for (int j = 0; j < N; j++) {
				if (!v[j] && g[minVertex][j] != 0 && dist[j] > min + g[minVertex][j]) {
					                                 dist[j] = min + g[minVertex][j];
					
				}
			}
		}
		System.out.println(dist[N-1]);
		sc.close();
	}

}
