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
    public void init(int N, int mMap[][]) {
    }

    /**
     * 여기가 시간초과가 제일 잘 나는 구간이다.
     * 결국 init()을 잘해줘야하는데, M의 크기가 1~5다.
     * 각 구조물의 높이도 1~5다.
     * 오름차순으로만 보자. 중복 조합
     * nHr = n+r-1 C r
     * 1 -> 1 ~ 5
     * 2 -> (1,1), (1,2), (1,3), (1,4), (1,5), (2,2), (2,3), (2,4), (2,5), (3,3), ... (5,5) 15개
     * 3 -> (1,1,1) ... (5,5,5)  35개
     * 4 -> 70개
     * 5 -> 126개
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
