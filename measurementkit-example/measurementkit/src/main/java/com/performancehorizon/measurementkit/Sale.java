package com.performancehorizon.measurementkit;




import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Sale item.  Attached to {@link Event}
 */
public class Sale {

    private String category;

    private BigDecimal value;
    private BigDecimal commission;
    private BigDecimal override;

    private Integer quantity;

    private String sku;
    private String voucher;
    private String country;

    private HashMap<String, String> saleMeta;

    /**
     *
     * intialise a sale with a category and a value
     * @param category category of the sale (corresponds to product in PH affiliate tracking service)
     * @param value decimal value of the sale
     */
    public Sale(String category, BigDecimal value) {
        this.category = category;
        this.value= value;
        this.saleMeta = new HashMap<String, String>();
    }

    /**
     * initialise a sale with a category, value, sku, and quantity
     * @param category  category of the sale (corresponds to product in PH affilate tracking service)
     * @param value decimal value of the sale
     * @param sku SKU of the product sold
     * @param quantity quanity of products sold
     */
    public Sale(String category, BigDecimal value, String sku, Integer quantity)
    {
        this(category, value);
        this.quantity= quantity;
        this.sku = sku;
    }

    /**
     * get the category of the sale
     * @return category of the sale (corresponds to product in PH affilate tracking service)
     */
    protected String getCategory()
    {
        return this.category;
    }

    /**
     * get the Decimal value of the sale
     * @return value of the sale
     */
    protected BigDecimal getValue()
    {
        return this.value;
    }

    /**
     * sets the SKU of the sale
     * @param sku SKU of the product
     */
    public void setSKU(String sku)
    {
        this.sku = sku;
    }

    /**
     * get the SKU of the sale
     * @return SKU of the product sold (may be null)
     */
    protected  String getSKU() {
        return this.sku;
    }

    /**
     * set the quantity of products sold
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * get the quantity of products sold
     * @return quantity sold (may be null)
     */
    protected  Integer getQuantity() {
        return this.quantity;
    }

    /**
     * get the commission amount of the sale
     * @return commission amount (may be null, in which case the commision amount will
     * be calculated according to campaign defaults)
     */
    protected  BigDecimal getCommission() {
        return commission;
    }

    /**
     * set the commission amount of the sale (paid to publisher)
     * @param commission The commission amount
     */
    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    /**
     * get the override amount of the sale (paid to Performance Horizon)
     * @return override amount (may be null, in which case the override amount will be
     * calculated according to campaing defaults)
     */
    protected  BigDecimal getOverride() {
        return this.override;
    }

    /**
     * set the commission amount of the sale (paid to Performance Horizon)
     * @param override The override amount
     */
    public void setOverride(BigDecimal override) {
        this.override = override;
    }


    /**
     * get the voucher code used with the sale
     * @return Voucher code used in sale (may be null)
     */
    protected  String getVoucher() {
        return voucher;
    }

    /**
     * set the voucher code used with the sale
     * @param voucher
     */
    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    protected  String getCountry() {
        return country;
    }

    /**
     * Sets the country code in which the sale took place.
     * ISO 3166-1 Alpha-3 code (e.g United Kingdom - GBR)
     * @param country - the country in which sale took place
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Sets a meta item for the sale, an arbitrary key/value that represents a property of the sale.
     * @param key - the key for the meta item
     * @param value - the value for the meta item
     */
    public void setMetaItem(String key, String value) {

        this.saleMeta.put(key, value);
    }

    protected  Map<String, String> getMetaItems() {
        return this.saleMeta;
    }
}
