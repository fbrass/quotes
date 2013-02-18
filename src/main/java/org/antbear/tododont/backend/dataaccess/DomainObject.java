package org.antbear.tododont.backend.dataaccess;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public interface DomainObject<PK> extends Serializable {

    @NotNull
    PK getPK();

    void setPK(@NotNull final PK pk);
}
