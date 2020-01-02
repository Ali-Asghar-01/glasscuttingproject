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
@Table(name = "GODOWN_PLACEMENT1", catalog = "", schema = "MAAZ")
@NamedQueries({
    @NamedQuery(name = "GodownPlacement1.findAll", query = "SELECT g FROM GodownPlacement1 g")
    , @NamedQuery(name = "GodownPlacement1.findById", query = "SELECT g FROM GodownPlacement1 g WHERE g.id = :id")
    , @NamedQuery(name = "GodownPlacement1.findByGodownId", query = "SELECT g FROM GodownPlacement1 g WHERE g.godownId = :godownId")
    , @NamedQuery(name = "GodownPlacement1.findByGpDate", query = "SELECT g FROM GodownPlacement1 g WHERE g.gpDate = :gpDate")
    , @NamedQuery(name = "GodownPlacement1.findByQuantity", query = "SELECT g FROM GodownPlacement1 g WHERE g.quantity = :quantity")
    , @NamedQuery(name = "GodownPlacement1.findByGlassMaterial", query = "SELECT g FROM GodownPlacement1 g WHERE g.glassMaterial = :glassMaterial")
    , @NamedQuery(name = "GodownPlacement1.findByShape", query = "SELECT g FROM GodownPlacement1 g WHERE g.shape = :shape")})
public class GodownPlacement1 implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "GODOWN_ID")
    private BigInteger godownId;
    @Column(name = "GP_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gpDate;
    @Basic(optional = false)
    @Column(name = "QUANTITY")
    private BigInteger quantity;
    @Basic(optional = false)
    @Column(name = "GLASS_MATERIAL")
    private String glassMaterial;
    @Basic(optional = false)
    @Column(name = "SHAPE")
    private String shape;

    public GodownPlacement1() {
    }

    public GodownPlacement1(BigDecimal id) {
        this.id = id;
    }

    public GodownPlacement1(BigDecimal id, BigInteger godownId, BigInteger quantity, String glassMaterial, String shape) {
        this.id = id;
        this.godownId = godownId;
        this.quantity = quantity;
        this.glassMaterial = glassMaterial;
        this.shape = shape;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        BigDecimal oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public BigInteger getGodownId() {
        return godownId;
    }

    public void setGodownId(BigInteger godownId) {
        BigInteger oldGodownId = this.godownId;
        this.godownId = godownId;
        changeSupport.firePropertyChange("godownId", oldGodownId, godownId);
    }

    public Date getGpDate() {
        return gpDate;
    }

    public void setGpDate(Date gpDate) {
        Date oldGpDate = this.gpDate;
        this.gpDate = gpDate;
        changeSupport.firePropertyChange("gpDate", oldGpDate, gpDate);
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        BigInteger oldQuantity = this.quantity;
        this.quantity = quantity;
        changeSupport.firePropertyChange("quantity", oldQuantity, quantity);
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GodownPlacement1)) {
            return false;
        }
        GodownPlacement1 other = (GodownPlacement1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.GodownPlacement1[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
