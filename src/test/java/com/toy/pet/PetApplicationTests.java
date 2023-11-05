package com.toy.pet;

import com.toy.pet.testconfig.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestConfig.class)
@SpringBootTest
class PetApplicationTests {

	@Test
	void contextLoads() {
	}

}
