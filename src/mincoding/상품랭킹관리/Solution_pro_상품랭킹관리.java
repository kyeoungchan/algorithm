package mincoding.상품랭킹관리;

import java.util.*;
import java.io.*;

/**
 * 각 상품 정보는 상품ID, 카테고리, 점수, 랭킹으로 구성
 * 중복된 상품ID는 입력으로 주어지지는 않지만 삭제된 후 다시 추가될 수는 있다.
 * 점수 범위는 0~100, 0 미만이면 0, 100 초과면 100
 */
public class Solution_pro_상품랭킹관리 {

    static class Product implements Comparable<Product> {
        int id, category, score, ranking;
        boolean changed;

        @Override
        public int compareTo(Product o) {
            // 우선순위: 랭킹이 작은 순, id가 큰 순
            return ranking == o.ranking ? Integer.compare(o.id, id) : Integer.compare(ranking, o.ranking);
        }
    }

    private PriorityQueue<Product>[] pq = new PriorityQueue[6], tempPqs = new PriorityQueue[150];
    private Product[] products = new Product[700_000];
    private Map<Integer, Integer> idToIdx = new HashMap<>();
    private int idx, pqIdx;


    public void init() {
        idToIdx.clear();
        idx = 0;
        pqIdx = 0;
        for (int i = 1; i < 6; i++) {
            pq[i] = new PriorityQueue<>();
        }
    }

    public void add(int mGoodsID, int mCategory, int mScore) {
        Product newProduct = createProduct(mGoodsID, mCategory, mScore);
        pq[mCategory].offer(newProduct);
    }

    // 각 테케마다 new 연산을 덜 사용하기 위한 메서드.
    // 그리고 id로 해당 객체를 찾아올 수 있도록 idToIdx라는 Map에 idx를 저장한다.
    private Product createProduct(int id, int category, int score) {
        if (products[idx] == null) products[idx] = new Product();
        idToIdx.put(id, idx);

        Product newProduct = products[idx++];
        newProduct.id = id;
        newProduct.category = category;
        newProduct.score = score;
        newProduct.ranking = getRanking(score);
        newProduct.changed = false;
        return newProduct;
    }

    private int getRanking(int score) {
        if (score >= 80) return 1;
        if (score >= 60) return 2;
        if (score >= 40) return 3;
        if (score >= 20) return 4;
        return 5;
    }

    // 삭제는 그저 해당 객체가 수정되었다고만 표시한다.
    // 그러면 pq에 있는 객체를 일일이 찾아서 삭제하지 않고 나중에 꺼내봤을 때 해당 객체가 수정된 애네? 하고 버리면 된다.
    public void remove(int mGoodsID) {
        Product existingProduct = products[idToIdx.get(mGoodsID)];
        existingProduct.changed = true;
    }

    // remove메서드처럼 원래 있던 객체는 수정되었다고 표시하고, 5점을 플러스한 새로운 객체를 add 메서드를 통해 넣어준다.
    public void purchase(int mGoodsID) {
        Product originProduct = products[idToIdx.get(mGoodsID)];
        int category = originProduct.category;
        int newScore = Math.min(originProduct.score + 5, 100);
        originProduct.changed = true;
        add(mGoodsID, category, newScore);
    }

    // purchase 메서드와 마찬가지로 remove메서드처럼 원래 있던 객체는 수정되었다고 표시하고, 10점을 마이너스한 새로운 객체를 add 메서드를 통해 넣어준다.
    public void takeBack(int mGoodsID) {
        Product originProduct = products[idToIdx.get(mGoodsID)];
        int category = originProduct.category;
        int newScore = Math.max(originProduct.score - 10, 0);
        originProduct.changed = true;
        add(mGoodsID, category, newScore);
    }

    public void changeScore(int mCategory, int mChangeScore) {
        // 불필요한 연산이면 빠르게 스킵한다.
        if (mChangeScore == 0 || pq[mCategory].isEmpty()) return;
        // PQ를 순환하면서 점수를 더한 새로운 상품을 temp에 보관하고, 나중에는 PQ를 아예 temp로 바꾼다.
        PriorityQueue<Product> temp = createPq();
        if (mChangeScore > 0) {
            while (!pq[mCategory].isEmpty()) {
                Product target = pq[mCategory].poll();
                if (target.changed) continue;
                target.changed = true;
                int newScore = Math.min(target.score + mChangeScore, 100);
                Product newProduct = createProduct(target.id, mCategory, newScore);
                temp.offer(newProduct);
            }
        } else {
            while (!pq[mCategory].isEmpty()) {
                Product target = pq[mCategory].poll();
                if (target.changed) continue;
                target.changed = true;
                int newScore = Math.max(target.score + mChangeScore, 0);
                Product newProduct = createProduct(target.id, mCategory, newScore);
                temp.offer(newProduct);
            }
        }
        pq[mCategory] = temp;
    }

    private PriorityQueue<Product> createPq() {
        if (tempPqs[pqIdx] == null) tempPqs[pqIdx] = new PriorityQueue<>();
        tempPqs[pqIdx].clear();
        return tempPqs[pqIdx++];
    }

    public int getTopRank(int mCategory) {
        int res = -1;
        if (mCategory == 0) {
            // 전체 상품이라면 1~5번 카테고리 상품 중에서 제일 우선순위가 높은 상품을 하나씩 뽑아서 비교한다.
            int highestRanking = 6;
            for (int category = 1; category < 6; category++) {
                while (!pq[category].isEmpty()) {
                    Product product = pq[category].poll();
                    if (!product.changed) {
                        if (product.ranking < highestRanking) {
                            res = product.id;
                            highestRanking = product.ranking;
                        } else if (product.ranking == highestRanking && product.id > res) {
                            res = product.id;
                        }
                        pq[category].offer(product);
                        break;
                    }
                }
            }
        } else {
            // 그냥 1~5는 PQ에서 뽑아서 확인해보고 수정표시가 안돼있는 상품이면 해당 객체의 id를 반환한다.
            while (true) {
                Product product = pq[mCategory].poll();
                if (!product.changed) {
                    res = product.id;
                    pq[mCategory].offer(product);
                    break;
                }
            }
        }
        return res;
    }
}
