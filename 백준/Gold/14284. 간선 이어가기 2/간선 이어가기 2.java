import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[][] g = new int[N+1][N+1];
		int[] dist = new int[N + 1];
		for (int i = 1; i < N + 1; i++) dist[i] = Integer.MAX_VALUE;
		boolean[] v = new boolean[N + 1];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			g[a][b] = c;
			g[b][a] = c;
		}
		st = new StringTokenizer(br.readLine(), " ");
		int s = Integer.parseInt(st.nextToken());
		int t = Integer.parseInt(st.nextToken());
		
		dist[s] = 0;
		for (int i = 0; i < N; i++) {
			int minVertex = -1;
			int min = Integer.MAX_VALUE;
			for (int j = 1; j < N + 1; j++) {
				if (!v[j] && min > dist[j]) {
					minVertex = j;
					min = dist[j];
				}
			}
			
			v[minVertex] = true;
			if (minVertex == t) break;
			
			for (int j = 1; j < N + 1; j++) {
				if (!v[j] && g[minVertex][j] != 0 && dist[j] > g[minVertex][j] + dist[minVertex]) {
					dist[j] = g[minVertex][j] + dist[minVertex];
				}
			}
		}
		System.out.println(dist[t]);
		br.close();
	}

}