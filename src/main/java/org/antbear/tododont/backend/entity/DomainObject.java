package org.antbear.tododont.backend.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public interface DomainObject<PK> extends Serializable {

    @NotNull
    PK getPK();

    void setPK(@NotNull final PK pk);
}
