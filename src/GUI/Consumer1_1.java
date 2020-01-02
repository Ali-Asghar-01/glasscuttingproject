/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ali Asghar
 */
@Entity
@Table(name = "CONSUMER1", catalog = "", schema = "MAAZ")
@NamedQueries({
    @NamedQuery(name = "Consumer1_1.findAll", query = "SELECT c FROM Consumer1_1 c")
    , @NamedQuery(name = "Consumer1_1.findByOrderId", query = "SELECT c FROM Consumer1_1 c WHERE c.consumer1PK.orderId = :orderId")
    , @NamedQuery(name = "Consumer1_1.findByShopId", query = "SELECT c FROM Consumer1_1 c WHERE c.consumer1PK.shopId = :shopId")
    , @NamedQuery(name = "Consumer1_1.findByGlassMaterial", query = "SELECT c FROM Consumer1_1 c WHERE c.consumer1PK.glassMaterial = :glassMaterial")
    , @NamedQuery(name = "Consumer1_1.findByShape", query = "SELECT c FROM Consumer1_1 c WHERE c.consumer1PK.shape = :shape")
    , @NamedQuery(name = "Consumer1_1.findByQuantity", query = "SELECT c FROM Consumer1_1 c WHERE c.quantity = :quantity")
    , @NamedQuery(name = "Consumer1_1.findByAmountPerGlass", query = "SELECT c FROM Consumer1_1 c WHERE c.amountPerGlass = :amountPerGlass")
    , @NamedQuery(name = "Consumer1_1.findByTotalAmount", query = "SELECT c FROM Consumer1_1 c WHERE c.totalAmount = :totalAmount")
    , @NamedQuery(name = "Consumer1_1.findByPurchaseDate", query = "SELECT c FROM Consumer1_1 c WHERE c.purchaseDate = :purchaseDate")})
public class Consumer1_1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected Consumer1PK consumer1PK;
    @Column(name = "QUANTITY")
    private BigInteger quantity;
    @Column(name = "AMOUNT_PER_GLASS")
    private BigInteger amountPerGlass;
    @Column(name = "TOTAL_AMOUNT")
    private BigInteger totalAmount;
    @Column(name = "PURCHASE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;

    public Consumer1_1() {
    }

    public Consumer1_1(Consumer1PK consumer1PK) {
        this.consumer1PK = consumer1PK;
    }

    public Consumer1_1(BigInteger orderId, BigInteger shopId, String glassMaterial, String shape) {
        this.consumer1PK = new Consumer1PK(orderId, shopId, glassMaterial, shape);
    }

    public Consumer1PK getConsumer1PK() {
        return consumer1PK;
    }

    public void setConsumer1PK(Consumer1PK consumer1PK) {
        this.consumer1PK = consumer1PK;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public BigInteger getAmountPerGlass() {
        return amountPerGlass;
    }

    public void setAmountPerGlass(BigInteger amountPerGlass) {
        this.amountPerGlass = amountPerGlass;
    }

    public BigInteger getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigInteger totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consumer1PK != null ? consumer1PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumer1_1)) {
            return false;
        }
        Consumer1_1 other = (Consumer1_1) object;
        if ((this.consumer1PK == null && other.consumer1PK != null) || (this.consumer1PK != null && !this.consumer1PK.equals(other.consumer1PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Consumer1_1[ consumer1PK=" + consumer1PK + " ]";
    }
    
}
