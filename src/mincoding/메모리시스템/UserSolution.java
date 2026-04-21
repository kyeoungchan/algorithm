package mincoding.메모리시스템;

import java.util.*;

class UserSolution {
    static class Space implements Comparable<Space> {
        int idx, size;

        public Space(int idx, int size) {
            this.idx = idx;
            this.size = size;
        }

        @Override
        public int compareTo(Space o) {
            return size == o.size ? Integer.compare(idx, o.idx) : Integer.compare(size, o.size);
        }

        @Override
        public String toString() {
            return "idx: " + idx + ", size: " + size + "\n";
        }
    }


    private TreeMap<Integer, Integer> occupied = new TreeMap<>();
    private TreeSet<Space> emptyTs = new TreeSet<>();
    private int N;

    public void init(int N) {
//        System.out.println("초기화!");
        occupied.clear();
        emptyTs.clear();
        emptyTs.add(new Space(0, N));
        this.N = N;
    }

    public int allocate(int size) {
//        System.out.println("할당!");
//        System.out.println("emptyTs = " + emptyTs);
//        System.out.println("occupied = " + occupied);
        Space ceiling = emptyTs.ceiling(new Space(-1, size));
        if (ceiling == null) {
            return -1;
        }

        // 점유된 영역 표시
        occupied.put(ceiling.idx, ceiling.idx + size - 1);

        // 기존 빈 공간 제거 및 새로운 빈공간 할당
        emptyTs.remove(ceiling);
        emptyTs.add(new Space(ceiling.idx + size, ceiling.size - size));
//        System.out.println("emptyTs = " + emptyTs);
//        System.out.println("occupied = " + occupied);
        return ceiling.idx;
    }

    public int deallocate(int start) {
//        System.out.println("해제!");
//        System.out.println("emptyTs = " + emptyTs);
//        System.out.println("occupied = " + occupied);
        if (!occupied.containsKey(start)) {
            return -1;
        }

        // 점유된 공간의 끝 인덱스
        int end = occupied.get(start);

        int result = end - start + 1;

        // 일단 점유 공간에서 제거
        // 빈 공간을 관리하는 ts는 분기점마다 달라지므로 아래에서 관리
        occupied.remove(start);

        Space addedSpace = new Space(start, result);
        emptyTs.add(addedSpace);


        Map.Entry<Integer, Integer> rightOccupied = occupied.higherEntry(end);
        // 바로 뒷 공간이 점유되지 않은 공간이라면
        if (rightOccupied == null) {
            if (end != N - 1) {
                // 오른쪽이 모두 빈 케이스
                int rightSize = N - end - 1;
                emptyTs.remove(addedSpace);

                emptyTs.remove(new Space(end + 1, rightSize));

                addedSpace = new Space(start, addedSpace.size + rightSize);
                emptyTs.add(addedSpace);
            }
        } else if (rightOccupied.getKey() != end + 1) {
            // 우측에 점유된 공간 정보
            // 우측 점유된 공간 정보의 시작 인덱스는 end + 1보다 커야한다.
            int rightOccupiedStartIdx = rightOccupied.getKey();

            // 이게 존재해야한다.
            // 점유된 공간의 끝 다음 인덱스에서 바로 다음 점유된 인덱스 전까지의 사이즈
            int rightEmptyStart = end + 1;
            int rightEmptySize = rightOccupiedStartIdx - rightEmptyStart;
            emptyTs.remove(new Space(rightEmptyStart, rightEmptySize));

            // 위에서 넣어준 빈공간 정보도 제거
            emptyTs.remove(addedSpace);


            // 해제된 빈 공간과 합체
            addedSpace = new Space(start, rightEmptySize + result);
            emptyTs.add(addedSpace);
        }

        if (start == 0) {
//            System.out.println("emptyTs = " + emptyTs);
//            System.out.println("occupied = " + occupied);
            return result;
        }

        Map.Entry<Integer, Integer> leftOccupied = occupied.lowerEntry(start);
        // 바로 앞 공간이 점유되지 않은 공간이라면
        if (leftOccupied == null) {
            // 왼쪽이 모두 빈 케이스
            int leftSize = start;

            // 왼쪽에 원래 존재하던 빈 공간 제거
            emptyTs.remove(new Space(0, leftSize));

            // 위에서 넣어준 빈공간 정보도 제거
            emptyTs.remove(addedSpace);

            // 해제된 빈 공간과 합체
            emptyTs.add(new Space(0, addedSpace.size + leftSize));
        } else if (leftOccupied.getValue() != start - 1) {
            // 왼쪽에 점유된 공간이 있지만 점유된 공간이 해제된 공간과 이어지지 않을 때
            int leftEmptyStartIdx = leftOccupied.getValue() + 1;
            int leftEmptySize = start - leftEmptyStartIdx;

            // 왼쪽에 원래 존재하던 빈 공간 제거
            emptyTs.remove(new Space(leftEmptyStartIdx, leftEmptySize));

            // 위에서 넣어준 빈공간 정보도 제거
            emptyTs.remove(addedSpace);

            // 해제된 빈 공간과 합체
            emptyTs.add(new Space(leftEmptyStartIdx, addedSpace.size + leftEmptySize));
        }
//        System.out.println("emptyTs = " + emptyTs);
//        System.out.println("occupied = " + occupied);
        return result;
    }
}