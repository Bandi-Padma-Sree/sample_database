import java.util.ArrayList;
public interface DBInterface {
    abstract boolean editData(String query); // Supports Insert/Update/Delete queries
    abstract ArrayList<Object> getRows(String query); // Returns rows as per the query output
}