/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Ali Asghar
 */
@Entity
@Table(name = "AM_GLASS_DETAILS", catalog = "", schema = "MAAZ")
@NamedQueries({
    @NamedQuery(name = "AmGlassDetails.findAll", query = "SELECT a FROM AmGlassDetails a")
    , @NamedQuery(name = "AmGlassDetails.findByGlassMaterial", query = "SELECT a FROM AmGlassDetails a WHERE a.amGlassDetailsPK.glassMaterial = :glassMaterial")
    , @NamedQuery(name = "AmGlassDetails.findByShape", query = "SELECT a FROM AmGlassDetails a WHERE a.amGlassDetailsPK.shape = :shape")
    , @NamedQuery(name = "AmGlassDetails.findByQuantity", query = "SELECT a FROM AmGlassDetails a WHERE a.quantity = :quantity")})
public class AmGlassDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AmGlassDetailsPK amGlassDetailsPK;
    @Column(name = "QUANTITY")
    private BigInteger quantity;

    public AmGlassDetails() {
    }

    public AmGlassDetails(AmGlassDetailsPK amGlassDetailsPK) {
        this.amGlassDetailsPK = amGlassDetailsPK;
    }

    public AmGlassDetails(String glassMaterial, String shape) {
        this.amGlassDetailsPK = new AmGlassDetailsPK(glassMaterial, shape);
    }

    public AmGlassDetailsPK getAmGlassDetailsPK() {
        return amGlassDetailsPK;
    }

    public void setAmGlassDetailsPK(AmGlassDetailsPK amGlassDetailsPK) {
        this.amGlassDetailsPK = amGlassDetailsPK;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (amGlassDetailsPK != null ? amGlassDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmGlassDetails)) {
            return false;
        }
        AmGlassDetails other = (AmGlassDetails) object;
        if ((this.amGlassDetailsPK == null && other.amGlassDetailsPK != null) || (this.amGlassDetailsPK != null && !this.amGlassDetailsPK.equals(other.amGlassDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.AmGlassDetails[ amGlassDetailsPK=" + amGlassDetailsPK + " ]";
    }
    
}
