package com.assignment.auth_service.workflow.interfaces;

import com.assignment.common_library.dto.account.UpdateAccountDTO;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface AccountWorkflow {

    @WorkflowMethod
    void processUpdateAccount(UpdateAccountDTO update, long id);
}
