package swexpertacademy.리스트복사;

import java.util.*;

class Solution_pro_14613_리스트복사2 {

    static class Node {
        int index, value, previous;
        // index = -1, value = -1이면 깊은 복사를 한 노드. 부모 노드로 올라갈 수 있다.
        // index = -1, value != -1이면 루트 노드. 물론 previous가 -1인 것으로 이미 판별이 된다.

        public void setStatus(int index, int value, int previous) {
            this.index = index;
            this.value = value;
            this.previous = previous;
        }
    }

    final int MAX_ADDRESS_NUMBER = 5_000;
    final int MAX_CHANGE_NUMBER = 105_010;
    final int MAX_INIT_NUMBER = 10;
    final int MAX_LIST_LENGTH = 200_000;

    Map<String, Integer> map = new HashMap<>();
    Node[] changeLogs = new Node[MAX_CHANGE_NUMBER];
    int[] lastChanges = new int[MAX_ADDRESS_NUMBER];
    int[][] initList = new int[MAX_INIT_NUMBER][MAX_LIST_LENGTH];
    int changeNumber, initNumber, address;

    /**
     * 생성되어 있는 리스트는 없다.
     */
    public void init() {
        map.clear();
        address = changeNumber = initNumber = 0;
    }

    /**
     * mName 리스트가 생성되어 있지 않음이 보장된다.
     * mName 리스트를 새로 생성한다.
     * mName 리스트의 i번째 원소의 값은 mListValue[i]
     * 호출횟수: 10이하
     */
    public void makeList(char mName[], int mLength, int mListValue[]) {
        map.put(getStr(mName), address);
        lastChanges[address] = changeNumber;
        for (int i = 0; i < mLength; i++)
            initList[initNumber][i] = mListValue[i];
        createNode(-1, initNumber, -1);
        changeNumber++;
        initNumber++;
        address++;
    }

    private String getStr(char[] name) {
        String result = "";
        int index = 0;
        while (name[index] != '\0') {
            result += name[index++];
        }
        return result;
    }

    private void createNode(int index, int value, int previous) {
        if (changeLogs[changeNumber] == null)
            changeLogs[changeNumber] = new Node();
        changeLogs[changeNumber].setStatus(index, value, previous);
    }

    /**
     * mDest 리스트가 생성되어있지 않다.
     * mSrc 리스트가 생성되어있다.
     * mCopy가 false인 경우, 주소만 복사하는 방식을 사용
     * 5,000번 이하 호출
     */
    public void copyList(char mDest[], char mSrc[], boolean mCopy) {
        String destStr = getStr(mDest);
        String srcStr = getStr(mSrc);
        if (mCopy) {
            map.put(destStr, address);
            createNode(-1, -1, lastChanges[map.get(srcStr)]);
            lastChanges[address] = changeNumber;
            changeNumber++;
            address++;
        } else {
            map.put(destStr, map.get(srcStr));
        }
    }

    /**
     * mName 리스트의 mIndex번째 원소의 값을 mValue로 변경한다.
     * 100_000번 이하 호출
     */
    public void updateElement(char mName[], int mIndex, int mValue) {
        String targetStr = getStr(mName);
        createNode(mIndex, mValue, lastChanges[map.get(targetStr)]);
        lastChanges[map.get(targetStr)] = changeNumber;
        changeNumber++;
    }

    /**
     * mName 리스트의 mIndex 번째 원소를 반환한다.
     * mName 리스트가 생성되어있음이 보장된다.
     * 400번 이하 호출
     */
    public int element(char mName[], int mIndex) {
        String targetStr = getStr(mName);
        Node targetNode = changeLogs[lastChanges[map.get(targetStr)]];
        while (targetNode.previous != -1) {
            if (targetNode.index == mIndex) {
                return targetNode.value;
            }
            targetNode = changeLogs[targetNode.previous];
        }
        return initList[targetNode.value][mIndex];
    }
}
