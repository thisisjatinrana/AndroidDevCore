package rana.jatin.core.model;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Super class for all setModel {@link rana.jatin.core.activity.BaseIntent#setModel(Model)}
 * {@link rana.jatin.core.etc.FragmentHelper#setModel(Model)}
 * in project. Contains {@link #id} as unique setId for each object, {@link #type} type to compare object.
 */
public class Model implements Serializable {
    private long id;
    private String type;

    public Model() {
    }

    public Model(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
