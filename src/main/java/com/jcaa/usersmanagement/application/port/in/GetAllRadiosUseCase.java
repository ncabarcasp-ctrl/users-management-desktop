package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.Radio;
import java.util.List;

public interface GetAllRadiosUseCase {
  List<Radio> execute();
}
