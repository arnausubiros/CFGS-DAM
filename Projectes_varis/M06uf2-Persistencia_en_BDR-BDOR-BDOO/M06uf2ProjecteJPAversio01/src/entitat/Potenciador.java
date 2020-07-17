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
@Table(name = "potenciador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Potenciador.findAll", query = "SELECT p FROM Potenciador p"),
    @NamedQuery(name = "Potenciador.findByIdpotenciador", query = "SELECT p FROM Potenciador p WHERE p.idpotenciador = :idpotenciador"),
    @NamedQuery(name = "Potenciador.findByVida", query = "SELECT p FROM Potenciador p WHERE p.vida = :vida"),
    @NamedQuery(name = "Potenciador.findByDefensa", query = "SELECT p FROM Potenciador p WHERE p.defensa = :defensa")})
public class Potenciador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpotenciador")
    private Integer idpotenciador;
    @Column(name = "vida")
    private Integer vida;
    @Column(name = "defensa")
    private String defensa;

    public Potenciador() {
    }

    public Potenciador(Integer idpotenciador) {
        this.idpotenciador = idpotenciador;
    }

    public Integer getIdpotenciador() {
        return idpotenciador;
    }

    public void setIdpotenciador(Integer idpotenciador) {
        this.idpotenciador = idpotenciador;
    }

    public Integer getVida() {
        return vida;
    }

    public void setVida(Integer vida) {
        this.vida = vida;
    }

    public String getDefensa() {
        return defensa;
    }

    public void setDefensa(String defensa) {
        this.defensa = defensa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpotenciador != null ? idpotenciador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Potenciador)) {
            return false;
        }
        Potenciador other = (Potenciador) object;
        if ((this.idpotenciador == null && other.idpotenciador != null) || (this.idpotenciador != null && !this.idpotenciador.equals(other.idpotenciador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitat.Potenciador[ idpotenciador=" + idpotenciador + " ]";
    }
    
}
