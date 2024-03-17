package example;

import java.util.*;
import java.io.*;

public class PrimPqMain {

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
		
		int result = 0, cnt = 0;
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2)->Integer.compare(o1[1], o2[1]));
		minEdge[0] = 0;
//		System.out.println(Arrays.toString(minEdge));System.out.println();
		pq.offer(new int[] {0, minEdge[0]}); // 정점0, 가중치1
		while (!pq.isEmpty()) {
			int[] cur = pq.poll();
			int minVertex = cur[0];
			int min = cur[1];
			if (v[minVertex]) continue;
			
			v[minVertex] = true;
			result += min;
			/*
			System.out.println(Arrays.toString(v));
			System.out.println("minVertex=" + minVertex);
			System.out.println("min=" + min);
			System.out.println("result=" + result);
			*/
			if (cnt++ == N - 1) break;
			
			for (int j = 0; j < N; j++) {
				if (!v[j] && g[minVertex][j] != 0 && minEdge[j] > g[minVertex][j]) {
					                                 minEdge[j] = g[minVertex][j];
					                                 pq.offer(new int[] {j, minEdge[j]});
				}
			}
//			System.out.println(Arrays.toString(minEdge));System.out.println("=====");
		}
		System.out.println(result);
		sc.close();
	}

}
