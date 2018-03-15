package com.xuzp.stockplayer.web.dto.response;

import com.xuzp.stockplayer.model.BusinessDeal;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.InHandStock;
import com.xuzp.stockplayer.operate.IStockOperate;
import com.xuzp.stockplayer.rule.StockRule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author za-xuzhiping
 * @Date 2018/1/19
 * @Time 18:50
 */
public class QueryPlayerResponseDTO implements Serializable{

    private static final long serialVersionUID = -278086908199181448L;

    private String userName;

    private BigDecimal cash;

    private List<String> favoriteStocks;

    private List<BusinessDeal> businessDeals;

    private Set<InHandStock> inHandStocks;

    private List<String> originalRules;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public List<String> getFavoriteStocks() {
        return favoriteStocks;
    }

    public void setFavoriteStocks(List<String> favoriteStocks) {
        this.favoriteStocks = favoriteStocks;
    }

    public List<BusinessDeal> getBusinessDeals() {
        return businessDeals;
    }

    public void setBusinessDeals(List<BusinessDeal> businessDeals) {
        this.businessDeals = businessDeals;
    }

    public Set<InHandStock> getInHandStocks() {
        return inHandStocks;
    }

    public void setInHandStocks(Set<InHandStock> inHandStocks) {
        this.inHandStocks = inHandStocks;
    }

    public List<String> getOriginalRules() {
        return originalRules;
    }

    public void setOriginalRules(List<String> originalRules) {
        this.originalRules = originalRules;
    }
}
