package hr.bskracic.svarog.model;

import java.io.Serializable;

public class SvarogMessage implements Serializable {
    private Long nodeId;
    private String containerId;

    public SvarogMessage(Long nodeId, String containerId) {
        this.nodeId = nodeId;
        this.containerId = containerId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
}
