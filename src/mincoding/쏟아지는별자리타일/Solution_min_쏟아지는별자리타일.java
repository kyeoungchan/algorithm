package mincoding.쏟아지는별자리타일;

import java.io.*;
import java.util.*;


/**
 *
 */
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

    static class Shape {
        int[][] tile = new int[5][5];

        @Override
        public boolean equals(Object o) {
            Shape s = (Shape) o;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (tile[i][j] != s.tile[i][j]) return false;
                }
            }
            return true;
        }
    }

    private Map<Shape, PriorityQueue<Position>> shapeToPosition = new HashMap<>(); // 각 모양마다 가장 외쪽이면서 왼쪽에 위치한 타일의 중앙 값을 구하기 위한 Map
    private Shape[][] positionToShape; // 위치마다 포함하는 shape 정보를 조회하기 위한 이차원 배열
    private int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
    private int[][] plane;
    private int shapesIdx, positionIdx;
    private Shape[] shapes = new Shape[170_001];
    private Position[] positions = new Position[160_001];

    /**
     * @param N mPlane의 한 변 길이. 10 <= N <= 1,000
     * @param mPlane 0 또는 1로 구성
     */
    public void init(int N, int[][] mPlane) {
        positionIdx = shapesIdx = 0;
        this.plane = mPlane;
        shapeToPosition.clear();
        positionToShape = new Shape[N][N]; // 네 방향 중 아무 거나 저장한다.

        for (int i = 0; i < N - 5; i++) {
            for (int j = 0; j < N - 5; j++) {
                if (!isTile(i, j)) continue;
                setData(i, j);
                j += 5;
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
                cnt++;
                if (!row1 && i == 0) row1 = true;
                if (!row5 && i == 4) row5 = true;
                if (!col1 && j == 0) col1 = true;
                if (!col5 && j == 4) col5 = true;
            }
        }
        return cnt == 7 && row1 && row5 && col1 && col5;
    }

    private void setData(int startR, int startC) {
        System.out.println("startR = " + startR);
        System.out.println("startC = " + startC);
        for (int d = 0; d < 4; d++) {
            Shape shape = createShape();
            int[][] shapeTile = shape.tile;
            System.out.println("d = " + d);
            for (int i = 0; i < 5; i++) {
                int r = startR + dr[(d + 2) % 4] * i;
                int c = startC + dc[(d + 2) % 4] * i;
                for (int j = 0; j < 5; j++) {
                    shapeTile[i][j] = plane[r][c];
                    if (d == 0) positionToShape[r][c] = shape; // 처음 모양 하나만 저장
                    r += dr[(d + 1) % 4];
                    c += dc[(d + 1) % 4];
                    if (shapeTile[i][j] == 0) System.out.print("  ");
                    else System.out.print("* ");
                }
                System.out.println();
            }
            System.out.println();
            if (!shapeToPosition.containsKey(shape)) shapeToPosition.put(shape, new PriorityQueue<>());
            int midR = startR + dr[(d + 1) % 4] * 2 + dr[(d + 2) % 4] * 2;
            int midC = startC + dc[(d + 1) % 4] * 2 + dc[(d + 2) % 4] * 2;
            shapeToPosition.get(shape).offer(createPosition(midR, midC));
            startR += dr[(d + 1) % 4] * 4;
            startC += dc[(d + 1) % 4] * 4;
        }

    }

    private Shape createShape() {
        if (shapes[shapesIdx] == null) shapes[shapesIdx] = new Shape();
        return shapes[shapesIdx++];
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
        Shape shape = createShape();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                shape.tile[i][j] = mPiece[i][j];
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
        System.out.println("**** getPsotion ****");
        System.out.println("(" + mRow + ", " + mCol + ")");
        System.out.println();
        Shape target = positionToShape[mRow][mCol];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (target.tile[i][j] == 1) System.out.print("* ");
                else System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println();
        Position targetPosition = shapeToPosition.get(target).peek();
        return targetPosition.r * 10000 + targetPosition.c;
    }
}
