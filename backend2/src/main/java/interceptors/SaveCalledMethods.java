package interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Interceptor
@DevbridgeInterceptor
public class SaveCalledMethods implements Serializable{
    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        System.out.println("Called method: " + context.getMethod().getName());
        System.out.println(context.getMethod().getDeclaringClass());
        List<String[]> calledMethods = new ArrayList<>();
        return context.proceed();
    }
}
