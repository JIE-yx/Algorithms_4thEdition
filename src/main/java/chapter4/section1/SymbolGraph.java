package chapter4.section1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SymbolGraph {

    private Graph graph;

    private String[] idxToSymbol;

    private Map<String, Integer> symbolToIdx;

    /**
     * 对于symbolList[i]中的list，约定list(0)是顶点，list(1)、list(2)...都会和list(0)关联
     * 例如symbolList可以是
     * [[《功夫》，周星驰],
     * [《九品芝麻官》，吴孟达，周星驰]
     * [《流浪地球》，吴京，刘德华]
     *
     * @param symbolList
     */
    public SymbolGraph (List<List<String>> symbolList) {
        symbolToIdx = new HashMap<>();
        int diffSymbolNum = 0;
        // 构建符号到数字的索引
        for (List<String> subSymbolList : symbolList) {
            for (String symbol : subSymbolList) {
                if (symbolToIdx.containsKey(symbol)) {
                    continue;
                }
                symbolToIdx.put(symbol, diffSymbolNum);
                diffSymbolNum++;
            }
        }
        // 构建数字到符号的索引
        for (String symbol : symbolToIdx.keySet()) {
            int idx = symbolToIdx.get(symbol);
            idxToSymbol[idx] = symbol;
        }
        // 根据数字符号构建索引
        Graph graph = new Graph(diffSymbolNum);
        for (List<String> subSymbolList : symbolList) {
            String symbol = subSymbolList.get(0);
            int symbolIdx = symbolToIdx.get(symbol);
            int idx = 1;
            while (idx < subSymbolList.size()) {
                String connectedSymbol = subSymbolList.get(idx);
                int connectedSymbolIdx = symbolToIdx.get(connectedSymbol);
                graph.addEdge(symbolIdx, connectedSymbolIdx);
            }
        }
    }
}
