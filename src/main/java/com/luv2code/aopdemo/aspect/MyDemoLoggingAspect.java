package com.luv2code.aopdemo.aspect;

import com.luv2code.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
    public Object aroundGetFortune(ProceedingJoinPoint proceedingJoinPoint) throws  Throwable {
        String method = proceedingJoinPoint.getSignature().toShortString();
        myLogger.info("Executing @Around on method: " + method);
        long begin = System.currentTimeMillis();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            myLogger.warning(e.getMessage());
            result = "Major accident! But no woories, " + "your private AOP helicopter is on the way";
        }
        long end = System.currentTimeMillis();
        long duration = end - begin;
        myLogger.info("\nDuration: " + duration/1000 + " seconds");
        return result;
    }

    @After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        myLogger.info("Executing @After (finally)on method: " + method);

    }

    @AfterThrowing(pointcut ="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",throwing = "theException")
    public void afterThrowingFindAccountsAdvice(JoinPoint joinPoint,Throwable theException) {
        String method = joinPoint.getSignature().toShortString();
        myLogger.info("Executing @AfterThrowing on method: " + method);
        myLogger.info("The exception is: " + theException);
    }

    @AfterReturning(pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))", returning = "result")
    public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result) {
        String method = theJoinPoint.getSignature().toShortString();
        myLogger.info("Excuting @AfterReturning on method: " + method);
//        print out the results of the method call

//        let post proceess
//        convert the account names to uppercase
        convertAccountNamesToUpperCase(result);
    }

    private void convertAccountNamesToUpperCase(List<Account> result) {
//        looop accounts
        for (Account tempAccount : result) {
            String theUpperName = tempAccount.getName().toUpperCase();
            tempAccount.setName(theUpperName);
            myLogger.info("Result is: " + result);
        }
    }


    @Before("com.luv2code.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()")
	public void beforeAddAccountAdvice(JoinPoint joinPoint) {
        MethodSignature methodSig = (MethodSignature) joinPoint.getSignature();
        myLogger.info("Method: " + methodSig);
		myLogger.info("Day la method ko get set");
//		get args Lay nhieu tham so
        Object[] args = joinPoint.getArgs();
        for (Object tempArg : args) {
            System.out.println(tempArg);
            if (tempArg instanceof Account) {
                Account theAccount = (Account) tempArg;
                myLogger.info(" Account name: " + theAccount.getName());
                myLogger.info("Account level: " + theAccount.getLevel());
            }
        }

	}
}










