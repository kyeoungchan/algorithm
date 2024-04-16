import java.util.*;
import java.io.*;

public class Main {
	
	static final int INF = 987654321;
	static List<Node>[] edges;
	static int N, path[], dist[];
	
	static class Node implements Comparable<Node>{
		int vertext;
		int distance;
		
		public Node(int vertext, int distance) {
			this.vertext = vertext;
			this.distance = distance;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(distance, o.distance);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		edges = new List[N + 1];
		for (int i = 1; i < N + 1; i++) edges[i] = new ArrayList<>();
		
		int M = Integer.parseInt(st.nextToken());
		for (int i = 1; i < M + 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			edges[a].add(new Node(b, t));
			edges[b].add(new Node(a, t));
		}
		path = new int[N + 1];
		int min = getShortestPath();
		int answer = 0;
		for (int i = N; i > 1; i = path[i]) {
			int other = getOtherPath(path[i], i);
			if (other == INF) {
				answer = -1;
				break;
			}
			answer = Math.max(answer, other - min);
		}
		System.out.println(answer);
		br.close();
	}

	static int getOtherPath(int from, int to) {
		dist = new int[N + 1];
		Arrays.fill(dist, INF);
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[1] = 0;
		pq.offer(new Node(1, 0));
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			if (dist[cur.vertext] < cur.distance) continue;
			
			for (Node n: edges[cur.vertext]) {
				if (cur.vertext == from && n.vertext == to) continue;
				if (dist[n.vertext] > cur.distance + n.distance) {
					dist[n.vertext] = cur.distance + n.distance;
					pq.offer(new Node(n.vertext, dist[n.vertext]));
				}
			}
		}
		return dist[N];
	}
	
	static int getShortestPath() {
		dist = new int[N + 1];
		Arrays.fill(dist, INF);
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[1] = 0;
		pq.offer(new Node(1, 0));
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			if (dist[cur.vertext] < cur.distance) continue;
			
			for (Node n: edges[cur.vertext]) {
				if (dist[n.vertext] > cur.distance + n.distance) {
					dist[n.vertext] = cur.distance + n.distance;
					pq.offer(new Node(n.vertext, dist[n.vertext]));
					path[n.vertext] = cur.vertext; // 현재 경로 이전의 노드를 저장.
				}
			}
		}
		return dist[N];
	}
}