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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arnau
 */
@Entity
@Table(name = "enemics")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Enemics.findAll", query = "SELECT e FROM Enemics e"),
    @NamedQuery(name = "Enemics.findByIdenemics", query = "SELECT e FROM Enemics e WHERE e.idenemics = :idenemics"),
    @NamedQuery(name = "Enemics.findByNom", query = "SELECT e FROM Enemics e WHERE e.nom = :nom"),
    @NamedQuery(name = "Enemics.findByVelocitat", query = "SELECT e FROM Enemics e WHERE e.velocitat = :velocitat"),
    @NamedQuery(name = "Enemics.findByDefensa", query = "SELECT e FROM Enemics e WHERE e.defensa = :defensa"),
    @NamedQuery(name = "Enemics.findByPotencia", query = "SELECT e FROM Enemics e WHERE e.potencia = :potencia")})
public class Enemics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idenemics")
    private Integer  idenemics;
    @Column(name = "nom")
    private String nom;
    @Column(name = "velocitat")
    private Integer velocitat;
    @Column(name = "defensa")
    private Integer  defensa;
    @Column(name = "potencia")
    private Integer  potencia;

    public Enemics() {
    }

    public Enemics(Integer idenemics) {
        this.idenemics = idenemics;
    }

    public Integer getIdenemics() {
        return idenemics;
    }

    public void setIdenemics(Integer idenemics) {
        this.idenemics = idenemics;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getVelocitat() {
        return velocitat;
    }

    public void setVelocitat(Integer  velocitat) {
        this.velocitat = velocitat;
    }

    public Integer getDefensa() {
        return defensa;
    }

    public void setDefensa(Integer defensa) {
        this.defensa = defensa;
    }

    public Integer getPotencia() {
        return potencia;
    }

    public void setPotencia(Integer potencia) {
        this.potencia = potencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idenemics != null ? idenemics.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enemics)) {
            return false;
        }
        Enemics other = (Enemics) object;
        if ((this.idenemics == null && other.idenemics != null) || (this.idenemics != null && !this.idenemics.equals(other.idenemics))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitat.Enemics[ idenemics=" + idenemics + " ]";
    }
    
}
