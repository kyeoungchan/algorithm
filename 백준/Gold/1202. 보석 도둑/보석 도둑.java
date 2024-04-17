import java.util.*;
import java.io.*;

/**
 * 일단은 가방은 제일 가벼운 애부터 꺼낸다.
 * 보석은 제일 비싼 애부터, 가격이 같으면 무거운 애부터 꺼낸다.
 */
public class Main {
	
	static class Jewerly implements Comparable<Jewerly>{
		int value;
		int weight;
		
		public Jewerly(int value, int weight) {
			this.value = value;
			this.weight = weight;
		}

		@Override
		public int compareTo(Jewerly o) {
			return weight == o.weight ? Integer.compare(o.value, value) : Integer.compare(weight, o.weight);
		}
		
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
		Jewerly[] js = new Jewerly[N];
		int[] knapsacks = new int[K];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int w = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			js[i] = new Jewerly(v, w);
		}
		Arrays.sort(js);
		
		for (int i = 0; i < K; i++) {
			knapsacks[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(knapsacks);
		
		int jsIdx = 0;
		long answer = 0L;
		for (int w: knapsacks) {
			while (jsIdx < N && js[jsIdx].weight <= w) {
				pq.offer(js[jsIdx++].value);
			}
			
			if (!pq.isEmpty()) {
				answer += pq.poll();
			}
		}

		System.out.println(answer);
		br.close();
	}

}