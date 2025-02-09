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
    private Product[] products = new Product[1_000_000];
    private Map<Integer, Integer> idToIdx = new HashMap<>();
    private int idx;


    public void init() {
        idToIdx.clear();
        idx = 0;
        for (int i = 0; i < 6; i++) {
            if (pq[i] == null) {
                pq[i] = new PriorityQueue<>();
            } else {
                pq[i].clear();
            }
        }
    }

    public void add(int mGoodsID, int mCategory, int mScore) {
/*
        System.out.println("add");
        System.out.println("mGoodsID = " + mGoodsID);
        System.out.println("mCategory = " + mCategory);
        System.out.println("mScore = " + mScore);
*/
        Product newProduct = createProduct(mGoodsID, mCategory, mScore);
        pq[0].offer(newProduct);
        pq[mCategory].offer(newProduct);
/*
        System.out.println("pq[0] = " + pq[0]);
        System.out.println("pq[mCategory] = " + pq[mCategory]);
        System.out.println();
*/
    }

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

    public void remove(int mGoodsID) {
/*
        System.out.println("remove");
        System.out.println("mGoodsID = " + mGoodsID);
*/
        Product existingProduct = products[idToIdx.get(mGoodsID)];
        existingProduct.changed = true;
/*
        System.out.println("pq[0] = " + pq[0]);
        System.out.println();
*/
    }

    public void purchase(int mGoodsID) {
/*
        System.out.println("purchase");
        System.out.println("mGoodsID = " + mGoodsID);
*/
        Product originProduct = products[idToIdx.get(mGoodsID)];
        int category = originProduct.category;
        int newScore = Math.min(originProduct.score + 5, 100);
        remove(mGoodsID);
        add(mGoodsID, category, newScore);
//        System.out.println();
    }

    public void takeBack(int mGoodsID) {
/*
        System.out.println("takeBack");
        System.out.println("mGoodsID = " + mGoodsID);
*/
        Product originProduct = products[idToIdx.get(mGoodsID)];
        int category = originProduct.category;
        int newScore = Math.max(originProduct.score - 10, 0);
        remove(mGoodsID);
        add(mGoodsID, category, newScore);
//        System.out.println();
    }

    public void changeScore(int mCategory, int mChangeScore) {
/*
        System.out.println("changeScore");
        System.out.println("mCategory = " + mCategory);
        System.out.println("mChangeScore = " + mChangeScore);
        System.out.println();
*/
        if (mChangeScore == 0) return;
        PriorityQueue<Product> temp = new PriorityQueue<>();
        if (mChangeScore > 0) {
            while (!pq[mCategory].isEmpty()) {
                Product target = pq[mCategory].poll();
                if (target.changed) continue;
                target.changed = true;
                int newScore = Math.min(target.score + mChangeScore, 100);
                Product newProduct = createProduct(target.id, mCategory, newScore);
                temp.offer(newProduct);
                pq[0].offer(newProduct);
            }
        } else {
            while (!pq[mCategory].isEmpty()) {
                Product target = pq[mCategory].poll();
                if (target.changed) continue;
                target.changed = true;
                int newScore = Math.max(target.score + mChangeScore, 0);
                Product newProduct = createProduct(target.id, mCategory, newScore);
                temp.offer(newProduct);
                pq[0].offer(newProduct);
            }
        }
        pq[mCategory] = temp;
/*
        System.out.println();
        System.out.println();
*/
    }

    public int getTopRank(int mCategory) {
/*
        System.out.println("getTopRank");
        System.out.println("mCategory = " + mCategory);
        System.out.println("pq[mCategory] = " + pq[mCategory]);
        System.out.println();
*/
        int res = -1;
        while (true) {
            Product product = pq[mCategory].poll();
            if (!product.changed) {
                res = product.id;
                pq[mCategory].offer(product);
                break;
            }
        }
        return res;
    }
}
