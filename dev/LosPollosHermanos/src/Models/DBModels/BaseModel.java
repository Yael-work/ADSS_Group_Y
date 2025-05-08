package Models.DBModels;

import jakarta.persistence.*;

/**
 * BaseModel is an abstract class that serves as a base for all models in the application.
 */
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String modelName;


    public BaseModel(int id, String modelName) {
        if (modelName == null) {
            throw new IllegalArgumentException(String.format("%s::Name cannot be null", this.getClass().getName()));
        }
        this.id = id;
        this.modelName = modelName;
    }

    public BaseModel() {
        this.id = 0;
        this.modelName = this.getClass().getName();
    }

    public int getId() {
        return id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public void setName(String _name) {
        if (_name == null) {
            throw new IllegalArgumentException(String.format("%s::Name cannot be null", this.getClass().getName()));
        }
        this.modelName = _name;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                '}';
    }

    public String toPrettyString() {
        return String.format("ID: %d, Model Name: %s", id, modelName);
    }

    /**
     * Copies the properties of another BaseModel into this one.
     *
     * @param other The other BaseModel to copy from.
     * @return true if the copy was successful, false otherwise.
     */
    public boolean copyFromOther(BaseModel other) {
        if (other == null) {
            throw new IllegalArgumentException(String.format("%s::Other cannot be null", this.getClass().getName()));
        }
        this.id = other.id;
        this.modelName = other.modelName;
        return true;
    }
}
