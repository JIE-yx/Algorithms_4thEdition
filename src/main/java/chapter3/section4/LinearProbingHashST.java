package chapter3.section4;

public class LinearProbingHashST <Key, Val>{
    private int keyNum;  // 键值对的总数

    private int tableSize = 16;  // 线性探测表的大小

    private Key[] keys;

    private Val[] vals;

    public LinearProbingHashST() {
        keys = (Key[]) new Object[tableSize];
        vals = (Val[]) new Object[tableSize];
    }

    public LinearProbingHashST(int tableSize) {
        if (tableSize <= 0) {
            throw new RuntimeException("size cannot be less than 1");
        }
        this.tableSize = tableSize;
        keys = (Key[]) new Object[tableSize];
        vals = (Val[]) new Object[tableSize];
    }

    public void put(Key key, Val val) {
        checkKey(key);
        // 避免频繁的hash碰撞
        if (keyNum >= tableSize / 2) {
            resize(2 * tableSize);
        }
        // 计算索引
        int idx = getIdx(key);
        // 哈希碰撞，线性探测
        // 如果当前位置不为null，且也不是equals，就继续探测
        while (keys[idx] != null && !keys[idx].equals(key)) {
            // 不断增加索引，到右边界时，下一次探测是0索引
            idx = (idx + 1) % tableSize;
        }
        keys[idx] = key;
        vals[idx] = val;
        keyNum = keyNum + 1;
    }

    public Val get(Key key) {
        checkKey(key);
        int idx = getIdx(key);
        // 找到key的条件是idx处为null(说明没有这个key)，或者idx处的key和key相同
        // 还需要继续找的条件是 idx处不为null，且idx的key不是目标key
        while (keys[idx] != null && !keys[idx].equals(key)) {
            idx = (idx + 1) % tableSize;
        }
        return vals[idx];
    }

    public void delete(Key key) {
        checkKey(key);
        int idx = getIdx(key);
        while (keys[idx] != null && !keys[idx].equals(key)) {
            idx = (idx + 1) % tableSize;
        }

        // 说明key不存在
        if (keys[idx] == null) {
            return;
        }
        keys[idx] = null;
        vals[idx] = null;
        keyNum = keyNum - 1;
        idx = (idx + 1) % tableSize;
        // 需要删除同一个键簇中(连续的键)，右侧所有的键
        while (keys[idx] != null) {
            Key keyToMove = keys[idx];
            Val valToMove = vals[idx];
            keys[idx] = null;
            vals[idx] = null;
            // 注意，这里要keyNum --
            keyNum = keyNum - 1;
            // 在put方法里面会执行keyNum + 1的操作
            put(keyToMove, valToMove);
            idx = (idx + 1) % tableSize;
        }
        // resize if necessary
        if (keyNum > 0 && keyNum <= tableSize / 8) {
            resize(tableSize / 2);
        }
    }

    private void resize(int len) {
        LinearProbingHashST<Key, Val> linearProbingHashST = new LinearProbingHashST<>(len);
        for (int i = 0; i < tableSize; i ++) {
            Key key = keys[i];
            if (key == null) {
                continue;
            }
            linearProbingHashST.put(key, vals[i]);
        }
        keys = linearProbingHashST.keys;
        vals = linearProbingHashST.vals;
        tableSize = linearProbingHashST.tableSize;
    }

    private int getIdx(Key key) {
        checkKey(key);
        return key.hashCode() % tableSize;
    }

    private void checkKey(Key key) {
        if (key == null) {
            throw new RuntimeException("key cannot be null");
        }
    }


}
