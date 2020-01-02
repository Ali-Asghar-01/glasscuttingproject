/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Ali Asghar
 */
@Embeddable
public class AmGlassDetailsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "GLASS_MATERIAL")
    private String glassMaterial;
    @Basic(optional = false)
    @Column(name = "SHAPE")
    private String shape;

    public AmGlassDetailsPK() {
    }

    public AmGlassDetailsPK(String glassMaterial, String shape) {
        this.glassMaterial = glassMaterial;
        this.shape = shape;
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
        hash += (glassMaterial != null ? glassMaterial.hashCode() : 0);
        hash += (shape != null ? shape.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmGlassDetailsPK)) {
            return false;
        }
        AmGlassDetailsPK other = (AmGlassDetailsPK) object;
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
        return "GUI.AmGlassDetailsPK[ glassMaterial=" + glassMaterial + ", shape=" + shape + " ]";
    }
    
}
