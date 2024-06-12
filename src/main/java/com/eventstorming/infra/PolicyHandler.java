representativeFor: Policy
path: {{name}}/{{{options.packagePath}}}/infra
mergeType: template
---
package {{options.package}}.infra;

import {{options.package}}.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.Message;
import java.util.function.Consumer;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler{

    @Bean
    public Consumer<Message<?>> discardFunction() {
        return message -> {
            // Ingore unnecessary message
            System.out.println("Discarded message: " + message);
        };
    }
    {{#policies}}
    {{#relationEventInfo}}
    
    @Bean
    public Consumer<Message<{{eventValue.namePascalCase}}>> whenever{{eventValue.namePascalCase}}_{{../namePascalCase}}() {
        return event -> {
            {{eventValue.namePascalCase}} {{eventValue.nameCamelCase}} = event.getPayload();
            {{#../aggregateList}}{{namePascalCase}}{{/../aggregateList}}.{{../nameCamelCase}}({{eventValue.nameCamelCase}});
        };
    }
    {{/relationEventInfo}}
    {{/policies}}
}
//>>> Clean Arch / Inbound Adaptor