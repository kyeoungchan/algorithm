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
			for (int i = 0; i < N; i++) // 한 글자씩 pars에 담는다.
				pars[i] = s.substring(i, i+1);
			int len = N / 4; // 한 숫자당 길이다.
			List<NumInfo> numbers = new ArrayList<>();
			StringBuilder tempSb = new StringBuilder(); // 문자열들을 더하고 빼주기 위한 StringBuilder
			for (int i = 0; i < len; i++) {
				tempSb.append(pars[i]); // 글자의 길이만큼 한 글자씩 넣어준다.
			}
			numbers.add(new NumInfo(tempSb.toString())); // numbers리스트에 NumInfo 객체를 담는다.
			for (int i = 1; i < N; i++) {
				int endI = i + len - 1; // len 길이만큼의 숫자 중 마지막 인덱스를 담는다.
				if (endI > N - 1) endI = endI - N;
				tempSb.deleteCharAt(0); // 맨 앞의 글자를 뺀다.
				tempSb.append(pars[endI]); // 마지막에 다른 글자를 붙인다.
				boolean isDupl = false;
				NumInfo numInfo = new NumInfo(tempSb.toString());
				for (NumInfo n : numbers) { // 중복 여부 확인
					if (n.equals(numInfo)) {
						isDupl = true;
						break;
					}
				}
				if (!isDupl)
					numbers.add(numInfo);
			}
			Collections.sort(numbers, Comparator.comparingInt(n -> -n.hex)); // 내림차순 정렬
			int ans = numbers.get(K - 1).hex;
			sb.append("#").append(tc).append(" ").append(ans).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}
	
	static class NumInfo {
		String symbol;
		int hex;
		
		NumInfo(String symbol) {
			// 숫자를 문자열로 받으면, 그 값을 16진수 -> 10진수로 해독한다.
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
