/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Ali Asghar
 */
@Embeddable
public class Consumer1PK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ORDER_ID")
    private BigInteger orderId;
    @Basic(optional = false)
    @Column(name = "SHOP_ID")
    private BigInteger shopId;
    @Basic(optional = false)
    @Column(name = "GLASS_MATERIAL")
    private String glassMaterial;
    @Basic(optional = false)
    @Column(name = "SHAPE")
    private String shape;

    public Consumer1PK() {
    }

    public Consumer1PK(BigInteger orderId, BigInteger shopId, String glassMaterial, String shape) {
        this.orderId = orderId;
        this.shopId = shopId;
        this.glassMaterial = glassMaterial;
        this.shape = shape;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public BigInteger getShopId() {
        return shopId;
    }

    public void setShopId(BigInteger shopId) {
        this.shopId = shopId;
    }

    public String getGlassMaterial() {
        return glassMaterial;
    }

    public void setGlassMaterial(String glassMaterial) {
        this.glassMaterial = glassMaterial;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        hash += (shopId != null ? shopId.hashCode() : 0);
        hash += (glassMaterial != null ? glassMaterial.hashCode() : 0);
        hash += (shape != null ? shape.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumer1PK)) {
            return false;
        }
        Consumer1PK other = (Consumer1PK) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        if ((this.shopId == null && other.shopId != null) || (this.shopId != null && !this.shopId.equals(other.shopId))) {
            return false;
        }
        if ((this.glassMaterial == null && other.glassMaterial != null) || (this.glassMaterial != null && !this.glassMaterial.equals(other.glassMaterial))) {
            return false;
        }
        if ((this.shape == null && other.shape != null) || (this.shape != null && !this.shape.equals(other.shape))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Consumer1PK[ orderId=" + orderId + ", shopId=" + shopId + ", glassMaterial=" + glassMaterial + ", shape=" + shape + " ]";
    }
    
}
