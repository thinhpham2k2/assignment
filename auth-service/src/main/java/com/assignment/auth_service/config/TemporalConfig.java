package com.assignment.auth_service.config;

import com.assignment.common_library.activity.AccountActivity;
import com.assignment.common_library.workflow.AccountWorkflowImpl;
import com.assignment.common_library.workflow.WorkerHelper;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TemporalConfig {

    @Value("${temporal.serviceAddress}")
    private String target;

    private final AccountActivity accountActivity;

    @PostConstruct
    public void startWorkers() {

        try {

            var stub = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                    .setTarget(target)
                    .setEnableHttps(false)
                    .build());
            var client = WorkflowClient.newInstance(stub);

            var factory = WorkerFactory.newInstance(client);

            Worker worker = factory.newWorker(WorkerHelper.WORKFLOW_ACCOUNT_TASK_QUEUE);
            worker.registerWorkflowImplementationTypes(AccountWorkflowImpl.class);
            worker.registerActivitiesImplementations(accountActivity);
            factory.start();

        } catch (Exception ignored) {

        }
    }
}
