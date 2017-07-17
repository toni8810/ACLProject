package acl.test.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toni8810 on 17/07/17.
 */
@Entity
@Table(name = "farmer")
public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long farmerId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Distributor distributor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "farmer")
    private List<Farm> farms = new ArrayList<>();

    public List<Farm> getFarms() {
        return farms;
    }

    public void setFarms(List<Farm> farms) {
        this.farms = farms;
    }

    public Long getId() {
        return farmerId;
    }

    public void setId(Long farmerId) {
        this.farmerId = farmerId;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    @Override
    public String toString() {
        return "Farmer{" +
                "farmerId=" + farmerId +
                ", distributor=" + distributor +
                ", farms=" + farms +
                '}';
    }
}
