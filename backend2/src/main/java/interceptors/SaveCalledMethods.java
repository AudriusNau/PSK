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
        File file = new File("./methods.txt");
        if (file.createNewFile())
        {
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }
        FileWriter writer = new FileWriter(file, true);
        writer.write("Called method: " + context.getMethod().getName() + " Class: " + context.getMethod().getDeclaringClass() + "\r\n");
        writer.close();
        return context.proceed();
    }
}
