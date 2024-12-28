package com.assignment.common_library.workflow;

import com.assignment.common_library.activity.AccountActivity;
import com.assignment.common_library.workflow.interfaces.AccountWorkflow;
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
    public void processUpdateAccount(Object update, long id) {

        accountActivity.updateAccount(update, id);
    }
}
