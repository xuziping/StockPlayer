import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static Set<String> STOCKS = null;
//"s_sh000001"
    static {
        STOCKS = Sets.newHashSet( "sh600000", "sz000568");
    }

    public static void main(String[] args) {
        for (String stock : STOCKS) {
            query(stock);
        }
    }

    private static void query(String stockCode) {
        URL ur = null;
        try {
            ur = new URL(String.format(URL, stockCode));
            HttpURLConnection uc = (HttpURLConnection) ur.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream(), "GBK"));
            String line;
            while ((line = reader.readLine()) != null) {
                Stock stock = parse(line);
                log.info(JSON.toJSONString(stock));
            }
        } catch (Exception e) {
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
