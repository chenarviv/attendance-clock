package assignment.devices;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashSet;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = {"intango.assignment"})
@EnableJpaRepositories(basePackages = "intango.assignment",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class))
public class MobileDevicesConfiguration {

    @Bean("GalaxyS10Plus")
    public BaseMobileDevice constructGalaxyS10Plus() {
        return new GalaxyS10Plus(new HashSet<String>() {{
            addAll(Arrays.asList("Mobile phone", "Android", "Samsung", "XL_Screen_Size",
                    "RES_1440x2560", "Front Camera"));
        }});
    }

    @Bean("GalaxyS9Edge")
    public BaseMobileDevice constructGalaxyS9Edge() {
        return new GalaxyS9Edge(new HashSet<String>() {{
            addAll(Arrays.asList("Mobile phone", "SmartPhone", "Android", "Samsung", "Medium_Screen_Size",
                    "RES_1080x1920", "Front Camera", "Back Camera"));
        }});
    }

    @Bean("Iphone11")
    public BaseMobileDevice constructIphone11() {
        return new Iphone11(new HashSet<String>() {{
            addAll(Arrays.asList("Mobile phone", "iOS", "Apple", "Big_Screen_Size",
                    "RES_720x1280", "Front Camera", "Gyroscope"));
        }});
    }

    @Bean("XiaomiMi5")
    public BaseMobileDevice constructXiaomiMi5() {
        return new XiaomiMi5(new HashSet<String>() {{
            addAll(Arrays.asList("Mobile phone", "Android", "Samsung", "Medium_Screen_Size",
                    "RES_720x1280", "Front Camera", "Back camera", "Gyroscope", "SmartPhone"));
        }});
    }

}
