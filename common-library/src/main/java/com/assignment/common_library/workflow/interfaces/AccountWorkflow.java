package com.assignment.common_library.workflow.interfaces;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface AccountWorkflow {

    @WorkflowMethod
    void processUpdateAccount(Object update, long id);
}
