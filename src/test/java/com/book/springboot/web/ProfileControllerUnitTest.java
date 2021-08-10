package com.book.springboot.web;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileControllerUnitTest {

    @Test
    public void real_profile이_조회된다() {
        // given
        String expectedProfile = "real";
        MockEnvironment environment = new MockEnvironment();
        environment.addActiveProfile(expectedProfile);
        environment.addActiveProfile("oauth");
        environment.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(environment);

        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void real_profile이_없으면_첫_번째가_조회된다(){
        // given
        String expectedProfile = "oauth";
        MockEnvironment environment = new MockEnvironment();

        environment.addActiveProfile(expectedProfile);
        environment.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(environment);

        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);

    }

    @Test
    public void active_profile이_없으면_default가_조회된다(){
        // given
        String expectedProfile = "default";
        MockEnvironment environment = new MockEnvironment();
        ProfileController controller = new ProfileController(environment);

        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}
