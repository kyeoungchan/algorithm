package example;

import java.util.*;
import java.io.*;

public class PrimMain {

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/prim_input.txt"));
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[][] g = new int[N][N];
		boolean[] v = new boolean[N];
		int[] minEdge = new int[N]; // 프림 w[]
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				g[i][j] = sc.nextInt();
			}
			minEdge[i] = Integer.MAX_VALUE;
		}
		
		// step1. MST에 포함된 정점으로 부터 각 점의 최소 가중치 찾기
		int result = 0, cnt = 0;
		minEdge[0] = 0;
		for (int i = 0; i < N; i++) {
			int minVertex = -1;
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < N; j++) {
				if (!v[j] && min > minEdge[j]) {
					         minVertex = j;
					         min = minEdge[j];
				}
			}
			
			// step2. 방문처리
			v[minVertex] = true;
			result += min;
			if (cnt++ == N - 1) break;
			
			// step3. 갱신
			for (int j = 0; j < N; j++) {
				if (!v[j] && g[minVertex][j] != 0 && minEdge[j] > g[minVertex][j]) {
					                                 minEdge[j] = g[minVertex][j];
					
				}
			}
		}
		System.out.println(result);
		sc.close();
	}

}
