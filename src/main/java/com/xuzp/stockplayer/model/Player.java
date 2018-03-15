package com.xuzp.stockplayer.model;

import com.xuzp.stockplayer.operate.IStockOperate;
import com.xuzp.stockplayer.rule.StockRule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author XuZiPing
 * @Date 2018/1/7
 * @Time 2:01
 */
public class Player implements Serializable {

    private static final long serialVersionUID = -6491614473928680256L;

    private String userName;

    private BigDecimal cash;

    private List<String> favoriteStocks;

    private List<BusinessDeal> businessDeals;

    private Set<InHandStock> inHandStocks;

    private Boolean useSimulateData;

    private List<StockRule<IStock, IStockOperate>> rules;

    private List<String> originalRules;

    public Player(){

    }

    public Player(Player player) {
        this.setUserName(player.getUserName());
        this.setCash(new BigDecimal(player.getCash().toString()));
        this.setUseSimulateData(player.getUseSimulateData());
        this.setFavoriteStocks(player.getFavoriteStocks());
        this.setBusinessDeals(player.getBusinessDeals().stream().map(x->new BusinessDeal(x)).collect(Collectors.toList()));
        this.setInHandStocks(player.getInHandStocks().stream().map(x->new InHandStock(x)).collect(Collectors.toSet()));
        this.setRules(player.getRules());
        this.setOriginalRules(player.getOriginalRules());
    }

    public Player(String userName, BigDecimal cash, List<String> favoriteStocks, List<BusinessDeal> businessDeals
            , Set<InHandStock> inHandStocks, Boolean useSimulateData, List<StockRule<IStock, IStockOperate>> rules,
            List<String> originalRules){
        this.userName = userName;
        this.cash = cash;
        this.favoriteStocks = favoriteStocks;
        this.businessDeals = businessDeals;
        this.inHandStocks = inHandStocks;
        this.useSimulateData = useSimulateData;
        this.rules = rules;
        this.originalRules = originalRules;
    }

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getFavoriteStocks() {
        return favoriteStocks;
    }

    public void setFavoriteStocks(List<String> favoriteStocks) {
        this.favoriteStocks = favoriteStocks;
    }

    public Boolean getUseSimulateData() {
        return useSimulateData;
    }

    public void setUseSimulateData(Boolean useSimulateData) {
        this.useSimulateData = useSimulateData;
    }

    public void copy(Player other){
        this.setInHandStocks(other.getInHandStocks());
        this.setCash(other.getCash());
        this.setBusinessDeals(other.getBusinessDeals());
        this.setFavoriteStocks(other.getFavoriteStocks());
        this.setUseSimulateData(other.getUseSimulateData());
        this.setUserName(other.getUserName());
        this.setRules(other.getRules());
        this.setOriginalRules(other.getOriginalRules());
    }

    public Optional<InHandStock> getInHandStock(String stockCode) {
        return getInHandStocks().parallelStream().filter(x -> x.getStockCode().equalsIgnoreCase(stockCode))
                .findFirst();
    }

    public List<StockRule<IStock, IStockOperate>> getRules() {
        return rules;
    }

    public void setRules(List<StockRule<IStock, IStockOperate>> rules) {
        this.rules = rules;
    }

    public List<String> getOriginalRules() {
        return originalRules;
    }

    public void setOriginalRules(List<String> originalRules) {
        this.originalRules = originalRules;
    }
}
