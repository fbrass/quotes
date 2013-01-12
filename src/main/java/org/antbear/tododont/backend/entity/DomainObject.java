package org.antbear.tododont.backend.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public interface DomainObject extends Serializable {

    @NotNull
    Long getId();

    void setId(@NotNull final Long id);
}
