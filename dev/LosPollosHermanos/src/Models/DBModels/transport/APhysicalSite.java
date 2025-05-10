package Models.DBModels.transport;

import Models.DBModels.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // or SINGLE_TABLE or TABLE_PER_CLASS
public abstract class APhysicalSite extends BaseModel {
    public APhysicalSite(int id, String name) {
        super(id, name);
    }

    public APhysicalSite() {
        super(0, APhysicalSite.class.getName());
    }

    public String getSiteName() {
        return null;
    }

    public void setSiteName(String siteName) {

    }
}
