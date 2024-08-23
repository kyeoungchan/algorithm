package mincoding.동물애호가;

import java.util.*;

public class Solution_min_동물애호가 {

    static class Animal implements Comparable<Animal> {
        int id, adoptTime, lifespan;
        int deadDate;
        boolean changed;

        public void setData(int id, int adoptTime, int lifespan) {
            this.id = id;
            this.adoptTime = adoptTime;
            this.lifespan = lifespan;
            deadDate = adoptTime + lifespan;
            changed = false;
        }

        @Override
        public int compareTo(Animal o) {
            return Integer.compare(deadDate, o.deadDate);
        }
    }

    private Map<Integer, Integer> idIdxMap = new HashMap<>();
    private Animal[] animals = new Animal[200_001];
    private int idIdx, animalCnt;
    private PriorityQueue<Animal> pq = new PriorityQueue<>();

    public void init() {
        idIdxMap.clear();
        idIdx = -1;
        pq.clear();
        animalCnt = 0;
    }

    private void timeMoves(int mTime) {
        while (!pq.isEmpty() && pq.peek().deadDate <= mTime) {
            Animal animal = pq.poll();
            if (!animal.changed) {
                // 백신접종으로 기대수명이 늘어난 동물이 PQ에 중복돼서 있을 수 있으므로
                idIdxMap.remove(animal.id);
                animalCnt--;
            }
        }
    }

    /**
     * 최대 150,000번 호출
     */
    public int adopt(int mTime, int id, int life) {
        timeMoves(mTime);
        if (idIdxMap.containsKey(id)) return animalCnt;

        idIdx++;
        idIdxMap.put(id, idIdx);
        if (animals[idIdx] == null) animals[idIdx] = new Animal();
        animals[idIdx].setData(id, mTime, life);
        pq.offer(animals[idIdx]);
        animalCnt++;
        return animalCnt;
    }

    /**
     * 최대 50,000번 호출
     */
    public int getLife(int mTime, int id) {
        timeMoves(mTime);
        if (!idIdxMap.containsKey(id)) return -1;
        Animal target = animals[idIdxMap.get(id)];
        return target.deadDate - mTime;
    }

    /**
     * 최대 50,000번 호출
     */
    public int vaccinate(int mTime, int id, int life) {
        timeMoves(mTime);
        if (!idIdxMap.containsKey(id)) return -1;
        Animal target = animals[idIdxMap.get(id)];
        target.changed = true;

        idIdx++;
        idIdxMap.put(id, idIdx);
        if (animals[idIdx] == null) animals[idIdx] = new Animal();
        animals[idIdx].setData(id, target.adoptTime, target.lifespan + life);
        pq.offer(animals[idIdx]);
        return animals[idIdx].deadDate - mTime;
    }
}
