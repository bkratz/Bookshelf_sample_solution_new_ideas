package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test-prod")
class EnvironmentTest {

	@Value("${server.port}")
	private int port;

	@Test
	@EnabledIf(expression = "#{environment.acceptsProfiles('test-prod', 'someotherprofile')}", loadContext = true)
	void verifyProdPort() {
		assertEquals(8090, port);
	}
}
