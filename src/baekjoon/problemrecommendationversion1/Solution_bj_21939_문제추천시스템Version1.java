package baekjoon.problemrecommendationversion1;

import java.util.*;
import java.io.*;

public class Solution_bj_21939_문제추천시스템Version1 {
	
	static class Node implements Comparable<Node> {
		int p;
		int l;
		public Node(int p, int l) {
			this.p = p;
			this.l = l;
		}
		@Override
		public int hashCode() {
			return Objects.hash(l, p);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			return l == other.l && p == other.p;
		}
		@Override
		public int compareTo(Node o) {
			return l-o.l == 0 ? p - o.p : l - o.l; 
		}
		
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int N = Integer.parseInt(br.readLine());
		HashMap<Node, Boolean> solved = new HashMap<>();
		HashMap<Integer, Node> index = new HashMap<>();
		PriorityQueue<Node> pqHard = new PriorityQueue<>(((Node n1, Node n2) -> n2.l-n1.l == 0 ? n2.p-n1.p : n2.l-n1.l));
		PriorityQueue<Node> pqEasy = new PriorityQueue<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int p = Integer.parseInt(st.nextToken());
			int l = Integer.parseInt(st.nextToken());
			Node n = new Node(p, l);
			pqHard.offer(n);
			pqEasy.offer(n);
			solved.put(n, false);
			index.put(p, n);
		}
		
		int M = Integer.parseInt(br.readLine());
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			String op = st.nextToken();
			if (op.equals("add")) {
				int p = Integer.parseInt(st.nextToken());
				int l = Integer.parseInt(st.nextToken());
				Node n = new Node(p, l);
				solved.put(n, false);
				pqHard.offer(n);
				pqEasy.offer(n);
				index.put(p, n);
			} else if (op.equals("recommend")) {
				int select = Integer.parseInt(st.nextToken());
				if (select == 1) {
					Node rec = pqHard.poll();
					while (solved.get(rec)) {
						rec = pqHard.poll();
					}
					pqHard.offer(rec);
					sb.append(rec.p).append("\n");
				} else {
					Node rec = pqEasy.poll();
					while (solved.get(rec)) {
						rec = pqEasy.poll();
					}
					pqEasy.offer(rec);
					sb.append(rec.p).append("\n");
				}
			} else {
				int p = Integer.parseInt(st.nextToken());
				solved.put(index.get(p), true);
			}
		}
		System.out.println(sb.toString());
		br.close();
	}

}
