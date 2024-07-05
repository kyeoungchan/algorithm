package swexpertacademy.리스트복사;

import java.util.*;

class Solution_pro_14613_리스트복사 {

    static class Node {
        List<Integer> list;

        public Node(Node srcNode) {
            list = new ArrayList<>(srcNode.list.size());
            list.addAll(srcNode.list);
        }

        public Node(List<Integer> list) {
            this.list = list;
        }

        public Node(int mLength, int[] mListValue) {
            list = new ArrayList<>(mLength);
            for (int i = 0; i < mLength; i++)
                list.add(mListValue[i]);
        }
    }

    List<Node> list = new ArrayList<>();
    int nodeIdx;
    Map<String, Integer> idxMap = new HashMap<>();

    /**
     * 생성되어 있는 리스트는 없다.
     */
    public void init() {
        nodeIdx = 0;
        idxMap.clear();
        list.clear();
    }

    /**
     * mName 리스트가 생성되어 있지 않음이 보장된다.
     * mName 리스트를 새로 생성한다.
     * mName 리스트의 i번째 원소의 값은 mListValue[i]
     * 호출횟수: 10이하
     */
    public void makeList(char mName[], int mLength, int mListValue[]) {

        String strName = String.copyValueOf(mName, 0, getStrLen(mName));
        idxMap.put(strName, nodeIdx++);
        list.add(new Node(mLength, mListValue));
    }

    private int getStrLen(char[] mName) {
        for (int i = 0; i < mName.length; i++) {
            if (mName[i] == '\0') {
                return i;
            }
        }
        return -1;
    }

    /**
     * mDest 리스트가 생성되어있지 않다.
     * mSrc 리스트가 생성되어있다.
     * mCopy가 false인 경우, 주소만 복사하는 방식을 사용
     * 5,000번 이하 호출
     */
    public void copyList(char mDest[], char mSrc[], boolean mCopy) {
        String strDest = String.copyValueOf(mDest, 0, getStrLen(mDest));
        idxMap.put(strDest, nodeIdx++);

        Node srcNode = list.get(idxMap.get(String.copyValueOf(mSrc, 0, getStrLen(mSrc))));
        if (mCopy) {
            // mCopy가 true인 경우, 값을 모두 복사하는 방식을 사용
            Node newNode = new Node(srcNode);
            list.add(newNode);
        } else {
            // mSrc 리스트를 mDest 리스트에 복사
            Node newNode = new Node(srcNode.list);
            list.add(newNode);
        }
    }

    /**
     * mName 리스트의 mIndex번째 원소의 값을 mValue로 변경한다.
     * 100_000번 이하 호출
     */
    public void updateElement(char mName[], int mIndex, int mValue) {
        String strName = String.copyValueOf(mName, 0, getStrLen(mName));
        int index = idxMap.get(strName);
        list.get(index).list.set(mIndex, mValue);
    }

    /**
     * mName 리스트의 mIndex 번째 원소를 반환한다.
     * mName 리스트가 생성되어있음이 보장된다.
     * 400번 이하 호출
     */
    public int element(char mName[], int mIndex) {
        String strName = String.copyValueOf(mName, 0, getStrLen(mName));
        Node targetNode = list.get(idxMap.get(strName));
        return targetNode.list.get(mIndex);
    }
}
