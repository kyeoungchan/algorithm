package mincoding.전자상가;

import java.io.*;
import java.util.*;

/**
 * 2개의 부품 창고를 가진 컴퓨터 부품 업체
 * 업체는 소비자의 예산에 맞춰 컴퓨터가 최대한 성능을 내도록 견적을 짜주는 서비스를 제공한다.
 * 컴퓨터 조립 시에는 3 종류의 부품을 각각 하나씩 선택하며, 그 중 가장 성능이 낮은 부품의 성능이 컴퓨터의 성능이 된다.
 * 소비자가 지불하는 가격 = 부품 가격 + 운송료
 * 만약 최대 성능을 내는 조합이 여러개라면 가장 가격이 낮은 조합을 선택
 * 한 창고의 부품으로만 선택하는 경우, 그 곳에서 바로 출고
 * 두 창고의 부품이 모두 필요하다면, 운송료를 지불하고 부품을 모은 후 출고(창고는 2개)
 */
public class Solution_min_전자상가 {

    static class Part implements Comparable<Part> {
        int wareHouseNumber, type, price, performance;

        public void setData(int wareHouseNumber, int type, int price, int performance) {
            this.wareHouseNumber = wareHouseNumber;
            this.type = type;
            this.price = price;
            this.performance = performance;
        }

        @Override
        public int compareTo(Part o) {
            return Integer.compare(o.performance, performance);
        }
    }

    List<Part>[][] wareHouses;
    Part[] parts = new Part[4_000]; // stock는 최대 4,000번 호출
    int charge, idx, maxPerformance;

    /**
     * @param mCharge 창고 간 운송료 ( 1 <= mCharge <= 100_000 )
     */
    void init(int mCharge) {
        wareHouses = new List[2][3];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                wareHouses[i][j] = new ArrayList<>();
            }
        }
        charge = mCharge;
        maxPerformance = idx = -1;
    }

    /**
     * 부품 목록에 새로운 부품을 추가한다.
     * @param mType 부품 종류(0, 1, 2 중 하나)
     * @param mPrice 부품 가격 (1 <= mPrice <= 100_000)
     * @param mPerformance 부품 성능 (1 <= mPerformance <= 1_000_000)
     * @param mPosition 창고 번호 (0 <= mPosition <= 1)
     * @return 입고된 창고에 동일한 type인 부품의 가짓수 반환
     * 호출 횟수: 4_000
     */
    int stock(int mType, int mPrice, int mPerformance, int mPosition) {
        Part part = getPart(mType, mPrice, mPerformance, mPosition);
        wareHouses[mPosition][mType].add(part);
        maxPerformance = Math.max(maxPerformance, mPerformance);
        return wareHouses[mPosition][mType].size();
    }

    private Part getPart(int type, int price, int performance, int position) {
        idx++;
        if (parts[idx] == null) parts[idx] = new Part();
        parts[idx].setData(position, type, price, performance);
        return parts[idx];
    }

    /**
     * 소비자의 예산이 mBudget일 때 최대의 성능을 내는 부품 조합 선택
     * 부품은 품절되지 않는다. (delete 기능 없음)
     * 같은 성능이면 가격이 가장 낮은 조합 선택
     * @param mBudget 소비자의 예산 (1 <= mBudget <= 500_000)
     * @return 조립이 가능할 경우, Result.mPerformance=성능, Result.mPrice=가격
     * 불가능할 경우, Result.mPerformance = Result.mPrice = 0
     * 호출 횟수: 400 이하
     */
    Main.Result order(int mBudget) {
        Main.Result res = new Main.Result();
        int left = 1;
        int right = maxPerformance;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int finalPrice = canBuy(mid, mBudget);
            if (finalPrice != -1) {
                left = mid + 1;
                res.mPrice = finalPrice;
                res.mPerformance = mid;
            } else {
                right = mid - 1;
            }
        }
        return res;
    }


    private int canBuy(int mid, int mBudget) {
        int[][] prices = new int[2][3];

        for (int i = 0; i < 2; i++) {
            Arrays.fill(prices[i], 500_001);
            for (int j = 0; j < 3; j++) {
                for (Part part : wareHouses[i][j]) {
                    if (part.performance < mid) continue;
                    prices[i][j] = Math.min(prices[i][j], part.price);
                }
            }
        }

        int A = 0;
        int B = 0;
        int C = charge;
        for (int i = 0; i < 3; i++) {
            A += prices[0][i];
            B += prices[1][i];
            C += Math.min(prices[0][i], prices[1][i]);
        }
        int finalPrice = Math.min(A, Math.min(B, C));

        if (finalPrice > mBudget) return -1;
        return finalPrice;
    }

}
