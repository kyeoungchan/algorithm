package swexpertacademy.protectfilm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 메모리:30,016kb, 시간:2,335ms
 */
public class Solution_2112_보호필름_연민호 {
	static int D, W;	//행, 열
	static int K;	//합격 기준
	static int[][] film = new int[13][20];	//필름 정보
	
	static int[] A = new int[20];	//A투입 시 참조할 배열
	static int[] B = {1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1};	//B투입 시 참조할 배열
	
	static int min;	//약품 최소 주입 횟수
	
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
			
			min = 13;
			dfs(0, 0);
			sb.append(min).append("\n");
		}
		System.out.println(sb);
	}

	/**
	 * r행에 대한 약품투입 여부를 결정하고, 다음 행(r+1) 행에 대한 약품투입여부 결정은 재귀로 넘김
	 * @param r 약품투입 여부를 결정할 행
	 * @param useCnt 약품투입한 행의 개수
	 */
	private static void dfs(int r, int useCnt) {
		if(r==D) {	//모든 행의 약품투입 여부 결정 완료
			if(!isValid()) return;	//합격기준 만족X
			
			min = Math.min(min, useCnt);	//합격기준 만족 시 최소 약품투입횟수라면 갱신
			return;
		}
		//1.주입 X
		dfs(r+1, useCnt);
		
		int[] temp = film[r];//r행의 원본 배열을 저장 해놓음
		
		//2.r행에 A투입
		film[r] = A;
		dfs(r+1, useCnt+1);
		
		//3.r행에 B투입
		film[r] = B;
		dfs(r+1, useCnt+1);
		
		film[r] = temp;		//r행의 원본 배열 되돌리기
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
