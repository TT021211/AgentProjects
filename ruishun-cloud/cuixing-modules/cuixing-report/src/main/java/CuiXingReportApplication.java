import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @Author wupuj
 * @create 2024/7/15 14:39
 */
@EnableCaching
@SpringBootApplication(scanBasePackages = {"org.jeecg", "com.cuixing"}, exclude = {MongoAutoConfiguration.class})
public class CuiXingReportApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(CuiXingReportApplication.class, args);
        Environment env = application.getEnvironment();
        String port = env.getProperty("server.port");
        String path = env.containsProperty("server.servlet.context-path") ? env.getProperty("server.servlet.context-path") : "";
        System.out.print("\n----------------------------------------------------------\n\t" +
                "JimuReport 积木报表平台 is running!  Access URL:\n\t" +
                "报表工作台: \t\thttp://localhost:" + port + path + "/jmreport/list\n\t" +
                "仪表盘工作台: \t\thttp://localhost:" + port + path + "/drag/list\n\t");
        System.out.println("(♥◠‿◠)ﾉﾞ  报表服务启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
