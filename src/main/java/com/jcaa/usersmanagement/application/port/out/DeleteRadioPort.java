package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.RadioId;

public interface DeleteRadioPort {
  void delete(RadioId radioId);
}
