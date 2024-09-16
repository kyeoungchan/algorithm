package mincoding.블록제거게임;

import java.io.*;
import java.util.*;

/**
 * 블록을 추가하는 기능
 * - 격자판의 가장 윗부분에 가로방향으로 연속된 블록들을 위치시킨다.
 * - 하나의 블록 크기는 격자판 하나의 셀 크기와 동일하다.
 * - 시간이 흐르면서 아래로 블록들은 내려간다.
 * - 블록들이 격자판 가장 아래로 내려와 더이상 내려갈 수 없으면 그 블록들은 제거되어 없어진다.
 * 블록을 제거하는 기능
 * - 블록들이 맨밑으로 내려오지 않더라도 제거시키는 방법이 존재한다.
 * - 각각의 열마다 바닥에서 가장 가까운 블록 하나가 제거된다.
 */
public class Solution_min_블록제거게임 {

    static class Block {
        int initTime, startCol, endCol, innerCnt;
        boolean[] hasRemoved;

        public Block(int initTime, int startCol, int innerCnt) {
            this.initTime = initTime;
            this.startCol = startCol;
            this.innerCnt = innerCnt;
            endCol = startCol + innerCnt; // 이 인덱스는 포함되지 않는다. exclusive
            hasRemoved = new boolean[innerCnt];
        }

        public int remove(boolean[] externalRemoved) {
            int result = 0;
            for (int i = 0; i < endCol - startCol; i++) {
                if (hasRemoved[i] || externalRemoved[startCol + i]) continue;
                externalRemoved[startCol + i] = true;
                hasRemoved[i] = true;
                result++;
            }
            innerCnt -= result;
            return result;
        }

        public boolean removedAll() {
            return innerCnt == 0;
        }

        public boolean droppedBottom(int timeStamp, int R) {
            return timeStamp - initTime >= R;
        }
    }

    private int R, C, totalCnt;
    private ArrayDeque<Block> blockQueue = new ArrayDeque<>();

    /**
     * 10 <= R <= 50
     * 10 <= C <= 10_000
     */
    void init(int R, int C) {
        this.R = R;
        this.C = C;
        totalCnt = 0;
        blockQueue.clear();
    }

    /**
     * 시각이 timeStamp일 때
     * 격자판 가장 윗부분에 가로방향의 위치 col에 len개의 연속된 블록들을 위치시킨다.
     * 격자판을 벗어나서 블록들이 위치하게 하는 col과 len값이 주어지는 경우는 없다.
     * 블록들을 위치시킨 후, 격자판에 남아있는 블록의 총 개수를 반환한다.
     * @return 격자판에 남아있는 블록의 총 개수
     * 1,000번 호출
     */
    int dropBlocks(int mTimestamp, int mCol, int mLen) {
        timeMove(mTimestamp);
        totalCnt += mLen;
        blockQueue.offer(new Block(mTimestamp, mCol, mLen));
        return totalCnt;
    }

    private void timeMove(int timeStamp) {
        while (!blockQueue.isEmpty()) {
            if (blockQueue.peek().removedAll()) {
                blockQueue.remove();
                continue;
            }
            if (!blockQueue.peek().droppedBottom(timeStamp, R)) break;
            Block block = blockQueue.poll();
            totalCnt -= block.innerCnt;
        }
    }

    /**
     * timeStamp일 때 블록들을 제거하는 기능을 사용한다.
     * @return 블록들을 제거한 후, 격자판에 남아있는 블록의 총 개수를 반환
     * 400번 호출
     */
    int removeBlocks(int mTimestamp) {
        timeMove(mTimestamp);
        ArrayDeque<Block> tempQ = new ArrayDeque<>();
        boolean[] removed = new boolean[C];
        while (!blockQueue.isEmpty()) {
            Block block = blockQueue.poll();
            int removedCnt = block.remove(removed);
            totalCnt -= removedCnt;
            if (!block.removedAll()) tempQ.offer(block);
        }
        return totalCnt;
    }
}
