import java.util.*;
import java.io.*;

public class Main {
	
	static final int INF = 987654321; 
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		StringBuilder sb = new StringBuilder();
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[][] dist = new int[n + 1][n + 1];
		int[][] node = new int[n + 1][n + 1];
		int[][] g = new int[n+1][n+1];
		
		for (int i = 1; i < n + 1; i++)
			Arrays.fill(dist[i], INF);
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int time = Integer.parseInt(st.nextToken());
			g[a][b] = time;
			g[b][a] = time;
			dist[a][b] = time;
			dist[b][a] = time;
			node[a][b] = b;
			node[b][a] = a;
		}
		
		for (int k = 1; k < n + 1; k++) {
			for (int i = 1; i < n + 1; i++) {
				if (i == k) continue;
				for (int j =1; j < n + 1; j++) {
					if (i==j) continue;
					int usingK = dist[i][k] + dist[k][j];
					if (dist[i][j] > usingK) {
						dist[i][j] = usingK;
						node[i][j] = node[i][k];
					}
				}
			}
		}
		
		for (int i = 1; i < n + 1; i++) {
			for (int j = 1; j < n + 1; j++) {
				if (i == j) sb.append('-');
				else sb.append(node[i][j]);
				sb.append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	
	}
}