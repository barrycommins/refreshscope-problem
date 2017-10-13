package com.barrycommins.refreshscopeproblem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RefreshScopeProblemApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testStringProperty() {
        //check initial value
        String stringValue = restTemplate.getForObject("/string", String.class);
        assertThat(stringValue).isEqualTo("testValue");

        //update and refresh value
        System.setProperty("stringProperty", "testValue2");
        final ResponseEntity<String> refreshEntity = restTemplate.postForEntity("/refresh", "", String.class);
        assertThat(refreshEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        //test new value
        stringValue = restTemplate.getForObject("/string", String.class);
        assertThat(stringValue).isEqualTo("testValue2");
    }

    @Test
    public void testListPropertyAddItem() {
        //check initial value
        List list = restTemplate.getForObject("/list", List.class);
        assertThat(list).hasSize(2);

        //update and refresh value
        System.setProperty("listProperty", "item1,item2,item3");
        final ResponseEntity<String> refreshEntity = restTemplate.postForEntity("/refresh", "", String.class);
        assertThat(refreshEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        //test new value
        list = restTemplate.getForObject("/list", List.class);
        assertThat(list).hasSize(3);
    }

    @Test
    public void testListPropertyRemoveItem() {
        //check initial value
        List list = restTemplate.getForObject("/list", List.class);
        assertThat(list).hasSize(2);

        //update and refresh value
        System.setProperty("listProperty", "item1");
        final ResponseEntity<String> refreshEntity = restTemplate.postForEntity("/refresh", "", String.class);
        assertThat(refreshEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        //test new value
        list = restTemplate.getForObject("/list", List.class);
        assertThat(list).hasSize(1);
    }

}
