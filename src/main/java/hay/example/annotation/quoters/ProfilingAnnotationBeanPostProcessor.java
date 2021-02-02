package hay.example.annotation.quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class> map = new HashMap<>();
    private ProfilingController controller = new ProfilingController();

    public ProfilingAnnotationBeanPostProcessor() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(controller, new ObjectName("profiling","name","controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader(),
                    beanClass.getInterfaces(),
                    (proxy, method, args) -> {
                        if (controller.isEnabled()) {
                            System.out.println("Profiling...");
                            long before = System.nanoTime();
                            Object invoke = method.invoke(bean, args);
                            long after = System.nanoTime();
                            System.out.println(after - before);
                            System.out.println("Finish");
                            return invoke;
                        } else {
                            return method.invoke(bean, args);
                        }
                    });
        }

        return bean;
    }
}
