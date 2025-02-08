package mincoding.상품랭킹관리;

import java.util.*;

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
            return ranking == o.ranking ? Integer.compare(o.id, id) : Integer.compare(ranking, o.ranking);
        }

        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", category=" + category +
                    ", score=" + score +
                    ", ranking=" + ranking +
                    ", changed=" + changed +
                    '}';
        }
    }

    private PriorityQueue<Product>[] pq = new PriorityQueue[6];
    private Product[] products = new Product[7_600_000];
    private Map<Integer, Integer> idToIdx = new HashMap<>();
    private List<Integer>[] categoriesToIdx = new List[6];
    private int idx;


    public void init() {
        idToIdx.clear();
        idx = 0;
        for (int i = 0; i < 6; i++) {
            if (categoriesToIdx[i] == null) {
                categoriesToIdx[i] = new ArrayList<>();
            } else {
                categoriesToIdx[i].clear();
            }
            if (pq[i] == null) {
                pq[i] = new PriorityQueue<>();
            } else {
                pq[i].clear();
            }
        }
    }

    public void add(int mGoodsID, int mCategory, int mScore) {
        System.out.println("add");
        System.out.println("mGoodsID = " + mGoodsID);
        System.out.println("mCategory = " + mCategory);
        System.out.println("mScore = " + mScore);
        Product newProduct = createProduct(mGoodsID, mCategory, mScore);
        pq[0].offer(newProduct);
        pq[mCategory].offer(newProduct);
        System.out.println("pq[0] = " + pq[0]);
        System.out.println("pq[mCategory] = " + pq[mCategory]);
        System.out.println();
    }

    private Product createProduct(int id, int category, int score) {
        if (products[idx] == null) products[idx] = new Product();
        idToIdx.put(id, idx);
        if (!categoriesToIdx[category].contains(id)) categoriesToIdx[category].add(id);
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

    public void remove(int mGoodsID) {
        System.out.println("remove");
        System.out.println("mGoodsID = " + mGoodsID);
        Product existingProduct = products[idToIdx.get(mGoodsID)];
        existingProduct.changed = true;
//        idToIdx.remove(mGoodsID);
        System.out.println("pq[0] = " + pq[0]);
        System.out.println();
    }

    public void purchase(int mGoodsID) {
        System.out.println("purchase");
        System.out.println("mGoodsID = " + mGoodsID);
        calcScore(mGoodsID, 5);
        System.out.println();
    }

    public void takeBack(int mGoodsID) {
        System.out.println("takeBack");
        System.out.println("mGoodsID = " + mGoodsID);
        calcScore(mGoodsID, -10);
        System.out.println();
    }

    private void calcScore(int id, int score) {
        Product originProduct = products[idToIdx.get(id)];
        int category = originProduct.category;
        int originScore = originProduct.score;
        int newScore = originScore + score;
        if (newScore < 0) newScore = 0;
        else if (newScore > 100) newScore = 100;
        remove(id);
        add(id, category, newScore);
    }

    public void changeScore(int mCategory, int mChangeScore) {
        System.out.println("changeScore");
        System.out.println("mCategory = " + mCategory);
        System.out.println("mChangeScore = " + mChangeScore);
        System.out.println();
        if (mChangeScore == 0) return;
        int len = categoriesToIdx[mCategory].size();
        for (int i = 0; i < len; i++) {
            int id = categoriesToIdx[mCategory].get(i);
//            if (!idToIdx.containsKey(id)) continue;
            Product target = products[idToIdx.get(id)];
            if (target.changed) continue;
            calcScore(id, mChangeScore);
        }
        System.out.println();
        System.out.println();
    }

    public int getTopRank(int mCategory) {
        System.out.println("getTopRank");
        System.out.println("mCategory = " + mCategory);
        System.out.println("pq[mCategory] = " + pq[mCategory]);
        System.out.println();
        int res = -1;
        for (Product p : pq[mCategory]) {
            if (!p.changed) {
                res = p.id;
                break;
            }
        }

        return res;
    }
}
