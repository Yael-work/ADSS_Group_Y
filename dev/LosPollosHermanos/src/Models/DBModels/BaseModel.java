package Models.DBModels;

/**
 * BaseModel is an abstract class that serves as a base for all models in the application.
 */
public abstract class BaseModel {
    private int id;
    private String modelName;


    public BaseModel(int id, String modelName) {
        if (modelName == null) {
            throw new IllegalArgumentException(String.format("%s::Name cannot be null", this.getClass().getName()));
        }
        this.id = id;
        this.modelName = modelName;
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
}
