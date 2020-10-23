package fr.fpage.swingy.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Artefact {

    @NotNull
    @Min(0)
    private final Integer value;
    @NotNull
    private final ArtefactTypes artefactTypes;

    public Artefact(Integer value, ArtefactTypes artefactTypes) {
        this.value = value;
        this.artefactTypes = artefactTypes;
    }

    @NotNull
    public ArtefactTypes getArtefactTypes() {
        return artefactTypes;
    }

    @NotNull
    @Min(0)
    public Integer getValue() {
        return value;
    }
}
