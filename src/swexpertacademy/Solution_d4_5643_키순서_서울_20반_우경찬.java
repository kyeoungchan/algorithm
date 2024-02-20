package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d4_5643_키순서_서울_20반_우경찬 {

	static int N;
	static List<Integer>[] parent, child;
	static boolean[] isChild, isParent;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d4_5643.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			N = Integer.parseInt(br.readLine());
			int M = Integer.parseInt(br.readLine());
			parent = new List[N + 1];
			child = new List[N + 1];
//			int[] indegree = new int[N + 1];
			int result = 0;

			for (int i = 1; i < N + 1; i++) {
				parent[i] = new ArrayList<>();
				child[i] = new ArrayList<>();
			}
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				parent[a].add(b);
				child[b].add(a);
			}
			for (int i = 1; i < N + 1; i++) {
				isChild = new boolean[N + 1];
				getChildCount(i);
				int childCnt = 0;
				for (int j = 1; j < N + 1; j++)
					if (isChild[j]) childCnt++;
				isParent = new boolean[N + 1];
				getParentCount(i);
				int parentCnt= 0;
				for (int j = 1; j < N + 1; j++)
					if (isParent[j]) parentCnt++;

				if (childCnt + parentCnt == N - 1)
					result++;
			}
			
			sb.append("#").append(tc).append(" ").append(result).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	static void getParentCount(int i) {
		for (int j = 0; j < parent[i].size(); j++) {
			int p =(int) parent[i].get(j);
			if (isParent[p]) continue;
			isParent[p] = true;
			getParentCount(p);
		}
	}

	static void getChildCount(int i) {
		for (int j = 0; j < child[i].size(); j++) {
			int c =(int) child[i].get(j);
			if (isChild[c]) continue;
			isChild[c] = true;
			getChildCount(c);
		}
	}

	static boolean isIn(List<Integer> list, int node) {
		for (int n : list) {
			if (n == node)
				return true;
		}
		return false;
	}

	/*
	 * static void topologistSort(List[] graph, int[] indegree) {
	 * ArrayDeque<Integer> q = new ArrayDeque<>();
	 * 
	 * for (int i = 1; i < N + 1; i++) { if (indegree[i] == 0) { q.offer(i); } } if
	 * (q.size() == 1) result++; if (q.isEmpty()) { return; }
	 * 
	 * while (!q.isEmpty()) { int current = q.poll(); int cnt = 0; for (int i = 0; i
	 * < graph[current].size(); i++) { int next = (int)graph[current].get(i);
	 * indegree[next]--; if (indegree[next] == 0) { q.offer(next); cnt++; } } if
	 * (q.size() == 1 && cnt == 1) result++;
	 * 
	 * } }
	 */
}
