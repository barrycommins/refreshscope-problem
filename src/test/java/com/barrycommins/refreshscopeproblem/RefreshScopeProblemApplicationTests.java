package com.barrycommins.refreshscopeproblem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"stringProperty=testValue", "listProperty=item1,item2"})
public class RefreshScopeProblemApplicationTests {

    @Autowired
    private RefreshScopeConfigurationProperties properties;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private org.springframework.cloud.context.scope.refresh.RefreshScope scope;

    @Autowired
    private ConfigurableEnvironment environment;

    @Test
    @DirtiesContext
    public void testStringValue() {
        assertThat(this.properties.getStringProperty()).isEqualTo("testValue");
        EnvironmentTestUtils.addEnvironment(this.environment, "stringProperty:newValue");
        this.scope.refreshAll();
        assertThat(this.properties.getStringProperty()).isEqualTo("newValue");
    }

    @Test
    @DirtiesContext
    public void testListValuesChanged() {
        assertThat(this.properties.getListProperty()).hasSize(2);
        assertThat(this.properties.getListProperty()).contains("item1", "item2");
        EnvironmentTestUtils.addEnvironment(this.environment, "listProperty:itemA,itemB");
        this.scope.refreshAll();
        assertThat(this.properties.getListProperty()).hasSize(2);
        assertThat(this.properties.getListProperty()).contains("itemA", "itemB");
    }

    @Test
    @DirtiesContext
    public void testListValueAdded() {
        assertThat(this.properties.getListProperty()).hasSize(2);
        assertThat(this.properties.getListProperty()).contains("item1", "item2");
        EnvironmentTestUtils.addEnvironment(this.environment, "listProperty:item1,item2,item3");
        this.scope.refreshAll();
        assertThat(this.properties.getListProperty()).hasSize(3);
        assertThat(this.properties.getListProperty()).contains("item1", "item2", "item3");
    }

    @Test
    @DirtiesContext
    public void testListValueRemoved() {
        assertThat(this.properties.getListProperty()).hasSize(2);
        assertThat(this.properties.getListProperty()).contains("item1", "item2");
        EnvironmentTestUtils.addEnvironment(this.environment, "listProperty:item1");
        this.scope.refreshAll();
        assertThat(this.properties.getListProperty()).hasSize(1);
        assertThat(this.properties.getListProperty()).contains("item1");
    }
}
