package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import java.util.Optional;

public interface GetRadioByIdPort {
  Optional<Radio> getById(RadioId radioId);
}
