package swexpertacademy.미생물격리;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 메모리:52,216kb, 시간:1,408ms
 */
public class Solution_2382_미생물격리_연민호_리스트만사용 {
	//상1, 하2, 좌3, 우4
	static int[] dr = {0, -1, 1, 0, 0};
	static int[] dc = {0, 0, 0, -1, 1};
	
	static int N;	//맵의 크기
	static int M;	//이동하는 시간
	static int K;	//군집의 개수
	
	static int totalCnt;	//모든 군집의 미생물 수의 합
	
	static List<Microbe> microbes = new ArrayList<>();
	static class Microbe {	//군집 클래스
		int r,c;
		int cnt;	//미생물의 수
		int dir;	//군집의 이동방향
		
		int compareCnt;	//비교용 미생물의 수
		public Microbe(int r, int c, int cnt, int dir) {
			this.r = r;
			this.c = c;
			this.cnt = cnt;
			this.dir = dir;
		}
		void move() {
			compareCnt = cnt;		//비교용 미생물 수를 cnt값으로 설정
			
			//이동
			r += dr[dir];
			c += dc[dir];
			
			//이동하는 좌표가 가장자리인 경우
			if(r==0 || r==N-1 || c==0 || c==N-1) {
				dir = (dir%2==1 ? dir+1 : dir-1);			//반대방향 전환
				totalCnt -= (cnt%2==0 ? cnt/2 : cnt/2+1);	//전체 미생물 수 줄이기
				cnt /= 2;									//미생물 수 반으로 줄이기
			}
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("res/input_d9_2382.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			microbes.clear();	//초기화
			totalCnt = 0;		//초기화
			for(int i=0; i<K; i++) {
				st = new StringTokenizer(br.readLine());
				int r = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int cnt = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken());
				
				microbes.add(new Microbe(r, c, cnt, dir));
				totalCnt+=cnt;	//총 미생물 수 누적
			}
			while(M-->0) {
				for (int i=0; i<microbes.size(); i++){
					Microbe m = microbes.get(i);
					m.move();  //군집 이동
					
					//먼저 이동한 군집과 겹치는지 체크
					for(int j=0; j<=i-1; j++) {
						Microbe other = microbes.get(j);
						if(m.r!=other.r || m.c!=other.c) continue; //겹치지 않음
	
						if(m.compareCnt > other.compareCnt) { 
							m.cnt += other.cnt;     //미생물 수 합치기
							microbes.remove(other); //작은 쪽의 미생물 정보 삭제
						}
						else {
							other.cnt += m.cnt;     //미생물 수 합치기
							microbes.remove(m); //작은 쪽의 미생물 정보 삭제
						}
						i--;	//리스트에서 삭제됐으니 요소들이 한 칸씩 당겨짐
						break;	//일치하는 경우는 하나밖에 없으므로 탐색 끝냄(제거하면서 넘어왔기 때문)
					}
				}
			}
			sb.append("#").append(tc).append(" ").append(totalCnt).append("\n");
		}
		System.out.println(sb);
	}
}
