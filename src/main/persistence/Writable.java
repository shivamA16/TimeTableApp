package persistence;

import org.json.JSONObject;

// Represents information that can be written to file
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
