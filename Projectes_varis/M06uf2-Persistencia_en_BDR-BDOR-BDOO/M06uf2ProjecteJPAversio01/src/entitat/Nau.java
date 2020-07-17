/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitat;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arnau
 */
@Entity
@Table(name = "nau")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nau.findAll", query = "SELECT n FROM Nau n"),
    @NamedQuery(name = "Nau.findByIdnau", query = "SELECT n FROM Nau n WHERE n.idnau = :idnau"),
    @NamedQuery(name = "Nau.findByNomNau", query = "SELECT n FROM Nau n WHERE n.nomNau = :nomNau"),
    @NamedQuery(name = "Nau.findByVidaNau", query = "SELECT n FROM Nau n WHERE n.vidaNau = :vidaNau"),
    @NamedQuery(name = "Nau.findByDefensaNau", query = "SELECT n FROM Nau n WHERE n.defensaNau = :defensaNau"),
    @NamedQuery(name = "Nau.findByVelocitatNau", query = "SELECT n FROM Nau n WHERE n.velocitatNau = :velocitatNau"),
    @NamedQuery(name = "Nau.findByPotenciaNau", query = "SELECT n FROM Nau n WHERE n.potenciaNau = :potenciaNau")})
public class Nau implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnau")
    private Integer idnau;
    @Column(name = "nomNau")
    private String nomNau;
    @Column(name = "vidaNau")
    private Integer vidaNau;
    @Column(name = "defensaNau")
    private Integer defensaNau;
    @Column(name = "velocitatNau")
    private Integer velocitatNau;
    @Column(name = "potenciaNau")
    private Integer potenciaNau;
    
//    @Lob
//    @Column(name = "fotoNau")
//    private byte[] fotoNau;

    public Nau() {
    }

    public Nau(Integer idnau) {
        this.idnau = idnau;
    }

    public Integer getIdnau() {
        return idnau;
    }

    public void setIdnau(Integer idnau) {
        this.idnau = idnau;
    }

    public String getNomNau() {
        return nomNau;
    }

    public void setNomNau(String nomNau) {
        this.nomNau = nomNau;
    }

    public Integer getVidaNau() {
        return vidaNau;
    }

    public void setVidaNau(Integer vidaNau) {
        this.vidaNau = vidaNau;
    }

    public Integer getDefensaNau() {
        return defensaNau;
    }

    public void setDefensaNau(Integer defensaNau) {
        this.defensaNau = defensaNau;
    }

    public Integer getVelocitatNau() {
        return velocitatNau;
    }

    public void setVelocitatNau(Integer velocitatNau) {
        this.velocitatNau = velocitatNau;
    }

    public Integer getPotenciaNau() {
        return potenciaNau;
    }

    public void setPotenciaNau(Integer potenciaNau) {
        this.potenciaNau = potenciaNau;
    }

//    public byte[] getFotoNau() {
//        return fotoNau;
//    }
//
//    public void setFotoNau(byte[] fotoNau) {
//        this.fotoNau = fotoNau;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnau != null ? idnau.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nau)) {
            return false;
        }
        Nau other = (Nau) object;
        if ((this.idnau == null && other.idnau != null) || (this.idnau != null && !this.idnau.equals(other.idnau))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitat.Nau[ idnau=" + idnau + " ]";
    }
    
}
