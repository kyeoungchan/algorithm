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
		int[] dist = new int[n + 1];
		int[] node = new int[n + 1];
		int[][] g = new int[n+1][n+1];
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int time = Integer.parseInt(st.nextToken());
			g[a][b] = time;
			g[b][a] = time;
		}
		
		PriorityQueue<int[]> pq;
		for (int start = 1; start < n + 1; start++) {
			Arrays.fill(dist, INF);
			pq = new PriorityQueue<>((o1,o2)->Integer.compare(o1[1], o2[1]));
			dist[start] = 0;
			pq.offer(new int[] {start, 0});
			while(!pq.isEmpty()) {
				int[] cur = pq.poll();
				int minVertex = cur[0];
				int min = cur[1];
				if (dist[minVertex] < min) continue;
				
				for (int i = 1; i < n + 1; i++) {
					if (g[minVertex][i] != 0 && dist[i] > min + g[minVertex][i]) {
						dist[i] = min + g[minVertex][i];
						pq.offer(new int[] {i, dist[i]});
						if (minVertex == start) node[i] = i;
						else {
							node[i] = node[minVertex];
						}
					}
				}
			}
			for (int i = 1; i < n + 1; i++) {
				if (i == start) sb.append('-');
				else sb.append(node[i]);
				sb.append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	
	}
}