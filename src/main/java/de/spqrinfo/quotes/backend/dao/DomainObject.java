package de.spqrinfo.quotes.backend.dao;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public interface DomainObject<PK> extends Serializable {

    @NotNull
    PK getPK();

    void setPK(@NotNull final PK pk);
}
