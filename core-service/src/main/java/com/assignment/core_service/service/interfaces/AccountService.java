package com.assignment.core_service.service.interfaces;

import com.assignment.core_service.dto.account.AccountDTO;
import com.assignment.core_service.dto.account.CreateAccountDTO;
import com.assignment.core_service.dto.account.UpdateAccountDTO;
import org.springframework.data.web.PagedModel;

public interface AccountService {

    boolean isDuplicateUsername(String userName);

    boolean existsById(long id);

    AccountDTO findById(long id);

    PagedModel<AccountDTO> findAllByCondition(String search, String sort, int page, int limit);

    void create(CreateAccountDTO create);

    void update(UpdateAccountDTO update, long id);

    void delete(long id);
}
