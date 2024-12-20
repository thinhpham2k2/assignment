package com.assignment.core_service.workflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

import java.time.Duration;

public class WorkerHelper {

    public static final String WORKFLOW_ACCOUNT_TASK_QUEUE = "AccountTaskQueue";

    public static RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();

    private static WorkflowOptions getWorkflowOptions(String taskQueue, String workflowId) {
        var builder = WorkflowOptions.newBuilder();

        builder.setWorkflowId(workflowId);
        builder.setTaskQueue(taskQueue);
        builder.setWorkflowRunTimeout(java.time.Duration.ofMinutes(5));
        builder.setWorkflowTaskTimeout(java.time.Duration.ofMinutes(1));
        return builder.build();
    }

    public static WorkflowClient getWorkflowClient() {
        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                .setEnableHttps(false)
                .build());
        return WorkflowClient.newInstance(service);
    }

    public static ActivityOptions defaultActivityOptions() {
        return
                ActivityOptions.newBuilder()
                        // Timeout options specify when to automatically timeout Activities if the process is taking too long.
                        .setStartToCloseTimeout(Duration.ofSeconds(5))
                        .setRetryOptions(retryoptions)
                        .build();
    }
}
