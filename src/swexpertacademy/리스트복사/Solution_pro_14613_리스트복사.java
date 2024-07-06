package swexpertacademy.리스트복사;

import java.util.*;

class Solution_pro_14613_리스트복사 {

    static class Pair {
        int first, second;

        public void setData(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    static final int MAX_ADDRESS = 5_000;
    static final int MAX_CHANGE_NUMBER = 105_010;
    static final int MAX_INIT_NUMBER = 10;

    int address, changeNumber, initNumber;
    int[] previousChange = new int[MAX_CHANGE_NUMBER];
    int[] lastChange = new int[MAX_ADDRESS];
    int[][] initList = new int[MAX_INIT_NUMBER][];

    Pair[] changeLog = new Pair[MAX_CHANGE_NUMBER];
    Map<String, Integer> map = new HashMap<>();

    /**
     * 생성되어 있는 리스트는 없다.
     */
    public void init() {
        address = changeNumber = initNumber = 0;
        map.clear();
    }

    /**
     * mName 리스트가 생성되어 있지 않음이 보장된다.
     * mName 리스트를 새로 생성한다.
     * mName 리스트의 i번째 원소의 값은 mListValue[i]
     * 호출횟수: 10이하
     */
    public void makeList(char mName[], int mLength, int mListValue[]) {
        // 먼저 리스트 이름과 포인터(주소)를 저장한다.
        String strName = getStr(mName);
        map.put(strName, address);

        // 새로운 리스트를 생성하고 이차원 배열에 저장한다.
        initList[initNumber] = new int[mLength];
        for (int i = 0; i < mLength; i++)
            initList[initNumber][i] = mListValue[i];

        // 루트 노드이므로 부모노드는 -1 표시
        previousChange[changeNumber] = -1;
        // 새로운 Pair를 생성하고, 루트노드이므로 본 리스트를 볼 수 있도록 initNumber를 저장한다.
        createPair(-1, initNumber);
        // 가장 최신으로 바뀐 노드에 대한 정보를 저장한다.
        lastChange[address] = changeNumber;

        initNumber++;
        address++;
        changeNumber++;
    }

    private String getStr(char[] mName) {
        String result = "";
        for (int i = 0; i < mName.length; i++) {
            if (mName[i] == '\0') break;
            result += mName[i];
        }
        return result;
    }

    private void createPair(int start, int second) {
        if (changeLog[changeNumber] == null) changeLog[changeNumber] = new Pair();
        changeLog[changeNumber].setData(start, second);
    }

    /**
     * mDest 리스트가 생성되어있지 않다.
     * mSrc 리스트가 생성되어있다.
     * mCopy가 false인 경우, 주소만 복사하는 방식을 사용
     * 5,000번 이하 호출
     */
    public void copyList(char mDest[], char mSrc[], boolean mCopy) {
        String strDest = getStr(mDest);
        String strSrc = getStr(mSrc);
        if (mCopy) {
            map.put(strDest, address);
            previousChange[changeNumber] = lastChange[map.get(strSrc)];
            lastChange[address] = changeNumber;
            createPair(-1, -1);

            changeNumber++;
            address++;
        } else {
            // 그냥 같은 곳을 가리키도록 하면 된다.
            map.put(strDest, map.get(strSrc));
        }
    }

    /**
     * mName 리스트의 mIndex번째 원소의 값을 mValue로 변경한다.
     * 100_000번 이하 호출
     */
    public void updateElement(char mName[], int mIndex, int mValue) {
        String strName = getStr(mName);
        createPair(mIndex, mValue);
        previousChange[changeNumber] = lastChange[map.get(strName)];
        lastChange[map.get(strName)] = changeNumber;
        changeNumber++;
    }

    /**
     * mName 리스트의 mIndex 번째 원소를 반환한다.
     * mName 리스트가 생성되어있음이 보장된다.
     * 400번 이하 호출
     */
    public int element(char mName[], int mIndex) {
        String strName = getStr(mName);
        int latestChange = lastChange[map.get(strName)];
        Pair cur = changeLog[latestChange];
        while (latestChange != -1) {
            cur = changeLog[latestChange];
            if (cur.first == mIndex) {
                return cur.second;
            }
            latestChange = previousChange[latestChange];
        }

        return initList[cur.second][mIndex];
    }
}
