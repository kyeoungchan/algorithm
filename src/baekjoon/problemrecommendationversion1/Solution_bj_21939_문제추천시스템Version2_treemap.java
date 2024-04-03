package baekjoon.problemrecommendationversion1;

import java.util.*;
import java.io.*;

public class Solution_bj_21939_문제추천시스템Version2_treemap {
	
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
		TreeMap<Node, Integer> recommendList = new TreeMap<>();
		HashMap<Integer, Node> index = new HashMap<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int p = Integer.parseInt(st.nextToken());
			int l = Integer.parseInt(st.nextToken());
			Node n = new Node(p, l);
			recommendList.put(n, p);
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
				recommendList.put(n, p);
				index.put(p, n);
			} else if (op.equals("recommend")) {
				int select = Integer.parseInt(st.nextToken());
				Node rec;
				if (select == 1) rec = recommendList.lastKey();
				else rec = recommendList.firstKey();
				sb.append(rec.p).append("\n");
			} else {
				int p = Integer.parseInt(st.nextToken());
				recommendList.remove(index.get(p));
			}
		}
		System.out.println(sb.toString());
		br.close();
	}

}
