package cn.itcast.store.product.domain;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {

    // 主键ID
    private String pid;

    // 商品名称
    private String pname;

    // 市场价格
    private double market_price;

    // 商城价格
    private double shop_price;

    // 商品图片
    private String pimage;

    // 上架时间
    private Date pdate;

    // 是否热门(0:非热门；1：热门)
    private int is_hot;

    // 商品描述
    private String pdesc;

    // 上架状态(0:已上架；1：已下架)
    private int pflag;

    // 外键cid
    private Category category;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getMarket_price() {
        return market_price;
    }

    public void setMarket_price(double market_price) {
        this.market_price = market_price;
    }

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double shop_price) {
        this.shop_price = shop_price;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public Date getPdate() {
        return pdate;
    }

    public void setPdate(Date pdate) {
        this.pdate = pdate;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public int getPflag() {
        return pflag;
    }

    public void setPflag(int pflag) {
        this.pflag = pflag;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product() {

    }

}
