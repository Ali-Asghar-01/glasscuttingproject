/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "VENDOR_MAIN1", catalog = "", schema = "MAAZ")
@NamedQueries({
    @NamedQuery(name = "VendorMain1.findAll", query = "SELECT v FROM VendorMain1 v")
    , @NamedQuery(name = "VendorMain1.findByOrderId", query = "SELECT v FROM VendorMain1 v WHERE v.orderId = :orderId")
    , @NamedQuery(name = "VendorMain1.findByVendorId", query = "SELECT v FROM VendorMain1 v WHERE v.vendorId = :vendorId")
    , @NamedQuery(name = "VendorMain1.findByPurchaseDate", query = "SELECT v FROM VendorMain1 v WHERE v.purchaseDate = :purchaseDate")
    , @NamedQuery(name = "VendorMain1.findByGlassMaterial", query = "SELECT v FROM VendorMain1 v WHERE v.glassMaterial = :glassMaterial")
    , @NamedQuery(name = "VendorMain1.findByQuantity", query = "SELECT v FROM VendorMain1 v WHERE v.quantity = :quantity")
    , @NamedQuery(name = "VendorMain1.findByAmountPerGlass", query = "SELECT v FROM VendorMain1 v WHERE v.amountPerGlass = :amountPerGlass")
    , @NamedQuery(name = "VendorMain1.findByTotalAmount", query = "SELECT v FROM VendorMain1 v WHERE v.totalAmount = :totalAmount")})
public class VendorMain1 implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ORDER_ID")
    private BigDecimal orderId;
    @Basic(optional = false)
    @Column(name = "VENDOR_ID")
    private BigInteger vendorId;
    @Basic(optional = false)
    @Column(name = "PURCHASE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;
    @Basic(optional = false)
    @Column(name = "GLASS_MATERIAL")
    private String glassMaterial;
    @Basic(optional = false)
    @Column(name = "QUANTITY")
    private BigInteger quantity;
    @Basic(optional = false)
    @Column(name = "AMOUNT_PER_GLASS")
    private BigInteger amountPerGlass;
    @Basic(optional = false)
    @Column(name = "TOTAL_AMOUNT")
    private BigInteger totalAmount;

    public VendorMain1() {
    }

    public VendorMain1(BigDecimal orderId) {
        this.orderId = orderId;
    }

    public VendorMain1(BigDecimal orderId, BigInteger vendorId, Date purchaseDate, String glassMaterial, BigInteger quantity, BigInteger amountPerGlass, BigInteger totalAmount) {
        this.orderId = orderId;
        this.vendorId = vendorId;
        this.purchaseDate = purchaseDate;
        this.glassMaterial = glassMaterial;
        this.quantity = quantity;
        this.amountPerGlass = amountPerGlass;
        this.totalAmount = totalAmount;
    }

    public BigDecimal getOrderId() {
        return orderId;
    }

    public void setOrderId(BigDecimal orderId) {
        BigDecimal oldOrderId = this.orderId;
        this.orderId = orderId;
        changeSupport.firePropertyChange("orderId", oldOrderId, orderId);
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        BigInteger oldVendorId = this.vendorId;
        this.vendorId = vendorId;
        changeSupport.firePropertyChange("vendorId", oldVendorId, vendorId);
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        Date oldPurchaseDate = this.purchaseDate;
        this.purchaseDate = purchaseDate;
        changeSupport.firePropertyChange("purchaseDate", oldPurchaseDate, purchaseDate);
    }

    public String getGlassMaterial() {
        return glassMaterial;
    }

    public void setGlassMaterial(String glassMaterial) {
        String oldGlassMaterial = this.glassMaterial;
        this.glassMaterial = glassMaterial;
        changeSupport.firePropertyChange("glassMaterial", oldGlassMaterial, glassMaterial);
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VendorMain1)) {
            return false;
        }
        VendorMain1 other = (VendorMain1) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.VendorMain1[ orderId=" + orderId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
