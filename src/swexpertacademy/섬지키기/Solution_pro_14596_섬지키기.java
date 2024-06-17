package swexpertacademy.섬지키기;

import java.util.*;
import java.io.*;

/**
 * 격자 모양의 섬이 있다.
 * 섬의 크기는 NxN, 바다로 둘러쌓여있다.
 * 섬의 숫자는 각 지역의 고도를 나타낸다.
 * 바닷물은 mSeaLevel-1 이하인 지역을 침투할 수 있다.
 * 하지만 대각선으로는 침투할 수 없다.
 */
public class Solution_pro_14596_섬지키기 {

    static class Event {
        int r, c, d; // m개의 숫자 조합 중에서 가장 작은 애를 기준으로 위치와 방향.
        // 예를 들어, 1, 2, 3의 조합을 아래방향으로 놓고, 1의 위치가 0,0이라면 => r=0,c=0,d=2
        // d는 상우하좌. 제일 작은 애에서 어느방향으로 가야 구조물들로 향하는지 정보다.

        public Event(int r, int c, int d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }

    // 1~5의 길이마다의 최대 1875개의 조합 중 Event가 얼마나 들어갈 수 있는지
    List<Event>[][] events;
    int[] sizes = new int[]{0, 5, 15, 75, 1875};

    public void init(int N, int mMap[][]) {
        events = new List[6][1875];
        for (int i = 1; i < 6; i++) {
            events[i] = new List[sizes[i]];
            for (int j = 0; j < sizes[i]; j++) {
                setEvents(N, mMap, i, j);
            }
        }
    }

    private void setEvents(int N, int[][] nMap, int mLen, int shapeNum) {
        int[] structure = getStructure(mLen, shapeNum);
    }

    private int[] getStructure(int mLen, int shapeNum) {
        int[] structure = new int[mLen];
        if (mLen == 1) {
            // shapeNum은 0~4로 주어진다.
            structure[0] = shapeNum + 1;
            return structure;
        } else {
            // 2 => 5+4+3+2+1. 3 => 25+20+15+10+5. 4 => 25*(5+4+3+2+1). 5 => 125*(5+4+3+2+1)
            int mod = (int) Math.pow(5, (shapeNum - 2));
            // 2=> 0~4, 5~8, 9~11, 12~13, 14
            // 3=> 0~24, 25~44, 45~59, 60~69, 70~74
            // 4=> 0~124, 125~224
            int tempLen = mLen;
            for (int i = 1; i < 6; i++) {
                int tempMod = (mod * (6 - i));
                if (tempLen / tempMod == 0) {
                    structure[0] = i;
                    break;
                }
                tempLen -= tempMod;
            }
            // 3=> 0~24, 0~19, 0~14
            // (1,1), (1,2)...(5,5), (2,2), (2,3), ... ,(5,5)
            // 4 => 0~124, 0~99

        }
        return structure;
    }
    /**
     * 여기가 시간초과가 제일 잘 나는 구간이다.
     * 결국 init()을 잘해줘야하는데, M의 크기가 1~5다.
     * 각 구조물의 높이도 1~5다.
     * 오름차순으로만 보자. 중복 조합
     * nHr = n+r-1 C r
     * 1 -> 1 ~ 5
     * 2 -> (1,1), (1,2), (1,3), (1,4), (1,5), (2,2), (2,3), (2,4), (2,5), (3,3), ... (5,5) 15개(5+4+3+2+1)
     * 3 -> (1,1,1) (1,1,2), ... (1,1,5), (1,2,1), ... (1,2,5), (1,3,1), (1,3,2), (1,3,3), (1,3,4), ... (1,5,5)
     * (1,?,?) => 25개.
     * (2,1,2), (2,1,3), (2,1,4), (2,1,5), (2,2,2), (2,2,3),...,(2,2,5), (2,3,2),...(2,3,5), (2,4,2)...(2,4,5)
     * (2,?,?) => 20개
     * (3,1,3),(3,1,4),(3,1,5), (3,2,3),... =>15개
     * (4,?,?) => 10개
     * (5,?,?) => 5개
     * 총 5 * (1+2+3+4+5) = 75개
     * 4 -> (1,1,1,1),...,(1,1,1,5), (1,1,2,1)...(1,1,2,5),...,(1,1,5,5),(1,2,1,1),...,(1,5,5,5) => 125개
     * (2,1,1,2),(2,1,1,3),..(2,1,1,5),(2,1,2,2),..,(2,1,2,5),...,(2,5,5,5) => 100개
     * (5,1,1,5),(5,1,2,5),...,(5,1,5,5),(5,2,1,5),...,(5,5,5,5)
     * 총 25(5+4+3+2+1)
     * 5 -> 125*(5+4+3+2+1) = 1875
     * 각 경우마다의 설치할 수 있는 경우를 클래스로 저장
     * Event로 저장하자.
     * init에서 한 번 각 경우마다의 Event를 배열에 저장한다.
     */
    public int numberOfCandidate(int M, int mStructure[]) {
        return 0;
    }

    /**
     * numberOfCandidate()의 메서드와 약간 비슷하지만, 각 경우마다 구조물을 세우고 바닷물을 직접 일으킨다.
     * 각 바닷물들은 bfs로 땅을 뒤덮고, 그 결과 남아있는 지역의 최대 개수를 반환한다.
     */
    public int maxArea(int M, int mStructure[], int mSeaLevel) {
        return 0;
    }
}
