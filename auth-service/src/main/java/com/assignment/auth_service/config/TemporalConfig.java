package com.assignment.auth_service.config;

import com.assignment.auth_service.activity.interfaces.AccountActivity;
import com.assignment.auth_service.workflow.AccountWorkflowImpl;
import com.assignment.auth_service.workflow.WorkerHelper;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TemporalConfig {

    private final AccountActivity accountActivity;

    @PostConstruct
    public void startWorkers() {
        var stub = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                .setEnableHttps(false)
                .build());
        var client = WorkflowClient.newInstance(stub);

        var factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker(WorkerHelper.WORKFLOW_ACCOUNT_TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(AccountWorkflowImpl.class);
        worker.registerActivitiesImplementations(accountActivity);
        factory.start();
    }
}