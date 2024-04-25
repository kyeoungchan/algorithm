import java.util.*;
import java.io.*;

public class Main {
	
	/**
	 * 
	 */
	static class Brick implements Comparable<Brick>{
		int id;
		int bottom;
		int height;
		int weight;
		
		public Brick(int id, int bottom, int height, int weight) {
			this.id = id;
			this.bottom = bottom;
			this.height = height;
			this.weight = weight;
		}

		@Override
		public int compareTo(Brick o) {
			return Integer.compare(bottom, o.bottom);
		}
		
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		List<Brick> list = new ArrayList<>();
		list.add(new Brick(0, 0, 0, 0));
		for (int id = 1; id < N + 1; id++) {
			st = new StringTokenizer(br.readLine(), " ");
			int b = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			list.add(new Brick(id, b, h, w));
		}
		Collections.sort(list);
		int max = 0;
		int[] dp = new int[N + 1];
		for (int i = 1; i < N + 1; i++) {
			for (int j = 0; j < i; j++) {
				if (list.get(j).weight > list.get(i).weight) continue;
				dp[i] = Math.max(dp[i], dp[j] + list.get(i).height);
			}
			max = Math.max(max, dp[i]);
		}

		StringBuilder sb = new StringBuilder();
		ArrayDeque<Integer> stack = new ArrayDeque<>();
		while (N > 0) {
			if(max == dp[N]) {
				stack.push(list.get(N).id);
				max -= list.get(N).height;
			}
			N--;
		}
		sb.append(stack.size()).append("\n");
		while (!stack.isEmpty()) {
			sb.append(stack.pop()).append("\n");
		}
		System.out.print(sb.toString());
		br.close();
	}

}