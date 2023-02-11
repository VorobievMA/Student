package edu.java.satudentorder.Domein;

import edu.java.satudentorder.Domein.register.CityRegisterResponse;

public class CityRegisterCheckerResponse extends CityRegisterResponse {
    private boolean existing;
    private Boolean temporal;

    public boolean isExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }

    public Boolean getTemporal() {
        return temporal;
    }

    public void setTemporal(Boolean temporal) {
        this.temporal = temporal;
    }

    @Override
    public String toString() {
        return "CityRegisterCheckerResponse{" +
                "existing=" + existing +
                ", temporal=" + temporal +
                '}';
    }
}
