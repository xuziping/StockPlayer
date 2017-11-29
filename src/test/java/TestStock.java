import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * @author za-xuzhiping
 * @Date 2017/11/29
 * @Time 17:10
 */
@Slf4j
public class TestStock {


//    private static final String URL = "https://link.zhihu.com/?target=http%3A//vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData%3Fpage%3D1%26num%3D100%26sort%3Dsymbol%26asc%3D1%26node%3Dhs_a%26symbol%3D%26_s_r_a%3Dinit";

    private static final String URL = "http://hq.sinajs.cn/list=%s";
    private static Set<Category> CATEGORIES = null;

    //"s_sh000001"
    static {
        CATEGORIES = Sets.newHashSet(new Category("银行", Lists.newArrayList("600036", "000001", "600000", "601398", "601288", "601818")),
                new Category("医药", Lists.newArrayList("600276", "600196", "000963")),
                new Category("汽车", Lists.newArrayList("600104", "002594", "601238")),
                new Category("保险", Lists.newArrayList("601318", "601628")),
                new Category("酒/食品", Lists.newArrayList("000568", "000858", "000895")),
                new Category("AI", Lists.newArrayList("002230", "002415", "002405")),
                new Category("家电", Lists.newArrayList("600690", "000333")),
                new Category("房地产", Lists.newArrayList("000002", "600655")),
                new Category("电子", Lists.<String>newArrayList("000725", "000977", "002497")),
                new Category("物流", Lists.newArrayList("002352")),
                new Category("证券", Lists.newArrayList("600030", "002670", "000776")),
                new Category("其它", Lists.newArrayList("601888", "601668"))
        );
    }

    public static void main(String[] args) {
        for (Category category : CATEGORIES) {
            log.info("=============== {} =============", category.getName());
            for (String stock : category.getStockCodeList()) {
                query(stock);
            }
        }
    }

    private static void query(String stockCode) {
        URL ur = null;
        try {
            if (stockCode.startsWith("60")) {
                stockCode = "sh" + stockCode;
            } else {
                stockCode = "sz" + stockCode;
            }
            ur = new URL(String.format(URL, stockCode));
            HttpURLConnection uc = (HttpURLConnection) ur.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream(), "GBK"));
            String line;
            while ((line = reader.readLine()) != null) {
                Stock stock = parse(line);
                stock.setCode(stockCode);
                log.info(JSON.toJSONString(stock));
            }
        } catch (Exception e) {
            log.error("Has error, stockCode={}", stockCode);
            e.printStackTrace();
        }
    }


    @Data
    static class Stock {
        private String name;
        private String code;
        private BigDecimal high;
        private BigDecimal low;
        private BigDecimal start;
        private BigDecimal yesterday;
        private BigDecimal current;
        private BigDecimal number;
        private BigDecimal amount;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Category {
        private String name;
        private List<String> stockCodeList;
    }

    private static Stock parse(String val) throws Exception {
        Stock stock = new Stock();
        String value = val.split("\"")[1];
        String[] data = value.split(",");
        stock.setName(data[0]);
        stock.setStart(new BigDecimal(data[1]));
        stock.setYesterday(new BigDecimal(data[2]));
        stock.setCurrent(new BigDecimal(data[3]));
        stock.setHigh(new BigDecimal(data[4]));
        stock.setLow(new BigDecimal(data[5]));
        stock.setNumber(new BigDecimal(data[8]));
        stock.setAmount(new BigDecimal(data[9]));
        return stock;
    }
}
