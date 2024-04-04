package swexpertacademy.protectfilm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 메모리:31,304kb, 시간:319ms
 */
public class Solution_2112_보호필름_연민호_최적화_전역변수활용한재귀종료 {
	static int D, W;	//행, 열
	static int K;	//합격 기준
	static int[][] film = new int[13][20];	//필름 정보
	
	static int[] A = new int[20];	//A투입 시 참조할 배열
	static int[] B = {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1};	//B투입 시 참조할 배열
	
	static boolean flag;	//합격 기준 K를 만족하는 경우를 찾으면 true
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			D = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			for(int i=0; i<D; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<W; j++) {
					film[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			sb.append("#").append(tc).append(" ");
			if(K==1) {	//K==1인 경우, 0출력 후, 다음 테케
				sb.append("0\n");	
				continue;
			}
			
			flag=false;	//초기화
			int useCnt;	//약품 투입을 사용한 횟수
			for(useCnt=0; useCnt<=K-1; useCnt++) {	
				combination(0, 0, useCnt);
				if(flag) break;
			}
			sb.append(useCnt).append("\n");
		}
		System.out.println(sb);
	}

	/**
	 * cnt번째로 약물투입할 행을 고르고 다음 투입할 행을 고르는 것은 재귀로 넘김
	 * @param cnt 현재까지 약품투입한 행의 개수
	 * @param start 다음 약품투입을 고려할 행의 시작 인덱스
	 * @param useCnt 약품투입할 행의 수
	 */
	private static void combination(int cnt, int start, int useCnt) {
		if(cnt==useCnt) {	//useCnt개수만큼의 행에 대한 약품 투입 완료
			if(!isValid()) return;	//합격기준 만족X
			
			flag = true;	//합격 기준 만족하므로 true
			return;
		}
		
		for(int r=start; r<D; r++) {
			int[] temp = film[r];//r행의 원본 배열을 저장 해놓음
			
			/**
			 * try finally를 사용한 이유?
			 * combination의 결과가 return true가 되는 상황에도 r행의 원본 배열 정보를 되돌리기 위해
			 */
			try {
				//1.r행에 A투입
				film[r] = A;
				combination(cnt+1, r+1, useCnt);
				if(flag) return;
				/*
				 * 재귀 함수 호출의 후, flag가 true라면 합격기준을 만족한 경우를 찾았으므로 
				 * 더 이상 탐색하지 않고 리턴
				 */
				
				//2.r행에 B투입
				film[r] = B;
				combination(cnt+1, r+1, useCnt);
				if(flag) return;
				
			} finally {
				film[r] = temp;		//r행의 원본 배열 되돌리기
			}
		}
	}

	/**
	 * 합격 기준 K 만족 시 true 반환
	 * @return
	 */
	public static boolean isValid() {
		A : for (int c=0; c<W; c++) {
			int length = 1;
			for (int r=1; r<D; r++) {
				//(r,c), (r-1,c) 셀이 같은 경우, length+1
				// 이 때, 증가한 length가 K라면 유효하므로 다음 열 검증(continue)
				if(film[r][c]== film[r-1][c]) {
					if(++length==K) continue A;
				}
				//(r,c), (r-1,c) 셀이 다른 경우, length 초기화
				else length = 1;
			}
			return false;	//length길이가 K가 된 적이 없다는 뜻 (유효X)
		}
		return true;	//모든 열이 유효하다는 뜻, true 반환
	}
}
