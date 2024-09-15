package mincoding.쏟아지는별자리타일;

import java.io.*;
import java.util.*;


public class Solution_min_쏟아지는별자리타일 {

    static class Position implements Comparable<Position> {
        int r, c;

        public void setData(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public int compareTo(Position o) {
            return r == o.r ? Integer.compare(c, o.c) : Integer.compare(r, o.r);
        }
    }

    private Map<Integer, PriorityQueue<Position>> shapeToPosition = new HashMap<>(); // 각 모양마다 가장 외쪽이면서 왼쪽에 위치한 타일의 중앙 값을 구하기 위한 Map
    private int[][] positionToShape; // 위치마다 포함하는 shape 정보를 조회하기 위한 이차원 배열
    private int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1}, hashArr = new int[4];
    private int[][] plane;
    private int positionIdx;
    private Position[] positions = new Position[160_001];

    /**
     * @param N mPlane의 한 변 길이. 10 <= N <= 1,000
     * @param mPlane 0 또는 1로 구성
     */
    public void init(int N, int[][] mPlane) {
        positionIdx = 0;
        plane = mPlane;
        shapeToPosition.clear();
        positionToShape = new int[N][N]; // 네 방향 중 아무 거나 저장한다.

        for (int i = 0; i < N - 4; i++) {
            for (int j = 0; j < N - 4; j++) {
                if (!isTile(i, j)) continue;
                setData(i, j);
                /////////////////////////////////
                j += 4;
                /*
                내가 미스해서 시간이 가장 오래 걸린 부분
                for문 자체가 j++를 시켜주므로 +=5를 하면 내가 의도한 것보다 한칸 더 오른쪽으로 건너뛰게 된다.
                */
                ////////////////////////////////
            }
        }
    }

    /**
     * 별자리 타일의 조건
     * 별자리 타일은 5 * 5 크기다.
     * 별은 7개다.
     * 각 가장자리에 최소한 1개 이상의 별이 있다.
     */
    private boolean isTile(int startR, int startC) {
        int cnt = 0;
        boolean row1 = false, row5 = false, col1 = false, col5 = false;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int r = startR + i;
                int c = startC + j;
                if (plane[r][c] == 0) continue;
                if (++cnt > 7) return false;
                if (!row1 && i == 0) row1 = true;
                if (!row5 && i == 4) row5 = true;
                if (!col1 && j == 0) col1 = true;
                if (!col5 && j == 4) col5 = true;
            }
        }
        return cnt == 7 && row1 && row5 && col1 && col5;
    }

    private void setData(int startR, int startC) {
        int previousHash = 0;
        for (int d = 0; d < 4; d++) {
            int shapeHash = 0;
            for (int i = 0; i < 5; i++) {
                int r = startR + dr[(d + 2) % 4] * i;
                int c = startC + dc[(d + 2) % 4] * i;
                for (int j = 0; j < 5; j++) {
                    shapeHash *= 10;
                    shapeHash += plane[r][c];
                    if (d == 1) positionToShape[r][c] = previousHash;
                    r += dr[(d + 1) % 4];
                    c += dc[(d + 1) % 4];
                }
            }
            boolean hasSameShapeChecked = false;
            for (int i = 0; i < d; i++) {
                if (hashArr[i] == shapeHash) {
                    hasSameShapeChecked = true;
                    break;
                }
            }
            if (hasSameShapeChecked) continue;
            hashArr[d] = shapeHash;
            if (!shapeToPosition.containsKey(shapeHash)) shapeToPosition.put(shapeHash, new PriorityQueue<>());
            int midR = startR + dr[(d + 1) % 4] * 2 + dr[(d + 2) % 4] * 2;
            int midC = startC + dc[(d + 1) % 4] * 2 + dc[(d + 2) % 4] * 2;
            shapeToPosition.get(shapeHash).add(createPosition(midR, midC));
            startR += dr[(d + 1) % 4] * 4;
            startC += dc[(d + 1) % 4] * 4;
            if (d == 0) previousHash = shapeHash;
        }

    }

    private Position createPosition(int r, int c) {
        if (positions[positionIdx] == null) positions[positionIdx] = new Position();
        positions[positionIdx].setData(r, c);
        return positions[positionIdx++];
    }

    /**
     * 최대 호출 횟수: 10_000번
     * @return 별자리 개수 반환
     */
    public int getCount(int[][] mPiece) {
        int shape = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                shape *= 10;
                shape += mPiece[i][j];
            }
        }
        if (!shapeToPosition.containsKey(shape)) return 0;
        return shapeToPosition.get(shape).size();
    }

    /**
     * 평면의 mRow, mCol 셀을 덮고 있는 별자리 타일과 동일한 별자리 타일들을 모두 찾고, 찾은 타일들 중에서 가장 위쪽에 있는 타일의 중앙 값을 반환한다.
     * 평면의 (mRow, mCol) 셀은 반드시 별자리타일이 덮고 있다.
     * @return row * 10000 + col
     */
    public int getPosition(int mRow, int mCol) {
        int target = positionToShape[mRow][mCol];
//        Position targetPosition = shapeToPosition.get(target).pollFirst();
        Position targetPosition = shapeToPosition.get(target).peek();
//        shapeToPosition.get(target).add(targetPosition);
        return targetPosition.r * 10000 + targetPosition.c;
    }
}
