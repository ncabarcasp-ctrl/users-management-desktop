package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.Radio;
import java.util.List;

public interface GetAllRadiosPort {
  List<Radio> getAll();
}
