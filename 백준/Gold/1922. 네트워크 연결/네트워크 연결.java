import java.util.*;
import java.io.*;

/**
 * 프림으로
 */
public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		List<int[]>[] edges = new List[N + 1];
		int[] minEdges = new int[N + 1];
		for (int i = 1; i < N  + 1; i++) {
			edges[i] = new ArrayList<>();
			minEdges[i] = Integer.MAX_VALUE;
		}
		int M = Integer.parseInt(br.readLine());
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			edges[from].add(new int[] {to, cost});
			edges[to].add(new int[] {from, cost});
		}

		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
		boolean[] v = new boolean[N + 1];
		minEdges[1] = 0;
		pq.offer(new int[] {1, 0});
		int result = 0;
		while (!pq.isEmpty()) {
			int[] cur = pq.poll();
			int minVertex = cur[0];
			int min = cur[1];
			
			if(v[minVertex]) continue;
			v[minVertex] = true;
			result += min;
			
			for (int[] edge : edges[minVertex]) {
				int to = edge[0];
				int cost = edge[1];
				if (!v[to] && minEdges[to] > cost) {
					minEdges[to] = cost;
					pq.offer(new int[] {to, cost});
				}
			}
		}
		System.out.println(result);
		br.close();
	}

}