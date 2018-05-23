package rana.jatin.core.model;

import java.io.Serializable;

import rana.jatin.core.base.BaseIntent;
import rana.jatin.core.util.FragmentUtil;

/**
 * Super class for all setModel {@link BaseIntent#setModel(Model)}
 * {@link FragmentUtil#setModel(Model)}
 * in project. Contains {@link #modelId} as unique setId for each object
 */
public class Model implements Serializable {
    private long modelId;

    public Model() {
    }

    public Model(long modelId) {
        this.modelId = modelId;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

}
