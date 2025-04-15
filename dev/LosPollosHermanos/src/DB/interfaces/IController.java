package DB.interfaces;

import java.util.ArrayList;

public interface IController<TypeObj> {
    ArrayList<TypeObj> getAllRecords();
}
