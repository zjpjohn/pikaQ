package com.baidu.pikaq.core.db.commit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baidu.pikaq.core.db.commit.executor.CommitExecutor;
import com.baidu.pikaq.core.db.commit.inner.PjpCommitRunnable;

/**
 * 事务拦截器 在事务提交成功 来做
 */
@Aspect
@Component
public class AfterCommitAnnotationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfterCommitAnnotationAspect.class);

    private final CommitExecutor afterCommitExecutor = CommitExecutorFactory.getAfterCommitDefaultImpl();

    @Around(value = "@annotation(com.baidu.pikaq.core.db.commit.AfterCommit)", argNames = "pjp")
    public Object aroundAdvice(final ProceedingJoinPoint pjp) {
        afterCommitExecutor.execute(new PjpCommitRunnable(pjp));
        return null;
    }

}
