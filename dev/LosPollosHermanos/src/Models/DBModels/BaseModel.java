package Models.DBModels;

/**
 * BaseModel is an abstract class that serves as a base for all models in the application.
 */
public abstract class BaseModel {
    private int id;
    private String name;


    public BaseModel(int id, String name) {
        if (name == null) {
            throw new IllegalArgumentException(String.format("%s::Name cannot be null", this.getClass().getName()));
        }
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public void setName(String _name) {
        if (_name == null) {
            throw new IllegalArgumentException(String.format("%s::Name cannot be null", this.getClass().getName()));
        }
        this.name = _name;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
