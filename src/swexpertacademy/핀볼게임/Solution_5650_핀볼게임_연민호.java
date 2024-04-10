package swexpertacademy.핀볼게임;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 메모리:47,888kb, 시간:931ms
 * @author minho
 *
 */
public class Solution_5650_핀볼게임_연민호 {
	final static int TOP=0, RIGHT=1, BOT=2, LEFT=3;
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	
	static int N;
	static int[][] map = new int[100][100];
	
	static int[][] wormhole = new int[11][2];
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=T; tc++) {
			N = Integer.parseInt(br.readLine().trim());
			for(int i=6; i<=10; i++){
				Arrays.fill(wormhole[i], -1);
			}
			
			for(int i=0; i<N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					//입력 받을 때, 원홀인 경우 저장
					if(map[i][j] >= 6){
						int num = map[i][j];
						//2차원 배열 정보를 1차원정보로 저장
						if(wormhole[num][0]==-1) wormhole[num][0] = i*N + j;
						else wormhole[num][1] = i*N + j;
					}
				}
			}
			
			//step 01. 출발 위치와 진행 방향 정하기
			int maxScore=0; 
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(map[i][j]!=0) continue;	//빈 칸이 아니면 다음 좌표로
					for(int dir=0; dir<4; dir++) {
						
						//step 02. 게임 시작
						int score = game(i,j,dir);
						
						//step 03. 점수가 최대라면 갱신
						maxScore = Math.max(maxScore, score);
					}
				}
			}
			sb.append("#").append(tc).append(" ").append(maxScore).append("\n");
		}
		System.out.println(sb);
	}

	/**
	 * (sr, sc) 좌표를 시작으로 게임 진행
	 * @param sr 
	 * @param sc
	 * @param dir
	 * @return 해당 게임의 점수를 반환
	 */
	private static int game(int sr, int sc, int dir) {
		int score = 0;
		
		int r = sr;
		int c = sc;
		while(true) {
			//이동
			r += dr[dir];
			c += dc[dir];
			
			//경계 넘어감(벽 만남)
			if(r<0 || r>=N || c<0 || c>=N) {
				dir = (dir+2)%4;	//반대방향
				score++;			//점수 + 1
				continue;			
			}
			//블랙홀 만나거나, 출발위치로 돌아오는 경우, 게임 종료
			if(map[r][c]==-1) break;	//블랙홀 만남
			if(r==sr && c==sc) break;	//출발위치로 돌아옴
			
			//블록
			if(map[r][c]==1){
				if(dir==TOP || dir==RIGHT) dir = (dir+2)%4;
				else if(dir==BOT) dir=RIGHT;
				else dir=TOP;
				score++;
			}else if(map[r][c]==2){
				if(dir==BOT || dir==RIGHT) dir = (dir+2)%4;
				else if(dir==LEFT) dir=BOT;
				else dir=RIGHT;
				score++;
			}else if(map[r][c]==3){
				if(dir==BOT || dir==LEFT) dir = (dir+2)%4;
				else if(dir==RIGHT) dir=BOT;
				else dir=LEFT;
				score++;
			}else if(map[r][c]==4){
				if(dir==TOP || dir==LEFT) dir = (dir+2)%4;
				else if(dir==RIGHT) dir=TOP;
				else dir=LEFT;
				score++;
			}else if(map[r][c]==5) {
				dir = (dir+2)%4;
				score++;
			}
			//웜홀
			else if(6<=map[r][c]) {
				int num = map[r][c];
				if( wormhole[num][0] == (r*N+c) ) {
					r = wormhole[num][1]/N;
					c = wormhole[num][1]%N;
				}else{
					r = wormhole[num][0]/N;
					c = wormhole[num][0]%N;
				}
			}
		}
		return score;
	}
}
