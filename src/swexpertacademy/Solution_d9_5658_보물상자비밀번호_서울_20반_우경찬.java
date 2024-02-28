package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d9_5658_보물상자비밀번호_서울_20반_우경찬 {

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d9_5658.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			String s = br.readLine();
			String[] pars = new String[N];
			for (int i = 0; i < N; i++)
				pars[i] = s.substring(i, i+1);
			int len = N / 4;
			List<NumInfo> numbers = new ArrayList<>();
			StringBuilder tempSb = new StringBuilder();
			for (int i = 0; i < len; i++) {
				tempSb.append(pars[i]);
			}
			numbers.add(new NumInfo(tempSb.toString()));
			for (int i = 1; i < N; i++) {
				int endI = i + len - 1;
				if (endI > N - 1) endI = endI - N;
				tempSb.deleteCharAt(0);
				tempSb.append(pars[endI]);
				boolean isDupl = false;
				NumInfo numInfo = new NumInfo(tempSb.toString());
				for (NumInfo n : numbers) {
					if (n.equals(numInfo)) {
						isDupl = true;
						break;
					}
				}
				if (!isDupl)
					numbers.add(numInfo);
			}
			int ans = 0;
			int idx = 0;
			Collections.sort(numbers, Comparator.comparingInt(n -> -n.hex));
			for (NumInfo n : numbers) {
				if (idx++ == K - 1) {
					ans = n.hex;
					break;
				}
			}
			sb.append("#").append(tc).append(" ").append(ans).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}
	
	static class NumInfo {
		String symbol;
		int hex;
		
		NumInfo(String symbol) {
			this.symbol = symbol;
			hex = Integer.parseInt(symbol, 16);
		}

		@Override
		public int hashCode() {
			return Objects.hash(hex, symbol);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			NumInfo other = (NumInfo) obj;
			return hex == other.hex && Objects.equals(symbol, other.symbol);
		}
	}

}
