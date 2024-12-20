package com.assignment.auth_service.workflow;

import com.assignment.auth_service.activity.interfaces.AccountActivity;
import com.assignment.auth_service.dto.account.UpdateAccountDTO;
import com.assignment.auth_service.workflow.interfaces.AccountWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class AccountWorkflowImpl implements AccountWorkflow {

    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(10))//set to 10 seconds for demo, default is 100 seconds
            .setBackoffCoefficient(2)
            .setMaximumAttempts(30)
            .build();

    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(retryoptions)
            .build();

    private final AccountActivity accountActivity = Workflow.newActivityStub(AccountActivity.class, defaultActivityOptions);

    @Override
    public void processUpdateAccount(UpdateAccountDTO update, long id) {

        System.out.println("Da toi core");
        accountActivity.updateAccount(update, id);
    }
}
