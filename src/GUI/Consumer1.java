/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import javax.persistence.Transient;

/**
 *
 * @author Ali Asghar
 */
@Entity
@Table(name = "CONSUMER1", catalog = "", schema = "MAAZ")
@NamedQueries({
    @NamedQuery(name = "Consumer1.findAll", query = "SELECT c FROM Consumer1 c")
    , @NamedQuery(name = "Consumer1.findByOrderId", query = "SELECT c FROM Consumer1 c WHERE c.consumer1PK.orderId = :orderId")
    , @NamedQuery(name = "Consumer1.findByShopId", query = "SELECT c FROM Consumer1 c WHERE c.consumer1PK.shopId = :shopId")
    , @NamedQuery(name = "Consumer1.findByGlassMaterial", query = "SELECT c FROM Consumer1 c WHERE c.consumer1PK.glassMaterial = :glassMaterial")
    , @NamedQuery(name = "Consumer1.findByShape", query = "SELECT c FROM Consumer1 c WHERE c.consumer1PK.shape = :shape")
    , @NamedQuery(name = "Consumer1.findByQuantity", query = "SELECT c FROM Consumer1 c WHERE c.quantity = :quantity")
    , @NamedQuery(name = "Consumer1.findByAmountPerGlass", query = "SELECT c FROM Consumer1 c WHERE c.amountPerGlass = :amountPerGlass")
    , @NamedQuery(name = "Consumer1.findByTotalAmount", query = "SELECT c FROM Consumer1 c WHERE c.totalAmount = :totalAmount")
    , @NamedQuery(name = "Consumer1.findByPurchaseDate", query = "SELECT c FROM Consumer1 c WHERE c.purchaseDate = :purchaseDate")})
public class Consumer1 implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

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

    public Consumer1() {
    }

    public Consumer1(Consumer1PK consumer1PK) {
        this.consumer1PK = consumer1PK;
    }

    public Consumer1(BigInteger orderId, BigInteger shopId, String glassMaterial, String shape) {
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
        BigInteger oldQuantity = this.quantity;
        this.quantity = quantity;
        changeSupport.firePropertyChange("quantity", oldQuantity, quantity);
    }

    public BigInteger getAmountPerGlass() {
        return amountPerGlass;
    }

    public void setAmountPerGlass(BigInteger amountPerGlass) {
        BigInteger oldAmountPerGlass = this.amountPerGlass;
        this.amountPerGlass = amountPerGlass;
        changeSupport.firePropertyChange("amountPerGlass", oldAmountPerGlass, amountPerGlass);
    }

    public BigInteger getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigInteger totalAmount) {
        BigInteger oldTotalAmount = this.totalAmount;
        this.totalAmount = totalAmount;
        changeSupport.firePropertyChange("totalAmount", oldTotalAmount, totalAmount);
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        Date oldPurchaseDate = this.purchaseDate;
        this.purchaseDate = purchaseDate;
        changeSupport.firePropertyChange("purchaseDate", oldPurchaseDate, purchaseDate);
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
        if (!(object instanceof Consumer1)) {
            return false;
        }
        Consumer1 other = (Consumer1) object;
        if ((this.consumer1PK == null && other.consumer1PK != null) || (this.consumer1PK != null && !this.consumer1PK.equals(other.consumer1PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Consumer1[ consumer1PK=" + consumer1PK + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
