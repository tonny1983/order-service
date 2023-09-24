# Test

## Consumer Driven Contract tests

### Problem to solve
The service depends on the `catalog-service`, and therefore, there is an API contract between the service and `catalog-service`.

As a consumer of `catalog-service`, the service should believe it can retrieve the data according to the agreement.

How can the agreement/contract be verified?

### Solution
Add tests for the agreement/contract

### Discussion
The consumer side test depends on the stub which is generated in producer side (which is `catalog-service`) in the case.
Once the stub generated and been available, the consumer can retrieve it and run the tests.
`SPRING_PROFILES_ACTIVE=contract` should be added in order to run the contract tests in the service.