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
@Table(name = "GLASS_MANUFACTURED_ORDER", catalog = "", schema = "MAAZ")
@NamedQueries({
    @NamedQuery(name = "GlassManufacturedOrder.findAll", query = "SELECT g FROM GlassManufacturedOrder g")
    , @NamedQuery(name = "GlassManufacturedOrder.findByGmId", query = "SELECT g FROM GlassManufacturedOrder g WHERE g.gmId = :gmId")
    , @NamedQuery(name = "GlassManufacturedOrder.findByGlassMaterial", query = "SELECT g FROM GlassManufacturedOrder g WHERE g.glassMaterial = :glassMaterial")
    , @NamedQuery(name = "GlassManufacturedOrder.findByShape", query = "SELECT g FROM GlassManufacturedOrder g WHERE g.shape = :shape")
    , @NamedQuery(name = "GlassManufacturedOrder.findByQuantity", query = "SELECT g FROM GlassManufacturedOrder g WHERE g.quantity = :quantity")
    , @NamedQuery(name = "GlassManufacturedOrder.findByGmDate", query = "SELECT g FROM GlassManufacturedOrder g WHERE g.gmDate = :gmDate")
    , @NamedQuery(name = "GlassManufacturedOrder.findByLength", query = "SELECT g FROM GlassManufacturedOrder g WHERE g.length = :length")
    , @NamedQuery(name = "GlassManufacturedOrder.findByWidth", query = "SELECT g FROM GlassManufacturedOrder g WHERE g.width = :width")
    , @NamedQuery(name = "GlassManufacturedOrder.findByHeight", query = "SELECT g FROM GlassManufacturedOrder g WHERE g.height = :height")
    , @NamedQuery(name = "GlassManufacturedOrder.findByAmountPerGlass", query = "SELECT g FROM GlassManufacturedOrder g WHERE g.amountPerGlass = :amountPerGlass")})
public class GlassManufacturedOrder implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "GM_ID")
    private BigDecimal gmId;
    @Basic(optional = false)
    @Column(name = "GLASS_MATERIAL")
    private String glassMaterial;
    @Basic(optional = false)
    @Column(name = "SHAPE")
    private String shape;
    @Basic(optional = false)
    @Column(name = "QUANTITY")
    private BigInteger quantity;
    @Column(name = "GM_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gmDate;
    @Column(name = "LENGTH")
    private BigInteger length;
    @Column(name = "WIDTH")
    private BigInteger width;
    @Column(name = "HEIGHT")
    private BigInteger height;
    @Column(name = "AMOUNT_PER_GLASS")
    private BigInteger amountPerGlass;

    public GlassManufacturedOrder() {
    }

    public GlassManufacturedOrder(BigDecimal gmId) {
        this.gmId = gmId;
    }

    public GlassManufacturedOrder(BigDecimal gmId, String glassMaterial, String shape, BigInteger quantity) {
        this.gmId = gmId;
        this.glassMaterial = glassMaterial;
        this.shape = shape;
        this.quantity = quantity;
    }

    public BigDecimal getGmId() {
        return gmId;
    }

    public void setGmId(BigDecimal gmId) {
        BigDecimal oldGmId = this.gmId;
        this.gmId = gmId;
        changeSupport.firePropertyChange("gmId", oldGmId, gmId);
    }

    public String getGlassMaterial() {
        return glassMaterial;
    }

    public void setGlassMaterial(String glassMaterial) {
        String oldGlassMaterial = this.glassMaterial;
        this.glassMaterial = glassMaterial;
        changeSupport.firePropertyChange("glassMaterial", oldGlassMaterial, glassMaterial);
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        String oldShape = this.shape;
        this.shape = shape;
        changeSupport.firePropertyChange("shape", oldShape, shape);
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        BigInteger oldQuantity = this.quantity;
        this.quantity = quantity;
        changeSupport.firePropertyChange("quantity", oldQuantity, quantity);
    }

    public Date getGmDate() {
        return gmDate;
    }

    public void setGmDate(Date gmDate) {
        Date oldGmDate = this.gmDate;
        this.gmDate = gmDate;
        changeSupport.firePropertyChange("gmDate", oldGmDate, gmDate);
    }

    public BigInteger getLength() {
        return length;
    }

    public void setLength(BigInteger length) {
        BigInteger oldLength = this.length;
        this.length = length;
        changeSupport.firePropertyChange("length", oldLength, length);
    }

    public BigInteger getWidth() {
        return width;
    }

    public void setWidth(BigInteger width) {
        BigInteger oldWidth = this.width;
        this.width = width;
        changeSupport.firePropertyChange("width", oldWidth, width);
    }

    public BigInteger getHeight() {
        return height;
    }

    public void setHeight(BigInteger height) {
        BigInteger oldHeight = this.height;
        this.height = height;
        changeSupport.firePropertyChange("height", oldHeight, height);
    }

    public BigInteger getAmountPerGlass() {
        return amountPerGlass;
    }

    public void setAmountPerGlass(BigInteger amountPerGlass) {
        BigInteger oldAmountPerGlass = this.amountPerGlass;
        this.amountPerGlass = amountPerGlass;
        changeSupport.firePropertyChange("amountPerGlass", oldAmountPerGlass, amountPerGlass);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gmId != null ? gmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GlassManufacturedOrder)) {
            return false;
        }
        GlassManufacturedOrder other = (GlassManufacturedOrder) object;
        if ((this.gmId == null && other.gmId != null) || (this.gmId != null && !this.gmId.equals(other.gmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.GlassManufacturedOrder[ gmId=" + gmId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
